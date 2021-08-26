package com.shoppa.ui.Home;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.HomeCardViewPagerAdapter;
import com.shoppa.Adapters.HomeFeatureProductAdapter;
import com.shoppa.Adapters.HomeTopCategoryAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerModel;
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.Model.PremiumSellerModel;
import com.shoppa.R;
import com.shoppa.ui.AllCategory.AllCategoryFragment;
import com.shoppa.ui.Buyer.BuyerFragment;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.LatestBuyLead.LatestBuyLeadFragment;
import com.shoppa.ui.Package.PackagesFragment;
import com.shoppa.ui.SubCategory.SubCategoryFragment;
import com.shoppa.ui.SupplierInfo.SupplierInfoFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    Timer timer;
    Handler handler;
    int page = 1;
    public static ViewPager mCardViewPager;
    public static TabLayout mHomeCardTabIndicator;
    private final Handler CategoryHandler = new Handler();
    public BuyerModel mBuyerModel;
    public SwipeRefreshLayout mHomeFragmentSwipeLayout;
    public MaterialTextView mHomeFragmentSeeAllBtn, mHomeFeatureProductSeeAllBtn, mHomeBuyerName, mHomeBuyerProduct, mHomeBuyerEmail, mHomeBuyerMobile, mHomeBuyerAddress;
    public RecyclerView mHomeFragmentRecyclerView;
    public HomeTopCategoryAdapter mHomeTopCategoryAdapter;
    public RecyclerView mHomeFeatureProductRecyclerView;
    HomeCardViewPagerAdapter mHomeCardViewPagerAdapter;
    HomeFeatureProductAdapter mHomeFeatureProductAdapter;
    boolean backRequest = false;
    Dialog mProgressDialog;
    Handler mBannerHandler = new Handler();
    private ShapeableImageView mHomeCardNoBuyerImage;
    private LinearLayout mHomeBuyerLayout;
    private HomeViewModel mViewModel;




    Runnable mBannerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isBannerResponseDone) {
                setFeatureProductAdapter();
                stopBannerHandler();
            } else {
                startBannerHandler();
            }
        }
    };


    private final Runnable repeatativeCatRunnable = new Runnable() {
        public void run() {

            Log.i("catData", "run: " + mViewModel.isCatResponseDone);

            if (mViewModel.isCatResponseDone) {
                setCategoryAdapter();
                stopCatHandler();
            } else {
                startCatHandler();
            }
        }
    };
    private MaterialCardView mHomeBuyLeadCard;
    private Handler taskHandler;
    private final Runnable repeatativeTaskRunnable = new Runnable() {
        public void run() {
            int i = mCardViewPager.getCurrentItem();
            if (i == mViewModel.getImagesList().getValue().size() - 1) {
                i = 0;
                mCardViewPager.setCurrentItem(i);
                startHandler();
            } else {
                i = i + 1;
                mCardViewPager.setCurrentItem(i);
                startHandler();
            }
        }
    };


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);

        mProgressDialog = new Dialog(requireContext());
        taskHandler = new Handler();
        Handler mBackHandler = new Handler();
        mHomeCardNoBuyerImage = root.findViewById(R.id.home_card_no_buyer_image);
        mHomeBuyerLayout = root.findViewById(R.id.home_buyer_layout);
        mHomeBuyerLayout.setVisibility(View.VISIBLE);
        handler=new Handler();
        timer=new Timer();
        viewpagerflipper();
        try {
            mHomeBuyLeadCard = root.findViewById(R.id.home_buyer_main_card);
            mHomeBuyLeadCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyerFragment(DataManager.homeBuyerArrayList);
                }
            });

            mHomeBuyerName = root.findViewById(R.id.home_buyer_name);
            mHomeBuyerName.setText(mBuyerModel.getBuyerName());
            mHomeBuyerProduct = root.findViewById(R.id.home_buyer_product);
            mHomeBuyerProduct.setText(mBuyerModel.getBuyerProduct());
            mHomeBuyerEmail = root.findViewById(R.id.home_buyer_email);
            mHomeBuyerEmail.setText(mBuyerModel.getBuyersEmail());
            mHomeBuyerMobile = root.findViewById(R.id.home_buyer_mobile);
            mHomeBuyerMobile.setText(mBuyerModel.getBuyerMobile());
            mHomeBuyerAddress = root.findViewById(R.id.home_buyer_address);
            mHomeBuyerAddress.setText(mBuyerModel.getBuyersAddress());
        } catch (Exception e) {
            e.printStackTrace();
            root.findViewById(R.id.home_buyer_main_card).setVisibility(View.GONE);
            mHomeCardNoBuyerImage.setVisibility(View.VISIBLE);
            Glide.with(requireContext())
                    .load("https://www.shoppa.in/img_for_link/no-user.jpeg")
                    .into(mHomeCardNoBuyerImage);

        }

        mHomeFragmentSwipeLayout = root.findViewById(R.id.home_fragment_refresh_layout);
        mHomeFragmentSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.setCategoryData();
                mViewModel.setPremiumSellerData();
                repeatativeCatRunnable.run();
                mBannerRunnable.run();
                DataManager.showDialog(requireContext(), requireActivity(), mProgressDialog, "open");
                setHomeCardAdapter();
                mHomeFragmentSwipeLayout.setRefreshing(false);
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


                    onBackTriggered();
                    backRequest = true;

                    mBackHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backRequest = false;
                        }
                    }, 200);

                    return true;
                }
                return false;
            }
        });
        Timer timer;

      /*  int i=0;
        for(i=0;i<=2;i++)
        {
            try {
                Thread.sleep(1000000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mCardViewPager.setCurrentItem(i);
        }*/


        DashboardFragment.mDashboardHomeBtn.setIconTintResource(R.color.icon_selected);
        DashboardFragment.mDashboardHomeTxt.setVisibility(View.VISIBLE);

        DashboardFragment.mDashboardPackageBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardPackageTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardChatBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardChatTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardOptionBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardOptionTxt.setVisibility(View.GONE);
        DashboardFragment.mHomeSearchView.setVisibility(View.VISIBLE);

        mHomeFeatureProductSeeAllBtn = root.findViewById(R.id.home_feature_product_see_all_btn);
        mHomeFeatureProductSeeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topCategoryFragment();
            }
        });
        mHomeCardTabIndicator = root.findViewById(R.id.home_card_tab_indicator);
        mHomeFragmentSeeAllBtn = root.findViewById(R.id.home_fragment_see_all_btn);
        mHomeFragmentSeeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topCategoryFragment();
            }
        });

        mHomeFragmentRecyclerView = root.findViewById(R.id.home_fragment_category_recycler_view);
        mCardViewPager = root.findViewById(R.id.home_card_view_pager);
        mHomeFeatureProductRecyclerView = root.findViewById(R.id.home_feature_product_recycler_view);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.setContext(getContext());

        DataManager.showDialog(requireContext(), requireActivity(), mProgressDialog, "open");
        repeatativeCatRunnable.run();
        mBannerRunnable.run();
        setHomeCardAdapter();
        stopHandler();
