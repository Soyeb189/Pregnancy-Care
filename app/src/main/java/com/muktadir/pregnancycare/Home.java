package com.muktadir.pregnancycare;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.net.sip.SipSession;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.muktadir.pregnancycare.Helper.LocaleHelper;
import com.muktadir.pregnancycare.Medicine_Pack.Add_Medicine;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.Calendar;

import io.paperdb.Paper;


public class Home extends AppCompatActivity implements OnClickListener{
    BoomMenuButton boomMenuButton;
    private CardView profileCardView,appointmentCardView,medicineCardView,notesCardView,statusCardView,tipsCardView;
    TextView textViewDay,textViewMonth,textViewWeek,textViewName;

    ArrayList<Integer> imageIdList;
    ArrayList<String> imageTitleList;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase db;




    String name,pregDayFinalString;
    String number = "999";
    String getPeriodDay,getPeriodMonth,getPeriodYear;

    int day,pregMonthFinal,pregDayFinal,totalPregDay,totalPregWeek,totalPregMonth;

    int periodYearInt,periodMonthInt,periodDayInt,pregYear,pregMonth,pregDay,pregDayTotal,pregMonthTotal,finalPregWeek;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Paper.init(this);

        String Language = Paper.book().read("language");
     //   Toast.makeText(this, Language, Toast.LENGTH_LONG).show();
        if(Language==null){
            Paper.book().write("language", "en");
            updateView((String)Paper.book().read("language"));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageIdList = new ArrayList<>();
        imageTitleList = new ArrayList<>();
        setInitialData();
        boomMenuButton = findViewById(R.id.bmb);
        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        textViewDay = findViewById(R.id.txtVwDay);
        textViewMonth = findViewById(R.id.txtVwMonth);
        textViewWeek = findViewById(R.id.txtVwWeek);
        textViewName = findViewById(R.id.txtVwName);



        //Firebase Fatch Data

        DatabaseReference myRefPeriod = db.getReference(firebaseAuth.getCurrentUser().getUid());

        myRefPeriod.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.getKey().equals("name"))
                {
                    String name = dataSnapshot.getValue(String.class);
                    textViewName.setText(name);

                }

                if(dataSnapshot.getKey().equals("periodDay"))
                {
                    String showPeriodDay = dataSnapshot.getValue(String.class);
                    getPeriodDay = showPeriodDay;

                }

                if(dataSnapshot.getKey().equals("periodMonth"))
                {
                    String showPeriodMonth = dataSnapshot.getValue(String.class);
                    getPeriodMonth = showPeriodMonth;

                }


