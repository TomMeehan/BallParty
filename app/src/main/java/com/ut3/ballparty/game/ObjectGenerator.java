package com.ut3.ballparty.game;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;

import com.ut3.ballparty.model.Bonus;
import com.ut3.ballparty.model.Grid;
import com.ut3.ballparty.model.GridObject;
import com.ut3.ballparty.model.Obstacle;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ObjectGenerator {

    private static final int TWO_OBJECTS_THRESHOLD = 40;
    private static final int THREE_OBJECTS_THRESHOLD = 90;
    private static final int IS_BONUS_THRESHOLD = 90;
    private static final int IS_DESTRUCTIBLE_THRESHOLD = 80;

    private final int WALL_CD = 15000;

    private boolean canGenerateThree = true;
    private Handler wallHandler;

    public ObjectGenerator(){
        this.wallHandler = new Handler();
    }


    private Runnable wallCDReset = new Runnable() {
        @Override
        public void run() {
            canGenerateThree = true;
        }
    };

    public List<GridObject> generate(){

        Random rand = new Random();
        int randomInt = rand.nextInt(100) + 1;
        int nObjects = 1;
        if (randomInt > THREE_OBJECTS_THRESHOLD && canGenerateThree) { // THREE OBJECTS SPAWNED
            nObjects = 3;
            this.canGenerateThree = false;
            wallHandler.postDelayed(wallCDReset, WALL_CD);
        } else if (randomInt > TWO_OBJECTS_THRESHOLD) { // TWO OBJECTS SPAWNED
            nObjects = 2;
        }

        List<GridObject> generatedObjects = new LinkedList<>();

        for (int i = 0; i < nObjects; i++){
            GridObject object;
            int bonusChance = rand.nextInt(100) + 1;
            if (bonusChance > IS_BONUS_THRESHOLD){
                object = new Bonus(1000);
            } else {
                int destructibleChance = rand.nextInt(100) + 1;
                if (destructibleChance > IS_DESTRUCTIBLE_THRESHOLD){
                    object = new Obstacle(true);
                } else {
                    object = new Obstacle(false);
                }
            }
            generatedObjects.add(object);
        }

        return generatedObjects;
    }
}
