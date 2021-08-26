package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.R;

import java.util.ArrayList;

public class SupplierInfoProductAdapter extends RecyclerView.Adapter<SupplierInfoProductAdapter.SupplierInfoProductVH> {

    Context context;
    ArrayList<ProductDetailModel> mSupplierInfoProductModelArrayList;
    OnItemCLickListener listener;

    public SupplierInfoProductAdapter(Context context, ArrayList<ProductDetailModel> mSupplierInfoProductModelArrayList, OnItemCLickListener listener) {
        this.context = context;
        this.mSupplierInfoProductModelArrayList = mSupplierInfoProductModelArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SupplierInfoProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_supplier_info_prodcuts, parent, false);
        return new SupplierInfoProductAdapter.SupplierInfoProductVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierInfoProductVH holder, int position) {

        ProductDetailModel model = mSupplierInfoProductModelArrayList.get(position);

        Glide.with(context).load(model.getProductImage()).error(R.drawable.no_image_found).into(holder.mCellSupplierProductImg);
        holder.mCellSupplierProductName.setText(model.getProductName());

        holder.mCellSupplierProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSupplierInfoProductModelArrayList.size();
    }

    public interface OnItemCLickListener {
        void OnItemClick(ProductDetailModel model);

        void OnViewClick();
    }

    public static class SupplierInfoProductVH extends RecyclerView.ViewHolder {

        ShapeableImageView mCellSupplierProductImg;
        MaterialTextView mCellSupplierProductName;

        public SupplierInfoProductVH(@NonNull View itemView) {
            super(itemView);

            mCellSupplierProductImg = itemView.findViewById(R.id.cell_supplier_product_img);
            mCellSupplierProductName = itemView.findViewById(R.id.cell_supplier_product_name);

        }
    }
}
