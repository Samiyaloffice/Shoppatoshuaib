package com.shoppa.ui.TradeAlerts;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.PackageModel;
import com.shoppa.Model.TradeAlertModel;
import com.shoppa.RepositoryManager.SinglePackageRepository;
import com.shoppa.RepositoryManager.TradeAlertRepository;

import java.util.ArrayList;

public class TradeAlertsViewModel extends ViewModel {

    public boolean isResponseDone = false, isPackageResponseDone = false;
    TradeAlertRepository mTradeAlertRepo;
    SinglePackageRepository mSinglePackageRepo;
    ArrayList<TradeAlertModel> mTradeAlertArrayList;
    MutableLiveData<ArrayList<TradeAlertModel>> mData;
    PackageModel mPackageModel;
    MutableLiveData<PackageModel> mPackageData;
    Handler tradeHandler = new Handler();
    Runnable tradeRunnable = new Runnable() {
        @Override
        public void run() {
            if (mTradeAlertRepo.isResponseDone) {
                mTradeAlertArrayList = new ArrayList<>();
                mTradeAlertArrayList = mTradeAlertRepo.getTradeAlert().getValue();
                stopTradeHandler();
            } else {
                startTradeHandler();
            }
        }
    };
    Handler mPackageHandler = new Handler();
    Runnable mPackageRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSinglePackageRepo.isResponseDone) {
                mPackageModel = mSinglePackageRepo.getSinglePackage().getValue();
                stopPackageHandler();
            } else {
                startPackageHandler();
            }
        }
    };
    private Context context;

    /*MutableLiveData<ArrayList<String>> data;
    ArrayList<String> mTradeAlertArrayList;
*/
    public TradeAlertsViewModel() {
        /*mTradeAlertArrayList = new ArrayList<>();
        data = new MutableLiveData<>();
        data.setValue(mTradeAlertArrayList);*/
    }

    private void startPackageHandler() {
        mPackageHandler.postDelayed(mPackageRunnable, 100);
        isPackageResponseDone = false;
    }

    private void stopPackageHandler() {
        mPackageHandler.removeCallbacks(mPackageRunnable);
        isPackageResponseDone = true;
    }

    private void startTradeHandler() {
        tradeHandler.postDelayed(tradeRunnable, 100);
        isResponseDone = false;
    }

    private void stopTradeHandler() {
        tradeHandler.removeCallbacks(tradeRunnable);
        isResponseDone = true;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void fetchTradeAlert() {
        isResponseDone = false;
        mTradeAlertRepo = TradeAlertRepository.getInstance(context);
        mTradeAlertRepo.fetchTradeAlert();
        tradeRunnable.run();
    }

    public LiveData<ArrayList<TradeAlertModel>> getTradeAlertData() {
        mData = new MutableLiveData<>();
        mData.setValue(mTradeAlertArrayList);
        return mData;
    }

    public void fetchPackageData(String packageName) {
        isPackageResponseDone = false;
        mSinglePackageRepo = SinglePackageRepository.getInstance(context);
        mSinglePackageRepo.fetchPackage(packageName);
        mPackageRunnable.run();
    }

    public LiveData<PackageModel> getPackageData() {
        mPackageData = new MutableLiveData<>();
        mPackageData.setValue(mPackageModel);
        return mPackageData;
    }

    /*public void setTradeAlertData(String data) {
        mTradeAlertArrayList.add(data);
        this.data.setValue(mTradeAlertArrayList);
        Toast.makeText(context, "DataUpdated" + data, Toast.LENGTH_SHORT).show();
    }*/

    /*public LiveData<ArrayList<String>> getTradeData() {
        return data;
    }*/

    /*public void removeElement(int position) {
        mTradeAlertArrayList.remove(position);
        this.data.setValue(mTradeAlertArrayList);
    }*/

}