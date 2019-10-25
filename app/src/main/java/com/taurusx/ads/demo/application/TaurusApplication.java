package com.taurusx.ads.demo.application;

import android.app.Application;

import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.TaurusXAdsConfiguration;
import com.taurusx.ads.demo.constance.Constance;

public class TaurusApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TaurusXAds.getDefault().setGdprConsent(true);
        // Show Log
        TaurusXAds.setLogEnable(true);
        // Init With AppId
        TaurusXAdsConfiguration configuration = new TaurusXAdsConfiguration.Builder(this)
                .appId(Constance.APP_UNIT_ID)
                .build();
        TaurusXAds.getDefault().initialize(this, configuration);
    }

}
