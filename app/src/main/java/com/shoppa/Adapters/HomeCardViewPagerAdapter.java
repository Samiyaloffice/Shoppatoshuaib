package com.shoppa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.shoppa.R;

import java.util.ArrayList;

public class HomeCardViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> mCardList;

    OnItemClickListener listener;

    public HomeCardViewPagerAdapter(Context context, ArrayList<String> mCardList, OnItemClickListener listener) {
        this.context = context;
        this.mCardList = mCardList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mCardList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_card_cell, container, false);
        ImageView mHomeCardImage = view.findViewById(R.id.home_card_image);
        mHomeCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(position + "");
                listener.OnItemClick(position + "");

            }
        });
        Glide.with(context).load(mCardList.get(position)).error(R.drawable.no_image_found).into(mHomeCardImage);
        container.addView(view, 0);
        return view;
    }

    public interface OnItemClickListener {
        void OnViewClick();

        void OnItemClick(String position);
    }
}
