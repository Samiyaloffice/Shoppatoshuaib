package com.shoppa.Model;

public class ProductDetailModel {

    String ProductId, SeoUrl, ProductName, ProductImage, ProductDescription, SeoTitle, ProductPostDateTime, SellerId;

    public ProductDetailModel(String productId,
                              String seoUrl,
                              String productName,
                              String productImage,
                              String productDescription,
                              String seoTitle,
                              String sellerId,
                              String productPostDateTime) {
        ProductId = productId;
        SeoUrl = seoUrl;
        ProductName = productName;
        ProductImage = productImage;
        ProductDescription = productDescription;
        SeoTitle = seoTitle;
        SellerId = sellerId;
        ProductPostDateTime = productPostDateTime;
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

    public String getSeoUrl() {
        return SeoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        SeoUrl = seoUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getSeoTitle() {
        return SeoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        SeoTitle = seoTitle;
    }

    public String getProductPostDateTime() {
        return ProductPostDateTime;
    }

    public void setProductPostDateTime(String productPostDateTime) {
        ProductPostDateTime = productPostDateTime;
    }

}
