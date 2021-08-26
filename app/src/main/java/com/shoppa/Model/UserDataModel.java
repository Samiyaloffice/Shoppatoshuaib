package com.shoppa.Model;

public class UserDataModel {

    public static UserDataModel mInstance;
    String id,
            sl_number,
            seller_password,
            seller_name,
            seller_image,
            seller_email,
            seller_usertype,
            seller_userstatus,
            seller_company,
            seller_address,
            seller_website,
            seller_post_id,
            seller_whatsapp,
            seller_skype,
            seller_type,
            seller_plan;

    public UserDataModel() {
    }

    public UserDataModel(String id,
                         String sl_number,
                         String seller_password,
                         String seller_name,
                         String seller_image,
                         String seller_email,
                         String seller_usertype,
                         String seller_userstatus,
                         String seller_company,
                         String seller_address,
                         String seller_website,
                         String seller_post_id,
                         String seller_whatsapp,
                         String seller_skype,
                         String seller_type,
                         String seller_plan) {
        this.id = id;
        this.sl_number = sl_number;
        this.seller_password = seller_password;
        this.seller_name = seller_name;
        this.seller_image = seller_image;
        this.seller_email = seller_email;
        this.seller_usertype = seller_usertype;
        this.seller_userstatus = seller_userstatus;
        this.seller_company = seller_company;
        this.seller_address = seller_address;
        this.seller_website = seller_website;
        this.seller_post_id = seller_post_id;
        this.seller_whatsapp = seller_whatsapp;
        this.seller_skype = seller_skype;
        this.seller_type = seller_type;
        this.seller_plan = seller_plan;
    }

    public static UserDataModel getmInstance() {
        if (mInstance == null) {
            mInstance = new UserDataModel();
        }
        return mInstance;
    }

    public String getSeller_whatsapp() {
        return seller_whatsapp;
    }

    public void setSeller_whatsapp(String seller_whatsapp) {
        this.seller_whatsapp = seller_whatsapp;
    }

    public String getSeller_skype() {
        return seller_skype;
    }

    public void setSeller_skype(String seller_skype) {
        this.seller_skype = seller_skype;
    }

    public String getSeller_password() {
        return seller_password;
    }

    public void setSeller_password(String seller_password) {
        this.seller_password = seller_password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSl_number() {
        return sl_number;
    }

    public void setSl_number(String sl_number) {
        this.sl_number = sl_number;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSeller_image() {
        return seller_image;
    }

    public void setSeller_image(String seller_image) {
        this.seller_image = seller_image;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getSeller_usertype() {
        return seller_usertype;
    }

    public void setSeller_usertype(String seller_usertype) {
        this.seller_usertype = seller_usertype;
    }

    public String getSeller_userstatus() {
        return seller_userstatus;
    }

    public void setSeller_userstatus(String seller_userstatus) {
        this.seller_userstatus = seller_userstatus;
    }

    public String getSeller_company() {
        return seller_company;
    }

    public void setSeller_company(String seller_company) {
        this.seller_company = seller_company;
    }

    public String getSeller_address() {
        return seller_address;
    }

    public void setSeller_address(String seller_address) {
        this.seller_address = seller_address;
    }

    public String getSeller_website() {
        return seller_website;
    }

    public void setSeller_website(String seller_website) {
        this.seller_website = seller_website;
    }

    public String getSeller_post_id() {
        return seller_post_id;
    }

    public void setSeller_post_id(String seller_post_id) {
        this.seller_post_id = seller_post_id;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }

    public String getSeller_plan() {
        return seller_plan;
    }

    public void setSeller_plan(String seller_plan) {
        this.seller_plan = seller_plan;
    }
}
