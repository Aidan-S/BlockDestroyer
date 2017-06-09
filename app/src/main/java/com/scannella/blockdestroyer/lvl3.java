package com.scannella.blockdestroyer;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Random;


public class lvl3 extends AppCompatActivity {

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

        // The game objects
        racketPosition = new Point();
        racketPosition.x = screenWidth / 2;
        racketPosition.y = screenHeight - 520;
        racketWidth = screenWidth / 8;
        racketHeight = 10;

        ballWidth = screenWidth / 35;
        ballPosition = new Point();
        ballPosition.x = racketPosition.x;
        ballPosition.y = racketPosition.y - (racketHeight/2) - ballWidth;

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

            if (racketIsMovingRight && racketPosition.x + (racketWidth/2)< screenWidth) {
                racketPosition.x = racketPosition.x + 25;
            }
            if (racketIsMovingLeft && racketPosition.x - (racketWidth/2) > 0) {
                racketPosition.x = racketPosition.x - 25;
            }

            // detect collisions - ball hit right of screen
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



            // hit the top of the screen
            if (ballPosition.y < 0) {
                ballIsMovingDown = true;
                ballIsMovingUp = false;
                ballPosition.y = 1;
                soundPool.play(sample1, 1, 1, 0, 0, 1);
            }

            //ball goes past paddle
            if(ballPosition.y + ballWidth > racketPosition.y + racketHeight/2 + 20 +50){

                lives = lives - 1;
                if (lives < 1) {
                    lives = 3;
                    score = 0;
                    soundPool.play(sample4, 1, 1, 0, 0, 1);
                    Intent intent = new Intent(lvl3.this, MainActivity.class);
                    startActivity(intent);
                    //Toast.makeText(lvl3.this, "You Lost, Stop it", Toast.LENGTH_SHORT).show();
                }
                ballPosition.y = racketPosition.y - (racketHeight/2) - ballWidth;
                ballPosition.x = racketPosition.x;

            }

            // Has ball hit racket
            if (ballPosition.y + ballWidth > racketPosition.y - racketHeight/2) {
                int halfRacket = racketWidth / 2;
                if (ballPosition.x + ballWidth > (racketPosition.x - halfRacket)
                        && ballPosition.x < (racketPosition.x + halfRacket)) {

                    ballIsMovingUp = true;
                    ballIsMovingDown = false;
                    soundPool.play(sample3, 1, 1, 0, 0, 1);

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


//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

            //detect if ball hits bricks
            if( ballPosition.y < 551 )
            {
                int numBrick = 0;
                for(int c = 0; c < 6; c++ ){
                    for(int r = 0; r < 3; r++ ){
                        //bricks[numBrick] = new block(brickWidth, brickHeight, r, c);
                        RectF tmpRect = bricks[numBrick].getRect();
                        if( bricks[numBrick].getAlive() ) {
                            // Check if upper left corner enters a brick
                            if( ballPosition.y < tmpRect.bottom
                                    && ballPosition.y > tmpRect.top
                                    && ballPosition.x > tmpRect.left
                                    && ballPosition.x < tmpRect.right ){
                                bricks[numBrick].hit();

                                ballIsMovingDown = true;
                                ballIsMovingUp = false;
                                soundPool.play(sample1, 1, 1, 0, 0, 1);
                            }
                            // Check if upper right corner enters a brick
                            if( ballPosition.y < tmpRect.bottom
                                    && ballPosition.y > tmpRect.top
                                    && ballPosition.x + ballWidth > tmpRect.left
                                    && ballPosition.x + ballWidth < tmpRect.right ){
                                bricks[numBrick].hit();

                                ballIsMovingDown = true;
                                ballIsMovingUp = false;
                                soundPool.play(sample1, 1, 1, 0, 0, 1);
                            }
                        }
                        numBrick++;
                    }
                }
            }

            score = 360;
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getAlive()) {
                    score -= 20;
                }
            }

            if(score == 360){
                //MainActivity.points.setText("You got 360");

                Intent intent = new Intent(lvl3.this, MainActivity.class);
                startActivity(intent);
            }


//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

            if (ballIsMovingDown) ballPosition.y += 24;
            if (ballIsMovingUp) ballPosition.y -= 24;
            if (ballIsMovingLeft) ballPosition.x -= 24;
            if (ballIsMovingRight) ballPosition.x += 24;


        }

        public void drawCourt() {
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();


                canvas.drawColor(Color.argb(255, 255, 51, 51));//the background
                paint.setColor(Color.BLACK);

                paint.setTextSize(45);
                canvas.drawText("Score:" + score + "  Lives: " + lives, 40, racketPosition.y + 70, paint);

                //draw blocks
                for(int i = 0; i < numBricks; i++){
                    if(bricks[i].getAlive()) {
                        canvas.drawRect(bricks[i].getRect(), paint);
                    }
                }

                // Draw the squash racket
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
            Intent intent = new Intent(lvl3.this, MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.itmPause) {
            onPause();
        }
        if (id == R.id.itmPlay) {
            onResume();
        }

        return super.onOptionsItemSelected(item);

    }
}