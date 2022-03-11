package com.ut3.ballparty.model;

import android.graphics.Color;

public class Obstacle extends GridObject {

    private boolean destructible;

    public Obstacle(){}

    public Obstacle(int color, boolean destructible){
        super(color, Shape.SQUARE);
        this.destructible = destructible;
    }

    public boolean isDestructible() {
        return destructible;
    }

    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }
}
