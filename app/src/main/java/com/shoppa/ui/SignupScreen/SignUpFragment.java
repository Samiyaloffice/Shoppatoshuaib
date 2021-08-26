package com.shoppa.ui.SignupScreen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.google.android.material.shape.CornerFamily;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.SignUpModel;
import com.shoppa.R;
import com.shoppa.ui.Login.LoginFragment;

import static android.app.Activity.RESULT_OK;

public class SignUpFragment extends Fragment {

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    public static MaterialButton mSignUpScreenBuyerBtn, mSignUpScreenSellerBtn, mSignUpContinueBtn;
    public String isFrom = "";
    public boolean backRequest = false;
    ShapeableImageView mSignUpTxtBackgroundImg;
    Dialog dialog;

    EditText mSignUpFullName,
            mSignUpEmail,
            mSignUpPhoneNumber,
            mSignUpCompanyName,
            mSignUpAddress,
            mSignUpSellerType,
            mSignUpBusinessTitle,
            mSignUpBusinessDescription,
            mSignUpGSTNumber,
            mSignUpEmployees,
            mSignUpSortOrder,
            mSignUpYourProduct;

    SignUpModel mSignUpModel;
    boolean isImageCapture = false;
    int which = 0;
    RelativeLayout mSignUpImageView;
    ShapeableImageView mSignUpImg, mSignUpCancelImg;
    MaterialCardView mSignUpAddImg;
    RelativeLayout mSignUpBannerImageView;
    ShapeableImageView mSignUpBannerImg, mSignUpCancelBannerImg;
    MaterialCardView mSignUpBannerAddImg;
    RelativeLayout mSignUpLogoImageView;
    ShapeableImageView mSignUpLogoImg, mSignUpCancelLogoImg;
    MaterialCardView mSignUpLogoAddImg;
    ShapeableImageView currentImg;
    RelativeLayout currentImageView;
    MaterialCardView currentAddImg;
    Handler mHandler = new Handler();
    private SignUpViewModel mViewModel;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone || mViewModel.isError) {
                if (mViewModel.isResponseDone) {
                    callLoginFragment();
                } else {
                    Toast.makeText(requireContext(), "error while creating user", Toast.LENGTH_SHORT).show();
                }
                stopHandler();
            } else {
                startHandler();
            }
        }
    };

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sign_up_fragment, container, false);

        dialog = new Dialog(requireContext());
        mSignUpFullName = root.findViewById(R.id.sign_up_full_name);
        mSignUpEmail = root.findViewById(R.id.sign_up_email);
        mSignUpPhoneNumber = root.findViewById(R.id.sign_up_phone_number);
        mSignUpCompanyName = root.findViewById(R.id.sign_up_company_name);
        mSignUpAddress = root.findViewById(R.id.sing_up_address);
        mSignUpSellerType = root.findViewById(R.id.sign_up_seller_type);
        mSignUpBusinessTitle = root.findViewById(R.id.sign_up_business_title);
        mSignUpBusinessDescription = root.findViewById(R.id.sign_up_business_description);
        mSignUpGSTNumber = root.findViewById(R.id.sign_up_gst_number);
        mSignUpEmployees = root.findViewById(R.id.sign_up_employees);
        mSignUpSortOrder = root.findViewById(R.id.sign_up_sort_order);
        mSignUpYourProduct = root.findViewById(R.id.sign_up_product);


        Handler mBackHandler = new Handler();

        mSignUpImageView = root.findViewById(R.id.sign_up_img_view);
        mSignUpImg = root.findViewById(R.id.sign_up_img);
        mSignUpCancelImg = root.findViewById(R.id.sign_up_cancel_img);
        mSignUpCancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignUpImageView.setVisibility(View.GONE);
                mSignUpAddImg.setVisibility(View.VISIBLE);
//                Glide.with(requireContext()).load("").into(mSignUpImg);
                mSignUpImg.setImageDrawable(null);
            }
        });
        mSignUpAddImg = root.findViewById(R.id.sign_up_add_img_card);
        mSignUpAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImg = mSignUpImg;
                currentImageView = mSignUpImageView;
                currentAddImg = mSignUpAddImg;
                mSignUpImageView.setVisibility(View.VISIBLE);
                mSignUpAddImg.setVisibility(View.GONE);
                picPhoto();
            }
        });

        mSignUpBannerImageView = root.findViewById(R.id.sign_up_banner_img_view);
        mSignUpBannerImg = root.findViewById(R.id.sign_up_banner_img);
        mSignUpCancelBannerImg = root.findViewById(R.id.sign_up_cancel_banner_img);
        mSignUpCancelBannerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignUpBannerImageView.setVisibility(View.GONE);
                mSignUpBannerAddImg.setVisibility(View.VISIBLE);
