package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.InterstitialAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.mediation.networkconfig.KuaiShouInterstitialConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressInterstitialConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokFullScreenVideoConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokNormalInterstitialConfig;

public class InterstitialActivity extends BaseActivity {

    private final String TAG = "InterstitialActivity";

    private Button mLoadButton;
    private Button mShowButton;

    private InterstitialAd mInterstitialAd;
    private String mInterstitialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("InterstitialAd");

        setContentView(R.layout.activity_interstitial);
        initData();
        // Init InterstitialAd
        initInterstitialAd();
        // Set NetworkConfigs
        setNetworkConfigs();
    }

    private void initInterstitialAd() {
        // Create InterstitialAd
        mLoadButton = findViewById(R.id.interstitial_load);
        mShowButton = findViewById(R.id.interstitial_show);

        if (mInterstitialId == null || TextUtils.isEmpty(mInterstitialId)) {
            mLoadButton.setVisibility(View.GONE);
            mShowButton.setVisibility(View.GONE);
            return;
        }

        mInterstitialAd = new InterstitialAd(InterstitialActivity.this);
        mInterstitialAd.setAdUnitId(mInterstitialId);

        // Listen Ad load result
        mInterstitialAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "InterstitialAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "InterstitialAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "InterstitialAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "InterstitialAd onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "InterstitialAd onAdFailedToLoad: " + adError);
            }
        });

        // Load ad
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAd.loadAd();
            }
        });

        // Show ad
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isReady()) {
                    mInterstitialAd.show();
                }
                mShowButton.setEnabled(false);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mInterstitialId = intent.getStringExtra("interstitial");
    }

    private void setNetworkConfigs() {
        mInterstitialAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(TikTokNormalInterstitialConfig.Builder()
                        .setAppDownloadListener(new TikTokAppDownloadListener() {
                            @Override
                            public void onIdle() {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
                            }

                            @Override
                            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
                            }

                            @Override
                            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
                            }
                        })
                        .build())
                .addConfig(TikTokExpressInterstitialConfig.Builder()
                        .setAppDownloadListener(new TikTokAppDownloadListener() {
                            @Override
                            public void onIdle() {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
                            }

                            @Override
                            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
                            }

                            @Override
                            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
                            }
                        })
                        .build())
                .addConfig(TikTokFullScreenVideoConfig.Builder()
                        .setAppDownloadListener(new TikTokAppDownloadListener() {
                            @Override
                            public void onIdle() {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
                            }

                            @Override
                            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
                            }

                            @Override
                            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
                            }
                        })
                        .build())
                .addConfig(KuaiShouInterstitialConfig.Builder()
                        .setShowLandscape(false)
                        .setShowSence("xx game")
                        .setSkipThirtySecond(true)
                        .build())
                .build());
    }



}
