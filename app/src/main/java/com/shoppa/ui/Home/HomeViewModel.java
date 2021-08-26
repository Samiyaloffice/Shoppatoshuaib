package com.shoppa.ui.Home;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.tabs.TabLayout;
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.Model.PremiumSellerModel;
import com.shoppa.RepositoryManager.HomeCategoryRepository;
import com.shoppa.RepositoryManager.PremiumSellerRepository;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final Handler CategoryHandler = new Handler();
    public ArrayList<String> mImagesArrayList;
    public ArrayList<PremiumSellerModel> mPremiumSellerArrayList;
    public MutableLiveData<ArrayList<PremiumSellerModel>> mBannerData;
    public MutableLiveData<ArrayList<String>> mImagesMutableLiveData;
    public ArrayList<HomeCategoryModel> mHomeCatModelArrayList;
    public boolean isCatResponseDone = false, isBannerResponseDone = false;
    public MutableLiveData<ArrayList<HomeCategoryModel>> mCategoryData;
    Handler mHandler;
    TabLayout.Tab mTab;
    Context context;
    PremiumSellerRepository mPremiumSellerRepo;
    HomeCategoryRepository mCategoryRepo;

    private final Runnable repeatativeCategoryRunnable = new Runnable() {

        public void run() {
            Log.i("catData", "run: " + mCategoryRepo.isResponseDone);
            if (mCategoryRepo.isResponseDone) {
                mHomeCatModelArrayList = mCategoryRepo.getHomeCategories().getValue();
                mCategoryData.setValue(mHomeCatModelArrayList);
//                Log.i("catData", "run: " + mCategoryData.getValue().get(0).getCategoryName());
                stopCatHandler();
            } else {
                startCatHandler();
            }
        }
    };
    Handler mBannerHandler = new Handler();
    Runnable mBannerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPremiumSellerRepo.isResponseDone || mPremiumSellerRepo.isError) {
                if (mPremiumSellerRepo.isResponseDone) {
                    isBannerResponseDone = true;
                    mPremiumSellerArrayList = mPremiumSellerRepo.getPremiumSellerData().getValue();
                    mBannerData.setValue(mPremiumSellerArrayList);
                } else {
                    isBannerResponseDone = false;
                }
                stopBannerHandler();
            } else {
                startBannerHandler();
            }
        }
    };

    public HomeViewModel() {
        mImagesArrayList = new ArrayList<>();
        mImagesMutableLiveData = new MutableLiveData<>();
        mHomeCatModelArrayList = new ArrayList<>();
        mCategoryData = new MutableLiveData<>();
        mPremiumSellerArrayList = new ArrayList<>();
        mBannerData = new MutableLiveData<>();
        setImagesListData();
    }

    public void setContext(Context context) {
        this.context = context;
        mCategoryRepo = HomeCategoryRepository.getInstance(context);
        mPremiumSellerRepo = PremiumSellerRepository.getInstance(context);
        setPremiumSellerData();
        setCategoryData();
//        setTabRotator();
    }

    public void setCategoryData() {
        isCatResponseDone = false;
        mCategoryRepo.fetchData();
        repeatativeCategoryRunnable.run();
    }

    private void setTabRotator() {
        HomeFragment.mHomeCardTabIndicator.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(HomeFragment.mCardViewPager) {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });
//        repeatativeTaskRunnable.run();
    }

    public LiveData<ArrayList<HomeCategoryModel>> getCatData() {
        Log.i("catData", "getCatData: LiveCatTriggered");
        return mCategoryData;
    }

    void startCatHandler() {
        CategoryHandler.postDelayed(repeatativeCategoryRunnable, 100);
        isCatResponseDone = false;
    }

    void stopCatHandler() {
        CategoryHandler.removeCallbacks(repeatativeCategoryRunnable);
        isCatResponseDone = true;
    }

    private void setImagesListData() {
        mImagesArrayList.add("https://www.shoppa.in/img_for_link/android1.jpeg");
        mImagesArrayList.add("https://www.shoppa.in/img_for_link/android2.jpg");
        mImagesArrayList.add("https://www.shoppa.in/img_for_link/android3.jpg");
        mImagesMutableLiveData.setValue(mImagesArrayList);
    }

    public LiveData<ArrayList<String>> getImagesList() {
        return mImagesMutableLiveData;
    }

    public ArrayList<HomeCategoryModel> getAllCatData() {
        return mCategoryRepo.getAllCatData().getValue();
    }

    public void setPremiumSellerData() {
        isBannerResponseDone = false;
        Log.i("SellerData", "setPremiumSellerData: setPremiumSellerData");
        mPremiumSellerRepo.fetchSellerData();
        mBannerRunnable.run();
    }

    private void startBannerHandler() {
        mBannerHandler.postDelayed(mBannerRunnable, 100);
    }

    private void stopBannerHandler() {
        mBannerHandler.removeCallbacks(mBannerRunnable);
    }

    public LiveData<ArrayList<PremiumSellerModel>> getPremiumSellerData() {
        return mBannerData;
    }

}