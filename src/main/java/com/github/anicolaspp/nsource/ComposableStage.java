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
    
    protected A getCurrent() {
        if (!hasMoved) {
            throw new InvalidOperationException("moveNext() should be called before calling getCurrent()");
        }
        
        return current;
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
        return () -> {
            var result = zero;
            
            while (moveNext()) {
                result = biFunction.apply(getCurrent(), result);
            }
            
            return result;
        };
    }
    
    public RunnableStage<Done> forEach(Consumer<A> consumer) {
        return () -> {
            while (moveNext()) {
                consumer.accept(getCurrent());
            }
            
            return Done.getInstance();
        };
    }
    
    public RunnableStage<Optional<A>> first() {
        return () -> {
            if (moveNext()) {
                return Optional.of(getCurrent());
            } else {
                return Optional.empty();
            }
        };
    }
    
    public RunnableStage<A> firstOrDefault(Supplier<A> defaultValue) {
        return () -> {
            if (moveNext()) {
                return getCurrent();
            } else {
                return defaultValue.get();
            }
        };
    }
}

