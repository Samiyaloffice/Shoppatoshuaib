package com.shoppa.Model;

public class BuyerEnquiryModel {

    String name,
            email,
            address,
            mobile,
            city,
            state,
            country,
            product,
            quantity,
            unit,
            comments,
            seller_name,
            Buyer_src;


    public BuyerEnquiryModel(String name,
                             String email,
                             String address,
                             String mobile,
                             String city,
                             String state,
                             String country,
                             String product,
                             String quantity,
                             String unit,
                             String comments,
                             String seller_name,
                             String buyer_src) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.city = city;
        this.state = state;
        this.country = country;
        this.product = product;
        this.quantity = quantity;
        this.unit = unit;
        this.comments = comments;
        this.seller_name = seller_name;
        Buyer_src = buyer_src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getBuyer_src() {
        return Buyer_src;
    }

    public void setBuyer_src(String buyer_src) {
        Buyer_src = buyer_src;
    }


}


