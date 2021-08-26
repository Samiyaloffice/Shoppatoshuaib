package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.SellerDetailModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListedSellerRepository {

    private static Context context;
    private static ListedSellerRepository instance;
    public boolean isResponseDone = false, isPResponseDone = false;
    public String productId = "";
    ArrayList<SellerDetailModel> mSellerDetailArrayList;
    MutableLiveData<ArrayList<SellerDetailModel>> data;

    public static ListedSellerRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new ListedSellerRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchListedSeller(ArrayList<String> mListedSellerArrayList) {

        isResponseDone = false;

        mSellerDetailArrayList = new ArrayList<>();
        Log.i("listedSellers", "fetchListedSeller: listedSellersList - " + mListedSellerArrayList);
        for (int i = 0; i < mListedSellerArrayList.size(); i++) {
            int finalI = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getSellerDetails(mListedSellerArrayList.get(i)), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.i("listedSellers", "onResponse: response - " + response);

                            mSellerDetailArrayList.add(new SellerDetailModel(response.optString("id"),
                                    response.optString("post_title"),
                                    response.optString("post_title_seo_url"),
                                    response.optString("post_type"),
                                    response.optString("post_image"),
                                    response.optString("meta_description"),
                                    response.optString("seller_contact"),
                                    response.optString("seller_plan"),
                                    response.optString("seller_name")));

                            if (mListedSellerArrayList.size() == mSellerDetailArrayList.size()) {
                                isResponseDone = true;
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        mListedSellerArrayList.remove(finalI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    DataManager.checkVolleyError(error,context);
                    isResponseDone = false;
                }
            });
//            NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
            NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
        }

    }

    public LiveData<ArrayList<SellerDetailModel>> getListedSeller() {
        data = new MutableLiveData<>();
        Log.i("ListedSeller", "getListedSeller: data - " + mSellerDetailArrayList);
        data.setValue(mSellerDetailArrayList);
        return data;
    }

    public void fetchProductId(String sellerId, String productName) {

        isPResponseDone = false;

        JSONObject params = new JSONObject();
        try {
            params.put("post_title", productName);
            params.put("post_id", sellerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("productIdResponse", "fetchProductId: params - " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getProductIdUrl(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("productIdResponse", "onResponse: " + response);

                        try {
                            productId = response.getJSONArray("records").getJSONObject(0).optString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        isPResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isPResponseDone = false;
            }
        });
//        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }
}
