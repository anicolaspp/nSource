package com.github.anicolaspp.nsource;

import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    
    @Test
    public void testFirstSome() {
        val source = Arrays.asList(1, 1, 3, 4, 5);
        
        val result = nSource
                .from(source)
                .first()
                .run();
        
        assert result.get() == 1;
    }
    
    @Test
    public void testFirtNone() {
        val result = nSource
                .from(List.of())
                .first()
                .run();
        
        assert !result.isPresent();
    }
    
    @Test
    public void testFirstOrDefault() {
        assert nSource
                .from(List.<Integer>of())
                .firstOrDefault(() -> 7)
                .run() == 7;
        
        assert nSource
                .from(Arrays.asList(1, 1, 3, 4, 5))
                .firstOrDefault(() -> 7)
                .run() == 1;
    }
}
