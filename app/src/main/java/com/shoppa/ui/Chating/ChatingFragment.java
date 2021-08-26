package com.shoppa.ui.Chating;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppa.Adapters.ChatingAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.R;

public class ChatingFragment extends Fragment {

    RecyclerView mChatingRecyclerView;
    ChatingAdapter mChatingAdapter;
    private ChatingViewModel mViewModel;

    public static ChatingFragment newInstance() {
        return new ChatingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.chating_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(ChatingViewModel.class);

        mChatingRecyclerView = root.findViewById(R.id.chating_recycler_view);

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");

                    DataManager.mChatSenRecyclerView.setVisibility(View.VISIBLE);
                    DataManager.mChatSentFrameLayout.setVisibility(View.GONE);
                    DataManager.mChatInboxRecyclerView.setVisibility(View.VISIBLE);
                    DataManager.mChatInboxFrameLayout.setVisibility(View.GONE);

                    return true;
                }
                return false;
            }
        });

        setChatingFragmentAdapter();

        return root;
    }

    private void setChatingFragmentAdapter() {

        mChatingRecyclerView.setHasFixedSize(true);
        mChatingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mChatingAdapter = new ChatingAdapter(getContext(), new ChatingAdapter.OnItemClickListener() {
            @Override
            public void OnViewClick() {

            }

            @Override
            public void OnItemClick() {

            }
        });

        mChatingRecyclerView.setAdapter(mChatingAdapter);

    }
}