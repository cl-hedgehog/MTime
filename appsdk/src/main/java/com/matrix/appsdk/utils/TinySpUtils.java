package com.matrix.appsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.matrix.appsdk.common.AndroidLogger;
import com.trace.mtk.log.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author BMR
 * @ClassName: TinySpUtils
 * @Description: 简单的SharedPreferences封装
 * ref:github willchyis/android-dev,kcochibili/TinyDB--Android-Shared-Preferences-Turbo
 * @date 2015/12/31 10:03
 */
public class TinySpUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static String FILE_NAME_DEFAULT = "share_file_default";

    private static SharedPreferences getSpByName(Context appContext, final String spFileName) {
        return appContext.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void put(Context context, String filename, String key, Object object) {
        SharedPreferences sp = getSpByName(context, filename);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(Context context, String key, Object object) {
        put(context, FILE_NAME_DEFAULT, key, object);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object get(Context context, String filename, String key, Object defaultObject) {
        if(key == null || defaultObject == null){
            Logger.beginError(AndroidLogger.TAG).p("key or value is empty ").end();
        }
        SharedPreferences sp = getSpByName(context, filename);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }


    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String filename, String key) {
        SharedPreferences sp = getSpByName(context, filename);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void remove(Context context, String key) {
        remove(context, FILE_NAME_DEFAULT, key);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context, String filename) {
        SharedPreferences sp = getSpByName(context, filename);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(Context context, String filename, String key) {
        SharedPreferences sp = getSpByName(context, filename);
        return sp.contains(key);
    }

    public static boolean contains(Context context, String key) {
        return contains(context, FILE_NAME_DEFAULT, key);
    }


    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context, String filename) {
        SharedPreferences sp = getSpByName(context, filename);
        return sp.getAll();
    }

    public static Map<String, ?> getAll(Context context) {
        return getAll(context, FILE_NAME_DEFAULT);
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     * @param  key
     */
    public void checkForNullKey(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }


    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     * @param  value
     */
    public void checkForNullValue(String value){
        if (value == null){
            throw new NullPointerException();
        }
    }

}
