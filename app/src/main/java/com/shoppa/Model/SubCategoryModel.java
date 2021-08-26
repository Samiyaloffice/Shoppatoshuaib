package com.shoppa.Model;

public class SubCategoryModel {

    String SubCatId, SubCatName, SeoUrl, SubCatImage, ProductCount;

    public SubCategoryModel(String subCatId, String subCatName, String seoUrl, String subCatImage, String productCount) {
        SubCatId = subCatId;
        SubCatName = subCatName;
        SeoUrl = seoUrl;
        SubCatImage = subCatImage;
        ProductCount = productCount;
    }

    public String getProductCount() {
        return ProductCount;
    }

    public void setProductCount(String productCount) {
        ProductCount = productCount;
    }

    public String getSubCatId() {
        return SubCatId;
    }

    public void setSubCatId(String subCatId) {
        SubCatId = subCatId;
    }

    public String getSubCatName() {
        return SubCatName;
    }

    public void setSubCatName(String subCatName) {
        SubCatName = subCatName;
    }

    public String getSeoUrl() {
        return SeoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        SeoUrl = seoUrl;
    }

    public String getSubCatImage() {
        return SubCatImage;
    }

    public void setSubCatImage(String subCatImage) {
        SubCatImage = subCatImage;
    }
}
