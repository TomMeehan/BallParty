package com.ut3.ballparty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ut3.ballparty.game.GameActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void startGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}