package com.muktadir.pregnancycare.Medicine_Pack;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.muktadir.pregnancycare.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_Medicine extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

    TextView date,time, setDate, setTime;
    int hour,minute,year,month,day;
    RelativeLayout date_l, time_l;
    Button save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__medicine);



        date=(TextView)findViewById(R.id.date_text);
        time=(TextView)findViewById(R.id.time_text);
        date_l = (RelativeLayout)findViewById(R.id.date_layout);
        time_l = (RelativeLayout) findViewById(R.id.time_layout);
        setDate = (TextView) findViewById(R.id.set_date);
        setTime = (TextView) findViewById(R.id.set_time);
        save=(Button)findViewById(R.id.save_icon);

        time_l.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c=Calendar.getInstance();
                        hour=c.get(Calendar.HOUR_OF_DAY);
                        minute=c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog=new TimePickerDialog(Add_Medicine.this,m_time,hour,minute,true);
                        timePickerDialog.show();


                    }
                }
        );
        date_l.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        date.setError(null);
                        DialogFragment datePicker=new DatePickerFragment();
                        datePicker.show(getFragmentManager(),"date picker");
                    }
                }
        );

        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Date date=new Date();
                        Calendar calendar=Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        Calendar currentTime=Calendar.getInstance();
                        calendar.setTime(date);
                        currentTime.setTime(date);
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.HOUR_OF_DAY ,hour);
                        calendar.set(Calendar.MINUTE,minute);
                        if(calendar.before(currentTime))
                        {
                            calendar.add(Calendar.DATE,1);
                        }
                        Intent intent=new Intent(getApplicationContext(),NotificationReceiver.class);
                        intent.putExtra("NID",101);
                        intent.putExtra("text","Time to appoinment doctor");
                        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                    }
                }
        );

    }

    TimePickerDialog.OnTimeSetListener m_time=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            setTime.setText(hourOfDay +":"+ minute);
            /*String minuteS=String.valueOf(minute);
            String AMorPM="AM";
            if(hourOfDay>12)
            {
                Integer value=hourOfDay-12;
                hourOfDay=value;
                AMorPM="PM";

            }
            if(minute<10)
            {
                minuteS="0"+String.valueOf(minute);
            }*/

        }
    };


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        this.year=year;
        this.month=month;
        day=dayOfMonth;

        c.set(c.YEAR,year);
        c.set(c.MONTH,month);
        c.set(c.DAY_OF_MONTH,dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        setDate.setText(currentDateString);
    }
}
