package com.dreamzone.mtime.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.dreamzone.mtime.R;
import com.dreamzone.mtime.common.ActivityStack;
import com.matrix.appsdk.utils.DeviceUtils;
import com.matrix.appsdk.widget.dialog.ProcessDialog;


/**
 * @author BMR
 * @ClassName: BaseActivity
 * @Description: 普通Activity的基类
 * @date 2015/9/26 18:46
 */
public class BaseActivity extends FragmentActivity {

    ProcessDialog process;
    private boolean isShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityStack.getIns().push(this);
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


    public boolean noNetWork(){
        return  !DeviceUtils.checkNetEnv(this);
    }
}
