package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.ReportModel;
import com.shoppa.Model.TroubleTicketModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportRepository {

    private static Context context;
    private static ReportRepository instance;
    public boolean isResponseDone = false;
    public boolean isReportResponseDone = false;
    ArrayList<TroubleTicketModel> mTroubleTicketModelArrayList;
    MutableLiveData<ArrayList<TroubleTicketModel>> mData;


    public static ReportRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new ReportRepository();
            context = cxt;
        }
        return instance;
    }

    public void postUserReport(ReportModel model) {

        isResponseDone = false;

        JSONObject params = new JSONObject();
        try {
            params.put("complaint_subject", model.getComplaintTitle());
            params.put("complaint_desc", model.getComplaintDescription());
            params.put("phone", model.getComplaintPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getReportURL(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("ReportResponse", "onResponse: " + response);
                        Toast.makeText(context, "Report Posted", Toast.LENGTH_SHORT).show();
                        isResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.checkVolleyError(error, context);
                isResponseDone = true;
            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }


    public void fetchTroubleTicket() {

        isReportResponseDone = false;
        mTroubleTicketModelArrayList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getReports(UserDataModel.getmInstance().getSl_number()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mTroubleTicketModelArrayList.add(new TroubleTicketModel(obj.optString("id"),
                                        obj.optString("complaint_subject"),
                                        obj.optString("complaint_desc"),
                                        obj.optString("post_date")));
                            }

                            isReportResponseDone = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.checkVolleyError(error, context);
                isReportResponseDone = true;
            }
        });
       NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ArrayList<TroubleTicketModel>> getTroubleTickets() {
        mData = new MutableLiveData<>();
        mData.setValue(mTroubleTicketModelArrayList);
        return mData;
    }

}
