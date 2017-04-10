package com.example.rxretrofitdaggermvp.utils;

import com.orhanobut.logger.Logger;

/**
 * Created by MrKong on 2017/4/1.
 */

public class LogUtil {

    private static boolean isDebug = true;

//    private static boolean isDebug = false;

    /**
     * InformationLevel
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).i(msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Logger.i(msg);
        }
    }

    /**
     * DebugLevel
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).d(msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Logger.d(msg);
        }
    }

    /**
     * WarningLevel
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).w(msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            Logger.w(msg);
        }
    }

    /**
     * ErrorLevel
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).e(msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Logger.e(msg);
        }
    }

    /**
     * formatJson
     *
     * @param tag
     * @param json
     */
    public static void json(String tag, String json) {
        if (isDebug) {
            Logger.t(tag).json(json);
        }
    }

    public static void json(String json) {
        if (isDebug) {
            Logger.json(json);
        }
    }
}
