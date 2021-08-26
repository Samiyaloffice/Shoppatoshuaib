package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.PremiumSellerModel;
import com.shoppa.R;

import java.util.ArrayList;

public class HomeFeatureProductAdapter extends RecyclerView.Adapter<HomeFeatureProductAdapter.HomeFeatureProductVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<PremiumSellerModel> mPremiumSellerArrayList;

    public HomeFeatureProductAdapter(Context context, ArrayList<PremiumSellerModel> mPremiumSellerArrayList, OnItemClickListener listener) {
        this.listener = listener;
        this.mPremiumSellerArrayList = mPremiumSellerArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeFeatureProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.home_feature_product_cell, parent, false);
        return new HomeFeatureProductAdapter.HomeFeatureProductVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFeatureProductVH holder, int position) {
        float radius = 10;
        holder.mHomeSellerImg.setShapeAppearanceModel(holder.mHomeSellerImg.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, radius)
                .build());

        PremiumSellerModel model = mPremiumSellerArrayList.get(position);

        Glide.with(context).load(model.getSellerImage()).error(R.drawable.no_image_found).into(holder.mHomeSellerImg);

        holder.mHomeSellerName.setText(model.getSellerName());
        holder.mHomeSellerEmail.setText(model.getSellerEmail());
        holder.mHomeSellerNumber.setText(model.getSellerNumber());
        holder.mHomeSellerAddress.setText(model.getSellerAddress());

        holder.mFeatureProductMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPremiumSellerArrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick();

        void OnViewClick(PremiumSellerModel model);
    }

    public static class HomeFeatureProductVH extends RecyclerView.ViewHolder {

        ShapeableImageView mHomeSellerImg;
        MaterialCardView mFeatureProductMainCard;
        MaterialTextView mHomeSellerName, mHomeSellerEmail, mHomeSellerNumber, mHomeSellerAddress;


        public HomeFeatureProductVH(@NonNull View itemView) {
            super(itemView);

            mHomeSellerImg = itemView.findViewById(R.id.home_seller_img);
            mFeatureProductMainCard = itemView.findViewById(R.id.feature_product_main_card);
            mHomeSellerName = itemView.findViewById(R.id.home_seller_name);
            mHomeSellerEmail = itemView.findViewById(R.id.home_seller_email);
            mHomeSellerNumber = itemView.findViewById(R.id.home_seller_number);
            mHomeSellerAddress = itemView.findViewById(R.id.home_seller_address);
        }
    }

}
