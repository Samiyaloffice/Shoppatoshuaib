package com.shoppa.ui.TradeAlerts;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.shoppa.Adapters.TradeAlertAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.PackageModel;
import com.shoppa.Model.TradeAlertModel;
import com.shoppa.R;
import com.shoppa.ui.PackageItem.PackageItemFragment;

import java.util.ArrayList;

public class TradeAlertsFragment extends Fragment {

    private final boolean isTextInput = false;
    RecyclerView mTradeAlertsRecyclerView;
    TradeAlertAdapter mTradeAlertAdapter;
    ShapeableImageView mTradeAlertAddBtn;
    Handler tradeHandler = new Handler();
    Handler mPackageHandler = new Handler();
    private TradeAlertsViewModel mViewModel;
    private Dialog dialog;
    Runnable mPackageRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isPackageResponseDone) {
                packageDetailFragment(mViewModel.getPackageData().getValue());
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
                stopPackageHandler();
            } else {
                startPackageHandler();
            }
        }
    };
    Runnable tradeRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                setTradeAlertAdapter();
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
                stopTradeHandler();
            } else {
                startTradeHandler();
            }
        }
    };

    public static TradeAlertsFragment newInstance() {
        return new TradeAlertsFragment();
    }

    private void packageDetailFragment(PackageModel value) {

        PackageItemFragment fragment = new PackageItemFragment();
        fragment.model = value;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("PackageItemFragmentAdded");
        transaction.commit();

    }

    private void stopPackageHandler() {
        mPackageHandler.removeCallbacks(mPackageRunnable);
    }

    private void startPackageHandler() {
        mPackageHandler.postDelayed(mPackageRunnable, 100);
    }

    private void stopTradeHandler() {
        tradeHandler.removeCallbacks(tradeRunnable);
    }

    private void startTradeHandler() {
        tradeHandler.postDelayed(tradeRunnable, 100);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DataManager.isFrom = "optionFragment";
        dialog = new Dialog(requireContext());
        mViewModel = new ViewModelProvider(this).get(TradeAlertsViewModel.class);
        View root = inflater.inflate(R.layout.trade_alerts_fragment, container, false);
        mTradeAlertsRecyclerView = root.findViewById(R.id.trade_alerts_recycler_View);

        mViewModel.setContext(getContext());
        getTradeAlertData();

        return root;

    }

    public void getTradeAlertData() {
        mViewModel.fetchTradeAlert();
        tradeRunnable.run();
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
    }

    private void setTradeAlertAdapter() {

        mTradeAlertsRecyclerView.setHasFixedSize(true);
        mTradeAlertsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mTradeAlertAdapter = new TradeAlertAdapter(getContext(), mViewModel.getTradeAlertData().getValue(), new TradeAlertAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String PackageName) {
                packageFragment(PackageName);
            }

            @Override
            public void OnViewClick() {

            }
        });
        mTradeAlertsRecyclerView.setAdapter(mTradeAlertAdapter);

        mViewModel.getTradeAlertData().observe(getViewLifecycleOwner(), new Observer<ArrayList<TradeAlertModel>>() {
            @Override
            public void onChanged(ArrayList<TradeAlertModel> tradeAlertModels) {
                mTradeAlertAdapter.notifyDataSetChanged();
            }
        });
    }

    private void packageFragment(String packageName) {
        mViewModel.fetchPackageData(packageName);
        mPackageRunnable.run();
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
    }

}