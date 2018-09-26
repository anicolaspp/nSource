package com.github.anicolaspp.nsource;

import lombok.val;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;

public class MappedStageTest {
    
    @Test
    public void testMap() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .map(n -> n + 1)
                .map(Object::toString)
                .foldLeft("", (a, b) -> b + a)
                .run();
        
        assert result.equals("23456");
    }
    
    @Test
    public void testMapIdentity() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .map(Function.identity())
                .toList()
                .run();
        
        assert result.containsAll(source);
    }
}