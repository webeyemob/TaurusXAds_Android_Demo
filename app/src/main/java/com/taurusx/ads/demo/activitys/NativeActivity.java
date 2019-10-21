package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.taurusx.ads.core.api.ad.nativead.NativeAd;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;


public class NativeActivity extends BaseActivity {

    private final String TAG = "NativeActivity";

    private Button mLoadButton;
    private Button mShowButton;
    private FrameLayout mContainer;

    private String mNativeId;

    private NativeAd mNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("NativeAd");
        setContentView(R.layout.activity_native);

        initData();
        initNativeAd();
    }

    private void initNativeAd() {
        mContainer = findViewById(R.id.layout_container);
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
                if (adView != null) {
                    mContainer.removeAllViews();
                    mContainer.addView(adView);
                }
            }
        });
        mShowButton = findViewById(R.id.native_show);
        if (mNativeId == null || TextUtils.isEmpty(mNativeId)) {
            mLoadButton.setVisibility(View.GONE);
            mShowButton.setVisibility(View.GONE);
            return;
        }

        // Create NativeAd
        mNativeAd = new NativeAd(this);
        mNativeAd.setAdUnitId(mNativeId);

        // Set Single NativeAdLayout
        mNativeAd.setNativeAdLayout(NativeAdLayout.getLargeLayout1());

        // Listen Ad load result
        mNativeAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "NativeAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "NativeAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "NativeAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "NativeAd onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "NativeAd onAdFailedToLoad: " + adError);
            }
        });


    }



    private void initData() {
        Intent intent = getIntent();
        mNativeId = intent.getStringExtra("native");
    }
}
