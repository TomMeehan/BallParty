package com.ut3.ballparty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ut3.ballparty.game.GameActivity;

public class MainActivity extends Activity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.img);

        TextView scoreValue = findViewById(R.id.scoreValue);
        SharedPreferences sharedScore = this.getPreferences(Context.MODE_PRIVATE);
        int score = sharedScore.getInt("score",0);
        scoreValue.setText(String.valueOf(score));

    }

    public void startGame(View view) {
        //ICI CHANGER ACTIVITE
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}