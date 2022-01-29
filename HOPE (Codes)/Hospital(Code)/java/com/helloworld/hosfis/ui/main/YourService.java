package com.helloworld.hosfis.ui.main;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.helloworld.hosfis.MainActivity;
import com.helloworld.hosfis.R;


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
        String name= storage.getString("HospitalName","Zembaboy");
        notificationManager = NotificationManagerCompat.from(this);

        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
        dref=FirebaseDatabase.getInstance().getReference().child("Hospitals/"+name+"/Accidents");

       /* dref.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {


                editor.putString("accidentNum", String.valueOf(dataSnapshot.getChildrenCount()-1));
                editor.commit();
                Toast.makeText(getApplicationContext(), String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                String num= String.valueOf(dataSnapshot.getChildrenCount()-1);
                Intent activityintent= new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent contentIntent =PendingIntent.getActivity(getApplicationContext(),
                        0, activityintent,0);
                if (dataSnapshot.child("Accident"++"/Latitude").exists()) {
                    daa = dataSnapshot.child("Accident0/Latitude").getValue().toString();

                    if (!daa.equals("0")){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "There is an accident",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                .setContentTitle("Accident nearby!")
                                .setContentText("Show the location")
                                .setSmallIcon(R.drawable.ic_looooogo)
                                .setContentIntent(contentIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                                .setContentIntent(contentIntent)

                                .setCategory(NotificationCompat.CATEGORY_ALARM)
                                .setSound(uri)
                                .setOnlyAlertOnce(true)
                                .setAutoCancel(true);

                        builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        notificationManager.notify(0, builder.build());

                    }

                }
            }


            public void onCancelled(DatabaseError databaseError) {


            }  });
*/
       dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               String numofprev= String.valueOf(dataSnapshot.getChildrenCount());
                editor.putString("accidentNum", String.valueOf(dataSnapshot.getChildrenCount()));
                editor.commit();
                  // Toast.makeText(YourService.this, numofprev, Toast.LENGTH_SHORT).show();
                buildnoti();



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                editor.putString("accidentNum", String.valueOf(dataSnapshot.getChildrenCount()));
                editor.commit();

                String numofprev= String.valueOf(dataSnapshot.getChildrenCount());

                Intent activityintent= new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent contentIntent =PendingIntent.getActivity(getApplicationContext(),
                        0, activityintent,0);
                if (dataSnapshot.child("Accident"+numofprev+"/Latitude").exists()) {
                    daa = dataSnapshot.child("Accident"+numofprev+"/Latitude").getValue().toString();

                    if (!daa.equals("0")){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "There is an accident",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                .setContentTitle("Accident nearby!")
                                .setContentText("Show the location")
                                .setSmallIcon(R.drawable.ic_looooogo)
                                .setContentIntent(contentIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                                .setContentIntent(contentIntent)

                                .setCategory(NotificationCompat.CATEGORY_ALARM)
                                .setSound(uri)
                                .setOnlyAlertOnce(true)
                                .setAutoCancel(false);     ;

                        builder.setDefaults(Notification.DEFAULT_VIBRATE);
                        notificationManager.notify(0, builder.build());

                    }

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return super.onStartCommand(intent, flags, startId);


    }


public void buildnoti(){
    final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    Intent activityintent= new Intent(getApplicationContext(), MainActivity.class);
    PendingIntent contentIntent =PendingIntent.getActivity(getApplicationContext(),
            0, activityintent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
        .setContentTitle("Accident nearby!")
        .setContentText("Show the location")
        .setSmallIcon(R.drawable.ic_looooogo)
        .setContentIntent(contentIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
        .setContentIntent(contentIntent)

        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setSound(uri)
        .setOnlyAlertOnce(true)
        .setAutoCancel(true);

    builder.setDefaults(Notification.DEFAULT_VIBRATE);
    notificationManager.notify(0, builder.build());


}

}