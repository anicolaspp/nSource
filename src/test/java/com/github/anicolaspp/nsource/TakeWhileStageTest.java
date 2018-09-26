package com.github.anicolaspp.nsource;

import lombok.val;
import org.junit.Test;

import java.util.Arrays;

public class TakeWhileStageTest {
    
    @Test
    public void testTakeWhile() {
        val source = Arrays.asList(1, 1, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .takeWhile(x -> x == 1)
                .toList()
                .run();
        
        assert result.size() == 2;
    }
    
    @Test
    public void testTakeWhileNothing() {
        val source = Arrays.asList(1, 1, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .takeWhile(x -> x < 0)
                .toList()
                .run();
        
        assert result.size() == 0;
    }
    
    @Test
    public void testTakeWhileSkipAFew() {
        val source = Arrays.asList(1, 1, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .takeWhile(x -> x != 5)
                .toList()
                .run();
        
        assert result.size() == 4;
    }
}
