package com.shoppa.ui.LatestBuyLead;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shoppa.Adapters.LatestBuyLeadAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;

public class LatestBuyLeadFragment extends Fragment {

    RecyclerView mBuyLeadRecyclerView;
    LatestBuyLeadAdapter mLatestBuyLead;
    Dialog dialog;
    SwipeRefreshLayout mLatestBuyLeadRefreshLayout;
    Handler leadHandler = new Handler();
    private LatestBuyLeadViewModel mViewModel;
    Runnable leadRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                setBuyLeadsAdapter();
                stopLeadHandler();
            } else {
                startLeadHandler();
            }
        }
    };

    public static LatestBuyLeadFragment newInstance() {
        return new LatestBuyLeadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.latest_buy_lead_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(LatestBuyLeadViewModel.class);
        mBuyLeadRecyclerView = root.findViewById(R.id.buy_lead_recycler_view);
        mLatestBuyLeadRefreshLayout = root.findViewById(R.id.latest_buy_lead_refresh_layout);
        mLatestBuyLeadRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getBuyLeadData();
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                leadRunnable.run();
                mLatestBuyLeadRefreshLayout.setRefreshing(false);
            }
        });

        dialog = new Dialog(requireContext());

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    DashboardFragment fragment = new DashboardFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment);
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });

        mViewModel.setContext(requireContext());
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        leadRunnable.run();
//        setBuyLeadsAdapter();

        return root;
    }

    private void startLeadHandler() {
        leadHandler.postDelayed(leadRunnable, 100);
    }

    private void stopLeadHandler() {
        leadHandler.removeCallbacks(leadRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void setBuyLeadsAdapter() {

        mBuyLeadRecyclerView.setHasFixedSize(true);
        mBuyLeadRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mLatestBuyLead = new LatestBuyLeadAdapter(requireContext(), mViewModel.getLeadData().getValue(), new LatestBuyLeadAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick() {

            }
        });
        mBuyLeadRecyclerView.setAdapter(mLatestBuyLead);

    }

}