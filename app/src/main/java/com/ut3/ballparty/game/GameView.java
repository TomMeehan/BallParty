package com.ut3.ballparty.game;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.ut3.ballparty.game.threads.DrawThread;
import com.ut3.ballparty.game.threads.UpdateThread;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback{

    private DrawThread drawThread;
    private UpdateThread updateThread;

    public GameView(Context context) {
        super(context);

        //Surface
        getHolder().addCallback(this);
        setFocusable(true);

        //Threads
        drawThread = new DrawThread();
        updateThread = new UpdateThread();
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
