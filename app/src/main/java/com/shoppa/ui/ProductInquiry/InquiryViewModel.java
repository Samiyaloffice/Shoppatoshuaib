package com.shoppa.ui.ProductInquiry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.shoppa.Adapters.DropDownAdapter;
import com.shoppa.Adapters.DropDownUnitAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerEnquiryModel;
import com.shoppa.Model.CountryModel;
import com.shoppa.R;
import com.shoppa.RepositoryManager.BuyerEnquiryRepository;
import com.shoppa.RepositoryManager.BuyerRequestLocationRepositry;
import com.shoppa.ui.PostBuyer.PostBuyerFragment;

import java.util.ArrayList;

import static com.shoppa.ui.PostBuyer.PostBuyerFragment.dialog;
import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mCityAdapter;
import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mCountryAdapter;
import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mStateAdapter;
import static com.shoppa.ui.ProductInquiry.InquiryFragment.mInquiryCityAdapter;
import static com.shoppa.ui.ProductInquiry.InquiryFragment.mInquiryCountryAdapter;
import static com.shoppa.ui.ProductInquiry.InquiryFragment.mInquiryStateAdapter;
import static com.shoppa.ui.ProductInquiry.InquiryFragment.mInquiryUnitAdapter;

public class InquiryViewModel extends ViewModel {

    public boolean isResponseDone = false, isCountryResponseDone = false, isStateResponseDone = false, isCityResponseDone = false;
    Context context;

    ArrayList<CountryModel> mBuyerRequestCountry;
    ArrayList<CountryModel> mBuyerRequestState;
    ArrayList<CountryModel> mBuyerRequestCity;
    ArrayList<String> mUnitList;
    BuyerRequestLocationRepositry mBuyerRequestLocationRepo;

