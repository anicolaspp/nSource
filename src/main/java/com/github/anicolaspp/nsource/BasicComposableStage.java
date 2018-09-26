package com.github.anicolaspp.nsource;

import java.util.Iterator;

class BasicComposableStage<A> extends ComposableStage<A> {
    
    private Iterator<A> source;
    
    BasicComposableStage(Iterator<A> source) {
        
        this.source = source;
    }
    
    @Override
    protected boolean moveNext() {
        if (source.hasNext()) {
            current = source.next();
            hasMoved = true;
            
            return true;
        } else {
            return false;
        }
    }
}
