package com.github.anicolaspp.nsource;

import java.util.function.Predicate;

class TakeWhileStage<A> extends ComposableStage<A> {
    private final ComposableStage<A> previousStage;
    private final Predicate<A> predicate;
    
    TakeWhileStage(ComposableStage<A> previousStage, Predicate<A> predicate) {
        this.previousStage = previousStage;
        this.predicate = predicate;
    }
    
    @Override
    protected boolean moveNext() {
        if (previousStage.moveNext() && predicate.test(previousStage.getCurrent())) {
            current = previousStage.getCurrent();
            hasMoved = true;
            
            return true;
        } else {
            return false;
        }
    }
}
