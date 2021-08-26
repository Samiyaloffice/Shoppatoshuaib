package com.shoppa.ui.Dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.L;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.AllProductModel;
import com.shoppa.Model.BuyerModel;
import com.shoppa.R;
import com.shoppa.ui.Buyer.BuyerFragment;
import com.shoppa.ui.Chat.ChatFragment;
import com.shoppa.ui.Home.HomeFragment;
import com.shoppa.ui.ListedSeller.ListedSellerFragment;
import com.shoppa.ui.Login.LoginFragment;
import com.shoppa.ui.Notification.NotificationFragment;
import com.shoppa.ui.OptionMenu.OptionMenuFragment;
import com.shoppa.ui.Package.PackagesFragment;
import com.shoppa.ui.PackageItem.PackageItemFragment;
import com.shoppa.ui.PostBuyer.PostBuyerFragment;
import com.shoppa.ui.PostSeller.PostSellerFragment;
import com.shoppa.ui.ProductDetails.ProductDetailsFragment;
import com.shoppa.ui.WebView.WebViewFragment;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    public static LinearLayout mDashboardHomeLayout, mDashboardPackageLayout, mDashboardChatLayout, mDashboardOptionLayout;
    public static MaterialButton mDashboardHomeBtn, mDashboardPackageBtn, mDashboardChatBtn, mDashboardOptionBtn;
    public static MaterialTextView mDashboardHomeTxt, mDashboardPackageTxt, mDashboardChatTxt, mDashboardOptionTxt;
    public static SearchView mHomeSearchView;

    public static ListView mHomeListView;
    BuyerModel mBuyerModel;
    ArrayList<AllProductModel> mAllProductArrayList;
    ArrayList<String> mDuplicateProductList;
    ArrayAdapter<String> mAllProductAdapter;
    MaterialButton mDashboardFragmentPlusBtn;
    Dialog dialog;
    ShapeableImageView mDashboardNotificationBtn;
    Handler mBuyerHandler = new Handler();
    private SharedPreferences mSharedPreference;

    private MaterialButton mDashboardBuyerCount;
    private RelativeLayout mDashboardBuyerBtn;
    private String productId = "", productName = "";
    private DashboardViewModel mViewModel;
    Runnable mBuyerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isBuyerResponseDone) {
                /*try {
                    setBuyerCount(mViewModel.getBuyerData().getValue().size());
                } catch (Exception e) {
                    e.printStackTrace();
                    mDashboardBuyerCount.setVisibility(View.GONE);
                }*/

                try {
                    mBuyerModel = mViewModel.getBuyerData().getValue().get(0);

                    DataManager.homeBuyerArrayList = mViewModel.getBuyerData().getValue();

                    Log.i("BuyerResponse", "onErrorResponse: isBuyerResponse View - True");
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(requireContext(), "No Buyer Found", Toast.LENGTH_SHORT).show();
                }

                checkLocation();
                DataManager.showDialog(getContext(), requireActivity(), dialog, "close");

                stopBuyerHandler();
            } else {
                startBuyerHandler();
            }
        }
    };
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                mAllProductArrayList = mViewModel.getAllProduct().getValue();
                setAlternateList(mAllProductArrayList);
                stopHandler();
            } else {
                startHandler();
            }
        }
    };
    private SearchView.OnCloseListener closeListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
