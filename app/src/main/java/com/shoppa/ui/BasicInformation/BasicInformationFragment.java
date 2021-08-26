package com.shoppa.ui.BasicInformation;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BasicDetailModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;

public class BasicInformationFragment extends Fragment implements View.OnClickListener {

    public static ShapeableImageView mBasicInfoHeaderImg, mBasicInfoBodyImg;
    public static FloatingActionButton mBasicInfoEditBtn;
    public static TextInputLayout mBasicInfoNumber,
            mBasicInfoAddress,
            mBasicInfoType,
            mBasicInfoEmail,
            mBasicInfoName,
            mBasicInfoCompany;
    public static ScrollView mBasicInfoScrollView;
    public static RadioButton mBasicInfoMaleBtn, mBasicInfoFemaleBtn;
    public static MaterialButton mBasicInfoSubmitBtn;
    boolean editEnabled = false;
    BasicDetailModel model;
    Dialog dialog;
    Handler mDetailHandle = new Handler();
    private BasicInformationViewModel mViewModel;
    Runnable mDetailRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                stopDetailHandler();
            } else {
                startDetailHandler();
            }
        }
    };

    public static BasicInformationFragment newInstance() {
        return new BasicInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.basic_information_fragment, container, false);

        mBasicInfoHeaderImg = root.findViewById(R.id.basic_info_header_img);
        mBasicInfoBodyImg = root.findViewById(R.id.basic_info_body_img);
        dialog = new Dialog(requireContext());
        init(root);

        mBasicInfoNumber = root.findViewById(R.id.basic_info_mobile);
        mBasicInfoNumber.getEditText().setText(UserDataModel.mInstance.getSl_number());

        mBasicInfoAddress = root.findViewById(R.id.basic_info_address);
        mBasicInfoAddress.getEditText().setText(UserDataModel.mInstance.getSeller_address());

        mBasicInfoType = root.findViewById(R.id.basic_info_type);
        mBasicInfoType.getEditText().setText(UserDataModel.mInstance.getSeller_type());

        mBasicInfoEmail = root.findViewById(R.id.basic_info_email);
        mBasicInfoEmail.getEditText().setText(UserDataModel.mInstance.getSeller_email());

        mBasicInfoName = root.findViewById(R.id.basic_info_NAME);
        mBasicInfoName.getEditText().setText(UserDataModel.mInstance.getSeller_name());

        mBasicInfoCompany = root.findViewById(R.id.basic_info_company);
        mBasicInfoCompany.getEditText().setText(UserDataModel.mInstance.getSeller_company());

        mViewModel = new ViewModelProvider(this).get(BasicInformationViewModel.class);
        mViewModel.disableAllTxt();
        return root;
    }

    private void init(View root) {
        mBasicInfoEditBtn = root.findViewById(R.id.basic_info_edit_btn);
        mBasicInfoEditBtn.setOnClickListener(this);

        mBasicInfoSubmitBtn = root.findViewById(R.id.basic_info_submit_btn);
        mBasicInfoSubmitBtn.setOnClickListener(this);

        mBasicInfoScrollView = root.findViewById(R.id.basic_info_scroll_view);

    }

    @Override
    public void onClick(View v) {
        if (v == mBasicInfoEditBtn) {
            mViewModel.enableAllTxt();
        } else if (v == mBasicInfoSubmitBtn) {
            mViewModel.disableAllTxt();
            mViewModel.postBasicDetails(fillData());
            mDetailRunnable.run();
            DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        }
    }

    private void stopDetailHandler() {
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
        mDetailHandle.removeCallbacks(mDetailRunnable);
        DataManager.refreshUser(requireContext());
    }

    private void startDetailHandler() {
        mDetailHandle.postDelayed(mDetailRunnable, 100);
    }

    private BasicDetailModel fillData() {

        model = new BasicDetailModel(mBasicInfoNumber.getEditText().getText().toString(),
                mBasicInfoAddress.getEditText().getText().toString(),
                mBasicInfoType.getEditText().getText().toString(),
                mBasicInfoEmail.getEditText().getText().toString(),
                mBasicInfoName.getEditText().getText().toString(),
                mBasicInfoCompany.getEditText().getText().toString());

        return model;
    }


}