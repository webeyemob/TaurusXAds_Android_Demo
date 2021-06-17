package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.RewardedVideoAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.RewardedVideoAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;
import com.taurusx.ads.mediation.networkconfig.KuaiShouRewardedVideoConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokRewardedVideoConfig;


public class RewardedVideoActivity extends BaseActivity {

    private final String TAG = "RewardedVideoActivity";

    private String mRewardedVideoAdUnitId;
    private RewardedVideoAd mRewardedVideoAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_rewardedvideo);

        mRewardedVideoAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initRewardedVideoAd();

        mLoadButton = findViewById(R.id.rewardedvideo_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRewardedVideoAd.loadAd();
            }
        });

        mShowButton = findViewById(R.id.rewardedvideo_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                if (mRewardedVideoAd.isReady()) {
                    mRewardedVideoAd.show(RewardedVideoActivity.this);
                }
            }
        });
    }

    private void initRewardedVideoAd() {
        // Create RewardedVideoAd
        mRewardedVideoAd = new RewardedVideoAd(this);
        mRewardedVideoAd.setAdUnitId(mRewardedVideoAdUnitId);

        // Set Video Muted, default is sound
        // mRewardedVideoAd.setMuted(false);

        // (Optional) Set Network special Config
        mRewardedVideoAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(createKuaiShouRewardedVideoConfig())
                .addConfig(createTikTokRewardedVideoConfig())
                .build());

        // Listen Ad load result
        mRewardedVideoAd.setADListener(new RewardedVideoAdListener() {
            @Override
            public void onAdLoaded(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + lineItem.getName());
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdShown: " + lineItem.getName());
            }

            @Override
            public void onAdClicked(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdClicked: " + lineItem.getName());
            }

            @Override
            public void onAdClosed(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdClosed: " + lineItem.getName());
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.e(TAG, "onAdFailedToLoad: " + adError);
                Utils.toast(RewardedVideoActivity.this, adError.toString());
            }

            @Override
            public void onVideoStarted(ILineItem lineItem) {
                LogUtil.d(TAG, "onVideoStarted: " + lineItem.getName());
            }

            @Override
            public void onVideoCompleted(ILineItem lineItem) {
                LogUtil.d(TAG, "onVideoCompleted: " + lineItem.getName());
            }

            @Override
            public void onRewarded(ILineItem lineItem, RewardedVideoAd.RewardItem rewardItem) {
                LogUtil.d(TAG, "onRewarded: " + lineItem.getName() + ", rewardItem: " + rewardItem);
            }

            @Override
            public void onRewardFailed(ILineItem lineItem) {
                LogUtil.e(TAG, "onRewardFailed: " + lineItem.getName());
                Utils.toast(RewardedVideoActivity.this, "onRewardFailed");
            }
        });
    }

    private KuaiShouRewardedVideoConfig createKuaiShouRewardedVideoConfig() {
        return KuaiShouRewardedVideoConfig.Builder()
                // 30 秒后可关闭
                .setSkipThirtySecond(false)
                .build();
    }

    private TikTokRewardedVideoConfig createTikTokRewardedVideoConfig() {
        return TikTokRewardedVideoConfig.Builder()
                // 监听应用类广告下载
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
                .build();
    }
}