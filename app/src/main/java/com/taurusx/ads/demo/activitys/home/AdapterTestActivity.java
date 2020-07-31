package com.taurusx.ads.demo.activitys.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.taurusx.ads.core.api.model.AdType;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.activitys.adapter_ads.ListViewActivity;
import com.taurusx.ads.demo.activitys.adapter_ads.RecyclerViewActivity;

public class AdapterTestActivity extends BaseActivity {

    private static final String KEY_IS_LISTVIEW = "key_is_listview";

    public static void start(Context context, boolean isListView) {
        Intent intent = new Intent(context, AdapterTestActivity.class);
        intent.putExtra(KEY_IS_LISTVIEW, isListView);
        context.startActivity(intent);
    }

    private boolean mIsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_test);

        mIsListView = getIntent().getBooleanExtra(KEY_IS_LISTVIEW, true);
        getActionBar().setTitle(mIsListView ? "ListAdapter Test" : "RecyclerAdapter Test");

        findViewById(R.id.banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AdType.Banner, "738a93fd-aa91-4b0a-bb02-9a7f6ecc8852");
            }
        });
        findViewById(R.id.banner_merc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AdType.Banner, "95e5999f-4754-41f0-9693-b8256c1ba90d");
            }
        });
        findViewById(R.id.nativead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AdType.Native, "d4eb178b-9927-48eb-829e-e48807d964fd");
            }
        });
        findViewById(R.id.feedlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AdType.FeedList, "ed57c09d-777b-4b15-b70f-7af33c45437c");
            }
        });
        findViewById(R.id.mixivew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AdType.MixView, "f0115fd8-1dc3-4264-b065-5758f031e97c");
            }
        });
    }

    private void start(AdType adType, String adUnitId) {
        if (mIsListView) {
            ListViewActivity.start(this, adType.getType(), adUnitId);
        } else {
            RecyclerViewActivity.start(this, adType.getType(), adUnitId);
        }
    }
}
