package com.shoppa.ui.BuyOffers;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.shoppa.Adapters.LatestBuyLeadAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.LatestBuyLeadModel;
import com.shoppa.R;
import com.shoppa.ui.QuoteNow.QuoteNowFragment;

import java.util.ArrayList;

public class BuyOffersFragment extends Fragment {

    public static ShapeableImageView mBuyOfferBackImg, mBuyOfferNoDataImg;
    RecyclerView mBuyOfferRecyclerView;
    LatestBuyLeadAdapter mBuyOfferAdapter;
    Dialog dialog;
    Handler mOfferHandler = new Handler();
    private BuyOffersViewModel mViewModel;
    Runnable mOfferRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                setBuyOfferAdapter();
                stopOfferHandler();
            } else {
                startOfferHandler();
            }
        }
    };

    public static BuyOffersFragment newInstance() {
        return new BuyOffersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        DataManager.isFrom = "optionFragment";
        View root = inflater.inflate(R.layout.buy_offers_fragment, container, false);
        dialog = new Dialog(requireContext());
        mBuyOfferBackImg = root.findViewById(R.id.buy_offer_back_img);
        mBuyOfferRecyclerView = root.findViewById(R.id.buy_offer_recycler_view);
        mBuyOfferRecyclerView.setVisibility(View.VISIBLE);
        mBuyOfferNoDataImg = root.findViewById(R.id.buy_offer_no_data_found);
        mBuyOfferNoDataImg.setVisibility(View.GONE);
        mViewModel = new ViewModelProvider(this).get(BuyOffersViewModel.class);

        setImageCorners();
//        setBuyOfferAdapter();


        setBuyOfferData();


        return root;
    }

    private void setBuyOfferData() {
        mViewModel.getBuyOffers();
        mOfferRunnable.run();
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
    }

    private void stopOfferHandler() {
        mOfferHandler.removeCallbacks(mOfferRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void startOfferHandler() {
        mOfferHandler.postDelayed(mOfferRunnable, 100);
    }

    private void setBuyOfferAdapter() {

        mBuyOfferRecyclerView.setHasFixedSize(true);
        mBuyOfferRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mBuyOfferAdapter = new LatestBuyLeadAdapter(requireContext(), mViewModel.getBuyOfferData().getValue(), new LatestBuyLeadAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick() {

            }
        });
        mBuyOfferRecyclerView.setAdapter(mBuyOfferAdapter);

        checkDataAvailability(mViewModel.getBuyOfferData().getValue());

        /*mBuyOfferRecyclerView.setHasFixedSize(true);
        mBuyOfferRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBuyOfferAdapter = new BuyOfferAdapter(getContext(), new BuyOfferAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick() {
                buyOfferQuoteNow();
            }
        });
        mBuyOfferRecyclerView.setAdapter(mBuyOfferAdapter);*/
    }

    private void checkDataAvailability(ArrayList<LatestBuyLeadModel> value) {
        if (value.size() == 0) {
            mBuyOfferNoDataImg.setVisibility(View.VISIBLE);
            Glide.with(requireContext()).load(DataManager.noDataImg).error(R.drawable.no_image_found).into(mBuyOfferNoDataImg);
            mBuyOfferRecyclerView.setVisibility(View.GONE);
        }
    }

    private void buyOfferQuoteNow() {

        QuoteNowFragment fragment = new QuoteNowFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("QuoteFragmentAdded");
        transaction.commit();

    }

    private void setImageCorners() {

        float corner = 950f;

        mBuyOfferBackImg.setShapeAppearanceModel(mBuyOfferBackImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());

        mBuyOfferBackImg.setShapeAppearanceModel(mBuyOfferBackImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomRightCorner(CornerFamily.ROUNDED, corner).build());
    }

}