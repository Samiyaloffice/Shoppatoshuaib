package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.SignUpModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import java.util.HashMap;
import java.util.Map;

public class SignUpRepository {

    public static Context context;
    private static SignUpRepository instance;
    public boolean isResponseDone = false, isError = false;

    public static SignUpRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new SignUpRepository();
            context = cxt;
        }
        return instance;
    }

    public void submitSignUpDetails(SignUpModel model) {

        isResponseDone = false;
        isError = false;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, ApiManager.getSignUpUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("signResponse", "onResponse: response - " + response);
                        isResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isError = true;
                DataManager.checkVolleyError(error, context);
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("signResponse", "onErrorResponse: error - " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("seller_company", model.getSellerCompany());
                params.put("seller_business_description", model.getSellerBusinessDescription());
                params.put("seo_title", model.getSeoTitle());
                params.put("seller_gst", model.getSellerGst());
                params.put("no_of_emp", model.getNumberOfEmployee());
                params.put("sort_order", model.getSortOrder());
                params.put("seller_image", model.getSellerImage());
                params.put("seller_logo", model.getSellerLogo());
                params.put("seller_banner", model.getSellerBanner());
                params.put("seller_type", model.getSellerType());
                params.put("sl_number", model.getSellerNumber());
                params.put("seller_email", model.getSellerEmail());
                params.put("seller_name", model.getSellerName());
                params.put("seller_address", model.getSellerAddress());
                params.put("seller_product", model.getSellerProduct());

                return params;
            }
        };
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

}
