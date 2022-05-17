package com.example.wearoshyroscope;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.wearoshyroscope.databinding.ActivityMainBinding;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    SensorManager mSensorManager;
    Sensor mAccelerometerSensor;
    Sensor mMagneticFieldSensor;
    TextView mXValueText;
    TextView mYValueText;
    TextView mZValueText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        if (sensors.size() > 0){
            for (Sensor sensor : sensors){
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    if (mAccelerometerSensor == null)
                        mAccelerometerSensor = sensor;
                }
            }
        }
        mXValueText = findViewById(R.id.value_x);
        mYValueText = findViewById(R.id.value_y);
        mZValueText = findViewById(R.id.value_z);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                mXValueText.setText(String.format("%1.3f", sensorEvent.values[SensorManager.DATA_X]));
                mYValueText.setText(String.format("%1.3f", sensorEvent.values[SensorManager.DATA_Y]));
                mZValueText.setText(String.format("%1.3f", sensorEvent.values[SensorManager.DATA_Z]));
            } break;
        }
    }

    @Override
    protected void onPause(){
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometerSensor,
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagneticFieldSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

