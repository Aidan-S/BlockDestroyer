package com.scannella.blockdestroyer;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
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
import android.widget.ImageView;


public class lvl1 extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener  {


    // The player's paddle
    float screenX = getResources().getDisplayMetrics().widthPixels;
    float screenY = getResources().getDisplayMetrics().heightPixels;

    private GestureDetectorCompat GestureDetector;


    SurfaceHolder ourHolder;

    Canvas canvas;
    Paint paint;
    //Paddle paddle = new Paddle(screenX, screenY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl1);


        //prepares gestures
        this.GestureDetector = new GestureDetectorCompat(this, this);
        GestureDetector.setOnDoubleTapListener(this);


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
