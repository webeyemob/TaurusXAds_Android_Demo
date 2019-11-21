package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.RewardedVideoAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleRewardedVideoAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.KuaiShouRewardedVideoConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokRewardedVideoConfig;


public class RewardedVideoActivity extends BaseActivity {

    private final String TAG = "RewardedVideoActivity";

    private Button mLoadButton;
    private Button mShowButton;
    private String mRewardedId;

    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("RewardedVideoAd");

        setContentView(R.layout.activity_rewardedvideo);
        initData();
        // Init RewardedVideoAd
        initRewardedVideoAd();
        // Set NetworkConfigs
        setNetworkConfigs();
    }

    private void initRewardedVideoAd() {
        // Create RewardedVideoAd
        mLoadButton = findViewById(R.id.rewardedvideo_load);
        // Load ad
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRewardedVideoAd.loadAd();
            }
        });
        mShowButton = findViewById(R.id.rewardedvideo_show);
        // Show ad
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isReady()) {
                    mRewardedVideoAd.show();
                }
                mShowButton.setEnabled(false);
            }
        });

        if (mRewardedId == null || TextUtils.isEmpty(mRewardedId)) {
            mLoadButton.setVisibility(View.GONE);
            mShowButton.setVisibility(View.GONE);
            return;
        }
        mRewardedVideoAd = new RewardedVideoAd(this);
        mRewardedVideoAd.setAdUnitId(mRewardedId);

        // Listen Ad load result
        mRewardedVideoAd.setAdListener(new SimpleRewardedVideoAdListener() {
            @Override
            public void onAdLoaded() {
                LogUtil.d(TAG, "RewardedVideoAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown() {
                LogUtil.d(TAG, "RewardedVideoAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                LogUtil.d(TAG, "RewardedVideoAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                LogUtil.d(TAG, "RewardedVideoAd onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.d(TAG, "RewardedVideoAd onAdFailedToLoad: " + adError);
            }

            @Override
            public void onVideoStarted() {
                LogUtil.d(TAG, "RewardedVideoAd onVideoStarted");
            }

            @Override
            public void onVideoCompleted() {
                LogUtil.d(TAG, "RewardedVideoAd onVideoCompleted");
            }

            @Override
            public void onRewarded(RewardedVideoAd.RewardItem rewardItem) {
                LogUtil.d(TAG, "RewardedVideoAd onRewarded: " + rewardItem);
            }

            @Override
            public void onRewardFailed() {
                LogUtil.d(TAG, "RewardedVideoAd onRewardFailed");
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mRewardedId = intent.getStringExtra(Constance.BUNDLE_TYPE_REWARDED);
    }

    private void setNetworkConfigs() {
        mRewardedVideoAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(TikTokRewardedVideoConfig.Builder()
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
                .addConfig(KuaiShouRewardedVideoConfig.Builder()
                        .setSkipThirtySecond(true)
                        .setShowSence("xx game")
                        .setShowLandscape(false)
                        .build())
                .build());
    }
}
