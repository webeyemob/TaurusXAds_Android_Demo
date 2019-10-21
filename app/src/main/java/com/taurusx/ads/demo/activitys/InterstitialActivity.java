package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.InterstitialAd;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;

public class InterstitialActivity extends BaseActivity {

    private final String TAG = "InterstitialActivity";

    private Button mLoadButton;
    private Button mShowButton;

    private InterstitialAd mInterstitialAd;
    private String mInterstitialId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("InterstitialAd");

        setContentView(R.layout.activity_interstitial);
        initData();
        // Init InterstitialAd
        initInterstitialAd();
        // Set NetworkConfigs
    }

    private void initInterstitialAd() {
        // Create InterstitialAd
        mLoadButton = findViewById(R.id.interstitial_load);
        mShowButton = findViewById(R.id.interstitial_show);

        if (mInterstitialId == null || TextUtils.isEmpty(mInterstitialId)) {
            mLoadButton.setVisibility(View.GONE);
            mShowButton.setVisibility(View.GONE);
            return;
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(mInterstitialId);

        // Listen Ad load result
        mInterstitialAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "InterstitialAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "InterstitialAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "InterstitialAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "InterstitialAd onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "InterstitialAd onAdFailedToLoad: " + adError);
            }
        });

        // Load ad
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAd.loadAd();
            }
        });

        // Show ad
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isReady()) {
                    mInterstitialAd.show();
                }
                mShowButton.setEnabled(false);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mInterstitialId = intent.getStringExtra("interstitial");
    }

}
