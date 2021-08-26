package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.TroubleTicketModel;
import com.shoppa.R;

import java.util.ArrayList;

public class TroubleTicketAdapter extends RecyclerView.Adapter<TroubleTicketAdapter.TroubleTicketVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<TroubleTicketModel> mTroubleTicketModelArrayList;

    public TroubleTicketAdapter(Context context, OnItemClickListener listener, ArrayList<TroubleTicketModel> mTroubleTicketModelArrayList) {
        this.context = context;
        this.listener = listener;
        this.mTroubleTicketModelArrayList = mTroubleTicketModelArrayList;
    }

    @NonNull
    @Override
    public TroubleTicketVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.cell_trouble_ticket, parent, false);
        return new TroubleTicketAdapter.TroubleTicketVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TroubleTicketVH holder, int position) {
        TroubleTicketModel model = mTroubleTicketModelArrayList.get(position);

        holder.mTicketNumber.setText(model.getmTicketNumber());
        holder.mTicketDate.setText(model.getmTicketDate());
        holder.mTicketTitle.setText(model.getmTicketTitle());
        holder.mTicketDescription.setText(model.getmTicketDescription());

    }

    @Override
    public int getItemCount() {
        return mTroubleTicketModelArrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick();

        void OnViewClick();
    }

    public static class TroubleTicketVH extends RecyclerView.ViewHolder {

        MaterialTextView mTicketNumber, mTicketDate, mTicketTitle, mTicketDescription;

        public TroubleTicketVH(@NonNull View itemView) {
            super(itemView);

            mTicketNumber = itemView.findViewById(R.id.cell_trouble_ticket_no);
            mTicketDate = itemView.findViewById(R.id.cell_trouble_ticket_date);
            mTicketTitle = itemView.findViewById(R.id.cell_trouble_ticket_title);
            mTicketDescription = itemView.findViewById(R.id.cell_trouble_ticket_description);

        }
    }
}
