package com.shoppa.Model;

public class SupplierInfoProductModel {

    String mProductImg, mProductName;

    public SupplierInfoProductModel(String mProductImg, String mProductName) {
        this.mProductImg = mProductImg;
        this.mProductName = mProductName;
    }

    public String getmProductImg() {
        return mProductImg;
    }

    public void setmProductImg(String mProductImg) {
        this.mProductImg = mProductImg;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }
}

