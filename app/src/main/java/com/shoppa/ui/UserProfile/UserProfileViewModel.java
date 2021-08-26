package com.shoppa.ui.UserProfile;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.android.material.shape.CornerFamily;
import com.shoppa.DataManager.DataManager;
import com.shoppa.RepositoryManager.UserProfileImageRepository;

import static com.shoppa.ui.UserProfile.UserProfileFragment.dialog;
import static com.shoppa.ui.UserProfile.UserProfileFragment.mUserFragmentBackImg;
import static com.shoppa.ui.UserProfile.UserProfileFragment.mUserProfileImg;
import static com.shoppa.ui.UserProfile.UserProfileFragment.mUserProfileSubmitBtn;

public class UserProfileViewModel extends ViewModel implements View.OnClickListener {

    public boolean isResponseDone = false;
    Context context;
    UserProfileImageRepository mUserProfileImgRepo;
    Handler mImageHandler = new Handler();
    private Activity activity;
    Runnable mImageRunnable = new Runnable() {
        @Override
        public void run() {
            if (mUserProfileImgRepo.isResponseDone) {
                DataManager.showDialog(context, activity, dialog, "close");
                stopImageHandler();
            } else {
                startImageHandler();
            }
        }
    };

    public UserProfileViewModel() {
//        setImageCorners();
        setButtons();
    }

    private void setButtons() {
        mUserProfileSubmitBtn.setOnClickListener(this);
    }



    public void setContext(Context cnt1, Activity activity) {
        this.context = cnt1;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        if (v == mUserProfileSubmitBtn) {
            mUserProfileSubmitBtn.setVisibility(View.GONE);
            postImage();

        }
    }

    private void postImage() {

        mUserProfileImgRepo = UserProfileImageRepository.getInstance(context);
        mUserProfileImgRepo.postProfileImage(DataManager.BitMapToString(((BitmapDrawable) mUserProfileImg.getDrawable()).getBitmap()));
        isResponseDone = false;
        DataManager.showDialog(context, activity, dialog, "open");
        mImageRunnable.run();
    }

    private void startImageHandler() {
        mImageHandler.postDelayed(mImageRunnable, 100);
        isResponseDone = true;
    }

    private void stopImageHandler() {
        mImageHandler.removeCallbacks(mImageRunnable);
    }

}