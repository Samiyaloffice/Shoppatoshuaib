package com.shoppa.ui.Category;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.CategoryProductAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.CatProductModel;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.ListedSeller.ListedSellerFragment;
import com.shoppa.ui.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private final Handler mHandler = new Handler();
    RecyclerView mCategoryFragmentRecyclerView;
    CategoryProductAdapter mCategoryProductAdapter;
    MaterialTextView mCategoryNameFragmentTxt;
    Dialog dialog;
    SwipeRefreshLayout mCatSwipeRefresh;
    private ShapeableImageView mCatProNoDataFound;
    private CategoryViewModel mViewModel;
    private final Runnable dataWatcher = new Runnable() {
        @Override
        public void run() {

            if (mViewModel.isResponseDone) {
                stopHandler();
                setCategoryAdapter();
            } else {
                startHandler();
            }

        }
    };
    private FloatingActionButton mFABHomeBtn;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        mFABHomeBtn = root.findViewById(R.id.cat_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });

        mCatProNoDataFound = root.findViewById(R.id.cat_pro_no_data_found);
        mCatProNoDataFound.setVisibility(View.VISIBLE);
        dialog = new Dialog(requireContext());
        mCategoryNameFragmentTxt = root.findViewById(R.id.cat_name_fragment);
        mCategoryNameFragmentTxt.setText(DataManager.catProListName);
        mCategoryFragmentRecyclerView = root.findViewById(R.id.category_fragment_recycler_view);
        mCategoryFragmentRecyclerView.setVisibility(View.VISIBLE);

        mCatSwipeRefresh = root.findViewById(R.id.cat_swipe_refresh);
        mCatSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*mViewModel.setCatProductData(id);
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                dataWatcher.run();*/

                CategoryFragment fragment = new CategoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack("CategoryFragmentAdded");
                transaction.commit();

                mCatSwipeRefresh.setRefreshing(false);
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

                    if (DataManager.catProListIsFrom.matches("SubCategoryFragment")) {
                        SubCategoryFragment fragment = new SubCategoryFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, fragment);
                        transaction.addToBackStack("SubCategoryFragmentAdded");
                        transaction.commit();
                    }

                    return true;
                }
                return false;
            }
        });

        mViewModel.setContext(getContext(), DataManager.catProListId);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        dataWatcher.run();

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

    private void startHandler() {
        mHandler.postDelayed(dataWatcher, 100);
    }

    private void stopHandler() {
        mHandler.removeCallbacks(dataWatcher);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }


    private void checkData(ArrayList<CatProductModel> value) {

        if (value.size() == 0) {
            mCatProNoDataFound.setVisibility(View.VISIBLE);
            Glide.with(requireContext()).load(DataManager.noDataImg).error(R.drawable.no_image_found).into(mCatProNoDataFound);
            mCategoryFragmentRecyclerView.setVisibility(View.GONE);
        }

    }

    private void setCategoryAdapter() {

        checkData(mViewModel.getProductData().getValue());

        mCategoryFragmentRecyclerView.setHasFixedSize(true);
        mCategoryFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategoryProductAdapter = new CategoryProductAdapter(getContext(), new CategoryProductAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick(CatProductModel model) {
//                productDetailFragment(model);
                productListFragment(model);
            }

            @Override
            public void OnItemClick() {

            }
        }, mViewModel.getProductData().getValue());

        mCategoryFragmentRecyclerView.setAdapter(mCategoryProductAdapter);

        mCategoryFragmentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mViewModel.mCatProductsModelArrayList.size() != (mCategoryProductAdapter.position + 1)) {
                        mViewModel.setDuplicateList(mCategoryProductAdapter.position);
                        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                    }
                }
            }
        });

        mViewModel.getProductData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CatProductModel>>() {
            @Override
            public void onChanged(ArrayList<CatProductModel> catProductModels) {
                mCategoryProductAdapter.notifyDataSetChanged();
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
            }
        });
    }

    private void productListFragment(CatProductModel model) {

        ListedSellerFragment fragment = new ListedSellerFragment();
        DataManager.listedSellerPName = model.getProductName();
        DataManager.listedSellerIsFrom = "catFragment";
        DataManager.listedSellerPImg = model.getProductImage();
        DataManager.listedSellerId = model.getProductId();
        DataManager.listedSellerArrayList = model.getmListedSellerArrayList();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("ProductListFragmentAdded");
        transaction.commit();
    }

    /*private void productDetailFragment(CatProductModel model) {

        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.id = model.getProductId();
        fragment.sellerId = model.getSellerId();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("ProductDetailsFragmentAdded");
        transaction.commit();

    }*/


}