//            mHomeSearchView.setIconified(true);
            mHomeListView.setAdapter(null);
            mHomeSearchView.onActionViewCollapsed();
            return false;
        }
    };
    private Handler mSellerHandler = new Handler();
    private Runnable mSellerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isSellerResponseDone) {
                Log.i("DashBoardDebug", "run: SellerId - " + mViewModel.getSellerId().getValue());
                if (mViewModel.getSellerId().getValue().size() > 0) {
                    productDetailFragment(mViewModel.getSellerId().getValue().get(0));
                }

                stopSellerHandler();
            } else {
                startSellerHandler();
            }
        }
    };

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    private void setBuyerCount(int size) {
       /* if (size == 0) {
            Log.i("BuyerCount", "setBuyerCount: count - "+size);
            mDashboardBuyerCount.setVisibility(View.GONE);
        } else if (size > 99) {
            Log.i("BuyerCount", "setBuyerCount: count - "+size);
            mDashboardBuyerCount.setVisibility(View.VISIBLE);
            mDashboardBuyerCount.setText("99+");
        } else {
            Log.i("BuyerCount", "setBuyerCount: count - "+size);
            mDashboardBuyerCount.setVisibility(View.VISIBLE);
            mDashboardBuyerCount.setText(size+"");
        }*/
    }

    private void stopBuyerHandler() {
        mBuyerHandler.removeCallbacks(mBuyerRunnable);
    }

    private void startBuyerHandler() {
        mBuyerHandler.postDelayed(mBuyerRunnable, 100);
    }

    private void productDetailFragment(String sellerId) {

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.id = productId;
        fragment.sellerId = sellerId;
        fragment.productName = productName;
        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("ProductDetailFragmentAdded")
                .commit();

    }

    private void listedSellerFragment() {

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        ListedSellerFragment listedSellerFragment = new ListedSellerFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, listedSellerFragment)
                .addToBackStack("ListedSellerFragmentAdded")
                .commit();

    }

    private void startSellerHandler() {
        mSellerHandler.postDelayed(mSellerRunnable, 100);
    }

    private void stopSellerHandler() {
        mSellerHandler.removeCallbacks(mSellerRunnable);
    }

    private void setAlternateList(ArrayList<AllProductModel> mAllProductArrayList) {
        mDuplicateProductList = new ArrayList<>();
        for (int i = 0; i < mAllProductArrayList.size(); i++) {
            mDuplicateProductList.add(mAllProductArrayList.get(i).getProductName());
        }
        mDuplicateProductList.add("");
        mAllProductAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_selectable_list_item, mDuplicateProductList);
    }

    private void setAllProductsList() {
        mHomeListView.setAdapter(mAllProductAdapter);

        mHomeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mAllProductArrayList.contains(query)) {
                    mAllProductAdapter.getFilter().filter(query);
                } else {
                    Toast.makeText(requireContext(), "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAllProductAdapter.getFilter().filter(newText);
                return false;
            }
        });

        mHomeSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mHomeListView.setAdapter(null);
                mHomeSearchView.onActionViewCollapsed();
                return true;
            }
        });

        mHomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("searchPosition", "onItemClick: position - " + position);
                String query = (String) parent.getItemAtPosition(position);
