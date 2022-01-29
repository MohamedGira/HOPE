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

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyBrodcastReciever extends BroadcastReceiver {

    int[] Vectors = {
            R.drawable.not_rounded_pill,
            R.drawable.ic_rounded_pill,
            R.drawable.ic_oval_pill,
            R.drawable.ic_pill_tape,
            R.drawable.ic_pill_jar,
            R.drawable.ic_medicine_jar,
            R.drawable.ic_drinkable_med,
            R.drawable.ic_syrenge,
            R.drawable.ic_droplet_eye,
            R.drawable.ic_marham,
            R.drawable.ic_tuberclouses,
            R.drawable.ic_pipette,
            R.drawable.ic_pharma_jar
    };

    public int[] mColors = {R.color.red, R.color.lightred, R.color.babyred, R.color.redpill, R.color.Tanred,
            R.color.orange, R.color.lightorange, R.color.babyorange, R.color.orangepill, R.color.Tanorange,
            R.color.yellow, R.color.lightyellow, R.color.babyyellow, R.color.yellowpill, R.color.Tanyellow,
            R.color.green, R.color.lightgreen, R.color.babygreen, R.color.greenpill, R.color.Tangreen,
            R.color.purple, R.color.lightpurple, R.color.babypurple, R.color.purplepill, R.color.Tanpurple
            , R.color.Blue, R.color.lightblue, R.color.babyblue, R.color.bluepill, R.color.Tanblue, R.color.white};
    public static final String PREFS_NAME = "30";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {


        final SharedPreferences storage = context.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        Intent notificationIntent = new Intent(context, MainActivity.class);

//**add this line**
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

//**edit this line to put requestID as requestCode**
        PendingIntent contentIntent = PendingIntent.getActivity(context, 45484, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        int i = intent.getIntExtra("requestcode", -1);
        Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(storage.getString("NAME" + i, "not avail"))
                .setContentText((storage.getString("DOSE" + i, "not avail")))
                .setSmallIcon(Vectors[+storage.getInt("ICON" + i, 0)])
                .setContentIntent(contentIntent)
                .setSound(notification);

        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        manager.notify(i, builder.build());


        Intent intentt = new Intent(context, toshowinalarm.class);
        intentt.putExtra("namee", storage.getString("NAME" + i, "not avail"));
        intentt.putExtra("dosee", storage.getString("DOSE" + i, "not avail"));
        intentt.putExtra("iconee", storage.getInt("ICON" + i, 0));
        intentt.putExtra("codee", storage.getInt("Code" + i, i));
        intentt.putExtra("coloree1", storage.getInt("Color1" + i, 12));
        intentt.putExtra("coloree2", storage.getInt("Color2" + i, 12));


        intentt.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intentt);

    }
}
