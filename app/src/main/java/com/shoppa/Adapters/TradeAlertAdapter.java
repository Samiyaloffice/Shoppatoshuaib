package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.TradeAlertModel;
import com.shoppa.R;

import java.util.ArrayList;

public class TradeAlertAdapter extends RecyclerView.Adapter<TradeAlertAdapter.TradeAlertVH> {

    Context context;
    ArrayList<TradeAlertModel> mTradeAlertArrayList;
    OnItemClickListener listener;

    public TradeAlertAdapter(Context context, ArrayList<TradeAlertModel> mTradeAlertArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mTradeAlertArrayList = mTradeAlertArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TradeAlertVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_trade_alert, parent, false);
        return new TradeAlertAdapter.TradeAlertVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeAlertVH holder, int position) {

        TradeAlertModel model = mTradeAlertArrayList.get(position);

        holder.mCellTradeAlertMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(model.getPackageName());
            }
        });
        holder.mTradeCellName.setText(model.getDiscountName());
        holder.mTradeCellPercent.setText(model.getDiscountPercent());
        holder.mTradeCellPackage.setText("Package - " + model.getPackageName().replaceAll("\r\n", ""));
        holder.mTradeCellDescription.setText(model.getDiscountDescription());
        holder.mTradeCellDate.setText(model.getPostDate());
        holder.mTradeCellDiscPercent.setText(model.getDiscountPercent().replaceAll("%", ""));


        if (model.getPackageName().replaceAll("\r\n", "").matches("LITE")) {
            holder.mCellTradePopularBtn.setVisibility(View.VISIBLE);
            holder.mTradeCellPackage.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.mTradeCellPackage.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.mCellTradePopularBtn.setVisibility(View.GONE);
        }



        /*
        holder.mCellTradeTxt.setText(mTradeAlertArrayList.get(position));
        holder.mCellTradeRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(position);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mTradeAlertArrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(String packageName);

        void OnViewClick();
    }

    public static class TradeAlertVH extends RecyclerView.ViewHolder {

        MaterialTextView mTradeCellName, mTradeCellPercent, mTradeCellPackage, mTradeCellDescription, mTradeCellDate, mTradeCellDiscPercent;
        MaterialButton mCellTradePopularBtn;
        MaterialCardView mCellTradeAlertMainCard;

        public TradeAlertVH(@NonNull View itemView) {
            super(itemView);
            mCellTradePopularBtn = itemView.findViewById(R.id.cell_trade_popular_btn);
            mTradeCellName = itemView.findViewById(R.id.trade_cell_name);
            mTradeCellPercent = itemView.findViewById(R.id.trade_cell_percent);
            mTradeCellPackage = itemView.findViewById(R.id.trade_cell_package);
            mTradeCellDescription = itemView.findViewById(R.id.trade_cell_description);
            mTradeCellDate = itemView.findViewById(R.id.trade_cell_date);
            mTradeCellDiscPercent = itemView.findViewById(R.id.trade_cell_disc_percent);

            mCellTradeAlertMainCard = itemView.findViewById(R.id.cell_trade_alert_main_card);
        }
    }

}
