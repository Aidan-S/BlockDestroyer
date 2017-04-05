package com.scannella.blockdestroyer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class lvl1 extends AppCompatActivity {

    private Boolean paused = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl1);


        // Declare a Bitmap
        Bitmap blankBitmap = Bitmap.createBitmap(600,600,Bitmap.Config.ARGB_8888);
        // Declare a canvas
        Canvas canvas = new Canvas(blankBitmap);

        // Declare an object of type Paint
        Paint paint = new Paint();

        paint.setColor(Color.argb(255,  26, 128, 182));
        canvas.drawRect(50,450,500,550,paint);

    }

    @Override
    //create pull down options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
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

        return super.onOptionsItemSelected(item);

    }


}
