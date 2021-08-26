package com.shoppa.ui.TradeDetails;

import android.view.View;

import androidx.lifecycle.ViewModel;

import static com.shoppa.ui.TradeDetails.TradeDetailsFragment.mCompanyOverseasNo;
import static com.shoppa.ui.TradeDetails.TradeDetailsFragment.mCompanyOverseasYes;

public class TradeDetailsViewModel extends ViewModel implements View.OnClickListener {

    public TradeDetailsViewModel() {
        setRadioBtn();
    }

    private void setRadioBtn() {
        mCompanyOverseasYes.setOnClickListener(this);
        mCompanyOverseasNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mCompanyOverseasYes) {
            mCompanyOverseasNo.setChecked(false);
        } else if (v == mCompanyOverseasNo) {
            mCompanyOverseasYes.setChecked(true);
        }
    }
}