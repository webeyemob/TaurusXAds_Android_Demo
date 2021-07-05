package com.taurusx.ads.demo.application;

import android.app.Application;

import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.constant.DownloadNetwork;
import com.taurusx.ads.core.api.segment.Segment;
import com.taurusx.ads.demo.constance.Constance;

public class TaurusApplication extends Application {

    private final String TAG = "TaurusApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        // 授权 GDPR；true 表示同意
        TaurusXAds.getDefault().setGdprConsent(true);

        // 显示调试日志，tag 为 TaurusXAds
        TaurusXAds.getDefault().setLogEnable(true);

        // 设置 Segment，会根据设置的 Segment 请求对应的 AdUnit 配置。
        // 目前支持渠道、App VersionName、时间、国家。
        // 开发者仅需要设置渠道，其余 SDK 会自动获取。
        TaurusXAds.getDefault().setSegment(Segment.Builder()
                // 设置渠道
                .setChannel("YOUR_CHANNEL")
                .build());

        // 设置全局 NetworkConfig
        TaurusXAds.getDefault().setGlobalNetworkConfigs(
                NetworkConfigs.Builder()
                        .build());

        // 开启 Network 调试模式
        TaurusXAds.getDefault().setNetworkDebugMode(true);
        // 为某个 Network 开启调试模式
        // TaurusXAds.getDefault().setNetworkDebugMode(Network.Xxx, true);

        // 开启 Network 测试模式，发布时务必关闭测试模式
        TaurusXAds.getDefault().setNetworkTestMode(true);
        // 为某个 Network 开启测试模式
        // TaurusXAds.getDefault().setNetworkTestMode(Network.Xxx, true);

        // 点击广告下载 App 时，需要用户二次确认的网络（目前仅用于穿山甲、优量汇）
        // 默认为 DownloadNetwork.NONE，不需要用户二次确认
        TaurusXAds.getDefault().setDownloadConfirmNetwork(DownloadNetwork.ALL);

        // 初始化
        TaurusXAds.getDefault().setTestMode(true);
        TaurusXAds.getDefault().init(this, Constance.APP_ID);
    }
}
