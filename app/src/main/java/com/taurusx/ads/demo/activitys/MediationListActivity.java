package com.taurusx.ads.demo.activitys;

import android.os.Bundle;
import android.widget.ListView;

import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.adapter.SimpleMediationAdapter;
import com.taurusx.ads.demo.bean.Mediation;
import com.taurusx.ads.demo.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediationListActivity extends BaseActivity {
    private ListView mListView;
    private List<Map.Entry<String, Mediation>> mInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meditaion);
        getActionBar().setTitle("Netwrok Test");

        initData();
        initView();
    }

    private void initView() {
        mListView = findViewById(R.id.mediation_list);
        SimpleMediationAdapter adapter = new SimpleMediationAdapter(MediationListActivity.this, mInfoList);
        mListView.setAdapter(adapter);
    }

    private void initData() {
        String info = Utils.getAssetsContent(MediationListActivity.this, "taurus_ad.json");
        mInfoList = Utils.getMediation(info);
    }

}
