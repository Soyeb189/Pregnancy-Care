package com.muktadir.pregnancycare;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muktadir.pregnancycare.Adapter.User;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.Calendar;

public class Profile extends AppCompatActivity {

    ArrayList<Integer> imageIdList;
    ArrayList<String> imageTitleList;
    BoomMenuButton boomMenuButton;

    Button buttonSave;
    ImageView imageViewEdtName,imageViewEdtBirthday,imageViewEdtAge,imageViewEdtPeriod;
    TextView textViewName,textViewBirthday,textViewBirthMonth,textViewBirthYear,textViewPeriodYear,textViewAge,textViewPeriodDay,textViewPeriodMonth;

    String name,periodDay,periodMonth,periodYear,birthDay,birthMonth,birthYear,finalAge,pregDayFinalString;
    String getPeriodDay,getPeriodMonth,getPeriodYear,setPeriodMonth;

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase db;

    DatePickerDialog dpd;
    Calendar calendar;
    int day,month,year,age,pregMonthFinal,pregDayFinal;
    String number = "999";

    int periodYearInt,periodMonthInt,periodDayInt,pregYear,pregMonth,pregDay,pregDayTotal,pregMonthTotal,pregYearTotal,pregMonthTotalFinal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Profile");
        setContentView(R.layout.activity_profile);
        boomMenuButton = findViewById(R.id.bmb);
        imageIdList = new ArrayList<>();
        imageTitleList = new ArrayList<>();
        setInitialData();
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //For Show
        textViewName = findViewById(R.id.txtVwName);
        textViewBirthday = findViewById(R.id.txtVwBirthday);
        textViewBirthMonth = findViewById(R.id.txtVwBirthMonth);
        textViewBirthYear = findViewById(R.id.txtVwBirthYear);
        textViewPeriodYear = findViewById(R.id.txtVwPeriodYear);
        textViewPeriodDay = findViewById(R.id.txtVwPeriodDay);
        textViewPeriodMonth = findViewById(R.id.txtVwPeriodMonth);
        textViewAge = findViewById(R.id.txtVwAge);

        //For Edit
        imageViewEdtName = findViewById(R.id.edtName);
        imageViewEdtBirthday = findViewById(R.id.edtBirthday);
        imageViewEdtAge = findViewById(R.id.edtAge);
        imageViewEdtPeriod = findViewById(R.id.edtPeriod);

        //Save Button
        buttonSave= findViewById(R.id.btnSave);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();



