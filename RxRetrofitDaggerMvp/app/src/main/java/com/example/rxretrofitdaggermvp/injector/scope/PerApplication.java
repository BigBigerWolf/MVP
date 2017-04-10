package com.example.rxretrofitdaggermvp.injector.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by MrKong on 2017/4/1.
 * 控制全局单例
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
}
