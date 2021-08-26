package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.UserBuyRequestModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserBuyRequestRepository {

    private static Context context;
    private static UserBuyRequestRepository instance;
    public boolean isResponseDone = false;
    ArrayList<UserBuyRequestModel> mUserBuyRequestArrayList;
    MutableLiveData<ArrayList<UserBuyRequestModel>> mData;

    public static UserBuyRequestRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new UserBuyRequestRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchBuyRequest() {
        mUserBuyRequestArrayList = new ArrayList<>();
        isResponseDone = false;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getBuyerRequest(UserDataModel.getmInstance().getSl_number()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Log.i("buyerRequestResponse", "onResponse: " + response);
                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mUserBuyRequestArrayList.add(new UserBuyRequestModel(obj.optString("byr_name"),
                                        obj.optString("byr_email"),
                                        obj.optString("byr_address"),
                                        obj.optString("byr_product"),
                                        obj.optString("byr_quantity"),
                                        obj.optString("seller_name"),
                                        obj.optString("byr_unit"),
                                        obj.optString("enquiry_date"),
                                        obj.optString("byr_status"),
                                        obj.optString("byr_src")
                                ));
                            }
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }

                        isResponseDone = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.checkVolleyError(error, context);
                isResponseDone = true;
            }
        });
//        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public MutableLiveData<ArrayList<UserBuyRequestModel>> getUserRequestData() {
        mData = new MutableLiveData<>();
        mData.setValue(mUserBuyRequestArrayList);
        return mData;
    }
}
