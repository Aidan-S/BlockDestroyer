package com.scannella.blockdestroyer;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Random;


public class lvl1 extends AppCompatActivity {
    Canvas canvas;
    SquashCourtView squashCourtView;

    block[] bricks = new block[18];
    int numBricks;

    int brickWidth;
    int brickHeight;

    private SoundPool soundPool;

    int sample1 = -1;
    int sample2 = -1;
    int sample3 = -1;
    int sample4 = -1;

    Display display;
    Point size;
    int screenWidth;
    int screenHeight;

    int racketWidth;
    int racketHeight;
    Point racketPosition;

    Point ballPosition;
    int ballWidth;

    boolean ballIsMovingLeft;
    boolean ballIsMovingRight;
    boolean ballIsMovingDown;
    boolean ballIsMovingUp;

    boolean racketIsMovingRight;
    boolean racketIsMovingLeft;

    long lastFrameTime;
    int fps;
    int score;
    int lives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        squashCourtView = new SquashCourtView(this);
        setContentView(squashCourtView);
        // Sound Code
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            // Create objects of the two required classes
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;
            // create our three fx in memory ready for use
            descriptor = assetManager.openFd("sample1.ogg");
            sample1 = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("sample2.ogg");
            sample2 = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("sample3.ogg");
            sample3 = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("sample4.ogg");
            sample4 = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            // catch exceptions here
        }
        /// Get the screen size in Pixels
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        // The game objects
        racketPosition = new Point();
        racketPosition.x = screenWidth / 2;
        racketPosition.y = screenHeight - 20;
        racketWidth = screenWidth / 8;
        racketHeight = 10;

        ballWidth = screenWidth / 35;
        ballPosition = new Point();
        ballPosition.x = screenWidth / 2;
        ballPosition.y = 1 + ballWidth;

        lives = 3;
    }

    class SquashCourtView extends SurfaceView implements Runnable {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playingSquash;
        Paint paint;

        public SquashCourtView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
            ballIsMovingDown = true;

            //Send the ball in random direction
            Random randomNumber = new Random();
            int ballDirection = randomNumber.nextInt(3);
            switch (ballDirection) {
                case 0:
                    ballIsMovingLeft = true;
                    ballIsMovingRight = false;
                    break;
                case 1:
                    ballIsMovingRight = true;
                    ballIsMovingLeft = false;
                    break;
                case 2:
                    ballIsMovingLeft = false;
                    ballIsMovingRight = false;
                    break;
            }
        }

        @Override
        public void run() {
            while (playingSquash) {
                updateCourt();
                drawCourt();
                controlFPS();
            }
        }

        public void updateCourt() {

            //create bricks
            brickWidth = screenWidth / 6 ;
            brickHeight = screenHeight / 13;
            numBricks = 0;

            for(int c = 0; c < 6; c++ ){
                for(int r = 0; r < 3; r++ ){
                    bricks[numBricks] = new block(brickWidth, brickHeight, r, c);
                    numBricks ++;
                }
            }


            if (racketIsMovingRight) {
                racketPosition.x = racketPosition.x + 10;
            }
            if (racketIsMovingLeft) {
                racketPosition.x = racketPosition.x - 10;
            }



            // dtect collisions - ball hit right of screen
            if (ballPosition.x + ballWidth > screenWidth) {
                ballIsMovingLeft = true;
                ballIsMovingRight = false;
                soundPool.play(sample1, 1, 1, 0, 0, 1);
            }
            // ball hit left of screen
            if (ballPosition.x < 0) {
                ballIsMovingLeft = false;
                ballIsMovingRight = true;
                soundPool.play(sample1, 1, 1, 0, 0, 1);
            }

            //Edge of ball has hit bottom of screen
            if (ballPosition.y > screenHeight - ballWidth) {
                lives = lives - 1;
                if (lives == 0) {
                    lives = 3;
                    score = 0;
                    soundPool.play(sample4, 1, 1, 0, 0, 1);
                }
                ballPosition.y = 1 + ballWidth;
                // back to top of screen
                // choose horizontal direction for next ball
                Random randomNumber = new Random();
                int startX = randomNumber.nextInt(screenWidth - ballWidth) + 1;
                ballPosition.x = startX + ballWidth;

                int ballDirection = randomNumber.nextInt(3);
                switch (ballDirection) {
                    case 0:
                        ballIsMovingLeft = true;
                        ballIsMovingRight = false;
                        break;
                    case 1:
                        ballIsMovingRight = true;
                        ballIsMovingLeft = false;
                        break;
                    case 2:
                        ballIsMovingLeft = false;
                        ballIsMovingRight = false;
                        break;
                }

            }

            // hit the top of the screen
            if (ballPosition.y <= 0) {
                ballIsMovingDown = true;
                ballIsMovingUp = false;
                ballPosition.y = 1;
                soundPool.play(sample2, 1, 1, 0, 0, 1);
            }
            // depending upon the two direcitons we should be mving in adjust our x any positions
            if (ballIsMovingDown) ballPosition.y += 6;
            if (ballIsMovingUp) ballPosition.y -= 10;
            if (ballIsMovingLeft) ballPosition.x -= 12;
            if (ballIsMovingRight) ballPosition.x += 12;

            // Has ball hit racket
            if (ballPosition.y + ballWidth >= (racketPosition.y - racketHeight / 2)) {
                int halfRacket = racketWidth / 2;
                if (ballPosition.x + ballWidth > (racketPosition.x - halfRacket) && ballPosition.x - ballWidth < (racketPosition.x + halfRacket)) {
                    // rebound the vall vertically and play a sound
                    soundPool.play(sample3, 1, 1, 0, 0, 1);
                    score++;
                    ballIsMovingUp = true;
                    ballIsMovingDown = false;
                    // now decide how to rebound the ball horizontally
                    if (ballPosition.x < racketPosition.x) {
                        ballIsMovingRight = true;
                        ballIsMovingLeft = false;
                    } else {
                        ballIsMovingRight = false;
                        ballIsMovingLeft = true;
                    }
                }
            }
        }

        public void drawCourt() {
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();

                //draw blocks
                for(int i = 0; i < numBricks; i++){
                    if(bricks[i].getAlive()) {
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }

                //Paint paint = new Paint();
                canvas.drawColor(Color.argb(255, 51, 255, 153));//the background
                paint.setColor(Color.BLACK);


                // Draw the squash racket
                Log.i("Racket: ",racketPosition.x + ", " + racketPosition.y);
                canvas.drawRect(
                        racketPosition.x - (racketWidth / 2),
                        racketPosition.y - (racketHeight / 2),
                        racketPosition.x + (racketWidth / 2),
                        racketPosition.y + racketHeight, paint);

                // Draw the ball
                canvas.drawRect(ballPosition.x, ballPosition.y, ballPosition.x + ballWidth, ballPosition.y + ballWidth, paint);
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }
        public void controlFPS() {
            long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
            long timeToSleep = 15 - timeThisFrame;
            if (timeThisFrame > 0) {
                fps = (int) (1000 / timeThisFrame);
            }

            if (timeToSleep > 0){
                try {
                    ourThread.sleep(timeToSleep);
                } catch (InterruptedException e) {

                }
            }

            lastFrameTime = System.currentTimeMillis();
        }


        public void pause() {
            playingSquash = false;
            try {
                ourThread.join();
            } catch (InterruptedException e) {

            }
        }

        public void resume() {
            playingSquash = true;
            ourThread = new Thread(this);
            ourThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (motionEvent.getX() >= screenWidth / 2) {
                        racketIsMovingRight = true;
                        racketIsMovingLeft = false;
                    } else {
                        racketIsMovingLeft = true;
                        racketIsMovingRight = false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    racketIsMovingRight = false;
                    racketIsMovingLeft = false;
                    break;
            }
            return true;
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        while (true) {
            squashCourtView.pause();
            break;
        }
        finish();

    }
    @Override
    protected void onPause() {
        super.onPause();
        squashCourtView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        squashCourtView.resume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            squashCourtView.pause();
            finish();
            return true;
        }
        return false;
    }
}