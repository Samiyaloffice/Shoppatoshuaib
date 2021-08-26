package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.BuyerModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuyerRepository {

    private static Context context;
    private static BuyerRepository instance;
    public boolean isProResponseDone = false, isBuyerResponseDone = false;

    private ArrayList<String> mProductNameList;
    private MutableLiveData<ArrayList<String>> mProductData;
    private ArrayList<BuyerModel> mBuyerArrayList;
    private MutableLiveData<ArrayList<BuyerModel>> mBuyerData;

    public static BuyerRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new BuyerRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchSellerProduct() {

        mProductNameList = new ArrayList<>();
        isProResponseDone = false;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getSellerProductApi2(UserDataModel.getmInstance().getId()), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("buyerData", "onResponse: sellerProducts - " + response);

                        try {
                            JSONArray records = response.getJSONArray("records");
                            for (int i = 3; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mProductNameList.add(obj.optString("product_name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        isProResponseDone = true;
                        fetchBuyer(mProductNameList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isProResponseDone = true;
                isBuyerResponseDone = true;

                Log.i("BuyerResponse", "onErrorResponse: isBuyerResponse Repository - " + isBuyerResponseDone);
            }
        });

        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    public LiveData<ArrayList<String>> getSellerProducts() {
        mProductData = new MutableLiveData<>();
        mProductData.setValue(mProductNameList);
        return mProductData;
    }

    public void fetchBuyer(ArrayList<String> productNameList) {

        isBuyerResponseDone = false;
        mBuyerArrayList = new ArrayList<>();

        for (int i = 0; i < productNameList.size(); i++) {

            int finalI = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getProductBuyer(productNameList.get(i)), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("buyerData", "onResponse: Buyer Data - " + response);
                            try {
                                JSONArray records = response.getJSONArray("records");
                                for (int i = 0; i < records.length(); i++) {
                                    JSONObject obj = records.getJSONObject(i);
                                    mBuyerArrayList.add(new BuyerModel(obj.optString("Id"),
                                            obj.optString("Buyer_name"),
                                            obj.optString("Buyer_Product"),
                                            obj.optString("Buyers_Email"),
                                            obj.optString("Buyer_Mobile"),
                                            obj.optString("Buyers_Address")));
                                    Log.i("BuyerCount", "fetchBuyer: count - " + mBuyerArrayList.size());
                                    DataManager.dashboardBuyerCount.setText("" + mBuyerArrayList.size());

                                    if (mBuyerArrayList.size() == 0) {
                                        Log.i("BuyerCount", "setBuyerCount: count - " + mBuyerArrayList.size());
                                        DataManager.dashboardBuyerCount.setVisibility(View.GONE);
                                    } else if (mBuyerArrayList.size() > 99) {
                                        Log.i("BuyerCount", "setBuyerCount: count - " + mBuyerArrayList.size());
                                        DataManager.dashboardBuyerCount.setVisibility(View.VISIBLE);
                                        DataManager.dashboardBuyerCount.setText("99+");
                                    } else {
                                        Log.i("BuyerCount", "setBuyerCount: count - " + mBuyerArrayList.size());
                                        DataManager.dashboardBuyerCount.setVisibility(View.VISIBLE);
                                        DataManager.dashboardBuyerCount.setText(mBuyerArrayList.size() + "");
                                    }

                                }

                                if (records.length() == mBuyerArrayList.size()) {
                                    isBuyerResponseDone = true;
//                                    Log.i("BuyerCount", "fetchBuyer: count - " + mBuyerArrayList.size());
                                }

//                                Log.i("BuyerCount", "onResponse: response" + records);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   DataManager.checkVolleyError(error, context);
                    isBuyerResponseDone = true;
                }
            });

            NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
            NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);

        }

    }

    public LiveData<ArrayList<BuyerModel>> getBuyerData() {
        mBuyerData = new MutableLiveData<>();
        mBuyerData.setValue(mBuyerArrayList);
        return mBuyerData;
    }

}
