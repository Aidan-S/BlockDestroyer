package com.scannella.blockdestroyer;

import android.graphics.Canvas;


public class Ball {

    private double direction;

    public Ball(Canvas canvas, float x, float y) {
        direction = 270;
        canvas.drawCircle(srgsrzgaw);
    }

    public Ball(int r) {
        direction = r;
    }

    public void setDirection(int r) {
        direction = r;
    }
}
