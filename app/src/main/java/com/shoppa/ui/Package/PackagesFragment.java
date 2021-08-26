package com.shoppa.ui.Package;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shoppa.Adapters.PackageFragmentCellAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.PackageModel;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.Home.HomeFragment;
import com.shoppa.ui.PackageItem.PackageItemFragment;

public class PackagesFragment extends Fragment {


    public RecyclerView mPackageFragmentRecyclerView;
    PackageFragmentCellAdapter mPackageFragmentCellAdapter;
    boolean backRequest = false;
    Dialog dialog;
    SwipeRefreshLayout mPackageSwipeLayout;
    Handler packageHandler = new Handler();
    private PackagesViewModel mViewModel;

    Runnable packageRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                setPackageData();
                stopPackageHandler();
            } else {
                startPackageHandler();
            }
        }
    };

    public static PackagesFragment newInstance() {
        return new PackagesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.packages_fragment, container, false);
        dialog = new Dialog(requireContext());
        Handler mBackHandler = new Handler();


   mPackageSwipeLayout = root.findViewById(R.id.package_swipe_layout);
        mPackageSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.setPackageData();
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                packageRunnable.run();
                mPackageSwipeLayout.setRefreshing(false);
            }
        });

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    onBackTriggered();
                    backRequest = true;

                    mBackHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backRequest = false;
                        }
                    }, 2000);

                    return true;
                }
                return false;
            }
        });

        mViewModel = new ViewModelProvider(this).get(PackagesViewModel.class);

        DashboardFragment.mDashboardPackageBtn.setIconTintResource(R.color.icon_selected);
        DashboardFragment.mDashboardPackageTxt.setVisibility(View.VISIBLE);

        DashboardFragment.mDashboardHomeBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardHomeTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardChatBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardChatTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardOptionBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardOptionTxt.setVisibility(View.GONE);
        DashboardFragment.mHomeSearchView.setVisibility(View.GONE);

        mPackageFragmentRecyclerView = root.findViewById(R.id.package_fragment_recycler_view);

        mViewModel.setContext(getContext());
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        packageRunnable.run();
//        setPackageData();

        return root;
    }

    private void startPackageHandler() {
        packageHandler.postDelayed(packageRunnable, 100);
    }

    private void stopPackageHandler() {
        packageHandler.removeCallbacks(packageRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void onBackTriggered() {
        if (backRequest) {
            requireActivity().finish();
        } else {
            Toast.makeText(getContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private void setPackageData() {

        mPackageFragmentRecyclerView.setHasFixedSize(true);
        mPackageFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mPackageFragmentCellAdapter = new PackageFragmentCellAdapter(getContext(), mViewModel.getPackageData().getValue(), new PackageFragmentCellAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick() {

            }

            @Override
            public void OnViewClick(PackageModel model) {
                packageItemFragment(model);
            }
        });

        mPackageFragmentRecyclerView.setAdapter(mPackageFragmentCellAdapter);

    }

    private void packageItemFragment(PackageModel model) {

        PackageItemFragment fragment = new PackageItemFragment();
        fragment.model = model;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("PackageItemFragment");
        transaction.commit();
          }

}