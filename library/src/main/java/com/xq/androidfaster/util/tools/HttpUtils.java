package com.xq.androidfaster.util.tools;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.xq.androidfaster.util.JsonConverter;
import com.xq.androidfaster.util.callback.FasterHttpCallback;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public final class HttpUtils {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    private HttpUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * get请求
     * @param url：url
     * @param callBack：回调接口
     */
    public static void get(String url, HttpUtilsCallback callBack) {
        get(url, null, null, callBack);
    }

    /**
     * get请求，可以传递参数
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口
     */
    public static void get(String url, Map<String, String> paramsMap, HttpUtilsCallback callBack) {
        get(url, paramsMap, null, callBack);
    }

    /**
     * get请求，可以传递参数
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口
     */
    public static void get(String url, Map<String, String> paramsMap, Map<String, String> headerMap, HttpUtilsCallback callBack) {
        new RequestUtil(METHOD_GET, url, paramsMap, headerMap, callBack).execute();
    }

    /**
     * post请求
     * @param url：url
     * @param callBack：回调接口
     */
    public static void post(String url, HttpUtilsCallback callBack) {
        post(url, null, callBack);
    }

    /**
     * post请求，可以传递参数
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param callBack：回调接口
     */
    public static void post(String url, Map<String, String> paramsMap, HttpUtilsCallback callBack) {
        post(url, paramsMap, null, callBack);
    }

    /**
     * post请求，可以传递参数
     * @param url：url
     * @param paramsMap：map集合，封装键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口
     */
    public static void post(String url, Map<String, String> paramsMap, Map<String, String> headerMap, HttpUtilsCallback callBack) {
        new RequestUtil(METHOD_POST,url,paramsMap,headerMap,callBack).execute();
    }

    /**
     * post请求，可以传递参数
     * @param url：url
     * @param jsonStr：json格式的键值对参数
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void postJson(String url, String jsonStr, HttpUtilsCallback callBack) {
        postJson(url, jsonStr, null, callBack);
    }

    /**
     * post请求，可以传递参数
     * @param url：url
     * @param jsonStr：json格式的键值对参数
     * @param headerMap：map集合，封装请求头键值对
     * @param callBack：回调接口，onFailure方法在请求失败时调用，onResponse方法在请求成功后调用，这两个方法都执行在UI线程。
     */
    public static void postJson(String url, String jsonStr, Map<String, String> headerMap, HttpUtilsCallback callBack) {
        new RequestUtil(url,jsonStr,headerMap,callBack).execute();
    }

    static class RequestUtil{

        private Thread mThread;

        /**
         * 一般的get请求或post请求
         */
        RequestUtil(String method,String url, Map<String, String> paramsMap, Map<String, String> headerMap, HttpUtilsCallback callBack) {
            switch (method){
                case "GET":
                    httpGet(url,paramsMap,headerMap,callBack);
                    break;
                case "POST":
                    httpPost(url,paramsMap,null,headerMap,callBack);
                    break;
            }
        }

        /**
         * post请求，传递json格式数据。
         */
        RequestUtil(String url, String jsonStr, Map<String, String> headerMap, HttpUtilsCallback callBack) {
            httpPost(url,null,jsonStr,headerMap,callBack);
        }

        /**
         * get请求
         */
        private void httpGet(final String url, final Map<String, String> paramsMap, final Map<String, String> headerMap, final HttpUtilsCallback callBack) {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new RealRequest().get(getUrl(url,paramsMap),headerMap,callBack);
                }
            });
        }

        /**
         * post请求
         */
        private void httpPost(final String url, final Map<String, String> paramsMap, final String jsonStr, final Map<String, String> headerMap, final HttpUtilsCallback callBack) {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new RealRequest().post(url, getPostBody(paramsMap,jsonStr),getPostBodyType(paramsMap,jsonStr),headerMap,callBack);
                }
            });
        }

        /**
         * get请求，将键值对凭接到url上
         */
        private String getUrl(String path,Map<String, String> paramsMap) {
            if(paramsMap != null){
                path = path+"?";
                for (String key: paramsMap.keySet()){
                    path = path + key+"="+paramsMap.get(key)+"&";
                }
                path = path.substring(0,path.length()-1);
            }
            return path;
        }

        /**
         * 得到post请求的body
         */
        private  String getPostBody(Map<String, String> params,String jsonStr) {//throws UnsupportedEncodingException {
            if(params != null){
                return getPostBodyFormParameMap(params);
            }else if(!TextUtils.isEmpty(jsonStr)){
                return jsonStr;
            }
            return null;
        }

        /**
         * 根据键值对参数得到body
         */
        private  String getPostBodyFormParameMap(Map<String, String> params) {//throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first)
                        first = false;
                    else
                        result.append("&");
                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
                return result.toString();
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }

        /**
         * 得到bodyType
         */
        private String getPostBodyType(Map<String, String> paramsMap, String jsonStr) {
            if(paramsMap != null){
                //return "text/plain";不知为什么这儿总是报错。目前暂不设置(20170424)
                return null;
            }else if(!TextUtils.isEmpty(jsonStr)){
                return "application/json;charset=utf-8";
            }
            return null;
        }

        /**
         * 开启子线程，调用run方法
         */
        void execute(){
            if(mThread != null){
                mThread.start();
            }
        }

        static class RealRequest {

            /**
             * get请求
             */
            void get(String requestURL, Map<String, String> headerMap, HttpUtilsCallback callback){
                callback.onStart();
                HttpURLConnection conn = null;
                try {
                    conn= getHttpURLConnection(requestURL,"GET");
                    conn.setDoInput(true);
                    if(headerMap != null){
                        setHeader(conn,headerMap);
                    }
                    conn.connect();
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                        callback.onSuccess(streamToByteArray(conn.getInputStream()));
                    else
                        callback.onErro(new Exception(new String(streamToByteArray(conn.getErrorStream()),"utf-8")));
                } catch (Exception e) {
                    callback.onErro(e);
                } finally {
                    callback.onFinish();
                }
            }

            /**
             * post请求
             */
            void post(String requestURL, String body, String bodyType, Map<String, String> headerMap, HttpUtilsCallback callback) {
                callback.onStart();
                HttpURLConnection conn = null;
                try {
                    conn = getHttpURLConnection(requestURL, "POST");
                    conn.setDoOutput(true);//可写出
                    conn.setDoInput(true);//可读入
                    conn.setUseCaches(false);//不是有缓存
                    if (!TextUtils.isEmpty(bodyType)) {
                        conn.setRequestProperty("Content-Type", bodyType);
                    }
                    if (headerMap != null) {
                        setHeader(conn, headerMap);//请求头必须放在conn.connect()之前
                    }
                    conn.connect();// 连接，以上所有的请求配置必须在这个API调用之前
                    if (!TextUtils.isEmpty(body)) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                        writer.write(body);
                        writer.close();
                    }
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                        callback.onSuccess(streamToByteArray(conn.getInputStream()));
                    else
                        callback.onErro(new Exception(new String(streamToByteArray(conn.getErrorStream()),"utf-8")));
                } catch (Exception e) {
                    callback.onErro(e);
                } finally {
                    callback.onFinish();
                }
            }

            /**
             * 得到Connection对象，并进行一些设置
             */
            private HttpURLConnection getHttpURLConnection(String requestURL,String requestMethod) throws IOException {
                URL url = new URL(requestURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10*1000);
                conn.setReadTimeout(15*1000);
                conn.setRequestMethod(requestMethod);
                return conn;
            }

            /**
             * 设置请求头
             */
            private void setHeader(HttpURLConnection conn, Map<String, String> headerMap) {
                if(headerMap != null){
                    for (String key: headerMap.keySet()){
                        conn.setRequestProperty(key, headerMap.get(key));
                    }
                }
            }

            private static byte[] streamToByteArray(InputStream input) throws IOException {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024*4];
                int n = 0;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                return output.toByteArray();
            }
        }

    }

    public static abstract class HttpUtilsCallback<T> implements FasterHttpCallback<T> {

        static Handler mainHandler = new Handler(Looper.getMainLooper());

        private Class<T> entityClass;

        public HttpUtilsCallback(Class<T> entityClass) {
            this.entityClass = entityClass;
        }

        @Deprecated
        public void onStart(){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    requestStart();
                }
            });
        }

        @Deprecated
        public void onSuccess(byte[] bytes){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (entityClass.isAssignableFrom(byte[].class))
                        requestSuccess((T) bytes);
                    else
                    {
                        try {
                            String data = new String(bytes,"utf-8");
                            if (entityClass.isAssignableFrom(String.class))
                                requestSuccess((T) data);
                            else
                                requestSuccess(JsonConverter.jsonToObject(data,entityClass));
                        } catch (Exception e) {
                            requestError(e);
                        }
                    }
                }
            });
        }

        @Deprecated
        public void onErro(Exception e){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    requestError(e);
                }
            });
        }

        @Deprecated
        public void onFinish(){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    requestFinish();
                }
            });
        }

        @Override
        public boolean operating(T s, Object... objects) {
            return true;
        }

        private CallbackBuilder callbackBuilder = new CallbackBuilder();
        @Override
        public CallbackBuilder getCallbackBuilder() {
            return callbackBuilder;
        }
    }

}
