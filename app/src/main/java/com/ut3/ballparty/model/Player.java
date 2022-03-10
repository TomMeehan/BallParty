package com.ut3.ballparty.model;

public class Player extends GridObject {

    private int score;

    public Player(){
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int scoreToAdd){
        this.score += scoreToAdd;
    }
}
