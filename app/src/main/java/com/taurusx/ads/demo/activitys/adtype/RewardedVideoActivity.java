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
import com.taurusx.ads.mediation.networkconfig.QttRewardedVideoConfig;

import org.json.JSONException;
import org.json.JSONObject;


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
            public void onAdLoaded(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + lineItem.getName());
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdShown: " + lineItem.getName());
            }

            @Override
            public void onAdClicked(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdClicked: " + lineItem.getName());
            }

            @Override
            public void onAdClosed(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdClosed: " + lineItem.getName());
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.e(TAG, "onAdFailedToLoad: " + adError);
                Utils.toast(RewardedVideoActivity.this, adError.toString());
            }

            @Override
            public void onVideoStarted(ILineItem lineItem) {
                LogUtil.d(TAG, "onVideoStarted: " + lineItem.getName());
            }

            @Override
            public void onVideoCompleted(ILineItem lineItem) {
                LogUtil.d(TAG, "onVideoCompleted: " + lineItem.getName());
            }

            @Override
            public void onRewarded(ILineItem lineItem, RewardedVideoAd.RewardItem rewardItem) {
                LogUtil.d(TAG, "onRewarded: " + lineItem.getName() + ", rewardItem: " + rewardItem);
            }

            @Override
            public void onRewardFailed(ILineItem lineItem) {
                LogUtil.e(TAG, "onRewardFailed: " + lineItem.getName());
                Utils.toast(RewardedVideoActivity.this, "onRewardFailed");
            }
        });
    }

    private QttRewardedVideoConfig createQttRewardedVideoConfig() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("countdown_award_des", "可领奖励");
            jsonObject.put("close_dialog_title", "奖励提示");
            jsonObject.put("close_dialog_des", "看完视频即可获得奖励");
            jsonObject.put("close_dialog_exit_des", "放弃奖励");
            jsonObject.put("close_dialog_continue_btn_des", "看完视频");
            jsonObject.put("countdown_wait_des", "即将获得奖励");
            jsonObject.put("countdown_success_des", "奖励已发放");
            jsonObject.put("countdown_repeat_des", "不能重复领取奖励");
            jsonObject.put("countdown_fail_des", "领取奖励失败");
            jsonObject.put("countdown_icon_light_url", "http://cdn.aiclk.com/nsdk/res/imgstatic/incite_video_coin_light.png");
            jsonObject.put("countdown_icon_gray_url", "http://cdn.aiclk.com/nsdk/res/imgstatic/incite_video_coin_gray.png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return QttRewardedVideoConfig.Builder()
                // 设置激励视频的文案
                .setDescriptions(jsonObject)
                .build();
    }
}