                if(dataSnapshot.getKey().equals("periodYear"))
                {
                    String showPeriodYear = dataSnapshot.getValue(String.class);
                    getPeriodYear = showPeriodYear;

                }
                //Period Day Calendar
                int currentYear= Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth= Calendar.getInstance().get(Calendar.MONTH)+1;
                int currentDay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);


                try {
                    periodYearInt = Integer.valueOf(getPeriodYear);
                }
                catch (NumberFormatException nfe){

                }
                try {
                    periodMonthInt = Integer.valueOf(getPeriodMonth);
                }
                catch (NumberFormatException nfe){

                }
                try {
                    periodDayInt = Integer.valueOf(getPeriodDay);
                }
                catch (NumberFormatException nfe){

                }


                if (currentDay >= periodDayInt){
                    pregDay = currentDay-periodDayInt;

                    if (currentMonth>=periodMonthInt){
                        pregMonth = currentMonth-periodMonthInt;
                        pregYear = currentYear - periodYearInt;
                    }
                    else if (currentMonth<periodMonthInt){
                        pregMonth = (currentMonth+12) - periodMonthInt;
                        pregYear = currentYear - (periodYearInt+1);
                    }
                }
                else if(currentDay<periodDayInt){
                    pregDay = (currentDay+30)-periodDayInt;
                    if (currentMonth>=(periodMonthInt+1)){
                        pregMonth = currentMonth-periodMonthInt;
                        pregYear = currentYear - periodYearInt;
                    }
                    else if (currentMonth<(periodMonthInt+1)){
                        pregMonth = (currentMonth+12) - periodMonthInt;
                        pregYear = currentYear - (periodYearInt+1);
                    }

                }



                pregMonthTotal = pregYear*12;
                pregMonthFinal = pregMonthTotal+pregMonth;
                pregDayTotal = pregMonthFinal*30;
                pregDayFinal = pregDay + pregDayTotal;

                totalPregMonth = pregDayFinal / 30;
                totalPregWeek = totalPregMonth % 30;
                finalPregWeek = totalPregWeek / 7;
                totalPregDay = totalPregWeek % 7;

                pregDayFinalString = String.valueOf(pregDayFinal);


            //    Toast.makeText(getApplicationContext(),pregDayFinalString,Toast.LENGTH_SHORT).show();
                String pD = String.valueOf(totalPregDay);
                String pW = String.valueOf(finalPregWeek);
                String pM = String.valueOf(totalPregMonth);
                textViewMonth.setText(" "+pM+" ");
                textViewWeek.setText(" "+pW+" ");
                textViewDay.setText(" "+pD+" ");


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.getKey().equals("name"))
                {
                    String name = dataSnapshot.getValue(String.class);
                    textViewName.setText(name);

                }

                if(dataSnapshot.getKey().equals("periodDay"))
                {
                    String showPeriodDay = dataSnapshot.getValue(String.class);
                    getPeriodDay = showPeriodDay;

                }

                if(dataSnapshot.getKey().equals("periodMonth"))
                {
                    String showPeriodMonth = dataSnapshot.getValue(String.class);
                    getPeriodMonth = showPeriodMonth;

                }


                if(dataSnapshot.getKey().equals("periodYear"))
                {
                    String showPeriodYear = dataSnapshot.getValue(String.class);
                    getPeriodYear = showPeriodYear;


                }
                //Period Day Calendar
                int currentYear= Calendar.getInstance().get(Calendar.YEAR);
                int currentMonth= Calendar.getInstance().get(Calendar.MONTH)+1;
                int currentDay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);


                try {
                    periodYearInt = Integer.valueOf(getPeriodYear);
                }
                catch (NumberFormatException nfe){

                }
                try {
                    periodMonthInt = Integer.valueOf(getPeriodMonth);
                }
                catch (NumberFormatException nfe){

                }
                try {
                    periodDayInt = Integer.valueOf(getPeriodDay);
                }
                catch (NumberFormatException nfe){

                }

                    currentDay++;
                if (currentDay >= periodDayInt){
                    pregDay = currentDay-periodDayInt;

                    if (currentMonth>=periodMonthInt){
                        pregMonth = currentMonth-periodMonthInt;
                        pregYear = currentYear - periodYearInt;
                    }
                    else if (currentMonth<periodMonthInt){
                        pregMonth = (currentMonth+12) - periodMonthInt;
                        pregYear = currentYear - (periodYearInt+1);
                    }
                }
                else if(currentDay < periodDayInt){
                    pregDay = (currentDay+30)-periodDayInt;
                    periodMonthInt++;
                    if (currentMonth >= periodMonthInt){
                        pregMonth = currentMonth-periodMonthInt;
                        pregYear = currentYear - periodYearInt;
                    }
                    else if (currentMonth < periodMonthInt){
                        pregMonth = (currentMonth+12) - periodMonthInt;
                        pregYear = currentYear - (periodYearInt+1);
                    }

                }



                pregMonthTotal = pregYear*12;
                pregMonthFinal = pregMonthTotal+pregMonth;
                pregDayTotal = pregMonthFinal*30;
                pregDayFinal = pregDay + pregDayTotal;
