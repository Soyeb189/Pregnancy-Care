package com.muktadir.pregnancycare;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(SignIn.class)
                .withSplashTimeOut(3000)
                .withBackgroundResource(R.drawable.background_gradient)
                .withLogo(R.drawable.logopega)
                .withAfterLogoText("Pregnancy Care")
                .withHeaderText("SignIn")
                .withFooterText("Copyright 2018");

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);

        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);
    }
}
