package com.github.anicolaspp.nsource;

import java.util.function.Function;

class MappedStage<A, B> extends ComposableStage<B> {
    
    private final ComposableStage<A> previousStage;
    private final Function<A, B> fn;
    
    MappedStage(ComposableStage<A> previousStage, Function<A, B> fn) {
        
        this.previousStage = previousStage;
        this.fn = fn;
    }
    
    @Override
    protected boolean moveNext() {
        if (!previousStage.moveNext()) {
            return false;
        } else {
            current = fn.apply(previousStage.getCurrent());
            hasMoved = true;
            
            return true;
        }
    }
}
