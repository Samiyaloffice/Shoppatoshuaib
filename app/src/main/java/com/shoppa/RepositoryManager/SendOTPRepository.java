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

import java.util.Random;

public class SendOTPRepository {

    public static Context context;
    public static boolean isUserAvailable = false;
    public static boolean OtpSend = false;
    public static boolean isError = false;
    private static SendOTPRepository instance;
    SharedPreferences sharedPreferences;
    UserDataModel mUserDataModel;

    public static SendOTPRepository getInstance(Context ctx) {
        if (instance == null) {
            instance = new SendOTPRepository();
            context = ctx;
        }
        return instance;
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(899999) + 100000;

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public void sendOTP(String number) {

        OtpSend = false;
        isError = false;
        JSONObject params = new JSONObject();
        try {
            params.put("sl_number", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("sendOTPNumber", "sendOTP: params - " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getOTPUrl(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("sendOTPNumber", "onErrorResponse: response - " + response);

                        Toast.makeText(context, "OTP send", Toast.LENGTH_LONG).show();
                        isError = false;
                        OtpSend = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("sendOTPNumber", "onErrorResponse: error - " + error);
                isError = true;
                DataManager.checkVolleyError(error, context);
            }
        });

        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public void fetchUserDetails(String OTP) {

        isUserAvailable = false;
        isError = false;
        JSONObject params = new JSONObject();
        try {
            params.put("sl_otp", OTP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("UserDataTest", "fetchUserDetails: OTP " + OTP);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getUserDetail(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("UserDataTest", "onResponse: " + response);

                        Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();

                        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("auth", "present");
                        editor.putString("email", response.optString("seller_email"));
                        editor.putString("password", response.optString("seller_mpassword"));
                        editor.apply();

                        isError = false;
                        isUserAvailable = true;

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isError = true;
                DataManager.checkVolleyError(error, context);
//                Toast.makeText(context, "An Error Occurs", Toast.LENGTH_SHORT).show();
            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public void loginEmail(String email, String pass) {

        isUserAvailable = false;
        isError = false;
        JSONObject params = new JSONObject();
        try {
            params.put("seller_email", email);
            params.put("seller_mpassword", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getEmailPassUrl(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("emailLogin", "onResponse: response - " + response);

                        Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();

                        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("auth", "present");
                        editor.putString("email", response.optString("seller_email"));
                        editor.putString("password", response.optString("seller_mpassword"));
                        editor.apply();
                        isError = false;
                        isUserAvailable = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isError = true;
                DataManager.checkVolleyError(error, context);
//                Toast.makeText(context, "An Error Occurs", Toast.LENGTH_SHORT).show();
            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public void mainLogin(String email, String pass) {
        isUserAvailable = false;
        isError = false;
        JSONObject params = new JSONObject();
        try {
            params.put("seller_email", email);
            params.put("seller_mpassword", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getEmailPassUrl(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("emailLogin", "onResponse: main response - " + response);

                        Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();

                        UserDataModel.getmInstance().setId(response.optString("id"));
                        UserDataModel.getmInstance().setSl_number(response.optString("sl_number"));
                        UserDataModel.getmInstance().setSeller_password(response.optString("seller_mpassword"));
                        UserDataModel.getmInstance().setSeller_name(response.optString("seller_name"));
                        UserDataModel.getmInstance().setSeller_image(response.optString("seller_image"));
                        UserDataModel.getmInstance().setSeller_email(response.optString("seller_email"));
                        UserDataModel.getmInstance().setSeller_usertype(response.optString("seller_usertype"));
                        UserDataModel.getmInstance().setSeller_userstatus(response.optString("seller_userstatus"));
                        UserDataModel.getmInstance().setSeller_company(response.optString("seller_company"));
                        UserDataModel.getmInstance().setSeller_address(response.optString("seller_address"));
                        UserDataModel.getmInstance().setSeller_website(response.optString("seller_website"));
                        UserDataModel.getmInstance().setSeller_post_id(response.optString("seller_post_id"));
                        UserDataModel.getmInstance().setSeller_whatsapp(response.optString("seller_whatsapp"));
                        UserDataModel.getmInstance().setSeller_skype(response.optString("seller_skype"));
                        UserDataModel.getmInstance().setSeller_type(response.optString("seller_type"));
                        UserDataModel.getmInstance().setSeller_plan(response.optString("seller_plan"));

                        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("auth", "present");
                        editor.putString("email", response.optString("seller_email"));
                        editor.putString("password", response.optString("seller_mpassword"));
                        editor.apply();

                        isError = false;
                        isUserAvailable = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                isError = true;
                DataManager.checkVolleyError(error, context);

            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }


}
