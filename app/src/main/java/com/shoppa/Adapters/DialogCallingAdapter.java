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

public class DialogCallingAdapter extends RecyclerView.Adapter<DialogCallingAdapter.DialogCallingVH> {

    Context context;
    ArrayList<String> mNumbersArrayList;
    OnItemClickListener listener;

    public DialogCallingAdapter(Context context, ArrayList<String> mNumbersArrayList, OnItemClickListener listener) {
        this.context = context;
        this.mNumbersArrayList = mNumbersArrayList;
        this.listener = listener;
    }

    @NotNull
    @Override
    public DialogCallingVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_calling_cell, parent, false);
        return new DialogCallingAdapter.DialogCallingVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DialogCallingVH holder, int position) {
        holder.mCallingTxt.setText(mNumbersArrayList.get(position));

        holder.mCallingTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(mNumbersArrayList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNumbersArrayList.size();
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick(String number);
    }

    public static class DialogCallingVH extends RecyclerView.ViewHolder {

        MaterialTextView mCallingTxt;

        public DialogCallingVH(@NonNull @NotNull View itemView) {
            super(itemView);
            mCallingTxt = itemView.findViewById(R.id.number_txt);
        }
    }


}
