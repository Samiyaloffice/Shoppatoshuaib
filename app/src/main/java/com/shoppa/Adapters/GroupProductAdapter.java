package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.ProductGroupModel;
import com.shoppa.R;

import java.util.ArrayList;

public class GroupProductAdapter extends RecyclerView.Adapter<GroupProductAdapter.GroupProductVH> {

    Context context;
    ArrayList<ProductGroupModel> mProductGroupModelArrayList;
    OnItemClickListener listener;

    public GroupProductAdapter(Context context, ArrayList<ProductGroupModel> mProductGroupModelArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mProductGroupModelArrayList = mProductGroupModelArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_group_product, parent, false);
        return new GroupProductAdapter.GroupProductVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupProductVH holder, int position) {

        ProductGroupModel model = mProductGroupModelArrayList.get(position);

        holder.mCellGroupProductName.setText(model.getmGroupName());
        holder.mCellGroupProductCount.setText("(" + model.getmGroupProductCount() + ")");

        holder.mCellGroupProductOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductGroupModelArrayList.size();
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick(int position);
    }

    public static class GroupProductVH extends RecyclerView.ViewHolder {

        MaterialTextView mCellGroupProductName, mCellGroupProductCount;
        ShapeableImageView mCellGroupProductOptionBtn;

        public GroupProductVH(@NonNull View itemView) {
            super(itemView);

            mCellGroupProductName = itemView.findViewById(R.id.cell_group_product_name);
            mCellGroupProductCount = itemView.findViewById(R.id.cell_group_product_count);
            mCellGroupProductOptionBtn = itemView.findViewById(R.id.cell_group_product_option_menu);

        }
    }
}
