package com.taurusx.ads.demo.request;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 使用 HttpURLConnection 发送 Json 请求。
 * 支持 Get 和 Post。
 */
class JsonRequest {

    private final String TAG = "JsonRequest";
    private RequestHelper mRequestHelper = null;

    public JsonRequest() {
    }

    public static InputStream getResponseStream(HttpURLConnection connection) {
        try {
            InputStream is = connection.getInputStream();
            if (is != null) {
                String encoding = connection.getContentEncoding();
                if (encoding != null && TextUtils.equals(encoding, "gzip")) {
                    is = new GZIPInputStream(is);
                }
            }
            return is;
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setRequestHelper(RequestHelper requestHelper) {
        mRequestHelper = requestHelper;
    }

    public void request() {
//        LogUtil.d(TAG, "request()");
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                RequestHelper.Method method = mRequestHelper.getRequestMethod();
                if (method == RequestHelper.Method.GET) {
                    doGet();
                } else if (method == RequestHelper.Method.POST) {
                    doPost();
                }
            }
        });
    }

    private void doGet() {
        HttpURLConnection urlConnection;
        try {
            final String requestUrl = mRequestHelper.getRequestURL();
            // LogUtil.d(TAG, "requestUrl is " + requestUrl);
            URL url = new URL(requestUrl);
            //打开连接
//            if (requestUrl.startsWith("https")) {
//                SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
//                sslcontext.init(null, new TrustManager[]{new MyX509TrustManager()}, new SecureRandom());
//                urlConnection = (HttpsURLConnection) url.openConnection();
//                ((HttpsURLConnection) urlConnection).setSSLSocketFactory(sslcontext.getSocketFactory());
//            } else {
            urlConnection = (HttpURLConnection) url.openConnection();
//            }
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(mRequestHelper.getTimeOutS() * 1000);

            Map<String, String> headerMap = mRequestHelper.getRequestHeader();
            if (headerMap != null && !headerMap.isEmpty()) {
                for (String key : headerMap.keySet()) {
                    urlConnection.setRequestProperty(key, headerMap.get(key));
                }
            }

            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode >= 200 && statusCode < 300) {
//                LogUtil.d(TAG, " Ping succeeded.");
                onRequestDone(urlConnection);
            } else {
//                LogUtil.d(TAG, " Ping failed, status code: " + statusCode);
                onRequestFailed(urlConnection);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            onRequestFailed(null);
        } finally {
        }
    }

    private void doPost() {
        HttpURLConnection urlConnection;
        try {
            final String requestUrl = mRequestHelper.getRequestURL();
            // LogUtil.d(TAG, "requestUrl is " + requestUrl);
            byte[] data = mRequestHelper.getRequestJsonData();
//            LogUtil.d(TAG, "doPost() data:" + data);

            URL url = new URL(requestUrl);
            //打开连接
//            if (requestUrl.startsWith("https")) {
//                SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
//                sslcontext.init(null, new TrustManager[]{new MyX509TrustManager()}, new SecureRandom());
//                urlConnection = (HttpsURLConnection) url.openConnection();
//                ((HttpsURLConnection) urlConnection).setSSLSocketFactory(sslcontext.getSocketFactory());
//            } else {
            urlConnection = (HttpURLConnection) url.openConnection();
//            }
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(mRequestHelper.getTimeOutS() * 1000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            Map<String, String> headerMap = mRequestHelper.getRequestHeader();
            if (headerMap != null && !headerMap.isEmpty()) {
                for (String key : headerMap.keySet()) {
                    urlConnection.setRequestProperty(key, headerMap.get(key));
                }
            }

            OutputStream os = urlConnection.getOutputStream();
            String gzip = urlConnection.getRequestProperty("Content-Encoding");
            if (!TextUtils.isEmpty(gzip) && "gzip".equals(gzip)) {
                os.write(compress(data));
            } else {
                os.write(data);
            }
            os.flush();

//            // 获取URLConnection对象对应的输出流
//            PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
//            // 发送请求参数
//            printWriter.write(data);
//            // flush输出流的缓冲
//            printWriter.flush();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode >= 200 && statusCode < 300) {
//                LogUtil.d(TAG, " Ping succeeded.");
                onRequestDone(urlConnection);
            } else {
//                LogUtil.d(TAG, " Ping failed, status code: " + statusCode);
                onRequestFailed(urlConnection);
            }
        } catch (Exception | Error e) {
            // e.printStackTrace();
            onRequestFailed(null);
        } finally {
        }
    }

    private byte[] compress(byte[] str) {
        if (null == str || str.length <= 0) {
            return null;
        }
        // 创建一个新的 byte 数组输出流
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用默认缓冲区大小创建新的输出流
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            // 将 b.length 个字节写入此输出流
            gzip.write(str);
            gzip.close();
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toByteArray();
        } catch (Exception|Error e) {
            e.printStackTrace();
        }
        return null;
    }

    private void onRequestDone(HttpURLConnection connection) {
        mRequestHelper.onRequestFinished(connection, true);
    }

    private void onRequestFailed(HttpURLConnection connection) {
        mRequestHelper.onRequestFinished(connection, false);
    }

    /**
     * 获取请求参数、回调请求结果接口。
     */
    public interface RequestHelper {
        Map<String, String> getRequestHeader();

        /**
         * 请求方法。
         *
         * @return {@link Method}
         */
        Method getRequestMethod();

        /**
         * 请求的 url。
         *
         * @return url
         */
        String getRequestURL();

        /**
         * POST 请求的参数。
         *
         * @return json data
         */
        byte[] getRequestJsonData();

        int getTimeOutS();

        /**
         * 请求结果回调。
         *
         * @param response  response
         * @param succeeded 是否成功
         */
        void onRequestFinished(HttpURLConnection response, boolean succeeded);

        enum Method {
            GET,
            POST
        }
    }
}