        //Edit Name
        imageViewEdtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Profile.this, android.R.style.Theme_Material_Dialog_Alert);
                View mview=getLayoutInflater().inflate(R.layout.custom_dialog,null);
                final TextView text_view_dialog_age = mview.findViewById(R.id.text_view_custom_dialog);
                text_view_dialog_age.setHint("Name");
                builder.setView(mview);
                builder.setMessage("Enter your Name")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(!text_view_dialog_age.getText().toString().trim().equals(""))
                                {
                                    name=text_view_dialog_age.getText().toString().trim();
                                    textViewName.setText(name);
                                    name = textViewName.getText().toString().trim();

                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        }).show();

            }
        });

        //Edit Birthday
        imageViewEdtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);


                dpd = new DatePickerDialog(Profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        textViewBirthday.setText("");
                        textViewBirthMonth.setText("");
                        textViewBirthYear.setText(mDay + "/"+ (mMonth+1) + "/"+mYear );
                        //textViewBirthYear.setText(mYear);
                        //textViewBirthMonth.setText((mMonth+1));
                        //textViewBirthday.setText(mDay);

                        birthDay = String.valueOf(mDay);
                        birthMonth = String.valueOf(mMonth+1);
                        birthYear = String.valueOf(mYear);

                        // textViewBirthYear.setText(birthYear);
                        //textViewBirthMonth.setText(birthMonth);
                        //textViewBirthYear.setText(birthDay);



                        //Age Calculation

                        int currentYear= Calendar.getInstance().get(Calendar.YEAR);
                        int currentMonth= Calendar.getInstance().get(Calendar.MONTH)+1;
                        int currentDay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                        age=currentYear-mYear;
                        if((currentMonth-(mMonth+1))<0)
                        {
                            age--;
                        }
                        if(currentMonth-(mMonth+1)==0)
                        {
                            if(currentDay-mDay<0)
                            {
                                age--;
                            }
                        }
                        //Set Age
                        finalAge = String.valueOf(age);
                        textViewAge.setText(finalAge);
                        finalAge = textViewAge.getText().toString().trim();

                    }
                },day, month, year);
                dpd.show();

            }
        });



        //Edit Age
        imageViewEdtAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Please Enter Your Birthday",Toast.LENGTH_SHORT).show();
            }
        });

        //Edit Period Date
        imageViewEdtPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);


                dpd = new DatePickerDialog(Profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        if(mYear>year && mYear<year-1){
                            dpd.dismiss();
                            textViewPeriodDay.setText("");
                            textViewPeriodMonth.setText("");
                            textViewPeriodYear.setText("Please Set A Valid Year");
                        }
                        else if(mYear<=year && mYear>=year-1){
                            textViewPeriodDay.setText("");
                            textViewPeriodMonth.setText("");
                            textViewPeriodYear.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                            periodDay = String.valueOf(mDay);
                            periodMonth = String.valueOf(mMonth + 1);
                            periodYear = String.valueOf(mYear);
                        }
                        else {
                            textViewPeriodDay.setText("");
                            textViewPeriodMonth.setText("");
                            textViewPeriodYear.setText("Please Set A Valid Year");
                        }


                    }
                },day, month, year);
                dpd.show();



            }
        });


        //Save Button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserData();
            }
        });

        //Fetching Data



        DatabaseReference myRefAll = db.getReference(firebaseAuth.getCurrentUser().getUid());

        myRefAll.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.getKey().equals("name"))
                {
                    String name = dataSnapshot.getValue(String.class);
                    textViewName.setText(name);
                }

                if(dataSnapshot.getKey().equals("birthDay"))
                {
                    String showBirthDay = dataSnapshot.getValue(String.class);

                    textViewBirthday.setText(showBirthDay+"/");

                }

                if(dataSnapshot.getKey().equals("birthMonth"))
                {
                    String showBirthMonth = dataSnapshot.getValue(String.class);

                    textViewBirthMonth.setText(showBirthMonth+"/");

                }

                if(dataSnapshot.getKey().equals("birthYear"))
                {
                    String showBirthYear = dataSnapshot.getValue(String.class);

                    textViewBirthYear.setText(showBirthYear);


                }
                if(dataSnapshot.getKey().equals("finalAge"))
                {
                    String age = dataSnapshot.getValue(String.class);
                    textViewAge.setText(age);

                }

                if(dataSnapshot.getKey().equals("periodDay"))
                {
                    String showPeriodDay = dataSnapshot.getValue(String.class);
                    textViewPeriodDay.setText(showPeriodDay+"/");
                }

                if(dataSnapshot.getKey().equals("periodMonth"))
                {
                    String showPeriodMonth = dataSnapshot.getValue(String.class);
                    textViewPeriodMonth.setText(showPeriodMonth+"/");
                }


                if(dataSnapshot.getKey().equals("periodYear"))
                {
                    String showPeriodYear = dataSnapshot.getValue(String.class);
                    textViewPeriodYear.setText(showPeriodYear);

                }





            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.getKey().equals("name"))
                {
                    String name = dataSnapshot.getValue(String.class);
                    textViewName.setText(name);
                }

                if(dataSnapshot.getKey().equals("birthDay"))
                {
                    String showBirthDay = dataSnapshot.getValue(String.class);

                    textViewBirthday.setText(showBirthDay+"/");

                }

                if(dataSnapshot.getKey().equals("birthMonth"))
                {
                    String showBirthMonth = dataSnapshot.getValue(String.class);

                    textViewBirthMonth.setText(showBirthMonth+"/");

                }

                if(dataSnapshot.getKey().equals("birthYear"))
                {
                    String showBirthYear = dataSnapshot.getValue(String.class);

                    textViewBirthYear.setText(showBirthYear);


                }
                if(dataSnapshot.getKey().equals("finalAge"))
                {
                    String age = dataSnapshot.getValue(String.class);
                    textViewAge.setText(age);

                }

                if(dataSnapshot.getKey().equals("periodDay"))
                {
                    String showPeriodDay = dataSnapshot.getValue(String.class);
                    textViewPeriodDay.setText(showPeriodDay+"/");
                }

                if(dataSnapshot.getKey().equals("periodMonth"))
                {
                    String showPeriodMonth = dataSnapshot.getValue(String.class);
                    textViewPeriodMonth.setText(showPeriodMonth+"/");
                }


                if(dataSnapshot.getKey().equals("periodYear"))
                {
                    String showPeriodYear = dataSnapshot.getValue(String.class);
                    textViewPeriodYear.setText(showPeriodYear);

                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








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
                    if (index==0){
                        i = new Intent(Profile.this,Home.class);
                        startActivity(i);

                    }
                    if (index == 1) {
                        i = new Intent(Intent.ACTION_CALL);
                        String dial = "tel:" + number;
                        if (ActivityCompat.checkSelfPermission(Profile.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                            return;
                        }
                        else {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                        }

                    }
                    if (index==2){
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        i = new Intent(Profile.this,SignIn.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(),"Log Out Successfully",Toast.LENGTH_SHORT).show();

                    }

                }
            });
            boomMenuButton.addBuilder(builder);
        }



    }


    private void setInitialData() {
        //set icon id
        imageIdList.add(R.drawable.ic_home_black_24dp);
        imageIdList.add(R.drawable.ic_call_black_24dp);
        imageIdList.add(R.drawable.ic_power_settings_new_black_24dp);


        //set icon title

        imageTitleList.add(getString(R.string.profile_b_home));
        imageTitleList.add(getString(R.string.profile_b_emergency));
        imageTitleList.add(getString(R.string.profile_b_logout));


    }

    //Send Data to Firebase

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //String user = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());
        User user = new User(name,periodDay,periodMonth,periodYear,birthDay,birthMonth,birthYear,finalAge);
        myRef.setValue(user);

    }




}


