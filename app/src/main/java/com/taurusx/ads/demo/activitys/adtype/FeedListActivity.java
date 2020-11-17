package com.taurusx.ads.demo.activitys.adtype;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.baidu.mobad.feeds.ArticleInfo;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.express2.VideoOption2;
import com.taurusx.ads.core.api.ad.config.AdSize;
import com.taurusx.ads.core.api.ad.feedlist.Feed;
import com.taurusx.ads.core.api.ad.feedlist.FeedList;
import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.networkconfig.NetworkConfigs;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.FeedAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.core.api.utils.ScreenUtil;
import com.taurusx.ads.core.api.utils.ViewUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;
import com.taurusx.ads.mediation.networkconfig.BaiduFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.GDTAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.GDTCustom2_0FeedListConfig;
import com.taurusx.ads.mediation.networkconfig.GDTExpress2_0FeedListConfig;
import com.taurusx.ads.mediation.networkconfig.GDTExpressFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.KuaiShouAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.KuaiShouCustomFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokAppDownloadListener;
import com.taurusx.ads.mediation.networkconfig.TikTokCustomFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokDrawFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressDrawFeedListConfig;
import com.taurusx.ads.mediation.networkconfig.TikTokExpressFeedListConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FeedListActivity extends BaseActivity {

    private final String TAG = "FeedListActivity";

    private boolean mIsDrawFeedList;
    private String mFeedListAdUnitId;
    private FeedList mFeedList;

    private Button mLoadButton;
    private Button mShowButton;
    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra(Constant.KEY_TITLE);
        getActionBar().setTitle(title);
        setContentView(R.layout.activity_feedlist);

        mIsDrawFeedList = title.toLowerCase().contains("draw");
        mFeedListAdUnitId = getIntent().getStringExtra(Constant.KEY_ADUNITID);
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
                            View line = new View(FeedListActivity.this);
                            line.setBackgroundColor(Color.parseColor("#BBDEFB"));
                            mContainer.addView(line,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    ScreenUtil.dp2px(FeedListActivity.this, 8));
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
        mFeedList.setAdUnitId(mFeedListAdUnitId);

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
        if (!mIsDrawFeedList) {
            mFeedList.setExpressAdSize(new AdSize(360, 240));
        } else {
            // 9:16
            mFeedList.setExpressAdSize(new AdSize(288, 512));
        }

        // Set Video Muted, default is muted
        // mFeedList.setMuted(false);

        // (Optional) Set Network special Config
        mFeedList.setNetworkConfigs(NetworkConfigs.Builder()
                .addConfig(createBaiduFeedListConfig())
                .addConfig(createGDTCustom2_0FeedListConfig())
                .addConfig(createGDTExpressFeedListConfig())
                .addConfig(createGDTExpress2_0FeedListConfig())
                .addConfig(createKuaiShouCustomFeedListConfig())
                .addConfig(createTikTokCustomFeedListConfig())
                .addConfig(createTikTokExpressFeedListConfig())
                .addConfig(createTikTokDrawFeedListConfig())
                .addConfig(createTikTokExpressDrawFeedListConfig())
                .build());

        // Set FeedList Load Event
        mFeedList.setADListener(new FeedAdListener() {
            @Override
            public void onAdLoaded(ILineItem iLineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + iLineItem.getName());
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown(ILineItem iLineItem, @Nullable Feed feed) {
                LogUtil.d(TAG, "onAdShown: " + iLineItem.getName() + getFeedDesc(feed));
            }

            @Override
            public void onAdClicked(ILineItem iLineItem, @Nullable Feed feed) {
                LogUtil.d(TAG, "onAdClicked: " + iLineItem.getName() + getFeedDesc(feed));
            }

            @Override
            public void onAdClosed(ILineItem iLineItem, @Nullable Feed feed) {
                LogUtil.d(TAG, "onAdClosed: " + iLineItem.getName() + getFeedDesc(feed));
            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtil.e(TAG, "onAdFailedToLoad: " + adError);
                Utils.toast(FeedListActivity.this, adError.toString());
            }

            private String getFeedDesc(Feed feed) {
                return feed != null
                        ? ", Feed Title: " + feed.getFeedData().getTitle() + ", Body: " + feed.getFeedData().getBody()
                        : ", Feed is null";
            }
        });
    }

    private BaiduFeedListConfig createBaiduFeedListConfig() {
        Map<String, String> extra = new HashMap<>();
        // 用户维度：用户性别，取值：0-unknown，1-male，2-female
        extra.put(ArticleInfo.USER_SEX, "1");
        // 用户维度：收藏的小说 ID，最多五个 ID，且不同 ID 用 '/' 分隔
        extra.put(ArticleInfo.FAVORITE_BOOK, "这是小说的名称1/这是小说的名称2/这是小说的名称3");
        // 内容维度：小说、文章的名称
        extra.put(ArticleInfo.PAGE_TITLE, "测试书名");
        // 内容维度：小说、文章的ID
        extra.put(ArticleInfo.PAGE_ID, "1234567");
        // 内容维度：小说分类，一级分类和二级分类用 '/' 分隔
        extra.put(ArticleInfo.CONTENT_CATEGORY, "一级分类/二级分类");
        // 内容维度：小说、文章的标签，最多 10 个，且不同标签用 '/' 分隔
        extra.put(ArticleInfo.CONTENT_LABEL, "标签1/标签2/标签3");

        return BaiduFeedListConfig.Builder()
                .setRequestParametersExtra(extra)
                .build();
    }

    private GDTCustom2_0FeedListConfig createGDTCustom2_0FeedListConfig() {
        return GDTCustom2_0FeedListConfig.Builder()
                // 视频播放配置
                // .setVideoOption(new VideoOption.Builder()
                //         .setXxx(Xxx)
                //         .build())
                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
                // 此设置会影响广告填充，请谨慎设置。
                .setMinVideoDuration(5)
                .setMaxVideoDuration(60)
                // 监听应用类广告下载
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
                .build();
    }

    private GDTExpressFeedListConfig createGDTExpressFeedListConfig() {
        return GDTExpressFeedListConfig.Builder()
                // 视频播放配置
                .setVideoOption(new VideoOption.Builder()
                        // .setXxx(Xxx)
                        .build())
                // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
                // 此设置会影响广告填充，请谨慎设置。
                // .setMinVideoDuration(5)
                // .setMaxVideoDuration(60)
                .build();
    }

    private GDTExpress2_0FeedListConfig createGDTExpress2_0FeedListConfig() {
        return GDTExpress2_0FeedListConfig.Builder()
                // 视频播放配置
                .setVideoOption2(new VideoOption2.Builder()
                        // .setXxx(Xxx)
                        // 设置返回视频广告的视频时长，单位:秒，视频时长有效值范围为[5,60]。
                        // 此设置会影响广告填充，请谨慎设置。
                        // .setMinVideoDuration(5)
                        // .setMaxVideoDuration(60)
                        .build())
                .build();
    }

    private KuaiShouCustomFeedListConfig createKuaiShouCustomFeedListConfig() {
        return KuaiShouCustomFeedListConfig.Builder()
                .setAppDownloadListener(new KuaiShouAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        LogUtil.d(TAG, "KuaiShou onIdle");
                    }

                    @Override
                    public void onProgressUpdate(int i) {
                        LogUtil.d(TAG, "KuaiShou onProgressUpdate: " + i);
                    }

                    @Override
                    public void onDownloadFinished() {
                        LogUtil.d(TAG, "KuaiShou onDownloadFinished");
                    }

                    @Override
                    public void onInstalled() {
                        LogUtil.d(TAG, "KuaiShou onInstalled");
                    }
                })
                .build();
    }

    private TikTokCustomFeedListConfig createTikTokCustomFeedListConfig() {
        return TikTokCustomFeedListConfig.Builder()
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
                .build();
    }

    private TikTokExpressFeedListConfig createTikTokExpressFeedListConfig() {
        return TikTokExpressFeedListConfig.Builder()
                // 点击视频是否可以控制暂停/播放；默认不可控制
                // .setCanInterruptVideoPlay(true)
                // 监听应用类广告下载
                .setAppDownloadListener(new TikTokAppDownloadListener() {
                })
                // 使用默认的 Dislike Dialog 样式，仅需设置 Dislike 回调
                // .setDislikeCallback(new TikTokGetDislikeCallback() {})
                // 或自定义 Dislike Dialog
                // .setDislikeDialog(new TikTokGetDislikeDialog() {})
                .build();
    }

    private TikTokDrawFeedListConfig createTikTokDrawFeedListConfig() {
        return TikTokDrawFeedListConfig.Builder()
                // 点击视频是否可以控制暂停/播放；默认不可控制
                // .setCanInterruptVideoPlay(true)
                // 暂停时显示的图标；默认为 R.drawable.ic_toutiao_pause_icon
                // .setPauseIcon(R.drawable.ic_toutiao_pause_icon)
                // .setPauseIcon(bitmap)
                // .setPauseIcon(drawable)
                // 设置图标大小，单位 dp；默认为 50dp
                .setPauseIconSize(50)
                // 监听应用类广告下载
                .setAppDownloadListener(new TikTokAppDownloadListener() {
                })
                .build();
    }

    private TikTokExpressDrawFeedListConfig createTikTokExpressDrawFeedListConfig() {
        return TikTokExpressDrawFeedListConfig.Builder()
                // 点击视频是否可以控制暂停/播放；默认不可控制
                // .setCanInterruptVideoPlay(true)
                // 监听应用类广告下载
                .setAppDownloadListener(new TikTokAppDownloadListener() {
                })
                .build();
    }
}