package com.shoppa.ui.ChatInbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppa.Adapters.ChatAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;
import com.shoppa.ui.Chating.ChatingFragment;

public class ChatInboxFragment extends Fragment {

    RecyclerView mChatInboxRecyclerView;
    ChatAdapter mChatAdapter;
    private ChatInboxViewModel mViewModel;

    public static ChatInboxFragment newInstance() {
        return new ChatInboxFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        DataManager.isFrom = "chatFragment";

        View root = inflater.inflate(R.layout.chat_inbox_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(ChatInboxViewModel.class);

        mChatInboxRecyclerView = root.findViewById(R.id.chat_inbox_recycler_view);
        DataManager.mChatInboxRecyclerView = mChatInboxRecyclerView;
        DataManager.mChatInboxFrameLayout = root.findViewById(R.id.chat_inbox_frame_layout);

        DataManager.mChatInboxRecyclerView.setVisibility(View.VISIBLE);
        DataManager.mChatInboxFrameLayout.setVisibility(View.GONE);

        setChatInboxAdapter();

        return root;
    }

    private void setChatInboxAdapter() {

        mChatInboxRecyclerView.setHasFixedSize(true);
        mChatInboxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChatAdapter = new ChatAdapter(getContext(), new ChatAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {
                openChattingFragment();
            }

            @Override
            public void OnItemClick() {

            }
        });

        mChatInboxRecyclerView.setAdapter(mChatAdapter);

    }

    private void openChattingFragment() {

        DataManager.mChatInboxRecyclerView.setVisibility(View.GONE);
        DataManager.mChatInboxFrameLayout.setVisibility(View.VISIBLE);

        ChatingFragment fragment = new ChatingFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.chat_inbox_frame_layout, fragment);
        transaction.addToBackStack("chatInboxFragmentAdded");
        transaction.commit();

    }

}