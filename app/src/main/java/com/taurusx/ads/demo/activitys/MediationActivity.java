package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.demo.mixad.MixFullScreenActivity;
import com.taurusx.ads.demo.mixad.MixViewActivity;


public class MediationActivity extends BaseActivity {

    private final String TAG = "MediationActivity";

    private String mNetworkName;

    private String mBannerInfo;
    private String mInterstitialInfo;
    private String mNativeInfo;
    private String mRewardedInfo;
    private String mFeedListInfo;
    private String mSplashInfo;
    private String mMixViewInfo;
    private String mMixFullScreenInfo;

    // BannerAd
    private Button mBannerButton;

    // NativeAd
    private Button mNativeButton;

    // InterstitialAd
    private Button mInterstitialButton;

    // RewardedVideoAd
    private Button mRewardedButton;

    // FeedListAd
    private Button mFeedListButton;

    // SplashAd
    private Button mSpalshButton;

    // FeedListAd
    private Button mMixViewButton;

    // SplashAd
    private Button mMixFullScreenButton;


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
        initFeedListAd();
        initSplashAd();
        initMixViewAd();
        initMixFullScreenAd();
    }

    private void initData() {
        Intent intent = getIntent();
        mNetworkName = intent.getStringExtra(Constance.BUNDLE_NETWORK_NAME);
        mBannerInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_BANNER);
        mInterstitialInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_INTERSTITIAL);
        mNativeInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_NATIVE);
        mRewardedInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_REWARDED);
        mFeedListInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_FEEDLIST);
        mSplashInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_SPLASH);
        mMixViewInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_MIXVIEW);
        mMixFullScreenInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_MIXFULLSCREEN);

    }

    private void initBannerAdView() {
        mBannerButton = findViewById(R.id.banner_button);
        if (mBannerInfo == null || TextUtils.isEmpty(mBannerInfo)) {
            mBannerButton.setVisibility(View.GONE);
            return;
        }
        mBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_BANNER);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mBannerInfo);
                startActivity(intent);
            }
        });
    }

    private void initNativeAd() {
        mNativeButton = findViewById(R.id.native_button);
        if (mNativeInfo == null || TextUtils.isEmpty(mNativeInfo)) {
            mNativeButton.setVisibility(View.GONE);
            return;
        }
        mNativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_NATIVE);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mNativeInfo);
                startActivity(intent);
            }
        });
    }

    private void initInterstitialAd() {
        mInterstitialButton = findViewById(R.id.interstitial_button);

        if (mInterstitialInfo == null || TextUtils.isEmpty(mInterstitialInfo)) {
            mInterstitialButton.setVisibility(View.GONE);
            return;
        }
        mInterstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_INTERSTITIAL);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mInterstitialInfo);
                startActivity(intent);
            }
        });

    }

    private void initRewardedVideoAd() {
        mRewardedButton = findViewById(R.id.rewardedvideo_button);

        if (mRewardedInfo == null || TextUtils.isEmpty(mRewardedInfo)) {
            mRewardedButton.setVisibility(View.GONE);
            return;
        }
        mRewardedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_REWARDED);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mRewardedInfo);
                startActivity(intent);
            }
        });

    }

    private void initFeedListAd() {
        mFeedListButton = findViewById(R.id.feedlist_button);

        if (mFeedListInfo == null || TextUtils.isEmpty(mFeedListInfo)) {
            mFeedListButton.setVisibility(View.GONE);
            return;
        }
        mFeedListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_FEEDLIST);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mFeedListInfo);
                startActivity(intent);
            }
        });

    }

    private void initSplashAd() {
        mSpalshButton = findViewById(R.id.splash_button);

        if (mSplashInfo == null || TextUtils.isEmpty(mSplashInfo)) {
            mSpalshButton.setVisibility(View.GONE);
            return;
        }
        mSpalshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_SPLASH);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mSplashInfo);
                startActivity(intent);
            }
        });

    }

    private void initMixViewAd() {
        mMixViewButton = findViewById(R.id.mixview_button);

        if (mMixViewInfo == null || TextUtils.isEmpty(mMixViewInfo)) {
            mMixViewButton.setVisibility(View.GONE);
            return;
        }
        mMixViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_MIXVIEW);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mMixViewInfo);
                startActivity(intent);
            }
        });

    }

    private void initMixFullScreenAd() {
        mMixFullScreenButton = findViewById(R.id.mixfullscreen_button);

        if (mMixFullScreenInfo == null || TextUtils.isEmpty(mMixFullScreenInfo)) {
            mMixFullScreenButton.setVisibility(View.GONE);
            return;
        }
        mMixFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, AdContainerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME, mNetworkName);
                intent.putExtra(Constance.BUNDLE_TYPE_JSON, Constance.BUNDLE_TYPE_MIXFULLSCREEN);
                intent.putExtra(Constance.BUNDLE_TYPE_INFO, mMixFullScreenInfo);
                startActivity(intent);
            }
        });

    }
}

