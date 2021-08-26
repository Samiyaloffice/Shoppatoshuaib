package com.shoppa.RepositoryManager;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.LatestBuyLeadModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LatestBuyLeadRepository {

    private static Context context;
    private static LatestBuyLeadRepository instance;
    public boolean isResponseDone = false;
    ArrayList<LatestBuyLeadModel> mLatestBuyLeadArrayList;
    MutableLiveData<ArrayList<LatestBuyLeadModel>> mBuyLeadData;

    public static LatestBuyLeadRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new LatestBuyLeadRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchBuyLead() {

        isResponseDone = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getBuyLeadURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        mLatestBuyLeadArrayList = new ArrayList<>();

                        try {
                            JSONArray records = response.getJSONArray("records");
                            if (records.length() > 0) {
                                for (int i = 0; i < records.length(); i++) {
                                    JSONObject obj = records.getJSONObject(i);
                                    mLatestBuyLeadArrayList.add(new LatestBuyLeadModel(obj.optString("id"),
                                            obj.optString("byr_name"),
                                            obj.optString("byr_product"),
                                            obj.optString("byr_quantity"),
                                            obj.optString("byr_unit"),
                                            obj.optString("enquiry_date")));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        isResponseDone = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                DataManager.checkVolleyError(error, context);
                isResponseDone = true;

            }
        });
//        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ArrayList<LatestBuyLeadModel>> getBuyLeadData() {
        mBuyLeadData = new MutableLiveData<>();
        mBuyLeadData.setValue(mLatestBuyLeadArrayList);
        return mBuyLeadData;
    }

}
