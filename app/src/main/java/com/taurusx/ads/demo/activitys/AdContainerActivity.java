package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.demo.mixad.MixFullScreenActivity;
import com.taurusx.ads.demo.mixad.MixViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class AdContainerActivity extends BaseActivity {
    private final String TAG = "BannerActivity";

    private LinearLayout mContainer;
    private String mTitle;
    private String mType;
    private String mInfo;
    private JSONObject mInfoJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        getActionBar().setTitle(mTitle + "_" + mType);
        setContentView(R.layout.activity_container);


        // Init BannerAdView
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(Constance.BUNDLE_TYPE_MEDIATION_NAME);
        mType = intent.getStringExtra(Constance.BUNDLE_TYPE_JSON);
        mInfo = intent.getStringExtra(Constance.BUNDLE_TYPE_INFO);
    }

    private void initView() {
        mContainer = findViewById(R.id.layout_container);
        try {
            mInfoJson = new JSONObject(mInfo);

            Iterator it = mInfoJson.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                addAdView(key);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addAdView(String key) {
        Button button = new Button(AdContainerActivity.this);
        button.setText(key);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType.equals(Constance.BUNDLE_TYPE_BANNER)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, BannerActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_BANNER, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                } else if (mType.equals(Constance.BUNDLE_TYPE_NATIVE)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, NativeActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_NATIVE, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                } else if (mType.equals(Constance.BUNDLE_TYPE_INTERSTITIAL)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, InterstitialActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_INTERSTITIAL, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                } else if (mType.equals(Constance.BUNDLE_TYPE_REWARDED)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, RewardedVideoActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_REWARDED, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                } else if (mType.equals(Constance.BUNDLE_TYPE_MIXVIEW)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, MixViewActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_MIXVIEW, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                } else if (mType.equals(Constance.BUNDLE_TYPE_MIXFULLSCREEN)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, MixFullScreenActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_MIXFULLSCREEN, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                } else if (mType.equals(Constance.BUNDLE_TYPE_FEEDLIST)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, FeedListActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_FEEDLIST, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                } else if (mType.equals(Constance.BUNDLE_TYPE_SPLASH)) {
                    Intent intent = new Intent();
                    intent.setClass(AdContainerActivity.this, SplashActivity.class);
                    intent.putExtra(Constance.BUNDLE_TYPE_SPLASH, mInfoJson.optString(((Button)v).getText().toString()));
                    startActivity(intent);
                }
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mContainer.addView(button, params);
    }
}
