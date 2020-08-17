package com.taurusx.ads.demo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String TAG = "Utils";

    public static String getAssetsContent(Context context, String assetFile) {
        if (context == null || TextUtils.isEmpty(assetFile)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(assetFile);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer)) > 0) {
                builder.append(new String(buffer, 0, read));
            }
            is.close();
            return builder.toString();
        } catch (Exception e) {
            Log.e(TAG, "parse plugin failed: " + e.toString());
        }
        return null;
    }

    public static List<JSONObject> getMediationList(Context context, String jsonFileName) {
        String jsonString = Utils.getAssetsContent(context, jsonFileName);

        List<JSONObject> mediationList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray array = object.optJSONArray("mediation");
            int size = array.length();
            for (int i = 0; i < size; i++) {
                JSONObject mediationObject = array.getJSONObject(i);
                mediationList.add(mediationObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mediationList;
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
