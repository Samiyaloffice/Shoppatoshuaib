package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.shoppa.R;

public class ChatingAdapter extends RecyclerView.Adapter<ChatingAdapter.ChatingVH> {

    Context context;
    OnItemClickListener listener;

    public ChatingAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_chating, parent, false);
        return new ChatingAdapter.ChatingVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatingVH holder, int position) {
        if (position % 2 == 0) {
            holder.mChatingUserLayout.setVisibility(View.VISIBLE);
            holder.mChatingReplyLayout.setVisibility(View.GONE);
        } else {
            holder.mChatingUserLayout.setVisibility(View.GONE);
            holder.mChatingReplyLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick();
    }

    public static class ChatingVH extends RecyclerView.ViewHolder {

        LinearLayout mChatingReplyLayout, mChatingUserLayout;
        MaterialTextView mChatUsername, mChatMessageDate, mChatUserMessage;

        public ChatingVH(@NonNull View itemView) {
            super(itemView);

            mChatUsername = itemView.findViewById(R.id.cell_chating_user_name);
            mChatMessageDate = itemView.findViewById(R.id.cell_chating_user_message_date);
            mChatUserMessage = itemView.findViewById(R.id.cell_chating_user_message);
            mChatingReplyLayout = itemView.findViewById(R.id.cell_chating_reply_layout);
            mChatingUserLayout = itemView.findViewById(R.id.cell_chating_user_layout);
        }
    }

}
