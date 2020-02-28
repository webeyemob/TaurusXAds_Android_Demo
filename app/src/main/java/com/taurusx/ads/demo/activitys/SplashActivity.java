package com.taurusx.ads.demo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.taurusx.ads.core.api.ad.SplashAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.core.api.utils.ScreenUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.OPPOSplashConfig;
import com.taurusx.ads.mediation.networkconfig.SigmobSplashConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokSplashConfig;

public class SplashActivity extends Activity {

    private final String TAG = "SplashActivity";

    private FrameLayout mContainer;
    private Handler mExitHandler;

    private SplashAd mSplashAd;
    private String mSplashId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initData();
        initSplash();

        // Set NetworkConfigs
        setNetworkConfigs();
    }

    private void initData() {
        Intent intent = getIntent();
        mSplashId = intent.getStringExtra(Constance.BUNDLE_TYPE_SPLASH);
    }

    private void initSplash() {
        mContainer = findViewById(R.id.layout_container);
        // Exit Page When SplashAd Load More Than 3000ms
        mExitHandler = new Handler();
        mExitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
        mSplashAd = new SplashAd(this);
        // Set AdUnitId
        mSplashAd.setAdUnitId(mSplashId);
        mSplashAd.setContainer(mContainer);
        // Set SplashAd Load Event
        mSplashAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "SplashAd onAdLoaded");
                mExitHandler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "SplashAd onAdFailedToLoad: " + adError.toString());
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "SplashAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "SplashAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "SplashAd onAdClosed");
                // Exit Page When SplashAd Closed
                finish();
            }
        });
        // Load SplashAd
        mSplashAd.loadAd();
    }

    private void setNetworkConfigs() {
        View bottomArea = LayoutInflater.from(this).inflate(R.layout.layout_splash_oppo_bottom_area, null);
        mSplashAd.setNetworkConfigs(NetworkConfigs.Builder()
//                .addConfig(VivoSplashConfig.Builder()
//                        .setTitle("App Name")
//                        .setDesc("App Desc")
//                        .build())
                .addConfig(OPPOSplashConfig.Builder()
                        .setBottomArea(bottomArea)
                        .build())
                .addConfig(TikTokSplashConfig.Builder()
                        .setImageAcceptedSize(1080, 1920)
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
                .addConfig(SigmobSplashConfig.Builder()
                        .setDisableAutoHideAd(false)
                        .setDescription("TaurusXDemo")
                        .setTitle("TaurusX")
                        .build()
                )
                .build());
    }

}
