package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.TradeAlertModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TradeAlertRepository {

    private static Context context;
    private static TradeAlertRepository instance;
    public boolean isResponseDone = false;
    ArrayList<TradeAlertModel> mTradeAlertArraylist;
    MutableLiveData<ArrayList<TradeAlertModel>> mData;

    public static TradeAlertRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new TradeAlertRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchTradeAlert() {
        isResponseDone = false;
        mTradeAlertArraylist = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getTradeAlerts(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("tradeAlertResponse", "onResponse: Trade response - " + response);

                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mTradeAlertArraylist.add(new TradeAlertModel(obj.optString("id"),
                                        obj.optString("discount_name"),
                                        obj.optString("package_name"),
                                        obj.optString("discount_desc"),
                                        obj.optString("discount_per"),
                                        obj.optString("post_date")));
                            }

                        } catch (JSONException e) {
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
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public MutableLiveData<ArrayList<TradeAlertModel>> getTradeAlert() {
        mData = new MutableLiveData<>();
        mData.setValue(mTradeAlertArraylist);
        return mData;
    }

}
