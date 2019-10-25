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

    private String mBannerId;
    private String mInterstitialId;
    private String mNativeId;
    private String mRewardedId;
    private String mFeedListId;
    private String mSplashId;
    private String mMixViewId;
    private String mMixFullScreenId;

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
        mBannerId = intent.getStringExtra(Constance.BUNDLE_TYPE_BANNER);
        mInterstitialId = intent.getStringExtra(Constance.BUNDLE_TYPE_INTERSTITIAL);
        mNativeId = intent.getStringExtra(Constance.BUNDLE_TYPE_NATIVE);
        mRewardedId = intent.getStringExtra(Constance.BUNDLE_TYPE_REWARDED);
        mFeedListId = intent.getStringExtra(Constance.BUNDLE_TYPE_FEEDLIST);
        mSplashId = intent.getStringExtra(Constance.BUNDLE_TYPE_SPLASH);
        mMixViewId = intent.getStringExtra(Constance.BUNDLE_TYPE_MIXVIEW);
        mMixFullScreenId = intent.getStringExtra(Constance.BUNDLE_TYPE_MIXFULLSCREEN);

    }

    private void initBannerAdView() {
        mBannerButton = findViewById(R.id.banner_button);
        if (mBannerId == null || TextUtils.isEmpty(mBannerId)) {
            mBannerButton.setVisibility(View.GONE);
            return;
        }
        mBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, BannerActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_BANNER, mBannerId);
                startActivity(intent);
            }
        });
    }

    private void initNativeAd() {
        mNativeButton = findViewById(R.id.native_button);
        if (mNativeId == null || TextUtils.isEmpty(mNativeId)) {
            mNativeButton.setVisibility(View.GONE);
            return;
        }
        mNativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, NativeActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_NATIVE, mNativeId);
                startActivity(intent);
            }
        });
    }

    private void initInterstitialAd() {
        mInterstitialButton = findViewById(R.id.interstitial_button);

        if (mInterstitialId == null || TextUtils.isEmpty(mInterstitialId)) {
            mInterstitialButton.setVisibility(View.GONE);
            return;
        }
        mInterstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, InterstitialActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_INTERSTITIAL, mInterstitialId);
                startActivity(intent);
            }
        });

    }

    private void initRewardedVideoAd() {
        mRewardedButton = findViewById(R.id.rewardedvideo_button);

        if (mRewardedId == null || TextUtils.isEmpty(mRewardedId)) {
            mRewardedButton.setVisibility(View.GONE);
            return;
        }
        mRewardedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, RewardedVideoActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_REWARDED, mRewardedId);
                startActivity(intent);
            }
        });

    }

    private void initFeedListAd() {
        mFeedListButton = findViewById(R.id.feedlist_button);

        if (mFeedListId == null || TextUtils.isEmpty(mFeedListId)) {
            mFeedListButton.setVisibility(View.GONE);
            return;
        }
        mFeedListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, FeedListActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_FEEDLIST, mFeedListId);
                startActivity(intent);
            }
        });

    }

    private void initSplashAd() {
        mSpalshButton = findViewById(R.id.splash_button);

        if (mSplashId == null || TextUtils.isEmpty(mSplashId)) {
            mSpalshButton.setVisibility(View.GONE);
            return;
        }
        mSpalshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, SplashActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_SPLASH, mSplashId);
                startActivity(intent);
            }
        });

    }

    private void initMixViewAd() {
        mMixViewButton = findViewById(R.id.mixview_button);

        if (mMixViewId == null || TextUtils.isEmpty(mMixViewId)) {
            mMixViewButton.setVisibility(View.GONE);
            return;
        }
        mMixViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, MixViewActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MIXVIEW, mMixViewId);
                startActivity(intent);
            }
        });

    }

    private void initMixFullScreenAd() {
        mMixFullScreenButton = findViewById(R.id.mixfullscreen_button);

        if (mMixFullScreenId == null || TextUtils.isEmpty(mMixFullScreenId)) {
            mMixFullScreenButton.setVisibility(View.GONE);
            return;
        }
        mMixFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MediationActivity.this, MixFullScreenActivity.class);
                intent.putExtra(Constance.BUNDLE_TYPE_MIXFULLSCREEN, mMixFullScreenId);
                startActivity(intent);
            }
        });

    }
}

