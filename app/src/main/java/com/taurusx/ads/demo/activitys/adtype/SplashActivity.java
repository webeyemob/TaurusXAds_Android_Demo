package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.taurusx.ads.core.api.ad.SplashAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.SplashAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;
import com.taurusx.ads.mediation.networkconfig.SigmobSplashConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokSplashConfig;

public class SplashActivity extends FragmentActivity {

    private final String TAG = "SplashActivity";

    private String mSplashAdUnitId;
    private SplashAd mSplashAd;

    private FrameLayout mContainer;
    private Handler mExitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mSplashAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        mContainer = findViewById(R.id.layout_container);

        initSplash();

        // Exit Page When SplashAd Load More Than 3000ms
        mExitHandler = new Handler();
        mExitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        // 开屏广告禁止返回
        // super.onBackPressed();
    }

    private void initSplash() {
        // Create SplashAd
        mSplashAd = new SplashAd(this);
        mSplashAd.setAdUnitId(mSplashAdUnitId);

        // Set container to show SplashAd
        mSplashAd.setContainer(mContainer);

        // (Optional) Set Network special Config
        mSplashAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(createSigmobSplashConfig())
                .addConfig(createTikTokSplashConfig())
                .build());

        // Bottom area settings (For: OPPO, Sigmob and vivo)
        // Set the title & description displayed at the bottom of the splash ad
        mSplashAd.setBottomText("TaurusX Ads", "Demo for TaurusX Ads Sdk");
        // Or set the view displayed at the bottom of splash ad
        // View bottomArea = LayoutInflater.from(this).inflate(R.layout.layout_splash_bottom_area, null);
        // mSplashAd.setBottomView(bottomArea);

        // Set SplashAd Load Event
        mSplashAd.setADListener(new SplashAdListener() {
            @Override
            public void onAdLoaded(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + iLineItem.getName());
                mExitHandler.removeCallbacksAndMessages(null);
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
            public void onAdSkipped(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdSkipped: " + iLineItem.getName());
            }

            @Override
            public void onAdClosed(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdClosed: " + iLineItem.getName());
                finish();
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.e(TAG, "onAdFailedToLoad: " + adError);
                Utils.toast(SplashActivity.this, adError.toString());
                mExitHandler.removeCallbacksAndMessages(null);
                finish();
            }
        });

        // Load SplashAd
        mSplashAd.loadAd();
    }

    private SigmobSplashConfig createSigmobSplashConfig() {
        return SigmobSplashConfig.Builder()
                /**
                 * 广告结束，广告内容是否自动隐藏；默认 false。
                 * 若开屏和应用共用 Activity，建议 false。
                 * 若开屏是单独 Activity，建议true。
                 */
                .setDisableAutoHideAd(true)
                .build();
    }

    private TikTokSplashConfig createTikTokSplashConfig() {
        return TikTokSplashConfig.Builder()
                // 具体尺寸，单位 px；默认为屏幕大小
                // .setImageAcceptedSize(1080, 1920)
                // 宽度充满屏幕，高度固定
                // .setImageAcceptedSize(ViewGroup.LayoutParams.MATCH_PARENT, 1800)
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