    BuyerEnquiryRepository mBuyerEnquiryRepo;
    Handler enquiryHandler = new Handler();
    Runnable enquiryRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBuyerEnquiryRepo.isResponseDone) {
                stopEnquiryHandler();
            } else {
                startEnquiryHandler();

            }
        }
    };
    Handler mCountryHandler = new Handler();
    Handler mStateHandler = new Handler();
    Handler mCityHandler = new Handler();
    private Activity activity;
    private Dialog dialog;
    Runnable mStateRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBuyerRequestLocationRepo.isStateResponseDone) {
                setBuyerRequestState(mBuyerRequestLocationRepo.getmBuyerStateData().getValue());
                isStateResponseDone = true;
                Log.i("locationData", "run: GotState");
                stopStateHandler();
            } else {
                startStateHandler();
            }
        }
    };
    Runnable mCountryRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBuyerRequestLocationRepo.isCountryResponseDone) {
                setBuyerRequestCountry(mBuyerRequestLocationRepo.getBuyerCountry().getValue());
                isCountryResponseDone = true;
                Log.i("locationData", "run: GotCountry");
                stopCountryHandler();
            } else {
                startCountryHandler();
            }
        }
    };
    Runnable mCityRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBuyerRequestLocationRepo.isCityResponseDone) {
                setBuyerRequestCity(mBuyerRequestLocationRepo.getmBuyerCityData().getValue());
                isCityResponseDone = true;
                Log.i("locationData", "run: GotCity");
                stopCityHandler();
            } else {
                startCityHandler();
            }
        }
    };

    private void startStateHandler() {
        mStateHandler.postDelayed(mStateRunnable, 100);
    }

    private void stopStateHandler() {
        mStateHandler.removeCallbacks(mStateRunnable);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    private void setBuyerRequestState(ArrayList<CountryModel> list) {
        mBuyerRequestState = list;
        mInquiryStateAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mInquiryStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        InquiryFragment.mInquiryBuyerState.setAdapter(mInquiryStateAdapter);
        mInquiryStateAdapter.notifyDataSetChanged();
    }

    private void setBtns() {

        onClick(InquiryFragment.mInquiryBuyerCountry);
        onClick(InquiryFragment.mInquiryBuyerState);
        onClick(InquiryFragment.mInquiryBuyerCity);
        onClick(InquiryFragment.mInquiryBuyerUnit);

        InquiryFragment.mInquiryBuyerCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("locationData", "onCountryClick: position = " + position);
                InquiryFragment.mInquiryBuyerCountry.setText(mBuyerRequestCountry.get(position).getCountryName());
                DataManager.userCountryName = mBuyerRequestCountry.get(position).getCountryName();
                DataManager.userCountryId = mBuyerRequestCountry.get(position).getCountryId();

                InquiryFragment.mInquiryBuyerCity.setText("");
                DataManager.userStateName = "";
                DataManager.userStateId = "";
                InquiryFragment.mInquiryBuyerState.setText("");
                DataManager.userCityName = "";
                DataManager.userCityId = "";

                mBuyerRequestLocationRepo.fetchBuyerState(mBuyerRequestCountry.get(position).getCountryId());
                mBuyerRequestLocationRepo.isStateResponseDone = false;
                mStateRunnable.run();
                DataManager.showDialog(context, activity, dialog, "open");

            }
        });

        InquiryFragment.mInquiryBuyerState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InquiryFragment.mInquiryBuyerState.setText(mBuyerRequestState.get(position).getCountryName());
                DataManager.userStateName = mBuyerRequestState.get(position).getCountryName();
                DataManager.userStateId = mBuyerRequestState.get(position).getCountryId();

                InquiryFragment.mInquiryBuyerCity.setText("");
                DataManager.userCityName = "";
                DataManager.userCityId = "";

                mBuyerRequestLocationRepo.fetchBuyerCity(mBuyerRequestState.get(position).getCountryId());
                mCityRunnable.run();
                DataManager.showDialog(context, activity, dialog, "open");
                mBuyerRequestLocationRepo.isCityResponseDone = false;

            }
        });

        InquiryFragment.mInquiryBuyerCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InquiryFragment.mInquiryBuyerCity.setText(mBuyerRequestCity.get(position).getCountryName());
                DataManager.userCityName = mBuyerRequestCity.get(position).getCountryName();
                DataManager.userCityId = mBuyerRequestCity.get(position).getCountryId();
            }
        });

        InquiryFragment.mInquiryBuyerUnit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InquiryFragment.mInquiryBuyerUnit.setText(mUnitList.get(position));
                DataManager.buyerUnit = mUnitList.get(position);
            }
        });

    }

    private void stopCityHandler() {
        mCityHandler.removeCallbacks(mCityRunnable);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    private void startCityHandler() {
        mCityHandler.postDelayed(mCityRunnable, 100);
    }

    private void setBuyerRequestCity(ArrayList<CountryModel> list) {
        mBuyerRequestCity = list;
        Log.i("InquiryData", "setBuyerRequestCity: city list - "+list);
        mInquiryCityAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mInquiryCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        InquiryFragment.mInquiryBuyerCity.setAdapter(mInquiryCityAdapter);
        mInquiryCityAdapter.notifyDataSetChanged();
    }

    private void setUnit(ArrayList<String> list) {
        mInquiryUnitAdapter = new DropDownUnitAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mInquiryUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        InquiryFragment.mInquiryBuyerUnit.setAdapter(mInquiryUnitAdapter);
        mInquiryUnitAdapter.notifyDataSetChanged();
    }


    private void onClick(MaterialAutoCompleteTextView mBuyerRequestCountry) {
        mBuyerRequestCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBuyerRequestCountry.isPopupShowing()) {
                    mBuyerRequestCountry.dismissDropDown();
                } else {
                    mBuyerRequestCountry.showDropDown();
                }
            }
        });
    }

    private void startCountryHandler() {
        mCountryHandler.postDelayed(mCountryRunnable, 100);
    }

    private void stopCountryHandler() {
        mCountryHandler.removeCallbacks(mCountryRunnable);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    private void setBuyerRequestCountry(ArrayList<CountryModel> list) {
        mBuyerRequestCountry = list;
        Log.i("locationData", "setBuyerRequestCountry: list - " + list.size());
        mInquiryCountryAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mInquiryCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        InquiryFragment.mInquiryBuyerCountry.setAdapter(mInquiryCountryAdapter);
        mInquiryCountryAdapter.notifyDataSetChanged();
    }

    public void setContext(Context context, Activity activity, Dialog dialog) {
        this.context = context;
        this.activity = activity;
        this.dialog = dialog;
        getBuyerRequestData();
    }

    public void postInquiryData(BuyerEnquiryModel model) {
        mBuyerEnquiryRepo = BuyerEnquiryRepository.getInstance(context);
        mBuyerEnquiryRepo.postEnquiryData(model);
        enquiryRunnable.run();
    }

    public void getBuyerRequestData() {
        setBtns();
        mBuyerRequestLocationRepo = BuyerRequestLocationRepositry.getInstance(context);
        mBuyerRequestLocationRepo.fetchBuyerCountry();
        mUnitList = mBuyerRequestLocationRepo.getUnitData().getValue();
        setUnit(mUnitList);
        isCountryResponseDone = false;
        isStateResponseDone = false;
        isCityResponseDone = false;
        mCountryRunnable.run();
        DataManager.showDialog(context, activity, dialog, "open");
    }

    private void stopEnquiryHandler() {
        enquiryHandler.removeCallbacks(enquiryRunnable);
        isResponseDone = true;
    }

    private void startEnquiryHandler() {
        enquiryHandler.postDelayed(enquiryRunnable, 100);
        isResponseDone = false;
    }
}