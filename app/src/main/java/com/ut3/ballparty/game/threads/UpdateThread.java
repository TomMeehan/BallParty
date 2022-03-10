package com.ut3.ballparty.game.threads;

import android.os.Handler;

import com.ut3.ballparty.game.GameView;

public class UpdateThread extends Thread {

    private boolean running;
    private GameView gameView;
    private Handler updateHandler;
    private int updateTimer = 1000/20;

    public UpdateThread(GameView gameView) {
        super();
        this.updateHandler = new Handler();
        this.gameView = gameView;
    }

    private Runnable doUpdate = new Runnable() {
        @Override
        public void run() {
            updateState();
            updateHandler.postDelayed(this, updateTimer);
        }
    };



    private void updateState(){
        this.gameView.update();
    }

    @Override
    public void run() {
        doUpdate.run();
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
