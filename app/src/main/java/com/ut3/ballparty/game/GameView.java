package com.ut3.ballparty.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.ut3.ballparty.game.threads.DrawThread;
import com.ut3.ballparty.game.threads.UpdateThread;
import com.ut3.ballparty.model.Grid;
import com.ut3.ballparty.model.GridObject;
import com.ut3.ballparty.model.exceptions.PositionException;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback{

    private DrawThread drawThread;
    private UpdateThread updateThread;

    private Grid grid;

    public GameView(Context context) {
        super(context);

        //Surface
        getHolder().addCallback(this);
        setFocusable(true);

        //Game Grid
        this.grid = new Grid();

        //Threads
        drawThread = new DrawThread(getHolder(), this);
        updateThread = new UpdateThread(this);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            Log.d("GAME", "Canvas is drawing");
            canvas.drawColor(Color.WHITE);
            drawGrid(canvas);
        }
    }

    private void drawGrid(Canvas canvas) {
        for(int i = 0; i < Grid.H_SIZE; i++){
            for (int j = 0; j < Grid.V_SIZE; j++){
                try{
                    GridObject obj = grid.get(i,j);
                    if (obj != null){
                        Paint paint = new Paint();
                        paint.setColor(obj.getColor());
                        //canvas.drawRect(x, y, x+100, y+100, paint);
                    }
                } catch (PositionException e){
                    Log.d("POSITION", "Impossible de récupérer l'objet aux coordonnées " + i + "," + j);
                }
            }
        }
    }

    public void update(){
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.start();
        updateThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                updateThread.setRunning(false);
                updateThread.join();
                drawThread.setRunning(false);
                drawThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}
