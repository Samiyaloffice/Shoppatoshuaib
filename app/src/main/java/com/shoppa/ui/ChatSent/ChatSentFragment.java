package com.shoppa.ui.ChatSent;

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

public class ChatSentFragment extends Fragment {

    RecyclerView mChatSentRecyclerView;
    ChatAdapter mChatAdapter;
    private ChatSentViewModel mViewModel;

    public static ChatSentFragment newInstance() {
        return new ChatSentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        DataManager.isFrom = "chatFragment";
        mViewModel = new ViewModelProvider(this).get(ChatSentViewModel.class);
        View root = inflater.inflate(R.layout.chat_sent_fragment, container, false);

        mChatSentRecyclerView = root.findViewById(R.id.chat_sent_recycler_view);
        DataManager.mChatSenRecyclerView = mChatSentRecyclerView;
        DataManager.mChatSentFrameLayout = root.findViewById(R.id.chat_sent_frame_layout);

        DataManager.mChatSenRecyclerView.setVisibility(View.VISIBLE);
        DataManager.mChatSentFrameLayout.setVisibility(View.GONE);

        setChatAdapter();

        return root;
    }

    private void setChatAdapter() {

        mChatSentRecyclerView.setHasFixedSize(true);
        mChatSentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChatAdapter = new ChatAdapter(getContext(), new ChatAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {
                chatFragment();
            }

            @Override
            public void OnItemClick() {

            }
        });

        mChatSentRecyclerView.setAdapter(mChatAdapter);

    }

    private void chatFragment() {

        DataManager.mChatSenRecyclerView.setVisibility(View.GONE);
        DataManager.mChatSentFrameLayout.setVisibility(View.VISIBLE);

        ChatingFragment fragment = new ChatingFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.chat_sent_frame_layout, fragment);
        transaction.addToBackStack("chatingFragmentAdded");
        transaction.commit();


    }

}