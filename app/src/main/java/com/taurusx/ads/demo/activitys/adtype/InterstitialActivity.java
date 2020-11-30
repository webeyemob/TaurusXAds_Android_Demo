package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.InterstitialAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.AdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;

public class InterstitialActivity extends BaseActivity {

    private final String TAG = "InterstitialActivity";

    private String mInterstitialAdUnitId;
    private InterstitialAd mInterstitialAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_interstitial);

        mInterstitialAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initInterstitialAd();

        mLoadButton = findViewById(R.id.interstitial_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAd.loadAd();
            }
        });

        mShowButton = findViewById(R.id.interstitial_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                if (mInterstitialAd.isReady()) {
                    mInterstitialAd.show(InterstitialActivity.this);
                }
            }
        });
    }

    private void initInterstitialAd() {
        // Create InterstitialAd
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(mInterstitialAdUnitId);

        // Set Video Muted, default is sound
        // mInterstitialAd.setMuted(false);

        // (Optional) Set Network special Config
        mInterstitialAd.setNetworkConfigs(NetworkConfigs.Builder()
                .build());

        // Listen Ad load result
        mInterstitialAd.setADListener(new AdListener() {
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
                Utils.toast(InterstitialActivity.this, adError.toString());
            }
        });
    }
}