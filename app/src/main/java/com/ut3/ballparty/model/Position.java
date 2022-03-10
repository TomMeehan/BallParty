package com.ut3.ballparty.model;

public class Position {

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    private int hPos;
    private int vPos;

    public Position(int hPos, int vPos) {
        this.hPos = hPos;
        this.vPos = vPos;
    }

    public int gethPos() {
        return hPos;
    }

    public void sethPos(int hPos) {
        this.hPos = hPos;
    }

    public int getvPos() {
        return vPos;
    }

    public void setvPos(int vPos) {
        this.vPos = vPos;
    }
}
