package com.shoppa.ui.LatestBuyLead;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.LatestBuyLeadModel;
import com.shoppa.RepositoryManager.LatestBuyLeadRepository;

import java.util.ArrayList;

public class LatestBuyLeadViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public boolean isResponseDone = false;
    ArrayList<LatestBuyLeadModel> mBuyLeadArrayList;
    MutableLiveData<ArrayList<LatestBuyLeadModel>> mBuyLeadData;
    Context context;
    LatestBuyLeadRepository mLatestBuyLeadRepo;
    Handler leadHandler = new Handler();
    Runnable leadRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLatestBuyLeadRepo.isResponseDone) {
                isResponseDone = true;
                mBuyLeadArrayList = new ArrayList<>();
                mBuyLeadArrayList = mLatestBuyLeadRepo.getBuyLeadData().getValue();
                stopLeadHandler();
            } else {
                isResponseDone = false;
                startLeadHandler();
            }
        }
    };

    public void setContext(Context context) {
        this.context = context;
        getBuyLeadData();
    }

    public void getBuyLeadData() {
        isResponseDone = false;
        mLatestBuyLeadRepo = LatestBuyLeadRepository.getInstance(context);
        mLatestBuyLeadRepo.fetchBuyLead();
        leadRunnable.run();
    }

    private void startLeadHandler() {
        leadHandler.postDelayed(leadRunnable, 100);
    }

    private void stopLeadHandler() {
        leadHandler.removeCallbacks(leadRunnable);
    }

    public LiveData<ArrayList<LatestBuyLeadModel>> getLeadData() {
        mBuyLeadData = new MutableLiveData<>();
        mBuyLeadData.setValue(mBuyLeadArrayList);
        return mBuyLeadData;
    }
}