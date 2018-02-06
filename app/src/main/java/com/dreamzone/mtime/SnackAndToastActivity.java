package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamzone.mtime.base.BaseActivity;
import com.matrix.appsdk.BaseApp;
import com.matrix.appsdk.widget.FlexibleToast;
import com.matrix.appsdk.widget.ToastWithTwoText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author BMR
 * @ClassName: SnackAndToastActivity
 * @Description: 全局Toast和Snackbar的Demo
 * @date 2016/3/2 20:34
 */
public class SnackAndToastActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.btn_toast_global)
    Button btnToastGlobal;
    @BindView(R.id.btn_toast_local)
    Button btnToastLocal;
    @BindView(R.id.btn_snackbar_normal)
    Button btnSnackbarNormal;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.btn_snackbar_customer)
    Button btnSnackbarCustomer;
    @BindView(R.id.et_input)
    EditText etInput;


    public static void start(Context context) {
        Intent intent = new Intent(context, SnackAndToastActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar_toast);
        ButterKnife.bind(this);
        btnToastGlobal.setOnClickListener(this);
        btnToastLocal.setOnClickListener(this);
        btnSnackbarNormal.setOnClickListener(this);
        btnSnackbarCustomer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toast_global:
                showToastGlobal();
                //Snackbar.make(llRoot, "global", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.btn_toast_local:
                ToastWithTwoText.createToastConfig(SnackAndToastActivity.this).toastShow("local", "toast");
                //Snackbar.make(llRoot, "local", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.btn_snackbar_normal:
                showSnackbarCallback(btnSnackbarNormal);
                break;
            case R.id.btn_snackbar_customer:
                showSnackbarCustom();
                break;

        }
    }


    private void showToastCustomView() {
        View toastView = LayoutInflater.from(this).inflate(R.layout.layout_toast_with_two_text, null, false);
        TextView tvOne = (TextView) toastView.findViewById(R.id.tv_text_one);
        TextView tvTwo = (TextView) toastView.findViewById(R.id.tv_text_two);
        tvOne.setText("customer one");
        tvTwo.setText("customer two");
        FlexibleToast.Builder builder = new FlexibleToast.Builder(this).setCustomerView(toastView);
        BaseApp.getApp().toastShowByBuilder(builder);

    }

    private void showToastGlobal() {
        FlexibleToast.Builder builder = new FlexibleToast.Builder(SnackAndToastActivity.this).setGravity(FlexibleToast
                .GRAVITY_CENTER).setFirstText("Global");
        BaseApp.getApp().toastShowByBuilder(builder);

    }

    private void showSnackbarCustom() {
        Snackbar snackbar = Snackbar.make(llRoot, "Custom", Snackbar.LENGTH_SHORT);
        addCustomViewToSnackbar(snackbar, R.layout.layout_custom_snackbar, 0);
        snackbar.show();
    }

    public void addCustomViewToSnackbar(Snackbar snackbar, int layoutId, int index) {
        //获取snackbar的View(其实就是SnackbarLayout)
        View snackbarView = snackbar.getView();
        //将获取的View转换成SnackbarLayout
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        //加载布局文件新建View
        View customView = LayoutInflater.from(snackbarView.getContext()).inflate(layoutId, null);
        ImageView ivImg = (ImageView) customView.findViewById(R.id.iv_img);
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SnackAndToastActivity.this, "Custom image", Toast.LENGTH_LONG).show();
            }
        });
        //设置新建布局参数
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        //设置新建布局在Snackbar内垂直居中显示
        p.gravity = Gravity.CENTER_VERTICAL;
        //将新建布局添加进snackbarLayout相应位置
        snackbarLayout.addView(customView, index, p);
    }


    private void showSnackbarCallback(View view) {
        Snackbar snackbar = Snackbar.make(view, R.string.on_loading_text, Snackbar.LENGTH_LONG).setAction("cancel", new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastGlobal();
            }
        }).setCallback(callback);
        snackbar.show();
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color
                .colorAccent));
    }

    private Snackbar.Callback callback = new Snackbar.Callback() {
        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            super.onDismissed(snackbar, event);
            switch (event) {
                case DISMISS_EVENT_SWIPE:
                    etInput.setText("DISMISS_EVENT_SWIPE");
                    break;
                case DISMISS_EVENT_ACTION:
                    etInput.setText("DISMISS_EVENT_ACTION");
                    break;
                case DISMISS_EVENT_TIMEOUT:
                    etInput.setText("DISMISS_EVENT_TIMEOUT");
                    break;
                case DISMISS_EVENT_MANUAL:
                    etInput.setText("DISMISS_EVENT_MANUAL");
                    break;
                case DISMISS_EVENT_CONSECUTIVE:
                    etInput.setText("DISMISS_EVENT_CONSECUTIVE");
                    break;
            }

        }

        @Override
        public void onShown(Snackbar snackbar) {
            super.onShown(snackbar);
            etInput.setText("snackbar onShown");
        }
    };

    /**
     * 这种用法会Crash，用的是当前Activity的context初始化
     */
    private void showToastThreadCrash() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ToastWithTwoText.createToastConfig(SnackAndToastActivity.this).toastShow("thread", "SensorListActivity");
            }
        }).start();

    }

    private void showToastThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FlexibleToast.Builder builder = new FlexibleToast.Builder(SnackAndToastActivity.this).setGravity(FlexibleToast
                        .GRAVITY_TOP).
                        setFirstText("first").setSecondText("second=" + System.currentTimeMillis());
                BaseApp.getApp().toastShowByBuilder(builder);
            }
        }).start();

    }
}
