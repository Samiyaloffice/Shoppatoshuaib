package com.shoppa.ui.OptionMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.OptionElementAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.LoginActivity;
import com.shoppa.Model.OptionMenuModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;
import com.shoppa.ui.BuyOffers.BuyOffersFragment;
import com.shoppa.ui.BuyingRequest.BuyingRequestFragment;
import com.shoppa.ui.CompanyProfile.ComapnyProfileFragment;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.Products.ProductsFragment;
import com.shoppa.ui.TradeAlerts.TradeAlertsFragment;
import com.shoppa.ui.TroubleTickets.TroubleTicketsFragment;
import com.shoppa.ui.UserProfile.UserProfileFragment;
import com.shoppa.ui.Version.VersionFragment;

public class OptionMenuFragment extends Fragment implements View.OnClickListener {

    public static ScrollView mOptionMenuScrollView;
    public static ShapeableImageView mOptionFragmentBackImage, mOptionFragmentFrontImage, mOptionFragmentBottomImage, mOptionFragmentUserImage;
    public static RecyclerView mOptionElementsRecyclerView;
    public static OptionElementAdapter mOptionElementAdapter;
    public static FloatingActionButton mOptionElementEditBtn;
    public static ProgressBar mOptionElementProgressBar;
    public static LinearLayout mOptionElementLogoutBtn, mOptionElementVersionBtn;
    SwipeRefreshLayout mOptionMenuSwipeRefresh;
    MaterialTextView mOptionFragmentUserName, mOptionFragmentUserEmail, mOptionFragmentUserType, mOptionFragmentUserStatus, mOptionFragmentProgressTxt;
    Dialog dialog;
    boolean backRequest = false;
    private OptionMenuViewModel mViewModel;

    public static OptionMenuFragment newInstance() {
        return new OptionMenuFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        DataManager.checkUserStatus();
        DataManager.refreshUser(requireContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.option_menu_fragment, container, false);

        DataManager.checkUserStatus();

        mOptionMenuSwipeRefresh = root.findViewById(R.id.option_menu_swipe_refresh);
        mOptionMenuSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataManager.refreshUser(requireContext());
                optionMenuFragment();
                mOptionMenuSwipeRefresh.setRefreshing(false);
            }
        });

        Handler mBackHandler = new Handler();
        dialog = new Dialog(requireContext());
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
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
                    }, 2000);

                    return true;
                }
                return false;
            }
        });

        mOptionFragmentUserName = root.findViewById(R.id.option_fragment_user_name);
        mOptionFragmentUserName.setText(UserDataModel.getmInstance().getSeller_name());
        mOptionFragmentUserEmail = root.findViewById(R.id.option_fragment_user_email);
        mOptionFragmentUserEmail.setText(UserDataModel.getmInstance().getSeller_email());

        mOptionFragmentUserImage = root.findViewById(R.id.option_fragment_user_img);

        Log.i("userProfileImg", "onCreateView: " + UserDataModel.getmInstance().getSeller_image());
        String img = UserDataModel.getmInstance().getSeller_image();
        Glide.with(requireContext())
                .load(img)
                .error(R.drawable.user_profile_avtar)
                .into(mOptionFragmentUserImage);

        mOptionMenuScrollView = root.findViewById(R.id.option_menu_scroll_view);
        mOptionFragmentBackImage = root.findViewById(R.id.option_fragment_back_image);
        mOptionFragmentFrontImage = root.findViewById(R.id.option_fragment_front_image);
        mOptionFragmentBottomImage = root.findViewById(R.id.option_fragment_bottom_image);
        mOptionElementEditBtn = root.findViewById(R.id.option_element_edit_btn);
        mOptionElementProgressBar = root.findViewById(R.id.option_frag_progress_bar);
        mOptionElementProgressBar.setProgress(DataManager.userProgress);
        mOptionFragmentProgressTxt = root.findViewById(R.id.option_frag_progress);
        mOptionFragmentProgressTxt.setText(DataManager.userProgress + "%");
        mOptionFragmentUserType = root.findViewById(R.id.option_frag_user_type);
        mOptionFragmentUserType.setText(DataManager.userType);
        mOptionFragmentUserStatus = root.findViewById(R.id.option_frag_user_status);
        mOptionFragmentUserStatus.setText(DataManager.userStatus);
        mOptionElementLogoutBtn = root.findViewById(R.id.option_element_logout_btn);
        mOptionElementLogoutBtn.setOnClickListener(this);
        mOptionElementVersionBtn = root.findViewById(R.id.option_element_version_btn);
        mOptionElementVersionBtn.setOnClickListener(this);

        DashboardFragment.mDashboardOptionBtn.setIconTintResource(R.color.icon_selected);
        DashboardFragment.mDashboardOptionTxt.setVisibility(View.VISIBLE);

        DashboardFragment.mDashboardPackageBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardPackageTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardHomeBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardHomeTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardChatBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardChatTxt.setVisibility(View.GONE);
        DashboardFragment.mHomeSearchView.setVisibility(View.GONE);
        mOptionElementsRecyclerView = root.findViewById(R.id.option_element_recycler_view);

        mViewModel = new ViewModelProvider(this).get(OptionMenuViewModel.class);

        setOptionsElementData();
        setOptionFragmentView();

        mOptionElementEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfileFragment();
            }
        });

        return root;
    }

    private void optionMenuFragment() {
        OptionMenuFragment fragment = new OptionMenuFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_frame_layout, fragment);
        transaction.addToBackStack("OptionMenuFragmentAdded");
        transaction.commit();
    }

    private void onBackTriggered() {
        if (backRequest) {
            requireActivity().finish();
        } else {
            Toast.makeText(getContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setOptionFragmentView() {

        mOptionMenuScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int a = 434;
                if (scrollY < a) {
                    mOptionElementEditBtn.show();
                } else {
                    mOptionElementEditBtn.hide();
                }

            }//434
        });

        float corner = 100;
        mOptionFragmentBackImage.setShapeAppearanceModel(mOptionFragmentBackImage
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, corner).build());

        mOptionFragmentFrontImage.setShapeAppearanceModel(mOptionFragmentFrontImage
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, corner).build());

        mOptionFragmentFrontImage.setShapeAppearanceModel(mOptionFragmentFrontImage
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());

        mOptionFragmentBottomImage.setShapeAppearanceModel(mOptionFragmentBottomImage
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, corner).build());

        mOptionFragmentBottomImage.setShapeAppearanceModel(mOptionFragmentBottomImage
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());
    }

    public void userProfileFragment() {

        UserProfileFragment userProfileFragment = new UserProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, userProfileFragment);
        transaction.addToBackStack("PackageFragmentAdded");
        transaction.commit();

    }

    private void setOptionsElementData() {
        mOptionElementsRecyclerView.setHasFixedSize(true);
        mOptionElementsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mOptionElementAdapter = new OptionElementAdapter(getContext(), mViewModel.getElementsData().getValue(), new OptionElementAdapter.OnItemCLickListener() {
            @Override
            public void OnViewClick(OptionMenuModel model) {

                switch (model.getmElementTxt()) {
                    case "Buy Offers":
                        buyOffersFragment();
                        break;
                    case "Products":
                        productsFragment();
                        break;
                    case "Company Profile":
                        companyOffersFragment();
                        break;
                    case "Buying Requests":
                        buyingRequestFragment();
                        break;
                    case "Trade Alerts":
                        tradeAlertFragment();
                        break;
//                    case "Account Settings":
//                        accountSettingsFragment();
//                        break;
                    case "Trouble Ticket":
                        troubleTicketFragment();
                        break;
//                    case "Contact Us":
//                        contactUsFragment();
//                        break;

                }

            }

            @Override
            public void OnItemClick() {

            }
        });
        mOptionElementsRecyclerView.setAdapter(mOptionElementAdapter);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");

    }

