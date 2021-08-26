package com.shoppa.ui.CompanyIntroduction;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.SupplierInfoProductAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;

import java.util.ArrayList;

public class CompanyIntroductionFragment extends Fragment {

    public static RelativeLayout mCompanyIntroductionBaseCard;
    public static ScrollView mCompanyIntroductionScrollView;
    ShapeableImageView mCompanyIntroImg, mCompanyInfoNoDataImg;
    MaterialTextView mCompanyIntroName, mCompanyIntroWebsite, mCompanyIntroDescription;
    RecyclerView mCompanyIntroRecyclerView;
    SupplierInfoProductAdapter mSupplierInfoProductAdapter;
    Dialog dialog;
    Handler mProductHandler = new Handler();
    Handler mSellerHandler = new Handler();
    private CompanyIntroductionViewModel mViewModel;
    Runnable mProductRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isProductResponseDone) {
                setProductAdapter();
                stopProductHandler();
            } else {
                startProductHandler();
            }
        }
    };
    Runnable mSellerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                stopSellerHandler();
                setSellerData(mViewModel.getSellerDetail());
            } else {
                startSellerHandler();
            }
        }
    };

    public static CompanyIntroductionFragment newInstance() {
        return new CompanyIntroductionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.company_introduction_fragment, container, false);

        mCompanyIntroductionBaseCard = root.findViewById(R.id.company_introduction_base_card);
        mCompanyIntroductionScrollView = root.findViewById(R.id.company_introduction_scroll_view);

        mCompanyIntroImg = root.findViewById(R.id.company_intro_img);
        mCompanyIntroName = root.findViewById(R.id.company_intro_name);
        mCompanyIntroDescription = root.findViewById(R.id.company_intro_description);
        mCompanyIntroWebsite = root.findViewById(R.id.company_intro_website);
        mCompanyIntroRecyclerView = root.findViewById(R.id.company_intro_recycler_view);
        mCompanyIntroRecyclerView.setVisibility(View.VISIBLE);
        mCompanyInfoNoDataImg = root.findViewById(R.id.company_info_no_data_img);
        mCompanyInfoNoDataImg.setVisibility(View.GONE);
        mViewModel = new ViewModelProvider(this).get(CompanyIntroductionViewModel.class);
        dialog = new Dialog(requireContext());

        mViewModel.setContext(requireContext());
        mViewModel.getCompanyDetails(UserDataModel.mInstance.getId());
        mSellerRunnable.run();
        mViewModel.getSellerProduct(UserDataModel.mInstance.getId());
        mProductRunnable.run();
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "open");

        return root;
    }

    private void stopProductHandler() {
        mProductHandler.removeCallbacks(mProductRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void startProductHandler() {
        mProductHandler.postDelayed(mProductRunnable, 100);
    }

    private void setProductAdapter() {
        mCompanyIntroRecyclerView.setHasFixedSize(true);
        mCompanyIntroRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mSupplierInfoProductAdapter = new SupplierInfoProductAdapter(getContext(), mViewModel.getProductList().getValue(), new SupplierInfoProductAdapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(ProductDetailModel model) {
//                productDetailFragment(model);
            }

            @Override
            public void OnViewClick() {

            }
        });

        mCompanyIntroRecyclerView.setAdapter(mSupplierInfoProductAdapter);

        checkData(mViewModel.getProductList().getValue());

    }

    private void checkData(ArrayList<ProductDetailModel> value) {
        if (value.size() == 0) {
            mCompanyInfoNoDataImg.setVisibility(View.VISIBLE);
            Log.i("NoDataImg", "checkData: NoDataCalled");
            Glide.with(requireContext()).load(DataManager.noDataImg).error(R.drawable.no_image_found).into(mCompanyInfoNoDataImg);
            mCompanyIntroRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setSellerData(LiveData<SellerDetailModel> sellerDetail) {

        SellerDetailModel model = sellerDetail.getValue();

        Glide.with(requireContext()).load(model.getSellerPostImg()).error(R.drawable.no_image_found).into(mCompanyIntroImg);
        mCompanyIntroWebsite.setText(UserDataModel.getmInstance().getSeller_website());
        mCompanyIntroName.setText(model.getCompanyName());
        DataManager.setDescriptionLinks(requireContext(), model.getSellerDescription(), mCompanyIntroDescription);

    }

    private void stopSellerHandler() {
        mSellerHandler.removeCallbacks(mSellerRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), dialog, "close");
    }

    private void startSellerHandler() {
        mSellerHandler.postDelayed(mSellerRunnable, 100);
    }


}