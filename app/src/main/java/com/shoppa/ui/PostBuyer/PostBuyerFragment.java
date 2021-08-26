package com.shoppa.ui.PostBuyer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.DropDownAdapter;
import com.shoppa.Adapters.DropDownUnitAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerRequestModel;
import com.shoppa.R;
import com.shoppa.RepositoryManager.BuyerRequestRepository;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.WebView.WebViewFragment;

public class PostBuyerFragment extends Fragment {

    //    public static RadioButton mPostBuyerMemberOnlyRadioBtn, mPostBuyerAllMemberRadioBtn;
//    public static MaterialAutoCompleteTextView mPostBuyerTimeOfValidityTxt, mPostBuyerBuyingFrequencyTxt, mPostBuyerSelectUnitTxt, mPostBuyerSelectUnit2Txt;
    public static DropDownAdapter mCountryAdapter, mStateAdapter, mCityAdapter;
    public static DropDownUnitAdapter mBuyerUnitAdapter;
    public static TextInputLayout mBuyerRequestName, mBuyerRequestEmail, mBuyerRequestMobile, mBuyerRequestProduct, mBuyerRequestQuantity, mBuyerRequestComments;
    public static MaterialAutoCompleteTextView mBuyerRequestCity, mBuyerRequestState, mBuyerRequestCountry, mBuyerRequestUnit;
    public static Dialog dialog;
    MaterialTextView mPostBuyerTermsBtn;
    CheckBox mBuyerRequestCheckBox;
    BuyerRequestRepository mBuyerRequestRepo;
    BuyerRequestModel mBuyerRequestModel;
    MaterialButton mBuyerRequestPostBtn;
    private PostBuyerViewModel mViewModel;
    private FloatingActionButton mFABHomeBtn;

    public static PostBuyerFragment newInstance() {
        return new PostBuyerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.post_buyer_fragment, container, false);
        dialog = new Dialog(requireContext());
        mFABHomeBtn = root.findViewById(R.id.post_buyer_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });
        /*mPostBuyerAllMemberRadioBtn = root.findViewById(R.id.post_buyer_all_member_radio_btn);
        mPostBuyerMemberOnlyRadioBtn = root.findViewById(R.id.post_buyer_member_only_radio_btn);
        mPostBuyerTimeOfValidityTxt = root.findViewById(R.id.post_buyer_time_of_validity_txt);
        mPostBuyerBuyingFrequencyTxt = root.findViewById(R.id.post_buyer_buying_frequency_txt);
        mPostBuyerSelectUnitTxt = root.findViewById(R.id.post_buyer_select_unit_txt);
        mPostBuyerSelectUnit2Txt = root.findViewById(R.id.post_buyer_select_unit2_txt);*/
        mBuyerRequestPostBtn = root.findViewById(R.id.buyer_request_post_btn);
        mBuyerRequestPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBuyerRequest();
            }
        });
        mBuyerRequestCheckBox = root.findViewById(R.id.buyer_request_terms);
        mBuyerRequestName = root.findViewById(R.id.buyer_request_name);
        mBuyerRequestEmail = root.findViewById(R.id.buyer_request_email);
        mBuyerRequestMobile = root.findViewById(R.id.buyer_request_mobile);
        mBuyerRequestProduct = root.findViewById(R.id.buyer_request_product);
        mBuyerRequestQuantity = root.findViewById(R.id.buyer_request_quality);
        mBuyerRequestUnit = root.findViewById(R.id.buyer_request_unit);
        mBuyerRequestComments = root.findViewById(R.id.buyer_request_comment);

        mBuyerRequestCountry = root.findViewById(R.id.buyer_request_country);
        mBuyerRequestState = root.findViewById(R.id.buyer_request_state);
        mBuyerRequestCity = root.findViewById(R.id.buyer_request_city);

        mPostBuyerTermsBtn = root.findViewById(R.id.post_buyer_fragment_terms_btn);
        mPostBuyerTermsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTermsFragment();
            }
        });

        mViewModel = new ViewModelProvider(this).get(PostBuyerViewModel.class);
        mViewModel.setContext(getContext(), requireActivity());

        return root;
    }

    private void dashboardFragment() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        DataManager.isFrom = "";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, dashboardFragment);
        transaction.addToBackStack("HomeFragmentAdded");
        transaction.commit();


    }

    private void postBuyerRequest() {
        if (checkData()) {
            mViewModel.postBuyerRequestData(getBuyerRequestData());
        }
    }

    private BuyerRequestModel getBuyerRequestData() {

        mBuyerRequestModel = new BuyerRequestModel(mBuyerRequestName.getEditText().getText().toString(),
                mBuyerRequestEmail.getEditText().getText().toString(),
                mBuyerRequestMobile.getEditText().getText().toString(),
                DataManager.userCountryId,
                DataManager.userStateId,
                DataManager.userCityName,
                mBuyerRequestProduct.getEditText().getText().toString(),
                mBuyerRequestQuantity.getEditText().getText().toString(),
                mBuyerRequestUnit.getText().toString(),
                mBuyerRequestComments.getEditText().getText().toString(),
                "Application");

        return mBuyerRequestModel;

    }

    private boolean checkData() {

        boolean isDataAvailable = true;

        if (mBuyerRequestName.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestName.setErrorEnabled(true);
            mBuyerRequestName.setError("This option is mandatory");
        }
        if (mBuyerRequestEmail.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestEmail.setErrorEnabled(true);
            mBuyerRequestEmail.setError("This option is mandatory");
        }
        if (!mBuyerRequestEmail.getEditText().getText().toString().matches("") && !DataManager.isEmailValid(mBuyerRequestEmail.getEditText().getText().toString())) {
            isDataAvailable = false;
            mBuyerRequestEmail.setErrorEnabled(true);
            mBuyerRequestEmail.setError("Please enter Valid Email");
        }
        if (mBuyerRequestMobile.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestMobile.setErrorEnabled(true);
            mBuyerRequestMobile.setError("This option is mandatory");
        }
        if (!mBuyerRequestMobile.getEditText().getText().toString().matches("") && mBuyerRequestMobile.getEditText().getText().toString().length() < 10) {
            isDataAvailable = false;
            mBuyerRequestMobile.setErrorEnabled(true);
            mBuyerRequestMobile.setError("Please enter valid number");
        }
        if (mBuyerRequestCountry.getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestCountry.setError("This option is mandatory");
        }
        if (mBuyerRequestState.getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestState.setError("This option is mandatory");
        }
        if (mBuyerRequestCity.getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestCity.setError("This option is mandatory");
        }
        if (mBuyerRequestProduct.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestProduct.setErrorEnabled(true);
            mBuyerRequestProduct.setError("This option is mandatory");
        }
        if (mBuyerRequestQuantity.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestQuantity.setErrorEnabled(true);
            mBuyerRequestQuantity.setError("This option is mandatory");
        }
        if (mBuyerRequestUnit.getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestUnit.setError("This option is mandatory");
        }
        if (mBuyerRequestComments.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mBuyerRequestComments.setErrorEnabled(true);
            mBuyerRequestComments.setError("This option is mandatory");
        }
        if (!mBuyerRequestCheckBox.isChecked()) {
            isDataAvailable = false;
            mBuyerRequestCheckBox.setError("This option is mandatory");
        }

        return isDataAvailable;

    }

    private void openTermsFragment() {
        WebViewFragment fragment = new WebViewFragment();
        fragment.Url = "https://www.shoppa.in/terms-conditins";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack("WebViewFragemntAdded").commit();
    }
}