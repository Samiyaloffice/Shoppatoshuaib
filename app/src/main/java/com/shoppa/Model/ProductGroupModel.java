package com.shoppa.Model;

public class ProductGroupModel {

    String mGroupName, mGroupProductCount;

    public ProductGroupModel(String mGroupName, String mGroupProductCount) {
        this.mGroupName = mGroupName;
        this.mGroupProductCount = mGroupProductCount;
    }

    public String getmGroupName() {
        return mGroupName;
    }

    public void setmGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public String getmGroupProductCount() {
        return mGroupProductCount;
    }

    public void setmGroupProductCount(String mGroupProductCount) {
        this.mGroupProductCount = mGroupProductCount;
    }
}
