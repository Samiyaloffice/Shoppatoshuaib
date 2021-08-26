package com.shoppa.ui.BuyingRequest;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.UserBuyRequestModel;
import com.shoppa.RepositoryManager.UserBuyRequestRepository;

import java.util.ArrayList;

public class BuyingRequestViewModel extends ViewModel {

    public boolean isResponseDone = false;
    Context context;
    ArrayList<UserBuyRequestModel> mUserBuyRequestArrayList;
    MutableLiveData<ArrayList<UserBuyRequestModel>> mData;
    UserBuyRequestRepository mUserBuyRequestRepo;
    Handler mRequestHandler = new Handler();
    Runnable mRequestRunnable = new Runnable() {
        @Override
        public void run() {
            if (mUserBuyRequestRepo.isResponseDone) {
                isResponseDone = true;
                mUserBuyRequestArrayList = new ArrayList<>();
                mUserBuyRequestArrayList = mUserBuyRequestRepo.getUserRequestData().getValue();
                stopRequestHandler();
            } else {
                startRequestHandler();
            }
        }
    };

    public void setContext(Context context) {
        this.context = context;
    }

    public void getUserRequest() {
        mUserBuyRequestRepo = UserBuyRequestRepository.getInstance(context);
        mUserBuyRequestRepo.fetchBuyRequest();
        isResponseDone = false;
        mRequestRunnable.run();
    }

    private void stopRequestHandler() {
        mRequestHandler.removeCallbacks(mRequestRunnable);
    }

    private void startRequestHandler() {
        mRequestHandler.postDelayed(mRequestRunnable, 100);
    }

    public LiveData<ArrayList<UserBuyRequestModel>> getUserBuyRequest() {
        mData = new MutableLiveData<>();
        mData.setValue(mUserBuyRequestArrayList);
        return mData;
    }
}