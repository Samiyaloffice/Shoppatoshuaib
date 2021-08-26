package com.shoppa.ui.CompanyProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppa.Adapters.CompanyProfileAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;
import com.shoppa.ui.BasicComapnyDetails.BasicCompanyDetailsFragment;
import com.shoppa.ui.CertificationAndTrademark.CertificationAndTrademarkFragment;
import com.shoppa.ui.CompanyIntroduction.CompanyIntroductionFragment;
import com.shoppa.ui.FactoryDetails.FactoryDetailsFragment;
import com.shoppa.ui.SocialProfile.SocialProfileFragment;
import com.shoppa.ui.TradeDetails.TradeDetailsFragment;

public class ComapnyProfileFragment extends Fragment {

    RecyclerView mCompanyProfileRecyclerView;
    CompanyProfileAdapter mCompanyProfileAdapter;
    private ComapnyProfileViewModel mViewModel;

    public static ComapnyProfileFragment newInstance() {
        return new ComapnyProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ComapnyProfileViewModel.class);
        View root = inflater.inflate(R.layout.comapny_profile_fragment, container, false);
        mCompanyProfileRecyclerView = root.findViewById(R.id.company_profile_recycler_view);

        DataManager.isFrom = "optionFragment";

        getCompanyProfileData();

        return root;
    }

    private void getCompanyProfileData() {

        mCompanyProfileRecyclerView.setHasFixedSize(true);
        mCompanyProfileRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCompanyProfileAdapter = new CompanyProfileAdapter(getContext(), mViewModel.mCompanyProfileArrayList, new CompanyProfileAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                if (position == 0) {
                    basicCompanyDetailsFragment();
                } else if (position == 1) {
//                    factoryDetailsFragment();
                    companyIntroductionFragment();
                } else if (position == 2) {
//                    tradeDetailsFragment();
                    socialProfileFragment();
                } else if (position == 3) {
//                    companyIntroductionFragment();
                } else if (position == 4) {
//                    socialProfileFragment();
                } else if (position == 5) {
//                    certificationAndTrademarkFragment();
                }
            }

            @Override
            public void OnViewClick() {

            }
        });
        mCompanyProfileRecyclerView.setAdapter(mCompanyProfileAdapter);
    }

    private void certificationAndTrademarkFragment() {

        CertificationAndTrademarkFragment fragment = new CertificationAndTrademarkFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("CertificationAndTrademarkFragmentAdded");
        transaction.commit();

    }

    private void socialProfileFragment() {

        SocialProfileFragment fragment = new SocialProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("SocialProfileFragmentAdded");
        transaction.commit();

    }

    private void companyIntroductionFragment() {

        CompanyIntroductionFragment fragment = new CompanyIntroductionFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("CompanyIntroductionFragmentAdded");
        transaction.commit();

    }

    private void tradeDetailsFragment() {

        TradeDetailsFragment fragment = new TradeDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("TradeDetailsFragment");
        transaction.commit();

    }

    private void factoryDetailsFragment() {

        FactoryDetailsFragment fragment = new FactoryDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("FactoryDetailsFragmentAdded");
        transaction.commit();

    }

    private void basicCompanyDetailsFragment() {

        BasicCompanyDetailsFragment fragment = new BasicCompanyDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("BasicCompanyDetailsFragmentAdded");
        transaction.commit();

    }

}