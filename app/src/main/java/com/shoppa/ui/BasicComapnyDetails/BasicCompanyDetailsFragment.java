package com.shoppa.ui.BasicComapnyDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;

public class BasicCompanyDetailsFragment extends Fragment {

    TextInputLayout mCompanyDetailsType, mCompanyDetailsName, mCompanyDetailsLocation, mCompanyDetailsRegisNumber, mCompanyDetailsNumber, mCompanyDetailsWebsite;
    private BasicCompanyDetailsViewModel mViewModel;

    public static BasicCompanyDetailsFragment newInstance() {
        return new BasicCompanyDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(BasicCompanyDetailsViewModel.class);
        View root = inflater.inflate(R.layout.basic_company_details_fragment, container, false);

        mCompanyDetailsType = root.findViewById(R.id.company_details_type);
        mCompanyDetailsType.getEditText().setText(UserDataModel.getmInstance().getSeller_type());
        mCompanyDetailsType.setFocusableInTouchMode(false);

        mCompanyDetailsName = root.findViewById(R.id.company_details_name);
        mCompanyDetailsName.getEditText().setText(UserDataModel.getmInstance().getSeller_company());
        mCompanyDetailsName.setFocusableInTouchMode(false);

        mCompanyDetailsLocation = root.findViewById(R.id.company_details_location);
        mCompanyDetailsLocation.getEditText().setText(UserDataModel.getmInstance().getSeller_address());
        mCompanyDetailsLocation.setFocusableInTouchMode(false);

        mCompanyDetailsRegisNumber = root.findViewById(R.id.company_details_regis_number);
        mCompanyDetailsRegisNumber.getEditText().setText(UserDataModel.getmInstance().getId());
        mCompanyDetailsRegisNumber.setFocusableInTouchMode(false);

        mCompanyDetailsNumber = root.findViewById(R.id.company_details_number);
        mCompanyDetailsNumber.getEditText().setText(UserDataModel.getmInstance().getSl_number());
        mCompanyDetailsNumber.setFocusableInTouchMode(false);

        mCompanyDetailsWebsite = root.findViewById(R.id.company_details_website);
        mCompanyDetailsWebsite.getEditText().setText(UserDataModel.getmInstance().getSeller_website());
        mCompanyDetailsWebsite.setFocusableInTouchMode(false);


        return root;
    }

}