/*
 * Author: luotianjia
 * Date : 2015/01/06
 * All rights reserved.
 */
package com.matrix.appsdk.common;

import android.util.Log;

import com.trace.mtk.log.LogLevel;
import com.trace.mtk.log.Logger;
import com.trace.mtk.log.LoggerBase;

import java.io.File;
import java.io.IOException;

public class AndroidLogger extends LoggerBase
{
//    public static final long MAX_LOG_FILE_SIZE = 1024*1024;

    public static final String TAG = "MATRIX";
  
    private static final int LOGMAXSIZE = 1048576*5;

    public AndroidLogger()
    {
        this(null);
    }

    public AndroidLogger(String logfile)
    {
        this(logfile, LOGMAXSIZE);
    }
    
    public AndroidLogger(String logfile, long _maxSize)
    {
        super(logfile, _maxSize);
    }
    
    public boolean isLoggable(String tag, LogLevel level)
    {
        return isFileLoggable(tag, level) || isAndroidLoggable(tag, level);
    }

    private boolean isAndroidLoggable(String tag, LogLevel level)
    {
        return Log.isLoggable(tag, level.value());
    }
    
    public void write(Logger record)
    {
        String body = record.body();
        
        writeAndroidLog(record.tag(), record.level(), body);
        writeLogFile(record.tag(), record.level(), record.head() + body);
    }
    
    private void writeAndroidLog(String tag, LogLevel level, String info)
    {
        if (!isAndroidLoggable(tag, level))
            return ;
        switch(level.value())
        {
            case Log.VERBOSE:
                Log.v(tag, info);
                break;
            case Log.DEBUG:
                Log.d(tag, info);
                break;
            case Log.INFO:
                Log.i(tag, info);
                break;
            case Log.WARN:
                Log.w(tag, info);
                break;
            case Log.ERROR:
                Log.e(tag, info);
                break;
            default:
                Log.v(tag, "[" + level.toString() + "] " + info);
                break;
        }
    }


    public static void initLog(String path)
    {
        File file = new File(path);
        if (!(file.isDirectory() && file.exists()))
        {
            if(!file.mkdirs())
            {
                Logger.info(TAG, "Creat directory fail...");
            }
        }
        if(Logger.getLogger() == null)
        {
            Logger.setLogger(new AndroidLogger());
        }

        Logger.setMaxLogFileSize(LOGMAXSIZE);

        File f = new File(path + "/matrix.txt");

        if(!f.exists())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Logger.setLogFile(path + "/matrix.txt");
        Logger.setLogLevel(LogLevel.INFO);

    }


}

