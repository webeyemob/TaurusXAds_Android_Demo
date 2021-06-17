package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.InterstitialAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.InterstitialAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;
import com.taurusx.ads.mediation.networkconfig.GDTInterstitial2_0Config;
import com.taurusx.ads.mediation.networkconfig.KuaiShouInterstitialConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressInterstitialConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokFullScreenVideoConfig;

public class InterstitialActivity extends BaseActivity {

    private final String TAG = "InterstitialActivity";

    private String mInterstitialAdUnitId;
    private InterstitialAd mInterstitialAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_interstitial);

        mInterstitialAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initInterstitialAd();

        mLoadButton = findViewById(R.id.interstitial_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAd.loadAd();
            }
        });

        mShowButton = findViewById(R.id.interstitial_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                if (mInterstitialAd.isReady()) {
                    mInterstitialAd.show(InterstitialActivity.this);
                }
            }
        });
    }

    private void initInterstitialAd() {
        // Create InterstitialAd
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(mInterstitialAdUnitId);

        // Set Video Muted, default is sound
        // mInterstitialAd.setMuted(false);

        // (Optional) Set Network special Config
        mInterstitialAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(createGDTInterstitial2_0Config())
                .addConfig(createKuaiShouInterstitialConfig())
                .addConfig(createTikTokExpressInterstitialConfig())
                .addConfig(createTikTokFullScreenVideoConfig())
                .build());

        // Listen Ad load result
        mInterstitialAd.setADListener(new InterstitialAdListener() {
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
                Utils.toast(InterstitialActivity.this, adError.toString());
            }

            @Override
            public void onVideoStarted(ILineItem lineItem) {
                LogUtil.d(TAG, "onVideoStarted: " + lineItem.getName());
            }

            @Override
            public void onVideoCompleted(ILineItem lineItem) {
                LogUtil.d(TAG, "onVideoCompleted: " + lineItem.getName());
            }
        });
    }

    private GDTInterstitial2_0Config createGDTInterstitial2_0Config() {
        return GDTInterstitial2_0Config.Builder()
                // 视频播放配置
                // .setVideoOption(new VideoOption.Builder()
                //        // .setXxx(Xxx)
                //        .build())
                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
                // 此设置会影响广告填充，请谨慎设置。
                // .setMinVideoDuration(5)
                // .setMaxVideoDuration(60)
                .build();
    }

    private KuaiShouInterstitialConfig createKuaiShouInterstitialConfig() {
        return KuaiShouInterstitialConfig.Builder()
                // 30 秒后可关闭
                .setSkipThirtySecond(false)
                .build();
    }

    private TikTokExpressInterstitialConfig createTikTokExpressInterstitialConfig() {
        return TikTokExpressInterstitialConfig.Builder()
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

    private TikTokFullScreenVideoConfig createTikTokFullScreenVideoConfig() {
        return TikTokFullScreenVideoConfig.Builder()
                // 监听应用类广告下载
                .setAppDownloadListener(new TikTokAppDownloadListener() {
                })
                .build();
    }
}