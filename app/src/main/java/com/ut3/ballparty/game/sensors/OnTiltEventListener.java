package com.ut3.ballparty.game.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.util.Log;

import com.ut3.ballparty.model.Grid;

public class OnTiltEventListener implements SensorEventListener {

    private boolean tiltFlag;
    private Handler tiltHandler;
    private final Grid grid;

    private final int TILT_THRESHOLD = 8;
    private final int TILT_DELAY = 10000;

    public OnTiltEventListener(Grid grid){
        //Accelerometre
        tiltFlag = true;
        tiltHandler = new Handler();
        this.grid = grid;
    }

    private final Runnable mUpdateSwipTask = new Runnable(){
        public void run(){
            tiltFlag = true;
        }
    };

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(tiltFlag) {
            int sensor = sensorEvent.sensor.getType();
            float[] values = sensorEvent.values;

            synchronized (this) {
                if (sensor == Sensor.TYPE_ACCELEROMETER) {
                    float x = values[0];
                    //float y = values[1];
                    //float z = values[2];
                    //Log.d("onSensorChanged", " x= " + x + " y= " + y + " z= " + z );
                    if ((int) x > TILT_THRESHOLD) {
                        tiltFlag = false;
                        tiltHandler.postDelayed(mUpdateSwipTask, TILT_DELAY);
                        Log.d("onSensorChanged", " x= " + x + " on a penché vers la gauche");
                        grid.moveAllLeft();
                    } else if ((int) x < 0 - TILT_THRESHOLD) {
                        tiltFlag = false;
                        tiltHandler.postDelayed(mUpdateSwipTask, TILT_DELAY);
                        Log.d("onSensorChanged", " x= " + x + " on a penché vers la droite");
                        grid.moveAllRight();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
