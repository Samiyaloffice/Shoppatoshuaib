package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.UserBuyRequestModel;
import com.shoppa.R;

import java.util.ArrayList;

public class BuyingRequestAdapter extends RecyclerView.Adapter<BuyingRequestAdapter.BuyingRequestVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<UserBuyRequestModel> mBuyingRequestModelArrayList;

    public BuyingRequestAdapter(Context context, OnItemClickListener listener, ArrayList<UserBuyRequestModel> mBuyingRequestModelArrayList) {
        this.context = context;
        this.listener = listener;
        this.mBuyingRequestModelArrayList = mBuyingRequestModelArrayList;
    }

    @NonNull
    @Override
    public BuyingRequestVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_buying_request, parent, false);
        return new BuyingRequestAdapter.BuyingRequestVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyingRequestVH holder, int position) {

        UserBuyRequestModel model = mBuyingRequestModelArrayList.get(position);

        holder.mCellRequestProduct.setText(model.getByr_product());
        holder.mCellRequestBuyerName.setText(model.getByr_name());
        holder.mCellRequestSellerName.setText(model.getSeller_name());
        holder.mCellRequestBuyerAddress.setText(model.getByr_address());
        holder.mCellRequestBuyerEmail.setText(model.getByr_email());
        holder.mCellRequestBuyerQuantity.setText(model.getByr_quantity());
        holder.mCellRequestBuyerUnit.setText(model.getByr_unit());
        holder.mCellRequestDateOfApplied.setText(model.getEnquiry_date());

    }

    @Override
    public int getItemCount() {
        return mBuyingRequestModelArrayList.size();
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick();
    }

    public static class BuyingRequestVH extends RecyclerView.ViewHolder {

        MaterialTextView mCellRequestProduct, mCellRequestSellerName, mCellRequestBuyerName, mCellRequestBuyerEmail, mCellRequestBuyerAddress, mCellRequestBuyerQuantity, mCellRequestBuyerUnit, mCellRequestDateOfApplied;

        ShapeableImageView mCellRequestOptionMenu;

        public BuyingRequestVH(@NonNull View itemView) {
            super(itemView);

            mCellRequestProduct = itemView.findViewById(R.id.cell_request_title);
            mCellRequestDateOfApplied = itemView.findViewById(R.id.cell_request_date_of_applied);
            mCellRequestSellerName = itemView.findViewById(R.id.cell_request_seller_name);
            mCellRequestBuyerName = itemView.findViewById(R.id.cell_request_buyer_name);
            mCellRequestBuyerEmail = itemView.findViewById(R.id.cell_request_buyer_email);
            mCellRequestBuyerAddress = itemView.findViewById(R.id.cell_request_buyer_address);
            mCellRequestOptionMenu = itemView.findViewById(R.id.cell_request_option_menu);
            mCellRequestBuyerQuantity = itemView.findViewById(R.id.cell_request_quantity);
            mCellRequestBuyerUnit = itemView.findViewById(R.id.cell_request_unit);
        }
    }
}
