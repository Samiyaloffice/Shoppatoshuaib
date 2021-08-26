package com.shoppa.ui.TradeDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoppa.R;

public class TradeDetailsFragment extends Fragment {

    public static RadioButton mCompanyOverseasYes, mCompanyOverseasNo;
    private TradeDetailsViewModel mViewModel;

    public static TradeDetailsFragment newInstance() {
        return new TradeDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.trade_details_fragment, container, false);
        mCompanyOverseasYes = root.findViewById(R.id.company_overseas_yes);
        mCompanyOverseasNo = root.findViewById(R.id.company_overseas_no);
        mViewModel = new ViewModelProvider(this).get(TradeDetailsViewModel.class);

        return root;
    }
}