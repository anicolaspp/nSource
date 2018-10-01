package com.github.anicolaspp.nsource;

public class Done {
    private static Done instance = new Done();
    
    private Done() {}
    
    public static Done getInstance() {
        return instance;
    }
}
