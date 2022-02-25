package com.ut3.ballparty.game.threads;

public class UpdateThread extends Thread {

    private boolean running;

    @Override
    public void run(){

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
