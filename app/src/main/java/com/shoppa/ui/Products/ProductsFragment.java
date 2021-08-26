package com.shoppa.ui.Products;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.shoppa.Adapters.CustomFragmentPagerAdapter;
import com.shoppa.Adapters.SupplierInfoProductAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;
import com.shoppa.ui.AllProduct.AllProductFragment;
import com.shoppa.ui.DisapprovedProduct.DisapprovedProductFragment;
import com.shoppa.ui.GroupProduct.GroupProductFragment;
import com.shoppa.ui.PostSeller.PostSellerFragment;
import com.shoppa.ui.TrashProduct.TrashProductFragment;
import com.shoppa.ui.WebView.WebViewFragment;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {


    ArrayList<Fragment> mFragmentArrayList;
    ArrayList<String> mFragmentTitleArrayList;
    TabLayout mProductsFragmentTabLayout;
    ViewPager mProductsFragmentViewPager;
    CustomFragmentPagerAdapter mProductFragmentPagerAdapter;
    ShapeableImageView mProductFragmentAddBtn;
    Dialog dialog;
    ShapeableImageView mProductNoDataImg;
    SupplierInfoProductAdapter mSupplierProductAdapter;
    RecyclerView mProductsRecyclerView;
    Handler productHandler = new Handler();
    private ProductsViewModel mViewModel;
    Runnable productWatcher = new Runnable() {
        @Override
        public void run() {

            if (mViewModel.isResponseDone) {
                setSellerProduct();
                stopProductHandler();
            } else {
                startProductHandler();
            }

        }
    };

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.products_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        mProductsFragmentTabLayout = root.findViewById(R.id.products_fragment_tab_layout);
        mProductsFragmentViewPager = root.findViewById(R.id.products_fragment_view_pager);
        mProductFragmentAddBtn = root.findViewById(R.id.product_fragment_add_btn);
        mProductsRecyclerView = root.findViewById(R.id.products_recycler_view);
        mProductsRecyclerView.setVisibility(View.VISIBLE);
        dialog = new Dialog(requireContext());
        mProductNoDataImg = root.findViewById(R.id.product_no_data_img);
        mProductNoDataImg.setVisibility(View.GONE);
        mProductFragmentAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postSellerFragment();
                webViewFragment();
            }
        });
        DataManager.isFrom = "optionFragment";

        mViewModel.setContext(requireContext());
//        setUpViewPager();

        mViewModel.getProductData(UserDataModel.mInstance.getId());
        productWatcher.run();
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        return root;
    }

    private void setSellerProduct() {

        mProductsRecyclerView.setHasFixedSize(true);
        mProductsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        mSupplierProductAdapter = new SupplierInfoProductAdapter(requireContext(), mViewModel.getProductsList().getValue(), new SupplierInfoProductAdapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(ProductDetailModel model) {
//                productDetailFragment(model);
            }

            @Override
            public void OnViewClick() {

            }
        });
        mProductsRecyclerView.setAdapter(mSupplierProductAdapter);

        checkData(mViewModel.getProductsList().getValue());

    }

    private void checkData(ArrayList<ProductDetailModel> value) {

        if (value.size() == 0) {
            mProductNoDataImg.setVisibility(View.VISIBLE);
            Glide.with(requireContext()).load(DataManager.noDataImg).error(R.drawable.no_image_found).into(mProductNoDataImg);
            mProductsRecyclerView.setVisibility(View.GONE);
        }

    }

    private void startProductHandler() {
        productHandler.postDelayed(productWatcher, 100);
    }

    private void stopProductHandler() {
        productHandler.removeCallbacks(productWatcher);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }


    private void postSellerFragment() {

        PostSellerFragment fragment = new PostSellerFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("postSellerFragmentAdded");
        transaction.commit();

    }

    private void webViewFragment() {
        WebViewFragment fragment = new WebViewFragment();
        fragment.Url = "https://www.shoppa.in/sellers/index.php";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("webViewFragmentAdded");
        transaction.commit();
    }

    private void setUpViewPager() {

        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new AllProductFragment());
        mFragmentArrayList.add(new GroupProductFragment());
        mFragmentArrayList.add(new DisapprovedProductFragment());
        mFragmentArrayList.add(new TrashProductFragment());

        mFragmentTitleArrayList = new ArrayList<>();
        mFragmentTitleArrayList.add("All");
        mFragmentTitleArrayList.add("Group");
        mFragmentTitleArrayList.add("Disapproved");
        mFragmentTitleArrayList.add("Trash");

        setUpTabData();
        mProductFragmentPagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), mFragmentArrayList, mFragmentTitleArrayList);
        mProductsFragmentViewPager.setAdapter(mProductFragmentPagerAdapter);
        mProductsFragmentTabLayout.setupWithViewPager(mProductsFragmentViewPager);

    }

    private void setUpTabData() {

        mProductsFragmentTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        /*mProductsFragmentTabLayout.post(new Runnable() {
            @Override
            public void run() {
                // don't forget to add Tab first before measuring..
                DisplayMetrics displayMetrics = new DisplayMetrics();
                requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int widthS = displayMetrics.widthPixels;
                mProductsFragmentTabLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int widthT = mProductsFragmentTabLayout.getMeasuredWidth();
                mProductsFragmentTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                *//*if (widthS > widthT) {

         *//**//*mProductsFragmentTabLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));*//**//*
                }*//*
            }
        });*/
    }

}