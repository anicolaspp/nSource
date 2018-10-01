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
    public void testForEachTwice() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        StringBuilder stringBuilder = new StringBuilder();
        
        val append = nSource.from(source).forEach(stringBuilder::append);
        
        append.run();
        assert stringBuilder.toString().equals("12345");
        
        append.run();
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
                .from(new ArrayList<>())
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
    
    @Test(expected = RuntimeException.class)
    public void testClose() {
        
        ComposableStage<Integer> stage = nSource.from(new ArrayList<>());
        
        assert !stage.first().run().isPresent();
        
        stage.first();
    }
    
    @Test
    public void testAnyMatchTrue() {
        val source = Arrays.asList(1, 2, 3, -1, 5);
        
        assert nSource
                .from(source)
                .anyMatch(n -> n < 0)
                .run();
    }
    
    @Test
    public void testAnyMatchFalse() {
        val source = Arrays.asList(1, 2, 3, -1, 5);
        
        assert !nSource
                .from(source)
                .anyMatch(n -> n > 10)
                .run();
    }
    
    @Test
    public void testAllMatchTrue() {
        val source = Arrays.asList(1, 2, 3, 4, 5);
        
        assert nSource
                .from(source)
                .allMatch(n -> n > 0)
                .run();
    }
    
    @Test
    public void testAllMatchFalse() {
        val source = Arrays.asList(1, 2, 3, 4, 0);
        
        assert !nSource
                .from(source)
                .allMatch(n -> n > 0)
                .run();
    }
}