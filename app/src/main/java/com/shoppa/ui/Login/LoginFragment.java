package com.shoppa.ui.Login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.LoginScreenViewPagerAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.MainActivity;
import com.shoppa.R;
import com.shoppa.RepositoryManager.SendOTPRepository;
import com.shoppa.ui.SignupScreen.SignUpFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LoginFragment extends BottomSheetDialogFragment {

    public static ViewPager mLoginFragmentViewPager;
    public String isFrom = "", isFromTemp = "";
    public boolean backRequest = false;
    TabLayout mLoginScreenTabIndicator;
    SendOTPRepository mSendOtpRepository;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    MaterialTextView mLoginScreenUserLocation, mLoginScreenSkipBtn;
    LoginScreenViewPagerAdapter mLoginScreenViewPagerAdapter;
    Handler mHandler = new Handler();
    public Runnable userWatcher = new Runnable() {
        @Override
        public void run() {
            if (SendOTPRepository.isUserAvailable) {
                callMainActivity();
                stopHandler();
            } else {
                startHandler();
            }
        }
    };
    private SharedPreferences mLocationPreferance;
    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult
                    .getLastLocation();
            //Toast.makeText(requireContext(), mLastLocation.getLatitude() + " " + mLastLocation.getLongitude() + "", Toast.LENGTH_SHORT).show();

            Geocoder geocoder = new Geocoder(requireContext(),
                    Locale
                            .getDefault());

            List<Address> addresses = null;
            try {
                addresses = geocoder
                        .getFromLocation(mLastLocation
                                        .getLatitude(),
                                mLastLocation
                                        .getLongitude(),
                                1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataManager.userCityName = addresses
                    .get(0)
                    .getSubLocality();
            DataManager.userStateName = addresses
                    .get(0)
                    .getLocality();
            DataManager.userCountryName = addresses
                    .get(0)
                    .getCountryName();
            DataManager.userAddress = addresses.get(0).getAddressLine(0);
            //Toast.makeText(requireContext(), DataManager.userLocalityName +" "+ DataManager.userStateName + " " + DataManager.userCountryName, Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = mLocationPreferance.edit();
            editor.putString("loc", "present");
            editor.putString("city", DataManager.userCityName);
            editor.putString("state", DataManager.userStateName);
            editor.putString("country", DataManager.userCountryName);
            editor.putString("address", DataManager.userAddress);
            editor.apply();

            mLoginScreenUserLocation.setText(DataManager.userCityName + ", " + DataManager.userStateName + ", " + DataManager.userCountryName);
        }
    };
    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        mLoginScreenSkipBtn = root.findViewById(R.id.login_frag_skip_btn);
        if(isFromTemp.matches("mainActivity")){
            isFromTemp = "";
            mLoginScreenSkipBtn.setVisibility(View.GONE);
        }
        mLoginScreenSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainActivity();
            }
        });
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        Handler mBackHandler = new Handler();
        mLoginScreenTabIndicator = root.findViewById(R.id.login_screen_tab_indicator);
        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(requireContext());
        mLoginScreenUserLocation = root.findViewById(R.id.login_screen_user_location);
        mLocationPreferance = requireContext().getSharedPreferences("MyLocation", Context.MODE_PRIVATE);
        if (mLocationPreferance.getString("loc", "null").matches("null")) {
            getLastLocation();
        } else {
            mLoginScreenUserLocation.setText(mLocationPreferance.getString("city", "null") + ", " + mLocationPreferance.getString("state", "null") + ", " + mLocationPreferance.getString("country", "null"));
        }


        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    Toast.makeText(getContext(), "Press again to exit", Toast.LENGTH_SHORT).show();

                    onBackTriggered();
                    backRequest = true;

                    mBackHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backRequest = false;
                        }
                    }, 2000);

                    return true;
                }
                return false;
            }
        });


        mLoginFragmentViewPager = root.findViewById(R.id.login_screen_view_pager);
        setLoginFragmentAdapter();

        return root;
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            if (mLocationPreferance.getString("loc", "null").matches("null")) {
                // check if location is enabled
                if (isLocationEnabled()) {

                    // getting last
                    // location from
                    // FusedLocationClient
                    // object
                    mFusedLocationClient
                            .getLastLocation()
                            .addOnCompleteListener(new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    Location location = task
                                            .getResult();
                                    if (location == null) {
                                        requestNewLocationData();
                                    } else {
//                            Toast.makeText(requireContext(), location.getLatitude() +" "+ location.getLongitude() + "", Toast.LENGTH_SHORT).show();

                                        Geocoder geocoder = new Geocoder(requireContext(), Locale
                                                .getDefault());
                                        List<Address> addresses = null;
                                        try {
                                            addresses = geocoder
                                                    .getFromLocation(location.getLatitude(),
                                                            location.getLongitude(),
                                                            1);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            DataManager.userCityName = addresses
                                                    .get(0)
                                                    .getSubLocality();
                                            DataManager.userStateName = addresses
                                                    .get(0)
                                                    .getLocality();
                                            DataManager.userCountryName = addresses
                                                    .get(0)
                                                    .getCountryName();

                                            //Toast.makeText(requireContext(), DataManager.userLocalityName +" "+ DataManager.userStateName + " " + DataManager.userCountryName, Toast.LENGTH_LONG).show();
                                            DataManager.userAddress = addresses.get(0).getAddressLine(0);
                                            mLoginScreenUserLocation.setText(DataManager.userCityName + ", " + DataManager.userStateName + ", " + DataManager.userCountryName);


                                            SharedPreferences.Editor editor = mLocationPreferance.edit();
                                            editor.putString("loc", "present");
                                            editor.putString("city", DataManager.userCityName);
                                            editor.putString("state", DataManager.userStateName);
                                            editor.putString("country", DataManager.userCountryName);
                                            editor.putString("address", DataManager.userAddress);
                                            editor.apply();


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            });
                } else {
                    Toast
                            .makeText(requireContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }

            }
        } else {
            // if permissions aren't available,
            // request for permissions
            if (mLocationPreferance.getString("loc", "null").matches("null")) {
                requestPermissions();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest

        // object with appropriate methods

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest
                .setPriority(LocationRequest
                        .PRIORITY_HIGH_ACCURACY);
        mLocationRequest
                .setInterval(5);
        mLocationRequest
                .setFastestInterval(0);
        mLocationRequest
                .setNumUpdates(1);

        // setting LocationRequest

        // on FusedLocationClient

        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(requireContext());
        mFusedLocationClient
                .requestLocationUpdates(mLocationRequest,
                        mLocationCallback,
                        Looper.myLooper());
    }

    // method to check for permissions
    private boolean checkPermissions() {


        return ActivityCompat
                .checkSelfPermission(requireContext(),
                        Manifest
                                .permission
                                .ACCESS_COARSE_LOCATION) == PackageManager
                .PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(requireContext(),
                        Manifest
                                .permission
                                .ACCESS_FINE_LOCATION) == PackageManager
                .PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        if (mLocationPreferance.getString("loc", "null").matches("null")) {
            ActivityCompat
                    .requestPermissions(requireActivity(),
                            new String[]{
                                    Manifest
                                            .permission
                                            .ACCESS_COARSE_LOCATION,
                                    Manifest
                                            .permission
                                            .ACCESS_FINE_LOCATION},
                            PERMISSION_ID);
        }
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {


        LocationManager locationManager = (LocationManager)
                requireContext()
                        .getSystemService(Context.LOCATION_SERVICE);
        return
                locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)
                        || locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == PERMISSION_ID) {

            if (grantResults
                    .length > 0
                    && grantResults[0] == PackageManager
                    .PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {

        super
                .onResume();
        if (mLocationPreferance.getString("loc", "null").matches("null")) {
            if (checkPermissions()) {

                getLastLocation();

            }
        }
    }

    private void onBackTriggered() {
        if (backRequest) {
            requireActivity().finish();
        }
    }

    private void setLoginFragmentAdapter() {

        mLoginScreenViewPagerAdapter = new LoginScreenViewPagerAdapter(requireActivity(), getContext(), new LoginScreenViewPagerAdapter.OnViewClickListener() {
            @Override
            public void OnViewClick() {
                userWatcher.run();
            }

            @Override
            public void OnItemClick() {
                signUpFragment();
            }
        });
        mLoginFragmentViewPager.setAdapter(mLoginScreenViewPagerAdapter);

        mLoginScreenTabIndicator.setupWithViewPager(mLoginFragmentViewPager);

    }

    private void stopHandler() {
        mHandler.removeCallbacks(userWatcher);
    }

    private void startHandler() {
        mHandler.postDelayed(userWatcher, 100);
    }

    private void callMainActivity() {
        Log.i("userDetails", "OnViewClick: ActivityCalled");
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void signUpFragment() {

        SignUpFragment mSignUpFragment = new SignUpFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (isFrom.matches("LoginActivity")) {
            transaction.replace(R.id.login_screen_main_card, mSignUpFragment);
            transaction.addToBackStack("SignUpFragmentAdded");
            mSignUpFragment.isFrom = isFrom;
        } else {
            transaction.replace(R.id.nav_host_fragment, mSignUpFragment);
        }
        transaction.commit();
        DataManager.loginFragment.dismiss();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

