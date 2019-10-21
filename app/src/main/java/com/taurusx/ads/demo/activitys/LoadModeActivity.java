package com.taurusx.ads.demo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.constance.Constance;


public class LoadModeActivity extends BaseActivity {

    private final static String TAG = "LoadModeActivity";
    // serial
    private Button mSerialButton;
    // parallel
    private Button mParallelButton;
    // shuffle
    private Button mShuffleButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);
        getActionBar().setTitle("Mode Test");
        initView();
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
        Intent intent = new Intent();
        intent.setClass(LoadModeActivity.this, MediationActivity.class);
        intent.putExtra(Constance.BUNDLE_NETWORK_NAME, "Serial");
        intent.putExtra(Constance.BUNDLE_TYPE_BANNER,Constance.SERIAL_BANNER_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_REWARDED,Constance.SERIAL_REWARDED_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_INTERSTITIAL,Constance.SERIAL_INTERSTITIAL_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_NATIVE,Constance.SERIAL_NATIVE_ID);
        return intent;
    }

    private Intent getParallelIntent() {
        Intent intent = new Intent();
        intent.setClass(LoadModeActivity.this, MediationActivity.class);
        intent.putExtra(Constance.BUNDLE_NETWORK_NAME, "parallel");
        intent.putExtra(Constance.BUNDLE_TYPE_BANNER,Constance.PARALLEL_BANNER_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_REWARDED,Constance.PARALLEL_REWARDED_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_INTERSTITIAL,Constance.PARALLEL_INTERSTITIAL_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_NATIVE,Constance.PARALLEL_NATIVE_ID);
        return intent;
    }

    private Intent getShuffleIntent() {
        Intent intent = new Intent();
        intent.setClass(LoadModeActivity.this, MediationActivity.class);
        intent.putExtra(Constance.BUNDLE_NETWORK_NAME, "parallel");
        intent.putExtra(Constance.BUNDLE_TYPE_BANNER,Constance.SHUFFLE_BANNER_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_REWARDED,Constance.SHUFFLE_REWARDED_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_INTERSTITIAL,Constance.SHUFFLE_INTERSTITIAL_ID);
        intent.putExtra(Constance.BUNDLE_TYPE_NATIVE,Constance.SHUFFLE_NATIVE_ID);
        return intent;
    }

}