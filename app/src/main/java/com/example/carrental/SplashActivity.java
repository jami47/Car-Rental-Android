package com.example.carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = new Intent(SplashActivity.this, LogIn.class);

        new Handler().postDelayed(new Runnable() {  //runs parallely. creates delay
            @Override
            public void run() {
                startActivity(intent);
                finish();   //Remove the activity from the stack so that it is shown only once and doesn't come back when back button is clicked from the next activity.
            }
        }, 2500); //Delay in milli_seconds

    }
}