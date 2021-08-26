package com.shoppa.RepositoryManager;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.CountryModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuyerRequestLocationRepositry {

    private static Context context;
    private static BuyerRequestLocationRepositry instance;
    public boolean isCountryResponseDone = false;
    public boolean isStateResponseDone = false;
    public boolean isCityResponseDone = false;
    ArrayList<CountryModel> mBuyerRequestCountry;
    MutableLiveData<ArrayList<CountryModel>> mBuyerCountryData;
    ArrayList<String> mBuyerUnitList;
    MutableLiveData<ArrayList<String>> mUnitData;

    public static BuyerRequestLocationRepositry getInstance(Context cxt) {
        if (instance == null) {
            instance = new BuyerRequestLocationRepositry();
            context = cxt;
        }
        return instance;
    }

    public LiveData<ArrayList<String>> getUnitData() {

        mBuyerUnitList = new ArrayList<>();
        mBuyerUnitList.add("Piece(s) / Pcs");
        mBuyerUnitList.add("Bag(s)");
        mBuyerUnitList.add("Cartoon");
        mBuyerUnitList.add("Dozen");
        mBuyerUnitList.add("Feet");
        mBuyerUnitList.add("Kilogram / Kg");
        mBuyerUnitList.add("Meter(s)");
        mBuyerUnitList.add("Ton");
        mBuyerUnitList.add("Metric Ton");
        mBuyerUnitList.add("Square Feet");
        mBuyerUnitList.add("Square Meter");
        mBuyerUnitList.add("Plate");
        mBuyerUnitList.add("Liter");
        mBuyerUnitList.add("Other");

        mUnitData = new MutableLiveData<>();
        mUnitData.setValue(mBuyerUnitList);

        return mUnitData;

    }

    public void fetchBuyerCountry() {

        isCountryResponseDone = false;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getCountryUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mBuyerRequestCountry = new ArrayList<>();
                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mBuyerRequestCountry.add(new CountryModel(obj.optString("country_id"),
                                        obj.optString("country_name")));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        isCountryResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.checkVolleyError(error, context);
                isCountryResponseDone = true;
            }
        });
/*
       NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();*/
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ArrayList<CountryModel>> getBuyerCountry() {
        mBuyerCountryData = new MutableLiveData<>();
        mBuyerCountryData.setValue(mBuyerRequestCountry);
        return mBuyerCountryData;
    }

    public void fetchBuyerState(String id) {

        isStateResponseDone = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getStateUrl(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mBuyerRequestCountry = new ArrayList<>();
                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mBuyerRequestCountry.add(new CountryModel(obj.optString("state_id"),
                                        obj.optString("state_name")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        isStateResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.checkVolleyError(error, context);
                isStateResponseDone = true;
            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public MutableLiveData<ArrayList<CountryModel>> getmBuyerStateData() {
        mBuyerCountryData = new MutableLiveData<>();
        mBuyerCountryData.setValue(mBuyerRequestCountry);
        return mBuyerCountryData;
    }

    public void fetchBuyerCity(String id) {

        isCityResponseDone = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getCityUrl(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mBuyerRequestCountry = new ArrayList<>();
                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mBuyerRequestCountry.add(new CountryModel(obj.optString("city_id"),
                                        obj.optString("city_name")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        isCityResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.checkVolleyError(error, context);
                isCityResponseDone = true;
            }
        });

        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ArrayList<CountryModel>> getmBuyerCityData() {
        mBuyerCountryData = new MutableLiveData<>();
        mBuyerCountryData.setValue(mBuyerRequestCountry);
        return mBuyerCountryData;
    }

}
