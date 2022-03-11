package com.ut3.ballparty.game;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.ut3.ballparty.game.threads.DrawThread;
import com.ut3.ballparty.game.threads.UpdateThread;
import com.ut3.ballparty.model.CalculSwitchEvent;

import java.util.ArrayList;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback{

    private final DrawThread drawThread;
    private final UpdateThread updateThread;

    private final CalculSwitchEvent calculSwitchEvent;
    private ArrayList<Integer> listEvent;
    private int i;
    private boolean flag;
    private final Handler mHandler;

    public GameView(Context context) {
        super(context);

        //Surface
        getHolder().addCallback(this);
        setFocusable(true);

        //Threads
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
