package com.github.anicolaspp.nsource;

import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class BasicStageTest {
    
    @Test
    public void testToList() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .toList()
                .run();
        
        assert result.containsAll(source);
    }
    
    @Test
    public void testFoldLeft() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .foldLeft(new ArrayList<>(), (a, l) -> {
                    l.add(a);
                    
                    return l;
                })
                .run();
        
        assert result.containsAll(source);
    }
    
    @Test
    public void testFoldLeftJoining() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .foldLeft("", (a, b) -> b + a)
                .run();
        
        assert result.equals("12345");
    }
    
    @Test
    public void testForEach() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        StringBuilder stringBuilder = new StringBuilder();
        
        nSource.from(source).forEach(stringBuilder::append).run();
        
        assert stringBuilder.toString().equals("12345");
    }
    
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
}
