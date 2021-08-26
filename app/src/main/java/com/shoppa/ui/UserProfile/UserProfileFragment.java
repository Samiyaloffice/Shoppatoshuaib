package com.shoppa.ui.UserProfile;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.textview.MaterialTextView;

import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;
import com.shoppa.ui.AccountInformation.AccountIformationFragment;
import com.shoppa.ui.BasicInformation.BasicInformationFragment;
import com.shoppa.ui.ChangePassword.ChangePasswordFragment;
import com.shoppa.ui.MessengerId.MessengerIdFragment;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    public static ShapeableImageView mUserFragmentBackImg, mUserProfileEditBtn, mUserProfileImg;
    public static MaterialCardView mUserProfileChangePasswordBtn, mUserProfileMessengerId, mUserProfileAccountInfo, mUserProfileBasicInfo;
    public static Dialog dialog;
    public static MaterialButton mUserProfileSubmitBtn;
    ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
    MessengerIdFragment messengerIdFragment = new MessengerIdFragment();
    AccountIformationFragment accountIformationFragment = new AccountIformationFragment();
    BasicInformationFragment basicInformationFragment = new BasicInformationFragment();
    MaterialTextView mUserProfileName, mUserProfileEmail;
    Bitmap uImage;
    private UserProfileViewModel mViewModel;
    private int which = 0;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_profile_fragment, container, false);

        DataManager.isFrom = "optionFragment";
        dialog = new Dialog(requireContext());
        mUserProfileBasicInfo = root.findViewById(R.id.user_profile_basic_info);
        mUserProfileBasicInfo.setOnClickListener(this);
        mUserProfileName = root.findViewById(R.id.user_profile_name);
        mUserProfileName.setText(UserDataModel.getmInstance().getSeller_name());
        mUserProfileEmail = root.findViewById(R.id.user_profile_email);
        mUserProfileEmail.setText(UserDataModel.mInstance.getSeller_email());

        mUserProfileEditBtn = root.findViewById(R.id.user_profile_edit_btn);
        mUserProfileEditBtn.setOnClickListener(this);
        mUserProfileAccountInfo = root.findViewById(R.id.user_profile_account_info);
        mUserProfileAccountInfo.setOnClickListener(this);

        mUserProfileMessengerId = root.findViewById(R.id.user_profile_messenger_id);
        mUserProfileMessengerId.setOnClickListener(this);

        mUserProfileChangePasswordBtn = root.findViewById(R.id.user_profile_change_password_btn);
        mUserProfileChangePasswordBtn.setOnClickListener(this);

        mUserFragmentBackImg = root.findViewById(R.id.user_fragment_back_img);
        mUserProfileImg = root.findViewById(R.id.user_profile_img);

        Glide.with(requireContext())
                .load(UserDataModel.getmInstance().getSeller_image())
                .error(R.drawable.no_image_found)
                .into(mUserProfileImg);

        setImageCorners();

        mUserProfileSubmitBtn = root.findViewById(R.id.user_profile_submit_btn);

        mViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        mViewModel.setContext(getContext(), requireActivity());

        return root;
    }

    private void setImageCorners() {
        float corner = 500f;

        mUserFragmentBackImg.setShapeAppearanceModel(mUserFragmentBackImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());

        mUserFragmentBackImg.setShapeAppearanceModel(mUserFragmentBackImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomRightCorner(CornerFamily.ROUNDED, corner).build());

    }

    /*private void dashBoardFragment() {
        if (dashboardFragment.isAdded()) {

        } else {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, dashboardFragment);
            transaction.addToBackStack("changePasswordFragmentAdded");
            transaction.commit();
        }
    }*/

    public void changePasswordFrag() {
        if (changePasswordFragment.isAdded()) {

        } else {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, changePasswordFragment);
            transaction.addToBackStack("changePasswordFragmentAdded");
            transaction.commit();
        }
    }

    public void messengerIdFragment() {
        if (messengerIdFragment.isAdded()) {

        } else {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, messengerIdFragment);
            transaction.addToBackStack("changePasswordFragmentAdded");
            transaction.commit();
        }
    }

    public void basicInformationFragment() {
        if (basicInformationFragment.isAdded()) {

        } else {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, basicInformationFragment);
            transaction.addToBackStack("changePasswordFragmentAdded");
            transaction.commit();
        }
    }

    private void accountInfoFragment() {

        if (accountIformationFragment.isAdded()) {

        } else {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, accountIformationFragment);
            transaction.addToBackStack("changePasswordFragmentAdded");
            transaction.commit();
        }

    }

    @Override
    public void onClick(View v) {

        if (v == mUserProfileAccountInfo) {
            accountInfoFragment();
        } else if (v == mUserProfileMessengerId) {
            messengerIdFragment();
        } else if (v == mUserProfileChangePasswordBtn) {
            changePasswordFrag();
        } else if (v == mUserProfileBasicInfo) {
            basicInformationFragment();
        } else if (v == mUserProfileEditBtn) {
            picPhoto();
            mUserProfileSubmitBtn.setVisibility(View.VISIBLE);
        }
    }

    void picPhoto() {


        String[] str = new String[]{"Camera", "Gallery"};
        new AlertDialog.Builder(getActivity()).setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performImgPicAction(which);
                    }
                }).show();
    }

    public boolean checkPermission() {
        boolean isAllowed = true;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                isAllowed = false;
            } else {
                isAllowed = true;
            }
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                isAllowed = false;
            } else {
                isAllowed = true;
            }
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                isAllowed = false;
            } else {
                isAllowed = true;
            }
        }

        return isAllowed;
    }

    void performImgPicAction(int which) {
        if (checkPermission()) {

            this.which = which;
            if (which == 1) {
                Intent in;
                in = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(
                        Intent.createChooser(in, "Select profile picture"), which);
            } else {
                Intent pictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                );
                if (pictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(pictureIntent,
                            REQUEST_CAPTURE_IMAGE);
                }
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            Glide.with(requireContext()).load(uri).error(R.drawable.no_image_found).into(mUserProfileImg);
        } else if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mUserProfileImg.setImageBitmap(bitmap);

            }
        }

    }
}
