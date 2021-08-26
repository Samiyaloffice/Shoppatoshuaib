package com.shoppa.ui.MessengerId;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.ViewModel;

import com.shoppa.RepositoryManager.MessengerRepository;

public class MessengerIdViewModel extends ViewModel {

    public boolean isResponseDone = false;
    Context context;
    MessengerRepository mMessengerRepo;
    Handler mMessengerHandler = new Handler();
    Runnable mMessengerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMessengerRepo.isResponseDone) {
                isResponseDone = true;
                stopMessengerHandler();
            } else {
                startMessengerHandler();
            }
        }
    };

    public void setContext(Context context) {
        this.context = context;
    }

    public void postMessengerId(String skype, String whatsapp) {
        mMessengerRepo = MessengerRepository.getInstance(context);
        mMessengerRepo.postMessengerId(skype, whatsapp);
        isResponseDone = false;
        mMessengerRunnable.run();
    }

    private void startMessengerHandler() {
        mMessengerHandler.postDelayed(mMessengerRunnable, 100);
    }

    private void stopMessengerHandler() {
        mMessengerHandler.removeCallbacks(mMessengerRunnable);
    }
}