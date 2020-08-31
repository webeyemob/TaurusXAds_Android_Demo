package com.taurusx.ads.demo.activitys.adtype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.taurusx.ads.core.api.model.AdType;
import com.taurusx.ads.core.api.utils.ScreenUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class AdTypeActivity extends BaseActivity {

    private final String TAG = "AdTypeActivity";

    private static final String EXTRA_KEY_MEDIATION = "extra_mediation";
    private static final String EXTRA_KEY_IS_AUTO_LOAD = "extra_is_auto_load";

    public static void start(Context context, @Nullable JSONObject mediation) {
        start(context, mediation, false);
    }

    public static void start(Context context, @Nullable JSONObject mediation, boolean isAutoLoad) {
        if (mediation == null) {
            return;
        }
        Intent intent = new Intent(context, AdTypeActivity.class);
        intent.putExtra(EXTRA_KEY_MEDIATION, mediation.toString());
        intent.putExtra(EXTRA_KEY_IS_AUTO_LOAD, isAutoLoad);
        context.startActivity(intent);
    }

    private String mTitle;

    private JSONObject mMediation;
    private boolean mIsAutoLoad;

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adtype);

        try {
            mMediation = new JSONObject(getIntent().getStringExtra(EXTRA_KEY_MEDIATION));
            mIsAutoLoad = getIntent().getBooleanExtra(EXTRA_KEY_IS_AUTO_LOAD, false);
            mTitle = mMediation.optString(Constant.JSON_KEY_ADNAME);
        } catch (JSONException e) {
            e.printStackTrace();
            finish();
        }

        getActionBar().setTitle(mTitle);

        mContainer = findViewById(R.id.layout_container);

        addButtons();
    }

    private void addButtons() {
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_BANNER), AdType.Banner);
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_INTERSTITIAL), AdType.Interstitial);
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_REWARDED), AdType.RewardedVideo);
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_SPLASH), AdType.Splash);
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_FEEDLIST), AdType.FeedList);
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_MIXVIEW), AdType.MixView);
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_MIXFULLSCREEN), AdType.MixFullScreen);
        addButton(mMediation.optJSONObject(Constant.JSON_KEY_NATIVE), AdType.Native);
    }

    private void addButton(JSONObject object, AdType adType) {
        if (object != null) {
            // Add Group Title
            TextView title = new TextView(this);
            if (adType == AdType.Native) {
                title.setText(adType.getName().concat(" - [Deprecated, Use FeedList]"));
                title.setBackgroundColor(Color.parseColor("#aaaaaa"));
            } else {
                title.setText(adType.getName());
                if (adType == AdType.MixView || adType == AdType.MixFullScreen) {
                    title.setBackgroundColor(Color.parseColor("#FFCC80"));
                } else {
                    title.setBackgroundColor(Color.parseColor("#BBDEFB"));
                }
            }
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            title.setGravity(Gravity.CENTER_VERTICAL);
            title.setPadding(ScreenUtil.dp2px(this, 12),
                    ScreenUtil.dp2px(this, 7),
                    0,
                    ScreenUtil.dp2px(this, 7));
            mContainer.addView(title);

            // Add AdType
            Iterator<String> iterator = object.keys();
            while (iterator.hasNext()) {
                String name = iterator.next();
                String adUnitId = object.optString(name);

                // add AdType
                TextView adTypeTextView = new TextView(this);
                if (adType == AdType.MixView || adType == AdType.MixFullScreen) {
                    adTypeTextView.setBackgroundResource(R.drawable.mix_press_bg);
                } else {
                    adTypeTextView.setBackgroundResource(R.drawable.press_bg);
                }
                adTypeTextView.setText(name);
                adTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                adTypeTextView.setGravity(Gravity.CENTER);
                adTypeTextView.setPadding(0, ScreenUtil.dp2px(this, 14),
                        0, ScreenUtil.dp2px(this, 14));

                setClickListener(adTypeTextView, adType, adUnitId);

                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                mContainer.addView(adTypeTextView, buttonParams);

                // add line
                View line = new View(this);
                line.setBackgroundColor(Color.parseColor("#cccccc"));
                line.setMinimumHeight(1);
                mContainer.addView(line);
            }
        }
    }

    private void setClickListener(TextView textView, AdType adType, String adUnitId) {
        String title = mTitle.concat(" - ").concat(textView.getText().toString());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (adType) {
                    case Banner:
                        startActivity(new Intent(AdTypeActivity.this, BannerActivity.class)
                                .putExtra(Constant.KEY_TITLE, title)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId));
                        break;
                    case Interstitial:
                        startActivity(new Intent(AdTypeActivity.this, InterstitialActivity.class)
                                .putExtra(Constant.KEY_TITLE, title)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId)
                                .putExtra(Constant.KEY_IS_AUTO_LOAD, mIsAutoLoad));
                        break;
                    case RewardedVideo:
                        startActivity(new Intent(AdTypeActivity.this, RewardedVideoActivity.class)
                                .putExtra(Constant.KEY_TITLE, title)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId)
                                .putExtra(Constant.KEY_IS_AUTO_LOAD, mIsAutoLoad));
                        break;
                    case Splash:
                        startActivity(new Intent(AdTypeActivity.this, SplashActivity.class)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId));
                        break;
                    case FeedList:
                        startActivity(new Intent(AdTypeActivity.this, FeedListActivity.class)
                                .putExtra(Constant.KEY_TITLE, title)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId));
                        break;
                    case MixView:
                        startActivity(new Intent(AdTypeActivity.this, MixViewActivity.class)
                                .putExtra(Constant.KEY_TITLE, title)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId)
                                .putExtra(Constant.KEY_IS_AUTO_LOAD, mIsAutoLoad));
                        break;
                    case MixFullScreen:
                        startActivity(new Intent(AdTypeActivity.this, MixFullScreenActivity.class)
                                .putExtra(Constant.KEY_TITLE, title)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId));
                        break;
                    case Native:
                        startActivity(new Intent(AdTypeActivity.this, NativeActivity.class)
                                .putExtra(Constant.KEY_TITLE, title)
                                .putExtra(Constant.KEY_ADUNITID, adUnitId));
                        break;
                }
            }
        });
    }
}