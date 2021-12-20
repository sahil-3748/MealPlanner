package com.example.mealplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    final int count =3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //ImageView splashimage=findViewById(R.id.splashimage);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashtologin= new Intent(SplashScreen.this,Login.class);
                startActivity(splashtologin);
                finish();
            }
        },count);
    }
}