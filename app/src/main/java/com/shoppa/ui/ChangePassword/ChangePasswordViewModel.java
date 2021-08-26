package com.shoppa.ui.ChangePassword;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.ViewModel;

import com.shoppa.Model.UserDataModel;
import com.shoppa.RepositoryManager.ChangePasswordRepository;

import java.util.Objects;

import static com.shoppa.ui.ChangePassword.ChangePasswordFragment.mChangePasswordNewPass;
import static com.shoppa.ui.ChangePassword.ChangePasswordFragment.mChangePasswordOldPass;
import static com.shoppa.ui.ChangePassword.ChangePasswordFragment.mChangePasswordReCheckPass;

@SuppressLint("StaticFieldLeak")
public class ChangePasswordViewModel extends ViewModel {
    public boolean isResponseDone = false;
    ChangePasswordRepository mChangePassRepo;

    Context context;
    Handler passHandler = new Handler();
    Runnable passRunnable = new Runnable() {
        @Override
        public void run() {
            if (mChangePassRepo.isResponseDone) {
                isResponseDone = true;
                stopHandler();
            } else {
                startHandler();
            }
        }
    };

    private void startHandler() {
        passHandler.postDelayed(passRunnable, 100);
    }

    private void stopHandler() {
        passHandler.removeCallbacks(passRunnable);
    }

    public boolean checkPasswordValidity() {

        boolean isDataValid = true;

        if (Objects.requireNonNull(mChangePasswordOldPass.getEditText()).getText().toString().matches("")) {
            isDataValid = false;
            mChangePasswordOldPass.setErrorEnabled(true);
            mChangePasswordOldPass.setError("This option is Mandatory");
        } else {
            mChangePasswordOldPass.setErrorEnabled(false);
        }
        if (Objects.requireNonNull(mChangePasswordNewPass.getEditText()).getText().toString().matches("")) {
            isDataValid = false;
            mChangePasswordNewPass.setErrorEnabled(true);
            mChangePasswordNewPass.setError("This option is Mandatory");
        } else {
            mChangePasswordNewPass.setErrorEnabled(false);
        }
        if (Objects.requireNonNull(mChangePasswordReCheckPass.getEditText()).getText().toString().matches("")) {
            isDataValid = false;
            mChangePasswordReCheckPass.setErrorEnabled(true);
            mChangePasswordReCheckPass.setError("This option is Mandatory");
        } else {
            mChangePasswordReCheckPass.setErrorEnabled(false);
        }
        if (!mChangePasswordOldPass.getEditText().getText().toString().matches(UserDataModel.getmInstance().getSeller_password())) {
            isDataValid = false;
            mChangePasswordOldPass.setErrorEnabled(true);
            mChangePasswordOldPass.setError("Please Enter Correct Password");
        } else {
            mChangePasswordOldPass.setErrorEnabled(false);
        }
        if (!mChangePasswordNewPass.getEditText().getText().toString().matches(mChangePasswordReCheckPass.getEditText().getText().toString())) {
            isDataValid = false;
            mChangePasswordReCheckPass.setErrorEnabled(true);
            mChangePasswordReCheckPass.setError("Please recheck the password");
        } else {
            mChangePasswordReCheckPass.setErrorEnabled(false);
        }

        return isDataValid;
    }

    public void postPasswordDetails() {
        mChangePassRepo = ChangePasswordRepository.getInstance(context);
        mChangePassRepo.postChangePassword(Objects.requireNonNull(mChangePasswordNewPass.getEditText()).getText().toString());
        passRunnable.run();
    }

    public void setContext(Context requireContext) {
        this.context = requireContext;
    }
}