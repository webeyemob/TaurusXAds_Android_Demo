package com.taurusx.ads.demo.application;

import android.app.Application;

import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.constant.DownloadNetwork;
import com.taurusx.ads.core.api.segment.Segment;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.AdMobGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.BaiduGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.DFPGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.GDTAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.GDTGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.KuaiShouGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.PrebidConfig;

import org.prebid.mobile.Host;

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
                        .addConfig(createAdMobConfig())
                        .addConfig(createBaiduConfig())
                        .addConfig(createDFPConfig())
                        .addConfig(createGDTConfig())
                        .addConfig(createKuaiShouConfig())
                        .addConfig(createPrebidConfig())
                        .build());

        // 开启 Network 调试模式
//        TaurusXAds.getDefault().setNetworkDebugMode(true);
        // 为某个 Network 开启调试模式
        // TaurusXAds.getDefault().setNetworkDebugMode(Network.Xxx, true);

        // 开启 Network 测试模式，发布时务必关闭测试模式
        TaurusXAds.getDefault().setNetworkTestMode(true);
        // 为某个 Network 开启测试模式
        // TaurusXAds.getDefault().setNetworkTestMode(Network.Xxx, true);

        // 点击广告下载 App 时，需要用户二次确认的网络（目前仅用于穿山甲、优量汇）
        // 默认为 DownloadNetwork.NONE，不需要用户二次确认
        TaurusXAds.getDefault().setDownloadConfirmNetwork(DownloadNetwork.NONE);

        // 初始化
        TaurusXAds.getDefault().setTestMode(true);
        TaurusXAds.getDefault().init(this, Constance.APP_ID);
    }

    private AdMobGlobalConfig createAdMobConfig() {
        return AdMobGlobalConfig.Builder()
                .addTestDevice("Your Test Device Id 1")
                .addTestDevice("Your Test Device Id 2")
                .build();
    }

    private BaiduGlobalConfig createBaiduConfig() {
        return BaiduGlobalConfig.Builder()
                .setPermissionReadDeviceID(true) // 读取设备 ID 的权限（建议授权），可有效提升广告的 eCPM
                .setPermissionAppList(true) // 读取已安装应用列表权限（建议授权），可有效提升广告的 eCPM
                .setPermissionLocation(true) // 读取粗略地理位置权限
                .setPermissionStorage(true) // 读写外部存储权限
                .build();
    }

    private DFPGlobalConfig createDFPConfig() {
        return DFPGlobalConfig.Builder()
                .addTestDevice("Your Test Device Id 1")
                .addTestDevice("Your Test Device Id 2")
                .build();
    }

    private GDTGlobalConfig createGDTConfig() {
        return GDTGlobalConfig.Builder()
                .setAppDownloadListener(new GDTAppDownloadListener() {
                    @Override
                    public void onIdle(String appName) {
                        // 空闲状态
                    }

                    /**
                     * @param appName  当前下载的应用名称
                     * @param progress 下载进度，0～100
                     */
                    @Override
                    public void onDownloadActive(String appName, int progress) {
                        // 下载中，会调用多次
                    }

                    @Override
                    public void onDownloadPaused(String appName) {
                        // 下载暂停
                    }

                    @Override
                    public void onDownloadFailed(String appName) {
                        // 下载失败
                    }

                    @Override
                    public void onDownloadFinished(String appName) {
                        // 下载完成
                    }

                    @Override
                    public void onInstalled(String appName) {
                        // 安装完成
                    }
                })
                .build();
    }

    private KuaiShouGlobalConfig createKuaiShouConfig() {
        return KuaiShouGlobalConfig.Builder()
                // Apk 下载时，是否在通知栏中提示
                .showNotification(true)
                .build();
    }

    private PrebidConfig createPrebidConfig() {
        return PrebidConfig.Builder()
                // 设置部署 Prebid 服务的 host，可以使用 AppNexus、Rubicon 提供的服务，也可以自己部署 Prebid 服务
                // 测试 Demo 中的 Video 广告时，必须使用 Host.RUBICON；测试其余广告位必须使用 Host.APPNEXUS。
                .setPrebidServerHost(Host.APPNEXUS)
                // 设置 Prebid 的超时时间，默认 2_000
                // .setTimeoutMillis(2_000)
                // 是否允许使用地理位置信息，默认 false
                // .setShareGeoLocation(true)
                // 设置用户唯一 Id
                // .setBuyerId("user id")
                .build();
    }
}