//                Glide.with(requireContext()).load("").into(mSignUpBannerImg);
                mSignUpBannerImg.setImageDrawable(null);
            }
        });
        mSignUpBannerAddImg = root.findViewById(R.id.sign_up_add_img_banner_card);
        mSignUpBannerAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImg = mSignUpBannerImg;
                currentImageView = mSignUpBannerImageView;
                currentAddImg = mSignUpBannerAddImg;
                mSignUpBannerImageView.setVisibility(View.VISIBLE);
                mSignUpBannerAddImg.setVisibility(View.GONE);
                picPhoto();
            }
        });


        mSignUpLogoImageView = root.findViewById(R.id.sign_up_logo_img_view);
        mSignUpLogoImg = root.findViewById(R.id.sign_up_logo_img);
        mSignUpCancelLogoImg = root.findViewById(R.id.sign_up_cancel_logo_img);
        mSignUpCancelLogoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignUpLogoImageView.setVisibility(View.GONE);
                mSignUpLogoAddImg.setVisibility(View.VISIBLE);
//                Glide.with(requireContext()).load("").into(mSignUpLogoImg);
                mSignUpLogoImg.setImageDrawable(null);
            }
        });
        mSignUpLogoAddImg = root.findViewById(R.id.sign_up_add_img_logo_card);
        mSignUpLogoAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImg = mSignUpLogoImg;
                currentImageView = mSignUpLogoImageView;
                currentAddImg = mSignUpLogoAddImg;
                mSignUpLogoImageView.setVisibility(View.VISIBLE);
                mSignUpLogoAddImg.setVisibility(View.GONE);
                picPhoto();
            }
        });

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    callLoginFragment();

                    /*Toast.makeText(getContext(), "Press again to exit", Toast.LENGTH_SHORT).show();

                    onBackTriggered();
                    backRequest = true;

                    mBackHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backRequest = false;
                        }
                    }, 2000);*/

                    return true;
                }
                return false;
            }
        });


        mSignUpTxtBackgroundImg = root.findViewById(R.id.sign_up_txt_background_img);

        mSignUpContinueBtn = root.findViewById(R.id.sign_up_continue_btn);
        mSignUpContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
        mSignUpScreenBuyerBtn = root.findViewById(R.id.signUp_screen_buyer_btn);
        mSignUpScreenSellerBtn = root.findViewById(R.id.signUp_screen_seller_btn);

        mViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        mViewModel.setContext(getContext());

        float corner = 100;
        mSignUpTxtBackgroundImg.setShapeAppearanceModel(mSignUpTxtBackgroundImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());

        mViewModel.createDatePicker();

        return root;
    }

    private void onBackTriggered() {
        if (backRequest) {
            requireActivity().finish();
        }
    }

    private void callLoginFragment() {

        LoginFragment fragment = new LoginFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (isFrom.matches("LoginActivity")) {
            fragment.isFrom = "LoginActivity";
            transaction.replace(R.id.login_screen_main_card, fragment);
        } else {
            transaction.replace(R.id.nav_host_fragment, fragment);
        }
        transaction.commit();

    }

    void picPhoto() {

        currentImg.setVisibility(View.VISIBLE);
        currentImageView.setVisibility(View.VISIBLE);
        currentAddImg.setVisibility(View.GONE);

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
                }).setCancelable(false).show();
    }

    void performImgPicAction(int which) {

        this.which = which;

        isImageCapture = true;
        if (which == 1) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), which);
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


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        Log.i("ClickedImage", "onActivityResult: data = " + data);

        if (isImageCapture) {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                final Uri uri = data.getData();
                Glide.with(requireContext()).load(uri).error(R.drawable.no_image_found).into(currentImg);
                isImageCapture = false;
            } else if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
