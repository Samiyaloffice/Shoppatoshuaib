package com.shoppa.ui.SupplierInfo;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.SupplierInfoProductAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.Home.HomeFragment;
import com.shoppa.ui.ListedSeller.ListedSellerFragment;
import com.shoppa.ui.ProductDetails.ProductDetailsFragment;

public class SupplierInfoFragment extends Fragment {

    public String id;
    private FloatingActionButton mFABHomeBtn;
    ScrollView mSupplierInfoScrollView;
    RelativeLayout mSupplierInfoBaseCard;
    RatingBar mSupplierInfoNormalRating, mSupplierInfoSrsRating;
    RecyclerView mSupplierInfoRecyclerView;
    SupplierInfoProductAdapter mSupplierInfoProductAdapter;
    MaterialTextView mSupplierInfoCompanyName, mSupplierInfoCompanyDescription, mSupplierInfoJoinDate, mSupplierInfoNormalRatingTxt, mSupplierInfoSrsRatingTxt;
    ShapeableImageView mSupplierInfoCompanyImg;
    Float c = 1.000f;
    int pos = 0;
    Dialog dialog;
    SwipeRefreshLayout mSupplierSwipeRefresh;
    Handler sellerHandler = new Handler();
    Handler dataHandler = new Handler();
    private SupplierInfoViewModel mViewModel;
    public Runnable dataWatcher = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                setProductAdapter();
                stopHandler();
            } else {
                startHandler();
            }
        }
    };
    private SellerDetailModel mSellerDetailModel;
    Runnable sellerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isSellerResponseDone) {
                mSellerDetailModel = mViewModel.getSellerDetails().getValue();
                setSellerData();
                stopSellerHandler();
            } else {
                startSellerHandler();
            }
        }
    };

    public static SupplierInfoFragment newInstance() {
        return new SupplierInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SupplierInfoViewModel.class);
        View root = inflater.inflate(R.layout.supplier_info_fragment, container, false);

        mFABHomeBtn = root.findViewById(R.id.supplier_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });

        id = DataManager.supplierInfoId;
        dialog = new Dialog(requireContext());

        mSupplierInfoJoinDate = root.findViewById(R.id.supplier_info_join_date);

        mSupplierInfoNormalRatingTxt = root.findViewById(R.id.supplier_info_normal_rating_txt);
        mSupplierInfoNormalRating = root.findViewById(R.id.supplier_info_normal_rating);
        mSupplierInfoNormalRating.setRating(DataManager.generateRandNumber());
        mSupplierInfoNormalRatingTxt.setText(String.format("%.1f", mSupplierInfoNormalRating.getRating()) + "/5");
        mSupplierInfoNormalRating.setIsIndicator(true);
        mSupplierInfoSrsRatingTxt = root.findViewById(R.id.supplier_info_srs_rating_txt);
        mSupplierInfoSrsRating = root.findViewById(R.id.supplier_info_srs_rating);
        mSupplierInfoSrsRating.setRating(DataManager.generateRandNumber());
        mSupplierInfoSrsRatingTxt.setText(String.format("%.1f", mSupplierInfoSrsRating.getRating()) + "/5");
        mSupplierInfoSrsRating.setIsIndicator(true);

        mSupplierSwipeRefresh = root.findViewById(R.id.supplier_swipe_refresh);
        mSupplierSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getSupplierInfo(id);
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                dataWatcher.run();
                mSupplierSwipeRefresh.setRefreshing(false);
            }
        });

        /*root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);````
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    if (DataManager.listedSellerIsFrom.matches("productDetailFragment")) {
                        productDetailFragment();
                    } else if (DataManager.listedSellerIsFrom.matches("homeFragment")) {
                        homeFragment();
                    }

                    return true;
                }
                return false;
            }
        });*/

        mSupplierInfoBaseCard = root.findViewById(R.id.supplier_info_base_card);
        mSupplierInfoScrollView = root.findViewById(R.id.supplier_info_scroll_view);
        mSupplierInfoRecyclerView = root.findViewById(R.id.supplier_info_recycler_view);
        mSupplierInfoCompanyName = root.findViewById(R.id.supplier_info_company_name);
        mSupplierInfoCompanyImg = root.findViewById(R.id.supplier_info_company_img);
        mSupplierInfoCompanyDescription = root.findViewById(R.id.supplier_info_company_description);
        mViewModel.setContext(getContext(), id);

