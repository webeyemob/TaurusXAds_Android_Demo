package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.taurusx.ads.core.api.ad.SplashAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;

public class SplashActivity extends FragmentActivity {

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
        }, 30000);
    }

    private void initSplash() {
        // Create SplashAd
        mSplashAd = new SplashAd(this);
        mSplashAd.setAdUnitId(mSplashId);

        // Set container to show SplashAd
        mSplashAd.setContainer(mContainer);

        // (Optional) Set Network special Config
        mSplashAd.setNetworkConfigs(NetworkConfigs.Builder()
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
}