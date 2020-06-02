package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.taurusx.ads.core.api.ad.SplashAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";

    private String mSplashId;
    private SplashAd mSplashAd;

    private FrameLayout mContainer;
    private Handler mExitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashId = getIntent().getStringExtra(Constance.BUNDLE_TYPE_SPLASH);
        mContainer = findViewById(R.id.layout_container);

        initSplash();

        // Exit Page When SplashAd Load More Than 3000ms
        mExitHandler = new Handler();
        mExitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    private void initSplash() {
        // Create SplashAd
        mSplashAd = new SplashAd(this);
        mSplashAd.setAdUnitId(mSplashId);

        // Set container to show SplashAd
        mSplashAd.setContainer(mContainer);

        // (Optional) Set Network special Config
        mSplashAd.setNetworkConfigs(NetworkConfigs.Builder()
                // .addConfig(createOPPOSplashConfig())
//                .addConfig(createSigmobSplashConfig())
//                .addConfig(createTikTokSplashConfig())
                // .addConfig(createVivoSplashConfig())
                .build());

        // Set SplashAd Load Event
        mSplashAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "SplashAd onAdLoaded");
                mExitHandler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "SplashAd onAdFailedToLoad: " + adError.toString());
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "SplashAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "SplashAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "SplashAd onAdClosed");
                finish();
            }
        });

        // Load SplashAd
        mSplashAd.loadAd();
    }

//    private OPPOSplashConfig createOPPOSplashConfig() {
//        // 配置开屏广告底部的布局，可以设置 View 或标题和描述。
//
////        View bottomArea = LayoutInflater.from(this).inflate(R.layout.layout_splash_oppo_bottom_area, null);
////        return OPPOSplashConfig.Builder()
////                .setBottomArea(bottomArea)
////                .build();
//
////        return OPPOSplashConfig.Builder()
////                // 设置标题 id
////                .setTitle(R.string.app_name)
////                // 设置描述 id
////                .setDesc(R.string.app_desc)
////                // 设置标题
////                // .setTitle("App Name")
////                // 设置描述
////                // .setDesc("App Desc")
////                .build();
//    }

//    private SigmobSplashConfig createSigmobSplashConfig() {
//        return SigmobSplashConfig.Builder()
//                // 设置开屏底部显示的标题，一般为应用名称
//                .setTitle("App Name")
//                // 设置开屏底部显示的描述
//                .setDescription("App Desc")
//                /**
//                 * 广告结束，广告内容是否自动隐藏；默认 false。
//                 * 若开屏和应用共用 Activity，建议 false。
//                 * 若开屏是单独 Activity，建议true。
//                 */
//                .setDisableAutoHideAd(true)
//                .build();
//    }
//
//    private TikTokSplashConfig createTikTokSplashConfig() {
//        return TikTokSplashConfig.Builder()
//                // 具体尺寸，单位 px；默认为屏幕大小
//                // .setImageAcceptedSize(1080, 1920)
//                // 宽度充满屏幕，高度固定
//                // .setImageAcceptedSize(ViewGroup.LayoutParams.MATCH_PARENT, 1800)
//                // 监听应用类广告下载
//                .setAppDownloadListener(new TikTokAppDownloadListener() {
//                    @Override
//                    public void onIdle() {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
//                    }
//
//                    @Override
//                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
//                    }
//
//                    @Override
//                    public void onInstalled(String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
//                    }
//                })
//                .build();
//    }

//    private VivoSplashConfig createVivoSplashConfig() {
//        return VivoSplashConfig.Builder()
//                // 设置标题 id
//                .setTitle(R.string.app_name)
//                // 设置描述 id
//                .setDesc(R.string.app_desc)
//                // 设置标题
//                // .setTitle("App Name")
//                // 设置描述
//                // .setDesc("App Desc")
//                .build();
//    }
}