//        setProductAdapter();

        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");

        dataWatcher.run();
        sellerRunnable.run();
        setScrollProperty();

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

    private void homeFragment() {
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("HomeFragmentAdded");
        transaction.commit();
    }

    private void productDetailFragment(ProductDetailModel model) {

        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.id = model.getProductId();
        fragment.sellerId = id;
        fragment.productName = model.getProductName();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("productDetailsFragmentAdded");
        transaction.commit();

    }

    private void setSellerData() {
        mSupplierInfoCompanyName.setText(mSellerDetailModel.getCompanyName());
        Glide.with(requireContext()).load(mSellerDetailModel.getSellerPostImg()).error(R.drawable.no_image_found).into(mSupplierInfoCompanyImg);
        setDescriptionLinks(mSellerDetailModel.getSellerDescription(), mSupplierInfoCompanyDescription);
        mSupplierInfoJoinDate.setText(mSellerDetailModel.getSeoUrl());
    }

    private void stopSellerHandler() {
        sellerHandler.removeCallbacks(sellerRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void startSellerHandler() {
        sellerHandler.postDelayed(sellerRunnable, 100);
    }

    private void startHandler() {
        dataHandler.postDelayed(dataWatcher, 100);
    }

    private void stopHandler() {
        dataHandler.removeCallbacks(dataWatcher);
        try {
            DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setProductAdapter() {
        mSupplierInfoRecyclerView.setHasFixedSize(true);
        mSupplierInfoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mSupplierInfoProductAdapter = new SupplierInfoProductAdapter(getContext(), mViewModel.getSellerProducts().getValue(), new SupplierInfoProductAdapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(ProductDetailModel model) {
                productDetailFragment(model);
            }

            @Override
            public void OnViewClick() {

            }
        });

        mSupplierInfoRecyclerView.setAdapter(mSupplierInfoProductAdapter);

    }

    private void listedSellerFragment(ProductDetailModel model) {

        ListedSellerFragment fragment = new ListedSellerFragment();
        DataManager.listedSellerPName = model.getProductName();
        DataManager.listedSellerIsFrom = "supplierInfoFragment";
        DataManager.listedSellerPImg = model.getProductImage();
        DataManager.listedSellerPId = model.getProductId();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("ProductDetailsFragment");
        transaction.commit();

    }

    private void setScrollProperty() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSupplierInfoScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    int a = 0, b = 500;

                    if (pos < scrollY) {
                        if (scrollY >= 500) {
                            c = 0f;
                        } else {
                            c = c - 0.02f;
                        }
                        mSupplierInfoBaseCard.setAlpha((float) c);


                        if (scrollY >= 500) {
                            pos = 500;
                        } else {
                            pos = pos + 10;
                        }

                    } else if (pos > scrollY) {
                        if (scrollY <= 0) {
                            c = 1.0f;
                        } else {
                            c = c + 0.02f;
                        }

                        mSupplierInfoBaseCard.setAlpha((float) c);


                        if (scrollY <= 0) {
                            pos = 0;
                        } else {
                            pos = pos - 10;
                        }
                    }

                }
            });
        }

    }

    public void setDescriptionLinks(String descriptionText, MaterialTextView mDescriptionTxt) {
        mDescriptionTxt.setText(descriptionText);
        Linkify.addLinks(mDescriptionTxt, Linkify.WEB_URLS);
        mDescriptionTxt.setLinkTextColor(ContextCompat.getColor(requireContext(),
                R.color.linkColor));
    }


}