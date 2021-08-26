package com.shoppa.ui.ProductDetails;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.RepositoryManager.ProductDetailRepository;

public class ProductDetailsViewModel extends ViewModel {

    private final Handler mHandler = new Handler();
    public boolean isResponseDone = false;
    public boolean isSellerResponseDone = false;
    public boolean isDone = false;
    ProductDetailModel mProductDetailModel;
    MutableLiveData<SellerDetailModel> sellerData;
    MutableLiveData<ProductDetailModel> data;
    Context context;
    ProductDetailRepository mProductDetailRepo;
    private final Runnable dataWatcher = new Runnable() {
        @Override
        public void run() {
            if (mProductDetailRepo.isResponseDone) {
                data = new MutableLiveData<>();
                data.setValue(mProductDetailRepo.getProductDetailData().getValue());
                isResponseDone = true;
            }
            if (mProductDetailRepo.isSellerResponseDone) {
                sellerData = new MutableLiveData<>();
                sellerData.setValue(mProductDetailRepo.getSellerDetailData().getValue());
                isSellerResponseDone = true;
            }
            if (isResponseDone && isSellerResponseDone) {
                stopHandler();
            } else {
                startHandler();
            }
        }
    };

    public ProductDetailsViewModel() {

    }

    public void setContext(Context context, String productName, String sellerId) {
        this.context = context;
        mProductDetailRepo = ProductDetailRepository.getInstance(context);
        setProductDetailData(productName, sellerId);
    }

    public void setProductDetailData(String productName, String sellerId) {
        isResponseDone = false;
        isSellerResponseDone = false;
        mProductDetailRepo.fetchProductDetail(productName, sellerId);
        mProductDetailRepo.fetchSellerDetails(sellerId);
        dataWatcher.run();

    }

    private void startHandler() {
        mHandler.postDelayed(dataWatcher, 100);
        isDone = false;
    }

    private void stopHandler() {
        mHandler.removeCallbacks(dataWatcher);
        isDone = true;
    }

    public LiveData<ProductDetailModel> getProductDetail() {
        return data;
    }

    public LiveData<SellerDetailModel> getSellerDetail() {
        return sellerData;
    }
}
