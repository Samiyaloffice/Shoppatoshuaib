package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.UserDataModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MessengerRepository {

    private static Context context;
    private static MessengerRepository instance;
    public boolean isResponseDone = false;

    public static MessengerRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new MessengerRepository();
            context = cxt;
        }
        return instance;
    }

    public void postMessengerId(String Skype, String Whatsapp) {

        isResponseDone = false;

        JSONObject params = new JSONObject();
        try {
            params.put("sl_number", UserDataModel.getmInstance().getSl_number());
            params.put("whatsapp", Whatsapp);
            params.put("skype", Skype);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getMessengerURL(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("messengerResponse", "onResponse: " + response);
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
