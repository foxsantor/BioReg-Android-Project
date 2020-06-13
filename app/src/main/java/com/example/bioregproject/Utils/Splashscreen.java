package com.example.bioregproject.Utils;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;

import gr.net.maroulis.library.EasySplashScreen;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(Splashscreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2000)
                .withBackgroundColor(Color.parseColor("#3797DD"))
                .withLogo(R.drawable.logo_transparent);
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }

}