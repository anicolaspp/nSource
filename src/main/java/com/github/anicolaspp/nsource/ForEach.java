package com.github.anicolaspp.nsource;

import java.util.function.Consumer;

class ForEach<A> implements RunnableStage<Done> {
    
    private ComposableStage<A> source;
    private Consumer<A> consumer;
    
    private boolean hasValue = false;
    
    ForEach(ComposableStage<A> source, Consumer<A> consumer) {
        this.source = source;
        this.consumer = consumer;
    }
    
    @Override
    public Done run() {
        if (hasValue) {
            return Done.getInstance();
        }
    
        this.source.close();
        
        while (source.moveNext()) {
            hasValue = true;
            consumer.accept(source.getCurrent());
        }
    
        return Done.getInstance();
    }
}
