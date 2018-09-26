package com.github.anicolaspp.nsource;

public class nSource {
    
    public static <A> ComposableStage<A> from(Iterable<A> source) {
        return new BasicComposableStage<>(source.iterator());
    }
}

