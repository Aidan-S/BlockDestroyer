package com.scannella.blockdestroyer;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by scannella on 4/27/2017.
 */

public class GestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    PlayAreaView view;

    public GestureListener(PlayAreaView view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //Log.v(DEBUG_TAG, "onDown");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //Log.v(DEBUG_TAG, "onScroll");

        view.onMove(-distanceX, -distanceY);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, final float velocityX, final float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {return false;}

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
