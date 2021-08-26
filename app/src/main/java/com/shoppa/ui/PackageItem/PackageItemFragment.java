package com.shoppa.ui.PackageItem;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.PackageModel;
import com.shoppa.R;
import com.shoppa.Webviewpackage.WebviewBuy;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.WebView.WebViewFragment;

public class PackageItemFragment extends Fragment {

    public PackageModel model;

    MaterialTextView mPackageItemContent, mPackageItemContent2, mPackageItemCancelAmount, mPackageItemAmount, mPackageItemName, mPackageItemDiscountPer,
            package_item_priority_search1,
            package_item_display_pro,
            package_item_display_selling_lead,
            package_item_number_of_enquiry,
            package_item_access_to_new_buy,
            package_item_access_to_3million,
            package_item_priority_search2,
            package_item_detail_title,
            package_item_free_company_verification;

    MaterialButton mPackageItemApplyNowBtn, mPackageItemPopularBtn;
    ScrollView mPackageDetailsScroller;
    MaterialCardView mPackageItemMemberCard;
    private PackageItemViewModel mViewModel;

    private boolean isFadeOut = false;
    private boolean isFadeIn = true;
    private WebView buywenviews;

    public FloatingActionButton buypackage;
    private FloatingActionButton mFABHomeBtn;
    private WebView mPackageItemWebView;
    public static PackageItemFragment newInstance() {
        return new PackageItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.package_item_fragment, container, false);
        mFABHomeBtn = root.findViewById(R.id.package_item_fab_home_btn);
        mPackageItemWebView = root.findViewById(R.id.package_item_web_view);
        buypackage=root.findViewById(R.id.buypackage);
        buywenviews=(WebView) root.findViewById(R.id.buywv);
        mPackageItemWebView.setWebViewClient(new WebViewClient());
buypackage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       String url="https://www.shoppa.in/advertise-with-us";
        Intent intent=new Intent(getActivity().getApplicationContext(), WebviewBuy.class);
        intent.putExtra("links",url);
        startActivity(intent);

    }
});
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View v) {
                dashboardFragment();
            }
        });

        package_item_priority_search1 = root.findViewById(R.id.package_item_priority_search1);
        package_item_display_pro = root.findViewById(R.id.package_item_display_pro);
        package_item_display_selling_lead = root.findViewById(R.id.package_item_display_selling_lead);
        package_item_number_of_enquiry = root.findViewById(R.id.package_item_number_of_enquiry);
        package_item_access_to_new_buy = root.findViewById(R.id.package_item_access_to_new_buy);
        package_item_access_to_3million = root.findViewById(R.id.package_item_access_to_3million);
        package_item_priority_search2 = root.findViewById(R.id.package_item_priority_search2);
        package_item_free_company_verification = root.findViewById(R.id.package_item_free_company_verification);
        package_item_detail_title = root.findViewById(R.id.package_item_detail_title);
        mViewModel = new ViewModelProvider(this).get(PackageItemViewModel.class);
        mPackageItemContent = root.findViewById(R.id.package_item_content_txt);
        mPackageItemContent2 = root.findViewById(R.id.package_item_content2_txt);
        mPackageItemAmount = root.findViewById(R.id.package_item_amount);
        mPackageItemName = root.findViewById(R.id.package_item_title);
        mPackageItemName.setText(model.getPlan_name().replaceAll("\r\n", ""));
        mPackageItemCancelAmount = root.findViewById(R.id.package_item_cancel_amount);
        mPackageItemDiscountPer = root.findViewById(R.id.package_item_discount_perc);
        mPackageItemPopularBtn = root.findViewById(R.id.package_item_popular_btn);

        if (model.getDiscount().matches("")) {
            mPackageItemCancelAmount.setVisibility(View.INVISIBLE);
            if (model.getPlan_name().matches("STARTER")) {
                mPackageItemAmount.setText("₹" + DataManager.setCommas(model.getPlan_cost().substring(0, (model.getPlan_cost().length()))));
            } else {
                mPackageItemAmount.setText("₹" + DataManager.setCommas(model.getPlan_cost().substring(0, (model.getPlan_cost().length()))));
            }

            mPackageItemDiscountPer.setVisibility(View.GONE);
        } else {
            mPackageItemCancelAmount.setVisibility(View.VISIBLE);
            mPackageItemName.setBackgroundColor(requireContext().getResources().getColor(R.color.teal_200));
            package_item_detail_title.setBackgroundColor(requireContext().getResources().getColor(R.color.teal_200));
            if (model.getPlan_name().matches("STARTER")) {
                mPackageItemCancelAmount.setText("₹" + model.getPlan_cost());
            } else {
                mPackageItemCancelAmount.setText("₹" + model.getPlan_cost());
            }
            String discountCost = DataManager.calculatePercent(model.getPlan_cost(), model.getDiscount().replaceAll("%", ""));
            mPackageItemDiscountPer.setVisibility(View.VISIBLE);
            mPackageItemDiscountPer.setText(model.getDiscount() + " OFF");
            if (model.getPlan_name().matches("STARTER")) {
                mPackageItemAmount.setText("₹" + DataManager.setCommas(discountCost.substring(0, (model.getPlan_cost().length()))));
            } else {
                mPackageItemAmount.setText("₹" + DataManager.setCommas(discountCost.substring(0, (model.getPlan_cost().length()))));
            }

        }

        if (model.getPlan_name().replaceAll("\r\n", "").matches("LITE")) {
            mPackageItemName.setBackgroundColor(requireContext().getResources().getColor(R.color.red));
            mPackageItemPopularBtn.setVisibility(View.VISIBLE);
            package_item_detail_title.setBackgroundColor(requireContext().getResources().getColor(R.color.red));

            mPackageItemAmount.setTextColor(requireContext().getResources().getColor(R.color.red));
        } else {
            mPackageItemPopularBtn.setVisibility(View.INVISIBLE);
        }

