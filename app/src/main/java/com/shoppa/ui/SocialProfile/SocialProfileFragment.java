package com.shoppa.ui.SocialProfile;

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

public class SocialProfileFragment extends Fragment {

    TextInputLayout mSocialProfileWhatsapp, mSocialProfileSkype;
    private SocialProfileViewModel mViewModel;

    public static SocialProfileFragment newInstance() {
        return new SocialProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SocialProfileViewModel.class);
        View root = inflater.inflate(R.layout.social_profile_fragment, container, false);

        mSocialProfileWhatsapp = root.findViewById(R.id.social_profile_whatsapp);
        mSocialProfileWhatsapp.getEditText().setText(UserDataModel.getmInstance().getSeller_whatsapp());
        mSocialProfileSkype = root.findViewById(R.id.social_profile_skype);
        mSocialProfileSkype.getEditText().setText(UserDataModel.getmInstance().getSeller_skype());

        return root;
    }

}
