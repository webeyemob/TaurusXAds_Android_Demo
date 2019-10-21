package com.taurusx.ads.demo.bean;

import org.json.JSONObject;

public class Mediation {
    private String mName;
    private String mBannerId;
    private String mInterstitialId;
    private String mRewardedId;
    private String mNativeId;

    public Mediation() {

    }

    public static Mediation fromJson(JSONObject object) {
        Mediation mediation = new Mediation();
        mediation.mName = object.optString("adName");
        mediation.mBannerId = object.optString("banner");
        mediation.mInterstitialId = object.optString("interstitial");
        mediation.mNativeId = object.optString("native");
        mediation.mRewardedId = object.optString("rewarded");
        return mediation;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setBannerId(String bannerId) {
        this.mBannerId = bannerId;
    }

    public void setInterstitialId(String interstitialId) {
        this.mInterstitialId = interstitialId;
    }

    public void setRewardedId(String rewardedId) {
        this.mRewardedId = rewardedId;
    }

    public void setNativeId(String nativeId) {
        this.mNativeId = nativeId;
    }

    public String getmName() {
        return mName;
    }

    public String getBannerId() {
        return mBannerId;
    }

    public String getInterstitialId() {
        return mInterstitialId;
    }

    public String getRewardedId() {
        return mRewardedId;
    }

    public String getNativeId() {
        return mNativeId;
    }
}
