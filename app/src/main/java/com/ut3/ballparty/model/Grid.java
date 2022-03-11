package com.ut3.ballparty.model;

import android.graphics.Color;
import android.util.Log;

import com.ut3.ballparty.model.exceptions.PositionException;
import com.ut3.ballparty.model.exceptions.UnhandledCollisionException;

public class Grid {

    public static final int H_SIZE = 3;
    public static final int V_SIZE = 6;

    private GridObject[][] grid = new GridObject[H_SIZE][V_SIZE];
    private int playerHPos = Position.CENTER;
    private final int playerVPos = V_SIZE-1;

    public Grid(){
        this.grid[playerHPos][playerVPos] = new Player(Color.rgb(0, 0, 0));
    }

    public GridObject get(int hPos, int vPos) {
        try {
            checkPos(hPos, vPos);
        } catch (PositionException e) {
            Log.d("POSITION", e.getMessage());
        }
        return grid[hPos][vPos];
    }

    public void add(GridObject object, int hPos, int vPos) {
        try {
            checkPos(hPos, vPos);
        } catch (PositionException e){
            Log.d("POSITION", e.getMessage());
        }
        this.grid[hPos][vPos] = object;
    }

    public GridObject remove(int hPos, int vPos) {
        try {
            checkPos(hPos, vPos);
        } catch (PositionException e) {
            Log.d("POSITION", e.getMessage());
        }
        GridObject removedObject = this.grid[hPos][vPos];
        this.grid[hPos][vPos] = null;
        return removedObject;
    }

    public void movePlayerLeft() {
        if (playerHPos != Position.LEFT){
            if (grid[playerHPos - 1][playerVPos] != null){
                try {
                    handleCollision(grid[playerHPos][playerVPos], grid[playerHPos - 1][playerVPos]);
                } catch (UnhandledCollisionException e) {
                    Log.d("COLLISION", e.getMessage());
                }
            }
        }
    }

    public void movePlayerRight(){
        if (playerHPos != Position.RIGHT){
            if (grid[playerHPos + 1][playerVPos] != null){
                try {
                    handleCollision(grid[playerHPos][playerVPos], grid[playerHPos + 1][playerVPos]);
                } catch (UnhandledCollisionException e) {
                    Log.d("COLLISION", e.getMessage());
                }
            }
        }
    }

    public void tick() {
        for (int i = 0; i < H_SIZE; i++){
            for (int j = V_SIZE-1; j >= 0; j--){
                if (!(grid[i][j] instanceof Player)){
                    if ( j == (V_SIZE-1)) {
                        remove(i,j);
                    } else {
                        moveDown(i,j);
                    }
                }
            }
        }
    }

    private void moveDown(int hPos, int vPos) {
        if (grid[hPos][vPos + 1] != null){
            try {
                handleCollision(grid[hPos][vPos + 1], grid[hPos][vPos]);
            } catch (UnhandledCollisionException e) {
                Log.d("COLLISION", e.getMessage());
            }
        } else {
            add(grid[hPos][vPos], hPos, vPos + 1);
            remove(hPos, vPos);
        }
    }

    private void handleCollision(GridObject player, GridObject object) throws UnhandledCollisionException {
        if (!(player instanceof  Player)) throw new UnhandledCollisionException("Collision between two objects.");
        if (object instanceof Bonus){
            ((Player) player).addScore(((Bonus) object).getScore());
        } else if (object instanceof Obstacle) {
            //GAME LOST
            Log.d("END", "you lost the game");
        }
    }

    private void checkPos(int hPos, int vPos) throws PositionException {
        if (hPos >= H_SIZE || hPos < 0) throw new PositionException("Object out of horizontal grid!");
        if (vPos >= V_SIZE || vPos < 0) throw new PositionException("Object out of vertical grid!");
    }
}
