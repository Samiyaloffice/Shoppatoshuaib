package com.shoppa.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class CustomFragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    ArrayList<Fragment> mFragmentArrayList;
    ArrayList<String> mFragmentTitleArrayList;

    public CustomFragmentPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> mFragmentsArrayList, ArrayList<String> mFragmentTitleArrayList) {
        super(fm);

        this.mFragmentArrayList = mFragmentsArrayList;
        this.mFragmentTitleArrayList = mFragmentTitleArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleArrayList.get(position);
    }

}
