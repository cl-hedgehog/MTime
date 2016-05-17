package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.dreamzone.mtime.base.BaseActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpActivity extends BaseActivity {
    private TextView tvContentIn;
    private TextView tvContentBaidu;
    private OkHttpClient client = new OkHttpClient();
    private final String urlIn = "https://passport.in66.com/";
    private final String urlBaidu = "https://www.baidu.com/";
    private final String urlSolo = "https://www.solo-cloud.com/api/";
    private String strContentIn;
    private String strContentBaidu;

    private Handler handler;
    private final String TAG = "MTIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        tvContentIn = (TextView) findViewById(R.id.tv_content_in);
        tvContentBaidu = (TextView) findViewById(R.id.tv_content_baidu);
        handler = new Handler();
        initDataIn();
        initDataBaidu();

    }


    public static void start(Context context) {
        Intent intent = new Intent(context, OkHttpActivity.class);
        context.startActivity(intent);
    }

    private void initDataIn() {
        final Request requestIn = new Request.Builder().url(urlIn).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(requestIn).execute();
                    if (response.isSuccessful()) {
                        strContentIn = response.body().string();
                        Log.d(TAG, "strContentIn Success!");
                    } else {
                        strContentIn = "In request error!";
                        Log.d(TAG, "strContentIn Failure!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    strContentIn = "In IOException= " + e.getMessage();
                    Log.d(TAG, "strContentIn IOException!");
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvContentIn.setText(strContentIn);
                    }
                });
            }
        }).start();


    }

    private void initDataBaidu() {
        final Request requestBaidu = new Request.Builder().url(urlSolo).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(requestBaidu).execute();
                    if (response.isSuccessful()) {
                        strContentBaidu = response.body().string();
                        Log.d(TAG, "strContentBaidu Successs!");
                    } else {
                        strContentBaidu = "baidu request error!";
                        Log.d(TAG, "strContentBaidu Failure!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    strContentIn = "In IOException= " + e.getMessage();
                    Log.d(TAG, "strContentBaidu IOException!");
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvContentBaidu.setText(strContentBaidu);
                    }
                });
            }
        }).start();

    }

}
