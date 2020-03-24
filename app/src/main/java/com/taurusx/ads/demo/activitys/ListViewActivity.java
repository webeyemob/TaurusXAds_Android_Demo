// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.taurusx.ads.demo.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.nativead.layout.SequenceNativeAdLayoutPolicy;
import com.taurusx.ads.core.api.model.AdType;
import com.taurusx.ads.core.api.stream.AdapterAdParams;
import com.taurusx.ads.core.api.stream.ClientPosition;
import com.taurusx.ads.core.api.stream.TaurusXAdAdapter;
import com.taurusx.ads.demo.R;

public class ListViewActivity extends BaseActivity {

    private final static String TAG = "ListViewActivity";

    private final static String KEY_AD_TYPE = "key_ad_type";
    private final static String KEY_ADUNIT_ID = "key_adunit_id";

    public static void start(Context context, int adType, String adUnitId) {
        Intent intent = new Intent(context, ListViewActivity.class);
        intent.putExtra(KEY_AD_TYPE, adType);
        intent.putExtra(KEY_ADUNIT_ID, adUnitId);
        context.startActivity(intent);
    }

    private AdType mAdType;
    private String mAdUnitId;
    private AdapterAdParams mAdParams;

    private TaurusXAdAdapter mAdAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mAdType = AdType.from(getIntent().getIntExtra(KEY_AD_TYPE, AdType.MixView.getType()));
        mAdUnitId = getIntent().getStringExtra(KEY_ADUNIT_ID);
        getActionBar().setTitle(mAdType.getName());

        mAdParams = AdapterAdParams.newBuilder()
                .setAdUnit(mAdType, mAdUnitId)
                .setViewHolderItemLayout(R.layout.adholder_itemview, R.id.ad_container)
                .setNativeAdLayout(SequenceNativeAdLayoutPolicy.Builder()
                        .add(NativeAdLayout.getLargeLayout1())
                        .add(NativeAdLayout.getLargeLayout2())
                        .add(NativeAdLayout.getLargeLayout3())
                        .add(NativeAdLayout.getLargeLayout4())
                        .build())
                .build();

        final ListView listView = findViewById(R.id.list_view);

        findViewById(R.id.button_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdAdapter.loadAds(mAdParams);
            }
        });
        findViewById(R.id.button_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdAdapter.refreshAds(listView, mAdParams);
            }
        });

        final ArrayAdapter<String> originAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (int i = 0; i < 300; ++i) {
            originAdapter.add("Content Item " + i);
        }

        mAdAdapter = new TaurusXAdAdapter(this, originAdapter);

        // 本地 Position 配置
//        ClientPosition position = ClientPosition.newPosition()
//                .addFixedPosition(3, 7, 11)
//                .setRepeatPosition(5);
//        mAdAdapter = new TaurusXAdAdapter(this, originAdapter, position);

        listView.setAdapter(mAdAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdAdapter.destroy();
    }
}
