package com.shoppa.ui.ProductDetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.DialogCallingAdapter;
import com.shoppa.Adapters.SupplierInfoProductAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.R;
import com.shoppa.RepositoryManager.SellerProductRepository;
import com.shoppa.ui.ContactShoppa.ContactShoppaFragment;
import com.shoppa.ui.Dashboard.DashboardFragment;
import com.shoppa.ui.ProductInquiry.InquiryFragment;
import com.shoppa.ui.ListedSeller.ListedSellerFragment;
import com.shoppa.ui.SupplierInfo.SupplierInfoFragment;

import java.util.ArrayList;

public class ProductDetailsFragment extends Fragment {

    private final Handler mHandler = new Handler();
    public String id = "";
    public String sellerId = "";
    public String productName = "";
    private FloatingActionButton mFABHomeBtn;
    ProductDetailModel mProductDetailModel;
    SellerDetailModel mSellerDetailModel;
    MaterialCardView mProductSupplierDetailCard;
    MaterialButton mProductDetailInquiryBtn, mProductDetailContactBtn, mProductDetailType;
    MaterialTextView mProductDetailName, mProductDetailDescription, mProductDetailPostDate, mProductDetailSellerName, mProductDetailSellerDescription, mProductDetailSellerContact, mProductDetailPrice;
    ShapeableImageView mProductDetailmage, mProductDetailSellerImg;
    DialogCallingAdapter mDialogCallingAdapter;
    RecyclerView mSupplierProductRecyclerView;
    SupplierInfoProductAdapter mSupplierProductAdapter;
    SellerProductRepository mSellerProductRepo;
    Dialog dialog;
    SwipeRefreshLayout mProductRefreshLayout;
    Handler productHandler = new Handler();
    Runnable productWatcher = new Runnable() {
        @Override
        public void run() {

            if (mSellerProductRepo.isResponseDone) {
                setSellerProduct(mSellerProductRepo.getSellerProducts());
                stopProductHandler();
            } else {
                startProductHandler();
            }

        }
    };
    private ProductDetailsViewModel mViewModel;
    private final Runnable dataWatcher = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isDone) {
                setProductData();
                setSellerDetailData();
                stopHandler();
            } else {
                startHandler();
            }
        }
    };

    public static ProductDetailsFragment newInstance() {
        return new ProductDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_details_fragment, container, false);

        dialog = new Dialog(requireContext());

        mFABHomeBtn = root.findViewById(R.id.product_detail_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });

        mProductDetailPrice = root.findViewById(R.id.product_detail_price);
        mSupplierProductRecyclerView = root.findViewById(R.id.supplier_products_recycler_view);
        mSellerProductRepo = SellerProductRepository.getInstance(requireContext());
        mProductRefreshLayout = root.findViewById(R.id.product_swipe_refresh);
        mProductRefreshLayout.setOnRefreshListener(() -> {
            mViewModel.setProductDetailData(productName, sellerId);
            DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
            dataWatcher.run();
            mProductRefreshLayout.setRefreshing(false);
        });

        /*root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    listedSellerFragment();

                    return true;
                }
                return false;
            }
        });*/


        mProductDetailName = root.findViewById(R.id.product_detail_name);
        mProductDetailDescription = root.findViewById(R.id.product_detail_description);
        mProductDetailPostDate = root.findViewById(R.id.product_detail_post_date);
        mProductDetailmage = root.findViewById(R.id.product_detail_img);
        mProductDetailSellerImg = root.findViewById(R.id.product_detail_seller_img);
        mProductDetailSellerName = root.findViewById(R.id.product_detail_seller_name);
        mProductDetailSellerDescription = root.findViewById(R.id.product_detail_seller_description);
        mProductDetailType = root.findViewById(R.id.product_detail_type);
        mProductDetailSellerContact = root.findViewById(R.id.product_detail_seller_contact);

        mProductDetailContactBtn = root.findViewById(R.id.product_detail_contact_btn);
        mProductDetailContactBtn.setOnClickListener(v -> ContactFragment());

        mProductDetailInquiryBtn = root.findViewById(R.id.product_detail_inquiry_btn);
        mProductDetailInquiryBtn.setOnClickListener(v -> InquiryFragment());

        mProductSupplierDetailCard = root.findViewById(R.id.product_supplier_detail_card);
        mProductSupplierDetailCard.setOnClickListener(v -> supplierDetailFragment());

        mViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        mViewModel.setContext(getContext(), productName, sellerId);
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

    private void listedSellerFragment() {

        ListedSellerFragment fragment = new ListedSellerFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("ProductListFragmentAdded");
        transaction.commit();

    }

    private void setSellerDetailData() {
        mSellerDetailModel = mViewModel.getSellerDetail().getValue();
        assert mSellerDetailModel != null;
        mProductDetailSellerName.setText(mSellerDetailModel.getCompanyName());
        setDescriptionLinks(mSellerDetailModel.getSellerDescription(), mProductDetailSellerDescription);
        Glide.with(requireContext()).load(mSellerDetailModel.getSellerPostImg()).error(R.drawable.no_image_found).into(mProductDetailSellerImg);
        mProductDetailType.setText(mSellerDetailModel.getType());
        mProductDetailSellerContact.setText("Contact - " + mSellerDetailModel.getSellerContact());
        mProductDetailSellerContact.setOnClickListener(v -> openCallingDialog(mSellerDetailModel.getSellerContact()));

        mSellerProductRepo.fetchSellerProducts(mSellerDetailModel.getSellerId());
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");
        productWatcher.run();

    }

    private void startProductHandler() {
        productHandler.postDelayed(productWatcher, 100);
    }

    private void stopProductHandler() {
        productHandler.removeCallbacks(productWatcher);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void setSellerProduct(LiveData<ArrayList<ProductDetailModel>> sellerProducts) {

        mSupplierProductRecyclerView.setHasFixedSize(true);
        mSupplierProductRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        mSupplierProductAdapter = new SupplierInfoProductAdapter(requireContext(), sellerProducts.getValue(), new SupplierInfoProductAdapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(ProductDetailModel model) {
                productDetailFragment(model);
            }

            @Override
            public void OnViewClick() {

            }
        });
        mSupplierProductRecyclerView.setAdapter(mSupplierProductAdapter);
    }

    private void productDetailFragment(ProductDetailModel model) {

        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.id = model.getProductId();
        Log.i("productDetails", "productDetailFragment: id - " + model.getProductId());
        fragment.sellerId = model.getSellerId();
        fragment.productName = model.getProductName();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("productDetailFragmentAdded");
        transaction.commit();

    }

    private void productListFragment(ProductDetailModel model) {
        ListedSellerFragment fragment = new ListedSellerFragment();
        DataManager.listedSellerPName = model.getProductName();
        DataManager.listedSellerPImg = model.getProductImage();
        DataManager.listedSellerPId = model.getProductId();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("ProductListFragmentAdded");
        transaction.commit();
    }

    private void stopHandler() {
        mHandler.removeCallbacks(dataWatcher);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void startHandler() {
        mHandler.postDelayed(dataWatcher, 100);
    }

    private void setProductData() {
        mProductDetailModel = mViewModel.getProductDetail().getValue();
        mProductDetailName.setText(mProductDetailModel.getProductName());
        setDescriptionLinks(mProductDetailModel.getProductDescription(), mProductDetailDescription);
        mProductDetailPostDate.setText(mProductDetailModel.getProductPostDateTime());
        Glide.with(requireContext()).load(mProductDetailModel.getProductImage()).error(R.drawable.no_image_found).into(mProductDetailmage);
        mProductDetailPostDate.setText(mProductDetailModel.getProductPostDateTime());

        if (mProductDetailModel.getSeoTitle().matches("")) {
            mProductDetailPrice.setText("Ask Seller for price");
        } else {
            mProductDetailPrice.setText("₹ " + mProductDetailModel.getSeoTitle());
        }


    }

    public void setDescriptionLinks(String descriptionText, MaterialTextView mDescriptionTxt) {
        mDescriptionTxt.setText(descriptionText);
        Linkify.addLinks(mDescriptionTxt, Linkify.WEB_URLS);
        mDescriptionTxt.setLinkTextColor(ContextCompat.getColor(requireContext(),
                R.color.linkColor));
    }

    private void ContactFragment() {

        ContactShoppaFragment fragment = new ContactShoppaFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("ContactFragmentAdded");
        transaction.commit();

    }

    private void InquiryFragment() {

        InquiryFragment fragment = new InquiryFragment();
        fragment.mProductDetailModel = mProductDetailModel;
        fragment.mSellerDetailModel = mSellerDetailModel;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("InquiryFragment");
        transaction.commit();

    }

    private void supplierDetailFragment() {

        SupplierInfoFragment fragment = new SupplierInfoFragment();
        DataManager.supplierInfoId = mSellerDetailModel.getSellerId();
        DataManager.supplierInfoIsFrom = "productDetailFragment";
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack("supplierInfoFragment");
        transaction.commit();

    }

    private void openCallingDialog(String numbers) {

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
        }

        ArrayList<String> mNumbersArrayList;

        mNumbersArrayList = DataManager.separateIds(numbers);

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_calling_option);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        MaterialTextView mDialogNumber1, mDialogNumber2;

        RecyclerView mDialogChooseNumRecyclerView = dialog.findViewById(R.id.dialog_choose_num_recycler_view);

        mDialogChooseNumRecyclerView.setHasFixedSize(true);
        mDialogChooseNumRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mDialogCallingAdapter = new DialogCallingAdapter(requireContext(), mNumbersArrayList, new DialogCallingAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick(String number) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
                dialog.dismiss();
            }
        });


        mDialogChooseNumRecyclerView.setAdapter(mDialogCallingAdapter);

        dialog.show();

    }

}