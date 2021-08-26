package com.shoppa.Model;

public class LatestBuyLeadModel {

    String id, byr_name, byr_product, byr_quantity, byr_unit, enquiry_date;

    public LatestBuyLeadModel(String id,
                              String byr_name,
                              String byr_product,
                              String byr_quantity,
                              String byr_unit,
                              String enquiry_date) {
        this.id = id;
        this.byr_name = byr_name;
        this.byr_product = byr_product;
        this.byr_quantity = byr_quantity;
        this.byr_unit = byr_unit;
        this.enquiry_date = enquiry_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getByr_name() {
        return byr_name;
    }

    public void setByr_name(String byr_name) {
        this.byr_name = byr_name;
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

}
