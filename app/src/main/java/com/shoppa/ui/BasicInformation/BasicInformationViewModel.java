package com.shoppa.ui.BasicInformation;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.google.android.material.shape.CornerFamily;
import com.shoppa.Model.BasicDetailModel;
import com.shoppa.RepositoryManager.BasicDetailsRepository;

import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoAddress;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoBodyImg;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoCompany;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoEditBtn;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoEmail;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoFemaleBtn;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoHeaderImg;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoMaleBtn;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoName;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoNumber;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoScrollView;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoSubmitBtn;
import static com.shoppa.ui.BasicInformation.BasicInformationFragment.mBasicInfoType;


public class BasicInformationViewModel extends ViewModel implements View.OnClickListener {

    public boolean isResponseDone = false;
    BasicDetailsRepository mBasicDetailRepo;
    Context context;
    Handler mDetailHandler = new Handler();
    Runnable mDetailRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBasicDetailRepo.isResponseDone) {
                stopDetailHandler();
            } else {
                startDetailHandler();
            }
        }
    };

    public BasicInformationViewModel() {

        disableAllTxt();
        validateRadioBtns();
        setViewProperties();
        setScrollerProperty();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void setScrollerProperty() {
        mBasicInfoScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                int a = 200;

                if (scrollY < a) {
                    mBasicInfoEditBtn.show();
                } else {
                    mBasicInfoEditBtn.hide();
                }

            }//334
        });

    }

    private void setViewProperties() {
        float corner = 130f;

        mBasicInfoHeaderImg.setShapeAppearanceModel(mBasicInfoHeaderImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());

        mBasicInfoBodyImg.setShapeAppearanceModel(mBasicInfoBodyImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, corner).build());
    }

    private void validateRadioBtns() {

        /*mBasicInfoMaleBtn.setOnClickListener(this);
        mBasicInfoFemaleBtn.setOnClickListener(this);*/

    }

    public void disableAllTxt() {

        mBasicInfoName.getEditText().setFocusableInTouchMode(false);
        mBasicInfoName.getEditText().setFocusable(false);

        mBasicInfoAddress.getEditText().setFocusableInTouchMode(false);
        mBasicInfoAddress.getEditText().setFocusable(false);

        mBasicInfoCompany.getEditText().setFocusableInTouchMode(false);
        mBasicInfoCompany.getEditText().setFocusable(false);

        /*mBasicInfoMaleBtn.setClickable(false);

        mBasicInfoFemaleBtn.setClickable(false);*/

        mBasicInfoType.getEditText().setFocusableInTouchMode(false);
        mBasicInfoType.getEditText().setFocusable(false);

        mBasicInfoEmail.getEditText().setFocusableInTouchMode(false);
        mBasicInfoEmail.getEditText().setFocusable(false);

        mBasicInfoNumber.getEditText().setFocusableInTouchMode(false);
        mBasicInfoNumber.getEditText().setFocusable(false);

        mBasicInfoSubmitBtn.setVisibility(View.GONE);

    }

    public void enableAllTxt() {

        mBasicInfoName.getEditText().setFocusableInTouchMode(true);
        mBasicInfoName.getEditText().setFocusable(true);

        mBasicInfoAddress.getEditText().setFocusableInTouchMode(true);
        mBasicInfoAddress.getEditText().setFocusable(true);

        mBasicInfoCompany.getEditText().setFocusableInTouchMode(true);
        mBasicInfoCompany.getEditText().setFocusable(true);

        /*mBasicInfoMaleBtn.setClickable(true);

        mBasicInfoFemaleBtn.setClickable(true);*/

        mBasicInfoType.getEditText().setFocusableInTouchMode(true);
        mBasicInfoType.getEditText().setFocusable(true);

        mBasicInfoEmail.getEditText().setFocusableInTouchMode(true);
        mBasicInfoEmail.getEditText().setFocusable(true);

        mBasicInfoNumber.getEditText().setFocusableInTouchMode(true);
        mBasicInfoNumber.getEditText().setFocusable(true);

        mBasicInfoSubmitBtn.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        if (v == mBasicInfoMaleBtn) {
            mBasicInfoFemaleBtn.setChecked(false);
        } else if (v == mBasicInfoFemaleBtn) {
            mBasicInfoMaleBtn.setChecked(false);
        }
    }

    public void postBasicDetails(BasicDetailModel model) {

        mBasicDetailRepo = BasicDetailsRepository.getInstance(context);
        mBasicDetailRepo.postUserDetails(model);
        isResponseDone = false;
        mDetailRunnable.run();

    }

    private void startDetailHandler() {
        mDetailHandler.postDelayed(mDetailRunnable, 100);
    }

    private void stopDetailHandler() {
        mDetailHandler.removeCallbacks(mDetailRunnable);
        isResponseDone = true;
    }


}