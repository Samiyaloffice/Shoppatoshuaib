package com.shoppa.RepositoryManager;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SellerIdRepository {

    private static Context context;
    private static SellerIdRepository instance;
    public boolean isResponseDone = false;
    private ArrayList<String> mSellerIdArrayList;
    private MutableLiveData<ArrayList<String>> mData;

    public static SellerIdRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new SellerIdRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchSellerId(String productName) {

        isResponseDone = false;
        mSellerIdArrayList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getSellerId(productName), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray records = response.getJSONArray("records");
                            if (records.length() > 0) {
                                JSONObject obj = records.getJSONObject(0);
                                if (!obj.optString("post_id").matches("null") &&
                                        !obj.optString("post_id").matches("0") &&
                                        !obj.optString("post_id").matches("")) {
                                    mSellerIdArrayList.add(obj.optString("post_id"));
                                }
                            }else{
                                Toast.makeText(context, "No Seller for this product", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        isResponseDone = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No Seller for this product", Toast.LENGTH_SHORT).show();
                isResponseDone = true;
            }
        });

        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public LiveData<ArrayList<String>> getSellerId() {
        mData = new MutableLiveData<>();
        mData.setValue(mSellerIdArrayList);
        return mData;
    }

}
