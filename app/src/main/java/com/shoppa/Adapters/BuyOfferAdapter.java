package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.BuyOfferModel;
import com.shoppa.R;

import java.util.ArrayList;

public class BuyOfferAdapter extends RecyclerView.Adapter<BuyOfferAdapter.BuyOfferVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<BuyOfferModel> mBuyOfferModelArrayList;
    InnerBuyOfferAdapter mInnerBuyOfferAdapter;

    public BuyOfferAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public BuyOfferVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_buy_offer, parent, false);
        return new BuyOfferAdapter.BuyOfferVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyOfferVH holder, int position) {

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) holder.mCellBuyOfferMainCard.getLayoutParams();
        if (position == 0) {
            layoutParams.setMargins(20, 450, 20, 20);
        } else {
            layoutParams.setMargins(20, 20, 20, 20);
        }

        holder.mCellBuyOfferMainCard.requestLayout();

        holder.mCellBuyOfferRecyclerView.setHasFixedSize(true);
        holder.mCellBuyOfferRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mInnerBuyOfferAdapter = new InnerBuyOfferAdapter(context, new InnerBuyOfferAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick() {
                listener.OnItemClick();
            }
        });
        holder.mCellBuyOfferRecyclerView.setAdapter(mInnerBuyOfferAdapter);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick();
    }

    public static class BuyOfferVH extends RecyclerView.ViewHolder {

        MaterialTextView mCellBuyOffer;
        RecyclerView mCellBuyOfferRecyclerView;
        MaterialCardView mCellBuyOfferMainCard;

        public BuyOfferVH(@NonNull View itemView) {
            super(itemView);
            mCellBuyOfferMainCard = itemView.findViewById(R.id.cell_buy_offer_main_card);
            mCellBuyOffer = itemView.findViewById(R.id.cell_buy_offer_title);
            mCellBuyOfferRecyclerView = itemView.findViewById(R.id.cell_buy_offer_recycler_view);
        }
    }
}
