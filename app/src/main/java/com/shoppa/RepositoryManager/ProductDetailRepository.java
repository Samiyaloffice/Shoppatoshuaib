package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.ProductDetailModel;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailRepository {
    public static Context context;
    private static ProductDetailRepository instance;
    public boolean isResponseDone = false;
    public boolean isSellerResponseDone = false;
    ProductDetailModel mProductDetailModel;
    SellerDetailModel mSellerDetailModel;
    MutableLiveData<SellerDetailModel> mSellerData;
    MutableLiveData<ProductDetailModel> data;

    public static ProductDetailRepository getInstance(Context cxt) {

        if (instance == null) {
            instance = new ProductDetailRepository();
            context = cxt;
        }
        return instance;

    }

    public void fetchProductDetail(String productName, String sellerId) {

        isResponseDone = false;

        JSONObject params = new JSONObject();
        try {
            params.put("post_title", productName);
            params.put("post_id", sellerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("productDetails", "fetchProductDetail: params - "+params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getProductIdUrl(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONArray("records").getJSONObject(0);

                            Log.i("productDetails", "onResponse: " + response);
                            mProductDetailModel = new ProductDetailModel(obj.optString("id"),
                                    obj.optString("post_title_seo_url"),
                                    obj.optString("post_title"),
                                    obj.optString("post_image"),
                                    obj.optString("meta_description"),
                                    obj.optString("price"),
                                    obj.optString("post_id"),
                                    obj.optString("post_date_time"));

                            isResponseDone = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isResponseDone = true;
                DataManager.checkVolleyError(error, context);
            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public void fetchSellerDetails(String id) {

        isSellerResponseDone = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getSellerDetails(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("sellerDetails", "onResponse: response = " + response);

                        mSellerDetailModel = new SellerDetailModel(response.optString("id"),
                                response.optString("post_title"),
                                response.optString("post_date_time"),
                                response.optString("post_type"),
                                response.optString("post_image"),
                             
                                response.optString("meta_description"),
                                response.optString("seller_contact"),
                                response.optString("seller_plan"),
                                response.optString("seller_name"));

                        isSellerResponseDone = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isSellerResponseDone = true;
                DataManager.checkVolleyError(error, context);
            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public MutableLiveData<ProductDetailModel> getProductDetailData() {
        data = new MutableLiveData<>();
        data.setValue(mProductDetailModel);
        return data;
    }

    public MutableLiveData<SellerDetailModel> getSellerDetailData() {
        mSellerData = new MutableLiveData<>();
        mSellerData.setValue(mSellerDetailModel);
        return mSellerData;
    }

}

