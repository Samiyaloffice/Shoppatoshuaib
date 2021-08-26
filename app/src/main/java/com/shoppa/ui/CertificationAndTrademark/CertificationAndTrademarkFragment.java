package com.shoppa.ui.CertificationAndTrademark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.shoppa.Adapters.CustomFragmentPagerAdapter;
import com.shoppa.R;
import com.shoppa.ui.AddCertification.AddCertificationFragment;
import com.shoppa.ui.Certification.CertificationFragment;
import com.shoppa.ui.Trademark.TrademarkFragment;

import java.util.ArrayList;

public class CertificationAndTrademarkFragment extends Fragment {

    ArrayList<Fragment> mFragmentArrayList;
    ArrayList<String> mFragmentTitleArrayList;
    TabLayout mCertificationTabLayout;
    ViewPager mCertificationViewPager;
    CustomFragmentPagerAdapter mCustomFragmentPagerAdapter;
    ShapeableImageView mAddCertificationAddBtn;
    private CertificationAndTrademarkViewModel mViewModel;

    public static CertificationAndTrademarkFragment newInstance() {
        return new CertificationAndTrademarkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(CertificationAndTrademarkViewModel.class);
        View root = inflater.inflate(R.layout.certification_and_trademark_fragment, container, false);

        mCertificationTabLayout = root.findViewById(R.id.certification_tab_layout);
        mCertificationViewPager = root.findViewById(R.id.certification_view_pager);
        mAddCertificationAddBtn = root.findViewById(R.id.certification_add_btn);
        mAddCertificationAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCertificationBtn();
            }
        });

        setUpViewPager();

        return root;
    }

    private void addCertificationBtn() {

        AddCertificationFragment fragment = new AddCertificationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("AddCertificationFragment");
        transaction.commit();

    }

    private void setUpViewPager() {

        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new CertificationFragment());
        mFragmentArrayList.add(new TrademarkFragment());

        mFragmentTitleArrayList = new ArrayList<>();
        mFragmentTitleArrayList.add("Certification");
        mFragmentTitleArrayList.add("Trademark");

        mCustomFragmentPagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), mFragmentArrayList, mFragmentTitleArrayList);
        mCertificationViewPager.setAdapter(mCustomFragmentPagerAdapter);
        mCertificationTabLayout.setupWithViewPager(mCertificationViewPager);


    }

}