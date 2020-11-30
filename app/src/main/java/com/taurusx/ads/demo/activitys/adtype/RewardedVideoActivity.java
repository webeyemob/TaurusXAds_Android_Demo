package com.taurusx.ads.demo.activitys.adtype;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.RewardedVideoAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.RewardedVideoAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;


public class RewardedVideoActivity extends BaseActivity {

    private final String TAG = "RewardedVideoActivity";

    private String mRewardedVideoAdUnitId;
    private RewardedVideoAd mRewardedVideoAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));
        setContentView(R.layout.activity_rewardedvideo);

        mRewardedVideoAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
        initRewardedVideoAd();

        mLoadButton = findViewById(R.id.rewardedvideo_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRewardedVideoAd.loadAd();
            }
        });

        mShowButton = findViewById(R.id.rewardedvideo_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                if (mRewardedVideoAd.isReady()) {
                    mRewardedVideoAd.show(RewardedVideoActivity.this);
                }
            }
        });
    }

    private void initRewardedVideoAd() {
        // Create RewardedVideoAd
        mRewardedVideoAd = new RewardedVideoAd(this);
        mRewardedVideoAd.setAdUnitId(mRewardedVideoAdUnitId);

        // Set Video Muted, default is sound
        // mRewardedVideoAd.setMuted(false);

        // (Optional) Set Network special Config
        mRewardedVideoAd.setNetworkConfigs(NetworkConfigs.Builder()
                .build());

        // Listen Ad load result
        mRewardedVideoAd.setADListener(new RewardedVideoAdListener() {
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
                Utils.toast(RewardedVideoActivity.this, adError.toString());
            }

            @Override
            public void onVideoStarted(ILineItem iLineItem) {
                LogUtil.d(TAG, "onVideoStarted: " + iLineItem.getName());
            }

            @Override
            public void onVideoCompleted(ILineItem iLineItem) {
                LogUtil.d(TAG, "onVideoCompleted: " + iLineItem.getName());
            }

            @Override
            public void onRewarded(ILineItem iLineItem, RewardedVideoAd.RewardItem rewardItem) {
                LogUtil.d(TAG, "onRewarded: " + iLineItem.getName() + ", rewardItem: " + rewardItem);
            }

            @Override
            public void onRewardFailed(ILineItem iLineItem) {
                LogUtil.e(TAG, "onRewardFailed: " + iLineItem.getName());
                Utils.toast(RewardedVideoActivity.this, "onRewardFailed");
            }
        });
    }
}