package com.taurusx.ads.demo.activitys.adtype;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

import com.taurusx.ads.core.api.ad.SplashAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.SplashAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;

public class SplashActivity extends Activity {

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
            public void onAdLoaded(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + lineItem.getName());
                mExitHandler.removeCallbacksAndMessages(null);
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
            public void onAdSkipped(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdSkipped: " + lineItem.getName());
            }

            @Override
            public void onAdClosed(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdClosed: " + lineItem.getName());
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
}