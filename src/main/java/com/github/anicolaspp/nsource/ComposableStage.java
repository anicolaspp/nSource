package com.github.anicolaspp.nsource;

import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ComposableStage<A> {
    
    protected abstract boolean moveNext();
    
    protected A current;
    
    protected boolean hasMoved = false;
    
    protected A getCurrent() {
        if (!hasMoved) {
            throw new InvalidOperationException("moveNext() should be call before calling getCurrent()");
        }
        
        return current;
    }
    
    public <B> ComposableStage<B> map(Function<A, B> fn) {
        return new MappedStage<>(this, fn);
    }
    
    public ComposableStage<A> filter(Predicate<A> predicate) {
        return new FilteredStage<>(this, predicate);
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
}


class FilteredStage<A> extends ComposableStage<A> {
    
    private final ComposableStage<A> previousStage;
    private final Predicate<A> predicate;
    
    public FilteredStage(ComposableStage<A> previousStage, Predicate<A> predicate) {
        
        this.previousStage = previousStage;
        this.predicate = predicate;
    }
    
    @Override
    protected boolean moveNext() {
        if (!previousStage.moveNext()) {
            return false;
        } else {
            val a = previousStage.getCurrent();
            
            if (predicate.test(a)) {
                current = a;
                hasMoved = true;
                
                return true;
            } else {
                return moveNext();
            }
        }
    }
}
