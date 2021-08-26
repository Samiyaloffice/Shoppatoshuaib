package com.shoppa.Model;

public class BuyerRequestModel {

    String Name, Email, Mobile, Country, State, City, Product, Quantity, Unit, Comments, BuyerSrc;

    public BuyerRequestModel(String name,
                             String email,
                             String mobile,
                             String country,
                             String state,
                             String city,
                             String product,
                             String quantity,
                             String unit,
                             String comments,
                             String buyerSrc) {
        Name = name;
        Email = email;
        Mobile = mobile;
        Country = country;
        State = state;
        City = city;
        Product = product;
        Quantity = quantity;
        Unit = unit;
        Comments = comments;
        BuyerSrc = buyerSrc;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getBuyerSrc() {
        return BuyerSrc;
    }

    public void setBuyerSrc(String buyerSrc) {
        BuyerSrc = buyerSrc;
    }
}
