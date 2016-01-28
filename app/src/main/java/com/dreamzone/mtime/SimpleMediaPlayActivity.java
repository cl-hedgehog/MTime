package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.dreamzone.mtime.base.AppContext;
import com.dreamzone.mtime.base.BaseActivity;
import com.matrix.appsdk.common.AndroidLogger;
import com.matrix.appsdk.widget.AutoBgImageView;
import com.trace.mtk.log.Logger;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author BMR
 * @ClassName: SimpleMediaPlayActivity
 * @Description: 利用MediaPlayer的简单的MP3播放Demo。退出界面停止播放
 * @date 2016/1/10 16:14
 */
public class SimpleMediaPlayActivity extends BaseActivity implements View.OnClickListener {


    private static MediaPlayer mp = null;
    @Bind(R.id.aiv_img)
    AutoBgImageView aivImg;
    @Bind(R.id.ll_play)
    LinearLayout llPlay;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_mediaplayer);
        ButterKnife.bind(this);

        llPlay.setOnClickListener(this);
        aivImg.setOnClickListener(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SimpleMediaPlayActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_play:
                startBgPlay();
                break;
            case R.id.aiv_img:
                AppContext.getAppContext().showtoast("image click");
                Logger.beginInfo(AndroidLogger.TAG).p("on click").end();
                break;
        }

    }

    private void startBgPlay() {
        llPlay.setEnabled(false);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                playBGFromRaw();
            }
        });
        thread.start();
    }


    /**
     * 从app自带的资源文件中播放mp3
     */
    private void playBGFromRaw() {
        if (mp != null) {
            mp.release();
        }
        // 从资源文件中创建
        mp = MediaPlayer.create(this, R.raw.knbks);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    // 音乐播放结束后单曲循环，10s后自动播放
                    Thread.sleep(10000);
                    playBGFromRaw();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 从本地文件中读取mp3并播放
     */
    private void playBGFromSDFile() {
        if (mp != null) {
            mp.release();
        }
        mp = new MediaPlayer();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/可念不可说.mp3";
        try {
            mp.setDataSource(path);
            mp.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    Thread.sleep(10000);
                    playBGFromSDFile();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

        if (thread != null) {
            thread = null;
        }
        super.onDestroy();
    }
}
