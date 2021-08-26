package com.shoppa.ui.ContactUs;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.shoppa.R;

public class ContactUsFragment extends Fragment {

    MaterialButton mContactUsCallBtn, mContactUsEmailBtn;
    private ContactUsViewModel mViewModel;

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContactUsViewModel.class);
        View root = inflater.inflate(R.layout.contact_us_fragment, container, false);

        mContactUsCallBtn = root.findViewById(R.id.contact_us_call_btn);
        mContactUsCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCallingDialog();
            }
        });

        mContactUsEmailBtn = root.findViewById(R.id.contact_us_email_btn);
        mContactUsEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });

        return root;
    }

    private void openEmail() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:kartik@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "ProductInquiry");
        intent.putExtra(Intent.EXTRA_TEXT, "User Message");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void openCallingDialog() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
        }

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_calling_option);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /*MaterialTextView mDialogNumber1, mDialogNumber2;

        mDialogNumber1 = dialog.findViewById(R.id.dialog_calling_number_1);
        mDialogNumber2 = dialog.findViewById(R.id.dialog_calling_number_2);

        mDialogNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91 8929652267"));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        mDialogNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91 8929652267"));
                startActivity(intent);
                dialog.dismiss();
            }
        });*/

        dialog.show();

    }

}