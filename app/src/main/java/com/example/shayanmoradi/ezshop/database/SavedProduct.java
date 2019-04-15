package com.example.shayanmoradi.ezshop.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class SavedProduct {
    @Id(autoincrement = true)
    private Long id;
    private int ProductId;
    private String productName;
    private String productPrice;
    private int count;

    private String productImagePath;

    @Generated(hash = 113351724)
    public SavedProduct(Long id, int ProductId, String productName,
            String productPrice, int count, String productImagePath) {
        this.id = id;
        this.ProductId = ProductId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.count = count;
        this.productImagePath = productImagePath;
    }

    @Generated(hash = 1860125091)
    public SavedProduct() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getProductId() {
        return this.ProductId;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductImagePath() {
        return this.productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }


}
