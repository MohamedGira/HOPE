package com.helloworld.uifirs222;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.helloworld.uifirs222.Models.nearhospitalmodel;
import com.helloworld.uifirs222.Models.specializationmodel;
import com.helloworld.uifirs222.ui.main.Hospitalfrag;

import java.util.ArrayList;

public class Hospitaldetail extends AppCompatActivity {
    public final static int REQUEST_CODE = 10101;
private static final int  REQUEST_CALL=1;

    public static final String PREFS_NAME ="30" ;
    ImageView back_button;
    ImageView HospitalIcon;
    TextView HospitalName;
    TextView HospitalType;
    TextView HospitalDistance;
    TextView BloodAp;
    TextView BloodBp;
    TextView BloodAn;
    TextView BloodBn;
    TextView BloodABp;
    TextView BloodABn;
    TextView BloodOp;
    TextView BloodOn;
    TextView NursuiryI;
    TextView NursuiryII;
    TextView NursuiryIII;
    TextView NursuiryIIIA;
    TextView NursuiryIIIB;
    TextView NursuiryIIIC;
    TextView Bedsnum;
    TextView Emer,availat,patnum;
CardView mapcard,book,call;
    DatabaseReference dref,bookref;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitaldetail);
        getSupportActionBar().hide();

        final ListView nl = findViewById(R.id.lisssst);
        final ArrayList<specializationmodel> specialItem = new ArrayList<specializationmodel>();
        final Hospitaldetail.MyCustomAdapterre specialadapt = new Hospitaldetail.MyCustomAdapterre(specialItem);
nl.setAdapter(specialadapt);
call= findViewById(R.id.callcard);
mapcard=findViewById(R.id.mapcard);
        dref = FirebaseDatabase.getInstance().getReference();
        back_button = findViewById(R.id.back_button);
        HospitalIcon = findViewById(R.id.hospitalimg);
        HospitalName = findViewById(R.id.hospitalname);
        HospitalType = findViewById(R.id.hospitalkind);
        HospitalDistance=findViewById(R.id.hospitaldistance);

        BloodAp = findViewById(R.id.bloodtypeAplusnum);
        BloodAn = findViewById(R.id.bloodtypeAminusnum);
        BloodBp = findViewById(R.id.bloodtypeBplusnum);
        BloodBn = findViewById(R.id.bloodtypeBminusnum);
        BloodABp = findViewById(R.id.bloodtypeABplusnum);
        BloodABn = findViewById(R.id.bloodtypeABminusnum);
        BloodOp=findViewById(R.id.bloodtypeOplusnum);
        BloodOn=findViewById(R.id.bloodtypeOminusnum);
        NursuiryI = findViewById(R.id.lvl1nurnum);
        NursuiryII = findViewById(R.id.lvl2nurnum);
        NursuiryIII=findViewById(R.id.lvl3nurnum);
        NursuiryIIIA = findViewById(R.id.lvl4nurnum);
        NursuiryIIIB = findViewById(R.id.lvl5nurnum);
        NursuiryIIIC = findViewById(R.id.lvl6nurnum);
        Bedsnum = findViewById(R.id.numberofbedsavilabletxt);
        Emer = findViewById(R.id.emergencyavailablilitytxt);









//for dialog
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;

        final Dialog dialogbbok = new Dialog(this);
        dialogbbok.setContentView(R.layout.booking_dialog);
        availat=dialogbbok.findViewById(R.id.doctext);
        patnum=dialogbbok.findViewById(R.id.patients);
        book=dialogbbok.findViewById(R.id.book);



        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        final String bloodtypes []={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
        final String nursierys []={"I","II","III","IIIA","IIIB","IIIC"};
        final TextView text[]={BloodAp,BloodAn,BloodBp,BloodBn,BloodABp,BloodABn,BloodOp,BloodOn};
        final TextView textNur[]={NursuiryI,NursuiryII,NursuiryIII,NursuiryIIIA,NursuiryIIIB,NursuiryIIIC};
        final String Specialities []={"Skin","Teeth","Child","Brain & Nerves","Bones","Infertility",
                "Ear, Nose and Throat","Heart","Immunity","Heart & Chest"};

        Intent intent = getIntent();
        final String name = intent.getStringExtra("Name");
        String type = intent.getStringExtra("Type");
        final String number = intent.getStringExtra("Number");
        String distance = intent.getStringExtra("Distance");
        final String location = intent.getStringExtra("Location");
        int Logo = intent.getIntExtra("Logo", R.drawable.ic_android);
        HospitalIcon.setImageResource(Logo);
        HospitalName.setText(name);
        HospitalType.setText(type);
        HospitalDistance.setText(distance+"Km");
call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (ContextCompat.checkSelfPermission(Hospitaldetail.this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Hospitaldetail.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else {String dial = "tel:" + number;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

        }


    }
});

