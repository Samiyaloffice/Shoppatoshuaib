package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.R;

import java.util.ArrayList;

public class CompanyProfileAdapter extends RecyclerView.Adapter<CompanyProfileAdapter.CompanyProfileVH> {

    Context context;
    ArrayList<String> mCompanyProfileArrayList;
    OnItemClickListener listener;

    public CompanyProfileAdapter(Context context, ArrayList<String> mCompanyProfileArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mCompanyProfileArrayList = mCompanyProfileArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CompanyProfileVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_company_profile, parent, false);
        return new CompanyProfileAdapter.CompanyProfileVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyProfileVH holder, int position) {

        holder.mCellCompanyProfileTxt.setText(mCompanyProfileArrayList.get(position));
        holder.mCellCompanyProfileMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCompanyProfileArrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);

        void OnViewClick();
    }

    public static class CompanyProfileVH extends RecyclerView.ViewHolder {

        MaterialTextView mCellCompanyProfileTxt;
        MaterialCardView mCellCompanyProfileMainCard;

        public CompanyProfileVH(@NonNull View itemView) {
            super(itemView);
            mCellCompanyProfileTxt = itemView.findViewById(R.id.cell_company_profile_txt);
            mCellCompanyProfileMainCard = itemView.findViewById(R.id.cell_company_profile_main_card);
        }
    }

}
