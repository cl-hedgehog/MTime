package com.matrix.appsdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.matrix.appsdk.common.AndroidLogger;
import com.trace.mtk.log.Logger;

import java.util.Locale;

public class DeviceUtils {
    
    public static String getBuildModel() {
        String model = Build.MODEL.toLowerCase(Locale.getDefault());
        return model;
    }
    
//    /**
//     * <pre>
//     * 获取设备ID
//     * </pre>
//     * @param context
//     * @return
//     */
//    public static String getDeviceId(Context context) {
//        String deviceID = BUserAppConfig.readDeviceId(context);
//        if (!Tools.isStrEmpty(deviceID)) {
//            return deviceID;
//        }
//        StringBuilder deviceId = new StringBuilder();
//        // 渠道标志
//        deviceId.append("d");
//        try {
//            // wifi mac地址
//            WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
//            if (wifi != null) {
//                WifiInfo info = wifi.getConnectionInfo();
//                if (info != null) {
//                    String wifiMac = info.getMacAddress();
//                    if (!Tools.isStrEmpty(wifiMac)) {
//                        deviceId.append("wifi");
//                        deviceId.append(wifiMac);
//                        BUserAppConfig.saveDeviceId(context, deviceId.toString());
//                        return deviceId.toString();
//
//                    }
//                }
//            } else {
//            }
//
//            // IMEI（imei）
//            TelephonyManager tm = (TelephonyManager)context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//            if (tm != null) {
//                String imei = tm.getDeviceId();
//                if (!Tools.isStrEmpty(imei)) {
//                    deviceId.append("imei");
//                    deviceId.append(imei);
//                    BUserAppConfig.saveDeviceId(context, deviceId.toString());
//                    return deviceId.toString();
//                }
//            }
//
//            // 序列号（sn）
//            if (tm != null) {
//                String sn = tm.getSimSerialNumber();
//                if (!Tools.isStrEmpty(sn)) {
//                    deviceId.append("sn");
//                    deviceId.append(sn);
//                    BUserAppConfig.saveDeviceId(context, deviceId.toString());
//                    return deviceId.toString();
//                }
//            }
//
//            // 如果上面都没有， 则生成一个id：随机码
//            String uuid = getUUID(context);
//            if (!Tools.isStrEmpty(uuid)) {
//                deviceId.append("id");
//                deviceId.append(uuid);
//                return deviceId.toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            deviceId.append("id").append(getUUID(context));
//        }
//
//        BUserAppConfig.saveDeviceId(context, deviceId.toString());
//        return deviceId.toString();
//    }
//
//    /**
//     * 得到全局唯一UUID
//     */
//    private static String getUUID(Context context) {
//        String uuid = BUserAppConfig.readUUId(context);
//        if (Tools.isStrEmpty(uuid)) {
//            uuid = UUID.randomUUID().toString();
//            BUserAppConfig.saveUUId(context, uuid);
//        }
//        return uuid;
//    }
    
    public static String getDeviceInfo(Context context) {
        
        TelephonyManager service = (TelephonyManager)context
            .getSystemService(Context.TELEPHONY_SERVICE);
        String seperator = "|";
        String imei = service.getDeviceId();
        
        StringBuffer sb = new StringBuffer();
        sb.append("IMEI=").append(imei).append(seperator).append("VERSION=")
            .append(getVersionName(context)).append(seperator).append("SNAME=")
            .append(service.getNetworkOperatorName()).append(seperator).append("DEVICE=")
            .append(Build.MODEL).append(seperator).append("OSVERSION=")
            .append(Build.VERSION.RELEASE);
        return sb.toString();
    }
    
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.beginError(AndroidLogger.TAG).p(e).end();
            return "0.0.0";
        }
    }
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.beginError(AndroidLogger.TAG).p(e).end();
            return 1;
        }
    }


    
    /**
     * <pre>
     *  获取网络类型
     * </pre>
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager)context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return "NETTYPE=" + info.getTypeName();
        } else {
            return "";
        }
    }

    /**
     * <pre>
     * 判断设备是否是中兴  U930
     * </pre>
     * @return
     */
    public static boolean isZTE_U930() {
        return Build.MODEL.toLowerCase(Locale.getDefault()).startsWith("zte u930");
    }

    public static boolean checkNetEnv(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile = null;
        NetworkInfo.State wifi = null;
        NetworkInfo mobileInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo != null) {
            mobile = mobileInfo.getState();
        }
        NetworkInfo wifiInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            wifi = wifiInfo.getState();
        }
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            return true;
        }
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            return true;
        }
        return false;
    }
}
