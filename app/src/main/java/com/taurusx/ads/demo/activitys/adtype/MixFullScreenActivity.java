package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.config.AdSize;
import com.taurusx.ads.core.api.ad.mixfull.MixFullScreenAd;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.AdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;

public class MixFullScreenActivity extends BaseActivity {

    private final String TAG = "MixFullScreenActivity";

    private String mMixFulScreenAdUnitId;
    private MixFullScreenAd mMixFullScreenAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_mixfullscreen);

        mMixFulScreenAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
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
        mMixFullScreenAd.setAdUnitId(mMixFulScreenAdUnitId);

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
        mMixFullScreenAd.setADListener(new AdListener() {
            @Override
            public void onAdLoaded(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + iLineItem.getName());
                mShowButton.setEnabled(true);
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
            public void onAdClosed(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdClosed: " + iLineItem.getName());
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.e(TAG, "onAdFailedToLoad: " + adError);
                Utils.toast(MixFullScreenActivity.this, adError.toString());
            }
        });
    }
}
