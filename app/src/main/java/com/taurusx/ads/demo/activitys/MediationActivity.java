package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;


public class MediationActivity extends BaseActivity {

    private final String TAG = "MainActivity";

    private String mBannerId;
    private String mInterstitialId;
    private String mNativeId;
    private String mRewardedId;
    private String mNetworkName;

    // BannerAd
    private Button mBannerLoadButton;

    // NativeAd
    private Button mNativeLoadButton;

    // InterstitialAd
    private Button mInterstitialLoadButton;

    // RewardedVideoAd
    private Button mRewardedLoadButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mediation);
        initData();
        getActionBar().setTitle(mNetworkName);
        // Init Ads
        initBannerAdView();
        initNativeAd();
        initInterstitialAd();
        initRewardedVideoAd();
    }

    private void initData() {
        Intent intent = getIntent();
        mNetworkName = intent.getStringExtra(Constance.BUNDLE_NETWORK_NAME);
        mBannerId = intent.getStringExtra(Constance.BUNDLE_TYPE_BANNER);
        mInterstitialId = intent.getStringExtra(Constance.BUNDLE_TYPE_INTERSTITIAL);
        mNativeId = intent.getStringExtra(Constance.BUNDLE_TYPE_NATIVE);
        mRewardedId = intent.getStringExtra(Constance.BUNDLE_TYPE_REWARDED);
    }

    private void initBannerAdView() {
        // Load Ad
        mBannerLoadButton = findViewById(R.id.banner_button);
        if (mBannerId == null || TextUtils.isEmpty(mBannerId)) {
            mBannerLoadButton.setVisibility(View.GONE);
            return;
        }
        mBannerLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, BannerActivity.class);
                intent.putExtra("banner", mBannerId);
                startActivity(intent);
            }
        });
    }

    private void initNativeAd() {
        mNativeLoadButton = findViewById(R.id.native_button);
        if (mNativeId == null || TextUtils.isEmpty(mNativeId)) {
            mNativeLoadButton.setVisibility(View.GONE);
            return;
        }
        mNativeLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, NativeActivity.class);
                intent.putExtra("native", mNativeId);
                startActivity(intent);
            }
        });
    }

    private void initInterstitialAd() {
        // Create InterstitialAd
        mInterstitialLoadButton = findViewById(R.id.interstitial_button);

        if (mInterstitialId == null || TextUtils.isEmpty(mInterstitialId)) {
            mInterstitialLoadButton.setVisibility(View.GONE);
            return;
        }
        mInterstitialLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, InterstitialActivity.class);
                intent.putExtra("interstitial", mInterstitialId);
                startActivity(intent);
            }
        });

    }

    private void initRewardedVideoAd() {
        // Create RewardedVideoAd
        mRewardedLoadButton = findViewById(R.id.rewardedvideo_button);

        if (mRewardedId == null || TextUtils.isEmpty(mRewardedId)) {
            mRewardedLoadButton.setVisibility(View.GONE);
            return;
        }
        mRewardedLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, RewardedVideoActivity.class);
                intent.putExtra("rewarded", mRewardedId);
                startActivity(intent);
            }
        });

    }
}

