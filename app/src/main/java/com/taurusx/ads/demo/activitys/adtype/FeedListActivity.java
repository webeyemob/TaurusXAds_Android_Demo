package com.taurusx.ads.demo.activitys.adtype;

import android.graphics.Color;
import android.os.Bundle;
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
import com.taurusx.ads.core.api.listener.newapi.FeedAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.core.api.utils.ScreenUtil;
import com.taurusx.ads.core.api.utils.ViewUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;

import java.util.List;


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
                .build());

        // Set FeedList Load Event
        mFeedList.setADListener(new FeedAdListener() {
            @Override
            public void onAdLoaded(ILineItem lineItem) {
                LogUtil.d(TAG, "onAdLoaded: " + lineItem.getName());
                mShowButton.setEnabled(true);
            }

            @Override
            public void onAdShown(ILineItem lineItem, @Nullable Feed feed) {
                LogUtil.d(TAG, "onAdShown: " + lineItem.getName() + getFeedDesc(feed));
            }

            @Override
            public void onAdClicked(ILineItem lineItem, @Nullable Feed feed) {
                LogUtil.d(TAG, "onAdClicked: " + lineItem.getName() + getFeedDesc(feed));
            }

            @Override
            public void onAdClosed(ILineItem lineItem, @Nullable Feed feed) {
                LogUtil.d(TAG, "onAdClosed: " + lineItem.getName() + getFeedDesc(feed));
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
}