package com.taurusx.ads.demo.mixad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.config.AdSize;
import com.taurusx.ads.core.api.ad.mixfull.MixFullScreenAd;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.BaseActivity;
import com.taurusx.ads.demo.constance.Constance;

public class MixFullScreenActivity extends BaseActivity {

    private final String TAG = "MixFullScreenActivity";

    private String mMixFulSscreenId;
    private MixFullScreenAd mMixFullScreenAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("MixFullScreenAd");

        setContentView(R.layout.activity_mixfullscreen);

        mMixFulSscreenId = getIntent().getStringExtra(Constance.BUNDLE_TYPE_MIXFULLSCREEN);
        initMixFullScreenAd();

        mLoadButton = findViewById(R.id.mixfullscreen_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMixFullScreenAd.loadAd();
            }
        });

        mShowButton = findViewById(R.id.mixfullscreen_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                if (mMixFullScreenAd.isReady()) {
                    mMixFullScreenAd.show(MixFullScreenActivity.this);
                }
            }
        });
    }

    private void initMixFullScreenAd() {
        // Create MixFullScreenAd
        mMixFullScreenAd = new MixFullScreenAd(this);
        mMixFullScreenAd.setAdUnitId(mMixFulSscreenId);

        // Set Custom NativeAdLayout To Render Ad
//        mMixFullScreenAd.setNativeAdLayout(NativeAdLayout.Builder()
//                .setLayoutIdWithDefaultViewId(R.layout.mixfull_custom_layout)
//                .build());

        // Or Set NativeAdLayout Supported By WeSdk To Render Ad
        mMixFullScreenAd.setNativeAdLayout(NativeAdLayout.getFullLayout1());

        // Or Set NativeAdLayoutPolicy To Render Ad
        // WeSdk Support SequenceNativeAdLayoutPolicy And RandomNativeAdLayoutPolicy
        // You Can Implement Your NativeAdLayoutPolicy
//        mMixFullScreenAd.setNativeAdLayout(SequenceNativeAdLayoutPolicy.Builder()
//                .add(NativeAdLayout.getFullLayout1())
//                .add(NativeAdLayout.getFullLayout2())
//                .add(NativeAdLayout.getFullLayout3())
//                .add(NativeAdLayout.getFullLayout4())
//                .build());

        // Or Set MultiStyleNativeAdLayout To Render Ad
//        mMixFullScreenAd.setNativeAdLayout(MultiStyleNativeAdLayout.Builder()
//                .setDefaultLayout(NativeAdLayout.getFullLayout1())
//                .setLargeImageLayout(NativeAdLayout.getFullLayout2())
//                .setGroupImageLayout(NativeAdLayout.getFullLayout3())
//                .setVideoLayout(NativeAdLayout.getFullLayout4())
//                .build());

        // Set Express Native Size
        mMixFullScreenAd.setExpressAdSize(new AdSize(360, 250));

        // Set Video Muted, default is sound
        // mMixFullScreenAd.setMuted(false);

        // Set whether use can press Android back key to close MixFullScreenAd.
        // This value is only for Banner and Native, InterstitialAd always has self close logic.
        // Default is false.
        mMixFullScreenAd.setBackPressEnable(false);

        // (Optional) Set Network special Config
        // MixFullScreenAd can set Banner, Native, FeedList, Interstitial Config
        // Please see: BannerActivity, NativeActivity, FeedListActivity, InterstitialActivity
        mMixFullScreenAd.setNetworkConfigs(NetworkConfigs.Builder()
                // .addConfig(Banner NetworkConfig)
                // .addConfig(Native NetworkConfig)
                // .addConfig(FeedList NetworkConfig)
                // .addConfig(Interstitial NetworkConfig)
                .build());

        // Set MixFullScreenAd Load Event
        mMixFullScreenAd.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "MixFullScreenAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "MixFullScreenAd onAdFailedToLoad: " + adError.toString());
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "MixFullScreenAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "MixFullScreenAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "MixFullScreenAd onAdClosed");
            }
        });
    }
}