final String userPhone= storage.getString("User number", "0");

        mapcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?"+"&daddr=" +location);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       //blood loop
        for (int r=0; r<text.length;r++)
        {

          String w=  storage.getString("Hospitals/" + name + "/BloodTypes/" + bloodtypes[+r],"0");
            text[+r].setText(w);
            if(w.equals(0)||w.equals("0")){
                text[+r].setText("");
                text[+r].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close, 0, 0, 0);}
            else if(w.equals("")){
                text[+r].setText("");
                text[+r].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_refresh, 0, 0, 0);
            }
            else{
                text[+r].setText(w);}

           }
      // nursiry loop
        for (int r=0; r<textNur.length;r++)
        {

            String w=  storage.getString("Hospitals/" + name + "/Nurseries/" + nursierys[+r],"0");
            textNur[+r].setText(w);
            if(w.equals(0)||w.equals("0")){
                textNur[+r].setText("");
                textNur[+r].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close, 0, 0, 0);}
            else if(w.equals("")){
                textNur[+r].setText("");
                textNur[+r].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_refresh, 0, 0, 0);
            }
            else{
                textNur[+r].setText(w);}

        }
     //specialities loop
        for (int r=0; r<Specialities.length;r++)
        {

            if(storage.getString("Hospitals/" + name+ "/Specialities/" + Specialities[+r]+"/Availability","False")
                    .equals("True")){
                String doctor1Name=
                        storage.getString("Hospitals/" + name + "/Specialities/" + Specialities[+r]+"/doctor1","False");

                String doctor1Time=
                        storage.getString("Hospitals/" + name + "/Specialities/" + Specialities[+r]+"/doctor1time","False");

                specialItem.add( new specializationmodel(Specialities[+r],doctor1Name,doctor1Time));

        }
        }
       nl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selectedspeciality= (String) parent.getItemAtPosition(position);
