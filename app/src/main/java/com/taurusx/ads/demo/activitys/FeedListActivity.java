package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qq.e.ads.nativ.ADSize;
import com.taurusx.ads.core.api.ad.feedlist.Feed;
import com.taurusx.ads.core.api.ad.feedlist.FeedList;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleFeedAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.GDTAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.GDTCustom2_0FeedListConfig;
import com.taurusx.ads.mediation.networkconfig.GDTCustomFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.GDTExpressNativeConfig;
import com.taurusx.ads.mediation.networkconfig.KuaiShouAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.KuaiShouFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokCustomFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokDrawFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressFeedListConfig;

import java.util.List;


public class FeedListActivity extends BaseActivity {

    private final String TAG = "FeedListActivity";

    private Button mLoadButton;
    private Button mShowButton;
    private LinearLayout mContainer;

    private String mFeedListId;

    private FeedList mFeedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("FeedList");

        setContentView(R.layout.activity_feedlist);

        initData();
        // Init FeedList
        initFeedList();
        // Set NetworkConfigs
        setNetworkConfigs();
    }

    private void initData() {
        Intent intent = getIntent();
        mFeedListId = intent.getStringExtra(Constance.BUNDLE_TYPE_FEEDLIST);
    }

    private void initFeedList() {
        mContainer = findViewById(R.id.layout_container);
        mLoadButton = findViewById(R.id.feedList_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load FeedList
                mFeedList.loadAd();
            }
        });

        mShowButton = findViewById(R.id.feedList_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowButton.setEnabled(false);
                // Add FeedList AdView To UI
                List<Feed> feedList = mFeedList.getFeedList();
                if (feedList != null && !feedList.isEmpty()) {
                    mContainer.removeAllViews();
                    for (Feed feed : feedList) {
                        View adView = feed.getView();
                        if (adView != null) {
                            mContainer.addView(adView);
                        }
                    }

                }
            }
        });
        mFeedList = new FeedList(this);
        // Set AdUnitId
        mFeedList.setAdUnitId(mFeedListId);

        // Set Ad Count You Want To Load
        mFeedList.setCount(3);

        // Set Custom NativeAdLayout To Render Ad
//        mFeedList.setNativeAdLayout(NativeAdLayout.Builder()
//                .setLayoutIdWithDefaultViewId(R.layout.native_custom_layout)
//                .build());

        // Or Set NativeAdLayout Supported By TaurusX To Render Ad
        mFeedList.setNativeAdLayout(NativeAdLayout.getLargeLayout1());

        // Or Set NativeAdLayoutPolicy To Render Ad
        // WeSdk Support SequenceNativeAdLayoutPolicy And RandomNativeAdLayoutPolicy
        // You Can Implement Your NativeAdLayoutPolicy
//        mFeedList.setNativeAdLayout(SequenceNativeAdLayoutPolicy.Builder()
//                .add(NativeAdLayout.getLargeLayout1())
//                .add(NativeAdLayout.getLargeLayout2())
//                .add(NativeAdLayout.getLargeLayout3())
//                .add(NativeAdLayout.getLargeLayout4())
//                .build());

        // Or Set MultiStyleNativeAdLayout To Render Ad