//                query = query.replaceAll(Pattern.quote(","), " ");
                Log.i("searchPosition", "onItemClick: query - " + query);

                for (int i = 0; i < mDuplicateProductList.size(); i++) {
                    if (query.contentEquals(mDuplicateProductList.get(i))) {
                        Log.i("searchPosition", "onItemClick: real.position - " + i + " name - " + mDuplicateProductList.get(i));
                        Log.i("searchPosition", "onItemClick: Original List - ID - " + mAllProductArrayList.get(i).getProductId() + " name - " + mAllProductArrayList.get(i).getProductName());

                        mViewModel.fetchSellerId(mAllProductArrayList.get(i).getProductName());

                        productId = mAllProductArrayList.get(i).getProductId();
                        productName = mAllProductArrayList.get(i).getProductName();
                        mSellerRunnable.run();


                        break;
                    }
                }

            }
        });
    }

    private void stopHandler() {
        mHandler.removeCallbacks(mRunnable);
//        Toast.makeText(requireContext(), "AllProductFetched", Toast.LENGTH_SHORT).show();
    }

    private void startHandler() {
        mHandler.postDelayed(mRunnable, 100);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.dashboard_fragment, container, false);

        mSharedPreference = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        dialog = new Dialog(requireContext());
        mDashboardBuyerCount = root.findViewById(R.id.dashboard_buyer_count);
        DataManager.dashboardBuyerCount = mDashboardBuyerCount;
        mDashboardBuyerBtn = root.findViewById(R.id.dashboard_buyer_btn);
        mDashboardBuyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBuyerCount();
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
                    mHomeListView.setAdapter(null);
                    mHomeSearchView.onActionViewCollapsed();

                    return true;
                }
                return false;
            }
        });

        mHomeListView = root.findViewById(R.id.home_list_view);
        mDashboardNotificationBtn = root.findViewById(R.id.dashboard_notification_btn);
        mDashboardNotificationBtn.setOnClickListener(this);
        mDashboardHomeLayout = root.findViewById(R.id.dashboard_home_layout);
        mDashboardHomeLayout.setOnClickListener(this);
        mDashboardPackageLayout = root.findViewById(R.id.dashboard_package_layout);
        mDashboardPackageLayout.setOnClickListener(this);
        mDashboardChatLayout = root.findViewById(R.id.dashboard_chat_layout);
        mDashboardChatLayout.setOnClickListener(this);
        mDashboardOptionLayout = root.findViewById(R.id.dashboard_option_layout);
        mDashboardOptionLayout.setOnClickListener(this);
        mHomeSearchView = root.findViewById(R.id.home_search_view);

        mHomeSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllProductsList();
            }
        });


        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();


        mDashboardHomeBtn = root.findViewById(R.id.dashboard_home_btn);
        mDashboardHomeBtn.setOnClickListener(this);
        mDashboardPackageBtn = root.findViewById(R.id.dashboard_package_btn);
        mDashboardPackageBtn.setOnClickListener(this);
        mDashboardChatBtn = root.findViewById(R.id.dashboard_chat_btn);
        mDashboardChatBtn.setOnClickListener(this);
        mDashboardOptionBtn = root.findViewById(R.id.dashboard_option_btn);
        mDashboardOptionBtn.setOnClickListener(this);

        mDashboardHomeTxt = root.findViewById(R.id.dashboard_home_txt);
        mDashboardPackageTxt = root.findViewById(R.id.dashboard_package_txt);
        mDashboardChatTxt = root.findViewById(R.id.dashboard_chat_txt);
        mDashboardOptionTxt = root.findViewById(R.id.dashboard_option_txt);

        mDashboardFragmentPlusBtn = root.findViewById(R.id.dashboard_fragment_plus_btn);
        mDashboardFragmentPlusBtn.setOnClickListener(this);

        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        mViewModel.setContext(getContext());

        mViewModel.fetchAllProducts();
        mRunnable.run();

