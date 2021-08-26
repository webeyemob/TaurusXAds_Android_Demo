package com.taurusx.ads.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.demo.activitys.home.AdapterTestActivity;
import com.taurusx.ads.demo.activitys.home.LoadModeActivity;
import com.taurusx.ads.demo.activitys.home.NetworkActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setTitle("TaurusX " + TaurusXAds.SDK_VERSION_NAME);

        initView();
    }

    private void initView() {
        Button networks = findViewById(R.id.network_test);
        networks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NetworkActivity.class);
                startActivity(intent);
            }
        });

        Button strategy = findViewById(R.id.loadmode_test);
        strategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoadModeActivity.class);
                startActivity(intent);
            }
        });

        Button listViewTest = findViewById(R.id.listview_test);
        listViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterTestActivity.start(MainActivity.this, true);
            }
        });

        Button recyclerTest = findViewById(R.id.recyclerview_test);
        recyclerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterTestActivity.start(MainActivity.this, false);
            }
        });

        networks.performClick();
    }
}
