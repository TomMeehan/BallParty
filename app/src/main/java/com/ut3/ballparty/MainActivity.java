package com.ut3.ballparty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame();
    }

    public void startGame(){
        //ICI CHANGER ACTIVITE
        Intent intent = new Intent(this, MicTestActivity.class);
        startActivity(intent);
    }
}