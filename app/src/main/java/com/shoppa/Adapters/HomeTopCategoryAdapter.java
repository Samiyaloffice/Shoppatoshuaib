package com.shoppa.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.R;

import java.util.ArrayList;

public class HomeTopCategoryAdapter extends RecyclerView.Adapter<HomeTopCategoryAdapter.HomeTopCategoryVH> {

    Context context;
    OnViewClickListener listener;
    ArrayList<HomeCategoryModel> mHomeCategoryModelArrayList;

    public HomeTopCategoryAdapter(Context context, OnViewClickListener listener, ArrayList<HomeCategoryModel> mHomeCategoryModelArrayList) {
        this.context = context;
        this.listener = listener;
        this.mHomeCategoryModelArrayList = mHomeCategoryModelArrayList;
        Log.i("catData", "HomeTopCategoryAdapter: constructor called");
    }

    @NonNull
    @Override
    public HomeTopCategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_top_category_cell, parent, false);
        return new HomeTopCategoryAdapter.HomeTopCategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTopCategoryVH holder, int position) {

        /*holder.mCategoryImage.setShapeAppearanceModel(holder.mCategoryImage.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,50)
                .build());*/

        HomeCategoryModel model = mHomeCategoryModelArrayList.get(position);

        Glide.with(context).
                load(model.getCategoryImg())
                .error(R.drawable.no_image_found)
                .into(holder.mCategoryImage);

        holder.mCellHomeCategoryName.setText(model.getCategoryName());
        Log.i("catData", "onBindViewHolder: " + model.getCategoryName());

        holder.mHomeTopCategoryCellMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHomeCategoryModelArrayList.size();
    }

    public interface OnViewClickListener {
        void OnViewClick(HomeCategoryModel model);

        void OnItemClick();
    }

    public static class HomeTopCategoryVH extends RecyclerView.ViewHolder {

        ShapeableImageView mCategoryImage;
        LinearLayout mHomeTopCategoryCellMainCard;
        MaterialTextView mCellHomeCategoryName;

        public HomeTopCategoryVH(@NonNull View itemView) {
            super(itemView);

            mCategoryImage = itemView.findViewById(R.id.home_top_category_cell_img);
            mHomeTopCategoryCellMainCard = itemView.findViewById(R.id.home_top_category_cell_main_card);
            mCellHomeCategoryName = itemView.findViewById(R.id.cell_home_top_category_name);

        }
    }

}
