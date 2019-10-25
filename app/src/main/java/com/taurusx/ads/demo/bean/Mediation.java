package com.taurusx.ads.demo.bean;

import org.json.JSONObject;

public class Mediation {
    private String mName;
    private String mBannerId;
    private String mInterstitialId;
    private String mRewardedId;
    private String mNativeId;
    private String mFeedListId;
    private String mSplashId;

    public Mediation() {

    }

    public static Mediation fromJson(JSONObject object) {
        Mediation mediation = new Mediation();
        mediation.mName = object.optString("adName");
        mediation.mBannerId = object.optString("banner", "");
        mediation.mInterstitialId = object.optString("interstitial", "");
        mediation.mNativeId = object.optString("native", "");
        mediation.mRewardedId = object.optString("rewarded", "");
        mediation.mFeedListId = object.optString("feedlist", "");
        mediation.mSplashId = object.optString("splash", "");

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

    public void setFeedListId(String feedListId) {
        this.mFeedListId = feedListId;
    }

    public void setSplashId(String splashId) {
        this.mSplashId = splashId;
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

    public String getFeedListId() {
        return mFeedListId;
    }

    public String getSplashId() {
        return mSplashId;
    }
}
