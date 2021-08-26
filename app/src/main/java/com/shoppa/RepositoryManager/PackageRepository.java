package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.PackageModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PackageRepository {

    private static Context context;
    private static PackageRepository instance;
    public boolean isResponseDone = false;
    ArrayList<PackageModel> mPackageArrayList;
    MutableLiveData<ArrayList<PackageModel>> mPackageData;

    public static PackageRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new PackageRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchPackageData() {

        isResponseDone = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getPackageUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mPackageArrayList = new ArrayList<>();
                        try {
                            JSONArray records = response.getJSONArray("records");
                            Log.i("packagesResponse", "onResponse: response = " + response);
                            for (int i = 0; i < records.length(); i++) {
                                JSONObject obj = records.getJSONObject(i);
                                mPackageArrayList.add(new PackageModel(obj.optString("id"),
                                        obj.optString("plan_name"),
                                        obj.optString("plan_cost"),
                                        obj.optString("discount"),
                                        obj.optString("plan_date"),
                                        obj.optString("Priority_Search_Listing"),
                                        obj.optString("Display_Products"),
                                        obj.optString("Display_Selling_Leads"),
                                        obj.optString("Number_of_Inquiries_to_send_per_day_(Max.)"),
                                        obj.optString("Access_to_New_Buying_Leads"),
                                        obj.optString("Access_to_3_Million_Global_Buyer_Database"),
                                        obj.optString("Free_Company_Verification_Service")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        isResponseDone = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                DataManager.checkVolleyError(error, context);
                isResponseDone = true;
            }
        });
    NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public MutableLiveData<ArrayList<PackageModel>> getPackageData() {
        mPackageData = new MutableLiveData<>();
        mPackageData.setValue(mPackageArrayList);
        Log.i("PackageData", "run: dataLength = " + mPackageArrayList.size());
        return mPackageData;
    }

}
