package com.shoppa.ui.ProductInquiry;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.DropDownAdapter;
import com.shoppa.Adapters.DropDownUnitAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerEnquiryModel;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.WebView.WebViewFragment;

public class InquiryFragment extends Fragment {

    public static MaterialAutoCompleteTextView mInquiryBuyerCity,
            mInquiryBuyerUnit,
            mInquiryBuyerState,
            mInquiryBuyerCountry;
    public static DropDownAdapter mInquiryCountryAdapter, mInquiryStateAdapter, mInquiryCityAdapter;
    public static DropDownUnitAdapter mInquiryUnitAdapter;
    public ProductDetailModel mProductDetailModel;
    public SellerDetailModel mSellerDetailModel;
    MaterialTextView mContactShoppaTermsBtn, mInquiryProductName, mInquiryCompanyName;
    BuyerEnquiryModel mBuyerEnquiryModel;
    TextInputLayout mInquiryBuyerName,
            mInquiryBuyerEmail,
            mInquiryBuyerAddress,
            mInquiryBuyerMobile,
            mInquiryBuyerProduct,
            mInquiryBuyerQuantity,
            mInquiryBuyerDescription,
            mInquiryBuyerSellerName;
    MaterialButton mInquirySendBtn;
    CheckBox mInquiryTermsBtn;
    ShapeableImageView mInquiryProductImg;
    Dialog dialog;
    Handler enquiryHandler = new Handler();
    private InquiryViewModel mViewModel;
    Runnable enquiryRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {

                blankData();

                stopEnquiryHandler();
            } else {
                startEnquiryHandler();
            }
        }
    };
    private FloatingActionButton mFABHomeBtn;

    public static InquiryFragment newInstance() {
        return new InquiryFragment();
    }

    private void blankData() {

        mInquiryBuyerCity.setText("");
        mInquiryBuyerUnit.setText("");
        mInquiryBuyerState.setText("");
        mInquiryBuyerCountry.setText("");

        mInquiryBuyerName.getEditText().setText("");
        mInquiryBuyerEmail.getEditText().setText("");
        mInquiryBuyerAddress.getEditText().setText("");
        mInquiryBuyerMobile.getEditText().setText("");
        mInquiryBuyerQuantity.getEditText().setText("");
        mInquiryBuyerDescription.getEditText().setText("");
        mInquiryBuyerSellerName.getEditText().setText("");

        mInquiryTermsBtn.setChecked(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.contact_supplier_fragment, container, false);

        mFABHomeBtn = root.findViewById(R.id.contact_sup_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });

        mViewModel = new ViewModelProvider(this).get(InquiryViewModel.class);
        dialog = new Dialog(requireContext());
        mInquirySendBtn = root.findViewById(R.id.inquiry_send_btn);
        mInquirySendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        mInquiryTermsBtn = root.findViewById(R.id.inquiry_terms_btn);
        mInquiryProductImg = root.findViewById(R.id.inquiry_product_img);
        mInquiryProductName = root.findViewById(R.id.inquiry_product_name);
        mInquiryCompanyName = root.findViewById(R.id.inquiry_company_name);
        mInquiryBuyerName = root.findViewById(R.id.inquiry_buyer_name);
        mInquiryBuyerEmail = root.findViewById(R.id.inquiry_buyer_email);
        mInquiryBuyerAddress = root.findViewById(R.id.inquiry_buyer_address);
        mInquiryBuyerMobile = root.findViewById(R.id.inquiry_buyer_mobile);
        mInquiryBuyerCity = root.findViewById(R.id.inquiry_buyer_city);
        mInquiryBuyerState = root.findViewById(R.id.inquiry_buyer_state);
        mInquiryBuyerCountry = root.findViewById(R.id.inquiry_buyer_country);
        mInquiryBuyerProduct = root.findViewById(R.id.inquiry_buyer_product);
        mInquiryBuyerQuantity = root.findViewById(R.id.inquiry_buyer_quantity);
        mInquiryBuyerUnit = root.findViewById(R.id.inquiry_buyer_unit);
        mInquiryBuyerDescription = root.findViewById(R.id.inquiry_buyer_description);
        mInquiryBuyerSellerName = root.findViewById(R.id.inquiry_buyer_seller_name);


        mContactShoppaTermsBtn = root.findViewById(R.id.contact_shoppa_terms_btn);
        mContactShoppaTermsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTermsFragment();
            }
        });
        mViewModel.setContext(requireContext(), requireActivity(), dialog);
        setEnquiryData();

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

    private void setEnquiryData() {

        mInquiryProductName.setText(mProductDetailModel.getProductName());

        Glide.with(requireContext()).load(mProductDetailModel.getProductImage()).error(R.drawable.no_image_found).into(mInquiryProductImg);
        mInquiryCompanyName.setText(mSellerDetailModel.getCompanyName());

        mInquiryBuyerAddress.getEditText().setText(DataManager.userAddress);
        mInquiryBuyerProduct.getEditText().setText(mProductDetailModel.getProductName());
        mInquiryBuyerSellerName.getEditText().setText(mSellerDetailModel.getSellerName());

    }

    private void sendData() {
        if (checkData()) {
            mViewModel.postInquiryData(fillData());
            enquiryRunnable.run();
            DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        }
    }

    private void stopEnquiryHandler() {
        enquiryHandler.removeCallbacks(enquiryRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void startEnquiryHandler() {
        enquiryHandler.postDelayed(enquiryRunnable, 100);
    }


    private BuyerEnquiryModel fillData() {
        mBuyerEnquiryModel = new BuyerEnquiryModel(mInquiryBuyerName.getEditText().getText().toString(),
                mInquiryBuyerEmail.getEditText().getText().toString(),
                mInquiryBuyerAddress.getEditText().getText().toString(),
                mInquiryBuyerMobile.getEditText().getText().toString(),
                mInquiryBuyerCity.getText().toString(),
                DataManager.userStateId,
                DataManager.userCountryId,
                mInquiryBuyerProduct.getEditText().getText().toString(),
                mInquiryBuyerQuantity.getEditText().getText().toString(),
                mInquiryBuyerUnit.getText().toString(),
                mInquiryBuyerDescription.getEditText().getText().toString(),
                mInquiryBuyerSellerName.getEditText().getText().toString(),
                "Application");

        return mBuyerEnquiryModel;
    }

    private boolean checkData() {

        boolean isDataAvailable = true;
        if (mInquiryBuyerName.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerName.setErrorEnabled(true);
            mInquiryBuyerName.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerEmail.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerEmail.setErrorEnabled(true);
            mInquiryBuyerEmail.setError("This Option is Mandatory");
        }
        if (!mInquiryBuyerEmail.getEditText().getText().toString().matches("") && !DataManager.isEmailValid(mInquiryBuyerEmail.getEditText().getText().toString())) {
            isDataAvailable = false;
            mInquiryBuyerEmail.setErrorEnabled(true);
            mInquiryBuyerEmail.setError("Enter a Valid Email");
        }
        if (mInquiryBuyerAddress.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerAddress.setErrorEnabled(true);
            mInquiryBuyerAddress.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerMobile.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerMobile.setErrorEnabled(true);
            mInquiryBuyerMobile.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerMobile.getEditText().getText().toString().length() < 10 && !mInquiryBuyerMobile.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerMobile.setErrorEnabled(true);
            mInquiryBuyerMobile.setError("Enter Valid number");
        }
        if (mInquiryBuyerCity.getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerCity.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerState.getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerState.setError("This Option is Mandatory");
        }

        if (mInquiryBuyerCountry.getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerCountry.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerProduct.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerProduct.setErrorEnabled(true);
            mInquiryBuyerProduct.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerQuantity.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerQuantity.setErrorEnabled(true);
            mInquiryBuyerQuantity.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerUnit.getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerUnit.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerDescription.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerDescription.setErrorEnabled(true);
            mInquiryBuyerDescription.setError("This Option is Mandatory");
        }
        if (mInquiryBuyerSellerName.getEditText().getText().toString().matches("")) {
            isDataAvailable = false;
            mInquiryBuyerSellerName.setErrorEnabled(true);
            mInquiryBuyerSellerName.setError("This Option is Mandatory");
        }
        if (!mInquiryTermsBtn.isChecked()) {
            isDataAvailable = false;
            mInquiryTermsBtn.setError("Please Check this option");
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