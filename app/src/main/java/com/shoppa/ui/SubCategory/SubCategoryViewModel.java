package com.shoppa.ui.SubCategory;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.SubCategoryModel;
import com.shoppa.RepositoryManager.SubCategoryRepository;

import java.util.ArrayList;

public class SubCategoryViewModel extends ViewModel {

    public boolean isResponseDone = false;
    public ArrayList<SubCategoryModel> mSubCatModelArrayList;
    public ArrayList<SubCategoryModel> mDuplicateList;
    public MutableLiveData<ArrayList<SubCategoryModel>> mSubCatData;
    Context context;
    SubCategoryRepository mSubCatRepository;
    private final Handler mHandler = new Handler();
    private final Runnable repeatativeCatTask = new Runnable() {
        @Override
        public void run() {

            if (mSubCatRepository.isResponseDone) {
                mSubCatModelArrayList = mSubCatRepository.getSubCatData().getValue();
                setDuplicateList(0);
                stopHandler();
            } else {
                startHandler();
            }

        }
    };

    public SubCategoryViewModel() {
        mSubCatModelArrayList = new ArrayList<>();
        mSubCatData = new MutableLiveData<>();
        mDuplicateList = new ArrayList<>();
    }

    public void setContext(Context context, String id) {
        this.context = context;
        mSubCatRepository = SubCategoryRepository.getInstance(context);
        setSubCatData(id);
    }

    public void setDuplicateList(int position) {

        if (position != 0) {
            position = position + 1;
        }

        Log.i("scrollerPosition", "setDuplicateList: triggered position - " + position);
        ArrayList<SubCategoryModel> originalList = mSubCatModelArrayList;
        int temp = position + 10;

        if (mSubCatModelArrayList.size() <= temp) {
            for (; position < mSubCatModelArrayList.size(); position++) {
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

        mSubCatData.setValue(mDuplicateList);
    }

    private void startHandler() {
        mHandler.postDelayed(repeatativeCatTask, 100);
        isResponseDone = false;
    }

    private void stopHandler() {
        mHandler.removeCallbacks(repeatativeCatTask);
        isResponseDone = true;
    }

    public LiveData<ArrayList<SubCategoryModel>> getSubCatData() {
        Log.i("scrollerPosition", "getSubCatData: values called" + mDuplicateList.size());
        return mSubCatData;
    }

    public void setSubCatData(String id) {
        isResponseDone = false;
        mSubCatRepository.fetchSubCatData(id);
        repeatativeCatTask.run();
    }
}