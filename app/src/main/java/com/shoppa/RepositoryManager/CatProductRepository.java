package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.CatProductModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatProductRepository {

    public static Context context;
    private static CatProductRepository instance;
    public boolean isResponseDone = false;
    ArrayList<CatProductModel> mCatProductModelArrayList;
    MutableLiveData<ArrayList<CatProductModel>> mProductData;

    public static CatProductRepository getInstance(Context ctx) {
        if (instance == null) {
            instance = new CatProductRepository();
            context = ctx;
        }
        return instance;
    }

    public void fetchCatProductData(String id) {

        isResponseDone = false;
        mCatProductModelArrayList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getCatProductData(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("catProductsResponse", "onResponse: " + response);
                        try {
                            if (response.getJSONArray("records").length() > 0) {
                                JSONArray records = response.getJSONArray("records");

                                for (int i = 0; i < records.length(); i++) {
                                    JSONObject obj = records.getJSONObject(i);

                                    mCatProductModelArrayList.add(new CatProductModel(obj.optString("id"),
                                            obj.optString("post_title"),
                                            obj.optString("post_title_seo_url"),
                                            obj.optString("porduct_image"),
                                            obj.optString("seller_id"),
                                            obj.optString("Total_Sellers"),
                                            DataManager.separateIds(obj.optString("sellers_id_data"))));

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        isResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isResponseDone = true;
                DataManager.checkVolleyError(error, context);
            }
        });
//        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ArrayList<CatProductModel>> getCatProductData() {
        mProductData = new MutableLiveData<>();
        mProductData.setValue(mCatProductModelArrayList);
        return mProductData;
    }

}