if(isNetworkAvailable()){

                final String doctor1Name=
                                storage.getString("Hospitals/" + name + "/Specialities/" + selectedspeciality+"/doctor1","False");

                final String doctor1Time=
                                storage.getString("Hospitals/" + name+ "/Specialities/" + selectedspeciality+"/doctor1time","False");

                long doctor1patient=
                                storage.getLong("Hospitals/" + name + "/Specialities/" + selectedspeciality+"/doctor1patients",0);

                    availat.setText("Doctor"+" "+doctor1Name+" "+"is availible at"+" "+ doctor1Time+".");
                    if(doctor1patient==0){
                        patnum.setText("No patients ahead");

                    }
                    else{
                        patnum.setText("Number of patients ahead:"+" "+doctor1patient);
                    }

                    book.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new AlertDialog.Builder(Hospitaldetail.this)
                                    .setTitle("Confirmation")
                                    .setMessage("Do you want to book?")
                                    .setIcon(R.drawable.ic_sure)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            if(isNetworkAvailable()){
                                                String reservationpath="Hospitals/" + name + "/Specialities/" + selectedspeciality+"/doctor1patients/"+userPhone;
                                            bookref = FirebaseDatabase.getInstance().getReference(reservationpath);
                                            bookref.setValue("booked");
                                                editor.putString(reservationpath+"/docname",doctor1Name);
                                                editor.putString(reservationpath+"/doctime",doctor1Time);
                                                editor.putString(reservationpath+"/hospnameandspecial",selectedspeciality);
                                                editor.commit();

                                                dialogbbok.dismiss();
                                            Toast.makeText(Hospitaldetail.this, "Booked sucsessfully", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(Hospitaldetail.this, "To book, Please connect to Internet", Toast.LENGTH_LONG).show();
                                            }
                                        }})
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(Hospitaldetail.this, "K", Toast.LENGTH_SHORT).show();
                                            dialogbbok.dismiss();
                                        }
                                    }).show();


                        }
                    });
                dialogbbok.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialogbbok.show();
}
            else
{
    Toast.makeText(Hospitaldetail.this, "To book, Please connect to Internet", Toast.LENGTH_LONG).show();
}}
        });
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                    for (int b=0; b<text.length;b++)
                    {

                        if (dataSnapshot.child("Hospitals/"+name+"/BloodTypes").exists()) {
                            if(dataSnapshot.child("Hospitals/" + name + "/BloodTypes/" + bloodtypes[+b]).getValue().toString().trim().equals("0")||
                                    dataSnapshot.child("Hospitals/" + name + "/BloodTypes/" + bloodtypes[+b]).getValue().toString().trim().equals(0)){
                                text[+b].setText("");
                                text[+b].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close,0,0,0);

                                editor.putString("Hospitals/" + name + "/BloodTypes/" + bloodtypes[+b],
                                        dataSnapshot.child("Hospitals/" + name + "/BloodTypes/" + bloodtypes[+b]).getValue().toString().trim());
                                editor.commit();

                            }
                            else
                            {
                                text[+b].setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                                text[+b].setText(dataSnapshot.child("Hospitals/"+name+"/BloodTypes/"+bloodtypes[+b]).getValue().toString().trim());
                                editor.putString("Hospitals/" + name + "/BloodTypes/" + bloodtypes[+b],
                                        dataSnapshot.child("Hospitals/" + name + "/BloodTypes/" + bloodtypes[+b]).getValue().toString().trim());
                             editor.commit();

                            }

                    }




                    }

                     for (int b=0; b<textNur.length;b++)
                    {

                    if (dataSnapshot.child("Hospitals/"+name+"/Nurseries").exists()) {
                        if(dataSnapshot.child("Hospitals/" + name + "/Nurseries/" + nursierys[+b]).getValue().toString().trim().equals("0")||
                                dataSnapshot.child("Hospitals/" + name + "/Nurseries/" + nursierys[+b]).getValue().toString().trim().equals(0)){
                            textNur[+b].setText("");
                            textNur[+b].setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close,0,0,0);

                            editor.putString("Hospitals/" + name + "/Nurseries/" + nursierys[+b],
                                    dataSnapshot.child("Hospitals/" + name + "/Nurseries/" + nursierys[+b]).getValue().toString().trim());
                            editor.commit();}
                        else
                        {textNur[+b].setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                            textNur[+b].setText(dataSnapshot.child("Hospitals/"+name+"/Nurseries/"+nursierys[+b]).getValue().toString().trim());
                            editor.putString("Hospitals/" + name + "/Nurseries/" + nursierys[+b],
                                    dataSnapshot.child("Hospitals/" + name + "/Nurseries/" + nursierys[+b]).getValue().toString().trim());
                            editor.commit();}}
                }


                if (dataSnapshot.child("Hospitals/"+name+"/Specialities").exists()) {
                    specialItem.removeAll(specialItem);
                    specialadapt.notifyDataSetChanged();
                 //   Toast.makeText(Hospitaldetail.this,String.valueOf(dataSnapshot.child("Hospitals/"+name+"/Specialities").getChildrenCount()), Toast.LENGTH_SHORT).show();
                    for (int bb=0; bb<Specialities.length;bb++){
                        String Availability="Hospitals/" + name + "/Specialities/" + Specialities[+bb]+"/Availability";
                        String doctor1="Hospitals/" + name + "/Specialities/" + Specialities[+bb]+"/doctor1";
                        String doctor1time="Hospitals/" + name + "/Specialities/" + Specialities[+bb]+"/doctor1time";
                        String doctor1patients="Hospitals/" + name + "/Specialities/" + Specialities[+bb]+"/doctor1patients";
                        String doctor2="Hospitals/" + name + "/Specialities/" + Specialities[+bb]+"/doctor2";

                        String doctor2time="Hospitals/" + name + "/Specialities/" + Specialities[+bb]+"/doctor2time";
                        {if(dataSnapshot.child(Availability).exists()){
                            editor.putString(Availability,dataSnapshot.child(Availability).getValue().toString().trim());
                            if(dataSnapshot.child(Availability).getValue().toString().trim().equals("True")){
                                editor.putString(doctor1,dataSnapshot.child(doctor1).getValue().toString().trim());
                                editor.putString(doctor1time,dataSnapshot.child(doctor1time).getValue().toString().trim());

                                specialItem.add( new specializationmodel(Specialities[+bb],dataSnapshot.child(doctor1).getValue().toString().trim()
                                        ,dataSnapshot.child(doctor1time).getValue().toString().trim()));
                                specialadapt.notifyDataSetChanged();


                                if(dataSnapshot.child(doctor1patients).exists()){
                                    editor.putLong(doctor1patients,dataSnapshot.child(doctor1patients).getChildrenCount());

                                }
                                else{
                                    editor.putLong(doctor1patients,0);

                                }

                                if(dataSnapshot.child(doctor2).exists()){

                                    editor.putString(doctor2,dataSnapshot.child(doctor2).getValue().toString().trim());
                                    editor.putString(doctor2time,dataSnapshot.child(doctor2time).getValue().toString().trim());

                            }

                        }
                            editor.commit();
                        }
                    }}}






                if (dataSnapshot.child("Hospitals/"+name+"Beds").exists()) {
                    Bedsnum.setText(dataSnapshot.child("Hospitals/"+name+"Beds").getValue().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        nl.setOnTouchListener(new ListView.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
    }

    private class MyCustomAdapterre extends BaseAdapter {


        ArrayList<specializationmodel> specialItem= new ArrayList<specializationmodel>();
        MyCustomAdapterre(ArrayList<specializationmodel> Items){
            this.specialItem= Items;
        }

        @Override
        public int getCount() {return specialItem.size();}

        @Override
        public Object getItem(int position) { return specialItem.get(position).Specialization;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1= inflater.inflate(R.layout.specia_ltem,null);

            TextView textView=(TextView) view1.findViewById(R.id.whatSpecial);
            TextView textView2=(TextView) view1.findViewById(R.id.Docname);
            TextView textView3 = (TextView)  view1.findViewById(R.id.doctime);
            textView.setText(specialItem.get(i).Specialization);
            textView2.setText(specialItem.get(i).Name);
            textView3.setText(specialItem.get(i).Time);
            return view1;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }}
