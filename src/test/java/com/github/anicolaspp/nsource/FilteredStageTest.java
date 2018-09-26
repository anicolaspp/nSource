package com.github.anicolaspp.nsource;

import lombok.val;
import org.junit.Test;

import java.util.Arrays;

public class FilteredStageTest {
    @Test
    public void testFilter() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .filter(x -> x % 2 == 0)
                .map(n -> n + 1)
                .map(Object::toString)
                .foldLeft("", (a, b) -> b + a)
                .run();
        
        assert result.equals("35");
    }
    
    @Test
    public void testFilterRecursion() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .filter(x -> x == 5)
                .toList()
                .run();
        
        assert result.size() == 1;
    }
}
