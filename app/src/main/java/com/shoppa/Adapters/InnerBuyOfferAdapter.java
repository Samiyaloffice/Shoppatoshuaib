package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.InnerBuyOfferModel;
import com.shoppa.R;

import java.util.ArrayList;

public class InnerBuyOfferAdapter extends RecyclerView.Adapter<InnerBuyOfferAdapter.InnerBuyOfferVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<InnerBuyOfferModel> mInnerBuyOfferModelArrayList;

    public InnerBuyOfferAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InnerBuyOfferVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.inner_cell_buy_offer, parent, false);
        return new InnerBuyOfferAdapter.InnerBuyOfferVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerBuyOfferVH holder, int position) {
        holder.mInnerCellBuyOfferQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick();
    }

    public static class InnerBuyOfferVH extends RecyclerView.ViewHolder {
        MaterialTextView mInnerCellBuyOfferTitle, mInnerCellBuyOfferDate, mInnerCellBuyOfferDescription, mInnerCellBuyOfferCompanyName, mInnerCellBuyOfferQuoteBtn;
        ShapeableImageView mInnerCellBuyOfferCountryImg;

        public InnerBuyOfferVH(@NonNull View itemView) {
            super(itemView);

            mInnerCellBuyOfferTitle = itemView.findViewById(R.id.inner_cell_buy_offer_title);
            mInnerCellBuyOfferDate = itemView.findViewById(R.id.inner_cell_buy_offer_date);
            mInnerCellBuyOfferDescription = itemView.findViewById(R.id.inner_cell_buy_offer_description);
            mInnerCellBuyOfferCompanyName = itemView.findViewById(R.id.inner_cell_buy_offer_company_name);
            mInnerCellBuyOfferQuoteBtn = itemView.findViewById(R.id.inner_cell_buy_offer_quote_btn);
            mInnerCellBuyOfferCountryImg = itemView.findViewById(R.id.inner_cell_buy_offer_country_img);

        }
    }
}