/*    private void contactUsFragment() {
        ContactUsFragment fragment = new ContactUsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack("contactUsFragmentAdded").commit();
    }*/

    private void buyOffersFragment() {
        BuyOffersFragment buyOffersFragment = new BuyOffersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, buyOffersFragment).addToBackStack("buyOffersFragmentAdded").commit();
    }

    private void productsFragment() {
        ProductsFragment productsFragment = new ProductsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, productsFragment).addToBackStack("ProductFragmentAdded").commit();
    }

    private void companyOffersFragment() {
        ComapnyProfileFragment comapnyProfileFragment = new ComapnyProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, comapnyProfileFragment).addToBackStack("companyOffersFragment").commit();
    }

    private void buyingRequestFragment() {
        BuyingRequestFragment buyingRequestFragment = new BuyingRequestFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, buyingRequestFragment).addToBackStack("buyingRequestFragment").commit();
    }

    private void tradeAlertFragment() {
        TradeAlertsFragment tradeAlertsFragment = new TradeAlertsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, tradeAlertsFragment).addToBackStack("tradeAlertFragment").commit();
    }

//    private void accountSettingsFragment() {
//        AccountSettingsFragment accountSettingsFragment = new AccountSettingsFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.nav_host_fragment, accountSettingsFragment).addToBackStack("accountSettingsFragment").commit();
//    }

    private void troubleTicketFragment() {
        TroubleTicketsFragment troubleTicketsFragment = new TroubleTicketsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, troubleTicketsFragment).addToBackStack("troubleTicketFragment").commit();
    }

    private void versionFragment() {
        VersionFragment versionFragment = new VersionFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, versionFragment).addToBackStack("versionFragmentAdded").commit();
    }

    @Override
    public void onClick(View v) {
        if (v == mOptionElementVersionBtn) {
            versionFragment();
        } else if (v == mOptionElementLogoutBtn) {
            logoutApplication();
        }
    }

    private void logoutApplication() {

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("auth", null);
        editor.putString("email", "");
        editor.putString("password", "");
        editor.apply();

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        onDestroy();
        requireActivity().finish();

    }
}