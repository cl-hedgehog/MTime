package com.dreamzone.mtime.common;

import android.content.Context;

import com.matrix.appsdk.BaseApp;
import com.matrix.appsdk.utils.TinySpUtils;

/**
 * @author BMR
 * @ClassName: AppSPUtils
 * @Description: SharedPreferences utils for app config
 * @date 2016/1/28 15:22
 */
public final class AppSPUtils {
    public static AppSPUtils ins = new AppSPUtils();
    private final Context context;

    private final static String SP_CONFIG = "USER_CONFIG";
    public static final String SP_KEY_IS_LOGIN = "is_login";// has login success
    public static final String SP_KEY_IS_OPENED = "is_opened";// has open app before

    private AppSPUtils() {
        context = BaseApp.getApp();
    }

    public static AppSPUtils getSPInstance() {
        return ins;
    }

    public void saveIsLoginToSp(boolean isFirstLogin) {
        TinySpUtils.put(context, SP_CONFIG, SP_KEY_IS_LOGIN, isFirstLogin);
    }

    public boolean readIsLoginFromSp() {
        return (boolean)TinySpUtils.get(context, SP_CONFIG, SP_KEY_IS_LOGIN, false);
    }

    public void saveIsOpenedToSp(boolean isFirstOpen) {
        TinySpUtils.put(context, SP_CONFIG, SP_KEY_IS_OPENED, isFirstOpen);
    }

    public boolean readIsOpenedFromSp() {
        return (boolean)TinySpUtils.get(context, SP_CONFIG, SP_KEY_IS_OPENED, false);
    }

    public void clearSpConfigAll() {
        TinySpUtils.clear(context, SP_CONFIG);
    }


}
