package com.shoppa.ui.AllCategory;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.shoppa.Adapters.AllCategoryAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class AllCategoryFragment extends Fragment {

    RecyclerView mAllCategoryRecyclerView;
    AllCategoryAdapter mAllCategoryAdapter;
    Dialog dialog;
    SwipeRefreshLayout mALlCatSwipeLayout;
    private ShapeableImageView mAllCatNoDataFound;
    private AllCategoryViewModel mViewModel;
    private FloatingActionButton mFABHomeBtn;

    public static AllCategoryFragment newInstance() {
        return new AllCategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_category_fragment, container, false);

        mAllCatNoDataFound = root.findViewById(R.id.all_cat_no_data_found);
        mAllCatNoDataFound.setVisibility(View.GONE);
        mFABHomeBtn = root.findViewById(R.id.fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
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

                    DataManager.isFrom = "HomeFragment";
                    DashboardFragment fragment = new DashboardFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment);
                    transaction.addToBackStack("HomeFragmentAdded");
                    transaction.commit();

                    return true;
                }
                return false;
            }
        });

        mALlCatSwipeLayout = root.findViewById(R.id.all_cat_swipe_refresh);
        mALlCatSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                setCategoriesAdapter();
            }
        });

        dialog = new Dialog(requireContext());
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        mViewModel = new ViewModelProvider(this).get(AllCategoryViewModel.class);
        mAllCategoryRecyclerView = root.findViewById(R.id.all_category_recycler_view);
        mAllCategoryRecyclerView.setVisibility(View.VISIBLE);
        setCategoriesAdapter();
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


    private void checkData(ArrayList<HomeCategoryModel> value) {

        if (value.size() == 0) {
            mAllCatNoDataFound.setVisibility(View.VISIBLE);
            Glide.with(requireContext()).load(DataManager.noDataImg).error(R.drawable.no_image_found).into(mAllCatNoDataFound);
            mAllCategoryRecyclerView.setVisibility(View.GONE);
        }

    }

    private void setCategoriesAdapter() {

        checkData(DataManager.mAllCatArrayList);

        mAllCategoryRecyclerView.setHasFixedSize(true);
        mAllCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAllCategoryAdapter = new AllCategoryAdapter(getContext(), DataManager.mAllCatArrayList, new AllCategoryAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick() {
            }

            @Override
            public void OnViewClick(HomeCategoryModel model) {
                categoryFragment(model);
            }
        });
        mAllCategoryRecyclerView.setAdapter(mAllCategoryAdapter);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
        mALlCatSwipeLayout.setRefreshing(false);
    }

    private void categoryFragment(HomeCategoryModel model) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        DataManager.subCatId = model.getCategoryId();
        DataManager.subCatIsFrom = "AllCategoryFragment";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("SubCategoryFragmentAdded");
        transaction.commit();
    }

}