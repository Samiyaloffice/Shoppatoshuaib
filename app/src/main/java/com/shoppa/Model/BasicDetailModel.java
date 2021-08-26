package com.shoppa.Model;

public class BasicDetailModel {

    String sl_number, seller_address, seller_type, seller_email, seller_name, seller_company;

    public BasicDetailModel(String sl_number,
                            String seller_address,
                            String seller_type,
                            String seller_email,
                            String seller_name,
                            String seller_company) {
        this.sl_number = sl_number;
        this.seller_address = seller_address;
        this.seller_type = seller_type;
        this.seller_email = seller_email;
        this.seller_name = seller_name;
        this.seller_company = seller_company;
    }

    public String getSl_number() {
        return sl_number;
    }

    public void setSl_number(String sl_number) {
        this.sl_number = sl_number;
    }

    public String getSeller_address() {
        return seller_address;
    }

    public void setSeller_address(String seller_address) {
        this.seller_address = seller_address;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSeller_company() {
        return seller_company;
    }

    public void setSeller_company(String seller_company) {
        this.seller_company = seller_company;
    }
}
