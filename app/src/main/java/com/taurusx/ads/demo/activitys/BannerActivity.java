package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.inmobi.ads.InMobiBanner;
import com.taurusx.ads.core.api.ad.BannerAdView;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.InMobiBannerConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressBannerConfig;


public class BannerActivity extends BaseActivity {

    private final String TAG = "BannerActivity";

    private Button mLoadButton;
    private LinearLayout mContainer;
    private String mBannerId;

    private BannerAdView mBannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("BannerAdView");
        setContentView(R.layout.activity_banner);

        initData();
        // Init BannerAdView
        initBannerAdView();
        // Set NetworkConfigs
        setNetworkConfigs();

    }

    private void initData() {
        Intent intent = getIntent();
        mBannerId = intent.getStringExtra(Constance.BUNDLE_TYPE_BANNER);
    }

    private void initBannerAdView() {
        mContainer = findViewById(R.id.layout_banner);
        mLoadButton = findViewById(R.id.banner_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load BannerAdView
                mBannerAdView.loadAd();
            }
        });

        if (mBannerId == null || TextUtils.isEmpty(mBannerId)) {
            mLoadButton.setVisibility(View.GONE);
            Toast.makeText(BannerActivity.this, "bannerId is null", Toast.LENGTH_SHORT).show();
            return;
        }

        mBannerAdView = new BannerAdView(this);
        mBannerAdView.setAdUnitId(mBannerId);
        mContainer.addView(mBannerAdView);

        // Listen Ad load result
        mBannerAdView.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "BannerAdView onAdLoaded");
                ILineItem lineItem = mBannerAdView.getReadyLineItem();
                Log.d(TAG, "BannerAdView Ready LineItem: "+lineItem);
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "BannerAdView onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "BannerAdView onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "BannerAdView onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "BannerAdView onAdFailedToLoad: " + adError.toString());
            }
        });

    }

    private void setNetworkConfigs() {
        mBannerAdView.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(InMobiBannerConfig.Builder()
                        .setAnimationType(InMobiBanner.AnimationType.ANIMATION_OFF)
                        .build())
                .addConfig(TikTokExpressBannerConfig.Builder()
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
                .build());
    }
}
