package com.taurusx.ads.demo.activitys.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.adtype.AdTypeActivity;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.utils.Utils;

import org.json.JSONObject;

import java.util.List;


public class LoadModeActivity extends BaseActivity {

    private final static String TAG = "LoadModeActivity";

    private Button mSerialButton;
    private Button mParallelButton;
    private Button mShuffleButton;
    private Button mAutoLoadButton;

    private List<JSONObject> mMediationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loadmode);
        getActionBar().setTitle("LoadMode Test");

        mMediationList = Utils.getMediationList(this, "load_mode_ad.json");
        initView();
    }

    private void initView() {
        mSerialButton = findViewById(R.id.mode_serial);
        mSerialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdTypeActivity.start(LoadModeActivity.this, mMediationList.get(0));
            }
        });

        mParallelButton = findViewById(R.id.mode_parallel);
        mParallelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdTypeActivity.start(LoadModeActivity.this, mMediationList.get(1));
            }
        });

        mShuffleButton = findViewById(R.id.mode_shuffle);
        mShuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdTypeActivity.start(LoadModeActivity.this, mMediationList.get(2));
            }
        });

        mAutoLoadButton = findViewById(R.id.mode_autoload);
        mAutoLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdTypeActivity.start(LoadModeActivity.this, mMediationList.get(3));
            }
        });
    }
}