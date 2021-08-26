package com.shoppa.Model;

public class UserBuyRequestModel {

    String byr_name,
            byr_email,
            byr_address,
            byr_product,
            byr_quantity,
            seller_name,
            byr_unit,
            enquiry_date,
            byr_status,
            byr_src;

    public UserBuyRequestModel(String byr_name,
                               String byr_email,
                               String byr_address,
                               String byr_product,
                               String byr_quantity,
                               String seller_name,
                               String byr_unit,
                               String enquiry_date,
                               String byr_status,
                               String byr_src) {
        this.byr_name = byr_name;
        this.byr_email = byr_email;
        this.byr_address = byr_address;
        this.byr_product = byr_product;
        this.byr_quantity = byr_quantity;
        this.seller_name = seller_name;
        this.byr_unit = byr_unit;
        this.enquiry_date = enquiry_date;
        this.byr_status = byr_status;
        this.byr_src = byr_src;
    }

    public String getByr_name() {
        return byr_name;
    }

    public void setByr_name(String byr_name) {
        this.byr_name = byr_name;
    }

    public String getByr_email() {
        return byr_email;
    }

    public void setByr_email(String byr_email) {
        this.byr_email = byr_email;
    }

    public String getByr_address() {
        return byr_address;
    }

    public void setByr_address(String byr_address) {
        this.byr_address = byr_address;
    }

    public String getByr_product() {
        return byr_product;
    }

    public void setByr_product(String byr_product) {
        this.byr_product = byr_product;
    }

    public String getByr_quantity() {
        return byr_quantity;
    }

    public void setByr_quantity(String byr_quantity) {
        this.byr_quantity = byr_quantity;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getByr_unit() {
        return byr_unit;
    }

    public void setByr_unit(String byr_unit) {
        this.byr_unit = byr_unit;
    }

    public String getEnquiry_date() {
        return enquiry_date;
    }

    public void setEnquiry_date(String enquiry_date) {
        this.enquiry_date = enquiry_date;
    }

    public String getByr_status() {
        return byr_status;
    }

    public void setByr_status(String byr_status) {
        this.byr_status = byr_status;
    }

    public String getByr_src() {
        return byr_src;
    }

    public void setByr_src(String byr_src) {
        this.byr_src = byr_src;
    }
}
