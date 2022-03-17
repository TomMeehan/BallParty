package com.ut3.ballparty.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.ut3.ballparty.game.threads.DrawThread;
import com.ut3.ballparty.game.threads.UpdateThread;
import com.ut3.ballparty.model.Grid;
import com.ut3.ballparty.model.GridObject;
import com.ut3.ballparty.model.Obstacle;
import com.ut3.ballparty.model.Player;
import com.ut3.ballparty.model.exceptions.PositionException;
import com.ut3.ballparty.model.CalculSwitchEvent;

import java.util.ArrayList;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private final DrawThread drawThread;
    private final UpdateThread updateThread;

    private final CalculSwitchEvent calculSwitchEvent;
    private ArrayList<Integer> listEvent;
    private int i;
    private boolean flag;
    private final Handler mHandler;

    private Point windowSize;
    private int cellWidth;
    private int cellHeight;

    private Grid grid;

    private Handler tickHandler;
    private int tickTimer = 1000;

    public GameView(Context context, Point windowSize) {
        super(context);

        this.windowSize = windowSize;
        this.cellWidth = windowSize.x/Grid.H_SIZE;
        this.cellHeight = windowSize.y/Grid.V_SIZE;

        //Surface
        getHolder().addCallback(this);
        setFocusable(true);

        //Game Grid
        this.grid = new Grid();

        this.grid.add(new Obstacle(Color.rgb(250, 0, 0), false), Grid.H_SIZE-1, 0);

        //Threads
        drawThread = new DrawThread(getHolder(), this);
        updateThread = new UpdateThread(this);

        //Tick Handler
        tickHandler = new Handler();
        tickHandler.postDelayed(doTick, tickTimer);
    }

    private Runnable doTick = new Runnable() {
        @Override
        public void run() {
            grid.tick();
            tickHandler.postDelayed(this, tickTimer);
        }
    };

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
            for (int j = Grid.V_SIZE-1; j >= 0 ; j--){
                GridObject obj = grid.get(i,j);
                if (obj != null){
                    Paint paint = new Paint();
                    paint.setColor(obj.getColor());
                    switch (obj.getShape()){
                        case CIRCLE:
                            canvas.drawCircle((i*cellWidth)+(cellWidth/2), (j*cellHeight)+(cellHeight/2), cellWidth/4, paint);
                            break;
                        case SQUARE:
                            canvas.drawRect((i*cellWidth), (j*cellHeight), (i*cellWidth)+cellWidth, (j*cellHeight)+cellHeight, paint);
                            break;
                    }
                }
            }
        }
    }

    public void update(){
        drawThread = new DrawThread();
        updateThread = new UpdateThread();

        //onTouch Swap
        calculSwitchEvent = new CalculSwitchEvent();
        i = 0;
        flag = true;
        mHandler = new Handler();


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

    private final Runnable mUpdateSwipTask = new Runnable(){
        public void run(){
            flag = true;
        }
    };

    @Override
    public boolean onTouchEvent (MotionEvent event){
        if(flag){
            int action = event.getActionMasked();
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    i = 0;
                    listEvent = new ArrayList<>();
                case MotionEvent.ACTION_MOVE:
                    i++;
                    listEvent.add((int)event.getX());
                    if(i>10) {
                        flag = false;
                        mHandler.postDelayed(mUpdateSwipTask, 300);
                        int direction = calculSwitchEvent.calculateDirection(listEvent);
                        Log.d("MOVE switch direction", String.valueOf(direction));
                    }
            }

        }

        return true;
    }



}