//
//                totalPregMonth = pregDayFinal / 30;
//                totalPregWeek = totalPregMonth % 30;
//                finalPregWeek = totalPregWeek / 7;
//                totalPregDay = totalPregWeek % 7;
                finalPregWeek = pregDay / 7;
                totalPregDay = pregDay % 7;


                pregDayFinalString = String.valueOf(pregDayFinal);

              //  Toast.makeText(getApplicationContext(),pregDayFinalString,Toast.LENGTH_SHORT).show();
                String pD = String.valueOf(totalPregDay);
                String pW = String.valueOf(finalPregWeek);
             //   String pM = String.valueOf(totalPregMonth);
                String pM = String.valueOf(pregMonth);
                textViewMonth.setText(pM);
                textViewWeek.setText(pW);
                textViewDay.setText(pD);




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
            builder.imagePadding(new Rect(30, 30, 30, 30));

            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    // When the boom-button corresponding this builder is clicked.
                    Intent i;

                    if (index==0){
                        showChangeLanguageDialog();

                    }
                    if (index == 1) {
                        i = new Intent(Intent.ACTION_CALL);
                        String dial = "tel:" + number;
                        if (ActivityCompat.checkSelfPermission(Home.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                        i = new Intent(Home.this,SignIn.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(),"Log Out Successfully",Toast.LENGTH_SHORT).show();

                    }

                }
            });
            boomMenuButton.addBuilder(builder);
        }

        //card view
        profileCardView = findViewById(R.id.cardVwProfile);
        appointmentCardView = findViewById(R.id.cardVwAppointment);
        medicineCardView = findViewById(R.id.cardVwMedicine);
        notesCardView = findViewById(R.id.cardVwNotes);
        statusCardView = findViewById(R.id.cardVwStatus);
        tipsCardView = findViewById(R.id.cardVwTips);

        profileCardView.setOnClickListener(this);
        appointmentCardView.setOnClickListener(this);
        medicineCardView.setOnClickListener(this);
        notesCardView.setOnClickListener(this);
        statusCardView.setOnClickListener(this);
        tipsCardView.setOnClickListener(this);


        //Status Part

    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Resources resources = context.getResources();
    }

    private void showChangeLanguageDialog() {
        final String[] listLanguage = {"English","Bangla"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Home.this);
        mBuilder.setTitle(R.string.language);
        mBuilder.setSingleChoiceItems(listLanguage, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0){
                    Paper.book().write("language", "en");
                    updateView((String)Paper.book().read("language"));
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }

                if (i == 1){
                    Paper.book().write("language", "bn");
                    updateView((String)Paper.book().read("language"));
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);

                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    private void setInitialData() {
        //set icon id
        imageIdList.add(R.drawable.ic_build_black_24dp);
        imageIdList.add(R.drawable.ic_call_black_24dp);
        imageIdList.add(R.drawable.ic_power_settings_new_black_24dp);




        //set icon title

        imageTitleList.add(getString(R.string.home_b_settings));
        imageTitleList.add(getString(R.string.profile_b_emergency));
        imageTitleList.add(getString(R.string.profile_b_logout));


    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()){

            case R.id.cardVwProfile : i = new Intent(this,Profile.class);
                startActivity(i);
                break;

            case R.id.cardVwAppointment : i = new Intent(this,Appointment.class);
                startActivity(i);
                break;

            case R.id.cardVwMedicine : i = new Intent(this,Add_Medicine.class);
                startActivity(i);
                break;

            case R.id.cardVwNotes : i = new Intent(this,Notes.class);
                startActivity(i);
                break;

            case R.id.cardVwStatus : i = new Intent(this,Status.class);
                startActivity(i);
                break;

            case R.id.cardVwTips : i = new Intent(this,Tips.class);
                startActivity(i);
                break;

            default: break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String Language = Paper.book().read("language");
       // Toast.makeText(this, Language, Toast.LENGTH_LONG).show();
    }
}
