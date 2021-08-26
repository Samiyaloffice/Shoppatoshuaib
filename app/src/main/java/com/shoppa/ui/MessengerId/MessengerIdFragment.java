package com.shoppa.ui.MessengerId;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;

public class MessengerIdFragment extends Fragment {

    TextInputLayout mMessengerSkype, mMessengerWhatsapp;
    MaterialButton mMessengerSubmitBtn;
    Dialog dialog;
    Handler mMessengerHandler = new Handler();
    private MessengerIdViewModel mViewModel;
    Runnable mMessengerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                stopMessengerHandler();
            } else {
                startMessengerHandler();
            }
        }
    };

    public static MessengerIdFragment newInstance() {
        return new MessengerIdFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.messenger_id_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(MessengerIdViewModel.class);

        dialog = new Dialog(requireContext());
        mMessengerSkype = root.findViewById(R.id.messenger_skype);
        if (!UserDataModel.getmInstance().getSeller_skype().matches("")) {
            mMessengerSkype.getEditText().setText(UserDataModel.getmInstance().getSeller_skype());
        }
        mMessengerWhatsapp = root.findViewById(R.id.messenger_whatsapp);
        if (!UserDataModel.getmInstance().getSeller_whatsapp().matches("")) {
            mMessengerWhatsapp.getEditText().setText(UserDataModel.getmInstance().getSeller_whatsapp());
        }
        mMessengerSubmitBtn = root.findViewById(R.id.messenger_submit);
        mMessengerSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.postMessengerId(getSkypeData(), getWhatsappData());
                mMessengerRunnable.run();
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
            }
        });

        return root;
    }

    private void stopMessengerHandler() {
        mMessengerHandler.removeCallbacks(mMessengerRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
        DataManager.refreshUser(requireContext());
    }

    private void startMessengerHandler() {
        mMessengerHandler.postDelayed(mMessengerRunnable, 100);
    }

    private String getWhatsappData() {
        if (!mMessengerWhatsapp.getEditText().getText().toString().matches("")) {
            return mMessengerWhatsapp.getEditText().getText().toString();
        }
        return "";
    }

    private String getSkypeData() {

        if (!mMessengerSkype.getEditText().getText().toString().matches("")) {
            return mMessengerSkype.getEditText().getText().toString();
        }
        return "";

    }

}