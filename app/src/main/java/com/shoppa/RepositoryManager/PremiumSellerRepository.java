package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.PremiumSellerModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PremiumSellerRepository {

    private static Context context;
    private static PremiumSellerRepository instance;
    public boolean isResponseDone = false, isError = false;
    ArrayList<PremiumSellerModel> mPremiumSellerArrayList;
    MutableLiveData<ArrayList<PremiumSellerModel>> data;

    public static PremiumSellerRepository getInstance(Context cxt) {
        if (instance == null) {
            context = cxt;
            instance = new PremiumSellerRepository();
        }
        return instance;
    }

    public void fetchSellerData() {

        mPremiumSellerArrayList = new ArrayList<>();
        isResponseDone = false;
        isError = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getSellersDataUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("SellerData", "onResponse: response = " + response);

                        try {
                            JSONArray records = response.getJSONArray("records");

                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);

                                mPremiumSellerArrayList.add(new PremiumSellerModel(obj.optString("id"),
                                        obj.optString("seller_name"),
                                        obj.optString("seller_email"),
                                        obj.optString("seller_image"),
                                        obj.optString("seller_number"),
                                        obj.optString("seller_address")));

                            }

                            isResponseDone = true;
                            isError = false;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                DataManager.checkVolleyError(error, context);
                isResponseDone = false;
                isError = true;
            }
        });
       NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ArrayList<PremiumSellerModel>> getPremiumSellerData() {
        data = new MutableLiveData<>();
        data.setValue(mPremiumSellerArrayList);
        return data;
    }

}
