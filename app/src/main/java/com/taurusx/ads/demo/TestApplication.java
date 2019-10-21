package com.taurusx.ads.demo;

import android.app.Application;

import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.TaurusXAdsConfiguration;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TaurusXAds.getDefault().setGdprConsent(true);
        // Show Log
        TaurusXAds.setLogEnable(true);
        // Init With AppId
        TaurusXAdsConfiguration configuration = new TaurusXAdsConfiguration.Builder(this)
                .appId("d13be96e-e172-4645-b761-4827a0ae8c0c")
                .build();
        TaurusXAds.getDefault().initialize(this, configuration);
    }

}
