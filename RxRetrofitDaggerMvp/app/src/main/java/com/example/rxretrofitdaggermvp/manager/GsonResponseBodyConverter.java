package com.example.rxretrofitdaggermvp.manager;

import com.google.gson.Gson;
import com.example.rxretrofitdaggermvp.mvp.module.entity.BaseResponse;
import com.example.rxretrofitdaggermvp.utils.Constant;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
        switch (baseResponse.getError_code()) {
            case Constant.SUCCESS:
                return gson.fromJson(response, type);
            default:
                //防止服务端返回空串
                throw new ExceptionManger.ServerException(baseResponse.getError_code(), baseResponse.getReason());
        }
    }
}