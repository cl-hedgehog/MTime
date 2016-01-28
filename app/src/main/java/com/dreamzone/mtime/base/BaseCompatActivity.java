package com.dreamzone.mtime.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.dreamzone.mtime.R;
import com.dreamzone.mtime.common.ActivityStack;
import com.matrix.appsdk.widget.dialog.ProcessDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * @author BMR
 * @ClassName: BaseCompatActivity
 * @Description: 透明标题栏Activity的基类，包含设置透明标题栏的方法
 * @date 2015/9/26 18:46
 */
public class BaseCompatActivity extends AppCompatActivity {

    ProcessDialog process;
    private boolean isShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //initToolbar();
        ActivityStack.getIns().push(this);

    }

    /**
     * 设置透明标题栏,子类调用此函数
     */
    protected void initToolbar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 显示加载完成框
     *
     * @param resId       显示的文字
     * @param showProcess 是否显示progressBar的转圈
     */
    public void showProcessDialog(int resId, boolean showProcess) {
        if (process == null) {
            process = new ProcessDialog(this, getString(resId), false);
        } else {
            process.updateView(getString(resId), false);
        }
        if (!process.isShowing()) {
            if (!this.isFinishing()) {
                process.show();
            }
        }
    }


    /**
     * 显示loading框
     */
    public void showProcessDialog() {
        showProcessDialog(R.string.on_loading_text);
    }

    /**
     * 显示loading框
     *
     * @param resId 显示的文字
     */
    public void showProcessDialog(int resId) {
        showProcessDialog(getString(resId));
    }

    /**
     * 显示loading框
     *
     * @param msg 显示的文字内容
     */
    public void showProcessDialog(String msg) {
        if (process == null) {
            process = new ProcessDialog(this, msg);
        } else {
            process.updateView(msg);
        }
        if (!process.isShowing()) {
            if (!this.isFinishing()) {
                process.show();
            }
        }
    }

    /**
     * 关闭loading框
     */
    public void dissmissProcessDialog() {
        if (process != null && process.isShowing()) {
            process.dismiss();
        }
    }

    @Override
    public void finish() {
        dissmissProcessDialog();
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityStack.getIns().popup(this);
        dissmissProcessDialog();
    }


    public boolean checkNetEnv() {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

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
