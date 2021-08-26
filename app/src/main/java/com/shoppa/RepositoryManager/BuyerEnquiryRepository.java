package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerEnquiryModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONException;
import org.json.JSONObject;

public class BuyerEnquiryRepository {

    private static Context context;
    private static BuyerEnquiryRepository instance;
    public boolean isResponseDone = false;

    public static BuyerEnquiryRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new BuyerEnquiryRepository();
            context = cxt;
        }
        return instance;
    }

    public void postEnquiryData(BuyerEnquiryModel model) {

        isResponseDone = false;

        JSONObject params = new JSONObject();
        try {
            params.put("name", model.getName());
            params.put("email", model.getEmail());
            params.put("address", model.getAddress());
            params.put("mobile", model.getMobile());
            params.put("city", model.getCity());
            params.put("state", model.getState());
            params.put("country", model.getCountry());
            params.put("product", model.getProduct());
            params.put("quantity", model.getQuantity());
            params.put("unit", model.getUnit());
            params.put("comments", model.getComments());
            params.put("seller_name", model.getSeller_name());
            params.put("byr_src", model.getBuyer_src());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getBuyerEnquiryURL(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "" + response.optString("message"), Toast.LENGTH_LONG).show();
                        Log.i("BuyerEnquiryResponse", "onResponse: response = " + response);
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

        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

}
