package com.example.indoortracking;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.widget.ListAdapter;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.api.gax.httpjson.HttpJsonCallOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class APIRequests {
    static HashMap<String, String> allPlans = new HashMap<>();

    public static Request<JSONArray> getAllPlans(Context ctx, String base_url, APICallback callback){
        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);


        String url = base_url+"getplans/";
        String bucket_url = "https://storage.googleapis.com/floorplan-images/";
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        Log.i("JSON RESPONSE", "--->" + response);


                        for(int i=0; i<response.length(); i++) {
                            try{
                                String title = response.getJSONObject(i).getString("title");
                                String img = response.getJSONObject(i).getString("plan");
                                allPlans.put(title, bucket_url+img);
                                Log.i("Hashmap", allPlans.toString());
                                if (response != null){
                                    callback.onSuccess(response);
                                }
                            } catch (Exception e){
                                Log.d("Error.Response", e.getMessage());
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPONSE ERROR", error.toString());
                    }
                });

        return getRequest;
    }

    public static StringRequest login(Context ctx, String base_url, String username, String password, APICallback callback){
        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);


        String url = base_url+"loginmobile/";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("STRING RESPONSE", "--->" + response);
                        if (response.equals("LOGIN_SUCCESS")){
                            callback.onSuccess(response);
                        }else{
                            try {
                                callback.onError(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPONSE ERROR", error.toString());
                    }
                }
        ) {
            @Override
            protected HashMap<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        return postRequest;
    }

    public static Request<JSONArray> sendMapping(Context ctx, String base_url){
        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);

        JSONArray mapped_data = null;

        String url = base_url+"savemapping/";
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url, mapped_data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        Log.i("STRING RESPONSE", "--->" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPONSE ERROR", error.toString());
                    }
                }
        );

        return postRequest;
    }

    public static StringRequest sendCurrentPlan(Context ctx, String base_url, String currentPlan){

        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(true);

        String url = base_url+"getplans/";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("STRING RESPONSE", "--->" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPONSE ERROR", error.toString());
                    }
                }
        ) {
            @Override
            protected HashMap<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("plan", currentPlan);
                return params;
            }
        };

        return postRequest;
    }

    public static HashMap<String, String> getHashMap() {
        return allPlans;
    }
}