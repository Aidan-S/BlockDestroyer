package com.scannella.blockdestroyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static TextView points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        points = (TextView) findViewById(R.id.txtPoints);

        ImageButton button1 = (ImageButton) findViewById(R.id.btnLvl1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, lvl1.class);
                startActivity(intent);
            }
        });
        ImageButton button2 = (ImageButton) findViewById(R.id.btnLvl2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, lvl2.class);
                startActivity(intent);
            }
        });
        ImageButton button3 = (ImageButton) findViewById(R.id.btnLvl3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, lvl3.class);
                startActivity(intent);
            }
        });
    }


}
