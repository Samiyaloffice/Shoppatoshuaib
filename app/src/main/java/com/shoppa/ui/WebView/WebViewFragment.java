package com.shoppa.ui.WebView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;

public class WebViewFragment extends Fragment {

    public String Url = "https://www.shoppa.in/advertise-with-us";
    WebView mPurchasePackageWebView;
    private WebViewModel mViewModel;
    private FloatingActionButton mFABHomeBtn;

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.purchase_package_fragment, container, false);

        mPurchasePackageWebView = root.findViewById(R.id.purchase_package_web_view);

        mFABHomeBtn = root.findViewById(R.id.web_view_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });

        mPurchasePackageWebView.setWebViewClient(new WebViewClient());
        mPurchasePackageWebView.getSettings().setJavaScriptEnabled(true);
        mPurchasePackageWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mPurchasePackageWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mPurchasePackageWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mPurchasePackageWebView.setWebChromeClient(new WebChromeClient());
        mPurchasePackageWebView.loadUrl(Url);


        return root;
    }

    private void dashboardFragment() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        DataManager.isFrom = "";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, dashboardFragment);
        transaction.addToBackStack("HomeFragmentAdded");

        transaction.commit();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WebViewModel.class);

    }

}