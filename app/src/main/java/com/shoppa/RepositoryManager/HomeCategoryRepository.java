package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HomeCategoryRepository {

    public static Context context;
    private static HomeCategoryRepository instance;
    public boolean isResponseDone = false;
    ArrayList<HomeCategoryModel> mHomeCategoryModelArrayList;
    ArrayList<HomeCategoryModel> mAllCategoryModelArrayList;
    MutableLiveData<ArrayList<HomeCategoryModel>> data, allData;

    public static HomeCategoryRepository getInstance(Context ctx) {
        if (instance == null) {
            instance = new HomeCategoryRepository();
            context = ctx;
        }
        return instance;
    }

    public void fetchData() {
        long mRequestStartTime;

        mHomeCategoryModelArrayList = new ArrayList<>();
        mAllCategoryModelArrayList = new ArrayList<>();
        isResponseDone = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getCategoryData(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("catData", "onResponse: " + response);
                        try {
                            if (response.getJSONArray("records").length() > 0) {
                                JSONArray records = response.getJSONArray("records");
                                long mRequestStartTime = 0;
                                for (int i = 0; i < 6; i++) {
                                    JSONObject obj = records.getJSONObject(i);

                                    mHomeCategoryModelArrayList.add(new HomeCategoryModel(obj.optString("id"),
                                            obj.optString("cat_name"),
                                            obj.optString("cat_image"),
                                            obj.optString("fld_seo_url"),
                                            obj.optString("Total_subcat"),
                                            obj.optString("Total_products")));
                                    long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;

                                    Toast.makeText(context, "Loading time:-"+totalRequestTime, Toast.LENGTH_SHORT).show();
                                }

                                for (int i = 0; i < records.length(); i++) {
                                    JSONObject obj = records.getJSONObject(i);

                                    mAllCategoryModelArrayList.add(new HomeCategoryModel(obj.optString("id"),
                                            obj.optString("cat_name"),
                                            obj.optString("cat_image"),
                                            obj.optString("fld_seo_url"),
                                            obj.optString("Total_subcat"),
                                            obj.optString("Total_products")));
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
                DataManager.checkVolleyError(error, context);
                isResponseDone = true;
            }

        }






        );
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public MutableLiveData<ArrayList<HomeCategoryModel>> getHomeCategories() {
        data = new MutableLiveData<>();
        data.setValue(mHomeCategoryModelArrayList);
        return data;
    }

    public MutableLiveData<ArrayList<HomeCategoryModel>> getAllCatData() {
        allData = new MutableLiveData<>();
        allData.setValue(mAllCategoryModelArrayList);
        return allData;
    }

}

