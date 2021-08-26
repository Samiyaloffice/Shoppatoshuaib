package com.shoppa.Model;

public class BuyingRequestModel {

    String mRequestTitle, mCountryTag, mRequestDescription, mCompanyName, mDateOfApplied;

    public BuyingRequestModel(String mRequestTitle,
                              String mCountryTag,
                              String mRequestDescription,
                              String mCompanyName,
                              String mDateOfApplied) {

        this.mRequestTitle = mRequestTitle;
        this.mCountryTag = mCountryTag;
        this.mRequestDescription = mRequestDescription;
        this.mCompanyName = mCompanyName;
        this.mDateOfApplied = mDateOfApplied;
    }

    public String getmRequestTitle() {
        return mRequestTitle;
    }

    public void setmRequestTitle(String mRequestTitle) {
        this.mRequestTitle = mRequestTitle;
    }

    public String getmCountryTag() {
        return mCountryTag;
    }

    public void setmCountryTag(String mCountryTag) {
        this.mCountryTag = mCountryTag;
    }

    public String getmRequestDescription() {
        return mRequestDescription;
    }

    public void setmRequestDescription(String mRequestDescription) {
        this.mRequestDescription = mRequestDescription;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public String getmDateOfApplied() {
        return mDateOfApplied;
    }

    public void setmDateOfApplied(String mDateOfApplied) {
        this.mDateOfApplied = mDateOfApplied;
    }
}