//                    Glide.with(requireContext()).load(DataManager.getImageUri(requireContext(), (Bitmap) data.getExtras().get("data"))).into(currentImg);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    Log.i("ClickedImage", "onActivityResult: bitmap = " + bitmap);
                    currentImg.setImageBitmap(bitmap);
                    isImageCapture = false;
                }

            }
        } else {
            Toast.makeText(requireContext(), "Please Pick an Image", Toast.LENGTH_SHORT).show();
        }

    }

    private void postData() {

        if (checkData()) {

            mSignUpModel = new SignUpModel(mSignUpCompanyName.getText().toString(),
                    mSignUpBusinessDescription.getText().toString(),
                    mSignUpBusinessTitle.getText().toString(),
                    mSignUpGSTNumber.getText().toString(),
                    mSignUpEmployees.getText().toString(),
                    mSignUpSortOrder.getText().toString(),
                    DataManager.BitMapToString(((BitmapDrawable) mSignUpImg.getDrawable()).getBitmap()),
                    DataManager.BitMapToString(((BitmapDrawable) mSignUpLogoImg.getDrawable()).getBitmap()),
                    DataManager.BitMapToString(((BitmapDrawable) mSignUpBannerImg.getDrawable()).getBitmap()),
                    mSignUpSellerType.getText().toString(),
                    mSignUpPhoneNumber.getText().toString(),
                    mSignUpEmail.getText().toString(),
                    mSignUpFullName.getText().toString(),
                    mSignUpAddress.getText().toString(),
                    mSignUpYourProduct.getText().toString());

            mViewModel.postSignUpData(mSignUpModel);
            DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
            mRunnable.run();
        }
    }

    private boolean checkData() {

        boolean isDataValid = true;

        if (mSignUpFullName.getText().toString().matches("")) {
            mSignUpFullName.setError("This option is mandatory");
            isDataValid = false;
        }
        if (mSignUpEmail.getText().toString().matches("")) {
            mSignUpEmail.setError("This option is mandatory");
            isDataValid = false;
        }
        if (!DataManager.isEmailValid(mSignUpEmail.getText().toString()) && !mSignUpEmail.getText().toString().matches("")) {
            mSignUpEmail.setError("Please enter a correct email");
            isDataValid = false;
        }
        if (mSignUpPhoneNumber.getText().toString().matches("")) {
            mSignUpPhoneNumber.setError("This option is mandatory");
            isDataValid = false;
        }
        if (mSignUpPhoneNumber.getText().toString().length() < 10 && !mSignUpPhoneNumber.getText().toString().matches("")) {
            mSignUpPhoneNumber.setError("Please enter a correct number");
            isDataValid = false;
        }
        if (mSignUpCompanyName.getText().toString().matches("")) {
            mSignUpCompanyName.setError("This option is mandatory");
            isDataValid = false;
        }
        if (mSignUpAddress.getText().toString().matches("")) {
            mSignUpAddress.setError("This option is mandatory");
            isDataValid = false;
        }
        if (mSignUpSellerType.getText().toString().matches("")) {
            mSignUpSellerType.setError("This option is mandatory");
            isDataValid = false;
        }
        if (mSignUpImg.getDrawable() == null || mSignUpBannerImg.getDrawable() == null || mSignUpLogoImg.getDrawable() == null) {
            Toast.makeText(requireContext(), "Please Upload images", Toast.LENGTH_SHORT).show();
            isDataValid = false;
        }
        if (mSignUpBusinessDescription.getText().toString().matches("")) {
            mSignUpBusinessDescription.setError("This option is mandatory");
            isDataValid = false;
        }
        /*if (mSignUpGSTNumber.getText().toString().matches("")) {
            mSignUpGSTNumber.setError("This option is mandatory");
            isDataValid = false;
        }*/
        if (mSignUpBusinessTitle.getText().toString().matches("")) {
            mSignUpBusinessTitle.setError("This option is mandatory");
            isDataValid = false;
        }
        /*if (!DataManager.isValidGSTNo(mSignUpGSTNumber.getText().toString()) && !mSignUpGSTNumber.getText().toString().matches("")) {
            mSignUpGSTNumber.setError("Please enter a valid GST number");
            isDataValid = false;
        }*/
        if (mSignUpEmployees.getText().toString().matches("")) {
            mSignUpEmployees.setError("This option is mandatory");
            isDataValid = false;
        }
        if (mSignUpSortOrder.getText().toString().matches("")) {
            mSignUpSortOrder.setError("This option is mandatory");
            isDataValid = false;
        }
        if (mSignUpYourProduct.getText().toString().matches("")) {
            mSignUpYourProduct.setError("This option is mandatory");
            isDataValid = false;
        }

        return isDataValid;

    }

    private void startHandler() {
        mHandler.postDelayed(mRunnable, 100);
    }

    private void stopHandler() {
        mHandler.removeCallbacks(mRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }


}