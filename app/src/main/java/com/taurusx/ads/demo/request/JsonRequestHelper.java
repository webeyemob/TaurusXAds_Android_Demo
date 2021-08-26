package com.taurusx.ads.demo.request;

import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Map;

public class JsonRequestHelper {

    private static final String TAG = "JsonRequestHelper";

    public static void get(String url,
                           Map<String, String> headerMap,
                           int timeOutS,
                           OnRequestListener listener) {
        request(url, JsonRequest.RequestHelper.Method.GET, headerMap, null, timeOutS, listener);
    }

    public static void post(String url,
                            Map<String, String> headerMap,
                            int timeOutS,
                            OnRequestListener listener) {
        request(url, JsonRequest.RequestHelper.Method.POST, headerMap, null, timeOutS, listener);
    }

    public static void post(String url,
                            Map<String, String> headerMap,
                            String data,
                            int timeOutS,
                            OnRequestListener listener) {
        request(url, JsonRequest.RequestHelper.Method.POST, headerMap, data.getBytes(), timeOutS, listener);
    }

    public static void post(String url,
                            Map<String, String> headerMap,
                            byte[] data,
                            int timeOutS,
                            OnRequestListener listener) {
        request(url, JsonRequest.RequestHelper.Method.POST, headerMap, data, timeOutS, listener);
    }

    private static void request(final String url,
                                final JsonRequest.RequestHelper.Method method,
                                final Map<String, String> headerMap,
                                final byte[] postData,
                                final int timeOutS,
                                final OnRequestListener listener) {
        JsonRequest request = new JsonRequest();
        request.setRequestHelper(new JsonRequest.RequestHelper() {
            @Override
            public Method getRequestMethod() {
                return method;
            }

            @Override
            public String getRequestURL() {
                return url;
            }

            @Override
            public byte[] getRequestJsonData() {
                return postData;
            }

            @Override
            public Map<String, String> getRequestHeader() {
                return headerMap;
            }

            @Override
            public int getTimeOutS() {
                return timeOutS;
            }

            @Override
            public void onRequestFinished(HttpURLConnection response, boolean succeeded) {
                int statusCode = -1;
                if (response != null) {
                    try {
                        statusCode = response.getResponseCode();
//                        LogUtil.d(TAG, "statusCode: " + statusCode);
                        if (statusCode == 200) {
//                            LogUtil.d(TAG, "request success");
                            String responseString = InputStreamUtil.readString(
                                    JsonRequest.getResponseStream(response), Charset.forName("utf-8"));
//                            LogUtil.d(TAG, responseString);
                            if (listener != null) {
                                listener.onSuccess(responseString);
                            }
                            return;
                        }
                    } catch (Exception | Error e) {
                        e.printStackTrace();
                    }
                }
                if (listener != null) {
                    listener.onFail(statusCode);
                }
            }
        });
        request.request();
    }

    public interface OnRequestListener {
        void onSuccess(String result);

        void onFail(int statusCode);
    }
}
