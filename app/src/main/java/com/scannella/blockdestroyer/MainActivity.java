package com.scannella.blockdestroyer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button1 = (Button) findViewById(R.id.btnLvl1);
        //when button is clicked
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonRandomPosition();
                if(play.isPlaying()) {
                    play.pause();
                    play.seekTo(0);
                }
                play.start();

                word.setText("You Got Me");
            }
        });
        Button button2 = (Button) findViewById(R.id.btnLvl2);
        //when button is clicked
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonRandomPosition();
                if(play.isPlaying()) {
                    play.pause();
                    play.seekTo(0);
                }
                play.start();

                word.setText("You Got Me");
            }
        });
        Button button3 = (Button) findViewById(R.id.btnLvl3);
        //when button is clicked
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonRandomPosition();
                if(play.isPlaying()) {
                    play.pause();
                    play.seekTo(0);
                }
                play.start();

                word.setText("You Got Me");
            }
        });
    }


}
