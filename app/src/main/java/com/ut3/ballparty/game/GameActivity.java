package com.ut3.ballparty.game;

import android.app.Activity;
import android.os.Bundle;

import com.ut3.ballparty.R;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}
