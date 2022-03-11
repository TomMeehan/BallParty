package com.ut3.ballparty.model;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class CalculSwitchEvent {


    public CalculSwitchEvent() {
    }

    public int calculateDirection(ArrayList<Integer> listEvent) {
        int retour = 1;
        Log.d("calcule", " 1 er : "+listEvent.get(0)+ " 2e : "+listEvent.get(listEvent.size()-1));
        int res = listEvent.get(0) - listEvent.get(listEvent.size()-1);
        // si 1er - 2iem > 0, on switch vers la gauche sinon l'inverse
        if(res > 0 ){
            retour = 0;
        }else if (res < 0){
            retour = 1;
        }
        return retour;
    }
}
