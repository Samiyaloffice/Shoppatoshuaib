package com.shoppa.RepositoryManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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

public class ChangePasswordRepository {

    private static Context context;
    private static ChangePasswordRepository instance;
    public boolean isResponseDone = false;
    SharedPreferences sharedPreferences;

    public static ChangePasswordRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new ChangePasswordRepository();
            context = cxt;
        }
        return instance;
    }

    public void postChangePassword(String password) {

        isResponseDone = false;
        JSONObject params = new JSONObject();
        try {
            params.put("sl_number", UserDataModel.getmInstance().getSl_number());
            params.put("seller_password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("passwordResponse", "postChangePassword: " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getPasswordChangeURL(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("passwordResponse", "onResponse: response - " + response);
                        Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show();

                        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password", response.optString(password));
                        editor.apply();

                        DataManager.refreshUser(context);

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
