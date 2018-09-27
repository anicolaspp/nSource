package com.github.anicolaspp.nsource;

import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunnableStageTest {
    
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
    
    @Test
    public void testMutiRun() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
    
        val result = nSource
                .from(source)
                .first();
        
        assert result.run().get() == result.run().get();
    }
}