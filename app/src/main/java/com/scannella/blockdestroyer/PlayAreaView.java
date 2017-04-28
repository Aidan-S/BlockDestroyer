package com.scannella.blockdestroyer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static com.scannella.blockdestroyer.R.layout.activity_lvl1;

/**
 * Created by scannella on 4/27/2017.
 */

public class PlayAreaView extends View {
    private Matrix translate;
    private Bitmap paddle;
    private GestureDetector gestures;

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(paddle, translate, null);
        Matrix m = canvas.getMatrix();
        /*Log.d(DEBUG_TAG, "Matrix: "+translate.toShortString());
        Log.d(DEBUG_TAG, "Canvas: "+m.toShortString());*/
    }


    public PlayAreaView(Context context) {
        super(context);
        translate = new Matrix();
        gestures = new GestureDetector(activity_lvl1,
                new GestureListener(this));
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
    }

    public void onMove(float dx, float dy) {
        translate.postTranslate(dx, dy);
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestures.onTouchEvent(event);
    }



    public void onResetLocation() {
        translate.reset();
        invalidate();
    }

}
