package com.muktadir.pregnancycare;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

public class Tips extends AppCompatActivity implements View.OnClickListener {

    BoomMenuButton boomMenuButton;
    private CardView dietCardView, exerciseCardView, healthCardView, testCardView;

    ArrayList<Integer> imageIdList;
    ArrayList<String> imageTitleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        //card view
        dietCardView = findViewById(R.id.cardVwDiatChart);
        exerciseCardView = findViewById(R.id.cardVwExercise);
        healthCardView = findViewById(R.id.cardVwHelth);
        testCardView = findViewById(R.id.cardVwTest);


        dietCardView.setOnClickListener(this);
        exerciseCardView.setOnClickListener(this);
        healthCardView.setOnClickListener(this);
        testCardView.setOnClickListener(this);


        imageIdList = new ArrayList<>();
        imageTitleList = new ArrayList<>();
        setInitialData();
        boomMenuButton = findViewById(R.id.bmb);


        //boom menu

        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()

                    .normalImageRes(imageIdList.get(i))
                    .normalText(imageTitleList.get(i));
            builder.imagePadding(new Rect(40, 40, 40, 40));

            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    // When the boom-button corresponding this builder is clicked.
                    Intent i;
                    if (index == 0) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(Tips.this, SignIn.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Log Out Successfully", Toast.LENGTH_SHORT).show();

                    }

                }
            });
            boomMenuButton.addBuilder(builder);
        }


    }

    // Set Data for boom menu
    private void setInitialData() {
        //set icon id
        imageIdList.add(R.drawable.ic_power_settings_new_black_24dp);
        imageIdList.add(R.drawable.ic_add_alert_black_24dp);
        imageIdList.add(R.drawable.ic_build_black_24dp);


        //set icon title

        imageTitleList.add("Log Out");
        imageTitleList.add("Settings");
        imageTitleList.add("Notification");


    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {

            case R.id.cardVwDiatChart:
                i = new Intent(this, DiatChart.class);
                startActivity(i);
                break;

            case R.id.cardVwExercise:
                i = new Intent(this, Exercise.class);
                startActivity(i);
                break;

            case R.id.cardVwHelth:
                i = new Intent(this, Health.class);
                startActivity(i);
                break;

            case R.id.cardVwTest:
                i = new Intent(this, TestChart.class);
                startActivity(i);
                break;

            default:
                break;

        }
    }
}
