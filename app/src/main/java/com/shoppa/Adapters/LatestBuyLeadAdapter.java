package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.LatestBuyLeadModel;
import com.shoppa.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LatestBuyLeadAdapter extends RecyclerView.Adapter<LatestBuyLeadAdapter.LatestBuyLeadVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<LatestBuyLeadModel> mBuyLeadArrayList;

    public LatestBuyLeadAdapter(Context context, ArrayList<LatestBuyLeadModel> buyLeadArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mBuyLeadArrayList = buyLeadArrayList;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public LatestBuyLeadVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_latest_buy_lead, parent, false);
        return new LatestBuyLeadAdapter.LatestBuyLeadVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LatestBuyLeadVH holder, int position) {

        LatestBuyLeadModel model = mBuyLeadArrayList.get(position);

        holder.mCellBuyLeadName.setText(model.getByr_name());
        holder.mCellBuyLeadPostDate.setText(model.getEnquiry_date());
        holder.mCellBuyLeadUnit.setText(model.getByr_unit());
        holder.mCellBuyLeadQuantity.setText(model.getByr_quantity());
        holder.mCellBuyLeadProduct.setText(model.getByr_product());


    }

    @Override
    public int getItemCount() {
        return mBuyLeadArrayList.size();
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick();
    }

    public static class LatestBuyLeadVH extends RecyclerView.ViewHolder {

        MaterialTextView mCellBuyLeadName, mCellBuyLeadProduct, mCellBuyLeadQuantity, mCellBuyLeadUnit, mCellBuyLeadPostDate;

        public LatestBuyLeadVH(@NonNull @NotNull View itemView) {
            super(itemView);

            mCellBuyLeadName = itemView.findViewById(R.id.cell_buy_lead_name);
            mCellBuyLeadProduct = itemView.findViewById(R.id.cell_buy_lead_product);
            mCellBuyLeadQuantity = itemView.findViewById(R.id.cell_buy_lead_quantity);
            mCellBuyLeadUnit = itemView.findViewById(R.id.cell_buy_lead_unit);
            mCellBuyLeadPostDate = itemView.findViewById(R.id.cell_buy_lead_post_date);

        }
    }

}
