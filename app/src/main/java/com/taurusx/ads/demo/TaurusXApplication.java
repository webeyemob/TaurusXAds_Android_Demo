package com.taurusx.ads.demo;

import android.app.Application;

import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.litho.editor.flipper.LithoFlipperDescriptors;
import com.facebook.soloader.SoLoader;
import com.facebook.stetho.Stetho;
import com.mopub.common.SdkConfiguration;
import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.constant.DownloadNetwork;
import com.taurusx.ads.core.api.segment.Segment;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.mediation.networkconfig.MoPubGlobalConfig;

public class TaurusXApplication extends Application {

    private final String TAG = "TaurusXApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        SoLoader.init(this, false);

//        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
        final FlipperClient client = AndroidFlipperClient.getInstance(this);
//        client.addPlugin(new InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()));

//        ComponentsConfiguration.isDebugModeEnabled = true;

        final DescriptorMapping descriptorMapping = DescriptorMapping.withDefaults();
// This adds Litho capabilities to the layout inspector.
        LithoFlipperDescriptors.add(descriptorMapping);

        client.addPlugin(new InspectorFlipperPlugin(this, descriptorMapping));

        client.start();
//        }

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
                        .addConfig(createMoPubConfig())
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

    private MoPubGlobalConfig createMoPubConfig() {
        return MoPubGlobalConfig.Builder()
                .setSdkConfigurationBuilder(new SdkConfiguration.Builder("Any Valid MoPub AdUnitId In Your App")
                        // .withAdditionalNetwork(...)
                        // .withMediatedNetworkConfiguration(...)
                )
                .build();
    }
}