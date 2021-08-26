package com.shoppa.Model;

public class PremiumSellerModel {

    String id, sellerName, sellerEmail, sellerImage, sellerNumber, sellerAddress;

    public PremiumSellerModel(String id,
                              String sellerName,
                              String sellerEmail,
                              String sellerImage,
                              String sellerNumber,
                              String sellerAddress) {
        this.id = id;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.sellerImage = sellerImage;
        this.sellerNumber = sellerNumber;
        this.sellerAddress = sellerAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerImage() {
        return sellerImage;
    }

    public void setSellerImage(String sellerImage) {
        this.sellerImage = sellerImage;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }
}
