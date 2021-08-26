package com.shoppa.ui.Dashboard;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.AllProductModel;
import com.shoppa.Model.BuyerModel;
import com.shoppa.RepositoryManager.AllProductRepository;
import com.shoppa.RepositoryManager.BuyerRepository;
import com.shoppa.RepositoryManager.SellerIdRepository;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel implements View.OnClickListener {

    public boolean isResponseDone = false, isSellerResponseDone = false, isBuyerResponseDone = false;
    BuyerRepository mBuyerRepo;
    Context context;
    ArrayList<BuyerModel> mBuyerArrayList;
    MutableLiveData<ArrayList<BuyerModel>> mBuyerData;
    Handler mBuyerHandler = new Handler();
    Runnable mBuyerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBuyerRepo.isBuyerResponseDone) {
                mBuyerArrayList = mBuyerRepo.getBuyerData().getValue();
//                Log.i("BuyerCount", "run: count - "+mBuyerArrayList.size());
                isBuyerResponseDone = true;
                Log.i("BuyerResponse", "onErrorResponse: isBuyerResponse ViewModel - "+isBuyerResponseDone);
                stopBuyerHandler();
            } else {
                startBuyerHandler();
            }
        }
    };
    private Handler mSellerHandler = new Handler();
    private AllProductRepository mAllProductRepo;
    private SellerIdRepository mSellerIdRepo;
    private Handler mHandler = new Handler();
    private MutableLiveData<ArrayList<String>> mSellerData;
    private ArrayList<AllProductModel> mAllProductArrayList;
    private ArrayList<String> mSellerIdArrayList;
    private Runnable mSellerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSellerIdRepo.isResponseDone) {
                mSellerIdArrayList = mSellerIdRepo.getSellerId().getValue();

                isSellerResponseDone = true;
                stopSellerHandler();
            } else {
                isSellerResponseDone = false;
                startSellerHandler();
            }
        }
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mAllProductRepo.isResponseDone) {
                mAllProductArrayList = mAllProductRepo.getAllProduct().getValue();
                isResponseDone = true;
                stopHandler();
            } else {
                isResponseDone = false;
                startHandler();
            }
        }
    };
    private MutableLiveData<ArrayList<AllProductModel>> mData;

    private void startBuyerHandler() {
        mBuyerHandler.postDelayed(mBuyerRunnable, 100);
    }

    private void stopBuyerHandler() {
        mBuyerHandler.removeCallbacks(mBuyerRunnable);
    }

    private void startSellerHandler() {
        mSellerHandler.postDelayed(mSellerRunnable, 100);
    }

    private void stopSellerHandler() {
        mSellerHandler.removeCallbacks(mSellerRunnable);
    }

    private void stopHandler() {
        mHandler.removeCallbacks(mRunnable);
    }

    private void startHandler() {
        mHandler.postDelayed(mRunnable, 100);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {

    }

    public void fetchAllProducts() {
        isResponseDone = false;
        mAllProductRepo = AllProductRepository.getInstance(context);
        mAllProductRepo.fetchAllProducts();
        mRunnable.run();
    }

    public void fetchSellerId(String productName) {
        isSellerResponseDone = false;
        mSellerIdRepo = SellerIdRepository.getInstance(context);
        mSellerIdRepo.fetchSellerId(productName);
        mSellerRunnable.run();
    }

    public LiveData<ArrayList<String>> getSellerId() {
        mSellerData = new MutableLiveData<>();
        mSellerData.setValue(mSellerIdArrayList);
        return mSellerData;
    }

    public LiveData<ArrayList<AllProductModel>> getAllProduct() {
        mData = new MutableLiveData<>();
        mData.setValue(mAllProductArrayList);
        return mData;
    }

    public void fetchSellerProduct() {
        mBuyerRepo = BuyerRepository.getInstance(context);
        mBuyerRepo.fetchSellerProduct();
        isBuyerResponseDone = false;
        mBuyerRunnable.run();
    }

    public LiveData<ArrayList<BuyerModel>> getBuyerData() {
        mBuyerData = new MutableLiveData<>();
        mBuyerData.setValue(mBuyerArrayList);
        return mBuyerData;
    }

}