package com.scannella.blockdestroyer;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class lvl1 extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener  {

    final Handler handler = new Handler();

    // The player's paddle
    float screenX = getResources().getDisplayMetrics().widthPixels;
    float screenY = getResources().getDisplayMetrics().heightPixels;
    private Ball ball;
    private GestureDetectorCompat GestureDetector;

    private boolean paused = true;
    final Canvas canvas;
    private ImageView paddle;
    private int direction = 270;

    /*SurfaceHolder ourHolder;

    Canvas canvas;
    Paint paint;
    Paddle paddle = new Paddle(screenX, screenY);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl1);

        paddle = (ImageView) findViewById(R.id.imgPaddle);

        FrameLayout frame = (FrameLayout) findViewById(R.id.graphics_holder);
        PlayAreaView image = new PlayAreaView(this);
        frame.addView(paddle);



        //prepares gestures
        this.GestureDetector = new GestureDetectorCompat(this, this);
        GestureDetector.setOnDoubleTapListener(this);
        updateBall(canvas);

    }

    public void updateBall(Canvas canvas){
        while(!paused){
            handler.postDelayed(new Runnable() {
                @Override
                public void setBall(canvas) {
                    ball.setDirection(direction);
                }
            }, 100);
        }
    }



    @Override
    //create pull down options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }


    // Assign the touch listener to your view which you want to move


    // This defines your touch listener

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                return true;
            } else {
                return false;
            }
        }






    @Override
    //when item is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.itmBack) {
            Intent intent = new Intent(lvl1.this,MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.itmReset) {
            Intent intent = new Intent(lvl1.this,MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.itmPause) {
            Intent intent = new Intent(lvl1.this,MainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);

    }

    //gesture methods for when the player misses and taps the screen
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) { return false; }

    @Override
    public boolean onDoubleTap(MotionEvent e) {return false;}

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        /*float x = e.getX();
        float y = e.getY();
        if(x < screenX){
            switch (e.getAction()) {
                case e.ACTION_DOWN:
                    // Write your code to perform an action on down
                    break;
                case e.ACTION_MOVE:

                     break;
                case e.ACTION_UP:
                    // Write your code to perform an action on touch up
                    break;
            }
        }
        return true;*/
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



}

/*private class PlayAreaView extends View {
    private Matrix translate;
    private Bitmap droid;
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(droid, translate, null);
        Matrix m = canvas.getMatrix();
        *//*Log.d(DEBUG_TAG, "Matrix: "+translate.toShortString());
        Log.d(DEBUG_TAG, "Canvas: "+m.toShortString());*//*
    }
}*/