package com.shoppa.ui.AccountInformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;

public class AccountIformationFragment extends Fragment {

    TextInputEditText mAccountInformationUserId, mAccountInformationMemberType;
    private AccountIformationViewModel mViewModel;

    public static AccountIformationFragment newInstance() {
        return new AccountIformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.account_iformation_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(AccountIformationViewModel.class);

        mAccountInformationUserId = root.findViewById(R.id.account_information_user_id);
        mAccountInformationUserId.setText(UserDataModel.getmInstance().getId());

        mAccountInformationMemberType = root.findViewById(R.id.account_information_user_member_type);
        mAccountInformationMemberType.setText(UserDataModel.getmInstance().getSeller_plan());

        return root;
    }


}