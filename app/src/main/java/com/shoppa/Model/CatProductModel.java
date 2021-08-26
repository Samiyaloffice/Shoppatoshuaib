package com.shoppa.Model;

import java.util.ArrayList;

public class CatProductModel {

    String ProductId, ProductName, SeoUrl, ProductImage, SellerId, TotalSeller;
    ArrayList<String> mListedSellerArrayList;

    public CatProductModel(String productId, String productName, String seoUrl, String productImage, String sellerId, String totalSeller, ArrayList<String> listedSellerArrayList) {
        ProductId = productId;
        ProductName = productName;
        SeoUrl = seoUrl;
        ProductImage = productImage;
        SellerId = sellerId;
        TotalSeller = totalSeller;
        mListedSellerArrayList = listedSellerArrayList;
    }

    public ArrayList<String> getmListedSellerArrayList() {
        return mListedSellerArrayList;
    }

    public void setmListedSellerArrayList(ArrayList<String> mListedSellerArrayList) {
        this.mListedSellerArrayList = mListedSellerArrayList;
    }

    public String getTotalSeller() {
        return TotalSeller;
    }

    public void setTotalSeller(String totalSeller) {
        TotalSeller = totalSeller;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSeoUrl() {
        return SeoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        SeoUrl = seoUrl;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }
}
