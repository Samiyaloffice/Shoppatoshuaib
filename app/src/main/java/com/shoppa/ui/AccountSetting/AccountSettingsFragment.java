package com.shoppa.ui.AccountSetting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shoppa.Adapters.CustomFragmentPagerAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;
import com.shoppa.ui.EmailPreference.EmailPreferenceFragment;
import com.shoppa.ui.SubDomain.SubDomainFragment;

import java.util.ArrayList;

public class AccountSettingsFragment extends Fragment {

    CustomFragmentPagerAdapter mProductFragmentPagerAdapter;
    ArrayList<Fragment> mFragmentArrayList;
    ArrayList<String> mFragmentTitleList;
    TabLayout mAccountSettingsTabLayout;
    ViewPager mAccountSettingsViewPager;
    private AccountSettingsViewModel mViewModel;

    public static AccountSettingsFragment newInstance() {
        return new AccountSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DataManager.isFrom = "optionFragment";
        mViewModel = new ViewModelProvider(this).get(AccountSettingsViewModel.class);
        View root = inflater.inflate(R.layout.account_settings_fragment, container, false);

        mAccountSettingsViewPager = root.findViewById(R.id.account_settings_view_pager);
        mAccountSettingsTabLayout = root.findViewById(R.id.account_settings_tab_layout);

        setUpTabAdapter();

        return root;
    }

    private void setUpTabAdapter() {
        mFragmentArrayList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();

        mFragmentArrayList.add(new SubDomainFragment());
        mFragmentArrayList.add(new EmailPreferenceFragment());
        mFragmentTitleList.add("SubDomain");
        mFragmentTitleList.add("Email Preference");

        mProductFragmentPagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), mFragmentArrayList, mFragmentTitleList);
        mAccountSettingsViewPager.setAdapter(mProductFragmentPagerAdapter);
        mAccountSettingsTabLayout.setupWithViewPager(mAccountSettingsViewPager);

    }

}