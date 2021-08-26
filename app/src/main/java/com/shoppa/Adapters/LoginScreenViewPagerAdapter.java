package com.shoppa.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.LoginScreenViewPagerModel;
import com.shoppa.R;
import com.shoppa.RepositoryManager.SendOTPRepository;

import java.util.ArrayList;

public class LoginScreenViewPagerAdapter extends PagerAdapter {

    Context context;
    boolean otpDurationRemains = false, otpScreenAvailable = true;
    TextInputLayout mLoginScreenFirstInput;
    TextInputLayout mLoginScreenSecondInput;
    MaterialTextView mLoginScreenforgetPass, mLoginScreenSendOtp, mLoginScreenRegisterNow;
    MaterialButton mLoginScreenContinueBtn;
    LottieAnimationView mLoginScreenLeftArrow, mLoginScreenRightArrow;
    ArrayList<LoginScreenViewPagerModel> mLoginScreenViewArrayList;
    boolean phoneNumberValid = false;
    SendOTPRepository mSendOTPRepository;
    OnViewClickListener listener;
    Activity activity;
    Dialog dialog;
    Handler UserHandler = new Handler();
    Runnable UserWatcher = new Runnable() {
        @Override
        public void run() {
            if (SendOTPRepository.isUserAvailable || SendOTPRepository.isError) {
                stopUserHandler();
            } else {
                startUserHandler();
            }
        }
    };
    Handler OtpHandler = new Handler();
    Runnable OtpWatcher = new Runnable() {
        @Override
        public void run() {
            if (SendOTPRepository.OtpSend || SendOTPRepository.isError) {
                stopOtpHandler();
            } else {
                startOtpHandler();
            }
        }
    };

    public LoginScreenViewPagerAdapter(Activity activity, Context context, OnViewClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.activity = activity;
        this.dialog = new Dialog(context);
        mLoginScreenViewArrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.login_screen_pager_cell, container, false);

        mSendOTPRepository = SendOTPRepository.getInstance(context);

        mLoginScreenLeftArrow = view.findViewById(R.id.login_screen_left_arrow);
        mLoginScreenRightArrow = view.findViewById(R.id.login_screen_right_arrow);
        mLoginScreenFirstInput = view.findViewById(R.id.login_screen_first_input);
        mLoginScreenSecondInput = view.findViewById(R.id.login_screen_second_input);
        mLoginScreenforgetPass = view.findViewById(R.id.login_screen_forget_pass);
        mLoginScreenContinueBtn = view.findViewById(R.id.login_screen_continue_btn);
        mLoginScreenSendOtp = view.findViewById(R.id.login_screen_send_otp_btn);
        mLoginScreenRegisterNow = view.findViewById(R.id.login_screen_register_now_btn);

        mLoginScreenViewArrayList.add(new LoginScreenViewPagerModel(mLoginScreenFirstInput,
                mLoginScreenSecondInput,
                mLoginScreenforgetPass,
                mLoginScreenSendOtp,
                mLoginScreenContinueBtn,
                mLoginScreenRegisterNow));


