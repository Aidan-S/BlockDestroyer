package com.scannella.blockdestroyer;

import android.graphics.RectF;


public class Paddle {

    // get a rectangle object
    private RectF rect;

    // paddle height and width
    private float width;
    private float height;

    // left side of paddle
    private float side;

    // top of paddle
    private float top;

    //paddle speed
    private float paddleSpeed;

    //Ways paddle can move
    public final int STOPPED = 0;
    public final int RIGHT = 1;
    public final int LEFT = 2;

    //Direction the paddle is moving
    private int paddleDirection = STOPPED;

    //Constructor
    public Paddle(int screenX, int screenY) {
        width = 130;
        height = 20;

        //put paddle in the middle
        side = screenX / 2;
        top = screenY - 25;

        rect = new RectF(side, top, side + width, top + height);

        //Speed of paddle (pixels per sec.)
        paddleSpeed = 350;
    }

    //getter
    public RectF getRect() {
        return rect;
    }

    //setter
    public void setDirection(int direction) {
        paddleDirection = direction;
    }

    //update, will update when the paddle needs to change direction
    public void update(long fps) {
        if (paddleDirection == LEFT) {
            side = side - paddleSpeed / fps;
        }

        if (paddleDirection == RIGHT) {
            side = side + paddleSpeed / fps;
        }

        rect.left = side;
        rect.right = side + width;
    }

    public void setX(int distance, int direction){

    }

}