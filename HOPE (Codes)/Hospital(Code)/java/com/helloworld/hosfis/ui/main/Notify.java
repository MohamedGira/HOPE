package com.helloworld.hosfis.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.helloworld.hosfis.MainActivity;
import com.helloworld.hosfis.R;

import java.util.ArrayList;
import java.util.List;

public class Notify extends AppCompatActivity {
    DatabaseReference dref;

    ArrayList<String> mylist = new ArrayList<String>();
    public static final String PREFS_NAME ="30";

    String[] sArray;
ListView userlist;
CardView notify,cancel;
    String Speciali;
    String docname;
    String time;
    final String Specialities []={"Skin","Teeth","Child","Brain & Nerves","Bones","Infertility",
            "Ear, Nose and Throat","Heart","Immunity","Heart & Chest"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        Intent intent= getIntent();
        Speciali=intent.getStringExtra("SpecialName");
        final String HospName=storage.getString("HospitalName","Zembaboy");
        notify=findViewById(R.id.notifycard);
        cancel=findViewById(R.id.cancelcard);
        dref = FirebaseDatabase.getInstance().getReference();
        final String pathmainSpeciality="Hospitals/" + HospName+ "/Specialities/";

        userlist = findViewById(R.id.numberlist);
        final ArrayList<usermodel> number = new ArrayList<usermodel>();

        final MyCustomAdapter1 medadpter = new MyCustomAdapter1(number);
        userlist.setAdapter(medadpter);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
if(dataSnapshot.child(pathmainSpeciality+Speciali+"/doctor1patients").exists()) {

    number.removeAll(number);



    for (DataSnapshot uniqueKeySnapshot : dataSnapshot.child(pathmainSpeciality + Speciali + "/doctor1patients").getChildren()) {
          number.add(new usermodel(uniqueKeySnapshot.getKey()));
       }



    if (mylist.size() != 0) {
        sArray = new String[mylist.size()];
        for (int o = 0; o < sArray.length; o++) {
            sArray[o] = mylist.get(o);
        }
    }
    if (sArray != null && mylist.size() != 0) {
    //    Toast.makeText(Notify.this, String.valueOf(sArray.length), Toast.LENGTH_SHORT).show();

    }

    medadpter.notifyDataSetChanged();


}
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
for (int i=0;i<number.size();i++){

    DatabaseReference myRefus = database.getReference("Users/"+number.get(i).Name+"/Notified to Cancel");
    myRefus.setValue("False");
    DatabaseReference myrefdocname = database.getReference("Users/"+number.get(i).Name+"/Doctor name");
    myrefdocname .setValue(storage.getString(Speciali+"/Docname","dontknow"));
    DatabaseReference myrefdoctime = database.getReference("Users/"+number.get(i).Name+"/Doctor time");
    myrefdoctime .setValue(storage.getString(Speciali+"/DocTimefornoti","dontknow"));
    DatabaseReference myrefdocspec= database.getReference("Users/"+number.get(i).Name+"/Doctor speciality");
    myrefdocspec .setValue(Speciali);
    DatabaseReference myrefhosp= database.getReference("Users/"+number.get(i).Name+"/Hospital name");
    myrefhosp .setValue(HospName);
    DatabaseReference myRefuser = database.getReference("Users/"+number.get(i).Name+"/Notified to Attend");
    myRefuser.setValue("True");
}}
        });


cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        new AlertDialog.Builder(Notify.this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to cancel All appointments")
                .setIcon(R.drawable.ic_sure)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        editor.putBoolean(Speciali +"Available",false);
                        editor.commit();

                        for (int i=0;i<number.size();i++){
                            DatabaseReference myRefuser = database.getReference("Users/"+number.get(i).Name+"/Notified to Cancel");
                            myRefuser.setValue("True");
                            DatabaseReference myRefusera = database.getReference("Users/"+number.get(i).Name+"/Notified to Attend");
                            myRefusera.setValue("False");
                            DatabaseReference myrefdocname = database.getReference("Users/"+number.get(i).Name+"/Doctor name");
                            myrefdocname .setValue(storage.getString(Speciali+"/Docname","dontknow"));
                            DatabaseReference myrefdoctime = database.getReference("Users/"+number.get(i).Name+"/Doctor time");
                            myrefdoctime .setValue(storage.getString(Speciali+"/DocTimefornoti","dontknow"));
                            DatabaseReference myrefdocspec= database.getReference("Users/"+number.get(i).Name+"/Doctor speciality");
                            myrefdocspec .setValue(Speciali);
                            DatabaseReference myRefspecial = database.getReference(pathmainSpeciality+Speciali+"/Availability");
                            myRefspecial.setValue("False");




                            for(int o=0;o<Specialities.length;o++){
                                boolean avail= storage.getBoolean(Specialities[+o]+"Available",false);
                                if(avail){
                                    String Name=storage.getString(Specialities[+o]+"/Docname","don't");
                                    String Time=storage.getString(Specialities[+o]+"/DocTime","don't");
                                    DatabaseReference myRefavail = database.getReference(pathmainSpeciality+Specialities[+o]+"/Availability");
                                    DatabaseReference myRefname= database.getReference(pathmainSpeciality+Specialities[+o]+"/doctor1");
                                    DatabaseReference myReftime= database.getReference(pathmainSpeciality+Specialities[+o]+"/doctor1time");
                                    myRefavail.setValue("True");
                                    myRefname.setValue(Name);
                                    myReftime.setValue(Time);
                                }
                                else{
                                    DatabaseReference myRefavail = database.getReference(pathmainSpeciality+Specialities[+o]+"/Availability");
                                    myRefavail.setValue("False");}

                            }




                        }
                        DatabaseReference myRefappo = database.getReference(pathmainSpeciality + Speciali + "/doctor1patients");
                        myRefappo.removeValue();
                        editor.putBoolean(Speciali+"Available",false);
                        editor.remove(Speciali+"/Docname");
                        editor.remove(Speciali+"/DocTime");
                        editor.commit();
                        Intent intent1 = new Intent(Notify.this,MainActivity.class);
                        startActivity(intent1);
                    }})
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }
});
    }

    private class MyCustomAdapter1 extends BaseAdapter {


        ArrayList<usermodel> usermodels= new ArrayList<usermodel>();
        MyCustomAdapter1(ArrayList<usermodel> Items){
            this.usermodels= Items;
        }

        @Override
        public int getCount() {
            return usermodels.size();

        }

        @Override
        public Object getItem(int position) {
            return usermodels.get(position).getpatphone();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view2= inflater.inflate(R.layout.user_item,null);

            TextView textView=(TextView) view2.findViewById(R.id.userNum);

            textView.setText(usermodels.get(i).getpatphone());

            return view2;
        }
    }
}
