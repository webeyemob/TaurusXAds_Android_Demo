package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.qq.e.ads.cfg.VideoOption;
import com.taurusx.ads.core.api.ad.config.AdSize;
import com.taurusx.ads.core.api.ad.nativead.NativeAd;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.AdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;
import com.taurusx.ads.mediation.networkconfig.GDTAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.GDTCustom2_0NativeConfig;
import com.taurusx.ads.mediation.networkconfig.GDTExpressNativeConfig;
import com.taurusx.ads.mediation.networkconfig.KuaiShouAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.KuaiShouCustomNativeConfig;
import com.taurusx.ads.mediation.networkconfig.MintegralNativeConfig;

@Deprecated
public class NativeActivity extends BaseActivity {

    private final String TAG = "NativeActivity";

    private String mNativeAdUnitId;
    private NativeAd mNativeAd;

    private Button mLoadButton;
    private Button mShowButton;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_native);

        mNativeAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initNativeAd();

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
                if (adView != null && adView != mContainer.getChildAt(0)) {
                    mContainer.removeAllViews();
                    mContainer.addView(adView);
                }
            }
        });

        mContainer = findViewById(R.id.layout_container);
    }

    private void initNativeAd() {
        // Create NativeAd
        mNativeAd = new NativeAd(this);
        mNativeAd.setAdUnitId(mNativeAdUnitId);

        // Set Custom NativeAdLayout To Render Ad
//        mNativeAd.setNativeAdLayout(NativeAdLayout.Builder()
//                .setLayoutIdWithDefaultViewId(R.layout.native_custom_layout)
//                .build());

        // Or Set NativeAdLayout Supported By TaurusX To Render Ad
        mNativeAd.setNativeAdLayout(NativeAdLayout.getLargeLayout1());

        // Or Set NativeAdLayoutPolicy To Render Ad
        // WeSdk Support SequenceNativeAdLayoutPolicy And RandomNativeAdLayoutPolicy
        // You Can Implement Your NativeAdLayoutPolicy
//        mNativeAd.setNativeAdLayout(SequenceNativeAdLayoutPolicy.Builder()
//                .add(NativeAdLayout.getLargeLayout1())
//                .add(NativeAdLayout.getLargeLayout2())
//                .add(NativeAdLayout.getLargeLayout3())
//                .add(NativeAdLayout.getLargeLayout4())
//                .build());

        // Or Set MultiStyleNativeAdLayout To Render Ad
//        mNativeAd.setNativeAdLayout(MultiStyleNativeAdLayout.Builder()
//                .setDefaultLayout(NativeAdLayout.getMediumLayout())
//                .setLargeImageLayout(NativeAdLayout.getLargeLayout4())
//                .setGroupImageLayout(NativeAdLayout.getLargeLayout1())
//                .setVideoLayout(NativeAdLayout.getLargeLayout3())
//                .build());

        // Set Express Native Size
        mNativeAd.setExpressAdSize(new AdSize(360, 250));

        // Set Video Muted, default is muted
        // mNativeAd.setMuted(false);

        // (Optional) Set Network special Config
        mNativeAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(createGDTCustom2_0NativeConfig())
                .addConfig(createGDTExpressNativeConfig())
                .addConfig(createKuaiShouCustomNativeConfig())
                .addConfig(createMintegralNativeConfig())
                .build());

        // Listen Ad load result
        mNativeAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + iLineItem.getName());
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdShown: " + iLineItem.getName());
            }

            @Override
            public void onAdClicked(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdClicked: " + iLineItem.getName());
            }

            @Override
            public void onAdClosed(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdClosed: " + iLineItem.getName());
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.e(TAG, "onAdFailedToLoad: " + adError);
                Utils.toast(NativeActivity.this, adError.toString());
            }
        });
    }

    private GDTCustom2_0NativeConfig createGDTCustom2_0NativeConfig() {
        return GDTCustom2_0NativeConfig.Builder()
                // 视频播放配置
                .setVideoOption(new VideoOption.Builder()
                        // .setXxx(Xxx)
                        .build())
                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
                // 此设置会影响广告填充，请谨慎设置。
                // .setMinVideoDuration(5)
                // .setMaxVideoDuration(60)
                // 监听应用类广告下载
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
                .build();
    }

    private GDTExpressNativeConfig createGDTExpressNativeConfig() {
        return GDTExpressNativeConfig.Builder()
                // 视频播放配置
                .setVideoOption(new VideoOption.Builder()
                        // .setXxx(Xxx)
                        .build())
                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
                // 此设置会影响广告填充，请谨慎设置。
                // .setMinVideoDuration(5)
                // .setMaxVideoDuration(60)
                .build();
    }

    private KuaiShouCustomNativeConfig createKuaiShouCustomNativeConfig() {
        return KuaiShouCustomNativeConfig.Builder()
                .setAppDownloadListener(new KuaiShouAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        LogUtil.d(TAG ,"KuaiShou onIdle");
                    }

                    @Override
                    public void onProgressUpdate(int i) {
                        LogUtil.d(TAG ,"KuaiShou onProgressUpdate: " + i);
                    }

                    @Override
                    public void onDownloadFinished() {
                        LogUtil.d(TAG ,"KuaiShou onDownloadFinished");
                    }

                    @Override
                    public void onInstalled() {
                        LogUtil.d(TAG ,"KuaiShou onInstalled");
                    }
                })
                .build();
    }

    private MintegralNativeConfig createMintegralNativeConfig() {
        return MintegralNativeConfig.Builder()
                // 是否循环播放视频
                .setAllowLoopPlay(false)
                // 屏幕方向改变时是否重新加载视频
                .setAllowScreenChange(false)
                // 在视频未准备好播放之前是否显示图片
                .setAllowVideoRefresh(false)
                // 是否显示进度
                .setProgressVisibility(true)
                // 是否允许全屏显示
                .setIsAllowFullScreen(false)
                // 是否显示音量按钮
                .setSoundIndicatorVisibility(true)
                .build();
    }
}