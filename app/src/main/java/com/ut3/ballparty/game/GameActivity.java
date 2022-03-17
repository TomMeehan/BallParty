package com.ut3.ballparty.game;

import com.ut3.ballparty.R;
import android.app.Activity;
import android.graphics.Point;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Display;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.File;

public class GameActivity extends Activity  {

    private SensorManager sm = null;
    GameView gameView;

    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        gameView = new GameView(this, size);
        setContentView(gameView);
        
        // gyro sensor
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
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
        gameView.initializeSensors(sm);
    }

    @Override
    protected void onStop(){
        gameView.stopSensors(sm);
        super.onStop();
    }




}