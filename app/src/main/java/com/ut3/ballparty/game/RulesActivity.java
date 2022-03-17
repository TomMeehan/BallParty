package com.ut3.ballparty.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ut3.ballparty.MainActivity;
import com.ut3.ballparty.R;

public class RulesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }

    public void startRulesActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
