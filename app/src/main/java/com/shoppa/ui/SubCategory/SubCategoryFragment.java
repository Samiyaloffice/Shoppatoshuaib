package com.shoppa.ui.SubCategory;

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
import com.shoppa.Adapters.SubCatAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.Model.SubCategoryModel;
import com.shoppa.R;
import com.shoppa.ui.AllCategory.AllCategoryFragment;
import com.shoppa.ui.Category.CategoryFragment;
import com.shoppa.ui.Dashboard.DashboardFragment;

import java.util.ArrayList;

public class SubCategoryFragment extends Fragment {

    private final Handler CategoryHandler = new Handler();
    RecyclerView mSubCategoryRecyclerView;
    SubCatAdapter mSubCategoryAdapter;
    Dialog dialog;
    SwipeRefreshLayout mSubCatRefreshLayout;
    private ShapeableImageView mSubCatNoDataFound;
    private SubCategoryViewModel mViewModel;
    private final Runnable repeatativeRunnable = new Runnable() {
        public void run() {

            if (mViewModel.isResponseDone) {
                setCategoriesAdapter();
                stopHandler();
            } else {
                startHandler();
            }
        }
    };
    private FloatingActionButton mFABHomeBtn;

    public static SubCategoryFragment newInstance() {
        return new SubCategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sub_category_fragment, container, false);
        dialog = new Dialog(requireContext());

        mViewModel = new ViewModelProvider(this).get(SubCategoryViewModel.class);
        mSubCategoryRecyclerView = root.findViewById(R.id.sub_category_recycler_view);
        mSubCategoryRecyclerView.setVisibility(View.VISIBLE);
        mFABHomeBtn = root.findViewById(R.id.sub_cat_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });
        mSubCatNoDataFound = root.findViewById(R.id.sub_cat_no_data_found);
        mSubCatNoDataFound.setVisibility(View.GONE);

        mSubCatRefreshLayout = root.findViewById(R.id.sub_cat_swipe_layout);
        mSubCatRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*mViewModel.setSubCatData(id);
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                repeatativeRunnable.run();*/

                SubCategoryFragment fragment = new SubCategoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack("subCatFragmentAdded");
                transaction.commit();


                mSubCatRefreshLayout.setRefreshing(false);
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

                    if (DataManager.subCatIsFrom.matches("AllCategoryFragment")) {
                        AllCategoryFragment fragment = new AllCategoryFragment();

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, fragment);
                        transaction.addToBackStack("AllCategoryFragmentAdded");
                        transaction.commit();
                    } else if (DataManager.subCatIsFrom.matches("HomeFragment")) {
                        DataManager.isFrom = "HomeFragment";
                        DashboardFragment fragment = new DashboardFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, fragment);
                        transaction.addToBackStack("HomeFragmentAdded");
                        transaction.commit();
                    }

                    return true;
                }
                return false;
            }
        });

        mViewModel.setContext(getContext(), DataManager.subCatId);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        repeatativeRunnable.run();

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

    void startHandler() {
        CategoryHandler.postDelayed(repeatativeRunnable, 100);
    }

    void stopHandler() {
        CategoryHandler.removeCallbacks(repeatativeRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void checkData(ArrayList<SubCategoryModel> value) {

        if (value.size() == 0) {
            mSubCatNoDataFound.setVisibility(View.VISIBLE);
            Glide.with(requireContext()).load(DataManager.noDataImg).error(R.drawable.no_image_found).into(mSubCatNoDataFound);
            mSubCategoryRecyclerView.setVisibility(View.GONE);
        }

    }

    private void setCategoriesAdapter() {

        checkData(mViewModel.getSubCatData().getValue());

        mSubCategoryRecyclerView.setHasFixedSize(true);
        mSubCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSubCategoryAdapter = new SubCatAdapter(getContext(), mViewModel.getSubCatData().getValue(), new SubCatAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick(SubCategoryModel model) {
                categoryFragment(model);
            }
        });

        mSubCategoryRecyclerView.setAdapter(mSubCategoryAdapter);

        mSubCategoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mViewModel.mSubCatModelArrayList.size() != (mSubCategoryAdapter.position + 1)) {
                        mViewModel.setDuplicateList(mSubCategoryAdapter.position);
                        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
                    }
                }
            }
        });

        mViewModel.getSubCatData().observe(getViewLifecycleOwner(), new Observer<ArrayList<SubCategoryModel>>() {
            @Override
            public void onChanged(ArrayList<SubCategoryModel> subCategoryModels) {
                mSubCategoryAdapter.notifyDataSetChanged();
                DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
            }
        });
    }

    private void categoryFragment(SubCategoryModel model) {

        CategoryFragment fragment = new CategoryFragment();
        DataManager.catProListName = model.getSubCatName();
        DataManager.catProListId = model.getSubCatId();
        DataManager.catProListIsFrom = "SubCategoryFragment";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack("CategoryFragmentAdded").commit();

    }
}