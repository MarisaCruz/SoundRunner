package com.example.goncalomatos.spotifysdktest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.widget.TextView;


public class RunActivity extends Activity implements SensorEventListener{

    private SensorManager sm = null;
    private StepDetector stepDetector;

    //timer for log
    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        stepDetector = new StepDetector();
        stepDetector.addStepListener();

        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 500);

        //Log.d("RunActivity", Double.toString(accelDetector.getMagnitude()));
    }



    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //Run Button Logic
                        TextView accelValue = (TextView) findViewById(R.id.acceleration);

                    }
                });
            }
        };
    }
}
