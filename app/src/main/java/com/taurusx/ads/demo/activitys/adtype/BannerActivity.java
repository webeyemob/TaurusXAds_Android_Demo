package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.taurusx.ads.core.api.ad.BannerAdView;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.AdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;


public class BannerActivity extends BaseActivity {

    private final String TAG = "BannerActivity";

    private String mBannerAdUnitId;
    private BannerAdView mBannerAdView;

    private Button mLoadButton;
    private Button mShowButton;
    private Button mHideButton;

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_banner);

        mBannerAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initBannerAdView();

        mContainer = findViewById(R.id.layout_banner);
        // Add Banner to UI
        mContainer.addView(mBannerAdView);
        mContainer.setVisibility(View.INVISIBLE);

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
                mShowButton.setEnabled(false);
                mHideButton.setEnabled(true);
                mContainer.setVisibility(View.VISIBLE);
            }
        });

        mHideButton = findViewById(R.id.banner_hide);
        mHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(true);
                mHideButton.setEnabled(false);
                mContainer.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initBannerAdView() {
        // Create BannerAdView
        mBannerAdView = new BannerAdView(this);
        mBannerAdView.setAdUnitId(mBannerAdUnitId);

        // (Optional) Set Network special Config
        mBannerAdView.setNetworkConfigs(NetworkConfigs.Builder()
                .build());

        // Listen Ad load result
        mBannerAdView.setADListener(new AdListener() {
            @Override
            public void onAdLoaded(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + iLineItem.getName());
                if (mContainer.getVisibility() == View.INVISIBLE) {
                    mShowButton.setEnabled(true);
                    mHideButton.setEnabled(false);
                }
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
                Utils.toast(BannerActivity.this, adError.toString());
            }
        });
    }
}
