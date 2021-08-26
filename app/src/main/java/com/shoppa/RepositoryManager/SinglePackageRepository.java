package com.shoppa.RepositoryManager;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shoppa.DataManager.DataManager;
import com.shoppa.Model.PackageModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;

import org.json.JSONObject;

public class SinglePackageRepository {

    private static Context context;
    private static SinglePackageRepository instance;
    public boolean isResponseDone = false;
    PackageModel mPackageModel;
    MutableLiveData<PackageModel> mData;

    public static SinglePackageRepository getInstance(Context cxt) {
        if (instance == null) {
            instance = new SinglePackageRepository();
            context = cxt;
        }
        return instance;
    }

    public void fetchPackage(String packageName) {

        isResponseDone = false;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.getSinglePackageUrl(packageName), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("singlePackageResponse", "onResponse: singlePackageResponse - " + response);

                        mPackageModel = new PackageModel(response.optString("id"),
                                response.optString("plan_name"),
                                response.optString("plan_cost"),
                                response.optString("discount"),
                                response.optString("plan_date"),
                                response.optString("Priority_Search_Listing"),
                                response.optString("Display_Products"),
                                response.optString("Display_Selling_Leads"),
                                response.optString("Number_of_Inquiries_to_send_per_day_(Max.)"),
                                response.optString("Access_to_New_Buying_Leads"),
                                response.optString("Access_to_3_Million_Global_Buyer_Database"),
                                response.optString("Free_Company_Verification_Service"));

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

    public LiveData<PackageModel> getSinglePackage() {
        mData = new MutableLiveData<>();
        mData.setValue(mPackageModel);
        return mData;
    }

}
