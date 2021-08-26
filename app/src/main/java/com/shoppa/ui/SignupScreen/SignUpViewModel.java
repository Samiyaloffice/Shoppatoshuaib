package com.shoppa.ui.SignupScreen;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;

import androidx.lifecycle.ViewModel;

import com.shoppa.Model.SignUpModel;
import com.shoppa.R;
import com.shoppa.RepositoryManager.SignUpRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpViewModel extends ViewModel implements View.OnClickListener {

    public boolean isResponseDone = false, isError = false;
    Context context;
    SignUpRepository mSignUpRepo;
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSignUpRepo.isResponseDone || mSignUpRepo.isError) {
                if (mSignUpRepo.isResponseDone) {
                    isResponseDone = true;
                    isError = false;
                } else {
                    isResponseDone = false;
                    isError = true;
                }
                stopHandler();
            } else {
                startHandler();
            }
        }
    };

    public SignUpViewModel() {
        setButton();
    }

    public void setContext(Context context) {
        this.context = context;
        mSignUpRepo = SignUpRepository.getInstance(context);
    }

    private void setButton() {

        SignUpFragment.mSignUpScreenBuyerBtn.setOnClickListener(this);
        SignUpFragment.mSignUpScreenSellerBtn.setOnClickListener(this);

    }

    public void createDatePicker() {

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

       /* SignUpFragment.mSignUpScreenDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

    }

    private void updateLabel(Calendar myCalender) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

//        SignUpFragment.mSignUpScreenDatePicker.setText(sdf.format(myCalender.getTime()));
    }

    @Override
    public void onClick(View v) {
        if (v == SignUpFragment.mSignUpScreenBuyerBtn) {
            SignUpFragment.mSignUpScreenBuyerBtn.setBackgroundColor(context.getResources().getColor(R.color.dark_red));
            SignUpFragment.mSignUpScreenBuyerBtn.setTextColor(context.getResources().getColor(R.color.white));


            SignUpFragment.mSignUpScreenSellerBtn.setBackgroundColor(context.getResources().getColor(R.color.white));
            SignUpFragment.mSignUpScreenSellerBtn.setTextColor(context.getResources().getColor(R.color.dark_red));
            SignUpFragment.mSignUpScreenSellerBtn.setStrokeColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_red)));
            SignUpFragment.mSignUpScreenSellerBtn.setStrokeWidth(1);

        } else if (v == SignUpFragment.mSignUpScreenSellerBtn) {
            SignUpFragment.mSignUpScreenSellerBtn.setBackgroundColor(context.getResources().getColor(R.color.dark_red));
            SignUpFragment.mSignUpScreenSellerBtn.setTextColor(context.getResources().getColor(R.color.white));


            SignUpFragment.mSignUpScreenBuyerBtn.setBackgroundColor(context.getResources().getColor(R.color.white));
            SignUpFragment.mSignUpScreenBuyerBtn.setTextColor(context.getResources().getColor(R.color.dark_red));
            SignUpFragment.mSignUpScreenBuyerBtn.setStrokeColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_red)));
            SignUpFragment.mSignUpScreenBuyerBtn.setStrokeWidth(1);
        }
    }

    public void postSignUpData(SignUpModel model) {
        mSignUpRepo.submitSignUpDetails(model);
        mRunnable.run();
    }

    private void startHandler() {
        mHandler.postDelayed(mRunnable, 100);
    }

    private void stopHandler() {
        mHandler.removeCallbacks(mRunnable);
    }
}