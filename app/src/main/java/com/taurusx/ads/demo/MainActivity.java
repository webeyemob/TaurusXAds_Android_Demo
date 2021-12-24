package com.taurusx.ads.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.adview.AppLovinFullscreenActivity;
import com.applovin.impl.sdk.AppLovinAdBase;
import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.demo.activitys.home.AdapterTestActivity;
import com.taurusx.ads.demo.activitys.home.LoadModeActivity;
import com.taurusx.ads.demo.activitys.home.NetworkActivity;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class MainActivity extends Activity {

    private final String TAG = "haha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setTitle("TaurusX " + TaurusXAds.SDK_VERSION_NAME);

        initView();

        String gpUrl = "https://play.google.com/store/apps/details?id=com.global.ztmslg&hl=ja";
        gpUrl = gpUrl.replace(gpUrl, "abc");
        LogUtil.d(TAG, "gpurl: " + gpUrl);

        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                String activityName = activity.getClass().getName();
                LogUtil.d(TAG, "onResume: " + activityName);
                if (activityName.equals("com.applovin.adview.AppLovinFullscreenActivity")) {
                    AppLovinFullscreenActivity aty = (AppLovinFullscreenActivity) activity;
                    AppLovinAdBase adBase = aty.parentInterstitialWrapper.b();

                    try {
                        Field adObjectField = adBase.getClass().getSuperclass().getSuperclass().getDeclaredField("adObject");
                        adObjectField.setAccessible(true);
                        JSONObject adObject = (JSONObject) adObjectField.get(adBase);
                        LogUtil.d(TAG, "adObject: " + adObject);

                        String url = "http://api.richox.net/api/v1/strategy/lazada/click?q=-6x9bMTmO2zI0YJU88_jvUR3t1vMzI6wdJEf7MY2TUBsKWw4p5R6WDrfLHxS3hBwsGbPs0YIbZo0ULfO-7HnX0nFwD_BWCksuD47_xtGn6YIP2NaOl5V7hTSa6yQq6IgYzfxUwgmXUoqW5_4Kz_pvWpjshTB0BvHA3KF5K5jZjaHCtm9wSwqwDcknBtDR2U7M6umjp5AdsTi_A8Cj3K5Di-L5Yc4qwIiDgSPu7LIUL0W4qVQD9VBX5rZ_l7UHoKikWtzaoAINbv9PVhgOZYUnirSyR7lyg5qTOdvJi8MIot02tTHquv4zCD09ibPxx5Wvpv5390TnI54WDy8vgGLkFTWXStQjGGYopu0b5z6E4vFlJU7j1GK8FZ0DiWKwe-LHIyP0irzuzXAOw8lJi-jJA&clickid=gilM13V2r1JcGlaZWDRdxAwjOcezO9zWMc2eT3HmRI2SskIvThxZWXGxNqKlU-pzt6FdkgzqzLo-VEhdM-wRTBsG18IrZ3e6zZsJHVmJS6kmciFUZLVlzH8n0hxwyrgnPUIQmY7aCEn6ZOanL6rR-wxugCThVyA7p44lOU3MvpY&clickurl=aHR0cHM6Ly9jLmxhemFkYS5jby5pZC90L2MuMHltNmpZP3VybD1odHRwcyUzQSUyRiUyRnd3dy5sYXphZGEuY28uaWQlMkZtYXJrZXRpbmclMkZnYXRld2F5JTJGaW5kZXguaHRtbCZydGFfdG9rZW49M2ZlYjRlN2QtYzM4ZC00NmI0LWE4YzUtMDQwOTBkYTM1ZmI5JnJ0YV9ldmVudF9pZD0wYmZhNjE0MjE2MzkxNDAyODAwMjIyMDc3Jm9zPUFuZHJvaWQmZ3BzX2FkaWQ9YmE1ZmU0MDYtNjViZC00NjE4LWIxOTEtNzlkYjQwMGM3MmQyJmlkZmE9X19pZGZhX18maWRmdj1fX2lkZnZfXyZidW5kbGVfaWQ9d3Aud2F0dHBhZCZkZXZpY2VfbW9kZWw9Q1BIMTcyOSZkZXZpY2VfbWFrZT1PUFBPJmlwPV9faXBfXyZzdWJfaWQxPV9fQU1fQ0hBTk5FTF9fJnN1Yl9pZDI9X19BTV9DVVNUT01fREFUQV9f&click_tracking_url=aHR0cHM6Ly9jLmxhemFkYS5jby5pZC9jbS9jLjB5bTZqWT91cmw9aHR0cHMlM0ElMkYlMkZ3d3cubGF6YWRhLmNvLmlkJTJGbWFya2V0aW5nJTJGZ2F0ZXdheSUyRmluZGV4Lmh0bWwmcnRhX3Rva2VuPTNmZWI0ZTdkLWMzOGQtNDZiNC1hOGM1LTA0MDkwZGEzNWZiOSZydGFfZXZlbnRfaWQ9MGJmYTYxNDIxNjM5MTQwMjgwMDIyMjA3NyZvcz1BbmRyb2lkJmdwc19hZGlkPWJhNWZlNDA2LTY1YmQtNDYxOC1iMTkxLTc5ZGI0MDBjNzJkMiZpZGZhPV9faWRmYV9fJmlkZnY9X19pZGZ2X18mYnVuZGxlX2lkPXdwLndhdHRwYWQmZGV2aWNlX21vZGVsPUNQSDE3MjkmZGV2aWNlX21ha2U9T1BQTyZpcD1fX2lwX18mc3ViX2lkMT1fX0FNX0NIQU5ORUxfXyZzdWJfaWQyPV9fQU1fQ1VTVE9NX0RBVEFfXw&imp_tracking_url=aHR0cHM6Ly9jLmxhemFkYS5jby5pZC9pL2MuMHltNmpZP3VybD1odHRwcyUzQSUyRiUyRnd3dy5sYXphZGEuY28uaWQlMkZtYXJrZXRpbmclMkZnYXRld2F5JTJGaW5kZXguaHRtbCZydGFfdG9rZW49M2ZlYjRlN2QtYzM4ZC00NmI0LWE4YzUtMDQwOTBkYTM1ZmI5JnJ0YV9ldmVudF9pZD0wYmZhNjE0MjE2MzkxNDAyODAwMjIyMDc3Jm9zPUFuZHJvaWQmZ3BzX2FkaWQ9YmE1ZmU0MDYtNjViZC00NjE4LWIxOTEtNzlkYjQwMGM3MmQyJmlkZmE9X19pZGZhX18maWRmdj1fX2lkZnZfXyZidW5kbGVfaWQ9d3Aud2F0dHBhZCZkZXZpY2VfbW9kZWw9Q1BIMTcyOSZkZXZpY2VfbWFrZT1PUFBPJmlwPV9faXBfXyZzdWJfaWQxPV9fQU1fQ0hBTk5FTF9fJnN1Yl9pZDI9X19BTV9DVVNUT01fREFUQV9f";
                        adObject.put("click_url", url);

                        Field fullResponseField = adBase.getClass().getSuperclass().getSuperclass().getDeclaredField("fullResponse");
                        fullResponseField.setAccessible(true);
                        JSONObject fullResponse = (JSONObject) fullResponseField.get(adBase);
                        LogUtil.d(TAG, "fullResponse: " + fullResponse);
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
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
    }
}
