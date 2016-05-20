package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamzone.mtime.base.BaseActivity;
import com.matrix.appsdk.BaseApp;
import com.matrix.appsdk.utils.Tools;
import com.matrix.appsdk.widget.FlexibleToast;
import com.matrix.appsdk.widget.ToastWithTwoText;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.hardware.Sensor.TYPE_PROXIMITY;

/**
 * @author BMR
 * @ClassName: SensorListActivity
 * @Description: 列出该手机所有的传感器信息
 * @date 2016/3/2 20:34
 */
public class SensorListActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btn_get_sensor)
    Button btnGetSensor;
    @Bind(R.id.btn_get_toast)
    Button btnToast;
    @Bind(R.id.tv_sensor)
    TextView tvSensor;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    private SensorManager sensorManager;
    private StringBuffer sb;
    private List<Sensor> sensorList;
    private Sensor sensor;

    public static void start(Context context) {
        Intent intent = new Intent(context, SensorListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);
        ButterKnife.bind(this);
        btnGetSensor.setOnClickListener(this);
        btnToast.setOnClickListener(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);// 获得传感器列表
        int[] sSensor = Tools.measureView(btnGetSensor, 1, Tools.dip2px(this, 200), Tools.dip2px(this, 50));
        int[] sToast = Tools.measureView(btnToast, 0, 0, 0);
        Log.e("TEST", "sSensor width= " + sSensor[0] + ", height=" + sSensor[1]);
        Log.e("TEST", "sToast width= " + sToast[0] + ", height=" + sToast[1]);

        llRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Log.e("TEST", "btnGetSensor width= " + btnGetSensor.getWidth() + ", height=" + btnGetSensor.getHeight());
                Log.e("TEST", "btnToast width= " + btnToast.getWidth() + ", height=" + btnToast.getHeight());
                if (btnGetSensor.getWidth() > 0) {
                    removeOnGlobalLayoutListener(llRoot, this);
                }
            }
        });
    }

    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get_sensor) {
            getAllSensors();
            showToastTwo();
        } else if (v.getId() == R.id.btn_get_toast) {
            showSnackbar(v);
        }
    }

    /**
     * 这种用法会Crash，用的是当前Activity的context初始化
     */
    private void showToastThreadCrash() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ToastWithTwoText.createToastConfig(SensorListActivity.this).toastShow("thread", "SensorListActivity");
            }
        }).start();

    }

    private void showToastThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FlexibleToast.Builder builder = new FlexibleToast.Builder(SensorListActivity.this).setGravity(FlexibleToast
                        .GRAVITY_TOP).
                        setFirstText("first").setSecondText("second=" + System.currentTimeMillis());
                BaseApp.getApp().toastShowByBuilder(builder);
            }
        }).start();

    }


    private void showToastTwo() {
        View toastView = LayoutInflater.from(this).inflate(R.layout.layout_toast_with_two_text, null, false);
        TextView tvOne = (TextView) toastView.findViewById(R.id.tv_text_one);
        TextView tvTwo = (TextView) toastView.findViewById(R.id.tv_text_two);
        tvOne.setText("customer one");
        tvTwo.setText("customer two");
        FlexibleToast.Builder builder = new FlexibleToast.Builder(this).setCustomerView(toastView);
        BaseApp.getApp().toastShowByBuilder(builder);

    }

    private void showSnackbar(View view){
        Snackbar.make(view, "Simple Demo will continue to update...", Snackbar.LENGTH_LONG).setAction
                ("Action", null).show();
    }

    private void getAllSensors() {
        sb = new StringBuffer();
        sb.append("该手机有" + sensorList.size() + "个传感器,分别是:\n\n");
        for (int i = 0; i < sensorList.size(); i++) {
            sensor = sensorList.get(i);
            switch (sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    sb.append(i + 1 + ". 加速度传感器");
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    sb.append(i + 1 + ". 温度传感器");
                    break;
                case Sensor.TYPE_GRAVITY:
                    sb.append(i + 1 + ". 重力传感器");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    sb.append(i + 1 + ". 陀螺仪传感器");
                    break;
                case Sensor.TYPE_LIGHT:
                    sb.append(i + 1 + ". 环境光线传感器");
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    sb.append(i + 1 + ". 线性加速度传感器");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    sb.append(i + 1 + ". 电磁场传感器");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    sb.append(i + 1 + ". 方向传感器");
                    break;
                case Sensor.TYPE_PRESSURE:
                    sb.append(i + 1 + ". 压力传感器");
                    break;
                case TYPE_PROXIMITY:
                    sb.append(i + 1 + ". 距离传感器");
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    sb.append(i + 1 + ". 湿度传感器");
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    sb.append(i + 1 + ". 旋转矢量传感器");
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    sb.append(i + 1 + ". 温度传感器");
                    break;
                case Sensor.TYPE_GAME_ROTATION_VECTOR:
                    sb.append(i + 1 + ". 游戏动作传感器");
                    break;
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                    sb.append(i + 1 + ". 地磁旋转矢量传感器");
                    break;
                case Sensor.TYPE_SIGNIFICANT_MOTION:
                    sb.append(i + 1 + ". 特殊动作触发传感器");
                    break;
                case Sensor.TYPE_STEP_COUNTER:
                    sb.append(i + 1 + ". 计步传感器");
                    break;
                case Sensor.TYPE_STEP_DETECTOR:
                    sb.append(i + 1 + ". 步行检测传感器");
                    break;
                default:
                    sb.append(i + 1 + ". 未知传感器");
                    break;
            }
            sb.append("\n");
            sb.append("设备名称:" + sensor.getName() + "\n");
            sb.append("设备版本:" + sensor.getVersion() + "\n");
            sb.append("通用类型号:" + sensor.getType() + "\n");
            sb.append("设备商名称:" + sensor.getVendor() + "\n");
            sb.append("传感器功耗:" + sensor.getPower() + "\n");
            sb.append("传感器分辨率:" + sensor.getResolution() + "\n");
            sb.append("传感器最大量程:" + sensor.getMaximumRange() + "\n");

            sb.append("\n\n");
        }
        tvSensor.setText(sb.toString());
    }
}