//        mFeedList.setNativeAdLayout(MultiStyleNativeAdLayout.Builder()
//                .setDefaultLayout(NativeAdLayout.getMediumLayout())
//                .setLargeImageLayout(NativeAdLayout.getLargeLayout4())
//                .setGroupImageLayout(NativeAdLayout.getLargeLayout1())
//                .setVideoLayout(NativeAdLayout.getLargeLayout3())
//                .build());

        // Set FeedList Load Event
        mFeedList.setAdListener(new SimpleFeedAdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "FeedList onAdLoaded");
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                Log.d(TAG, "FeedList onAdFailedToLoad: " + adError.toString());
            }

            @Override
            public void onAdShown(@Nullable Feed feed) {
                Log.d(TAG, "FeedList onAdShown");
            }

            @Override
            public void onAdClicked(@Nullable Feed feed) {
                Log.d(TAG, "FeedList onAdClicked");
            }

            @Override
            public void onAdClosed(@Nullable Feed feed) {
                Log.d(TAG, "FeedList onAdClosed");
            }
        });
    }

    private void setNetworkConfigs() {
        mFeedList.setNetworkConfigs(NetworkConfigs.Builder()
//                .addConfig(TikTokDrawFeedListConfig.Builder()
//                        .setCanInterruptVideoPlay(false)
//                        .setPauseIcon(R.drawable.ic_wesdk_comm_star)
//                        .setPauseIconSize(150)
//                        .build())
//                .addConfig(TikTokExpressFeedListConfig.Builder()
//                        .setExpressViewAcceptedWidth(260)
//                        .setExpressViewAcceptedHeight(0)
//                        .build())
                .addConfig(GDTExpressNativeConfig.Builder()
                        // .setADSize(new ADSize(260, 200))
                        .setADSize(new ADSize(260, 200))
                        .build())
                .addConfig(GDTCustomFeedListConfig.Builder()
                        .setAppDownloadListener(new GDTAppDownloadListener() {
                            @Override
                            public void onIdle(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onIdle: " + appName);
                            }

                            @Override
                            public void onDownloadActive(String appName, int progress) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadActive: "
                                        + appName + ", " + progress + "%");
                            }

                            @Override
                            public void onDownloadPaused(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onInstalled: " + appName);
                            }
                        })
                        .build())
                .addConfig(GDTCustom2_0FeedListConfig.Builder()
                        .setAppDownloadListener(new GDTAppDownloadListener() {
                            @Override
                            public void onIdle(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onIdle: " + appName);
                            }

                            @Override
                            public void onDownloadActive(String appName, int progress) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadActive: "
                                        + appName + ", " + progress + "%");
                            }

                            @Override
                            public void onDownloadPaused(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String appName) {
                                LogUtil.d(TAG, "GDTAppDownloadListener: onInstalled: " + appName);
                            }
                        })
                        .build())
                .addConfig(TikTokDrawFeedListConfig.Builder()
                        .setCanInterruptVideoPlay(true)
//                        .setPauseIcon(R.drawable.ic_wesdk_comm_star)
//                        .setPauseIconSize(150)
                        .setAppDownloadListener(new TikTokAppDownloadListener() {
                            @Override
                            public void onIdle() {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
                            }

                            @Override
                            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
                            }

                            @Override
                            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
                            }
                        })
                        .build())
                .addConfig(TikTokCustomFeedListConfig.Builder()
                        .setAppDownloadListener(new TikTokAppDownloadListener() {
                            @Override
                            public void onIdle() {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
                            }

                            @Override
                            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
                            }

                            @Override
                            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
                            }
                        })
                        .build())
                .addConfig(TikTokExpressFeedListConfig.Builder()
                        .setAppDownloadListener(new TikTokAppDownloadListener() {
                            @Override
                            public void onIdle() {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
                            }

                            @Override
                            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
                            }

                            @Override
                            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
                            }

                            @Override
                            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
                            }

                            @Override
                            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
                            }

                            @Override
                            public void onInstalled(String fileName, String appName) {
                                LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
                            }
                        })
                        .build())
                .addConfig(KuaiShouFeedListConfig.Builder()
                        .setAppDownloadListener(new KuaiShouAppDownloadListener() {
                            @Override
                            public void onIdle() {
                                LogUtil.d(TAG ,"kuaishou onIdle");
                            }

                            @Override
                            public void onProgressUpdate(int i) {
                                LogUtil.d(TAG ,"kuaishou onProgressUpdate");
                            }

                            @Override
                            public void onDownloadFinished() {
                                LogUtil.d(TAG ,"kuaishou onDownloadFinished");
                            }

                            @Override
                            public void onInstalled() {
                                LogUtil.d(TAG ,"kuaishou onInstalled");
                            }
                        })
                    .build())
                .build());
    }
}
