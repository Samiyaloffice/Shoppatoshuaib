package com.shoppa.ui.PostBuyer;

import android.app.Activity;
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
import com.shoppa.Model.BuyerRequestModel;
import com.shoppa.Model.CountryModel;
import com.shoppa.R;
import com.shoppa.RepositoryManager.BuyerRequestLocationRepositry;
import com.shoppa.RepositoryManager.BuyerRequestRepository;
import com.shoppa.ui.ProductInquiry.InquiryFragment;

import java.util.ArrayList;

import static com.shoppa.ui.PostBuyer.PostBuyerFragment.dialog;
import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mBuyerUnitAdapter;
import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mCityAdapter;
import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mCountryAdapter;
import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mStateAdapter;
import static com.shoppa.ui.ProductInquiry.InquiryFragment.mInquiryUnitAdapter;
//import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mPostBuyerAllMemberRadioBtn;
//import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mPostBuyerBuyingFrequencyTxt;
//import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mPostBuyerMemberOnlyRadioBtn;
//import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mPostBuyerSelectUnit2Txt;
//import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mPostBuyerSelectUnitTxt;
//import static com.shoppa.ui.PostBuyer.PostBuyerFragment.mPostBuyerTimeOfValidityTxt;

public class PostBuyerViewModel extends ViewModel {

    public boolean isCountryResponseDone = false, isStateResponseDone = false, isCityResponseDone = false;
    Context context;
    Activity activity;
    ArrayList<CountryModel> mBuyerRequestCountry;
    ArrayList<CountryModel> mBuyerRequestState;
    ArrayList<CountryModel> mBuyerRequestCity;
    ArrayList<String> mBuyerUnitList;
    BuyerRequestRepository mBuyerRequestRepo;
    BuyerRequestLocationRepositry mBuyerRequestLocationRepo;
    Handler mCountryHandler = new Handler();
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

