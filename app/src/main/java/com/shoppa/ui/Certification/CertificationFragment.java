package com.shoppa.ui.Certification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoppa.R;

public class CertificationFragment extends Fragment {

    private CertificationViewModel mViewModel;

    public static CertificationFragment newInstance() {
        return new CertificationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CertificationViewModel.class);
        View root = inflater.inflate(R.layout.certification_fragment, container, false);

        return root;
    }
}