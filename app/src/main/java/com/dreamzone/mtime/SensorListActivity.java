package com.dreamzone.mtime;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamzone.mtime.base.BaseActivity;

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
    @Bind(R.id.tv_sensor)
    TextView tvSensor;

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
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);// 获得传感器列表
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get_sensor) {
            getAllSensors();
        }
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
