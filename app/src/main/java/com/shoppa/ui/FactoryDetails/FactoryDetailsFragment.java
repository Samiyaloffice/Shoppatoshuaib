package com.shoppa.ui.FactoryDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoppa.R;

public class FactoryDetailsFragment extends Fragment {

    private FactoryDetailsViewModel mViewModel;

    public static FactoryDetailsFragment newInstance() {
        return new FactoryDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FactoryDetailsViewModel.class);
        View root = inflater.inflate(R.layout.factory_details_fragment, container, false);

        return root;
    }

}