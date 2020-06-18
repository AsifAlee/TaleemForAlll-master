package com.shafiq.asifa.taleemforalll;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shafiq.asifa.taleemforalll.MainActivity;
import com.shafiq.asifa.taleemforalll.R;

public class SplashScreenAct extends AppCompatActivity {

    private  static  int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenAct.this, MainActivity.class);
                startActivity(intent);
            }
        },SPLASH_TIME_OUT);
    }

}
