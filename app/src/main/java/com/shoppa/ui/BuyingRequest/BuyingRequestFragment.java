package com.shoppa.ui.BuyingRequest;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.shoppa.Adapters.BuyingRequestAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;


public class BuyingRequestFragment extends Fragment {

    ShapeableImageView mBuyingRequestAddBtn;
    RecyclerView mBuyingRequestRecyclerView;
    BuyingRequestAdapter mBuyingRequestAdapter;
    Dialog dialog;
    Handler mRequestHandler = new Handler();
    private BuyingRequestViewModel mViewModel;
    Runnable mRequestRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                setBuyingRequestAdapter();
                stopRequestHandler();
            } else {
                startRequestHandler();
            }
        }
    };

    public static BuyingRequestFragment newInstance() {
        return new BuyingRequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DataManager.isFrom = "optionFragment";

        mViewModel = new ViewModelProvider(this).get(BuyingRequestViewModel.class);
        View root = inflater.inflate(R.layout.buying_request_fragment, container, false);

        dialog = new Dialog(requireContext());
        mBuyingRequestRecyclerView = root.findViewById(R.id.buying_request_recycler_view);
        mBuyingRequestAddBtn = root.findViewById(R.id.buying_request_add_btn);
        mBuyingRequestAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBuyerFragment();
            }
        });

        mViewModel.setContext(requireContext());
        mViewModel.getUserRequest();
        mRequestRunnable.run();
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
//        setBuyingRequestAdapter();

        return root;
    }

    private void stopRequestHandler() {
        mRequestHandler.removeCallbacks(mRequestRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void startRequestHandler() {
        mRequestHandler.postDelayed(mRequestRunnable, 100);
    }

    private void setBuyingRequestAdapter() {

        mBuyingRequestRecyclerView.setHasFixedSize(true);
        mBuyingRequestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBuyingRequestAdapter = new BuyingRequestAdapter(getContext(), new BuyingRequestAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick() {

            }
        }, mViewModel.getUserBuyRequest().getValue());

        mBuyingRequestRecyclerView.setAdapter(mBuyingRequestAdapter);

    }

    private void postBuyerFragment() {

        /*PostBuyerFragment fragment = new PostBuyerFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("postBuyerFrgmentAdded");
        transaction.commit();*/
//Seller Credibility Score
    }

}