package com.matrix.appsdk;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.matrix.appsdk.common.AndroidLogger;
import com.matrix.appsdk.utils.DeviceUtils;
import com.matrix.appsdk.widget.FlexibleToast;

/**
 * @author BMR
 * @ClassName: BaseApp
 * @Description: TODO:
 * @date 2016/1/10 13:30
 */
public class BaseApp extends Application {

    public final static String SD_APP_DIR = "/com.dreamzone.mtime/";
    public final static String APP_PKG_NAME = "com.dreamzone.mtime";
    public static String FILE_APP_DIR;
    public static String sdCardRootDir;
    private static BaseApp instance;
    /**
     * 全局的 handler 对象
     */
    private final Handler APPHANDLER = new Handler();
    /**
     * 全局的 Toast 对象
     */
    private FlexibleToast flexibleToast;

    public static void setInstance(BaseApp instance) {
        BaseApp.instance = instance;
    }

    public static BaseApp getApp() {
        return instance;
    }

    public Handler getAppHandler() {
        return APPHANDLER;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        initDir();
        initLog();
        if (!isMainProcess(APP_PKG_NAME)) {
            return;
        }
        initCommon();
    }

    private void initDir() {
        if (DeviceUtils.isZTE_U930()) {
            sdCardRootDir = "/mnt/sdcard-ext";
        } else {
            sdCardRootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        FILE_APP_DIR = sdCardRootDir + SD_APP_DIR;
    }

    /**
     * <pre>
     * 初始化 App 相关的数据
     * </pre>
     */
    private void initCommon() {
        flexibleToast = new FlexibleToast(this);
    }

    protected boolean isMainProcess(String appPkgName) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                if (appProcess.processName.equals(appPkgName)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void initLog() {
        AndroidLogger.initLog(FILE_APP_DIR);
    }

    public void toastShowByBuilder(final FlexibleToast.Builder builder) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            getAppHandler().post(new Runnable() {
                @Override
                public void run() {
                    flexibleToast.toastShow(builder);
                }
            });
        } else {
            flexibleToast.toastShow(builder);
        }
    }


}
