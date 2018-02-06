package com.dreamzone.mtime.common;

import android.content.Context;
import android.util.Log;

/**
 * @author bohe
 * @ClassName: TestSingle
 * @Description:
 * @date 2018/1/19 下午9:09
 */
public class TestSingle {
    private Context mContext;
    private static TestSingle test;
    private TestSingle(Context context){
        this.mContext = context;
        Log.e("MTime", "contxt = " + context.getClass().getSimpleName());
    }

    public static TestSingle getTestSingle(Context context){
        if(test == null){
            synchronized (TestSingle.class){
                if(test == null){
                    test = new TestSingle(context);
                }
            }
        }
        return test;
    }

}
