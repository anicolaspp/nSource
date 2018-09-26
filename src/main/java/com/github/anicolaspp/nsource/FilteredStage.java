package com.github.anicolaspp.nsource;

import lombok.val;

import java.util.function.Predicate;

class FilteredStage<A> extends ComposableStage<A> {
    
    private final ComposableStage<A> previousStage;
    private final Predicate<A> predicate;
    
    FilteredStage(ComposableStage<A> previousStage, Predicate<A> predicate) {
        
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
