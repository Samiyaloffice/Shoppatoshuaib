package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SellerProductRepository {

    public static Context context;
    private static SellerProductRepository instance;
    public boolean isResponseDone;
    ArrayList<ProductDetailModel> mProductDetailModelArrayList;
    MutableLiveData<ArrayList<ProductDetailModel>> data;

    public static SellerProductRepository getInstance(Context ctx) {
        if (instance == null) {
            instance = new SellerProductRepository();
            context = ctx;
        }
        return instance;
    }

    public void fetchSellerProducts(String id) {
        isResponseDone = false;

        mProductDetailModelArrayList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getSellerProduct(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("sellerProductDetails", "onResponse: " + response);
                        try {
                            JSONArray records = response.getJSONArray("records");
                            if (records.length() > 0) {
                                for (int i = 0; i < records.length(); i++) {
                                    JSONObject obj = records.getJSONObject(i);
                                    mProductDetailModelArrayList.add(new ProductDetailModel(obj.optString("id"),
                                            obj.optString("post_title_seo_url"),
                                            obj.optString("post_title"),
                                            obj.optString("post_image"),
                                            obj.optString("meta_description"),
                                            "", id,""
                                    ));

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
                Toast.makeText(context, "This Seller has no Products", Toast.LENGTH_SHORT).show();

            }
        });
//        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public LiveData<ArrayList<ProductDetailModel>> getSellerProducts() {
        data = new MutableLiveData<>();
        data.setValue(mProductDetailModelArrayList);
        return data;
    }

}
