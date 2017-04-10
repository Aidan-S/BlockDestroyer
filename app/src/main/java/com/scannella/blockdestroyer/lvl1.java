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

    private Boolean paused = true;
    // The player's paddle
    int screenX = getResources().getDisplayMetrics().widthPixels;
    int screenY = getResources().getDisplayMetrics().heightPixels;


    /*private GestureDetector gestureDetector;

    public lvl1(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }*/

    SurfaceHolder ourHolder;

    Canvas canvas;
    Paint paint;
    Paddle paddle = new Paddle(screenX, screenY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl1);

        //prepares gestures
        this.GestureDetector = new GestureDetectorCompat(this, this);
        GestureDetector.setOnDoubleTapListener(this);

        // Declare a Bitmap
        Bitmap blankBitmap = Bitmap.createBitmap(screenX,screenY,Bitmap.Config.ARGB_8888);
        // Declare a canvas
        Canvas canvas = new Canvas(blankBitmap);

        // Declare an object of type Paint
        Paint paint = new Paint();

        paint.setColor(Color.argb(255,  26, 128, 182));
        canvas.drawRect(50,450,500,550,paint);


    }



    public void draw() {


        if (ourHolder.getSurface().isValid()) {
            // Ready the canvas
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255,  26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  255, 255, 255));



            // Draw everything
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    //create pull down options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

   /* private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        public void onSwipeRight() {

        }
        public void onSwipeLeft() {

        }
        public void onSwipeTop() {

        }
        public void onSwipeBottom() {

        }
        public void onClick(){

        }

        public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
        }

    }*/






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


}
