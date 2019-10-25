package com.taurusx.ads.demo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import com.taurusx.ads.core.api.ad.SplashAd;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;

public class SplashActivity extends Activity {

    private final String TAG = "SplashActivity";

    private FrameLayout mContainer;
    private Handler mExitHandler;

    private SplashAd mSplashAd;
    private String mSplashId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initData();
        initSplash();
    }

    private void initData() {
        Intent intent = getIntent();
        mSplashId = intent.getStringExtra(Constance.BUNDLE_TYPE_SPLASH);
    }

    private void initSplash() {
        mContainer = findViewById(R.id.layout_container);
        // Exit Page When SplashAd Load More Than 3000ms
        mExitHandler = new Handler();
        mExitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
        mSplashAd = new SplashAd(this);
        // Set AdUnitId
        mSplashAd.setAdUnitId(mSplashId);
        mSplashAd.setContainer(mContainer);
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
                // Exit Page When SplashAd Closed
                finish();
            }
        });
        // Load SplashAd
        mSplashAd.loadAd();
    }

}
