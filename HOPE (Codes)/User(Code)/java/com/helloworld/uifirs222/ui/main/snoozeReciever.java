package com.helloworld.uifirs222.ui.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.helloworld.uifirs222.MainActivity;
import com.helloworld.uifirs222.R;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class snoozeReciever extends BroadcastReceiver {

    int Vectors []={
            R.drawable.ic_white_blue_capsule,
            R.drawable.ic_white_red_capsule,
            R.drawable.ic_white_green_capsule,
            R.drawable.ic_white_pink_capsule,
            R.drawable.ic_yellow_blue_capsule,
            R.drawable.ic_red_blue_capsule,
            R.drawable.ic_white_pill,
            R.drawable.ic_pink_pill,
            R.drawable.ic_yellow_pill,
            R.drawable.ic_blue_pill,
            R.drawable.ic_green_pill,
            R.drawable.ic_sringe,

    };

    int Color []={
            R.color.lblue,
            R.color.RED,
            R.color.lgreen,
            R.color.lpink,
            R.color.lblue,
            R.color.lblue,
            R.color.babyblue,
            R.color.lpink,
            R.color.lyellow,
            R.color.lblue,
            R.color.lgreen,
            R.color.linsu,


    };
    public static final String PREFS_NAME ="30";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {



        Uri notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        NotificationManager manager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);

//**add this line**
        notificationIntent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

//**edit this line to put requestID as requestCode**
        PendingIntent contentIntent = PendingIntent.getActivity(context, 45484,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        int Icon= intent.getIntExtra("snoozeIcon",5);
        String Name= intent.getStringExtra("snoozeName");
        String Dose= intent.getStringExtra("snoozeDose");
        String Code= intent.getStringExtra("snoozeCode");
        Toast.makeText(context, String.valueOf(Icon), Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(Name)
                .setContentText(Dose)
                .setSmallIcon(Vectors[+Icon])
                .setContentIntent(contentIntent)
                .setSound(notification);

        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        manager.notify(910, builder.build());


        Intent intentt= new Intent(context,toshowinalarm.class);
        intentt.putExtra("namee",Name);
        intentt.putExtra("dosee",Dose);
        intentt.putExtra("iconee",Icon);
        intentt.putExtra("codee",Code);
        intentt.setFlags(FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_CLEAR_TOP|FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intentt);

    }
}
