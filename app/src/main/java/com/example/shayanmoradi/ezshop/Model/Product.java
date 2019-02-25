package com.example.shayanmoradi.ezshop.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("average_rating")
    private String averageRate;
    @SerializedName("total_sales")
    private String totalSale;
    @SerializedName("date_created")
    private String mDateCreated;
    @SerializedName("description")
    private String mCompliteDescription;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("related_ids")
    private List<Integer> mRelatedIds;
    private List<Attribute> attributes;

    private List<Image> images;
    private String enlgishName;

    public Product(int mId,String totalSale, String averageRate, String mName, String mDateCreated, String mCompliteDescription, String mPrice, List<Attribute> attributes, List<Integer> mRelatedIds, List<Image> images, String mCategory) {
        this.mId = mId;
        this.totalSale=totalSale;
        this.averageRate = averageRate;
        this.attributes = attributes;
        this.mName = mName;
        this.mDateCreated = mDateCreated;
        this.mCompliteDescription = mCompliteDescription;
        this.mPrice = mPrice;
        this.mRelatedIds = mRelatedIds;
        this.images = images;

    }

    public String getTotalSale() {
        return totalSale;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public String getAverageRate() {
        return averageRate;
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

    public String getEnlgishName(Product product) {

        String fullName = product.getmName();
        int whereToConcat = 0;
        for (int i = 0; i < fullName.length(); i++) {

            boolean sate = Character.isLetter(fullName.charAt(i));
            if (sate == true)
                whereToConcat = i;


        }
        String englishName = fullName.substring(whereToConcat, product.getmName().length());
        return whereToConcat + product.getmName().length() + "";
    }

    public static List<Product> getTopHits(List<Product>allProducts) {

       // List<Product> allProducts = Repository.getInstance().getAllProducts();
        List<Product> resList = new ArrayList<>();


        int n = allProducts.size();
        Product[] tempList= allProducts.toArray(new Product[n]);
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++) {
                String first = tempList[j].getAverageRate();
                float firstf = Float.valueOf(first);

                String second = tempList[j+1].getAverageRate();
                float secondf = Float.valueOf(second);

                if (firstf > secondf) {
                    // swap arr[j+1] and arr[i]
                    // float temp = Float.valueOf(allProducts.get(j).getAverageRate());
                    Product tempis= tempList[j];
                    tempList[j]=tempList[j+1];
                    tempList[j+1]=tempis;


                }
            }
            resList=Arrays.asList(tempList);
        return resList;
    }
    public static List<Product> getTopsales(List<Product>allProducts) {

        //List<Product> allProducts = Repository.getInstance().getAllProducts();
        List<Product> resList = new ArrayList<>();


        int n = allProducts.size();
        Product[] tempList= allProducts.toArray(new Product[n]);
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++) {
                String first = tempList[j].getTotalSale();
                int firstf = Integer.valueOf(first);

                String second = tempList[j+1].getTotalSale();
                int secondf = Integer.valueOf(second);

                if (firstf > secondf) {
                    // swap arr[j+1] and arr[i]
                    // float temp = Float.valueOf(allProducts.get(j).getAverageRate());
                    Product tempis= tempList[j];
                    tempList[j]=tempList[j+1];
                    tempList[j+1]=tempis;


                }
            }
        resList=Arrays.asList(tempList);
        return resList;
    }




}
