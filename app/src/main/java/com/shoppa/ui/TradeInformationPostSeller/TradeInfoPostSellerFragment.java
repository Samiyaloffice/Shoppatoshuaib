package com.shoppa.ui.TradeInformationPostSeller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.R;
import com.shoppa.ui.WebView.WebViewFragment;

public class TradeInfoPostSellerFragment extends Fragment {

    public static RadioButton mTradeInfoFobPriceBtn, mTradeInfoQuantityPriceBtn, mTradeInfoOEMBtn, mTradeInfoStockBtn, mTradeInfoOverseasYesBtn, mTradeInfoOverseasNoBtn;
    public static LinearLayout mTradeInfoFobPriceLayout, mTradeInfoQuantityPriceLayout;
    public static MaterialAutoCompleteTextView mTradeInfoFobPriceTxt, mTradeInfoPerTxt, mTradeInfoUnitTxt, mTradeInfoSelectTimeTxt, mTradeInfoQuantityUnitTypeTxt;
    MaterialTextView mTradeInfoPolicyBtn;
    private TradeInfoPostSellerViewModel mViewModel;

    public static TradeInfoPostSellerFragment newInstance() {
        return new TradeInfoPostSellerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.trade_info_post_seller_fragment, container, false);

        mTradeInfoFobPriceBtn = root.findViewById(R.id.trade_info_fob_price_btn);
        mTradeInfoQuantityPriceBtn = root.findViewById(R.id.trade_info_quantity_price_btn);
        mTradeInfoOEMBtn = root.findViewById(R.id.trade_info_oem_btn);
        mTradeInfoStockBtn = root.findViewById(R.id.trade_info_stock_btn);
        mTradeInfoOverseasYesBtn = root.findViewById(R.id.trade_info_overseas_yes_btn);
        mTradeInfoOverseasNoBtn = root.findViewById(R.id.trade_info_overseas_no_btn);

        mTradeInfoPolicyBtn = root.findViewById(R.id.trade_info_policy_btn);
        mTradeInfoPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebViewFragment();
            }
        });

        mTradeInfoFobPriceLayout = root.findViewById(R.id.trade_info_fob_price_layout);
        mTradeInfoQuantityPriceLayout = root.findViewById(R.id.trade_info_quantity_price_layout);

        mTradeInfoFobPriceTxt = root.findViewById(R.id.trade_info_fob_price_txt);
        mTradeInfoPerTxt = root.findViewById(R.id.trade_info_per_txt);
        mTradeInfoUnitTxt = root.findViewById(R.id.trade_info_unit_txt);
        mTradeInfoSelectTimeTxt = root.findViewById(R.id.trade_info_select_time_txt);
        mTradeInfoQuantityUnitTypeTxt = root.findViewById(R.id.trade_info_quantity_unit_type_txt);
        mViewModel = new ViewModelProvider(this).get(TradeInfoPostSellerViewModel.class);
        mViewModel.setContext(getContext());
        return root;
    }

    private void openWebViewFragment() {

        WebViewFragment fragment = new WebViewFragment();
        fragment.Url = "https://www.shoppa.in/privacy-policy";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack("WebViewFragemntAdded").commit();

    }

}