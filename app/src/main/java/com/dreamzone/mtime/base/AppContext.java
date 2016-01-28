package com.dreamzone.mtime.base;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dreamzone.mtime.R;
import com.dreamzone.mtime.common.CrashHandler;
import com.matrix.appsdk.BaseApp;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/**
 * @author BMR
 * @ClassName: AppContext
 * @Description: TODO:
 * @date 2015/12/27 10:48
 */
public class AppContext extends BaseApp {

    protected CrashHandler crashHandler = null;// uncatchException 处理
    private static AppContext ins;
    public final static String APP_PKG_NAME = "com.dreamzone.mtime";
    // 初始化volley请求队列
    public static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        if (!isMainProcess(APP_PKG_NAME)) {
            return;
        }

        if (crashHandler == null) {
            crashHandler = CrashHandler.getInstance();
            crashHandler.init(this);
        }
        initImageLoader(getApplicationContext());

        queues = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpQueues() {
        return queues;
    }


    private void setInstance(AppContext context) {
        ins = context;
    }

    public static AppContext getAppContext() {
        return ins;
    }

    public void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, youg
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread
                .NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new
                Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024).writeDebugLogs().tasksProcessingOrder
                (QueueProcessingType.LIFO).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * load advertisement image
     */
    public static final DisplayImageOptions adImageDisplayOptions = new DisplayImageOptions.Builder().showImageOnFail
            (R.drawable.img_ad_failed).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig
            (Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).build();
}
