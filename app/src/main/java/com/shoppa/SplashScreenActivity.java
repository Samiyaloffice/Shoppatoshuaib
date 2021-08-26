package com.shoppa;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.shoppa.DataManager.DataManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult
                    .getLastLocation();
            //Toast.makeText(requireContext(), mLastLocation.getLatitude() + " " + mLastLocation.getLongitude() + "", Toast.LENGTH_SHORT).show();

            Geocoder geocoder = new Geocoder(SplashScreenActivity.this,
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

//            mLoginScreenUserLocation.setText(DataManager.userLocalityName + ", " + DataManager.userStateName + ", " + DataManager.userCountryName);
        }
    };
    ShapeableImageView mSplashScreenImg, mSplashScreenOwsImg;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mSplashScreenOwsImg = findViewById(R.id.splash_screen_ows_img);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.purple_700));
        }

        Glide.with(SplashScreenActivity.this).load("https://www.shoppa.in/img_for_link/ows.jpg").error(R.drawable.no_image_found).into(mSplashScreenOwsImg);

        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(this);
//        getLastLocation();

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPref", MODE_PRIVATE);

        mSplashScreenImg = findViewById(R.id.splash_screen_img);
        Glide.with(this).load("https://flakeads.co.uk/wp-content/uploads/2020/08/logo-750x431.png").error(R.drawable.no_image_found).into(mSplashScreenImg);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*if (sharedPreferences.getString("auth", "null").matches("null")) {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                }*/

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

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
//                            Toast.makeText(requir;lfl'ldfeContext(), location.getLatitude() +" "+ location.getLongitude() + "", Toast.LENGTH_SHORT).show();

                                    Geocoder geocoder = new Geocoder(SplashScreenActivity.this, Locale
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
//                                    mLoginScreenUserLocation.setText(DataManager.userLocalityName + ", " + DataManager.userStateName + ", " + DataManager.userCountryName);
                                }

                            }
                        });
            } else {
                Toast
                        .makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
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
                .getFusedLocationProviderClient(this);
        mFusedLocationClient
                .requestLocationUpdates(mLocationRequest,
                        mLocationCallback,
                        Looper.myLooper());
    }

    // method to check for permissions
    private boolean checkPermissions() {

        return ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission                 .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest
                                        .permission
                                        .ACCESS_COARSE_LOCATION,
                                Manifest
                                        .permission
                                        .ACCESS_FINE_LOCATION},
                        PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {

        LocationManager locationManager = (LocationManager)
                this
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
}