//        mPackageItemWebView.loadDataWithBaseURL(null, model.getPlan_content(), "text/html", "utf-8", null);

//        DataManager.htmlConverter(mPackageItemContent2Txt, model.getPlan_content());
//        DataManager.htmlConverter(mPackageItemContentTxt,model.getPlan_content());

        package_item_priority_search1.setText(model.getPriority_Search_Listing());
        package_item_display_pro.setText(model.getDisplay_Products());
        package_item_display_selling_lead.setText(model.getDisplay_Selling_Leads());
        package_item_number_of_enquiry.setText(model.getNumber_of_Inquiries_to_send_per_day());
        package_item_access_to_new_buy.setText(model.getAccess_to_New_Buying_Leads());
        package_item_access_to_3million.setText(model.getAccess_to_3_Million_Global_Buyer_Database());
        package_item_free_company_verification.setText(model.getFree_Company_Verification_Service());

        mPackageItemMemberCard = root.findViewById(R.id.package_item_member_card);
        mPackageItemApplyNowBtn = root.findViewById(R.id.package_item_apply_now_btn);
        mPackageItemApplyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentDialog();
            }
        });


//        setScroller(root.findViewById(R.id.package_details_scroller));

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


    private void openPaymentDialog() {

        Dialog dialog = new Dialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_apply_package, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (ViewGroup.LayoutParams.WRAP_CONTENT);
        int width = (int) (metrics.widthPixels * 0.80);

        dialog.getWindow().setLayout(width, height);


        MaterialButton mPayBtn, mEmiBtn;

        mPayBtn = dialog.findViewById(R.id.dialog_apply_package_pay_btn);
        mEmiBtn = dialog.findViewById(R.id.dialog_apply_package_emi_btn);

        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://www.shoppa.in/advertise-with-us");
                dialog.dismiss();
            }
        });
        mEmiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://pages.razorpay.com/pl_FiZRX1DB1480lC/view");
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void openWebView(String Url) {

        WebViewFragment fragment = new WebViewFragment();
        fragment.Url = Url;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("PurchasePackageFragmentAdded")
                .commit();

    }

    private void setScroller(ScrollView mPackageDetailsScroller) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPackageDetailsScroller.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.i("scroller", "onScrollChange: scrollY" + scrollY);
                    if (scrollY > 50) {
                        if (!isFadeOut) {
                            DataManager.setAlphaAnimation(mPackageItemMemberCard, "fadeOut");
                            isFadeOut = true;
                            isFadeIn = false;
                        }
                    } else if (scrollY < 50) {
                        if (!isFadeIn) {
                            DataManager.setAlphaAnimation(mPackageItemMemberCard, "fadeIn");
                            isFadeIn = true;
                            isFadeOut = false;
                        }
                    }
                }
            });
        }
    }
}