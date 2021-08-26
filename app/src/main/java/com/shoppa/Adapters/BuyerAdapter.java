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
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.BuyerModel;
import com.shoppa.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.BuyerVH> {

    Context context;
    ArrayList<BuyerModel> mBuyerArrayList;
    OnItemClickListener listener;

    public BuyerAdapter(Context context, ArrayList<BuyerModel> mBuyerArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mBuyerArrayList = mBuyerArrayList;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public BuyerVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_buyer_frag, parent, false);
        return new BuyerAdapter.BuyerVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BuyerVH holder, int position) {

        BuyerModel model = mBuyerArrayList.get(position);

        holder.mCellBuyerName.setText(model.getBuyerName());
        holder.mCellBuyerProduct.setText(model.getBuyerProduct());
        holder.mCellBuyerEmail.setText(model.getBuyersEmail());
        holder.mCellBuyerMobile.setText(model.getBuyerMobile());
        holder.mCellBuyerAddress.setText(model.getBuyersAddress());

        Glide.with(context).load(R.drawable.user_profile_avtar)
                .into(holder.mCellBuyerImage);

        holder.mCellBuyerMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBuyerArrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(BuyerModel model);

        void OnViewClick();
    }

    public static class BuyerVH extends RecyclerView.ViewHolder {

        MaterialTextView mCellBuyerName, mCellBuyerProduct, mCellBuyerEmail, mCellBuyerMobile, mCellBuyerAddress;
        MaterialCardView mCellBuyerMainCard;
        ShapeableImageView mCellBuyerImage;

        public BuyerVH(@NonNull @NotNull View itemView) {
            super(itemView);
            mCellBuyerImage = itemView.findViewById(R.id.cell_buyer_image);
            mCellBuyerName = itemView.findViewById(R.id.cell_buyer_name);
            mCellBuyerProduct = itemView.findViewById(R.id.cell_buyer_product);
            mCellBuyerEmail = itemView.findViewById(R.id.cell_buyer_email);
            mCellBuyerMobile = itemView.findViewById(R.id.cell_buyer_mobile);
            mCellBuyerAddress = itemView.findViewById(R.id.cell_buyer_address);
            mCellBuyerMainCard = itemView.findViewById(R.id.buyer_main_card);

        }
    }

}
