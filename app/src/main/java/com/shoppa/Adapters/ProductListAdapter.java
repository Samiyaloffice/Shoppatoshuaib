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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<SellerDetailModel> mListedSellerArrayList;

    public ProductListAdapter(Context context, ArrayList<SellerDetailModel> listedSellerArrayList, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.mListedSellerArrayList = listedSellerArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ProductListVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_products_list, parent, false);
        return new ProductListAdapter.ProductListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductListVH holder, int position) {


        SellerDetailModel model = mListedSellerArrayList.get(position);

        Log.i("listedSellerData", "onBindViewHolder: company - " + position + " name - " + model.getCompanyName());

        holder.mListedSellerType.setText(model.getType());
        holder.mListedSellerCompanyName.setText(model.getCompanyName());
        Glide.with(context).load(model.getSellerPostImg()).error(R.drawable.no_image_found).into(holder.mListedSellerImg);
        holder.mListedSellerContact.setText(model.getSellerContact());
        holder.mListedSellerDescription.setText(model.getSellerDescription());
        holder.mPlanname.setText(model.getSellerPlan());

        holder.mListedSellerMainCard.setOnClickListener(v -> listener.OnItemClick(model));
        holder.mListedSellerCountNumber.setText("" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mListedSellerArrayList.size();
    }


    public interface OnItemClickListener {
        void OnItemClick(SellerDetailModel model);

        void OnViewClick();
    }

    public static class ProductListVH extends RecyclerView.ViewHolder {
        MaterialTextView mListedSellerCompanyName, mListedSellerDescription, mListedSellerContact, mListedSellerCountNumber,mPlanname;
        ShapeableImageView mListedSellerImg;
        LinearLayout mListedSellerMainCard;
        MaterialButton mListedSellerType;

        public ProductListVH(@NonNull @NotNull View itemView) {
            super(itemView);
            mListedSellerType = itemView.findViewById(R.id.listed_seller_type);
            mListedSellerCountNumber = itemView.findViewById(R.id.listed_seller_count_number);
            mListedSellerCompanyName = itemView.findViewById(R.id.listed_seller_company_name);
            mListedSellerDescription = itemView.findViewById(R.id.listed_seller_description);
            mListedSellerContact = itemView.findViewById(R.id.listed_seller_contact);
            mListedSellerImg = itemView.findViewById(R.id.listed_seller_img);
            mListedSellerMainCard = itemView.findViewById(R.id.listed_seller_main_card);
            mPlanname=itemView.findViewById(R.id.plannames);

        }
    }
}
