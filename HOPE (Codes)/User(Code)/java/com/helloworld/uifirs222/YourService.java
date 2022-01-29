package com.helloworld.uifirs222;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class YourService extends Service {

    private NotificationManagerCompat notificationManager;
    private DatabaseReference dref;
    Vibrator vibrator;
    private String daa;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final SharedPreferences storage = getApplicationContext().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        String userphone = storage.getString("User number", "unknown");
        notificationManager = NotificationManagerCompat.from(this);

        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        dref = FirebaseDatabase.getInstance().getReference("Users/" + userphone);
        final Bitmap icon = BitmapFactory.decodeResource(getApplication().getResources(),
                R.drawable.ic_looooogo);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Notified to Attend").exists()
                ) {
                    if (dataSnapshot.child("Notified to Attend").getValue().toString().equals("True")) {
                        String drName = dataSnapshot.child("Doctor name").getValue().toString();
                        String drTime = dataSnapshot.child("Doctor time").getValue().toString();
                        String hospName = dataSnapshot.child("Hospital name").getValue().toString();


                        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        Intent activityintent = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                                0, activityintent, 0);
                      /*  NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())

                                .setContentTitle("Reminder")

                                .setContentText("Don't forget your appointment with Dr."+drName+" "+"at"+" "+drTime)
                                .setSmallIcon(R.drawable.ic_sure)
                                .setLargeIcon(icon)
                                .setContentIntent(contentIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                                .setContentIntent(contentIntent)
                                .setCategory(NotificationCompat.CATEGORY_ALARM)
                                .setSound(uri)
                                .setOnlyAlertOnce(true)
                                .setAutoCancel(true);

                        builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        notificationManager.notify(0, builder.build());*/

                 /*       Notification notification = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_sure)
                                .setContentTitle("Hope")
                                .setContentText("Reminder")
                                .setLargeIcon(icon)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("Don't forget your appointment with Dr."+drName+" "+"at"+" "+drTime))
                                .build();
                        notificationManager.notify(1,notification);*/
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_sure)
                                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                        R.mipmap.ic_laun))
                                .setContentTitle("Hope")
                                .setContentText("Reminder")
                                .setAutoCancel(false)
                                .setSound(uri)
                                .setColor(getResources().getColor(R.color.Black))
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("Don't forget your appointment with Dr." + drName + " " + "at" + " " + drTime).setBigContentTitle(hospName))
                                .setContentIntent(contentIntent);

                        android.app.NotificationManager notificationManager =
                                (android.app.NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

                        notificationManager.notify(101 /* ID of notification */, notificationBuilder.build());
                    }
                }

                if (dataSnapshot.child("Notified to Cancel").exists()
                ) {
                    if (dataSnapshot.child("Notified to Cancel").getValue().toString().equals("True")) {
                        String drName = dataSnapshot.child("Doctor name").getValue().toString();
                        String hospName = dataSnapshot.child("Hospital name").getValue().toString();

                        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        Intent activityintent = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                                202, activityintent, 0);


                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_sure)
                                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                        R.mipmap.ic_laun))
                                .setContentTitle("Hope")
                                .setContentText("Apology")
                                .setAutoCancel(false)
                                .setSound(uri)
                                .setColor(getResources().getColor(R.color.Black))
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("We are sorry to inform that your appointment with Dr." + drName + " " + "has been Canceled").setBigContentTitle(hospName))
                                .setContentIntent(contentIntent);

                        android.app.NotificationManager notificationManager =
                                (android.app.NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

                        notificationManager.notify(303 /* ID of notification */, notificationBuilder.build());


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return super.onStartCommand(intent, flags, startId);


    }


}
