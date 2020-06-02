package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.bean.Mediation;
import com.taurusx.ads.demo.constance.Constance;
import com.taurusx.ads.demo.utils.Utils;

import java.util.Map;


public class LoadModeActivity extends BaseActivity {

    private final static String TAG = "LoadModeActivity";

    private final static String MODE_SERIAL = "Serial";
    private final static String MODE_PARALLEL = "Parallel";
    private final static String MODE_SHUFFLE = "Shuffle";
    // serial
    private Button mSerialButton;
    // parallel
    private Button mParallelButton;
    // shuffle
    private Button mShuffleButton;

    private Mediation mSerialMediation;
    private Mediation mParallelMediaiton;
    private Mediation mShuffleMediaiton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);
        getSupportActionBar().setTitle("Strategy Test");
        initData();
        initView();
    }

    private void initData() {
        String json = Utils.getAssetsContent(LoadModeActivity.this, "load_mode_ad.json");
        Map<String, Mediation> map = Utils.getMediationMap(json);
        mSerialMediation = map.get(MODE_SERIAL);
        mParallelMediaiton = map.get(MODE_PARALLEL);
        mShuffleMediaiton = map.get(MODE_SHUFFLE);
    }

    private void initView() {
        mSerialButton = findViewById(R.id.mode_serial);
        mSerialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getSerialIntent());
            }
        });

        mParallelButton = findViewById(R.id.mode_parallel);
        mParallelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getParallelIntent());
            }
        });

        mShuffleButton = findViewById(R.id.mode_shuffle);
        mShuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getShuffleIntent());
            }
        });
    }

    private Intent getSerialIntent() {

        Intent intent = Utils.getBaseIntent(mShuffleMediaiton);
        intent.setClass(LoadModeActivity.this, MediationActivity.class);
        intent.putExtra(Constance.BUNDLE_NETWORK_NAME, MODE_SERIAL);
        return intent;
    }

    private Intent getParallelIntent() {
        Intent intent = Utils.getBaseIntent(mParallelMediaiton);
        intent.setClass(LoadModeActivity.this, MediationActivity.class);
        intent.putExtra(Constance.BUNDLE_NETWORK_NAME, MODE_PARALLEL);
        return intent;
    }

    private Intent getShuffleIntent() {
        Intent intent = Utils.getBaseIntent(mShuffleMediaiton);
        intent.setClass(LoadModeActivity.this, MediationActivity.class);
        intent.putExtra(Constance.BUNDLE_NETWORK_NAME, MODE_SHUFFLE);
        return intent;
    }

}