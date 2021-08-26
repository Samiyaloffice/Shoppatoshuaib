package com.shoppa.ui.ChangePassword;

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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;

public class ChangePasswordFragment extends Fragment {

    public static TextInputLayout mChangePasswordOldPass, mChangePasswordNewPass, mChangePasswordReCheckPass;
    ShapeableImageView shapeableImageView1, shapeableImageView2;
    MaterialButton mChangePasswordSaveBtn;
    Dialog dialog;
    Handler mPasswordHandler = new Handler();
    private ChangePasswordViewModel mViewModel;
    Runnable mPasswordRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                stopPasswordHandler();
            } else {
                startPasswordHandler();
            }
        }
    };

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.change_password_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);

        dialog = new Dialog(requireContext());
        mChangePasswordSaveBtn = root.findViewById(R.id.change_password_save_btn);
        mChangePasswordOldPass = root.findViewById(R.id.change_password_old_pass);
        mChangePasswordNewPass = root.findViewById(R.id.change_password_new_pass);
        mChangePasswordReCheckPass = root.findViewById(R.id.change_password_recheck_pass);

        mViewModel.setContext(requireContext());

        mChangePasswordSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.checkPasswordValidity()) {
                    mChangePasswordOldPass.setErrorEnabled(false);
                    mChangePasswordNewPass.setErrorEnabled(false);
                    mChangePasswordReCheckPass.setErrorEnabled(false);
                    mViewModel.postPasswordDetails();
                    mPasswordRunnable.run();
                    DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                }
            }
        });

        return root;
    }

    private void stopPasswordHandler() {
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
        mChangePasswordOldPass.getEditText().setText("");
        mChangePasswordNewPass.getEditText().setText("");
        mChangePasswordReCheckPass.getEditText().setText("");
        mPasswordHandler.removeCallbacks(mPasswordRunnable);
    }

    private void startPasswordHandler() {
        mPasswordHandler.postDelayed(mPasswordRunnable, 100);
    }

}