package com.github.anicolaspp.nsource;

import java.util.Optional;

class First<A> implements RunnableStage<Optional<A>> {
    
    private ComposableStage<A> stage;
    
    private Optional<A> value;
    
    private boolean hasValue = false;
    
    First(ComposableStage<A> stage) {
        this.stage = stage;
        
    }
    
    @Override
    public Optional<A> run() {
        if (hasValue) {
            return value;
        } else {
            this.stage.close();
            
            value = getReturnValue();
            hasValue = true;
    
            return value;
        }
    }
    
    private Optional<A> getReturnValue() {
        if (stage.moveNext()) {
            return Optional.of(stage.getCurrent());
        } else {
            return Optional.empty();
        }
    }
}
