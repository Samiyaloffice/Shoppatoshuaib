package com.shoppa.Model;

public class TradeAlertModel {

    String Id, DiscountName, PackageName, DiscountDescription, DiscountPercent, PostDate;

    public TradeAlertModel(String id,
                           String discountName,
                           String packageName,
                           String discountDescription,
                           String discountPercent,
                           String postDate) {
        Id = id;
        DiscountName = discountName;
        PackageName = packageName;
        DiscountDescription = discountDescription;
        DiscountPercent = discountPercent;
        PostDate = postDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDiscountName() {
        return DiscountName;
    }

    public void setDiscountName(String discountName) {
        DiscountName = discountName;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getDiscountDescription() {
        return DiscountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        DiscountDescription = discountDescription;
    }

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
    }

    public String getPostDate() {
        return PostDate;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }
}
