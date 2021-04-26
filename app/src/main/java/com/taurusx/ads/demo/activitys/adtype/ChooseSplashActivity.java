package com.taurusx.ads.demo.activitys.adtype;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.taurusx.ads.core.api.ad.SplashAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.SplashAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;

public class ChooseSplashActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "ChooseSplashActivity";

    private String mSplashAdUnitId;
    private SplashAd mSplashAd;

    private Button mLoadAd;
    private Button mLoadAdOnly;
    private Button mShowAd;
    private FrameLayout mContainer;

    private boolean mIsShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_splash);
        initView();
        mSplashAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initSplashAd();
    }

    private void initSplashAd() {
        // Create SplashAd
        mSplashAd = new SplashAd(this);
        mSplashAd.setAdUnitId(mSplashAdUnitId);

        // (Optional) Set Network special Config
        mSplashAd.setNetworkConfigs(NetworkConfigs.Builder()
                .build());

        mSplashAd.setContainer(mContainer);
        // Bottom area settings (For: OPPO, Sigmob and vivo)
        // Set the title & description displayed at the bottom of the splash ad
        mSplashAd.setBottomText("TaurusX Ads", "Demo for TaurusX Ads Sdk");
        // Or set the view displayed at the bottom of splash ad
        // View bottomArea = LayoutInflater.from(this).inflate(R.layout.layout_splash_bottom_area, null);
        // mSplashAd.setBottomView(bottomArea);

        // Set SplashAd Load Event
        mSplashAd.setADListener(new SplashAdListener() {
            @Override
            public void onAdLoaded(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + iLineItem.getName());
                mShowAd.setEnabled(true);
            }

            @Override
            public void onAdShown(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdShown: " + iLineItem.getName());
                mIsShowing = true;
            }

            @Override
            public void onAdClicked(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdClicked: " + iLineItem.getName());
            }

            @Override
            public void onAdSkipped(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdSkipped: " + iLineItem.getName());
            }

            @Override
            public void onAdClosed(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdClosed: " + iLineItem.getName());
                mIsShowing = false;
                getActionBar().show();
                mContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.e(TAG, "onAdFailedToLoad: " + adError);
                Utils.toast(ChooseSplashActivity.this, adError.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mIsShowing) {
            super.onBackPressed();
        }
    }

    private void initView() {
        mContainer = findViewById(R.id.splash_container);
        mLoadAd = findViewById(R.id.splash_load);
        mLoadAdOnly = findViewById(R.id.splash_load_only);
        mShowAd = findViewById(R.id.splash_show_ad);
        mLoadAd.setOnClickListener(this);
        mLoadAdOnly.setOnClickListener(this);
        mShowAd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_load:
                startActivity(new Intent(ChooseSplashActivity.this, SplashActivity.class)
                        .putExtra(Constant.KEY_ADUNITID, mSplashAdUnitId));
                break;
            case R.id.splash_load_only:
                mSplashAd.loadAdOnly();
                break;
            case R.id.splash_show_ad:
                mShowAd.setEnabled(false);
                if (mSplashAd.isReady()) {
                    mContainer.setVisibility(View.VISIBLE);
                    getActionBar().hide();
                    mSplashAd.showAd();
                }
                break;
            default:
                break;
        }
    }
}