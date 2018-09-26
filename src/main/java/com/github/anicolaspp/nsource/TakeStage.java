package com.github.anicolaspp.nsource;

class TakeStage<A> extends ComposableStage<A> {
    
    private final ComposableStage<A> previousStage;
    private int n;
    
    TakeStage(ComposableStage<A> previousStage, int n) {
        
        this.previousStage = previousStage;
        this.n = n;
    }
    
    @Override
    protected boolean moveNext() {
        if (n > 0) {
            if (!previousStage.moveNext()) {
                return false;
            } else {
                current = previousStage.getCurrent();
                hasMoved = true;
                n--;
                
                return true;
            }
        } else {
            return false;
        }
    }
}
