package com.shoppa.ui.Package;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.PackageModel;
import com.shoppa.RepositoryManager.PackageRepository;

import java.util.ArrayList;

public class PackagesViewModel extends ViewModel {

    public boolean isResponseDone = false;
    Context context;
    PackageRepository mPackageRepo;
    ArrayList<PackageModel> mPackageArrayList;
    MutableLiveData<ArrayList<PackageModel>> mPackageData;
    Handler packageHandler = new Handler();
    Runnable packageRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPackageRepo.isResponseDone) {
                mPackageArrayList = new ArrayList<>();
                mPackageArrayList = mPackageRepo.getPackageData().getValue();
                Log.i("PackageData", "run: dataLength = " + mPackageArrayList.size());
                isResponseDone = true;
                stopPackageHandler();
            } else {
                isResponseDone = false;
                startPackageHandler();
            }
        }
    };

    public void setContext(Context context) {
        this.context = context;
        setPackageData();
    }

    public void setPackageData() {
        isResponseDone = false;
        mPackageRepo = PackageRepository.getInstance(context);
        mPackageRepo.fetchPackageData();
        packageRunnable.run();
    }

    private void startPackageHandler() {
        packageHandler.postDelayed(packageRunnable, 100);
    }

    private void stopPackageHandler() {
        packageHandler.removeCallbacks(packageRunnable);
    }

    public LiveData<ArrayList<PackageModel>> getPackageData() {
        mPackageData = new MutableLiveData<>();
        mPackageData.setValue(mPackageArrayList);
        return mPackageData;
    }
}