package com.shoppa.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shoppa.Model.CountryModel;

import java.util.ArrayList;

public class DropDownUnitAdapter extends ArrayAdapter<String> {

    ArrayList<String> mSelectionArrayList;
    Context context;

    public DropDownUnitAdapter(@NonNull Context context, int resource, ArrayList<String> mSelectionArrayList) {
        super(context, resource);
        this.context = context;
        this.mSelectionArrayList = mSelectionArrayList;
    }

    @Override
    public int getCount() {
        return mSelectionArrayList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mSelectionArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(mSelectionArrayList.get(position));
        textView.setTextSize(15);
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextSize(15);
        textView.setText(mSelectionArrayList.get(position));
        return textView;
    }
}

