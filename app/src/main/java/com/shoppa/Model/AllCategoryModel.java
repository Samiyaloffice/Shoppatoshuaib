package com.shoppa.Model;

public class AllCategoryModel {
    String categoryImg, categoryTxt;

    public AllCategoryModel(String categoryImg, String categoryTxt) {
        this.categoryImg = categoryImg;
        this.categoryTxt = categoryTxt;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryTxt() {
        return categoryTxt;
    }

    public void setCategoryTxt(String categoryTxt) {
        this.categoryTxt = categoryTxt;
    }
}
