package com.shoppa.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.SubCategoryModel;
import com.shoppa.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.SubCatVH> {

    public int position = 0;
    Context context;
    ArrayList<SubCategoryModel> mSubCatModelArrayList;
    OnItemClickListener listener;

    public SubCatAdapter(Context context, ArrayList<SubCategoryModel> mSubCatModelArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mSubCatModelArrayList = mSubCatModelArrayList;
        this.listener = listener;
    }

    @NotNull
    @Override
    public SubCatVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.categories_cell, parent, false);
        return new SubCatAdapter.SubCatVH(root);
    }

    public void updateList(ArrayList<SubCategoryModel> mSubCatModelArrayList) {
        this.mSubCatModelArrayList = mSubCatModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubCatVH holder, int position) {

        this.position = position;

        Log.i("scrollerPosition", "onBindViewHolder: position - " + position);

        SubCategoryModel model = mSubCatModelArrayList.get(position);
        Glide.with(context).load(model.getSubCatImage())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.no_image_found)
                .apply(new RequestOptions().override(1080, 1080))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(holder.mCategoryCellImageView);

        holder.mCategoryCellTxt.setText(model.getSubCatName());

        holder.mCategoryCellSubCat.setVisibility(View.GONE);

        if (model.getProductCount().matches("1")) {
            holder.mCategoryCellProducts.setText("Available Product - " + model.getProductCount());
        } else if (model.getProductCount().matches("0")) {
            holder.mCategoryCellProducts.setText("");
        } else {
            holder.mCategoryCellProducts.setText("Available Products - " + model.getProductCount());
        }

        holder.mCategoryCellMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSubCatModelArrayList.size();
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick(SubCategoryModel model);
    }

    public static class SubCatVH extends RecyclerView.ViewHolder {

        ShapeableImageView mCategoryCellImageView;
        MaterialTextView mCategoryCellTxt, mCategoryCellProducts, mCategoryCellSubCat;
        RelativeLayout mCategoryCellMainCard;

        public SubCatVH(@NonNull @NotNull View itemView) {
            super(itemView);

            mCategoryCellMainCard = itemView.findViewById(R.id.category_cell_main_card);
            mCategoryCellImageView = itemView.findViewById(R.id.category_cell_img);
            mCategoryCellTxt = itemView.findViewById(R.id.category_cell_txt);
            mCategoryCellSubCat = itemView.findViewById(R.id.category_cell_sub_cat_count);
            mCategoryCellProducts = itemView.findViewById(R.id.category_cell_product_count);

        }
    }
}
