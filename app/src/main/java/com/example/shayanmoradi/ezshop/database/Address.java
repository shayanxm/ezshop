package com.example.shayanmoradi.ezshop.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Address {
    @Id(autoincrement = true)
    private Long id;
    private int lat;
    private int longtiude;
    private String Address1;
    private String Address2;
    @Generated(hash = 913072552)
    public Address(Long id, int lat, int longtiude, String Address1,
            String Address2) {
        this.id = id;
        this.lat = lat;
        this.longtiude = longtiude;
        this.Address1 = Address1;
        this.Address2 = Address2;
    }
    @Generated(hash = 388317431)
    public Address() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLat() {
        return this.lat;
    }
    public void setLat(int lat) {
        this.lat = lat;
    }
    public int getLongtiude() {
        return this.longtiude;
    }
    public void setLongtiude(int longtiude) {
        this.longtiude = longtiude;
    }
    public String getAddress1() {
        return this.Address1;
    }
    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }
    public String getAddress2() {
        return this.Address2;
    }
    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }
}
