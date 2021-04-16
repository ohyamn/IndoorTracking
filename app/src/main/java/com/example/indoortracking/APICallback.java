package com.example.indoortracking;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface APICallback {
    void onSuccess(String result);
    void onSuccess(JSONArray result);
    void onError(VolleyError result) throws Exception;
    void onError(String result);
}
