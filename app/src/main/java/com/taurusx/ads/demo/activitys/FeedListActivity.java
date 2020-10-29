package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.taurusx.ads.core.api.ad.config.AdSize;
import com.taurusx.ads.core.api.ad.feedlist.Feed;
import com.taurusx.ads.core.api.ad.feedlist.FeedList;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.SimpleFeedAdListener;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.core.api.utils.ViewUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.mediation.networkconfig.KuaiShouAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.KuaiShouCustomFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.MintegralFeedListConfig;

import java.util.List;


public class FeedListActivity extends BaseActivity {

    private final String TAG = "FeedListActivity";

    private String mFeedListId;
    private FeedList mFeedList;

    private Button mLoadButton;
    private Button mShowButton;
    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("FeedList");
        setContentView(R.layout.activity_feedlist);

        mFeedListId = getIntent().getStringExtra(Constance.BUNDLE_TYPE_FEEDLIST);
        initFeedList();

        mLoadButton = findViewById(R.id.feedList_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            ViewUtil.removeFromParent(adView);
                            mContainer.addView(adView);
                        }
                    }
                }
            }
        });

        mContainer = findViewById(R.id.layout_container);
    }

    private void initFeedList() {
        // Create FeedList
        mFeedList = new FeedList(this);
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

        // Set Express FeedList Size
        mFeedList.setExpressAdSize(new AdSize(360, 240));

        // Set Video Muted, default is muted
        // mFeedList.setMuted(false);

        // (Optional) Set Network special Config
        mFeedList.setNetworkConfigs(NetworkConfigs.Builder()
//                .addConfig(createGDTCustom2_0FeedListConfig())
//                .addConfig(createGDTExpressFeedListConfig())
                .addConfig(createKuaiShouCustomFeedListConfig())
                .addConfig(createMintegralFeedListConfig())
//                .addConfig(createTikTokCustomFeedListConfig())
//                .addConfig(createTikTokExpressFeedListConfig())
//                .addConfig(createTikTokDrawFeedListConfig())
//                .addConfig(createTikTokExpressDrawFeedListConfig())
                .build());

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

//    private GDTCustom2_0FeedListConfig createGDTCustom2_0FeedListConfig() {
//        return GDTCustom2_0FeedListConfig.Builder()
//                // 视频播放配置
//                .setVideoOption(new VideoOption.Builder()
//                        // .setXxx(Xxx)
//                        .build())
//                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
//                // 此设置会影响广告填充，请谨慎设置。
//                .setMinVideoDuration(5)
//                .setMaxVideoDuration(60)
//                // 监听应用类广告下载
//                .setAppDownloadListener(new GDTAppDownloadListener() {
//                    @Override
//                    public void onIdle(String appName) {
//                        LogUtil.d(TAG, "GDTAppDownloadListener: onIdle: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadActive(String appName, int progress) {
//                        LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadActive: "
//                                + appName + ", " + progress + "%");
//                    }
//
//                    @Override
//                    public void onDownloadPaused(String appName) {
//                        LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadPaused: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadFailed(String appName) {
//                        LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFailed: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadFinished(String appName) {
//                        LogUtil.d(TAG, "GDTAppDownloadListener: onDownloadFinished: " + appName);
//                    }
//
//                    @Override
//                    public void onInstalled(String appName) {
//                        LogUtil.d(TAG, "GDTAppDownloadListener: onInstalled: " + appName);
//                    }
//                })
//                .build();
//    }
//
//    private GDTExpressFeedListConfig createGDTExpressFeedListConfig() {
//        return GDTExpressFeedListConfig.Builder()
//                // 视频播放配置
//                .setVideoOption(new VideoOption.Builder()
//                        // .setXxx(Xxx)
//                        .build())
//                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
//                // 此设置会影响广告填充，请谨慎设置。
//                // .setMinVideoDuration(5)
//                // .setMaxVideoDuration(60)
//                .build();
//    }

    private KuaiShouCustomFeedListConfig createKuaiShouCustomFeedListConfig() {
        return KuaiShouCustomFeedListConfig.Builder()
                .setAppDownloadListener(new KuaiShouAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        LogUtil.d(TAG ,"KuaiShou onIdle");
                    }

                    @Override
                    public void onProgressUpdate(int i) {
                        LogUtil.d(TAG ,"KuaiShou onProgressUpdate: " + i);
                    }

                    @Override
                    public void onDownloadFinished() {
                        LogUtil.d(TAG ,"KuaiShou onDownloadFinished");
                    }

                    @Override
                    public void onInstalled() {
                        LogUtil.d(TAG ,"KuaiShou onInstalled");
                    }
                })
                .build();
    }

    private MintegralFeedListConfig createMintegralFeedListConfig() {
        return MintegralFeedListConfig.Builder()
                // 是否循环播放视频
                .setAllowLoopPlay(false)
                // 屏幕方向改变时是否重新加载视频
                .setAllowScreenChange(false)
                // 在视频未准备好播放之前是否显示图片
                .setAllowVideoRefresh(false)
                // 是否显示进度
                .setProgressVisibility(true)
                // 是否允许全屏显示
                .setIsAllowFullScreen(false)
                // 是否显示音量按钮
                .setSoundIndicatorVisibility(true)
                .build();
    }

