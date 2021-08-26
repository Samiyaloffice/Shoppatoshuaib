package com.shoppa.ui.Trademark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoppa.R;

public class TrademarkFragment extends Fragment {

    private TrademarkViewModel mViewModel;

    public static TrademarkFragment newInstance() {
        return new TrademarkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(TrademarkViewModel.class);
        View root = inflater.inflate(R.layout.trademark_fragment, container, false);

        return root;
    }

}