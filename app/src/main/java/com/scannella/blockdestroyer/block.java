package com.scannella.blockdestroyer;

import android.graphics.RectF;

public class block {

    private RectF rect;

    private boolean alive;

    public block(int width, int height, int row, int column){

        alive = true;

        rect = new RectF((column * width) + 1, (row * height) + 1, (column * width) + width - 1, (row * height) + height - 1);

    }

    public RectF getRect(){
        return this.rect;
    }

    public void hit(){
        alive = false;
    }

    public boolean getAlive(){
        return alive;
    }
}