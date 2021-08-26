package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.PackageModel;
import com.shoppa.R;

import java.util.ArrayList;

public class PackageFragmentCellAdapter extends RecyclerView.Adapter<PackageFragmentCellAdapter.PackageFragmentCellVH> {

    Context context;
    OnItemClickListener listener;
    ArrayList<PackageModel> mPackageArrayList;

    public PackageFragmentCellAdapter(Context context, ArrayList<PackageModel> mPackageArrayList, OnItemClickListener listener) {
           this.listener = listener;
        this.mPackageArrayList = mPackageArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PackageFragmentCellVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.package_fragment_sample_cell, parent, false);
        return new PackageFragmentCellAdapter.PackageFragmentCellVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageFragmentCellVH holder, int position) {


        PackageModel model = mPackageArrayList.get(position);
        holder.mPackageTitle.setText(model.getPlan_name());

        holder.mPackageCellMainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick(model);
            }
        });

        if (model.getDiscount().matches("")) {
            holder.mPackageCancelAmount.setVisibility(View.INVISIBLE);
            if (model.getPlan_name().matches("STARTER")) {
                holder.mPackageAmount.setText("₹" + DataManager.setCommas(model.getPlan_cost()+"\n Yearly"));
            } else {
                holder.mPackageAmount.setText("₹" + DataManager.setCommas(model.getPlan_cost()+"\n Month"));
            }

            holder.mPackagePercent.setVisibility(View.GONE);
        } else {
            holder.mPackageCancelAmount.setVisibility(View.VISIBLE);
            holder.mPackageTitle.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
            if (model.getPlan_name().matches("STARTER")) {
                holder.mPackageCancelAmount.setText("₹" + model.getPlan_cost());
            } else {
                holder.mPackageCancelAmount.setText("₹" + model.getPlan_cost());
            }
            String discountCost = DataManager.calculatePercent(model.getPlan_cost(), model.getDiscount().replaceAll("%", ""));
            holder.mPackagePercent.setVisibility(View.VISIBLE);
            holder.mPackagePercent.setText(model.getDiscount() + " OFF");
            if (model.getPlan_name().matches("STARTER")) {
                holder.mPackageAmount.setText("₹" + DataManager.setCommas(discountCost.substring(0, (model.getPlan_cost().length()))));
            } else {
                holder.mPackageAmount.setText("₹" + DataManager.setCommas(discountCost.substring(0, (model.getPlan_cost().length()))) + "\nMonth");
            }

        }

        if (model.getPlan_name().replaceAll("\r\n", "").matches("LITE")) {
            holder.mPackageTitle.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.mPackagePopularBtn.setVisibility(View.VISIBLE);
            holder.mPackageAmount.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            holder.mPackagePopularBtn.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return mPackageArrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick();

        void OnViewClick(PackageModel model);
    }

    public static class PackageFragmentCellVH extends RecyclerView.ViewHolder {

        MaterialTextView mPackageTitle, mPackageAmount, mPackageCancelAmount, mPackagePercent,
                package_item_priority_search1,
                package_item_display_pro,
                package_item_display_selling_lead,
                package_item_number_of_enquiry,
                package_item_access_to_new_buy,
                package_item_access_to_3million,
                package_item_priority_search2,
                package_item_free_company_verification;

        MaterialButton mPackagePopularBtn;
        MaterialCardView mPackageCellMainCard;

        /*MaterialTextView mPackageCellCost, mPackageCellMemType, mPackageFragmentCellOriginalCost, mPackageCellDiscountTxt;
        RelativeLayout package_fragment_cell_main_view;
        LinearLayout mPackageFragmentOriginalAmountLayout;
*/
        public PackageFragmentCellVH(@NonNull View itemView) {
            super(itemView);

            mPackageTitle = itemView.findViewById(R.id.package_cell_title);
            mPackageAmount = itemView.findViewById(R.id.package_cell_amount);
            mPackagePopularBtn = itemView.findViewById(R.id.package_cell_popular_btn);
            mPackageCellMainCard = itemView.findViewById(R.id.package_cell_main_card);
            mPackageCancelAmount = itemView.findViewById(R.id.package_cell_cancel_amount);
            mPackagePercent = itemView.findViewById(R.id.package_cell_discount_perc);

/*            package_item_priority_search1 = itemView.findViewById(R.id.package_item_priority_search1);
            package_item_display_pro = itemView.findViewById(R.id.package_item_display_pro);
            package_item_display_selling_lead = itemView.findViewById(R.id.package_item_display_selling_lead);
            package_item_number_of_enquiry = itemView.findViewById(R.id.package_item_number_of_enquiry);
            package_item_access_to_new_buy = itemView.findViewById(R.id.package_item_access_to_new_buy);
            package_item_access_to_3million = itemView.findViewById(R.id.package_item_access_to_3million);
            package_item_priority_search2 = itemView.findViewById(R.id.package_item_priority_search2);
            package_item_free_company_verification = itemView.findViewById(R.id.package_item_free_company_verification);*/

            /*mPackageCellCost = itemView.findViewById(R.id.package_fragment_cell_cost);
            mPackageCellDiscountTxt = itemView.findViewById(R.id.package_cell_discount_txt);
            mPackageCellMemType = itemView.findViewById(R.id.package_fragment_cell_memb_type);
            package_fragment_cell_main_view = itemView.findViewById(R.id.package_fragment_cell_main_view);
            mPackageFragmentOriginalAmountLayout = itemView.findViewById(R.id.package_fragment_original_amount_layout);
            mPackageFragmentCellOriginalCost = itemView.findViewById(R.id.package_fragment_cell_original_cost);*/
        }
    }

}
