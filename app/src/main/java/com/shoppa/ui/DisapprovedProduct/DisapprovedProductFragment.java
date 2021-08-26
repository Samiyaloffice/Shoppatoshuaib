package com.shoppa.ui.DisapprovedProduct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoppa.R;

public class DisapprovedProductFragment extends Fragment {

    private DisapprovedProductViewModel mViewModel;

    public static DisapprovedProductFragment newInstance() {
        return new DisapprovedProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DisapprovedProductViewModel.class);

        View root = inflater.inflate(R.layout.disapproved_product_fragment, container, false);

        return root;
    }

}