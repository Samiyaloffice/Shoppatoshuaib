package com.shoppa.ui.SupplierInfo;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.RepositoryManager.ProductDetailRepository;
import com.shoppa.RepositoryManager.SellerProductRepository;

import java.util.ArrayList;

public class SupplierInfoViewModel extends ViewModel {

    public boolean isResponseDone = false;
    public boolean isSellerResponseDone = false;
    ProductDetailRepository mProductDetailRepo;
    ArrayList<ProductDetailModel> mProductDetailModelArrayList;
    SellerDetailModel mSellerDetailModel;
    MutableLiveData<SellerDetailModel> mSellerData;
    MutableLiveData<ArrayList<ProductDetailModel>> data;
    SellerProductRepository mSellerProductRepository;
    Handler sellerHandler = new Handler();
    public Runnable sellerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mProductDetailRepo.isSellerResponseDone) {
                mSellerDetailModel = mProductDetailRepo.getSellerDetailData().getValue();
                stopSellerHandler();
            } else {
                startSellerHandler();
            }
        }
    };
    Handler dataHandler = new Handler();
    public Runnable dataWatcher = new Runnable() {
        @Override
        public void run() {
            if (mSellerProductRepository.isResponseDone) {
                mProductDetailModelArrayList = new ArrayList<>();
                mProductDetailModelArrayList = mSellerProductRepository.getSellerProducts().getValue();
                stopHandler();
            } else {
                startHandler();
            }
        }
    };

    public SupplierInfoViewModel() {

    }

    public void setContext(Context context, String id) {
        mSellerProductRepository = SellerProductRepository.getInstance(context);
        mProductDetailRepo = ProductDetailRepository.getInstance(context);
        getSupplierInfo(id);
    }

    public void getSupplierInfo(String id) {
        isSellerResponseDone = false;
        isResponseDone = false;
        mProductDetailRepo.fetchSellerDetails(id);
        mSellerProductRepository.fetchSellerProducts(id);
        dataWatcher.run();
        sellerRunnable.run();
    }

    private void startSellerHandler() {
        sellerHandler.postDelayed(sellerRunnable, 100);
        isSellerResponseDone = false;
    }

    private void stopSellerHandler() {
        sellerHandler.removeCallbacks(sellerRunnable);
        isSellerResponseDone = true;
    }

    private void stopHandler() {
        dataHandler.removeCallbacks(dataWatcher);
        isResponseDone = true;
    }

    private void startHandler() {
        dataHandler.postDelayed(dataWatcher, 100);
        isResponseDone = false;
    }

    public LiveData<ArrayList<ProductDetailModel>> getSellerProducts() {
        data = new MutableLiveData<>();
        data.setValue(mProductDetailModelArrayList);
        return data;
    }

    public LiveData<SellerDetailModel> getSellerDetails() {
        mSellerData = new MutableLiveData<>();
        mSellerData.setValue(mSellerDetailModel);
        return mSellerData;
    }

}