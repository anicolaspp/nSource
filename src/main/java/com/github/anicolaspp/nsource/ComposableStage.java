package com.github.anicolaspp.nsource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class ComposableStage<A> {
    
    protected abstract boolean moveNext();
    
    protected A current;
    
    protected boolean hasMoved = false;
    
    protected boolean isConsumed = false;
    
    protected A getCurrent() {
        if (!hasMoved) {
            throw new InvalidOperationException("moveNext() should be called before calling getCurrent()");
        }
        
        return current;
    }
    
    public boolean getIsConsumed() {
        return isConsumed;
    }
    
    void close() {
        isConsumed = true;
    }
    
    public <B> ComposableStage<B> map(Function<A, B> fn) {
        return new MappedStage<>(this, fn);
    }
    
    public ComposableStage<A> filter(Predicate<A> predicate) {
        return new FilteredStage<>(this, predicate);
    }
    
    public ComposableStage<A> take(int n) {
        return new TakeStage<>(this, n);
    }
    
    public ComposableStage<A> takeWhile(Predicate<A> predicate) {
        return new TakeWhileStage<>(this, predicate);
    }
    
    public RunnableStage<List<A>> toList() {
        return foldLeft(new ArrayList<>(), (a, l) -> {
            l.add(a);
            
            return l;
        });
    }
    
    public <B> RunnableStage<B> foldLeft(B zero, BiFunction<A, B, B> biFunction) {
        throwExceptionIfConsumed();
        
        return new Fold<>(this, zero, biFunction);
    }
    
    public RunnableStage<Done> forEach(Consumer<A> consumer) {
        throwExceptionIfConsumed();
    
        return new ForEach<>(this, consumer);
    }
    
    public RunnableStage<Optional<A>> first() {
        throwExceptionIfConsumed();
    
        return new First<>(this);
    }
    
    public RunnableStage<A> firstOrDefault(Supplier<A> defaultValue) {
        throwExceptionIfConsumed();
    
        return new FirstOrDefault<>(this, defaultValue);
    }
    
    public RunnableStage<Boolean> anyMatch(Predicate<A> predicate) {
        return () -> this.filter(predicate).first().run().isPresent();
    }
    
    public RunnableStage<Boolean> allMatch(Predicate<A> predicate) {
        return () -> this.foldLeft(true, (item, acc) -> acc && predicate.test(item)).run();
    }
    
    private void throwExceptionIfConsumed() {
        if (isConsumed) {
            throw new RuntimeException("");
        }
    }
}

