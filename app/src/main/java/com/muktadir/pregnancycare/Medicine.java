package com.muktadir.pregnancycare;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.muktadir.pregnancycare.Medicine_Pack.Add_Medicine;

public class Medicine extends AppCompatActivity {
    private FloatingActionButton mAddReminderButton;
    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        mToolbar = findViewById(R.id.toolbar_medicine);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.appointment_doctor);

        mAddReminderButton = (FloatingActionButton)findViewById(R.id.fab_medicine);

        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addmedicine = new Intent(Medicine.this, Add_Medicine.class);
                startActivity(addmedicine);
            }
        });


    }
}
