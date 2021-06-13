package com.example.check.model;

        import android.content.Context;

        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.example.check.ServiceClient;
        import com.google.gson.Gson;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.lang.reflect.Type;
        import java.util.ArrayList;

public class URLModel {
    public interface GetCheckResponse {
        void response(URLResponse urls);
        void error();
    }

    public interface GetHistoryResponse {
        void response(URLResponse url);
        void error();
    }

    public void checkURL(URL request, GetCheckResponse handler){
        Gson gson = new Gson();
        String json = gson.toJson(request);
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(json);
        }
        catch (JSONException jsonException){
            //TODO
            handler.error();
        }

        ServiceClient serviceClient = ServiceClient.getSharedInstance(null);
        serviceClient.post(jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        URLResponse url = gson.fromJson(response.toString(), URLResponse.class);
                        handler.response(url);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        handler.error();
                    }
                });
    }

    public void getHistory(URL request, GetHistoryResponse handler){
        Gson gson = new Gson();
        String json = gson.toJson(request);
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(json);
        }
        catch (JSONException jsonException) {
            //TODO
            handler.error();
        }

        ServiceClient serviceClient = ServiceClient.getSharedInstance(null);
        if (request.urlId!=0){
            serviceClient.getURL(jsonObject, request.urlId, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Gson gson = new Gson();
                            URLResponse url = gson.fromJson(response.toString(), URLResponse.class);
                            handler.response(url);
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            handler.error();
                        }
                    });
        }
        else{
            serviceClient.getURLs(jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Gson gson = new Gson();
                            URLResponse url = gson.fromJson(response.toString(), URLResponse.class);
                            handler.response(url);
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            handler.error();
                        }
                    });
        }

    }
}