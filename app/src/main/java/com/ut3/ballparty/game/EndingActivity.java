package com.ut3.ballparty.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ut3.ballparty.MainActivity;
import com.ut3.ballparty.R;

public class EndingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences sharedScore = this.getPreferences(Context.MODE_PRIVATE);
        int score = sharedScore.getInt("score",0);
        TextView textView = (TextView) findViewById(R.id.score_textview);
        textView.setText(String.valueOf(score));

        setContentView(R.layout.activity_ending);
    }

    public void toMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}