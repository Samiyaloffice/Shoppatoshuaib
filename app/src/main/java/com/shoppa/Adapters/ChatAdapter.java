package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.ChatModel;
import com.shoppa.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatVH> {

    Context context;
    ArrayList<ChatModel> mChatModelArrayList;
    OnItemClickListener listener;

    public ChatAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_chat, parent, false);
        return new ChatAdapter.ChatVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatVH holder, int position) {

        holder.mCellChatMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick();
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

    public static class ChatVH extends RecyclerView.ViewHolder {

        ShapeableImageView mCellChatCompanyImg;
        MaterialTextView mCellChatCompanyName, mCellChatMessage;
        MaterialCardView mCellChatMainCard;

        public ChatVH(@NonNull View itemView) {
            super(itemView);

            mCellChatCompanyImg = itemView.findViewById(R.id.cell_chat_company_img);
            mCellChatCompanyName = itemView.findViewById(R.id.cell_chat_company_name);
            mCellChatMessage = itemView.findViewById(R.id.cell_chat_message);
            mCellChatMainCard = itemView.findViewById(R.id.cell_chat_main_card);
        }
    }

}
