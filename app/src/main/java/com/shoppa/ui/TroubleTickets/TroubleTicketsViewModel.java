package com.shoppa.ui.TroubleTickets;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shoppa.Model.ReportModel;
import com.shoppa.Model.TroubleTicketModel;
import com.shoppa.RepositoryManager.ReportRepository;

import java.util.ArrayList;

public class TroubleTicketsViewModel extends ViewModel {

    public boolean isResponseDone = false, isReportResponseDone = false;
    public ArrayList<TroubleTicketModel> mTroubleTicketModelArrayList;
    public MutableLiveData<ArrayList<TroubleTicketModel>> data;
    Context context;
    ReportRepository mReportRepo;
    Handler mReportHandler = new Handler();
    Runnable mReportRunnable = new Runnable() {
        @Override
        public void run() {
            if (mReportRepo.isResponseDone) {
                isResponseDone = true;
                stopReportHandler();
            } else {
                startReportHandler();
            }
        }
    };
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mReportRepo.isReportResponseDone) {
                isReportResponseDone = true;
                mTroubleTicketModelArrayList = mReportRepo.getTroubleTickets().getValue();
                stopHandler();
            } else {
                startHandler();
            }
        }
    };

    public TroubleTicketsViewModel() {
//        mTroubleTicketModelArrayList = new ArrayList<>();
//        data= new MutableLiveData<>();
//        data.setValue(mTroubleTicketModelArrayList);

    }

    private void startHandler() {
        mHandler.postDelayed(mRunnable, 100);
    }

    private void stopHandler() {
        mHandler.removeCallbacks(mRunnable);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void postReportData(ReportModel model) {
        mReportRepo = ReportRepository.getInstance(context);
        mReportRepo.postUserReport(model);
        isResponseDone = false;
        mReportRunnable.run();
    }

    private void stopReportHandler() {
        mReportHandler.removeCallbacks(mReportRunnable);
    }

    private void startReportHandler() {
        mReportHandler.postDelayed(mReportRunnable, 100);
    }

    public void fetchTroubleTickets() {
        mReportRepo = ReportRepository.getInstance(context);
        mReportRepo.fetchTroubleTicket();
        mRunnable.run();
        isReportResponseDone = false;
    }

    public LiveData<ArrayList<TroubleTicketModel>> getTroubleTickets() {
        data = new MutableLiveData<>();
        data.setValue(mTroubleTicketModelArrayList);
        return data;
    }

/*    public void setTroubleTicketData(TroubleTicketModel data) {
        mTroubleTicketModelArrayList.add(data);
        this.data.setValue(mTroubleTicketModelArrayList);

    }

    public LiveData<ArrayList<TroubleTicketModel>> getTroubleTicketData() {
        return data;
    }

    public void removeElement(int position) {
        mTroubleTicketModelArrayList.remove(position);
        this.data.setValue(mTroubleTicketModelArrayList);
    }*/
}