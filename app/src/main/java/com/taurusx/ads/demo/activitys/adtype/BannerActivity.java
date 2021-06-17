package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.taurusx.ads.core.api.ad.BannerAdView;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.AdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;
import com.taurusx.ads.mediation.networkconfig.AdMobBannerConfig;
import com.taurusx.ads.mediation.networkconfig.DFPBannerConfig;
import com.taurusx.ads.mediation.networkconfig.InMobiBannerConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressBannerConfig;


public class BannerActivity extends BaseActivity {

    private final String TAG = "BannerActivity";

    private String mBannerAdUnitId;
    private BannerAdView mBannerAdView;

    private Button mLoadButton;
    private Button mShowButton;
    private Button mHideButton;

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_banner);

        mBannerAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initBannerAdView();

        mContainer = findViewById(R.id.layout_banner);
        // Add Banner to UI
        mContainer.addView(mBannerAdView);
        mContainer.setVisibility(View.INVISIBLE);

        mLoadButton = findViewById(R.id.banner_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBannerAdView.loadAd();
            }
        });

        mShowButton = findViewById(R.id.banner_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                mHideButton.setEnabled(true);
                mContainer.setVisibility(View.VISIBLE);
            }
        });

        mHideButton = findViewById(R.id.banner_hide);
        mHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(true);
                mHideButton.setEnabled(false);
                mContainer.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initBannerAdView() {
        // Create BannerAdView
        mBannerAdView = new BannerAdView(this);
        mBannerAdView.setAdUnitId(mBannerAdUnitId);

        // (Optional) Set Network special Config
        mBannerAdView.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(createAdMobBannerConfig())
                .addConfig(createDFPBannerConfig())
                .addConfig(createInMobiBannerConfig())
                .addConfig(createTikTokExpressBannerConfig())
                .build());

        // Listen Ad load result
        mBannerAdView.setADListener(new AdListener() {
            @Override
            public void onAdLoaded(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + lineItem.getName());
                if (mContainer.getVisibility() == View.INVISIBLE) {
                    mShowButton.setEnabled(true);
                    mHideButton.setEnabled(false);
                }
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
                Utils.toast(BannerActivity.this, adError.toString());
            }
        });
    }

    private AdMobBannerConfig createAdMobBannerConfig() {
        // 根据当前屏幕的方向设置 Adaptive Banner 的尺寸
        return AdMobBannerConfig.Builder()
                // .setAnchoredAdaptiveBannerSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 320))
                // .setInlineAdaptiveBannerSize(AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(this, 300))
                .build();

        // 或者，如果你想预加载，可以指定期望的屏幕方向。
//        return AdMobBannerConfig.Builder()
//                .setAnchoredAdaptiveBannerSize(AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, 320))
//                .setInlineAdaptiveBannerSize(AdSize.getLandscapeInlineAdaptiveBannerAdSize(this, 728))
//                .build();
    }

    private DFPBannerConfig createDFPBannerConfig() {
        // 根据当前屏幕的方向设置 Adaptive Banner 的尺寸
        return DFPBannerConfig.Builder()
                // .setAnchoredAdaptiveBannerSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 320))
                // .setInlineAdaptiveBannerSize(AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(this, 300))
                .build();

        // 或者，如果你想预加载，可以指定期望的屏幕方向。
//        return DFPBannerConfig.Builder()
//                .setAnchoredAdaptiveBannerSize(AdSize.getPortraitAnchoredAdaptiveBannerAdSize(this, 320))
//                .setInlineAdaptiveBannerSize(AdSize.getLandscapeInlineAdaptiveBannerAdSize(this, 728))
//                .build();
    }

    private InMobiBannerConfig createInMobiBannerConfig() {
        return InMobiBannerConfig.Builder()
                // 设置刷新动画；默认为 InMobiBanner.AnimationType.ANIMATION_OFF
                // .setAnimationType(InMobiBanner.AnimationType.ANIMATION_OFF)
                .build();
    }

    private TikTokExpressBannerConfig createTikTokExpressBannerConfig() {
        return TikTokExpressBannerConfig.Builder()
                // 使用默认的 Dislike Dialog 样式，仅需设置 Dislike 回调
                // .setDislikeCallback(new TikTokGetDislikeCallback() {})
                // 或自定义 Dislike Dialog
                // .setDislikeDialog(new TikTokGetDislikeDialog() {})
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
