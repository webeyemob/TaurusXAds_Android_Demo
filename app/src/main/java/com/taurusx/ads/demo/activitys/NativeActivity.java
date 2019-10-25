package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.qq.e.ads.nativ.ADSize;
import com.taurusx.ads.core.api.ad.nativead.NativeAd;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.mediation.networkconfig.GDTAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.GDTCustom2_0NativeConfig;
import com.taurusx.ads.mediation.networkconfig.GDTCustomNativeConfig;
import com.taurusx.ads.mediation.networkconfig.GDTExpressNativeConfig;
import com.taurusx.ads.mediation.networkconfig.OPPONativeTemplateConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokCustomBannerConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokCustomInterstitialConfig;


public class NativeActivity extends BaseActivity {

    private final String TAG = "NativeActivity";

    private Button mLoadButton;
    private Button mShowButton;
    private FrameLayout mContainer;

    private String mNativeId;

    private NativeAd mNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("NativeAd");
        setContentView(R.layout.activity_native);

        initData();
        initNativeAd();

        // Set NetworkConfigs
        setNetworkConfigs();
    }

    private void initNativeAd() {
        mContainer = findViewById(R.id.layout_container);
        mLoadButton = findViewById(R.id.native_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNativeAd.loadAd();
            }
        });
        mShowButton = findViewById(R.id.native_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                // Add NativeAd AdView To UI
                View adView = mNativeAd.getAdView();
                if (adView != null) {
                    mContainer.removeAllViews();
                    mContainer.addView(adView);
                }
            }
        });
        mShowButton = findViewById(R.id.native_show);
        if (mNativeId == null || TextUtils.isEmpty(mNativeId)) {
            mLoadButton.setVisibility(View.GONE);
            mShowButton.setVisibility(View.GONE);
            return;
        }

        // Create NativeAd
        mNativeAd = new NativeAd(this);
        mNativeAd.setAdUnitId(mNativeId);

        // Set Single NativeAdLayout
        mNativeAd.setNativeAdLayout(NativeAdLayout.getLargeLayout1());

        // Listen Ad load result
        mNativeAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "NativeAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "NativeAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "NativeAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "NativeAd onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "NativeAd onAdFailedToLoad: " + adError);
            }
        });


    }



    private void initData() {
        Intent intent = getIntent();
        mNativeId = intent.getStringExtra("native");
    }

    private void setNetworkConfigs() {
        mNativeAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(GDTExpressNativeConfig.Builder()
                        .setADSize(new ADSize(250, ADSize.AUTO_HEIGHT))
                        .build())
                .addConfig(GDTCustomNativeConfig.Builder()
                        .setAppDownloadListener(new GDTAppDownloadListener() {
                            @Override
                            public void onIdle(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onIdle: " + appName);
                            }

                            @Override
                            public void onDownloadActive(String appName, int progress) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadActive: "
                                        + appName + ", " + progress + "%");
                            }

                            @Override
                            public void onDownloadPaused(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onInstalled: " + appName);
                            }
                        })
                        .build())
                .addConfig(GDTCustom2_0NativeConfig.Builder()
                        .setAppDownloadListener(new GDTAppDownloadListener() {
                            @Override
                            public void onIdle(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onIdle: " + appName);
                            }

                            @Override
                            public void onDownloadActive(String appName, int progress) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadActive: "
                                        + appName + ", " + progress + "%");
                            }

                            @Override
                            public void onDownloadPaused(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onInstalled: " + appName);
                            }
                        })
                        .build())
                .addConfig(TikTokCustomInterstitialConfig.Builder()
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
                .addConfig(TikTokCustomBannerConfig.Builder()
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
                .addConfig(OPPONativeTemplateConfig.Builder()
                        .setWidth(234)
                        .setHeight(567)
                        .build())
                .build());
    }
}
