package com.shoppa.ui.Chat;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shoppa.Adapters.CustomFragmentPagerAdapter;
import com.shoppa.R;
import com.shoppa.ui.ChatInbox.ChatInboxFragment;
import com.shoppa.ui.ChatSent.ChatSentFragment;
import com.shoppa.ui.Dashboard.DashboardFragment;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    public static TabLayout mChatFragmentTabLayout;
    public static ViewPager mChatFragmentViewPager;
    CustomFragmentPagerAdapter mChatViewPagerAdapter;
    ArrayList<Fragment> mFragmentArrayList;
    ArrayList<String> mFragmentTitleArrayList;
    boolean backRequest = false;
    private ChatViewModel mViewModel;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.chat_fragment, container, false);

        Handler mBackHandler = new Handler();

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    Toast.makeText(getContext(), "Press again to exit", Toast.LENGTH_SHORT).show();

                    onBackTriggered();
                    backRequest = true;

                    mBackHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backRequest = false;
                        }
                    }, 2000);

                    return true;
                }
                return false;
            }
        });

        DashboardFragment.mDashboardChatBtn.setIconTintResource(R.color.icon_selected);
        DashboardFragment.mDashboardChatTxt.setVisibility(View.VISIBLE);

        DashboardFragment.mDashboardPackageBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardPackageTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardHomeBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardHomeTxt.setVisibility(View.GONE);
        DashboardFragment.mDashboardOptionBtn.setIconTintResource(R.color.tab_indicator_gray);
        DashboardFragment.mDashboardOptionTxt.setVisibility(View.GONE);
        DashboardFragment.mHomeSearchView.setVisibility(View.GONE);

        mChatFragmentTabLayout = root.findViewById(R.id.chat_fragment_tab_layout);
        mChatFragmentViewPager = root.findViewById(R.id.chat_fragment_view_pager);

        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        setViewPagerWithTab();

        return root;
    }

    private void onBackTriggered() {
        if (backRequest) {
            requireActivity().finish();
        }
    }

    private void setViewPagerWithTab() {

        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new ChatInboxFragment());
        mFragmentArrayList.add(new ChatSentFragment());

        mFragmentTitleArrayList = new ArrayList<>();
        mFragmentTitleArrayList.add("Inbox");
        mFragmentTitleArrayList.add("Sent");

        mChatViewPagerAdapter = new CustomFragmentPagerAdapter(
                getChildFragmentManager(), mFragmentArrayList, mFragmentTitleArrayList);
        mChatFragmentViewPager.setAdapter(mChatViewPagerAdapter);

        // It is used to join TabLayout with ViewPager.
        mChatFragmentTabLayout.setupWithViewPager(mChatFragmentViewPager);
    }
}