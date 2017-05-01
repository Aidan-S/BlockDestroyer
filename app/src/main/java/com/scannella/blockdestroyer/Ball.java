package com.scannella.blockdestroyer;

import android.graphics.Canvas;



/**
 * Created by scannella on 4/5/2017.
 */

public class Ball {

    private double direction;

    public Ball(Canvas canvas) {
        direction = 270;
        canvas.drawCircle(x, y, radius, paint);
    }

    public Ball(double r) {
        direction = r;
    }

    public void setDirection(double r) {
        direction = r;
    }
}
