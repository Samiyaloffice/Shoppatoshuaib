package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BasicDetailModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONException;
import org.json.JSONObject;

public class BasicDetailsRepository {

    private static Context context;
    private static BasicDetailsRepository instance;
    public boolean isResponseDone = false;

    public static BasicDetailsRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new BasicDetailsRepository();
            context = cxt;
        }
        return instance;
    }

    public void postUserDetails(BasicDetailModel model) {

        isResponseDone = false;

        JSONObject params = new JSONObject();
        try {
            params.put("sl_number", model.getSl_number());
            params.put("seller_address", model.getSeller_address());
            params.put("seller_type", model.getSeller_type());
            params.put("seller_email", model.getSeller_email());
            params.put("seller_name", model.getSeller_name());
            params.put("seller_company", model.getSeller_company());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getEditDetailURL(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("basicDetailsResponse", "onResponse: " + response);
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

}
