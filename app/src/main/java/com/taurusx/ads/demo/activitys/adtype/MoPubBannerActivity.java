package com.taurusx.ads.demo.activitys.adtype;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mopub.common.MoPub;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.AdViewController;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.network.AdLoader;
import com.mopub.network.AdResponse;
import com.mopub.network.MoPubNetworkError;
import com.taurusx.ads.core.api.model.BannerAdSize;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.core.api.utils.ScreenUtil;
import com.taurusx.ads.demo.R;
import com.taurusx.ads.demo.activitys.base.BaseActivity;
import com.taurusx.ads.demo.request.JsonRequestHelper;
import com.taurusx.ads.demo.utils.Constant;
import com.taurusx.ads.demo.utils.Utils;
import com.taurusx.ads.mediation.helper.MoPubHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class MoPubBannerActivity extends BaseActivity {

    private final String TAG = "MoPubBannerActivity";

    private MoPubView mMoPubView;
    private String mAdUnitId = "";
    private FrameLayout mAdContainer;

    private EditText mBodyEditText;
    private String mBody;

    private Button mLoadButton;
    private Button mClearButton;

    private RadioGroup mRadioGroup;

    private LinearLayout mContainer;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getIntent().getStringExtra(Constant.KEY_TITLE));

        setContentView(R.layout.activity_banner_mopub);

        mContainer = findViewById(R.id.layout_banner);
        mProgressBar = findViewById(R.id.progressBar);

        mBodyEditText = findViewById(R.id.editText_body);

        mLoadButton = findViewById(R.id.banner_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainer.removeAllViews();

                mBody = mBodyEditText.getText().toString();
                if (TextUtils.isEmpty(mBody)) {
                    Utils.toast(MoPubBannerActivity.this, "Empty Content");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);
                int id = mRadioGroup.getCheckedRadioButtonId();
                if (id == R.id.radiobutton_mraid_html) {
                    loadAdImpl();
                } else if (id == R.id.radiobutton_bidresponse) {
                    try {
                        loadFromJson(mBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mProgressBar.setVisibility(View.GONE);
                    }
                } else if (id == R.id.radiobutton_bidresponse_url) {
                    JsonRequestHelper.get(mBody, null, 5, new JsonRequestHelper.OnRequestListener() {
                        @Override
                        public void onSuccess(String result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        loadFromJson(result);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        mProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFail(int statusCode) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(MoPubBannerActivity.this,
                                            "Request Bid Url failed: " + statusCode,
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                } else {
                    Utils.toast(MoPubBannerActivity.this, "Please Select Content Type");
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        mClearButton = findViewById(R.id.clear_content);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBodyEditText.setText("");
            }
        });

        mRadioGroup = findViewById(R.id.radioGroup);
    }

    private void loadFromJson(String body) throws Exception {
        JSONObject object = new JSONObject(body);
        JSONArray seatbidArray = object.optJSONArray("seatbid");
        JSONObject bidObject = seatbidArray.optJSONObject(0).optJSONArray("bid").optJSONObject(0);
        mBody = bidObject.optString("adm");
        loadAdImpl();
    }

    private void loadAdImpl() {
        initBannerAdView();

        if (MoPub.isSdkInitialized()) {
            mMoPubView.loadAd();
        } else {
            LogUtil.d(TAG, "Wait MoPub Init");
            MoPubHelper.registerInitListener(new SdkInitializationListener() {
                @Override
                public void onInitializationFinished() {
                    mMoPubView.loadAd();
                }
            });
        }
    }

    private void initBannerAdView() {
        BannerAdSize adSize;
        if (getActionBar().getTitle().toString().contains("320*50")) {
            adSize = BannerAdSize.BANNER_320_50;
            mAdUnitId = "5b4d99baa2dd4c9a966d1eadfa609e96";
        } else if (getActionBar().getTitle().toString().contains("300*250")) {
            adSize = BannerAdSize.BANNER_300_250;
            mAdUnitId = "35888ba0b7c64cf8840bde307ef6ee5a";
        } else {
            adSize = BannerAdSize.BANNER_320_50;
            mAdUnitId = "5b4d99baa2dd4c9a966d1eadfa609e96";
        }

        MoPubHelper.init(this, mAdUnitId);

        mMoPubView = new MoPubView(this);
        mMoPubView.setAdUnitId(mAdUnitId);
        // 通过父布局设置高度
        // mMoPubView.setAdSize(MoPubView.MoPubAdSize.HEIGHT_50);
        mMoPubView.setAutorefreshEnabled(false);

        Context appContext = this.getApplicationContext();
        mAdContainer = new FrameLayout(appContext);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                adSize.getWidth(appContext),
                adSize.getHeight(appContext));
        params.gravity = Gravity.CENTER;
        mAdContainer.addView(mMoPubView, params);

        mMoPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
            @Override
            public void onBannerLoaded(@NonNull MoPubView moPubView) {
                LogUtil.d(TAG, "onBannerLoaded");
                mProgressBar.setVisibility(View.GONE);
                mContainer.removeAllViews();
                mContainer.addView(mAdContainer);
            }

            @Override
            public void onBannerFailed(MoPubView moPubView, MoPubErrorCode moPubErrorCode) {
                LogUtil.e(TAG, "onBannerFailed: " + moPubErrorCode.toString());
                mProgressBar.setVisibility(View.GONE);
                Utils.toast(MoPubBannerActivity.this, moPubErrorCode.toString());
            }

            @Override
            public void onBannerClicked(MoPubView moPubView) {
                LogUtil.d(TAG, "onBannerClicked");
            }

            @Override
            public void onBannerExpanded(MoPubView moPubView) {
                LogUtil.d(TAG, "onBannerExpanded");
            }

            @Override
            public void onBannerCollapsed(MoPubView moPubView) {
                LogUtil.d(TAG, "onBannerCollapsed");
            }
        });

        AdViewController adViewController = mMoPubView.getAdViewController();
        AdLoader.Listener listener = new AdLoader.Listener() {
            public void onResponse(@NonNull AdResponse response) {
                String body = "<!DOCTYPE html> <html> <head>  <!-- Adgroup is 0d0563208bfa4a0ea5f5da4db9ca0c5e -->  <meta http-equiv=\"Content-Security-Policy\" content=\"upgrade-insecure-requests\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">  <style> body { margin:0; padding:0; overflow:hidden; background:transparent; } </style> <script src=\"mraid.js\"></script>  <script type=\"text/javascript\"> function webviewDidAppear() { if (typeof trackImpressionHelper == 'function') { trackImpressionHelper(); } }  </script> </head> <body> " + mBody + " </body> </html>";

                try {
                    Field mResponseBodyField = AdResponse.class.getDeclaredField("mResponseBody");
                    mResponseBodyField.setAccessible(true);
                    mResponseBodyField.set(response, body);

                    Field mServerExtrasField = AdResponse.class.getDeclaredField("mServerExtras");
                    mServerExtrasField.setAccessible(true);
                    Map<String, String> serverExtras = (Map<String, String>) mServerExtrasField.get(response);
                    serverExtras.put("html-response-body", body);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                try {
                    Method onAdLoadSuccessMethod = AdViewController.class.getDeclaredMethod("onAdLoadSuccess", AdResponse.class);
                    onAdLoadSuccessMethod.setAccessible(true);
                    onAdLoadSuccessMethod.invoke(adViewController, response);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            public void onErrorResponse(@NonNull MoPubNetworkError networkError) {
                try {
                    Method onAdLoadErrorMethod = AdViewController.class.getDeclaredMethod("onAdLoadError", MoPubNetworkError.class);
                    onAdLoadErrorMethod.setAccessible(true);
                    onAdLoadErrorMethod.invoke(adViewController, networkError);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            Field listenerField = AdViewController.class.getDeclaredField("mAdListener");
            listenerField.setAccessible(true);
            listenerField.set(adViewController, listener);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}