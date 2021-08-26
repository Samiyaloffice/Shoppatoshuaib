package com.shoppa.ui.CompanyIntroduction;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.RepositoryManager.ProductDetailRepository;
import com.shoppa.RepositoryManager.SellerProductRepository;

import java.util.ArrayList;

import static com.shoppa.ui.CompanyIntroduction.CompanyIntroductionFragment.mCompanyIntroductionBaseCard;
import static com.shoppa.ui.CompanyIntroduction.CompanyIntroductionFragment.mCompanyIntroductionScrollView;

public class CompanyIntroductionViewModel extends ViewModel {

    public boolean isResponseDone = false, isProductResponseDone = false;
    Float c = 1.000f;
    int pos = 0;
    Context context;
    ProductDetailRepository mProductDetailRepo;
    SellerDetailModel mSellerDetailModel;
    MutableLiveData<SellerDetailModel> data;


    ArrayList<ProductDetailModel> mProductDetailModelArrayList;
    MutableLiveData<ArrayList<ProductDetailModel>> mProductData;
    SellerProductRepository mSellerProductRepo;
    Handler mDetailHandler = new Handler();
    Runnable mDetailRunnable = new Runnable() {
        @Override
        public void run() {
            if (mProductDetailRepo.isSellerResponseDone) {
                isResponseDone = true;
                mSellerDetailModel = mProductDetailRepo.getSellerDetailData().getValue();
                stopDetailHandler();
            } else {
                startDetailHandler();
            }
        }
    };
    Handler mProductHandler = new Handler();
    Runnable mProductRunnable = new Runnable() {
        @Override
        public void run() {
            if (mProductDetailRepo.isResponseDone) {
                isProductResponseDone = true;
                mProductDetailModelArrayList = new ArrayList<>();
                mProductDetailModelArrayList = mSellerProductRepo.getSellerProducts().getValue();

                stopProductHandler();
            } else {
                startProductHandler();
            }
        }
    };

    public CompanyIntroductionViewModel() {
        setScrollProperty();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getCompanyDetails(String id) {

        mProductDetailRepo = ProductDetailRepository.getInstance(context);
        mProductDetailRepo.fetchSellerDetails(id);
        isResponseDone = false;
        mDetailRunnable.run();
    }

    private void stopDetailHandler() {
        mDetailHandler.removeCallbacks(mDetailRunnable);
    }

    private void startDetailHandler() {
        mDetailHandler.postDelayed(mDetailRunnable, 100);
    }

    public LiveData<SellerDetailModel> getSellerDetail() {
        data = new MutableLiveData<>();
        data.setValue(mSellerDetailModel);
        return data;
    }

    private void setScrollProperty() {

        mCompanyIntroductionScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                int a = 0, b = 500;

                if (pos < scrollY) {
                    if (scrollY >= 500) {
                        c = 0f;
                    } else {
                        c = c - 0.02f;
                    }
                    mCompanyIntroductionBaseCard.setAlpha((float) c);
                    Log.i("scroller", "onScrollChange: alpha " + c);

                    if (scrollY >= 500) {
                        pos = 500;
                    } else {
                        pos = pos + 10;
                    }

                } else if (pos > scrollY) {
                    if (scrollY <= 0) {
                        c = 1.0f;
                    } else {
                        c = c + 0.02f;
                    }

                    mCompanyIntroductionBaseCard.setAlpha((float) c);
                    Log.i("scroller", "onScrollChange: alpha " + c);

                    if (scrollY <= 0) {
                        pos = 0;
                    } else {
                        pos = pos - 10;
                    }
                }

                Log.i("scroller", "onScrollChange: ScrollY" + scrollY);
                Log.i("scroller", "onScrollChange: oldScrollY" + oldScrollY);

            }
        });

    }

    public void getSellerProduct(String id) {

        mSellerProductRepo = SellerProductRepository.getInstance(context);
        mSellerProductRepo.fetchSellerProducts(id);
        isProductResponseDone = false;
        mProductRunnable.run();
    }

    private void stopProductHandler() {
        mProductHandler.removeCallbacks(mProductRunnable);
    }

    private void startProductHandler() {
        mProductHandler.postDelayed(mProductRunnable, 100);
    }

    public LiveData<ArrayList<ProductDetailModel>> getProductList() {
        mProductData = new MutableLiveData<>();
        mProductData.setValue(mProductDetailModelArrayList);
        return mProductData;
    }
}