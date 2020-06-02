package com.taurusx.ads.demo.mixad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.taurusx.ads.core.api.ad.MixViewAd;
import com.taurusx.ads.core.api.ad.config.AdSize;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.BaseActivity;
import com.taurusx.ads.demo.constance.Constance;

public class MixViewActivity extends BaseActivity {

    private final String TAG = "MixViewActivity";

    private String mMixViewId;
    private MixViewAd mMixViewAd;

    private Button mLoadButton;
    private Button mShowButton;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MixViewAd");
        setContentView(R.layout.activity_mixview);

        mMixViewId = getIntent().getStringExtra(Constance.BUNDLE_TYPE_MIXVIEW);
        initMixView();

        mLoadButton = findViewById(R.id.mxiview_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load MixViewAd
                mMixViewAd.loadAd();
            }
        });

        mShowButton = findViewById(R.id.mxiview_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                // Add MixViewAd AdView To UI
                View adView = mMixViewAd.getAdView();
                if (adView != null) {
                    mContainer.removeAllViews();
                    mContainer.addView(adView);
                }
            }
        });

        mContainer = findViewById(R.id.layout_container);
    }

    private void initMixView() {
        // Create MixViewAd
        mMixViewAd = new MixViewAd(this);
        mMixViewAd.setAdUnitId(mMixViewId);

        // Set Custom NativeAdLayout To Render Ad
//        mMixViewAd.setNativeAdLayout(NativeAdLayout.Builder()
//                .setLayoutIdWithDefaultViewId(R.layout.native_custom_layout)
//                .build());

        // Or Set NativeAdLayout Supported By TaurusX To Render Ad
        mMixViewAd.setNativeAdLayout(NativeAdLayout.getLargeLayout1());

        // Or Set NativeAdLayoutPolicy To Render Ad
        // WeSdk Support SequenceNativeAdLayoutPolicy And RandomNativeAdLayoutPolicy
        // You Can Implement Your NativeAdLayoutPolicy
//        mMixViewAd.setNativeAdLayout(SequenceNativeAdLayoutPolicy.Builder()
//                .add(NativeAdLayout.getLargeLayout1())
//                .add(NativeAdLayout.getLargeLayout2())
//                .add(NativeAdLayout.getLargeLayout3())
//                .add(NativeAdLayout.getLargeLayout4())
//                .build());

        // Or Set MultiStyleNativeAdLayout To Render Ad
//        mMixViewAd.setNativeAdLayout(MultiStyleNativeAdLayout.Builder()
//                .setDefaultLayout(NativeAdLayout.getMediumLayout())
//                .setLargeImageLayout(NativeAdLayout.getLargeLayout4())
//                .setGroupImageLayout(NativeAdLayout.getLargeLayout1())
//                .setVideoLayout(NativeAdLayout.getLargeLayout3())
//                .build());

        // Set Express Native Size
        mMixViewAd.setExpressAdSize(new AdSize(360, 250));

        // Set Video Muted, default is muted
        // mMixViewAd.setMuted(false);

        // (Optional) Set Network special Config
        // MixViewAd can set Banner, Native, FeedList Config
        // Please see: BannerActivity, NativeActivity, FeedListActivity
        mMixViewAd.setNetworkConfigs(NetworkConfigs.Builder()
                // .addConfig(Banner NetworkConfig)
                // .addConfig(Native NetworkConfig)
                // .addConfig(FeedList NetworkConfig)
                .build());

        // Set MixView Load Event
        mMixViewAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "MixViewAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "MixViewAd onAdFailedToLoad: " + adError.toString());
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "MixViewAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "MixViewAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "MixViewAd onAdClosed");
            }
        });
    }
}
