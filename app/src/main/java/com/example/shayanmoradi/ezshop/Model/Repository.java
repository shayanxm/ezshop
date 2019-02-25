package com.example.shayanmoradi.ezshop.Model;

import java.util.ArrayList;
import java.util.List;

public class Repository {


    private static Repository instance;
    List<Product> allProducts;
    List<Category> allCategories;
    List<Product> topHits;
    List<Product> newest;
    List<Product> topRated;

    private Repository() {
        allProducts = new ArrayList<>();
        allCategories=new ArrayList<>();
        topHits=new ArrayList<>();
        newest=new ArrayList<>();
        topRated=new ArrayList<>();

    }

    public List<Product> getTopHits() {
        return topHits;
    }

    public void setTopHits(List<Product> topHits) {
        this.topHits = topHits;
    }

    public List<Product> getNewest() {
        return newest;
    }

    public void setNewest(List<Product> newest) {
        this.newest = newest;
    }

    public List<Product> getTopRated() {
        return topRated;
    }

    public void setTopRated(List<Product> topRated) {
        this.topRated = topRated;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }


}