//    private TikTokCustomFeedListConfig createTikTokCustomFeedListConfig() {
//        return TikTokCustomFeedListConfig.Builder()
//                .setAppDownloadListener(new TikTokAppDownloadListener() {
//                    @Override
//                    public void onIdle() {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onIdle");
//                    }
//
//                    @Override
//                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadActive: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadPaused: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFailed: " + appName);
//                    }
//
//                    @Override
//                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onDownloadFinished: " + appName);
//                    }
//
//                    @Override
//                    public void onInstalled(String fileName, String appName) {
//                        LogUtil.d(TAG, "TikTokAppDownloadListener: onInstalled");
//                    }
//                })
//                .build();
//    }
//
//    private TikTokExpressFeedListConfig createTikTokExpressFeedListConfig() {
//        return TikTokExpressFeedListConfig.Builder()
//                // 监听应用类广告下载
//                .setAppDownloadListener(new TikTokAppDownloadListener() {})
//                // 使用默认的 Dislike Dialog 样式，仅需设置 Dislike 回调
//                // .setDislikeCallback(new TikTokGetDislikeCallback() {})
//                // 或自定义 Dislike Dialog
//                // .setDislikeDialog(new TikTokGetDislikeDialog() {})
//                .build();
//    }
//
//    private TikTokDrawFeedListConfig createTikTokDrawFeedListConfig() {
//        return TikTokDrawFeedListConfig.Builder()
//                // 点击视频是否可以控制暂停/播放；默认不可控制
//                .setCanInterruptVideoPlay(true)
//                // 暂停时显示的图标；默认为 R.drawable.ic_toutiao_pause_icon
//                // .setPauseIcon(R.drawable.ic_toutiao_pause_icon)
//                // .setPauseIcon(bitmap)
//                // .setPauseIcon(drawable)
//                // 设置图标大小，单位 dp；默认为 50dp
//                .setPauseIconSize(50)
//                // 监听应用类广告下载
//                .setAppDownloadListener(new TikTokAppDownloadListener() {})
//                .build();
//    }
//
//    private TikTokExpressDrawFeedListConfig createTikTokExpressDrawFeedListConfig() {
//        return TikTokExpressDrawFeedListConfig.Builder()
//                // 点击视频是否可以控制暂停/播放；默认不可控制
//                .setCanInterruptVideoPlay(true)
//                // 监听应用类广告下载
//                .setAppDownloadListener(new TikTokAppDownloadListener() {})
//                .build();
//    }
}