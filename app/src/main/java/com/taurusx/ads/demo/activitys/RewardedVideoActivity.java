package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.ad.RewardedVideoAd;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleRewardedVideoAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.KuaiShouRewardedVideoConfig;


public class RewardedVideoActivity extends BaseActivity {

    private final String TAG = "RewardedVideoActivity";

    private String mRewardedId;
    private RewardedVideoAd mRewardedVideoAd;

    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("RewardedVideoAd");
        setContentView(R.layout.activity_rewardedvideo);

        mRewardedId = getIntent().getStringExtra(Constance.BUNDLE_TYPE_REWARDED);
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
                if (mRewardedVideoAd.isReady()) {
                    mRewardedVideoAd.show(RewardedVideoActivity.this);
                }
                mShowButton.setEnabled(false);
            }
        });
    }

    private void initRewardedVideoAd() {
        // Create RewardedVideoAd
        mRewardedVideoAd = new RewardedVideoAd(this);
        mRewardedVideoAd.setAdUnitId(mRewardedId);

        // Set Video Muted, default is sound
        // mRewardedVideoAd.setMuted(false);

        // (Optional) Set Network special Config
        mRewardedVideoAd.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(createKuaiShouRewardedVideoConfig())
                .build());

        // Listen Ad load result
        mRewardedVideoAd.setAdListener(new SimpleRewardedVideoAdListener() {
            @Override
            public void onAdLoaded() {
                LogUtil.d(TAG, "RewardedVideoAd onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown() {
                LogUtil.d(TAG, "RewardedVideoAd onAdShown");
            }

            @Override
            public void onAdClicked() {
                LogUtil.d(TAG, "RewardedVideoAd onAdClicked");
            }

            @Override
            public void onAdClosed() {
                LogUtil.d(TAG, "RewardedVideoAd onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.d(TAG, "RewardedVideoAd onAdFailedToLoad: " + adError);
            }

            @Override
            public void onVideoStarted() {
                LogUtil.d(TAG, "RewardedVideoAd onVideoStarted");
            }

            @Override
            public void onVideoCompleted() {
                LogUtil.d(TAG, "RewardedVideoAd onVideoCompleted");
            }

            @Override
            public void onRewarded(RewardedVideoAd.RewardItem rewardItem) {
                LogUtil.d(TAG, "RewardedVideoAd onRewarded: " + rewardItem);
            }

            @Override
            public void onRewardFailed() {
                LogUtil.d(TAG, "RewardedVideoAd onRewardFailed");
            }
        });
    }

    private KuaiShouRewardedVideoConfig createKuaiShouRewardedVideoConfig() {
        return KuaiShouRewardedVideoConfig.Builder()
                // 拓展场景参数，可不设置
                .setShowScene("xx game")
                // 30 秒后可关闭
                .setSkipThirtySecond(false)
                .build();
    }
}