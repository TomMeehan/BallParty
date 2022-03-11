package com.ut3.ballparty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

public class MicTestActivity extends AppCompatActivity {

    private TextView dbText;

    private double volume;
    private MediaRecorder mediaRecorder;

    private Handler handler;
    private Runnable dbThread = new Runnable() {
        @Override
        public void run() {
            dbText.setText("" + computeVolume());
            handler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volume = 0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("${externalCacheDir.absolutePath}/test.3gp"); // "/dev/null" fait planter visiblement
        try {
            mediaRecorder.prepare();
            Log.d("MicTestActivityStarted", "Media recorder prepared I guess?");
            mediaRecorder.start();
            Log.d("MicTestActivityStarted", "Media recorder started I guess?");
        } catch (IOException e) {
            Log.d("MicTestActivityStarted", "HOLY SHIT BRO IT DIDN'T WORK");
            Log.d("MicTestActivityStarted", e.getMessage());
        }

        setContentView(R.layout.activity_mic_test);

        dbText = ((TextView) this.findViewById(R.id.textView3));

        handler = new Handler();
        handler.postDelayed(dbThread, 0);
    }

    public double computeVolume() {
        if (mediaRecorder != null) {
            double volume = 20 * Math.log10(this.getAmplitude());
            Log.d("MicTestActivity", "Volume: " + volume);
            return volume;
        }
        return 0;
    }

    private double getAmplitude() {
        if (mediaRecorder != null) {
            Log.d("MicTestActivity", "Media recorder not null, max amplitude = " + mediaRecorder.getMaxAmplitude());
            return mediaRecorder.getMaxAmplitude();
        } else {
            Log.d("MicTestActivity", "Media recorder is null or not started, returning 0");
            return 0;
        }
    }

}