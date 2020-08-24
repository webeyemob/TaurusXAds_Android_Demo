package com.taurusx.ads.demo;

import android.app.Application;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.mintegral.msdk.MIntegralUser;
import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.constant.DownloadNetwork;
import com.taurusx.ads.core.api.segment.Segment;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.mediation.networkconfig.AdMobGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.BaiduGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.DFPGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.GDTAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.GDTGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.KuaiShouGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.MintegralGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.PrebidConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.TuiaGlobalConfig;

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
                        .addConfig(createMintegralConfig())
                        .addConfig(createPrebidConfig())
                        .addConfig(createTikTokConfig())
                        .addConfig(createTuiaConfig())
                        .build());

        // 开启 Network 调试模式
        TaurusXAds.getDefault().setNetworkDebugMode(true);
        // 为某个 Network 开启调试模式
        // TaurusXAds.getDefault().setNetworkDebugMode(Network.Xxx, true);

        // 开启 Network 测试模式，发布时务必关闭测试模式
        TaurusXAds.getDefault().setNetworkTestMode(true);
        // 为某个 Network 开启测试模式
        // TaurusXAds.getDefault().setNetworkTestMode(Network.Xxx, true);

        // 点击广告下载 App 时，需要用户二次确认的网络（目前仅用于穿山甲、优量汇<横幅、信息流>）
        // 默认为 DownloadNetwork.NONE，不需要用户二次确认
        TaurusXAds.getDefault().setDownloadConfirmNetwork(DownloadNetwork.NONE);

        // 初始化
        TaurusXAds.getDefault().setTestMode(true);
        TaurusXAds.getDefault().init(this, Constant.APP_ID);
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

    private MintegralGlobalConfig createMintegralConfig() {
        MIntegralUser mIntegralUser = new MIntegralUser();
        // 是否是付费用户，1-付费用户, 0-普通用户
        // mIntegralUser.setPay(...);
        // 性别，1-男性, 2-女性
        // mIntegralUser.setGender(...);
        // 年龄
        // mIntegralUser.setAge(...);
        // 默认用户类型
        // mIntegralUser.setCustom("Custom parameters");
        // 经纬度
        // mIntegralUser.setLng(...);
        // mIntegralUser.setLat(...);

        return MintegralGlobalConfig.Builder()
                .setUser(mIntegralUser)
                // 如果设置为 true，将不会收集用户来展示个性化广告
                // 默认为 false
                // .setCCPAStatus(false)
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

    private TikTokGlobalConfig createTikTokConfig() {
        return TikTokGlobalConfig.Builder()
                // 设置是否为计费用户
                .setIsPaid(false)
                // 设置用户画像的关键词列表，不能超过为 1000 个字符
                // .setKeywords("")
                // 设置额外的用户信息，不能超过为 1000 个字符
                // .setData("")
                // 设置落地页主题，默认为 TTAdConstant.TITLE_BAR_THEME_LIGHT
                .setTitleBarTheme(TTAdConstant.TITLE_BAR_THEME_LIGHT)
                // 设置是否允许 SDK 弹出通知
                .setAllowShowNotify(true)
                // 设置是否允许落地页出现在锁屏上面
                .setAllowShowPageWhenScreenLock(true)
                // 设置是否支持多进程
                .setSupportMultiProcess(false)
                // 监听应用类广告下载
                .setAppDownloadListener(createTikTokAppDownloadListener())
                .build();
    }

    private TikTokAppDownloadListener createTikTokAppDownloadListener() {
        return new TikTokAppDownloadListener() {
            @Override
            public void onIdle() {
                // 空闲状态
            }

            /**
             * @param totalBytes 安装包总字节数 -1 ：未知
             * @param currBytes 当前已下载的字节数
             * @param fileName 下载文件路径
             * @param appName 当前下载的应用名称
             */
            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                // 下载中，会调用多次
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                // 下载暂停
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                // 下载失败
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                // 下载完成
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                // 安装完成
            }
        };
    }

    private TuiaGlobalConfig createTuiaConfig() {
        return TuiaGlobalConfig.Builder()
                .setUserId("test-userid-123456")
                .build();
    }
}