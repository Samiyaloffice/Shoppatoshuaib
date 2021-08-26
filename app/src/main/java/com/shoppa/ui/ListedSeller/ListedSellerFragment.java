package com.shoppa.ui.ListedSeller;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.ProductListAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.Model.SubCategoryModel;
import com.shoppa.R;
import com.shoppa.ui.Category.CategoryFragment;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.ProductDetails.ProductDetailsFragment;
import com.shoppa.ui.SupplierInfo.SupplierInfoFragment;

import java.util.ArrayList;

public class ListedSellerFragment extends Fragment {

    public String mProductName = "";
    public String mProductImg = "";
    public String mProductId = "";
    public String SellerId = "";
    private FloatingActionButton mFABHomeBtn;
    public ArrayList<String> mListedSellerArrayList;
    Dialog dialog;
    RecyclerView mProductListRecyclerView;
    ProductListAdapter mProductListAdapter;
    MaterialTextView mProductListName;
    ShapeableImageView mProductListImg, mListedSellerNoDataFound;
    SearchView mProductListSearchView;
    Handler mSellerHandler = new Handler();
    Handler mPHandler = new Handler();
    private ListedSellerViewModel mViewModel;
    Runnable mPRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isPResponseDone) {
                productDetailsFragment(mViewModel.productId, SellerId);
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
                stopPHandler();
            } else {
                startPHandler();
            }
        }
    };
    Runnable mSellerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                setListAdapter();
                stopSellerHandler();
            } else {
                startSellerHandler();
            }
        }
    };

    public static ListedSellerFragment newInstance() {
        return new ListedSellerFragment();
    }

    private void productDetailsFragment(String productId, String sellerId) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.id = productId;
        fragment.sellerId = sellerId;
        fragment.productName = mProductName;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("ProductDetailFragmentAdded");
        transaction.commit();
    }

    private void startSellerHandler() {
        mSellerHandler.postDelayed(mSellerRunnable, 100);
    }

    private void stopSellerHandler() {
        mSellerHandler.removeCallbacks(mSellerRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ListedSellerViewModel.class);
        dialog = new Dialog(requireContext());

        mProductName = DataManager.listedSellerPName;
        mProductImg = DataManager.listedSellerPImg;
        SellerId = DataManager.listedSellerId;
        mListedSellerArrayList = DataManager.listedSellerArrayList;

        Log.i("sellersList", "onCreateView: data - " + mListedSellerArrayList);
        View root = inflater.inflate(R.layout.products_list_fragment, container, false);

        mFABHomeBtn = root.findViewById(R.id.listed_seller_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });

        mListedSellerNoDataFound = root.findViewById(R.id.listed_seller_no_data_found);
        mListedSellerNoDataFound.setVisibility(View.GONE);

        mProductListRecyclerView = root.findViewById(R.id.products_list_recycler_view);
        mProductListRecyclerView.setVisibility(View.VISIBLE);
        mProductListName = root.findViewById(R.id.products_list_name);
        mProductListName.setText(mProductName);
        mProductListImg = root.findViewById(R.id.products_list_Img);
        mProductListSearchView = root.findViewById(R.id.product_list_search_view);
        mProductListSearchView.setOnSearchClickListener(v -> mViewModel.searchItem(mProductListSearchView.getQuery().toString()));
        Glide.with(requireContext()).load(mProductImg).error(R.drawable.no_image_found).into(mProductListImg);
//        setListAdapter();

        /*root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    if (DataManager.listedSellerIsFrom.matches("catFragment")) {
                        categoryFragment();
                    } else if (DataManager.listedSellerIsFrom.matches("supplierInfoFragment")) {
                        supplierInfoFragment();
                    }

                    return true;
                }
                return false;
            }
        });*/

        mViewModel.setContext(requireContext());
        fetchSeller();
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

    private void categoryFragment() {
        CategoryFragment fragment = new CategoryFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("categoryFragmentAdded");
        transaction.commit();
    }

    private void supplierInfoFragment() {
        SupplierInfoFragment fragment = new SupplierInfoFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("supplierInfoFragmentAdded");
        transaction.commit();
    }

    private void fetchSeller() {
        mViewModel.fetchSeller(mListedSellerArrayList);
        mSellerRunnable.run();
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
    }

    private void checkData(ArrayList<SellerDetailModel> value) {
        if (value.size() == 0) {
            mListedSellerNoDataFound.setVisibility(View.VISIBLE);
            Glide.with(requireContext())
                    .load(DataManager.noDataImg)
                    .error(R.drawable.no_image_found)
                    .into(mListedSellerNoDataFound);
            mProductListRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setListAdapter() {

        checkData(mViewModel.getSellerData().getValue());

        mProductListRecyclerView.setHasFixedSize(true);
        mProductListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mProductListAdapter = new ProductListAdapter(requireContext(), mViewModel.getSellerData().getValue(), new ProductListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(SellerDetailModel model) {
                mViewModel.fetchProductId(model.getSellerId(), mProductName);
                SellerId = model.getSellerId();
                mPRunnable.run();
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
            }

            @Override
            public void OnViewClick() {

            }
        });
        mProductListRecyclerView.setAdapter(mProductListAdapter);
        mProductListAdapter.notifyDataSetChanged();

        Log.i("listedSellerData", "onBindViewHolder: listLength - " + mViewModel.getSellerData().getValue().size());
    }

    private void stopPHandler() {
        mPHandler.removeCallbacks(mPRunnable);
    }

    private void startPHandler() {
        mPHandler.postDelayed(mPRunnable, 100);
    }

}