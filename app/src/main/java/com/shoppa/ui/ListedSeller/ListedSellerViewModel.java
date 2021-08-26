package com.shoppa.ui.ListedSeller;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.SellerDetailModel;
import com.shoppa.RepositoryManager.ListedSellerRepository;

import java.util.ArrayList;

public class ListedSellerViewModel extends ViewModel {

    public boolean isResponseDone = false, isPResponseDone = false;
    public String productId = "";
    Context context;
    ArrayList<SellerDetailModel> mListedSellerArrayList;
    MutableLiveData<ArrayList<SellerDetailModel>> mData;
    ListedSellerRepository mListedSellerRepo;
    Handler mSellerHandler = new Handler();
    Runnable mSellerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mListedSellerRepo.isResponseDone) {
                isResponseDone = true;
                mListedSellerArrayList = mListedSellerRepo.getListedSeller().getValue();
                stopSellerHandler();
            } else {
                isResponseDone = false;
                startSellerHandler();
            }
        }
    };
    Handler mPIdHandler = new Handler();
    Runnable mPIdRunnable = new Runnable() {
        @Override
        public void run() {
            if (mListedSellerRepo.isPResponseDone) {
                isPResponseDone = true;
                productId = mListedSellerRepo.productId;
                stopPHandler();
            } else {
                isPResponseDone = false;
                startPHandler();
            }
        }
    };

    private void stopPHandler() {
        mPIdHandler.removeCallbacks(mPIdRunnable);
    }

    private void startPHandler() {
        mPIdHandler.postDelayed(mPIdRunnable, 100);
    }


    public void fetchProductId(String sellerId, String productName) {
        mListedSellerRepo.fetchProductId(sellerId, productName);
        mPIdRunnable.run();
    }

    private void startSellerHandler() {
        mSellerHandler.postDelayed(mSellerRunnable, 100);
    }

    private void stopSellerHandler() {
        mSellerHandler.removeCallbacks(mSellerRunnable);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void searchItem(String toString) {

    }

    public void fetchSeller(ArrayList<String> mListedSellerArrayList) {
        mListedSellerRepo = ListedSellerRepository.getInstance(context);
        mListedSellerRepo.fetchListedSeller(mListedSellerArrayList);
        this.mListedSellerArrayList = new ArrayList<>();
        mSellerRunnable.run();
    }

    public LiveData<ArrayList<SellerDetailModel>> getSellerData() {
        mData = new MutableLiveData<>();
        mData.setValue(mListedSellerArrayList);
        return mData;
    }

}