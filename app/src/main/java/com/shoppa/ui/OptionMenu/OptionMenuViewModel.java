package com.shoppa.ui.OptionMenu;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.OptionMenuModel;

import java.util.ArrayList;

public class OptionMenuViewModel extends ViewModel implements View.OnClickListener {

    ArrayList<OptionMenuModel> mOptionMenuArrayList;
    MutableLiveData<ArrayList<OptionMenuModel>> mOptionElementsData;

    public OptionMenuViewModel() {
        mOptionMenuArrayList = new ArrayList<>();
        mOptionElementsData = new MutableLiveData<>();
        createOptionMenuData();
    }

    private void createOptionMenuData() {

        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/?id=QoQo3Iqhg9BE&size=2x&color=000000", "Buy Offers"));
        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/?id=3lZRI90tuuSg&size=2x&color=000000", "Products"));
        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/?id=y5jka4xFlBDe&size=2x&color=000000", "Company Profile"));
        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/?id=m62gV9AoQMAj&size=2x&color=000000", "Buying Requests"));
        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/material-outlined/50/000000/free-shipping.png", "Trade Alerts"));
//        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/?id=F9go0xWzWtU8&size=2x&color=000000","Contact Us"));
//        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/?id=Ddh01yU064XS&size=2x&color=000000", "Account Settings"));
        mOptionMenuArrayList.add(new OptionMenuModel("https://img.icons8.com/pastel-glyph/64/000000/security-shield.png", "Trouble Ticket"));

        mOptionElementsData.setValue(mOptionMenuArrayList);
    }

    public LiveData<ArrayList<OptionMenuModel>> getElementsData() {
        return mOptionElementsData;
    }

    @Override
    public void onClick(View v) {
    }

}