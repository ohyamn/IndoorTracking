package com.example.indoortracking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface APICallback {
    void onSuccess(String result);
    void onSuccess(JSONArray result);
    void onError(String result) throws Exception;
}
