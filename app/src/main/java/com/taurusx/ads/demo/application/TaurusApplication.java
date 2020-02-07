package com.taurusx.ads.demo.application;

import android.app.Application;
import android.view.View;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.model.Network;
import com.taurusx.ads.core.api.segment.Segment;
import com.taurusx.ads.core.api.tracker.SimpleTrackerListener;
import com.taurusx.ads.core.api.tracker.TaurusXAdsTracker;
import com.taurusx.ads.core.api.tracker.TrackerInfo;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.AdMobGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.DFPGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.FiveGlobalNetworkConfig;
import com.taurusx.ads.mediation.networkconfig.GDTAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.GDTGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.KuaiShouGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.OPPONativeTemplateConfig;
import com.taurusx.ads.mediation.networkconfig.OPPOSplashConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokDrawFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokSplashConfig;

public class TaurusApplication extends Application {

    private final String TAG = "TaurusApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        TaurusXAds.getDefault().setLogEnable(true);
        TaurusXAds.getDefault().setGdprConsent(true);
        TaurusXAds.getDefault().setSegment(Segment.Builder()
                        .setChannel("test_channel")
                        .build());
        setGlobalNetworkConfigs();
        // TaurusXAds.getDefault().setNetworkDebugMode(true);
        // TaurusXAds.getDefault().setNetworkTestMode(true);
        TaurusXAds.getDefault().init(this, Constance.APP_UNIT_ID);

