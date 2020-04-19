package com.taurusx.ads.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.taurusx.ads.demo.bean.Mediation;
import com.taurusx.ads.demo.constance.Constance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static List<Map.Entry<String, Mediation>> getMediation(String info) {
        HashMap<String, Mediation> map = new HashMap<>();
        try {
            JSONObject object = new JSONObject(info);
            JSONArray array = object.optJSONArray("mediation");
            int size = array.length();
            map.clear();
            for(int i=0; i<size; i++) {
                JSONObject item = array.getJSONObject(i);
                Mediation mediation = Mediation.fromJson(item);
                map.put(mediation.getName(), mediation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sortMediation(map);
    }

    private static List<Map.Entry<String, Mediation>> sortMediation(HashMap<String, Mediation> maps) {
        Set<Map.Entry<String, Mediation>> set = maps.entrySet();
        List<Map.Entry<String, Mediation>> list = new ArrayList<>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Mediation>>() {
            @Override
            public int compare(Map.Entry<String, Mediation> o1, Map.Entry<String, Mediation> o2) {
                return o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase());
            }
        });
        return list;
    }

    public static Mediation getMediationSingle(String info) {
        try {
            JSONObject object = new JSONObject(info);
            JSONArray array = object.optJSONArray("mediation");
            JSONObject mediationJson = array.getJSONObject(0);
            Mediation mediation = Mediation.fromJson(mediationJson);
            return mediation;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, Mediation> getMediationMap(String info) {
        HashMap<String, Mediation> map = new HashMap<>();
        try {
            JSONObject object = new JSONObject(info);
            JSONArray array = object.optJSONArray("mediation");
            int size = array.length();
            map.clear();
            for (int i = 0; i < size; i++) {
                JSONObject item = array.getJSONObject(i);
                Mediation mediation = Mediation.fromJson(item);
                map.put(mediation.getName(), mediation);
            }
            return map;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Intent getBaseIntent(Mediation mediation) {
        Intent intent = new Intent();
        if (mediation.getBannerJson() != null) {
            intent.putExtra(Constance.BUNDLE_TYPE_BANNER, mediation.getBannerJson().toString());
        }
        if (mediation.getInterstitalJson() != null) {
            intent.putExtra(Constance.BUNDLE_TYPE_INTERSTITIAL, mediation.getInterstitalJson().toString());
        }
        if (mediation.getNativeJson() != null) {
            intent.putExtra(Constance.BUNDLE_TYPE_NATIVE, mediation.getNativeJson().toString());
        }
        if (mediation.getRewardedVideoJson() != null) {
            intent.putExtra(Constance.BUNDLE_TYPE_REWARDED, mediation.getRewardedVideoJson().toString());
        }
        return intent;
    }
}
