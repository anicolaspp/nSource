package com.github.anicolaspp.nsource;

import lombok.val;
import org.junit.Test;

import java.util.Arrays;

public class TakeStageTest {
    @Test
    public void testTake() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .take(3)
                .toList()
                .run();
        
        assert result.size() == 3;
        
        assert result.get(0) == 1;
        assert result.get(1) == 2;
        assert result.get(2) == 3;
    }
    
    @Test
    public void testTakeNothing() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .take(0)
                .toList()
                .run();
        
        assert result.size() == 0;
    }
    
    @Test
    public void testTakeTooMuch() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .take(6)
                .toList()
                .run();
        
        assert result.size() == 5;
    }
}
