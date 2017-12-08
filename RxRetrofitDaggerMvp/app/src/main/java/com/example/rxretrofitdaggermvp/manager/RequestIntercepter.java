package com.example.rxretrofitdaggermvp.manager;

import android.support.annotation.NonNull;

import com.example.rxretrofitdaggermvp.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by MrKong on 2017/11/10.
 * 自定义请求拦截器
 * 监测超时时间，获取参数，url，参数加密
 */

public class RequestIntercepter implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String originalUrl = originalRequest.url().toString();
        LogUtil.d(originalUrl);
        RequestBody originalRequestBody = originalRequest.body();
        /** requestParams **/
        String paramsStr = "";
        if (originalRequestBody != null) {
            Buffer buffer = new Buffer();
            originalRequestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            paramsStr = buffer.readString(charset);
        }
        /** request--responseTime **/
        long startTime = System.nanoTime();
        Response response = chain.proceed(chain.request());
        int code = response.code();
        long chainTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            /** string()调用后会关闭response流，需重新构建response **/
            String subtype = null;
            ResponseBody body;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                subtype = contentType.subtype();
            }
            if (subtype != null && (subtype.contains("json")
                    || subtype.contains("xml")
                    || subtype.contains("plain")
                    || subtype.contains("html"))) {
                /** responseString **/
                String bodyString = responseBody.string();
                LogUtil.d(bodyString);
                body = ResponseBody.create(contentType, bodyString);
            } else {
                return response;
            }
            return response.newBuilder().body(body).build();
        } else {
            return response;
        }
    }
}
