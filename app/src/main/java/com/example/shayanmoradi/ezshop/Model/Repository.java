package com.example.shayanmoradi.ezshop.Model;

import java.util.ArrayList;
import java.util.List;

public class Repository {


    private static Repository instance;
    List<Product> allProducts;
    List<Category> allCategories;


    private Repository() {
        allProducts = new ArrayList<>();
        allCategories=new ArrayList<>();

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
