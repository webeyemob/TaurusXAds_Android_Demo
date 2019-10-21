package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.taurusx.ads.core.api.ad.BannerAdView;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleAdListener;
import com.taurusx.ads.demo.R;


public class BannerActivity extends BaseActivity {

    private final String TAG = "BannerActivity";

    private Button mLoadButton;
    private FrameLayout mContainer;
    private String mBannerId;

    private BannerAdView mBannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("BannerAdView");
        setContentView(R.layout.activity_banner);

        initData();
        // Init BannerAdView
        initBannerAdView();

    }

    private void initData() {
        Intent intent = getIntent();
        mBannerId = intent.getStringExtra("banner");
    }

    private void initBannerAdView() {
        mContainer = findViewById(R.id.layout_banner);
        mLoadButton = findViewById(R.id.banner_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load BannerAdView
                mBannerAdView.loadAd();
            }
        });

        if (mBannerId == null || TextUtils.isEmpty(mBannerId)) {
            mLoadButton.setVisibility(View.GONE);
            Toast.makeText(BannerActivity.this, "bannerId is null", Toast.LENGTH_SHORT).show();
            return;
        }

        mBannerAdView = new BannerAdView(this);
        mBannerAdView.setAdUnitId(mBannerId);

        // Listen Ad load result
        mBannerAdView.setAdListener(new SimpleAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "BannerAdView onAdLoaded");
                mContainer.removeAllViews();
                mContainer.addView(mBannerAdView);
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