//        repeatativeTaskRunnable.run();
        return root;
    }

    private void viewpagerflipper() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i=mCardViewPager.getCurrentItem();
                        if(i==2)
                        {
                            i=0;
                            mCardViewPager.setCurrentItem(i);
                        }
                        else
                        {
                            i++;
                            mCardViewPager.setCurrentItem(i);

                        }

                    }
                });
            }
        },2000,2500);
    }

    private void buyLeadFragment() {

    }


    private void topCategoryFragment() {

        stopHandler();
        AllCategoryFragment fragment = new AllCategoryFragment();
        DataManager.mAllCatArrayList = mViewModel.getAllCatData();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("AllCategoryFragmentAdded");
        transaction.commit();

    }

    private void onBackTriggered() {
        if (backRequest) {
            requireActivity().finish();
        } else {
            Toast.makeText(getContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    void startCatHandler() {
        CategoryHandler.postDelayed(repeatativeCatRunnable, 100);

    }

    void stopCatHandler() {
        CategoryHandler.removeCallbacks(repeatativeCatRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), mProgressDialog, "close");
    }

    private void startBannerHandler() {
        mBannerHandler.postDelayed(mBannerRunnable, 100);
    }

    private void stopBannerHandler() {
        mBannerHandler.removeCallbacks(mBannerRunnable);
    }

    private void setFeatureProductAdapter() {
        mHomeFeatureProductRecyclerView.setHasFixedSize(true);
        mHomeFeatureProductRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mHomeFeatureProductAdapter = new HomeFeatureProductAdapter(getContext(), mViewModel.getPremiumSellerData().getValue(), new HomeFeatureProductAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick() {

            }

            @Override
            public void OnViewClick(PremiumSellerModel model) {
                sellerDetailFragment(model);
            }
        });
        mHomeFeatureProductRecyclerView.setAdapter(mHomeFeatureProductAdapter);
        mHomeFeatureProductRecyclerView.setNestedScrollingEnabled(false);
    }

    private void sellerDetailFragment(PremiumSellerModel model) {
        SupplierInfoFragment fragment = new SupplierInfoFragment();
        DataManager.supplierInfoId = model.getId();
        DataManager.supplierInfoIsFrom = "homeFragment";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("supplierInfoFragment");
        transaction.commit();

    }

    private void setHomeCardAdapter() {
        mHomeCardViewPagerAdapter = new HomeCardViewPagerAdapter(getContext(), mViewModel.getImagesList().getValue(), new HomeCardViewPagerAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick(String position) {
                /*if (position.matches("0")) {
                    pacakgeFragment();
                } else if (position.matches("1")) {
                    latestBuyLeadFragment();
                }*/
            }
        });
        mCardViewPager.setAdapter(mHomeCardViewPagerAdapter);
        mHomeCardTabIndicator.setupWithViewPager(mCardViewPager);
    }

    private void pacakgeFragment() {
        DataManager.isFrom = "packageFragment";
        PackagesFragment packageFragment = new PackagesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_frame_layout, packageFragment);
        transaction.addToBackStack("PackageFragmentAdded");
        transaction.commit();
    }

    private void latestBuyLeadFragment() {
        LatestBuyLeadFragment fragment = new LatestBuyLeadFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("LatestBuyLeadFragmentAdded");
        transaction.commit();
    }

    void startHandler() {
        taskHandler.postDelayed(repeatativeTaskRunnable, 5000);
    }

    void stopHandler() {
        taskHandler.removeCallbacks(repeatativeTaskRunnable);
    }


    private void setCategoryAdapter() {
        mHomeFragmentRecyclerView.setHasFixedSize(true);
        mHomeFragmentRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mHomeTopCategoryAdapter = new HomeTopCategoryAdapter(getContext(), new HomeTopCategoryAdapter.OnViewClickListener() {
            @Override
            public void OnViewClick(HomeCategoryModel model) {
                categoryFragment(model);
            }

            @Override
            public void OnItemClick() {

            }
        }, mViewModel.getCatData().getValue());
        mHomeFragmentRecyclerView.setAdapter(mHomeTopCategoryAdapter);
        mHomeFragmentRecyclerView.setNestedScrollingEnabled(false);

        mViewModel.getCatData().observe(getViewLifecycleOwner(), new Observer<ArrayList<HomeCategoryModel>>() {
            @Override
            public void onChanged(ArrayList<HomeCategoryModel> homeCategoryModels) {
                mHomeTopCategoryAdapter.notifyDataSetChanged();
                Log.i("catData", "onChanged: Data Notified");
            }
        });
    }


    private void categoryFragment(HomeCategoryModel model) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        DataManager.subCatId = model.getCategoryId();
        DataManager.subCatIsFrom = "HomeFragment";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("CategoryFragmentAdded");
        transaction.commit();

    }

    private void buyerFragment(ArrayList<BuyerModel> mBuyerArrayList) {
        BuyerFragment fragment = new BuyerFragment();
        fragment.mBuyerArrayList = mBuyerArrayList;
        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("BuyerFragmentAdded")
                .commit();
    }

}