package com.shoppa.Model;

public class InnerBuyOfferModel {

    String mBuyOfferTitle, mBuyOfferCountryImg, mBuyOfferDate, mBuyOfferDescription, mBuyOfferCompanyName;

    public InnerBuyOfferModel(String mBuyOfferTitle,
                              String mBuyOfferCountryImg,
                              String mBuyOfferDate,
                              String mBuyOfferDescription,
                              String mBuyOfferCompanyName) {
        this.mBuyOfferTitle = mBuyOfferTitle;
        this.mBuyOfferCountryImg = mBuyOfferCountryImg;
        this.mBuyOfferDate = mBuyOfferDate;
        this.mBuyOfferDescription = mBuyOfferDescription;
        this.mBuyOfferCompanyName = mBuyOfferCompanyName;
    }

    public String getmBuyOfferTitle() {
        return mBuyOfferTitle;
    }

    public void setmBuyOfferTitle(String mBuyOfferTitle) {
        this.mBuyOfferTitle = mBuyOfferTitle;
    }

    public String getmBuyOfferCountryImg() {
        return mBuyOfferCountryImg;
    }

    public void setmBuyOfferCountryImg(String mBuyOfferCountryImg) {
        this.mBuyOfferCountryImg = mBuyOfferCountryImg;
    }

    public String getmBuyOfferDate() {
        return mBuyOfferDate;
    }

    public void setmBuyOfferDate(String mBuyOfferDate) {
        this.mBuyOfferDate = mBuyOfferDate;
    }

    public String getmBuyOfferDescription() {
        return mBuyOfferDescription;
    }

    public void setmBuyOfferDescription(String mBuyOfferDescription) {
        this.mBuyOfferDescription = mBuyOfferDescription;
    }

    public String getmBuyOfferCompanyName() {
        return mBuyOfferCompanyName;
    }

    public void setmBuyOfferCompanyName(String mBuyOfferCompanyName) {
        this.mBuyOfferCompanyName = mBuyOfferCompanyName;
    }
}
