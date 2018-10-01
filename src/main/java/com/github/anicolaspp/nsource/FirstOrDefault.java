package com.github.anicolaspp.nsource;

import java.util.function.Supplier;

class FirstOrDefault<A> implements RunnableStage<A> {
    
    private final ComposableStage<A> source;
    private final Supplier<A> defaultValue;
    
    private boolean hasValue = false;
    
    private A value;
    
    FirstOrDefault(ComposableStage<A> source, Supplier<A> defaultValue) {
        this.source = source;
        
        this.defaultValue = defaultValue;
    }
    
    @Override
    public A run() {
        if (hasValue) {
            return value;
        } else {
            this.source.close();
            hasValue = true;
            value = getResult(source, defaultValue);
            
            return value;
        }
    }
    
    private A getResult(ComposableStage<A> cs, Supplier<A> defaultValue) {
        if (cs.moveNext()) {
            return cs.getCurrent();
        } else {
            return defaultValue.get();
        }
    }
}
