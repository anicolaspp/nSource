package com.github.anicolaspp.nsource;

import java.util.function.BiFunction;

class Fold<A, B> implements RunnableStage<B> {
    
    private ComposableStage<A> source;
    private final B zero;
    private final BiFunction<A, B, B> biFunction;
    private boolean hasValue = false;
    private B value;
    
    Fold(ComposableStage<A> source, B zero, BiFunction<A, B, B> biFunction) {
        this.source = source;
        this.zero = zero;
        this.biFunction = biFunction;
    }
    
    @Override
    public B run() {
        if (hasValue) {
            return value;
        }
        
        source.close();
        hasValue = true;
    
        B result = zero;
    
        while (source.moveNext()) {
            result = biFunction.apply(source.getCurrent(), result);
        }
    
        value = result;
        
        return result;
    }
}
