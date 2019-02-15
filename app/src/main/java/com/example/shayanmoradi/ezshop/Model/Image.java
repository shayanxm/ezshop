package com.example.shayanmoradi.ezshop.Model;

import com.google.gson.annotations.SerializedName;

public class Image {
@SerializedName("src")
    private String path;

    public Image(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
