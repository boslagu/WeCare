package com.example.thea.wecare;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Tottie on 11/19/2017.
 */

public class NotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context,"",Toast.LENGTH_LONG).show();
//        Vibrator v = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
//        v.vibrate(10000);

//
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.home)
                .setContentTitle("We Care Notification")
                .setContentText("Time for Monitoring.");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

    }
}