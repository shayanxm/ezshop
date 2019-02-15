package com.example.shayanmoradi.ezshop.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("date_created")
    private String mDateCreated;
    @SerializedName("description")
    private String mCompliteDescription;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("related_ids")
    private List<Integer>mRelatedIds;

    private List<Image> images;


    public Product(int mId, String mName, String mDateCreated, String mCompliteDescription, String mPrice, List<Integer> mRelatedIds, List<Image> images, String mCategory) {
        this.mId = mId;
        this.mName = mName;
        this.mDateCreated = mDateCreated;
        this.mCompliteDescription = mCompliteDescription;
        this.mPrice = mPrice;
        this.mRelatedIds = mRelatedIds;
        this.images = images;

    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmDateCreated() {
        return mDateCreated;
    }

    public String getmCompliteDescription() {
        return mCompliteDescription;
    }

    public String getmPrice() {
        return mPrice;
    }


    public List<Image> getImages() {
        return images;
    }

    public List<Integer> getmRelatedIds() {
        return mRelatedIds;
    }
}
