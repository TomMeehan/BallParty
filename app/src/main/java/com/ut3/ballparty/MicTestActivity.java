package com.ut3.ballparty;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.io.IOException;

public class MicTestActivity extends AppCompatActivity {

    private TextView dbText;

    private double volume;
    private MediaRecorder mediaRecorder;

    private Handler handler;
    private Runnable thread = new Runnable() {
        @Override
        public void run() {
            dbText.setText("" + getAmplitude());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volume = 0;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("/dev/null");
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();

        setContentView(R.layout.activity_mic_test);

        dbText = ((TextView) this.findViewById(R.id.textView3));

        handler = new Handler();
        handler.postDelayed(thread, 0);
    }

    public double computeVolume() {
        if (mediaRecorder != null) {
            return 20 * Math.log10(this.getAmplitude());
        }
        return 0;
    }

    private double getAmplitude() {
        return mediaRecorder != null ? mediaRecorder.getMaxAmplitude() : 0;
    }

}