package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.OptionMenuModel;
import com.shoppa.R;

import java.util.ArrayList;

public class OptionElementAdapter extends RecyclerView.Adapter<OptionElementAdapter.OptionElementVh> {

    Context context;
    ArrayList<OptionMenuModel> mOptionMenuModelArrayList;
    OnItemCLickListener listener;

    public OptionElementAdapter(Context context, ArrayList<OptionMenuModel> mOptionMenuModelArrayList, OnItemCLickListener listener) {
        this.context = context;
        this.mOptionMenuModelArrayList = mOptionMenuModelArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OptionElementVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.option_elements_cell, parent, false);
        return new OptionElementAdapter.OptionElementVh(root);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionElementVh holder, int position) {
        OptionMenuModel model = mOptionMenuModelArrayList.get(position);
        Glide.with(context).load(model.getmElementAnim()).error(R.drawable.no_image_found).into(holder.mOptionElementImg);
        holder.mOptionElementTxt.setText(model.getmElementTxt());

        holder.mOptionElementMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOptionMenuModelArrayList.size();
    }

    public interface OnItemCLickListener {
        void OnViewClick(OptionMenuModel model);

        void OnItemClick();
    }

    public static class OptionElementVh extends RecyclerView.ViewHolder {
        ShapeableImageView mOptionElementImg;
        MaterialTextView mOptionElementTxt;
        LinearLayout mOptionElementMainCard;

        public OptionElementVh(@NonNull View itemView) {
            super(itemView);
            mOptionElementMainCard = itemView.findViewById(R.id.option_element_main_card);
            mOptionElementImg = itemView.findViewById(R.id.option_element_img);
            mOptionElementTxt = itemView.findViewById(R.id.option_element_text);
        }
    }

}
