package com.example.shayanmoradi.ezshop.database;

import android.content.Context;

import com.example.shayanmoradi.ezshop.slider.App;

import java.util.List;

public class SavedProductsManger {
    public static SavedProductsManger instance;
    private Context mContext;

    private DaoSession daoSession = App.getApp().getDaoSession();
    private SavedProductDao detailsDao = daoSession.getSavedProductDao();

    public SavedProductsManger(Context context) {
        mContext = context.getApplicationContext();
    }

    public static SavedProductsManger getInstance(Context context) {
        if (instance == null)
            instance = new SavedProductsManger(context);
        return instance;
    }

    public SavedProduct getProduct(int productId) {
        List<SavedProduct> savedProductList = detailsDao.queryBuilder()
                .where(SavedProductDao.Properties.ProductId.eq(productId))
                .list();
        for (SavedProduct savedProduct : savedProductList) {
            return savedProduct;
        }
        return null;
    }
    public List<SavedProduct> getBag(){
        return detailsDao.loadAll();

    }
    public void addToBag(SavedProduct songDetails) {
        detailsDao.insert(songDetails);
    }

    public void update(SavedProduct songDetails) {
        detailsDao.update(songDetails);
    }

    public void delete(SavedProduct songDetails) {
        detailsDao.delete(songDetails);
    }
    public void deleteBgag() {
        detailsDao.deleteAll();
    }
}
