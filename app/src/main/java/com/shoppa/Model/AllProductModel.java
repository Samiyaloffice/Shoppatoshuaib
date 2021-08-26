package com.shoppa.Model;

public class AllProductModel {

    String ProductId, ProductName, SeoUrl, ProductImage;

    public AllProductModel(String productId, String productName, String seoUrl, String productImage) {
        ProductId = productId;
        ProductName = productName;
        SeoUrl = seoUrl;
        ProductImage = productImage;
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
