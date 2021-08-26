package com.shoppa.ui.TradeInformationPostSeller;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;

import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoFobPriceBtn;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoFobPriceLayout;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoFobPriceTxt;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoOEMBtn;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoOverseasNoBtn;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoOverseasYesBtn;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoPerTxt;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoQuantityPriceBtn;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoQuantityPriceLayout;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoQuantityUnitTypeTxt;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoSelectTimeTxt;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoStockBtn;
import static com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment.mTradeInfoUnitTxt;

public class TradeInfoPostSellerViewModel extends ViewModel implements View.OnClickListener {


    ArrayList<String> mCommonArrayList;
    private Context context;

    public TradeInfoPostSellerViewModel() {
        setBtn();
        setFOBPriceData();
    }


    private void setFOBPriceData() {

        mCommonArrayList = new ArrayList<>();
        mCommonArrayList.add("Item 1");
        mCommonArrayList.add("Item 2");
        mCommonArrayList.add("Item 3");
        mCommonArrayList.add("Item 4");
        mCommonArrayList.add("Item 5");

    }

    private void setBtn() {

        onClick(mTradeInfoFobPriceTxt);
        onClick(mTradeInfoPerTxt);
        onClick(mTradeInfoUnitTxt);
        onClick(mTradeInfoSelectTimeTxt);
        onClick(mTradeInfoQuantityUnitTypeTxt);

        mTradeInfoFobPriceBtn.setOnClickListener(this);
        mTradeInfoQuantityPriceBtn.setOnClickListener(this);
        mTradeInfoOEMBtn.setOnClickListener(this);
        mTradeInfoStockBtn.setOnClickListener(this);
        mTradeInfoOverseasYesBtn.setOnClickListener(this);
        mTradeInfoOverseasNoBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mTradeInfoFobPriceBtn) {
            mTradeInfoQuantityPriceBtn.setChecked(false);
            mTradeInfoFobPriceLayout.setVisibility(View.VISIBLE);
            mTradeInfoQuantityPriceLayout.setVisibility(View.GONE);
        }
        if (v == mTradeInfoQuantityPriceBtn) {

            mTradeInfoFobPriceBtn.setChecked(false);
            mTradeInfoFobPriceLayout.setVisibility(View.GONE);
            mTradeInfoQuantityPriceLayout.setVisibility(View.VISIBLE);
        }
        if (v == mTradeInfoOEMBtn) {
            mTradeInfoStockBtn.setChecked(false);
        }
        if (v == mTradeInfoStockBtn) {
            mTradeInfoOEMBtn.setChecked(false);
        }
        if (v == mTradeInfoOverseasYesBtn) {

            mTradeInfoOverseasNoBtn.setChecked(false);
        }
        if (v == mTradeInfoOverseasNoBtn) {
            mTradeInfoOverseasYesBtn.setChecked(false);
        }

    }

    public void setContext(Context context) {
        this.context = context;
        setAdapters();
    }

    private void setAdapters() {
        /*mDropDownAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, mCommonArrayList);
        mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTradeInfoFobPriceTxt.setAdapter(mDropDownAdapter);

        mDropDownAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, mCommonArrayList);
        mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTradeInfoPerTxt.setAdapter(mDropDownAdapter);

        mDropDownAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, mCommonArrayList);
        mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTradeInfoUnitTxt.setAdapter(mDropDownAdapter);

        mDropDownAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, mCommonArrayList);
        mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTradeInfoSelectTimeTxt.setAdapter(mDropDownAdapter);

        mDropDownAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, mCommonArrayList);
        mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTradeInfoQuantityUnitTypeTxt.setAdapter(mDropDownAdapter);*/
    }

    public void onClick(MaterialAutoCompleteTextView mV) {
        mV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mV.isPopupShowing()) {
                    mV.dismissDropDown();
                } else {
                    mV.showDropDown();
                }
            }
        });
    }
}