        if (position == 0) {
            otpScreenAvailable = true;
            mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setHint("Phone No.");
            mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
            mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setVisibility(View.GONE);
            mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setHint("OTP");
            mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setPasswordVisibilityToggleEnabled(false);
            mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
            mLoginScreenViewArrayList.get(0).getmLoginScreenSendOtp().setVisibility(View.VISIBLE);
            mLoginScreenViewArrayList.get(0).getmLoginScreenforgetPass().setVisibility(View.GONE);
//            mLoginScreenRightArrow.setVisibility(View.VISIBLE);
//            mLoginScreenLeftArrow.setVisibility(View.GONE);
            mLoginScreenViewArrayList.get(0).getmLoginScreenSendOtp().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        if (phoneNumberValid) {
                            sendOtp();
                            mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setErrorEnabled(false);
                            mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setVisibility(View.VISIBLE);
                        }
//                        Log.i("otpScreenPosition", "instantiateItem: " + position);

                    }
                }
            });

            mLoginScreenViewArrayList.get(0).getmLoginScreenContinueBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*listener.OnViewClick("OTP", mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().toString(),
                            mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().getEditText().toString() );*/

                    if (!mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().getText().toString().matches("")
                            && !mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().getEditText().getText().toString().matches("")) {
                        mSendOTPRepository.fetchUserDetails(mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().getEditText().getText().toString());
                        DataManager.showDialog(context, activity, dialog, "open");
                        UserWatcher.run();
                    }

                }
            });


        } else if (position == 1) {
            otpScreenAvailable = false;
            mLoginScreenViewArrayList.get(1).getmLoginScreenFirstInput().setHint("Email Address or Username");
            mLoginScreenViewArrayList.get(1).getmLoginScreenFirstInput().getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
            mLoginScreenViewArrayList.get(1).getmLoginScreenSecondInput().setHint("Password");
//            mLoginScreenLeftArrow.setVisibility(View.VISIBLE);
//            mLoginScreenRightArrow.setVisibility(View.GONE);
            mLoginScreenViewArrayList.get(1).getmLoginScreenSecondInput().setPasswordVisibilityToggleEnabled(true);
            mLoginScreenViewArrayList.get(1).getmLoginScreenSecondInput().getEditText().setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mLoginScreenViewArrayList.get(1).getmLoginScreenforgetPass().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("otpScreenPosition", "instantiateItem: " + position);
                }
            });
            mLoginScreenViewArrayList.get(1).getmLoginScreenforgetPass().setVisibility(View.VISIBLE);
            mLoginScreenViewArrayList.get(1).getmLoginScreenSendOtp().setVisibility(View.GONE);

            mLoginScreenViewArrayList.get(1).getmLoginScreenContinueBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mLoginScreenViewArrayList.get(1).getmLoginScreenFirstInput().getEditText().getText().toString().matches("")
                            && !mLoginScreenViewArrayList.get(1).getmLoginScreenSecondInput().getEditText().getText().toString().matches("")) {
                        mSendOTPRepository.loginEmail(mLoginScreenViewArrayList.get(1).getmLoginScreenFirstInput().getEditText().getText().toString(),
                                mLoginScreenViewArrayList.get(1).getmLoginScreenSecondInput().getEditText().getText().toString());
                        DataManager.showDialog(context, activity, dialog, "open");
                        UserWatcher.run();
                    }
                }
            });

            Log.i("otpScreenPosition", "instantiateItem: " + position);
        }

        mLoginScreenRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick();
            }
        });

        container.addView(view);

        mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().getText().length() == 10) {
                    mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setErrorEnabled(false);
                    phoneNumberValid = true;
                    mLoginScreenViewArrayList.get(0).getmLoginScreenSendOtp().setTextColor(context.getResources().getColor(R.color.red));
                    if (otpDurationRemains) {
                        mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setVisibility(View.VISIBLE);
                    }
                } else if (mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().getText().toString().matches("")) {
                    mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setErrorEnabled(true);
                    mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setError("Please Enter your phone number");
                    mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setVisibility(View.GONE);
                    phoneNumberValid = false;
                } else if (mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().getText().length() > 10 || mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().getText().length() < 10) {
                    mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setErrorEnabled(true);
                    mLoginScreenViewArrayList.get(0).getmLoginScreenSendOtp().setTextColor(context.getResources().getColor(R.color.tab_indicator_gray));
                    mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setError("Please Enter a valid Phone Number");
                    mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setVisibility(View.GONE);
                    phoneNumberValid = false;
                } else {
                    mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setErrorEnabled(true);
                    mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().setError("Please Enter your phone number");
                    mLoginScreenViewArrayList.get(0).getmLoginScreenSecondInput().setVisibility(View.GONE);
                    phoneNumberValid = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void stopUserHandler() {
        UserHandler.removeCallbacks(UserWatcher);
        if (!SendOTPRepository.isError) {
            listener.OnViewClick();
        }
        DataManager.showDialog(context, activity, dialog, "close");
    }

    private void startUserHandler() {
        UserHandler.postDelayed(UserWatcher, 100);
    }

    public void sendOtp() {
        if (!otpDurationRemains) {
            starTimer();
            callOTPRepo();
        } else {

        }
    }

    private void callOTPRepo() {

        mSendOTPRepository.sendOTP(mLoginScreenViewArrayList.get(0).getmLoginScreenFirstInput().getEditText().getText().toString());
        OtpWatcher.run();
        DataManager.showDialog(context, activity, dialog, "open");
    }

    private void startOtpHandler() {
        OtpHandler.postDelayed(OtpWatcher, 100);
    }

    private void stopOtpHandler() {
        OtpHandler.removeCallbacks(OtpWatcher);
        DataManager.showDialog(context, activity, dialog, "close");
    }

    public void starTimer() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                otpDurationRemains = true;
                mLoginScreenViewArrayList.get(0).getmLoginScreenSendOtp().setText(DataManager.newConvertToTime(millisUntilFinished));

            }

            public void onFinish() {
                otpDurationRemains = false;
                mLoginScreenViewArrayList.get(0).getmLoginScreenSendOtp().setText("Resend OTP");
            }
        }.start();
    }


    public interface OnViewClickListener {
        void OnViewClick();

        void OnItemClick();
    }

}
