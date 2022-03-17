package com.ut3.ballparty.model;

public class Bonus extends GridObject {
    private int score;

    public Bonus(){}

    public Bonus(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
