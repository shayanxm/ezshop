package com.example.shayanmoradi.ezshop.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private String slug;
    private int id;
    @SerializedName("parent")
    private int parnetId;
    @SerializedName("image")
    private Image images;

    public Category(String name, String slug, int id, int parnetId, Image images) {
        this.name = name;
        this.slug = slug;
        this.id = id;
        this.parnetId = parnetId;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public int getId() {
        return id;
    }

    public int getParnetId() {
        return parnetId;
    }

    public Image getImages() {
        return images;
    }

    public static List<Category> filterParents(List<Category> categories) {
        List<Category> categoriesFinal = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getParnetId() ==0)
                categoriesFinal.add(categories.get(i));
        }
        return categoriesFinal;
    }
    public static List<Category>getChilderns(int id){
        List<Category>categories= Repository.getInstance().getAllCategories();
        List<Category> categoriesFinal = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getParnetId() ==id)
                categoriesFinal.add(categories.get(i));
        }
        return categoriesFinal;
    }
}
