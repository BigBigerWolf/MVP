package com.example.rxretrofitdaggermvp.manager;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.R;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by MrKong on 2017/3/29.
 */

public class ExceptionManger {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException serverException = (ServerException) e;
            ex = new ApiException(serverException, serverException.code);
            ex.message = serverException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof IOException) {
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.message = MyApp.getAppContext().getResources().getString(R.string.error_net);
            return ex;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }

    public static class ApiException extends Exception {
        public int code;
        public String message;

        public ApiException(Throwable throwable, int code) {
            super(throwable);
            this.code = code;

        }
    }

    public static class ServerException extends RuntimeException {

        public ServerException(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int code;
        public String message;
    }

    public static class ERROR {
        public static final int UNKNOWN = 1000;
        public static final int PARSE_ERROR = 1001;
        public static final int NETWORD_ERROR = 1002;
        public static final int HTTP_ERROR = 1003;
    }

}
