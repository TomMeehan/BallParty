package com.ut3.ballparty.game;

import com.ut3.ballparty.R;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.jouer);
        setContentView(new GameView(this));
    }
}