    /*private void setUpRadioButton() {
        mPostBuyerMemberOnlyRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostBuyerAllMemberRadioBtn.setChecked(false);
            }
        });
        mPostBuyerAllMemberRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostBuyerMemberOnlyRadioBtn.setChecked(false);
            }
        });
    }*/
    Handler mStateHandler = new Handler();
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
    Handler mCityHandler = new Handler();
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
    Handler mRequestHandler = new Handler();
    Runnable mRequestRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBuyerRequestRepo.isResponseDone) {
                stopPostHandler();
            } else {
                startPostHandler();
            }
        }
    };

    public PostBuyerViewModel() {

        /*setBuyerRequestCountry();
        setBuyerRequestState();
        setBuyerRequestCity();*/
    }

    private void setTimeValidityData() {

        /*mTimeValidityArrayList = new ArrayList<>();
        mTimeValidityArrayList.add("1 Week");
        mTimeValidityArrayList.add("2 Weeks");
        mTimeValidityArrayList.add("3 Weeks");
        mTimeValidityArrayList.add("1 Month");
        mTimeValidityArrayList.add("2 Months");
        mTimeValidityArrayList.add("3 Months");
        mTimeValidityArrayList.add("4 Months");
        mTimeValidityArrayList.add("5 Months");
        mTimeValidityArrayList.add("6 Months");
        mTimeValidityArrayList.add("1 Year");
*/
    }

    public void setContext(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        mBuyerRequestRepo = BuyerRequestRepository.getInstance(context);
        getBuyerRequestData();
        setBtns();

        /*setBuyerRequestCountry();
        setBuyerRequestState();
        setBuyerRequestCity();*/
    }

    public void getBuyerRequestData() {
        mBuyerRequestLocationRepo = BuyerRequestLocationRepositry.getInstance(context);
        mBuyerRequestLocationRepo.fetchBuyerCountry();
        mBuyerUnitList = mBuyerRequestLocationRepo.getUnitData().getValue();
        setUnit(mBuyerUnitList);
        isCountryResponseDone = false;
        isStateResponseDone = false;
        isCityResponseDone = false;
        mCountryRunnable.run();
        DataManager.showDialog(context, activity, dialog, "open");
    }
    private void setUnit(ArrayList<String> list) {
        mBuyerUnitAdapter = new DropDownUnitAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mBuyerUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PostBuyerFragment.mBuyerRequestUnit.setAdapter(mBuyerUnitAdapter);
        mBuyerUnitAdapter.notifyDataSetChanged();
    }

    private void startCountryHandler() {
        mCountryHandler.postDelayed(mCountryRunnable, 100);
    }

    private void stopCountryHandler() {
        mCountryHandler.removeCallbacks(mCountryRunnable);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    private void startStateHandler() {
        mStateHandler.postDelayed(mStateRunnable, 100);
    }

    private void stopStateHandler() {
        mStateHandler.removeCallbacks(mStateRunnable);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    private void stopCityHandler() {
        mCityHandler.removeCallbacks(mCityRunnable);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    private void startCityHandler() {
        mCityHandler.postDelayed(mCityRunnable, 100);
    }

    private void setBuyingFrequencyData() {
        /*mBuyingFrequencyArrayList = new ArrayList<>();

        mBuyingFrequencyArrayList.add("Day");
        mBuyingFrequencyArrayList.add("Quarter");
        mBuyingFrequencyArrayList.add("Week");
        mBuyingFrequencyArrayList.add("Month");
        mBuyingFrequencyArrayList.add("Year");*/
    }

    private void setSelectUnitData() {
        /*mSelectUnitArrayList = new ArrayList<>();

        mSelectUnitArrayList.add("Item 1");
        mSelectUnitArrayList.add("Item 2");
        mSelectUnitArrayList.add("Item 3");
        mSelectUnitArrayList.add("Item 4");
        mSelectUnitArrayList.add("Item 5");*/
    }

    private void setBtns() {

        onClick(PostBuyerFragment.mBuyerRequestCountry);
        onClick(PostBuyerFragment.mBuyerRequestState);
        onClick(PostBuyerFragment.mBuyerRequestCity);
        onClick(PostBuyerFragment.mBuyerRequestUnit);

        PostBuyerFragment.mBuyerRequestCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("locationData", "onCountryClick: position = " + position);
                PostBuyerFragment.mBuyerRequestCountry.setText(mBuyerRequestCountry.get(position).getCountryName());
                DataManager.userCountryName = mBuyerRequestCountry.get(position).getCountryName();
                DataManager.userCountryId = mBuyerRequestCountry.get(position).getCountryId();

                PostBuyerFragment.mBuyerRequestState.setText("");
                DataManager.userStateName = "";
                DataManager.userStateId = "";
                PostBuyerFragment.mBuyerRequestCity.setText("");
                DataManager.userCityName = "";
                DataManager.userCityId = "";

                mBuyerRequestLocationRepo.fetchBuyerState(mBuyerRequestCountry.get(position).getCountryId());
                mBuyerRequestLocationRepo.isStateResponseDone = false;
                mStateRunnable.run();
                DataManager.showDialog(context, activity, dialog, "open");

            }
        });

        PostBuyerFragment.mBuyerRequestState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostBuyerFragment.mBuyerRequestState.setText(mBuyerRequestState.get(position).getCountryName());
                DataManager.userStateName = mBuyerRequestState.get(position).getCountryName();
                DataManager.userStateId = mBuyerRequestState.get(position).getCountryId();

                PostBuyerFragment.mBuyerRequestCity.setText("");
                DataManager.userCityName = "";
                DataManager.userCityId = "";

                mBuyerRequestLocationRepo.fetchBuyerCity(mBuyerRequestState.get(position).getCountryId());
                mCityRunnable.run();
                DataManager.showDialog(context, activity, dialog, "open");
                mBuyerRequestLocationRepo.isCityResponseDone = false;

            }
        });

        PostBuyerFragment.mBuyerRequestCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostBuyerFragment.mBuyerRequestCity.setText(mBuyerRequestCity.get(position).getCountryName());
                DataManager.userCityName = mBuyerRequestCity.get(position).getCountryName();
                DataManager.userCityId = mBuyerRequestCity.get(position).getCountryId();
            }
        });

        PostBuyerFragment.mBuyerRequestUnit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostBuyerFragment.mBuyerRequestUnit.setText(mBuyerUnitList.get(position));
                DataManager.buyerUnit = mBuyerUnitList.get(position);
            }
        });


    }

    private void setBuyerRequestCountry(ArrayList<CountryModel> list) {
        mBuyerRequestCountry = list;
        Log.i("locationData", "setBuyerRequestCountry: list - " + list.size());
        mCountryAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PostBuyerFragment.mBuyerRequestCountry.setAdapter(mCountryAdapter);
        mCountryAdapter.notifyDataSetChanged();
    }

    private void setBuyerRequestState(ArrayList<CountryModel> list) {
        mBuyerRequestState = list;
        mStateAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PostBuyerFragment.mBuyerRequestState.setAdapter(mStateAdapter);
        mStateAdapter.notifyDataSetChanged();
    }

    private void setBuyerRequestCity(ArrayList<CountryModel> list) {
        mBuyerRequestCity = list;
        mCityAdapter = new DropDownAdapter(context, R.layout.support_simple_spinner_dropdown_item, list);
        mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PostBuyerFragment.mBuyerRequestCity.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();
    }

    public void postBuyerRequestData(BuyerRequestModel model) {
        mBuyerRequestRepo.postBuyerRequestData(model);
        mRequestRunnable.run();
        DataManager.showDialog(context, activity, dialog, "open");
    }

    private void startPostHandler() {
        mRequestHandler.postDelayed(mRequestRunnable, 100);
    }

    private void stopPostHandler() {
        mRequestHandler.removeCallbacks(mRequestRunnable);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    public void onClick(MaterialAutoCompleteTextView mV) {
        mV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mV.isPopupShowing()) {
                    mV.dismissDropDown();
                } else {
                    mV.showDropDown();
                }
            }
        });
    }
}