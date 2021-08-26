package com.shoppa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shoppa.DataManager.DataManager;
import com.shoppa.RepositoryManager.SendOTPRepository;
import com.shoppa.ui.Dashboard.DashboardFragment;

public class MainActivity extends AppCompatActivity {

    SendOTPRepository mSendOTPRepo;
    Dialog dialog;
    Handler UserHandler = new Handler();
    Runnable UserRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSendOTPRepo.isUserAvailable) {
                stopUserHandler();
            } else {
                startUserHandler();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

//        FirebaseInstallations.getInstance().getToken(true);

        /*Log.i("FirebaseNewToken", "onCreate: new Token"+FirebaseMessaging.getInstance().getToken());*/


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FirebaseNewToken", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("FirebaseNewToken", token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.purple_700));
        }

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);

        if (!sharedPreferences.getString("auth", "null").matches("null")) {
            mSendOTPRepo = SendOTPRepository.getInstance(this);
            mSendOTPRepo.mainLogin(sharedPreferences.getString("email", "null"), sharedPreferences.getString("password", "null"));
            UserRunnable.run();
            DataManager.showDialog(this, this, dialog, "open");
        }else{
            DashboardFragment fragment = new DashboardFragment();
            DataManager.isFrom = "";
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, fragment);
            transaction.commit();
        }

    }


    public void showdialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(dialog.getContext()).inflate(R.layout.internetdialog, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void startUserHandler() {
        UserHandler.postDelayed(UserRunnable, 100);
    }

    private void stopUserHandler() {
        UserHandler.removeCallbacks(UserRunnable);
        DataManager.showDialog(this, this, dialog, "close");
        DashboardFragment fragment = new DashboardFragment();
        DataManager.isFrom = "";

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}