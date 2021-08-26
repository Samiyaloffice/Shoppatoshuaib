package com.shoppa.ui.GroupProduct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.shoppa.Adapters.GroupProductAdapter;
import com.shoppa.Model.ProductGroupModel;
import com.shoppa.R;

import java.util.ArrayList;

public class GroupProductFragment extends Fragment {

    public static TextInputLayout mGroupProductNameTxt;
    public static MaterialButton mGroupProductSubmitBtn;
    RecyclerView mGroupProductRecyclerView;
    GroupProductAdapter mGroupProductAdapter;
    private GroupProductViewModel mViewModel;

    public static GroupProductFragment newInstance() {
        return new GroupProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.group_product_fragment, container, false);
        mGroupProductRecyclerView = root.findViewById(R.id.group_product_recycler_view);
        mGroupProductNameTxt = root.findViewById(R.id.group_product_name_txt);
        mGroupProductSubmitBtn = root.findViewById(R.id.group_product_submit_btn);

        mViewModel = new ViewModelProvider(this).get(GroupProductViewModel.class);

        mViewModel.setContext(getContext());

        mGroupProductSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setArrayListData(mGroupProductNameTxt.getEditText().getText().toString());
            }
        });

        mViewModel.getArrayListData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ProductGroupModel>>() {
            @Override
            public void onChanged(ArrayList<ProductGroupModel> productGroupModels) {
                mGroupProductAdapter.notifyDataSetChanged();
            }
        });

        setGroupProductData();

        return root;
    }

    private void setGroupProductData() {

        mGroupProductRecyclerView.setHasFixedSize(true);
        mGroupProductRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mGroupProductAdapter = new GroupProductAdapter(getContext(), mViewModel.getArrayListData().getValue(), new GroupProductAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick(int position) {
                mViewModel.removeGroup(position);
                mGroupProductAdapter.notifyDataSetChanged();
            }
        });

        mGroupProductRecyclerView.setAdapter(mGroupProductAdapter);

    }

}