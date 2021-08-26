package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.SubCategoryModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCategoryRepository {

    public static Context context;
    private static SubCategoryRepository instance;
    public boolean isResponseDone = false;
    ArrayList<SubCategoryModel> mSubCategoryModelArrayList;
    MutableLiveData<ArrayList<SubCategoryModel>> data;

    public static SubCategoryRepository getInstance(Context ctx) {
        if (instance == null) {
            instance = new SubCategoryRepository();
            context = ctx;
        }
        return instance;
    }

    public void fetchSubCatData(String id) {

        isResponseDone = false;
        mSubCategoryModelArrayList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getSubCatData(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getJSONArray("records").length() > 0) {
                                JSONArray records = response.getJSONArray("records");
                                Log.i("subCatData", "onResponse: " + response);
                                for (int i = 0; i < records.length(); i++) {
                                    JSONObject obj = records.getJSONObject(i);

                                    mSubCategoryModelArrayList.add(new SubCategoryModel(obj.optString("sc_id"),
                                            obj.optString("subcat_name"),
                                            obj.optString("fld_seo_url"),
                                            obj.optString("subcat_image"),
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
        });
//        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ArrayList<SubCategoryModel>> getSubCatData() {
        data = new MutableLiveData<>();
        data.setValue(mSubCategoryModelArrayList);
        return data;
    }

}
