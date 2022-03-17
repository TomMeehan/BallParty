package com.ut3.ballparty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MicTestActivity extends AppCompatActivity {

    private TextView dbText;
    private Button pitieStop;

    private MediaRecorder mediaRecorder;
    private File audioInput;

    private Handler handler;
    private Runnable dbThread = new Runnable() {
        @Override
        public void run() {
            dbText.setText("" + getAmplitude());
            handler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        audioInput = new File(getFilesDir() + "/ballparty.3gp");
        mediaRecorder.setOutputFile(audioInput.getAbsolutePath());
        try {
            mediaRecorder.prepare();
            Log.d("MicTestActivityStarted", "Media recorder prepared");
            mediaRecorder.start();
            Log.d("MicTestActivityStarted", "Media recorder started");
        } catch (Exception e) {
            Log.d("MicTestActivityStarted", "Error while preparing/starting MediaRecorder");
            Log.d("MicTestActivityStarted", e.getMessage());
        }

        setContentView(R.layout.activity_mic_test);

        dbText = ((TextView) this.findViewById(R.id.textView3));

        pitieStop = ((Button) this.findViewById(R.id.button2));
        pitieStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Log.d("MicTestActivityStop", "MediaRecorder stopped");
                if (audioInput.delete()) {
                    Log.d("MicTestActivityStop", "Audio input successfully deleted");
                } else {
                    Log.d("MicTestActivityStop", "AUDIO INPUT NOT DELETED PLS SEND HELP PL-");
                }
            }
        });

        handler = new Handler();
        handler.postDelayed(dbThread, 0);
    }

    private double getAmplitude() {
        if (mediaRecorder != null) {
            double amplitude = mediaRecorder.getMaxAmplitude();
            Log.d("MicTestActivity", "Media recorder not null, max amplitude = " + amplitude);
            return amplitude;
        } else {
            Log.d("MicTestActivity", "Media recorder is null or not started, returning 0");
            return 0;
        }
    }

}