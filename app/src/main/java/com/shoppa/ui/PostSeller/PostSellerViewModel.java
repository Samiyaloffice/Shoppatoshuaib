package com.shoppa.ui.PostSeller;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;

import static com.shoppa.ui.PostSeller.PostSellerFragment.mPostSellerOriginTxt;
import static com.shoppa.ui.PostSeller.PostSellerFragment.mPostSellerProductGroupTxt;

public class PostSellerViewModel extends ViewModel {

    Context context;
    ArrayList<String> mProductGroupArrayList;
    ArrayList<String> mOriginArrayList;


    public PostSellerViewModel() {
        setBtns();
        setProductGroupData();
        setOriginData();
    }

    private void setOriginData() {

        mOriginArrayList = new ArrayList<>();
        mOriginArrayList.add("Origin 1");
        mOriginArrayList.add("Origin 2");
        mOriginArrayList.add("Origin 3");
        mOriginArrayList.add("Origin 4");
        mOriginArrayList.add("Origin 5");

    }

    private void setProductGroupData() {

        mProductGroupArrayList = new ArrayList<>();
        mProductGroupArrayList.add("Item 1");
        mProductGroupArrayList.add("Item 2");
        mProductGroupArrayList.add("Item 3");
        mProductGroupArrayList.add("Item 4");
        mProductGroupArrayList.add("Item 5");
        mProductGroupArrayList.add("Item 6");
        mProductGroupArrayList.add("Item 7");
        mProductGroupArrayList.add("Item 8");
        mProductGroupArrayList.add("Item 9");
        mProductGroupArrayList.add("Item 10");

    }

    public void setContext(Context context) {
        this.context = context;
        setProductGroupDropDownView();
        setOriginDropDownView();
    }

    private void setOriginDropDownView() {
        /*mDropDownAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, mOriginArrayList);
        mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPostSellerOriginTxt.setAdapter(mDropDownAdapter);*/
    }

    private void setBtns() {

        onClick(mPostSellerProductGroupTxt);
        onClick(mPostSellerOriginTxt);

        mPostSellerProductGroupTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPostSellerProductGroupTxt.setText(mProductGroupArrayList.get(position));
            }
        });
        mPostSellerOriginTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPostSellerOriginTxt.setText(mOriginArrayList.get(position));
            }
        });


    }

    private void setProductGroupDropDownView() {
        /*mDropDownAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, mProductGroupArrayList);
        mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPostSellerProductGroupTxt.setAdapter(mDropDownAdapter);*/
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