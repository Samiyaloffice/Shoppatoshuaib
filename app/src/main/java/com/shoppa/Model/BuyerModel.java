package com.shoppa.Model;

public class BuyerModel {

    String Id, BuyerName, BuyerProduct, BuyersEmail, BuyerMobile, BuyersAddress;

    public BuyerModel(String id, String buyerName, String buyerProduct, String buyersEmail, String buyerMobile, String buyersAddress) {
        Id = id;
        BuyerName = buyerName;
        BuyerProduct = buyerProduct;
        BuyersEmail = buyersEmail;
        BuyerMobile = buyerMobile;
        BuyersAddress = buyersAddress;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getBuyerProduct() {
        return BuyerProduct;
    }

    public void setBuyerProduct(String buyerProduct) {
        BuyerProduct = buyerProduct;
    }

    public String getBuyersEmail() {
        return BuyersEmail;
    }

    public void setBuyersEmail(String buyersEmail) {
        BuyersEmail = buyersEmail;
    }

    public String getBuyerMobile() {
        return BuyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        BuyerMobile = buyerMobile;
    }

    public String getBuyersAddress() {
        return BuyersAddress;
    }

    public void setBuyersAddress(String buyersAddress) {
        BuyersAddress = buyersAddress;
    }
}
