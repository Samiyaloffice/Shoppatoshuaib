package com.shoppa.Model;

import java.util.ArrayList;

public class BuyOfferModel {

    String mBuyOfferTitle;
    ArrayList<InnerBuyOfferModel> mInnerBuyOfferModelsArrayList;

    public BuyOfferModel(String mBuyOfferTitle, ArrayList<InnerBuyOfferModel> mInnerBuyOfferModelsArrayList) {
        this.mBuyOfferTitle = mBuyOfferTitle;
        this.mInnerBuyOfferModelsArrayList = mInnerBuyOfferModelsArrayList;
    }

    public String getmBuyOfferTitle() {
        return mBuyOfferTitle;
    }

    public void setmBuyOfferTitle(String mBuyOfferTitle) {
        this.mBuyOfferTitle = mBuyOfferTitle;
    }

    public ArrayList<InnerBuyOfferModel> getmInnerBuyOfferModelsArrayList() {
        return mInnerBuyOfferModelsArrayList;
    }

    public void setmInnerBuyOfferModelsArrayList(ArrayList<InnerBuyOfferModel> mInnerBuyOfferModelsArrayList) {
        this.mInnerBuyOfferModelsArrayList = mInnerBuyOfferModelsArrayList;
    }
}
