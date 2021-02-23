package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.taurusx.ads.core.api.ad.config.AdSize;
import com.taurusx.ads.core.api.ad.nativead.NativeAd;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;


public class NativeActivity extends BaseActivity {

    private final String TAG = "NativeActivity";

    private String mNativeId;
    private NativeAd mNativeAd;

    private Button mLoadButton;
    private Button mShowButton;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("NativeAd");
        setContentView(R.layout.activity_native);

        mNativeId = getIntent().getStringExtra("native");
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
        mNativeAd.setAdUnitId(mNativeId);

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
        mNativeAd.setExpressAdSize(new AdSize(300, 100));

        // Set Video Muted, default is muted
        // mNativeAd.setMuted(false);

        // (Optional) Set Network special Config
        mNativeAd.setNetworkConfigs(NetworkConfigs.Builder()
                .build());

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
}