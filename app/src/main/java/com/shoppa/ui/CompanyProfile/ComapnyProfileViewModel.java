package com.shoppa.ui.CompanyProfile;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ComapnyProfileViewModel extends ViewModel {

    public ArrayList<String> mCompanyProfileArrayList;

    public ComapnyProfileViewModel() {

        setCompanyProfileData();

    }

    private void setCompanyProfileData() {
        mCompanyProfileArrayList = new ArrayList<>();

        mCompanyProfileArrayList.add("Basic Company Details");
//        mCompanyProfileArrayList.add("Factory Details");
//        mCompanyProfileArrayList.add("Trade Details");
        mCompanyProfileArrayList.add("Company Introduction");
        mCompanyProfileArrayList.add("Social Profiles");
//        mCompanyProfileArrayList.add("Certification And Trademark");

    }


}