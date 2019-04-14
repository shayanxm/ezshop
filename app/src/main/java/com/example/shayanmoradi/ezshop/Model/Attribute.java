package com.example.shayanmoradi.ezshop.Model;

import java.util.List;

public class Attribute {
    private String name;
    private List<String>options;
    int id;

    public Attribute(String name, List<String> options, int id) {
        this.name = name;
        this.options = options;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getOptions() {
        return options;
    }
}
