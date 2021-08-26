package com.shoppa.RepositoryManager;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.AllProductModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllProductRepository {

    private static Context context;
    private static AllProductRepository instance;
    public boolean isResponseDone = false;
    private ArrayList<AllProductModel> mAllProductArrayList;
    private MutableLiveData<ArrayList<AllProductModel>> mData;

    public static AllProductRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new AllProductRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchAllProducts() {

        isResponseDone = false;
        mAllProductArrayList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getAllProducts(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mAllProductArrayList.add(new AllProductModel(obj.optString("p_id"),
                                        obj.optString("product_name"),
                                        obj.optString("fld_seo_url"),
                                        obj.optString("product_image")));
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

    public LiveData<ArrayList<AllProductModel>> getAllProduct() {
        mData = new MutableLiveData<>();
        mData.setValue(mAllProductArrayList);
        return mData;
    }
}
