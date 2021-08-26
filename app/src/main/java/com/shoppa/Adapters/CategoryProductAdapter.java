package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.CatProductModel;
import com.shoppa.R;

import java.util.ArrayList;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.CategoryProductVH> {

    public int position = 0;
    ArrayList<CatProductModel> mCatProductModelArrayList;
    OnItemClickListener listener;
    Context context;

    public CategoryProductAdapter(Context context, OnItemClickListener listener, ArrayList<CatProductModel> mCatProductModelArrayList) {
        this.context = context;
        this.listener = listener;
        this.mCatProductModelArrayList = mCatProductModelArrayList;
    }

    @NonNull
    @Override
    public CategoryProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_category_fragment, parent, false);
        return new CategoryProductAdapter.CategoryProductVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductVH holder, int position) {

        this.position = position;

        CatProductModel model = mCatProductModelArrayList.get(position);

        holder.mProductNameCell.setText(model.getProductName());

        float corner = 10;
        holder.mProductImg.setShapeAppearanceModel(holder.mProductImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, corner).build());
        holder.mProductImg.setShapeAppearanceModel(holder.mProductImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, corner).build());

        holder.mProductImg.setShapeAppearanceModel(holder.mProductImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());
        holder.mProductImg.setShapeAppearanceModel(holder.mProductImg
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomRightCorner(CornerFamily.ROUNDED, corner).build());

        Glide.with(context).load(model.getProductImage()).error(R.drawable.no_image_found).into(holder.mProductImg);


        if (model.getTotalSeller().matches("1")) {
            holder.mSellerCount.setText("Listed Seller - " + model.getTotalSeller());
        } else if (model.getTotalSeller().matches("0")) {
            holder.mSellerCount.setText("");
        } else {
            holder.mSellerCount.setText("Listed Sellers - " + model.getTotalSeller());
        }

        holder.mCategoryCellMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCatProductModelArrayList.size();
    }

    public interface OnItemClickListener {

        void OnViewClick(CatProductModel model);

        void OnItemClick();

    }

    public static class CategoryProductVH extends RecyclerView.ViewHolder {

        RelativeLayout mCategoryCellMainCard;
        MaterialTextView mProductNameCell, mSellerCount;
        ShapeableImageView mProductImg;

        public CategoryProductVH(@NonNull View itemView) {
            super(itemView);

            mCategoryCellMainCard = itemView.findViewById(R.id.category_cell_main_card);
            mProductNameCell = itemView.findViewById(R.id.product_name_cell);
            mProductImg = itemView.findViewById(R.id.product_cell_img);
            mSellerCount = itemView.findViewById(R.id.categpry_pro_cell_seller_count);
        }
    }

}
