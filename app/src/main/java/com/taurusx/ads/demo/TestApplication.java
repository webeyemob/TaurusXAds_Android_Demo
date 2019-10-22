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
                .appId("4b4b6832-4267-42db-9c04-d517d5288bbb")
                .build();
        TaurusXAds.getDefault().initialize(this, configuration);
    }

}
