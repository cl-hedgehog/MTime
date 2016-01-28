package com.dreamzone.mtime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dreamzone.mtime.base.AppContext;
import com.dreamzone.mtime.base.BaseActivity;
import com.matrix.appsdk.utils.Tools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 欢迎页面，有网络并且非首次登陆会跳转到可跳过的广告页面 跳转到主页面
 * app演示demo：
 * 1. app广告页面展示并带倒计时跳过
 * 2. MediaPlayer播放MP3文件
 * 3. 模拟远程服务的调用。需要安装RemoteService的app，地址：https://github.com/maoranbian/RemoteService
 *
 * 包含RecyclerView和CardView的简单使用
 * 包含简单的SharedPreferences的简单封装
 * 包含App常用工具类
 *
 * App地址：
 *
 * 计划更新更多MaterialDesign的示例。。。
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.tv_welcome)
    TextView tvWelcome;

    private String adImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        tvWelcome.setText(R.string.title_welcome);

        adImgUrl = "http://www.mangowed.com/uploads/allimg/131217/1-13121H04SSa.jpg";
        gotoNextPage();
    }

    private void gotoNextPage() {
        // skip the advertisement if noNetwork or adImgUrl is empty
        if (noNetWork() || Tools.isStrEmpty(adImgUrl)) {
            AppContext.getAppContext().getAppHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoMainActivity();
                }
            }, 2000);
        } else {
            AppContext.getAppContext().getAppHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoAdActivity();
                }
            }, 2000);
        }

    }

    private void gotoAdActivity() {
        AdvertisementActivity.start(this, adImgUrl);
        finish();
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
