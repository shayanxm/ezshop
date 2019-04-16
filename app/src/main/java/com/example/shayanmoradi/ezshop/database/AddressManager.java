package com.example.shayanmoradi.ezshop.database;

import android.content.Context;

import com.example.shayanmoradi.ezshop.slider.App;

import java.util.List;

public class AddressManager {
    public static AddressManager instance;
    private DaoSession daoSession = App.getApp().getDaoSession();
    private AddressDao detailsDao = daoSession.getAddressDao();





    Context mContext;

    public AddressManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static AddressManager getInstance(Context context) {
        if (instance == null)
            instance = new AddressManager(context);
        return instance;
    }


    public Address getAddress(String address1) {
        List<Address> addressList = detailsDao.queryBuilder()
                .where(AddressDao.Properties.Address1.eq(address1))

                .list();
//        for (Address address : addressList) {
//            return address;
//        }
        return addressList.get(0);
    }

    public List<Address> getAllList() {
        return detailsDao.loadAll();

    }

    public void addAddress(Address address) {
        detailsDao.insert(address);
    }

    public void update(Address address) {
        detailsDao.update(address);
    }

    public void delete(Address address) {
        detailsDao.delete(address);
    }
}
