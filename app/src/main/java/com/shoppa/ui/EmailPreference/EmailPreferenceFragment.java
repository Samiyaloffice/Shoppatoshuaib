package com.shoppa.ui.EmailPreference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoppa.R;

public class EmailPreferenceFragment extends Fragment {

    private EmailPreferenceViewModel mViewModel;

    public static EmailPreferenceFragment newInstance() {
        return new EmailPreferenceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(EmailPreferenceViewModel.class);
        View root = inflater.inflate(R.layout.email_preference_fragment, container, false);

        return root;
    }

}