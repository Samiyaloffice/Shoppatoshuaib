package com.shoppa.Model;

public class SellerDetailModel {

    String SellerId, CompanyName, SeoUrl, Type, SellerPostImg, SellerDescription, SellerContact, SellerName,SellerPlan;

    public SellerDetailModel(String sellerId,
                             String companyName,
                             String seoUrl,
                             String type,
                             String sellerPostImg,
                             String sellerDescription,
                             String sellerContact,
                             String sellerPlan,

                             String sellerName) {
        SellerId = sellerId;
        CompanyName = companyName;
        SeoUrl = seoUrl;
        Type = type;

        SellerPostImg = sellerPostImg;
        SellerDescription = sellerDescription;
        SellerContact = sellerContact;
        SellerName = sellerName;
        SellerPlan=sellerPlan;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getSeoUrl() {
        return SeoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        SeoUrl = seoUrl;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSellerPostImg() {
        return SellerPostImg;
    }

    public void setSellerPostImg(String sellerPostImg) {
        SellerPostImg = sellerPostImg;
    }

    public String getSellerDescription() {
        return SellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        SellerDescription = sellerDescription;
    }

    public String getSellerContact() {
        return SellerContact;
    }

    public void setSellerContact(String sellerContact) {
        SellerContact = sellerContact;
    }
    public  String getSellerPlan()
    {
        return  SellerPlan;
    }
    public  void SetSellerPlan(String sellerPlan)
    {
        SellerPlan=sellerPlan;
    }

}
