package com.taurusx.ads.demo.activitys.adapter_ads;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taurusx.ads.core.api.ad.nativead.layout.NativeAdLayout;
import com.taurusx.ads.core.api.ad.nativead.layout.SequenceNativeAdLayoutPolicy;
import com.taurusx.ads.core.api.model.AdType;
import com.taurusx.ads.core.api.stream.AdapterAdLoadedListener;
import com.taurusx.ads.core.api.stream.AdapterAdParams;
import com.taurusx.ads.core.api.stream.TaurusXRecyclerAdapter;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.core.api.utils.ScreenUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;

import java.util.Locale;

public class RecyclerViewActivity extends BaseActivity {

    private final static String TAG = "RecyclerViewActivity";

    private final static String KEY_AD_TYPE = "key_ad_type";
    private final static String KEY_ADUNIT_ID = "key_adunit_id";

    public static void start(Context context, int adType, String adUnitId) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.putExtra(KEY_AD_TYPE, adType);
        intent.putExtra(KEY_ADUNIT_ID, adUnitId);
        context.startActivity(intent);
    }

    private Button mLoadAds;
    private Button mRefreshAds;

    private RecyclerView mRecyclerView;
    private TaurusXRecyclerAdapter mRecyclerAdapter;

    private AdType mAdType;
    private String mAdUnitId;
    private AdapterAdParams mAdParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

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

        mLoadAds = findViewById(R.id.button_load);
        mLoadAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerAdapter.loadAds(mAdParams);
            }
        });

        mRefreshAds = findViewById(R.id.button_refresh);
        mRefreshAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerAdapter.refreshAds(mAdParams);
            }
        });

        mRecyclerView = findViewById(R.id.recycler_view);

        final RecyclerView.Adapter originalAdapter = new DemoRecyclerAdapter();
        mRecyclerAdapter = new TaurusXRecyclerAdapter(this, originalAdapter);

        // 本地 Position 配置
//        ClientPosition position = ClientPosition.newPosition()
//                .addFixedPosition(0, 4, 8)
//                .setRepeatPosition(5);
//        mRecyclerAdapter = new TaurusXRecyclerAdapter(this, originalAdapter, position);

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter.setAdListener(new AdapterAdLoadedListener() {
            @Override
            public void onAdLoaded(int i) {
                LogUtil.d(TAG, "onAdLoaded: " + i);
            }

            @Override
            public void onAdRemoved(int i) {
                LogUtil.d(TAG, "onAdRemoved: " + i);
            }
        });
    }

    private static class DemoRecyclerAdapter extends RecyclerView.Adapter<DemoViewHolder> {
        private static final int ITEM_COUNT = 300;

        @Override
        public DemoViewHolder onCreateViewHolder(final ViewGroup parent,
                                                 final int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new DemoViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DemoViewHolder holder, final int position) {
            if (position % 2 == 1) {
                holder.textView.setBackgroundColor(Color.CYAN);
            } else {
                holder.textView.setBackgroundColor(Color.MAGENTA);
            }
            holder.textView.setText(String.format(Locale.US, "Content Item #%d", position));
        }

        @Override
        public long getItemId(final int position) {
            return (long) position;
        }

        @Override
        public int getItemCount() {
            return ITEM_COUNT;
        }
    }

    /**
     * A view holder for R.layout.simple_list_item_1
     */
    private static class DemoViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public DemoViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            textView.setMinHeight(ScreenUtil.dp2px(itemView.getContext(), 200));
        }
    }
}
