package com.shoppa.ui.Products;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.ProductDetailModel;
import com.shoppa.RepositoryManager.SellerProductRepository;

import java.util.ArrayList;

public class ProductsViewModel extends ViewModel {

    public boolean isResponseDone = false;
    ArrayList<ProductDetailModel> mProductDetailArrayList;
    MutableLiveData<ArrayList<ProductDetailModel>> data;
    Context context;
    SellerProductRepository mSellerProductRepo;
    Handler mProductHandler = new Handler();
    Runnable mProductRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSellerProductRepo.isResponseDone) {
                mProductDetailArrayList = new ArrayList<>();
                mProductDetailArrayList = mSellerProductRepo.getSellerProducts().getValue();
                isResponseDone = true;
                stopProductHandler();
            } else {
                startProductHandler();
            }
        }
    };

    public void setContext(Context context) {
        this.context = context;
    }

    public void getProductData(String id) {
        mSellerProductRepo = SellerProductRepository.getInstance(context);
        mSellerProductRepo.fetchSellerProducts(id);
        isResponseDone = false;
        mProductRunnable.run();
    }

    private void stopProductHandler() {
        mProductHandler.removeCallbacks(mProductRunnable);
    }

    private void startProductHandler() {
        mProductHandler.postDelayed(mProductRunnable, 100);
    }

    public LiveData<ArrayList<ProductDetailModel>> getProductsList() {
        data = new MutableLiveData<>();
        data.setValue(mProductDetailArrayList);
        return data;
    }
}