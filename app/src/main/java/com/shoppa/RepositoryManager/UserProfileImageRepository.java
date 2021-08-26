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
import com.shoppa.Model.UserDataModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import java.util.HashMap;
import java.util.Map;

public class UserProfileImageRepository {

    private static Context context;
    private static UserProfileImageRepository instance;
    public boolean isResponseDone = false;

    public static UserProfileImageRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new UserProfileImageRepository();
            context = cxt;
        }
        return instance;
    }

    public void postProfileImage(String image) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiManager.getProfileImageURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("profileResponse", "onResponse: " + response);
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                        isResponseDone = true;
                        DataManager.refreshUser(context);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.checkVolleyError(error, context);
                isResponseDone = true;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("sl_number", UserDataModel.getmInstance().getSl_number());
                params.put("seller_image", image);

                Log.i("userProfileImg", "getParams: " + params);

                return params;
            }
        };

        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(stringRequest);

    }

}
