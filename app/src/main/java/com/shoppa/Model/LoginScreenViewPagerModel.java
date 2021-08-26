package com.shoppa.Model;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class LoginScreenViewPagerModel {

    TextInputLayout mLoginScreenFirstInput;
    TextInputLayout mLoginScreenSecondInput;
    MaterialTextView mLoginScreenforgetPass, mLoginScreenSendOtp, mLoginScreenRegisterNow;
    MaterialButton mLoginScreenContinueBtn;


    public LoginScreenViewPagerModel(TextInputLayout mLoginScreenFirstInput,
                                     TextInputLayout mLoginScreenSecondInput,
                                     MaterialTextView mLoginScreenforgetPass,
                                     MaterialTextView mLoginScreenSendOtp,
                                     MaterialButton mLoginScreenContinueBtn,
                                     MaterialTextView mLoginScreenRegisterNow) {
        this.mLoginScreenFirstInput = mLoginScreenFirstInput;
        this.mLoginScreenSecondInput = mLoginScreenSecondInput;
        this.mLoginScreenforgetPass = mLoginScreenforgetPass;
        this.mLoginScreenSendOtp = mLoginScreenSendOtp;
        this.mLoginScreenContinueBtn = mLoginScreenContinueBtn;
        this.mLoginScreenRegisterNow = mLoginScreenRegisterNow;
    }

    public MaterialTextView getmLoginScreenRegisterNow() {
        return mLoginScreenRegisterNow;
    }

    public void setmLoginScreenRegisterNow(MaterialTextView mLoginScreenRegisterNow) {
        this.mLoginScreenRegisterNow = mLoginScreenRegisterNow;
    }

    public TextInputLayout getmLoginScreenFirstInput() {
        return mLoginScreenFirstInput;
    }

    public void setmLoginScreenFirstInput(TextInputLayout mLoginScreenFirstInput) {
        this.mLoginScreenFirstInput = mLoginScreenFirstInput;
    }

    public TextInputLayout getmLoginScreenSecondInput() {
        return mLoginScreenSecondInput;
    }

    public void setmLoginScreenSecondInput(TextInputLayout mLoginScreenSecondInput) {
        this.mLoginScreenSecondInput = mLoginScreenSecondInput;
    }

    public MaterialTextView getmLoginScreenforgetPass() {
        return mLoginScreenforgetPass;
    }

    public void setmLoginScreenforgetPass(MaterialTextView mLoginScreenforgetPass) {
        this.mLoginScreenforgetPass = mLoginScreenforgetPass;
    }

    public MaterialTextView getmLoginScreenSendOtp() {
        return mLoginScreenSendOtp;
    }

    public void setmLoginScreenSendOtp(MaterialTextView mLoginScreenSendOtp) {
        this.mLoginScreenSendOtp = mLoginScreenSendOtp;
    }

    public MaterialButton getmLoginScreenContinueBtn() {
        return mLoginScreenContinueBtn;
    }

    public void setmLoginScreenContinueBtn(MaterialButton mLoginScreenContinueBtn) {
        this.mLoginScreenContinueBtn = mLoginScreenContinueBtn;
    }
}
