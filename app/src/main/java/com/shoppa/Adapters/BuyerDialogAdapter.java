package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.shoppa.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BuyerDialogAdapter extends RecyclerView.Adapter<BuyerDialogAdapter.BuyerDialogVH> {

    Context context;
    ArrayList<String> mContactArrayList;
    OnItemClickListener listener;

    public BuyerDialogAdapter(Context context, ArrayList<String> mContactArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mContactArrayList = mContactArrayList;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public BuyerDialogVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_buyer_dialog, parent, false);
        return new BuyerDialogAdapter.BuyerDialogVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BuyerDialogVH holder, int position) {

        holder.mBuyerCellDialogTxt.setText(mContactArrayList.get(position));
        holder.mBuyerCellDialogTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(mContactArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContactArrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(String number);

        void OnViewClick();
    }

    public static class BuyerDialogVH extends RecyclerView.ViewHolder {

        MaterialTextView mBuyerCellDialogTxt;

        public BuyerDialogVH(@NonNull @NotNull View itemView) {
            super(itemView);
            mBuyerCellDialogTxt = itemView.findViewById(R.id.buyer_cell_dialog_txt);
        }
    }
}
