package com.shoppa.ui.PostSeller;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.R;
import com.shoppa.ui.TradeInformationPostSeller.TradeInfoPostSellerFragment;

import static android.app.Activity.RESULT_OK;

public class PostSellerFragment extends Fragment {

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    public static MaterialAutoCompleteTextView mPostSellerProductGroupTxt, mPostSellerOriginTxt;
    ShapeableImageView mPostSellerImg, mPostSellerCancelImg;
    MaterialCardView mPostSellerAddImg;
    RelativeLayout mPostSellerImgView;
    MaterialTextView mPostSellerAttachFile;
    MaterialButton mPostSellerNextBtn;
    int PICKFILE_RESULT_CODE = 100;
    boolean isImageCapture = false, isFileCapture = false;
    private PostSellerViewModel mViewModel;
    private int which = 0;

    public static PostSellerFragment newInstance() {
        return new PostSellerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.post_seller_fragment, container, false);

        mPostSellerProductGroupTxt = root.findViewById(R.id.post_seller_product_group_txt);
        mPostSellerOriginTxt = root.findViewById(R.id.post_seller_origin_txt);
        mPostSellerNextBtn = root.findViewById(R.id.post_seller_next_btn);
        mPostSellerNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tradeInfoFragment();
            }
        });
        mPostSellerAttachFile = root.findViewById(R.id.post_seller_attach_file);
        mPostSellerAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        mPostSellerCancelImg = root.findViewById(R.id.post_seller_cancel_img);
        mPostSellerCancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostSellerImgView.setVisibility(View.GONE);
                mPostSellerAddImg.setVisibility(View.VISIBLE);
                Glide.with(requireContext()).load("").error(R.drawable.no_image_found).into(mPostSellerImg);

            }
        });
        mPostSellerImgView = root.findViewById(R.id.post_seller_img_view);
        mPostSellerAddImg = root.findViewById(R.id.post_seller_add_img_card);
        mPostSellerImg = root.findViewById(R.id.post_seller_img);
        mPostSellerAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picPhoto();

            }
        });
        mViewModel = new ViewModelProvider(this).get(PostSellerViewModel.class);
        mViewModel.setContext(getContext());
        return root;
    }

    private void tradeInfoFragment() {
        TradeInfoPostSellerFragment fragment = new TradeInfoPostSellerFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("TradeInformationFragmentAdded");
        transaction.commit();
    }

    private void chooseFile() {
        isFileCapture = true;
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }

    void picPhoto() {

        mPostSellerImgView.setVisibility(View.VISIBLE);
        mPostSellerAddImg.setVisibility(View.GONE);

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
                Glide.with(requireContext()).load(uri).error(R.drawable.no_image_found).into(mPostSellerImg);
                isImageCapture = false;
            } else if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    mPostSellerImg.setImageBitmap(imageBitmap);
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