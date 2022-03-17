package com.ut3.ballparty.game.threads;

import android.os.Handler;
import android.util.Log;

import com.ut3.ballparty.game.ObjectGenerator;
import com.ut3.ballparty.model.Grid;
import com.ut3.ballparty.model.GridObject;

import java.util.List;
import java.util.Random;

public class UpdateThread extends Thread {

    private Grid grid;
    private Handler updateHandler;
    private int updateTimer = 1000;
    private boolean generateFlag = true;
    private boolean running = false;

    private ObjectGenerator generator;

    public UpdateThread(Grid grid) {
        super();
        this.updateHandler = new Handler();
        this.grid = grid;
        this.generator = new ObjectGenerator();
    }

    private void createNewLine(List<GridObject> objects){
        int remainingCells = Grid.H_SIZE;
        int chanceToPlace = 100/remainingCells;
        Random rand = new Random();
        while (objects.size() != 0){
            int randomPos = rand.nextInt(100);
            if (randomPos < chanceToPlace || objects.size() == remainingCells){
                Log.d("GENERATE", "placing : " + (Grid.H_SIZE-remainingCells) +"," + 0);
                grid.add(objects.get(0), Grid.H_SIZE-remainingCells, 0);
                objects.remove(0);
            }
            remainingCells--;
            if (remainingCells > 0) chanceToPlace = 100/remainingCells;
        }
    }

    private Runnable doUpdate = new Runnable() {
        @Override
        public void run() {
            if (running){
                updateState();
                updateHandler.postDelayed(this, updateTimer);
            }
        }
    };


    private void updateState(){
        grid.tick();
        if (generateFlag) {
            createNewLine(generator.generate());
        }
        generateFlag = !generateFlag;

    }

    @Override
    public void run() {
        doUpdate.run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
