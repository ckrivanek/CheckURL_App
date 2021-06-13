package com.example.check;

import android.content.Context;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ServiceClient {
    private static ServiceClient serviceClient;
    private static Context context;
    private RequestQueue requestQueue;
    private String serviceEndpoint = "https://mopsdev.bw.edu/~ckrivane17/ws.php/URL";

    private ServiceClient(Context context) {
        ServiceClient.context = context;
    }

    synchronized public static ServiceClient getSharedInstance(Context context) {
        if (serviceClient == null) {
            serviceClient = new ServiceClient(context);
        }
        return serviceClient;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    private void addRequestToQueue(Request request) {
        getRequestQueue().add(request);
    }

    public void post(JSONObject jsonObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, this.serviceEndpoint, jsonObject, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(66000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.addRequestToQueue(request);
    }

    public void getURLs(JSONObject jsonObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, this.serviceEndpoint, jsonObject, listener, errorListener);
        this.addRequestToQueue(request);
    }

    public void getURL(JSONObject jsonObject, int urlID, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String endpoint = this.serviceEndpoint+ "/" + String.valueOf(urlID);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, endpoint, jsonObject, listener, errorListener);
        this.addRequestToQueue(request);
    }
}