        registerTracker();
    }

    private void registerTracker() {
        TaurusXAdsTracker.getInstance().registerListener(new SimpleTrackerListener() {
            @Override
            public void onAdRequest(TrackerInfo info) {
                LogUtil.d(TAG, "track onAdRequest");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onAdLoaded(TrackerInfo info) {
                LogUtil.d(TAG, "track onAdLoaded");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onAdCallShow(TrackerInfo info) {
                LogUtil.d(TAG, "track onAdCallShow");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onAdShown(TrackerInfo info) {
                LogUtil.d(TAG, "track onAdShown");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onAdClicked(TrackerInfo info) {
                LogUtil.d(TAG, "track onAdClicked");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onAdClosed(TrackerInfo info) {
                LogUtil.d(TAG, "track onAdClosed");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onAdFailedToLoad(TrackerInfo info) {
                LogUtil.d(TAG, "track onAdFailedToLoad");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onVideoStarted(TrackerInfo info) {
                LogUtil.d(TAG, "track onVideoStarted");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onVideoCompleted(TrackerInfo info) {
                LogUtil.d(TAG, "track onVideoCompleted");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onRewarded(TrackerInfo info) {
                LogUtil.d(TAG, "track onRewarded");
                LogUtil.d(TAG, info.toString());
            }

            @Override
            public void onRewardFailed(TrackerInfo info) {
                LogUtil.d(TAG, "track onRewardFailed");
                LogUtil.d(TAG, info.toString());
            }
        });
    }

    private void setGlobalNetworkConfigs() {
        TaurusXAds.getDefault().setGlobalNetworkConfigs(
                NetworkConfigs.Builder()
                        .addConfig(DFPGlobalConfig.Builder()
                                .addTestDevice("acb")
                                .addTestDevice("ddd")
                                .addTestDevice("ABDBC6250A42D15197AD0FFEFDFE2BF2")
                                .build())
                        .addConfig(AdMobGlobalConfig.Builder()
                                .addTestDevice("Your Test Device Id 1")
                                .addTestDevice("Your Test Device Id 2")
                                .build())
                        .addConfig(TikTokDrawFeedListConfig.Builder()
                                .setCanInterruptVideoPlay(true)
                                .setPauseIcon(R.drawable.ic_taurusx_ads_close)
                                .setPauseIconSize(100)
                                .build())
                        .addConfig(TikTokExpressFeedListConfig.Builder()
                                .setExpressViewAcceptedSize(200, 0)
                                .build())
                        .addConfig(TikTokSplashConfig.Builder()
                                .setImageAcceptedSize(1000, 1000)
                                .build())
                        .addConfig(OPPOSplashConfig.Builder()
                                .setBottomArea(new View(this))
                                .setTitle(R.string.settings)
                                .setDesc("share_info")
                                .build())
                        .addConfig(OPPONativeTemplateConfig.Builder()
                                .setWidth(222)
                                .setHeight(111)
                                .build())
                        .addConfig(TikTokGlobalConfig.Builder()
                                // 设置是否为计费用户，默认为非计费用户
                                .setIsPaid(false)
                                // 设置用户画像的关键词列表，不能超过为 1000 个字符
                                .setKeywords("food, run, movie")
                                // 设置额外的用户信息，不能超过为 1000 个字符
                                .setData("")
                                // 设置落地页主题，默认为 TTAdConstant.TITLE_BAR_THEME_LIGHT
                                .setTitleBarTheme(TTAdConstant.TITLE_BAR_THEME_LIGHT)
                                // 允许直接下载 Apk 的网络集合
                                .setDirectDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_MOBILE)
                                // 设置是否允许 SDK 弹出通知
                                .setAllowShowNotify(true)
                                // 设置是否允许落地页出现在锁屏上面
                                .setAllowShowPageWhenScreenLock(true)
                                // 设置是否支持多进程
                                .setSupportMultiProcess(false)
                                .setAppDownloadListener(new TikTokAppDownloadListener() {
                                    @Override
                                    public void onIdle() {
                                        LogUtil.d(TAG, "global TikTokAppDownloadListener: onIdle");
                                    }

                                    @Override
                                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                                        LogUtil.d(TAG, "global TikTokAppDownloadListener: onDownloadActive: " + fileName);
                                    }

                                    @Override
                                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                                        LogUtil.d(TAG, "global TikTokAppDownloadListener: onDownloadPaused: " + fileName);
                                    }

                                    @Override
                                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                                        LogUtil.d(TAG, "global TikTokAppDownloadListener: onDownloadFailed: " + fileName);
                                    }

                                    @Override
                                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                                        LogUtil.d(TAG, "global TikTokAppDownloadListener: onDownloadFinished: " + fileName);
                                    }

                                    @Override
                                    public void onInstalled(String fileName, String appName) {
                                        LogUtil.d(TAG, "global TikTokAppDownloadListener: onInstalled: " + fileName);
                                    }
                                })
                                .build())
                        .addConfig(GDTGlobalConfig.Builder()
                                .setAppDownloadListener(new GDTAppDownloadListener() {
                                    @Override
                                    public void onIdle(String appName) {
                                        LogUtil.d(TAG, "global GDTAppDownloadListener: onIdle: " + appName);
                                    }

                                    @Override
                                    public void onDownloadActive(String appName, int progress) {
                                        LogUtil.d(TAG, "global GDTAppDownloadListener: onDownloadActive: "
                                                + appName + ", " + progress + "%");
                                    }

                                    @Override
                                    public void onDownloadPaused(String appName) {
                                        LogUtil.d(TAG, "global GDTAppDownloadListener: onDownloadPaused: " + appName);
                                    }

                                    @Override
                                    public void onDownloadFailed(String appName) {
                                        LogUtil.d(TAG, "global GDTAppDownloadListener: onDownloadFailed: " + appName);
                                    }

                                    @Override
                                    public void onDownloadFinished(String appName) {
                                        LogUtil.d(TAG, "global GDTAppDownloadListener: onDownloadFinished: " + appName);
                                    }

                                    @Override
                                    public void onInstalled(String appName) {
                                        LogUtil.d(TAG, "global GDTAppDownloadListener: onInstalled: " + appName);
                                    }
                                })
                                .build())
                        .addConfig(FiveGlobalNetworkConfig.Builder()
                                .setTestMode(true)
                                .build())
                        .addConfig(KuaiShouGlobalConfig.Builder()
                                .showNotification(true)
                                .setAppName("AppTest")
                                .debug(false)
                                .build())
                        .build());
    }

}
