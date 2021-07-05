package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.taurusx.ads.core.api.ad.BannerAdView;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;

public class BannerActivity extends BaseActivity {

    private final String TAG = "BannerActivity";

    private String mBannerId;
    private BannerAdView mBannerAdView;

    private Button mLoadButton;
    private Button mShowButton;
    private Button mHideButton;

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("BannerAdView");
        setContentView(R.layout.activity_banner);

        mBannerId = getIntent().getStringExtra(Constance.BUNDLE_TYPE_BANNER);
        initBannerAdView();

        mContainer = findViewById(R.id.layout_banner);
        // Add Banner to UI
        mContainer.setVisibility(View.GONE);
        mContainer.addView(mBannerAdView);

        mLoadButton = findViewById(R.id.banner_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBannerAdView.loadAd();
            }
        });

        mShowButton = findViewById(R.id.banner_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainer.setVisibility(View.VISIBLE);
            }
        });
        mHideButton = findViewById(R.id.banner_hide);
        mHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainer.setVisibility(View.GONE);
            }
        });
    }

    private void initBannerAdView() {
        // Create BannerAdView
        mBannerAdView = new BannerAdView(this);
        mBannerAdView.setAdUnitId(mBannerId);

        // (Optional) Set Network special Config
        mBannerAdView.setNetworkConfigs(NetworkConfigs.Builder()
                .build());

        // Listen Ad load result
        mBannerAdView.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "BannerAdView onAdLoaded");
                ILineItem lineItem = mBannerAdView.getReadyLineItem();
                Log.d(TAG, "BannerAdView Ready LineItem: " + lineItem);
            }

            @Override
            public void onAdShown() {
                Log.d(TAG, "BannerAdView onAdShown");
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "BannerAdView onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "BannerAdView onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "BannerAdView onAdFailedToLoad: " + adError.toString());
            }
        });
    }
}
