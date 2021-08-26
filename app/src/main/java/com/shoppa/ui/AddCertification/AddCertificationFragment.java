package com.shoppa.ui.AddCertification;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.shoppa.R;

import static android.app.Activity.RESULT_OK;

public class AddCertificationFragment extends Fragment {

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    ShapeableImageView mAddCertificationCancelImg, mAddCertificationImg;
    RelativeLayout mAddCertificationImgView;
    MaterialCardView mAddCertificationAddImg;
    int PICKFILE_RESULT_CODE = 100;
    boolean isImageCapture = false, isFileCapture = false;
    private AddCertificationViewModel mViewModel;
    private int which = 0;

    public static AddCertificationFragment newInstance() {
        return new AddCertificationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AddCertificationViewModel.class);
        View root = inflater.inflate(R.layout.add_certification_fragment, container, false);

        mAddCertificationCancelImg = root.findViewById(R.id.post_seller_cancel_img);
        mAddCertificationCancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddCertificationImgView.setVisibility(View.GONE);
                mAddCertificationAddImg.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load("").error(R.drawable.no_image_found).into(mAddCertificationImg);

            }
        });
        mAddCertificationImgView = root.findViewById(R.id.post_seller_img_view);
        mAddCertificationAddImg = root.findViewById(R.id.post_seller_add_img_card);
        mAddCertificationImg = root.findViewById(R.id.post_seller_img);
        mAddCertificationAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picPhoto();

            }
        });

        return root;
    }

    void picPhoto() {

        mAddCertificationImgView.setVisibility(View.VISIBLE);
        mAddCertificationAddImg.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        String[] str = new String[]{"Camera", "Gallery"};
        new AlertDialog.Builder(getActivity()).setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performImgPicAction(which);
                    }
                }).show();
    }

    void performImgPicAction(int which) {

        this.which = which;

        isImageCapture = true;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (isImageCapture) {

            if (requestCode == 1 && resultCode == RESULT_OK) {
                final Uri uri = data.getData();
                Glide.with(getContext()).load(uri).error(R.drawable.no_image_found).into(mAddCertificationImg);
                isImageCapture = false;
            } else if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    mAddCertificationImg.setImageBitmap(imageBitmap);
                    isImageCapture = false;
                }
            }

        } else if (isFileCapture) {

            Uri uri = data.getData();
            String src = uri.getPath();

            Log.i("fileCapturePath", "onActivityResult: filePath = " + src);

        }

    }
}