//        mHomeSearchView.setOnCloseListener(closeListener);

        getBuyerData();


        return root;
    }

    private void checkBuyerCount() {
        if (!mSharedPreference.getString("auth", "null").matches("null")) {
            try {
                if (mViewModel.getBuyerData().getValue().size() != 0) {
                    buyerFragment(mViewModel.getBuyerData().getValue());
                } else {
                    DataManager.dashboardBuyerCount.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "No Buyer Available", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "No Buyer Available", Toast.LENGTH_SHORT).show();
            }
        } else {
            loginFragment();
        }
    }

    private void buyerFragment(ArrayList<BuyerModel> mBuyerArrayList) {
        BuyerFragment fragment = new BuyerFragment();
        fragment.mBuyerArrayList = mBuyerArrayList;

        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("BuyerFragmentAdded")
                .commit();
    }

    private void openSearchFragment() {
    }

    private void checkLocation() {
        switch (DataManager.isFrom) {
            case "homeFragment":
                HomeFragment();
                break;
            case "packageFragment":
                PackagesFragment();
                break;
            case "plusBtn":
                showPostDialog();
                break;
            case "loginFragment":
                loginFragment();
                break;
            case "optionFragment":
                optionFragment();
                break;
            case "chatFragment":
                chatFragment();
                break;
            default:
                HomeFragment();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mDashboardHomeLayout || v == mDashboardHomeBtn) {
            HomeFragment();
        } else if (v == mDashboardPackageLayout || v == mDashboardPackageBtn) {
            PackagesFragment();
        } else if (v == mDashboardFragmentPlusBtn) {
            showPostDialog();
        } else if (v == mDashboardChatLayout || v == mDashboardChatBtn) {
            chatFragment();
        } else if (v == mDashboardOptionLayout || v == mDashboardOptionBtn) {
            optionFragment();
        } else if (v == mDashboardNotificationBtn) {
            notificationFragment();
        }
    }

    private void notificationFragment() {

        Toast.makeText(requireContext(), "No New Notification", Toast.LENGTH_SHORT).show();

        /*NotificationFragment fragment = new NotificationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("notificationFragmentAdded");
        transaction.commit();*/

    }


    private void showPostDialog() {
//        Toast.makeText(getContext(), "PostTriggered", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.post_dialog, null);
        view.setBackgroundColor(getResources().getColor(R.color.invisible));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(view);

        MaterialCardView mPostDialogBuyer, mPostDialogSeller;

        mPostDialogBuyer = dialog.findViewById(R.id.post_dialog_buyer);
        mPostDialogSeller = dialog.findViewById(R.id.post_dialog_seller);

        mPostDialogBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBuyerFragment();
                dialog.dismiss();
            }
        });

        mPostDialogSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postSellerFragment();
                webViewFragment();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void webViewFragment() {

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        WebViewFragment fragment = new WebViewFragment();
        fragment.Url = "https://www.shoppa.in/sellers/index.php";
        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("webViewFragmentAdded")
                .commit();
    }

    private void postSellerFragment() {

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        PostSellerFragment fragment = new PostSellerFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("postSellerFragment")
                .commit();
    }

    private void postBuyerFragment() {

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        PostBuyerFragment fragment = new PostBuyerFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("PostBuyerFragment")
                .commit();
    }

    private void optionFragment() {

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        if (mSharedPreference.getString("auth", "null").matches("null")) {
            loginFragment();
        } else {
            DataManager.isFrom = "optionFragment";
            OptionMenuFragment mOptionMenuFragment = new OptionMenuFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.dashboard_frame_layout, mOptionMenuFragment)
                    .addToBackStack("homeFragmentAdded")
                    .commit();
        }
    }

    private void chatFragment() {
//        loginFragment();
//        Toast.makeText(getContext(), "ChatTriggered", Toast.LENGTH_SHORT).show();

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        ChatFragment mChatFragment = new ChatFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.dashboard_frame_layout, mChatFragment)
                .addToBackStack("homeFragmentAdded")
                .commit();

    }

    private void loginFragment() {
//        Toast.makeText(getContext(), "LoginTriggered", Toast.LENGTH_SHORT).show();

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.isFromTemp = "mainActivity";
        DataManager.loginFragment = loginFragment;
        loginFragment.show(getFragmentManager(),
                "ModalBottomSheet");

    }

    public void HomeFragment() {
//        Toast.makeText(getContext(), "HomeTriggered", Toast.LENGTH_SHORT).show();

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        DataManager.isFrom = "homeFragment";

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.mBuyerModel = mBuyerModel;
        getFragmentManager().beginTransaction()
                .replace(R.id.dashboard_frame_layout, homeFragment)
                .addToBackStack("homeFragmentAdded")
                .commit();

    }

    public void PackagesFragment() {
//        Toast.makeText(getContext(), "packageTriggerd", Toast.LENGTH_SHORT).show();

        mHomeListView.setAdapter(null);
        mHomeSearchView.onActionViewCollapsed();

        DataManager.isFrom = "packageFragment";
        PackagesFragment packageFragment = new PackagesFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.dashboard_frame_layout, packageFragment)
                .addToBackStack("PackageFragmentAdded")
                .commit();

    }

    public void getBuyerData() {
        mViewModel.fetchSellerProduct();
        DataManager.showDialog(getContext(), requireActivity(), dialog, "open");
        mBuyerRunnable.run();
    }

}