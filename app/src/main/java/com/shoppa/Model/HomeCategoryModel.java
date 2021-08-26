package com.shoppa.Model;

public class HomeCategoryModel {

    String categoryId, categoryName, categoryImg, seoUrl, totalSubCat, totalProducts;

    public HomeCategoryModel(String categoryId, String categoryName, String categoryImg, String seoUrl, String totalSubCat, String totalProducts) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
        this.seoUrl = seoUrl;
        this.totalSubCat = totalSubCat;
        this.totalProducts = totalProducts;
    }

    public String getTotalSubCat() {
        return totalSubCat;
    }

    public void setTotalSubCat(String totalSubCat) {
        this.totalSubCat = totalSubCat;
    }

    public String getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(String totalProducts) {
        this.totalProducts = totalProducts;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getSeoUrl() {
        return seoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        this.seoUrl = seoUrl;
    }

}
