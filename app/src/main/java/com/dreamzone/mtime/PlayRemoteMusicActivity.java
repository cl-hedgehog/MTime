package com.dreamzone.mtime;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dreamzone.mtime.base.BaseCompatActivity;
import com.dreamzone.remoteservice.IMusicControlService;
import com.matrix.appsdk.common.AndroidLogger;
import com.trace.mtk.log.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author BMR
 * @ClassName: PlayRemoteMusicActivity
 * @Description: 简单的远程Service调用Demo，跨进程调用远程函数控制MP3的播放暂停和停止，退出界面停止播放
 * @date 2016/1/27 10:57
 */
public class PlayRemoteMusicActivity extends BaseCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_play)
    ImageButton btnPlay;
    @BindView(R.id.btn_pause)
    ImageButton btnPause;
    @BindView(R.id.btn_stop)
    ImageButton btnStop;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_play_status)
    TextView tvPlayStatus;
    private IMusicControlService controlService;


    public static void start(Context context) {
        Intent intent = new Intent(context, PlayRemoteMusicActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_remote_music);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolbar();
        initAIDLProxy();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int addRes = 0;
                if (controlService != null) {
                    try {
                        // 测试远程服务带返回值的计算方法
                        addRes = controlService.remoteAdd(10, 3);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                Snackbar.make(view, "The Result of Remote add(10, 3) is = " + addRes, Snackbar.LENGTH_LONG).setAction
                        ("Action", null).show();
            }
        });

        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        btnPlay.setEnabled(true);
        btnPause.setEnabled(false);
        btnStop.setEnabled(false);

    }

    private void initAIDLProxy() {
        Intent intent = new Intent();
        // 通过intent-filter隐式启动remote service服务
        intent.setAction("com.dreamzone.remoteservice.MusicService");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            controlService = IMusicControlService.Stub.asInterface(service);
            Logger.beginInfo(AndroidLogger.TAG).p("onServiceConnected ok").end();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            controlService = null;
            Logger.beginInfo(AndroidLogger.TAG).p("onServiceDisconnected").end();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.beginInfo(AndroidLogger.TAG).p("onDestroy").end();
        if (controlService != null) {
            unbindService(serviceConnection);
            Logger.beginInfo(AndroidLogger.TAG).p("unbindService").end();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (controlService != null) {
                    try {
                        controlService.play();
                        Logger.beginInfo(AndroidLogger.TAG).p("controlService play").end();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    btnPlay.setEnabled(false);
                    btnPause.setEnabled(true);
                    btnStop.setEnabled(true);
                    tvPlayStatus.setText("播放中");
                }
                break;
            case R.id.btn_pause:
                if (controlService != null) {
                    try {
                        controlService.pause();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    btnPlay.setEnabled(true);
                    btnPause.setEnabled(true);
                    btnStop.setEnabled(false);
                    tvPlayStatus.setText("暂停中");
                }
                break;
            case R.id.btn_stop:
                if (controlService != null) {
                    try {
                        controlService.stop();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    btnPlay.setEnabled(true);
                    btnPause.setEnabled(false);
                    btnStop.setEnabled(false);
                    tvPlayStatus.setText("已停止");
                }
                break;
            default:
                break;

        }

    }
}

