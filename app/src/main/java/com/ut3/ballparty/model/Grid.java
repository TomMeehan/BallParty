package com.ut3.ballparty.model;

import com.ut3.ballparty.model.exceptions.PositionException;
import com.ut3.ballparty.model.exceptions.UnhandledCollisionException;

public class Grid {

    public static final int H_SIZE = 3;
    public static final int V_SIZE = 6;

    private GridObject[][] grid = new GridObject[H_SIZE][V_SIZE];
    private int playerPosition = Position.CENTER;

    public Grid(){
        this.grid[playerPosition][0] = new Player();
    }

    public GridObject get(int hPos, int vPos) throws PositionException {
        checkPos(hPos, vPos);
        return grid[hPos][vPos];
    }

    public void add(GridObject object, int hPos, int vPos) throws PositionException {
        checkPos(hPos, vPos);
        this.grid[hPos][vPos] = object;
    }

    public GridObject remove(int hPos, int vPos) throws PositionException {
        checkPos(hPos, vPos);
        GridObject removedObject = this.grid[hPos][vPos];
        this.grid[hPos][vPos] = null;
        return removedObject;
    }

    public void movePlayerLeft() throws UnhandledCollisionException {
        if (playerPosition != Position.LEFT){
            if (grid[playerPosition - 1][0] != null){
                handleCollision(grid[playerPosition][0], grid[playerPosition - 1][0]);
            }
        }
    }

    public void movePlayerRight() throws UnhandledCollisionException {
        if (playerPosition != Position.RIGHT){
            if (grid[playerPosition + 1][0] != null){
                handleCollision(grid[playerPosition][0], grid[playerPosition + 1][0]);
            }
        }
    }

    public void tick() throws PositionException, UnhandledCollisionException {
        for (int i = 0; i < H_SIZE; i++){
            for (int j = 0; j < V_SIZE; j++){
                if (!(grid[i][j] instanceof Player)){
                    if ( j == 0) {
                        remove(i,j);
                    } else {
                        moveDown(i,j);
                    }
                }
            }
        }
    }

    private void moveDown(int hPos, int vPos) throws PositionException, UnhandledCollisionException {
        if (grid[hPos][vPos - 1] != null){
            handleCollision(grid[hPos][vPos - 1], grid[hPos][vPos]);
        } else {
            add(grid[hPos][vPos], hPos, vPos - 1);
            remove(hPos, vPos);
        }
    }

    private void handleCollision(GridObject player, GridObject object) throws UnhandledCollisionException {
        if (!(player instanceof  Player)) throw new UnhandledCollisionException("Collision between two objects.");
        if (object instanceof Bonus){
            ((Player) player).addScore(((Bonus) object).getScore());
        } else if (object instanceof Obstacle) {
            //END GAME
        }
    }

    private void checkPos(int hPos, int vPos) throws PositionException {
        if (hPos >= H_SIZE || hPos < 0) throw new PositionException("Object out of horizontal grid!");
        if (vPos >= V_SIZE || vPos < 0) throw new PositionException("Object out of vertical grid!");
    }
}
