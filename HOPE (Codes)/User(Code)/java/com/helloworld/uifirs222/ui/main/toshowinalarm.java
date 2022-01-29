package com.helloworld.uifirs222.ui.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloworld.uifirs222.MainActivity;
import com.helloworld.uifirs222.Models.yourmedicinelistmodel;
import com.helloworld.uifirs222.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class toshowinalarm extends AppCompatActivity {
    ListView ImageList;
    int [] Vectors ={
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
    ImageView icon;
    TextView time;
    TextView dose;
    ImageView done;
    ImageView snoze;
    public static final String PREFS_NAME ="30";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toshowinalarm);
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        ImageList=findViewById(R.id.ICONlist);
        final ArrayList<imagemodel> imagemodels= new ArrayList<imagemodel>();
        final toshowinalarm.MyCustomAdapterImage imgadapter = new MyCustomAdapterImage(imagemodels);
        ImageList.setAdapter(imgadapter);

        String check=storage.getString("foralarm","amouta");

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);





        snoze=findViewById(R.id.snooze);
ImageList = findViewById(R.id.ICONlist);
        done=findViewById(R.id.button);
        time=findViewById(R.id.time_txt);
        Intent intentt= getIntent();
        final String Name=intentt.getStringExtra("namee");
        final String Dose=intentt.getStringExtra("dosee");
        final int Icon=intentt.getIntExtra("iconee",0);
        final int Code =intentt.getIntExtra("codee",-1);
        final int Color1=intentt.getIntExtra("coloree1",2);
        final int Color2=intentt.getIntExtra("coloree2",9);

            editor.putString("SHOWINALARMPREVNAME" + Code, Name);
            editor.putString("SHOWINALARMPREVDOSE" + Code, Dose);
            editor.putInt("SHOWINALARMPREVICON" + Code, Icon);
            editor.putInt("SHOWINALARMPREVCOLOR1" + Code, Color1);
            editor.putInt("SHOWINALARMPREVCOLOR2" + Code, Color2);

            editor.commit();



      //  icon.setImageResource(Vectors[+Icon]);

        for (int i=0;i<100;i++){
            if(storage.contains("SHOWINALARMPREVNAME"+i)){



                Toast.makeText(this, "size"+String.valueOf(imagemodels.size())+",,"+i, Toast.LENGTH_SHORT).show();

                if(storage.getInt("SHOWINALARMPREVICON"+i,-1)==0){

                    LayerDrawable not_rounded_pill = (LayerDrawable) getResources().getDrawable(R.drawable.not_rounded_pill);
                    not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[storage.getInt("SHOWINALARMPREVCOLOR1"+i,0)])), PorterDuff.Mode.SRC_IN);
                    not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[storage.getInt("SHOWINALARMPREVCOLOR1"+i,0)])), PorterDuff.Mode.MULTIPLY);
                    not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[storage.getInt("SHOWINALARMPREVCOLOR2"+i,0)])), PorterDuff.Mode.SRC_IN);
                    not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[storage.getInt("SHOWINALARMPREVCOLOR2"+i,0)])), PorterDuff.Mode.MULTIPLY);

                    storage.getInt("SHOWINALARMPREVCOLOR2"+i,0);
                    imagemodels.add(new imagemodel(not_rounded_pill,
                            storage.getString("SHOWINALARMPREVNAME"+i,"notavail"),
                            storage.getString("SHOWINALARMPREVDOSE"+i,"notavail")));
                }else {
                    Drawable img = getResources().getDrawable(Vectors[storage.getInt("SHOWINALARMPREVICON"+i, 1)]);
                    img.setColorFilter(getResources().getColor(mColors[+storage.getInt("SHOWINALARMPREVCOLOR1"+i, 0)]), PorterDuff.Mode.SRC_IN);
                    img.setColorFilter(getResources().getColor(mColors[+storage.getInt("SHOWINALARMPREVCOLOR1"+i, 0)]), PorterDuff.Mode.MULTIPLY);


                    imagemodels.add(new imagemodel(img,
                            storage.getString("SHOWINALARMPREVNAME" + i, "notavail"),
                            storage.getString("SHOWINALARMPREVDOSE" + i, "notavail")));
                }
                String Min=String.valueOf(storage.getInt("Timer1Min"+i,5));
                String Hour=String.valueOf(storage.getInt("Timer1Hour"+i,5));
                time.setText("It is"+" "+Hour+":"+ Min+" "+"Time to take your medications" );


            }
        }
imgadapter.notifyDataSetChanged();
    /*    View someView = findViewById(R.id.ICON);

        // Find the root view
        View root = someView.getRootView();

        // Set the color
        root.setBackgroundColor(getResources().getColor(Color[+Icon]));*/
        final Intent intent =new Intent(toshowinalarm.this ,snoozeReciever.class);
        intent.putExtra("snoozeName",Name);
        intent.putExtra("snoozeDose",Dose);
        intent.putExtra("snoozeIcon",Icon);
        intent.putExtra("snoozeCode",Code);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i=0; i<100;i++){
                    editor.remove("SHOWINALARMPREVNAME"+i);
                    editor.remove("SHOWINALARMPREVDOSE"+i);
                    editor.remove("SHOWINALARMPREVICON"+i);
                    editor.commit();
                }
                PendingIntent.getBroadcast(toshowinalarm.this,5545,intent,0).cancel();

                Intent o = new Intent(toshowinalarm.this,MainActivity.class);
                o.setFlags(FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_CLEAR_TOP|FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(o);
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(Context.
                                NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_CLEAR_TOP|FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(homeIntent);


            }
        });
        snoze.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                final int min = 20;
                final int max = 80;
                final int random = new Random().nextInt((max - min) + 1) + min;
                PendingIntent peendingIntent= PendingIntent.getBroadcast(toshowinalarm.this,
                        random,intent,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+(1000*30), peendingIntent);
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_CLEAR_TOP|FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(homeIntent);
                Toast.makeText(toshowinalarm.this, "Snoozed for 5 minutes", Toast.LENGTH_LONG).show();
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(Context.
                                NOTIFICATION_SERVICE);
                notificationManager.cancelAll();

            }
        });
    }

    private class MyCustomAdapterImage extends BaseAdapter {

        ArrayList<imagemodel> imagemodels= new ArrayList<imagemodel>();
        MyCustomAdapterImage(ArrayList<imagemodel> Items){
            this.imagemodels= Items;
        }

        @Override
        public int getCount() {return imagemodels.size();}

        @Override
        public Object getItem(int position) {
            return imagemodels.get(position).mp1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1= inflater.inflate(R.layout.bitmapitem,null);

            ImageView bm= view1.findViewById(R.id.bitp);
            bm.setImageDrawable(imagemodels.get(i).getMp1());
            TextView name=view1.findViewById(R.id.itemname);
            name.setText(imagemodels.get(i).getName());
            TextView dose=view1.findViewById(R.id.itemdose);
            dose.setText(imagemodels.get(i).getDose());
            return view1;
        }
    }
}
