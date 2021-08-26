package com.shoppa.ui.Category;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.CatProductModel;
import com.shoppa.RepositoryManager.CatProductRepository;

import java.util.ArrayList;

public class CategoryViewModel extends ViewModel {

    private final Handler mHandler = new Handler();
    public boolean isResponseDone = false;
    ArrayList<CatProductModel> mCatProductsModelArrayList;
    ArrayList<CatProductModel> mDuplicateList;
    MutableLiveData<ArrayList<CatProductModel>> mProductData;
    CatProductRepository mProductRepo;
    private final Runnable dataWatcher = new Runnable() {
        @Override
        public void run() {

            if (mProductRepo.isResponseDone) {
                mCatProductsModelArrayList = mProductRepo.getCatProductData().getValue();
                setDuplicateList(0);
                stopHandler();
            } else {
                startHandler();
            }

        }
    };
    Context context;

    public CategoryViewModel() {
        mCatProductsModelArrayList = new ArrayList<>();
        mProductData = new MutableLiveData<>();
        mDuplicateList = new ArrayList<>();
    }

    public void setContext(Context context, String id) {
        this.context = context;
        mProductRepo = CatProductRepository.getInstance(context);
        setCatProductData(id);

    }

    public void setCatProductData(String id) {
        isResponseDone = false;
        mProductRepo.fetchCatProductData(id);
        dataWatcher.run();
    }

    private void startHandler() {
        mHandler.postDelayed(dataWatcher, 100);
        isResponseDone = false;
    }

    private void stopHandler() {
        mHandler.removeCallbacks(dataWatcher);
        isResponseDone = true;
    }

    public LiveData<ArrayList<CatProductModel>> getProductData() {
        return mProductData;
    }

    public void setDuplicateList(int position) {

        if (position != 0) {
            position = position + 1;
        }

        Log.i("scrollerPosition", "setDuplicateList: triggered position - " + position);
        ArrayList<CatProductModel> originalList = mCatProductsModelArrayList;
        int temp = position + 10;

        if (mCatProductsModelArrayList.size() <= temp) {
            for (; position < mCatProductsModelArrayList.size(); position++) {
                mDuplicateList.add(originalList.get(position));
            }
//            mDuplicateList.addAll(originalList.subList(position, mSubCatModelArrayList.size() - 1));
        } else {
            for (; position < temp; position++) {
                mDuplicateList.add(originalList.get(position));
            }
//            mDuplicateList.addAll(originalList.subList(position, temp));
        }
        /*    e.printStackTrace();
            Log.i("scrollerPosition", "setDuplicateList: error - " + e.getCause());
            mDuplicateList = originalList.subList(position, mSubCatModelArrayList.size() - 1);*/

        mProductData.setValue(mDuplicateList);

    }
}