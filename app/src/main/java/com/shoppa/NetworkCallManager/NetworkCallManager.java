package com.shoppa.NetworkCallManager;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkCallManager {
    private static NetworkCallManager networkCallManager;
    private RequestQueue requestQueue;
    private long mRequestStartTime;


    private static Context mctx;
    private NetworkCallManager(Context context){
        this.mctx=context;
        this.requestQueue=getRequestQueue();

    }
    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized NetworkCallManager getInstance(Context context){
        if (networkCallManager==null){
            networkCallManager=new NetworkCallManager(context);
        }
        return networkCallManager;
    }
    public<T> void addToRequestQue(Request<T> request){
        request.setRetryPolicy(new DefaultRetryPolicy(100000, 1, 1.0f));

        requestQueue.add(request);

    }
}