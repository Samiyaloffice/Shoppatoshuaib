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
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.R;

import java.util.ArrayList;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryAdapterVH> {

    Context context;
    ArrayList<HomeCategoryModel> mAllCategoryModelArrayList;
    OnItemClickListener listener;

    public AllCategoryAdapter(Context context, ArrayList<HomeCategoryModel> mAllCategoryModelArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mAllCategoryModelArrayList = mAllCategoryModelArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AllCategoryAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.categories_cell, parent, false);
        return new AllCategoryAdapter.AllCategoryAdapterVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryAdapterVH holder, int position) {

        HomeCategoryModel model = mAllCategoryModelArrayList.get(position);

        float corner = 10;
        holder.mCategoryCellImageView.setShapeAppearanceModel(holder.mCategoryCellImageView
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, corner).build());
        holder.mCategoryCellImageView.setShapeAppearanceModel(holder.mCategoryCellImageView
                .getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, corner).build());
        holder.mCategoryCellImageView.setShapeAppearanceModel(holder.mCategoryCellImageView
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, corner).build());
        holder.mCategoryCellImageView.setShapeAppearanceModel(holder.mCategoryCellImageView
                .getShapeAppearanceModel()
                .toBuilder()
                .setBottomRightCorner(CornerFamily.ROUNDED, corner).build());

        Glide.with(context).load(model.getCategoryImg()).error(R.drawable.no_image_found).into(holder.mCategoryCellImageView);

        holder.mCategoryCellTxt.setText(model.getCategoryName());

        if (model.getTotalSubCat().matches("1")) {
            holder.mCategoryCellSubCategory.setText("Total Sub Category - " + model.getTotalSubCat());
        } else if (model.getTotalSubCat().matches("0")) {
            holder.mCategoryCellSubCategory.setText("");
        } else {
            holder.mCategoryCellSubCategory.setText("Total Sub Categories - " + model.getTotalSubCat());
        }

        if (model.getTotalProducts().matches("1")) {
            holder.mCategoryCellProducts.setText("Available Product - " + model.getTotalProducts());
        } else if (model.getTotalSubCat().matches("0")) {
            holder.mCategoryCellProducts.setText("");
        } else {
            holder.mCategoryCellProducts.setText("Available Products - " + model.getTotalProducts());
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
        return mAllCategoryModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public interface OnItemClickListener {
        void OnItemClick();

        void OnViewClick(HomeCategoryModel model);
    }

    public static class AllCategoryAdapterVH extends RecyclerView.ViewHolder {

        ShapeableImageView mCategoryCellImageView;
        MaterialTextView mCategoryCellTxt, mCategoryCellSubCategory, mCategoryCellProducts;
        RelativeLayout mCategoryCellMainCard;

        public AllCategoryAdapterVH(@NonNull View itemView) {
            super(itemView);

            mCategoryCellMainCard = itemView.findViewById(R.id.category_cell_main_card);
            mCategoryCellImageView = itemView.findViewById(R.id.category_cell_img);
            mCategoryCellTxt = itemView.findViewById(R.id.category_cell_txt);
            mCategoryCellSubCategory = itemView.findViewById(R.id.category_cell_sub_cat_count);
            mCategoryCellProducts = itemView.findViewById(R.id.category_cell_product_count);

        }
    }

}
