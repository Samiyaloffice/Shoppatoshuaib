package com.shoppa.Webviewpackage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shoppa.MainActivity;
import com.shoppa.R;
import com.shoppa.ui.Package.PackagesFragment;
import com.shoppa.ui.PackageItem.PackageItemFragment;

public class WebviewBuy extends AppCompatActivity {
WebView buywebview;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

        super.onBackPressed();
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_buy);
        buywebview=findViewById(R.id.buywv);

        WebSettings webSettings=buywebview.getSettings();
webSettings.setJavaScriptEnabled(true);
webSettings.getBuiltInZoomControls();
webSettings.getDisplayZoomControls();

webSettings.setAppCacheEnabled(true);
webSettings.getLightTouchEnabled();

buywebview.setWebViewClient(new WebViewClient());
webSettings.setDomStorageEnabled(true);
/*webSettings.setForceDark(WebSettings.MENU_ITEM_WEB_SEARCH);*/
webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);


        Intent intent=getIntent();
        String buyweb=intent.getStringExtra("links");
        buywebview.loadUrl(buyweb);


    }

}