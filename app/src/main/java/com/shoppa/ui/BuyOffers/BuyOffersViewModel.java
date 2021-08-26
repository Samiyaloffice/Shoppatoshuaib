package com.shoppa.ui.BuyOffers;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.LatestBuyLeadModel;
import com.shoppa.RepositoryManager.LatestBuyLeadRepository;

import java.util.ArrayList;

public class BuyOffersViewModel extends ViewModel {

    public boolean isResponseDone = false;
    LatestBuyLeadRepository mLatestBuyLeadRepo;
    Context context;
    ArrayList<LatestBuyLeadModel> mBuyOfferArrayList;
    MutableLiveData<ArrayList<LatestBuyLeadModel>> mBuyOfferData;
    Handler mOfferHandler = new Handler();
    Runnable mOfferRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLatestBuyLeadRepo.isResponseDone) {
                mBuyOfferArrayList = new ArrayList<>();
                mBuyOfferArrayList = mLatestBuyLeadRepo.getBuyLeadData().getValue();
                stopOfferHandler();
            } else {
                startOfferHandler();
            }
        }
    };

    public BuyOffersViewModel() {

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getBuyOffers() {
        mLatestBuyLeadRepo = LatestBuyLeadRepository.getInstance(context);
        mLatestBuyLeadRepo.fetchBuyLead();
        mOfferRunnable.run();
        isResponseDone = false;
    }

    private void startOfferHandler() {
        mOfferHandler.postDelayed(mOfferRunnable, 100);
    }

    private void stopOfferHandler() {
        mOfferHandler.removeCallbacks(mOfferRunnable);
        isResponseDone = true;
    }

    public LiveData<ArrayList<LatestBuyLeadModel>> getBuyOfferData() {
        mBuyOfferData = new MutableLiveData<>();
        mBuyOfferData.setValue(mBuyOfferArrayList);
        return mBuyOfferData;
    }

}