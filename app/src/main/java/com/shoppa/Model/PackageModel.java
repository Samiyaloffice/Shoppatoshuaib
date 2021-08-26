package com.shoppa.Model;

public class PackageModel {

    String id, plan_name, plan_cost, discount, plan_date, Priority_Search_Listing, Display_Products, Display_Selling_Leads, Number_of_Inquiries_to_send_per_day, Access_to_New_Buying_Leads, Access_to_3_Million_Global_Buyer_Database, Free_Company_Verification_Service;

    public PackageModel(String id,
                        String plan_name,
                        String plan_cost,
                        String discount,
                        String plan_date,
                        String priority_Search_Listing,
                        String display_Products,
                        String display_Selling_Leads,
                        String number_of_Inquiries_to_send_per_day,
                        String access_to_New_Buying_Leads,
                        String access_to_3_Million_Global_Buyer_Database,
                        String free_Company_Verification_Service) {
        this.id = id;
        this.plan_name = plan_name;
        this.plan_cost = plan_cost;
        this.discount = discount;
        this.plan_date = plan_date;
        Priority_Search_Listing = priority_Search_Listing;
        Display_Products = display_Products;
        Display_Selling_Leads = display_Selling_Leads;
        Number_of_Inquiries_to_send_per_day = number_of_Inquiries_to_send_per_day;
        Access_to_New_Buying_Leads = access_to_New_Buying_Leads;
        Access_to_3_Million_Global_Buyer_Database = access_to_3_Million_Global_Buyer_Database;
        Free_Company_Verification_Service = free_Company_Verification_Service;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_cost() {
        return plan_cost;
    }

    public void setPlan_cost(String plan_cost) {
        this.plan_cost = plan_cost;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getPriority_Search_Listing() {
        return Priority_Search_Listing;
    }

    public void setPriority_Search_Listing(String priority_Search_Listing) {
        Priority_Search_Listing = priority_Search_Listing;
    }

    public String getDisplay_Products() {
        return Display_Products;
    }

    public void setDisplay_Products(String display_Products) {
        Display_Products = display_Products;
    }

    public String getDisplay_Selling_Leads() {
        return Display_Selling_Leads;
    }

    public void setDisplay_Selling_Leads(String display_Selling_Leads) {
        Display_Selling_Leads = display_Selling_Leads;
    }

    public String getNumber_of_Inquiries_to_send_per_day() {
        return Number_of_Inquiries_to_send_per_day;
    }

    public void setNumber_of_Inquiries_to_send_per_day(String number_of_Inquiries_to_send_per_day) {
        Number_of_Inquiries_to_send_per_day = number_of_Inquiries_to_send_per_day;
    }

    public String getAccess_to_New_Buying_Leads() {
        return Access_to_New_Buying_Leads;
    }

    public void setAccess_to_New_Buying_Leads(String access_to_New_Buying_Leads) {
        Access_to_New_Buying_Leads = access_to_New_Buying_Leads;
    }

    public String getAccess_to_3_Million_Global_Buyer_Database() {
        return Access_to_3_Million_Global_Buyer_Database;
    }

    public void setAccess_to_3_Million_Global_Buyer_Database(String access_to_3_Million_Global_Buyer_Database) {
        Access_to_3_Million_Global_Buyer_Database = access_to_3_Million_Global_Buyer_Database;
    }

    public String getFree_Company_Verification_Service() {
        return Free_Company_Verification_Service;
    }

    public void setFree_Company_Verification_Service(String free_Company_Verification_Service) {
        Free_Company_Verification_Service = free_Company_Verification_Service;
    }
}
