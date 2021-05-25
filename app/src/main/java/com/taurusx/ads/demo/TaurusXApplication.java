package com.taurusx.ads.demo;

import android.app.Application;

import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.constant.DownloadNetwork;
import com.taurusx.ads.core.api.segment.Segment;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.mediation.networkconfig.MobrainGlobalConfig;
import com.taurusx.ads.mediation.networkconfig.PrebidConfig;
import com.taurusx.ads.mediation.networkconfig.TopOnInterstitialConfig;
import com.taurusx.ads.mediation.networkconfig.TuiaGlobalConfig;

import org.prebid.mobile.Host;

public class TaurusXApplication extends Application {

    private final String TAG = "TaurusXApplication";

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
                        .addConfig(createMobrainConfig())
                        .addConfig(createPrebidConfig())
                        .addConfig(createTuiaConfig())
                        .addConfig(createTopOnConfig())
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
        TaurusXAds.getDefault().init(this, Constant.APP_ID);
    }

    private MobrainGlobalConfig createMobrainConfig() {
        return MobrainGlobalConfig.Builder()
                // 设置是否为计费用户
                .isPanglePaid(false)
                // 设置落地页主题，默认为 TTAdConstant.TITLE_BAR_THEME_LIGHT
                .setPangleTitleBarTheme(com.bytedance.msdk.api.TTAdConstant.TITLE_BAR_THEME_LIGHT)
                // 设置是否允许 SDK 弹出通知
                .allowPangleShowNotify(true)
                // 设置是否允许落地页出现在锁屏上面
                .allowPangleShowPageWhenScreenLock(true)
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

    private TuiaGlobalConfig createTuiaConfig() {
        return TuiaGlobalConfig.Builder()
                // .setUserId("test-userid-123456")
                .build();
    }

    private TopOnInterstitialConfig createTopOnConfig() {
        return TopOnInterstitialConfig.Builder().setIsUseRewardVideoAsInterstitialInSigmob(true)
                .build();
    }
}