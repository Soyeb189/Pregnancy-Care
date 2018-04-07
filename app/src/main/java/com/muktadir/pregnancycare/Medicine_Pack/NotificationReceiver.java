package com.muktadir.pregnancycare.Medicine_Pack;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.muktadir.pregnancycare.R;

public class NotificationReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int NID=intent.getIntExtra("NID",0);
        String text=intent.getStringExtra("text");

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent reaptingIntent=new Intent(context,Add_Medicine.class);
        reaptingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,NID,reaptingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_on_white_24dp)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setContentTitle("Medicine Reminder")
                .setContentText(text)
                .setAutoCancel(true);
        notificationManager.notify(NID,builder.build());
    }
}
