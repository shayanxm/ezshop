package com.example.shayanmoradi.ezshop.network;

import com.example.shayanmoradi.ezshop.Model.Product;

public class Root {
private Product product;

    public Root(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
