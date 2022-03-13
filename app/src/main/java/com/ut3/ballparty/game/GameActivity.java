package com.ut3.ballparty.game;

import com.ut3.ballparty.R;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class GameActivity extends Activity implements SensorEventListener {

    private SensorManager sm = null;
    private boolean flag;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.button);
        setContentView(new GameView(this));

        // gyro sensor
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        flag = true;
        mHandler = new Handler();
    }

    /*
    onResume et onStop :
    Ces deux méthodes (provenant de Activity) sont appelées respectivement
    à chaque fois que l'exécution de l'activité reprend ou est mise en pause
.
    On va s'abbonner à chaque reprise, et se désabonner lorsque notre application
    est mise en pause.

    Attention : L'ordre entre l'appel de la méthode 'super().onXxx()' est important!
    */

    @Override
    protected void onResume() {
        super.onResume();
        Sensor mMagneticField = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop(){
        super.onStop();
        sm.unregisterListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    private final Runnable mUpdateSwipTask = new Runnable(){
        public void run(){
            flag = true;
        }
    };

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(flag) {
            int sensor = sensorEvent.sensor.getType();
            float[] values = sensorEvent.values;

            synchronized (this) {
                if (sensor == Sensor.TYPE_ACCELEROMETER) {
                    float x = values[0];
                    //float y = values[1];
                    //float z = values[2];
                    //Log.d("onSensorChanged", " x= " + x + " y= " + y + " z= " + z );
                    if ((int) x > 8) {
                        flag = false;
                        mHandler.postDelayed(mUpdateSwipTask, 1000);
                        Log.d("onSensorChanged", " x= " + x + " on a penché vers la gauche");
                    } else if ((int) x < -8) {
                        flag = false;
                        mHandler.postDelayed(mUpdateSwipTask, 1000);
                        Log.d("onSensorChanged", " x= " + x + " on a penché vers la droite");
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}