package com.shoppa.ui.TroubleTickets;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Adapters.TroubleTicketAdapter;
import com.shoppa.DataManager.DataManager;
import com.shoppa.MainActivity;
import com.shoppa.Model.ReportModel;
import com.shoppa.Model.TroubleTicketModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TroubleTicketsFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    SwipeRefreshLayout ticketswipe;
    ShapeableImageView shapeableImageView;
    RecyclerView mTroubleTicketRecyclerView;
    private Bitmap bitmap;
    ShapeableImageView mTroubleTicketAddBtn;
    TroubleTicketAdapter mTroubleTicketAdapter;
    Dialog mDialog;
    Handler mReportHandler = new Handler();
    Handler mHandler = new Handler();
    private TroubleTicketsViewModel mViewModel;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isReportResponseDone) {
                setTroubleTicketAdapter();
                stopHandler();
            } else {
                startHandler();
            }
        }
    };
    Runnable mReportRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewModel.isResponseDone) {
                stopReportHandler();
            } else {
                startReportHandler();
            }
        }
    };

    public static TroubleTicketsFragment newInstance() {
        return new TroubleTicketsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DataManager.isFrom = "optionFragment";
        mViewModel = new ViewModelProvider(this).get(TroubleTicketsViewModel.class);
        View root = inflater.inflate(R.layout.trouble_tickets_fragment, container, false);

        mDialog = new Dialog(requireContext());
        mViewModel.setContext(getContext());

        ticketswipe=(SwipeRefreshLayout)root.findViewById(R.id.tickjetswipe);
        ticketswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshswipe();

            ticketswipe.setRefreshing(false);
            }
        });



        mTroubleTicketRecyclerView = root.findViewById(R.id.trouble_ticket_recycler_view);
        mTroubleTicketAddBtn = root.findViewById(R.id.trouble_ticket_add_btn);
        mTroubleTicketAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCreateTicketDialog();
            }
        });



//        setTroubleTicketAdapter();

        setTroubleTicketData();

        return root;
    }

    private void refreshswipe() {

 /*       FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();*/


    }

    private void setTroubleTicketAdapter() {

        mTroubleTicketRecyclerView.setHasFixedSize(true);
        mTroubleTicketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTroubleTicketAdapter = new TroubleTicketAdapter(getContext(), new TroubleTicketAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick() {

            }

            @Override
            public void OnViewClick() {

            }
        }, mViewModel.getTroubleTickets().getValue());
        mTroubleTicketRecyclerView.setAdapter(mTroubleTicketAdapter);

        mViewModel.getTroubleTickets().observe(getViewLifecycleOwner(), new Observer<ArrayList<TroubleTicketModel>>() {
            @Override
            public void onChanged(ArrayList<TroubleTicketModel> troubleTicketModels) {

            }
        });

    }


    private void setCreateTicketDialog() {

        Dialog dialog = new Dialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_add_trouble_ticket, null);
        dialog.setContentView(view);
        LinearLayout opengallaery;
        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = (int) (ViewGroup.LayoutParams.WRAP_CONTENT);
        int width = (int) (metrics.widthPixels * 0.80);

        dialog.getWindow().setLayout(width, height);

        TextInputLayout mTroubleTicketTitle, mTroubleTicketDescription;
        MaterialButton mTroubleTicketSubmitBtn;


        mTroubleTicketSubmitBtn = dialog.findViewById(R.id.dialog_ticket_submit);
        mTroubleTicketTitle = dialog.findViewById(R.id.dialog_ticket_title);
        shapeableImageView=dialog.findViewById(R.id.imageattached);
        mTroubleTicketDescription = dialog.findViewById(R.id.dialog_ticket_description);
        opengallaery=(LinearLayout) dialog.findViewById(R.id.attachement);
        opengallaery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
showFileChooser();
            }
        });


        mTroubleTicketSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTroubleTicketTitle.getEditText().getText().toString().matches("")
                        && !mTroubleTicketDescription.getEditText().getText().toString().matches("")) {


                    mViewModel.postReportData(new ReportModel(mTroubleTicketTitle.getEditText().getText().toString(),
                            mTroubleTicketDescription.getEditText().getText().toString(),

                            UserDataModel.getmInstance().getSl_number()));
                    mReportRunnable.run();
                    DataManager.showDialog(requireContext(), requireActivity(), mDialog, "open");
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                    Calendar c = Calendar.getInstance();
//                    String date = sdf.format(c.getTime());
//
//                    mViewModel.setTroubleTicketData(new TroubleTicketModel(String.valueOf(mViewModel.mTroubleTicketModelArrayList.size() + 1),
//                            mTroubleTicketTitle.getEditText().getText().toString(),
//                            mTroubleTicketDescription.getEditText().getText().toString(),
//                            date));

                    dialog.dismiss();

                }
            }
        });

        dialog.show();

    }
    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void showFileChooser() {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                shapeableImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    private void stopReportHandler() {
        mReportHandler.removeCallbacks(mReportRunnable);
        DataManager.showDialog(requireContext(), requireActivity(), mDialog, "close");
        setTroubleTicketData();
    }

    private void setTroubleTicketData() {
        mViewModel.fetchTroubleTickets();
        DataManager.showDialog(requireContext(), requireActivity(), mDialog, "open");
        mRunnable.run();

    }

    private void startHandler() {
        mHandler.postDelayed(mRunnable, 100);
    }

    private void stopHandler() {
        DataManager.showDialog(requireContext(), requireActivity(), mDialog, "close");
        mHandler.removeCallbacks(mRunnable);
    }

    private void startReportHandler() {
        mReportHandler.postDelayed(mReportRunnable, 100);
    }

}