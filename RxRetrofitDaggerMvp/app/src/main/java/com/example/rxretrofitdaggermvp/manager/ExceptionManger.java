package com.example.rxretrofitdaggermvp.manager;

import android.net.ParseException;

import com.example.rxretrofitdaggermvp.MyApp;
import com.example.rxretrofitdaggermvp.R;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by MrKong on 2017/3/29.
 */

public class ExceptionManger {
    private static final int UNAUTHORIZED = 401;                //（身份验证错误） 此页要求授权。您可能不希望将此网页纳入索引。
    private static final int FORBIDDEN = 403;                   //（禁止） 服务器拒绝请求。
    private static final int NOT_FOUND = 404;                   //（未找到） 服务器找不到请求的网页。例如，对于服务器上不存在的网页经常会返回此代码。
    private static final int REQUEST_TIMEOUT = 408;             //（请求超时） 服务器等候请求时发生超时。
    private static final int INTERNAL_SERVER_ERROR = 500;       //（服务器内部错误）  服务器遇到错误，无法完成请求。
    private static final int BAD_GATEWAY = 502;                 //（错误网关） 服务器作为网关或代理，从上游服务器收到了无效的响应。
    private static final int SERVICE_UNAVAILABLE = 503;         //（服务不可用） 目前无法使用服务器（由于超载或进行停机维护）。通常，这只是一种暂时的状态。
    private static final int GATEWAY_TIMEOUT = 504;             //（网关超时）  服务器作为网关或代理，未及时从上游服务器接收请求。

    //自定义错误码
    private static class ERROR {
        private static final int UNKNOWN = 1000;
        private static final int PARSE_ERROR = 1001;
        private static final int NETWORD_ERROR = 1002;
        private static final int UNKNOWN_HOST = 1003;
    }

    public static ApiException handleException(Throwable e) {
        e.printStackTrace();
        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, httpException.code());
            switch (ex.code) {
                case UNAUTHORIZED:
                    ex.message = MyApp.getAppContext().getString(R.string.exception_un_authorized);
                    break;
                case FORBIDDEN:
                    ex.message = MyApp.getAppContext().getString(R.string.exception_server_forbidden);
                    break;
                case NOT_FOUND:
                    ex.message = MyApp.getAppContext().getString(R.string.exception_not_found);
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = MyApp.getAppContext().getString(R.string.exception_request_time_out);
                    break;
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case INTERNAL_SERVER_ERROR:
                    ex.message = MyApp.getAppContext().getString(R.string.exception_internal_server_error);
                    break;
                case GATEWAY_TIMEOUT:
                    ex.message = MyApp.getAppContext().getString(R.string.exception_gateway_timeout);
                    break;
                default:
                    ex.message = MyApp.getAppContext().getString(R.string.exception_not_catched_http);
                    break;
            }
        } else if (e instanceof ServerException) {              //服务器返回的错误
            ServerException serverException = (ServerException) e;
            ex = new ApiException(serverException, serverException.code);
            ex.message = serverException.message;
        } else if (e instanceof JSONException
                || e instanceof ParseException) {               //数据解析错误
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = MyApp.getAppContext().getString(R.string.exception_json_parse);
        } else if (e instanceof ConnectException
                || e instanceof SocketTimeoutException) {       //连接错误
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.message = MyApp.getAppContext().getString(R.string.exception_request_time_out);
        } else if (e instanceof UnknownHostException) {         //host解析错误
            ex = new ApiException(e, ERROR.UNKNOWN_HOST);
            ex.message = MyApp.getAppContext().getString(R.string.exception_unknown_host);
        } else {                                                //未捕获到的错误
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = MyApp.getAppContext().getString(R.string.exception_not_catched);
            //这里会抓到相关observable的一切异常，比如处理数据导致的NullPointerException
            //throw new RuntimeException(e);
        }
        return ex;
    }

    public static class ApiException extends Exception {
        public int code;
        public String message;

        ApiException(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    static class ServerException extends RuntimeException {

        ServerException(int code, String desciption) {
            this.code = code;
            this.message = desciption;
        }

        public int code;
        public String message;
    }
}
