package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerRequestModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONException;
import org.json.JSONObject;

public class BuyerRequestRepository {

    private static Context context;
    private static BuyerRequestRepository instance;
    public boolean isResponseDone = false;

    public static BuyerRequestRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new BuyerRequestRepository();
            context = cxt;
        }
        return instance;
    }

    public void postBuyerRequestData(BuyerRequestModel model) {

        isResponseDone = false;

        JSONObject params = new JSONObject();
        try {
            params.put("name", model.getName());
            params.put("email", model.getEmail());
            params.put("mobile", model.getMobile());
            params.put("city", model.getCountry());
            params.put("state", model.getState());
            params.put("country", model.getCity());
            params.put("product", model.getProduct());
            params.put("quantity", model.getQuantity());
            params.put("unit", model.getUnit());
            params.put("comments", model.getComments());
            params.put("byr_src", model.getBuyerSrc());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("BuyerRequestData", "postBuyerRequestData: params = " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getBuyerRequestURL(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("BuyerRequestData", "onResponse: " + response);
                        isResponseDone = true;
                        Toast.makeText(context, "Request Posted", Toast.LENGTH_SHORT).show();
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
}
