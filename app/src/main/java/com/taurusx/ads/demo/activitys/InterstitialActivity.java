package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qq.e.ads.cfg.VideoOption;
import com.taurusx.ads.core.api.ad.InterstitialAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.mediation.networkconfig.GDTInterstitial2_0Config;
import com.taurusx.ads.mediation.networkconfig.KuaiShouInterstitialConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressInterstitialConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokFullScreenVideoConfig;

public class InterstitialActivity extends BaseActivity {

    private final String TAG = "InterstitialActivity";

    private String mInterstitialId;
    private InterstitialAd mInterstitialAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("InterstitialAd");
        setContentView(R.layout.activity_interstitial);

        mInterstitialId = getIntent().getStringExtra("interstitial");
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
                if (mInterstitialAd.isReady()) {
                    mInterstitialAd.show(InterstitialActivity.this);
                }
                mShowButton.setEnabled(false);
            }
        });
    }

    private void initInterstitialAd() {
        // Create InterstitialAd
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(mInterstitialId);

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
    }

    private GDTInterstitial2_0Config createGDTInterstitial2_0Config() {
        return GDTInterstitial2_0Config.Builder()
                // 视频播放配置
                .setVideoOption(new VideoOption.Builder()
                        // .setXxx(Xxx)
                        .build())
                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
                // 此设置会影响广告填充，请谨慎设置。
                // .setMinVideoDuration(5)
                // .setMaxVideoDuration(60)
                .build();
    }

    private KuaiShouInterstitialConfig createKuaiShouInterstitialConfig() {
        return KuaiShouInterstitialConfig.Builder()
                // 显示是否为横屏
                .setShowLandscape(false)
                // 拓展场景参数，可不设置
                .setShowSence("xx game")
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
                .setAppDownloadListener(new TikTokAppDownloadListener() {})
                .build();
    }
}