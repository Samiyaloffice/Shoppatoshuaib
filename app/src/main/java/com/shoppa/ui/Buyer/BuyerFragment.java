package com.shoppa.ui.Buyer;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shoppa.Adapters.BuyerAdapter;
import com.shoppa.Adapters.BuyerDialogAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerModel;
import com.shoppa.R;
import com.shoppa.ui.Dashboard.DashboardFragment;

import java.util.ArrayList;

public class BuyerFragment extends Fragment {

    public ArrayList<BuyerModel> mBuyerArrayList;
    private BuyerViewModel mViewModel;
    private BuyerAdapter mBuyerAdapter;
    private RecyclerView mBuyerRecyclerView;

    private FloatingActionButton mFABHomeBtn;

    public static BuyerFragment newInstance() {
        return new BuyerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.buyer_fragment, container, false);
        mBuyerRecyclerView = root.findViewById(R.id.buyer_frag_recycler_view);
        mViewModel = new ViewModelProvider(this).get(BuyerViewModel.class);

        mFABHomeBtn = root.findViewById(R.id.buyer_fab_home_btn);
        mFABHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardFragment();
            }
        });

        setBuyerData(mBuyerArrayList);

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

    private void setBuyerData(ArrayList<BuyerModel> mBuyerArrayList) {
        mBuyerRecyclerView.setHasFixedSize(true);
        mBuyerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mBuyerAdapter = new BuyerAdapter(requireContext(), mBuyerArrayList, new BuyerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(BuyerModel model) {
                openDialog(model);
            }

            @Override
            public void OnViewClick() {

            }
        });

        mBuyerRecyclerView.setAdapter(mBuyerAdapter);

    }

    private void openDialog(BuyerModel model) {

        Dialog dialog = new Dialog(requireContext());

        dialog.setContentView(R.layout.cell_buyer_contact);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (metrics.heightPixels * 1);
        int width = (int) (metrics.widthPixels * 1);

        dialog.getWindow().setLayout(width, height);

        MaterialButton mBuyerDialogCall, mBuyerDialogEmail;
        RecyclerView mBuyerDialogRecyclerView;

        mBuyerDialogCall = dialog.findViewById(R.id.buyer_dialog_call);
        mBuyerDialogEmail = dialog.findViewById(R.id.buyer_dialog_email);
        mBuyerDialogRecyclerView = dialog.findViewById(R.id.buyer_dialog_recycler_view);

        if (model.getBuyersEmail().matches("")) {
            mBuyerDialogEmail.setVisibility(View.GONE);
        } else {
            mBuyerDialogEmail.setVisibility(View.VISIBLE);
        }
        if (model.getBuyerMobile().matches("")) {
            mBuyerDialogCall.setVisibility(View.GONE);
        } else {
            mBuyerDialogCall.setVisibility(View.VISIBLE);
        }

        mBuyerDialogCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuyerDialogRecyclerView.setVisibility(View.VISIBLE);
                mBuyerDialogRecyclerView.setHasFixedSize(true);
                mBuyerDialogRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                BuyerDialogAdapter mBuyerDialogAdapter = new BuyerDialogAdapter(requireContext(), DataManager.separateNumbers(model.getBuyerMobile()), new BuyerDialogAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(String number) {

                        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
                        }

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+91 " + number));
                        startActivity(intent);
                        dialog.dismiss();

                    }

                    @Override
                    public void OnViewClick() {

                    }
                });

                mBuyerDialogRecyclerView.setAdapter(mBuyerDialogAdapter);
            }
        });

        mBuyerDialogEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuyerDialogRecyclerView.setVisibility(View.VISIBLE);
                mBuyerDialogRecyclerView.setHasFixedSize(true);
                mBuyerDialogRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                BuyerDialogAdapter mBuyerDialogAdapter = new BuyerDialogAdapter(requireContext(), DataManager.separateNumbers(model.getBuyersEmail()), new BuyerDialogAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(String number) {


                        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
                        }

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:" + number));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Seller at Shoppa");
                        intent.putExtra(Intent.EXTRA_TEXT, "Seller Message");
                        startActivity(Intent.createChooser(intent, "Send Email"));

                    }

                    @Override
                    public void OnViewClick() {

                    }
                });

                mBuyerDialogRecyclerView.setAdapter(mBuyerDialogAdapter);
            }
        });

        dialog.show();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}