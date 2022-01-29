package com.helloworld.uifirs222.ui.main;


import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.helloworld.uifirs222.Hospitaldetail;
import com.helloworld.uifirs222.MainActivity;
import com.helloworld.uifirs222.Models.nearhospitalmodel;
import com.helloworld.uifirs222.Models.yourappomodel;
import com.helloworld.uifirs222.Models.yourmedicinelistmodel;
import com.helloworld.uifirs222.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class youfrag extends Fragment {

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
    Location Alfouad, Rehan, Qasr, Demrdash, Cleopatra, DarElFouad,
            MahmoudSpecial, Dreamland, medicalcenteroctober, CityClinic, Alwaha, OctoberMilitary, AsiaClinics, ElShoroukHospital,
            SphinxSpecialized, Alharam, Tabarak, OctoberClinic, AlMehwar,
            Wafa, Salam, MaadiMilitary, QueenClinic, Sudan, ShifaSpecialist, Helal,
            Mohamady, Pola, zohoor, Horus, Ainshams;
    double Alfouaddis, Rehandis, Qasrdis, Demdis, Cleopatradis, DarElFouaddis,
            MahmoudSpecialdis, Dreamlanddis, medicalcenteroctoberdis, CityClinicdis, Alwahadis, OctoberMilitarydis, AsiaClinicsdis, ElShoroukHospitaldis,
            SphinxSpecializeddis, Alharamdis, Tabarakdis, OctoberClinicdis,
            AlMehwardis, Wafadis, Salamdis, MaadiMilitarydis, QueenClinicdis, Sudandis, ShifaSpecialistdis, Helaldis, Mohamadydis, Poladis, zohoordis, Horusdis, Ainshamsdis;

    private static final String PREFS_NAME = "30";
    final String Specialities[] = {"Skin", "Teeth", "Child", "Brain & Nerves", "Bones", "Infertility",
            "Ear, Nose and Throat", "Heart", "Immunity", "Heart & Chest"};
    final String Names[] = {
            "Al-Fouad", "Rehan", "Elqasr Eleiny", "Demerdash",
            "Cleopatra", "Dar ElFouad ", "Mahmoud ", "Dreamland ", "October Medical center "
            , "City Clinic", "Al-waha ", "October ", "Asia Clinic", "Al-Shorouk ",
            "Sphinx", "Al-haram ", "Tabarak ", "October ", "Al-Mehwar",
            "Al-Wafa", "Al-Salam ", "Maadi", "Queen Clinic", "Al-Sudan",
            "Al-Shifaa", "Al-Helal", "Al-Mohamady", "Miswsud ", "Saint Pola",
            "Al-zohoor", "Horus ", "Ainshams "};
    final String Types[] = {
            "Private", "Clinic", "Public", "Public",
            "Private", "Private", "Private", "Private", "Center",
            "Clinic", "public", "Military", "Clinic", "public",
            "Public", "public", "Private", "Clinic", "Public",
            "Clinic", "Private", "Military", "Clinic", "Public",
            "Public", "Public", "Public", "Private",
            "public", "Private", "Maternity"};
    final int Icons[] = {
            R.mipmap.ic_alfouad, R.mipmap.ic_rehan,
            R.mipmap.ic_qasr, R.mipmap.ic_demerdash, R.mipmap.ic_cleop,
            R.mipmap.ic_darelfouad, R.mipmap.ic_mahmoud, R.mipmap.ic_dream,
            R.mipmap.ic_ocrobermedicalcenter, R.mipmap.ic_city, R.mipmap.ic_waha,
            R.mipmap.ic_octobero, R.mipmap.ic_asia, R.mipmap.ic_shorok,
            R.mipmap.ic_shpinx, R.mipmap.ic_haram, R.mipmap.ic_tabarak,
            R.mipmap.ic_octpbern, R.mipmap.ic_mehwar, R.mipmap.ic_wafa,
            R.mipmap.ic_salam, R.mipmap.ic_maadi, R.mipmap.ic_queen,
            R.mipmap.ic_sudan, R.mipmap.ic_shifa, R.mipmap.ic_helal_round,
            R.mipmap.ic_mohammady, R.mipmap.ic_san_round,
            R.mipmap.ic_zohoor, R.drawable.ic_add, R.mipmap.ic_ain,
    };
    CardView navigate, profile, remove, aadd;
    Dialog decisionDialog;

    DatabaseReference dref;

    public youfrag() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_youfrag, container, false);


        aadd = V.findViewById(R.id.aadd);
        final ListView lss = (ListView) V.findViewById(R.id.youappolist);
        final FloatingActionButton add = V.findViewById(R.id.fab);
        final ListView ls = (ListView) V.findViewById(R.id.youmedlist);


//appointments decision dialog
        decisionDialog = new Dialog(getContext());
        decisionDialog.setContentView(R.layout.decision_dialog);
        navigate = decisionDialog.findViewById(R.id.navigate);
        profile = decisionDialog.findViewById(R.id.view_provile);
        remove = decisionDialog.findViewById(R.id.remove);

// Shared prefrences and database implemntations
        final SharedPreferences storage = this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = storage.edit();
        dref = FirebaseDatabase.getInstance().getReference();


        //getting the last available location from the main activity

        double mylat = Double.valueOf(storage.getString("LastLat", "30"));
        double mylong = Double.valueOf(storage.getString("LastLong", "30"));
        final Location me
                = new Location("");
        me.setLatitude(mylat);
        me.setLongitude(mylong);


        //Setting hospials location (in case of appointment)

        {
            Alfouad
                    = new Location("");
            Alfouad.setLatitude(30.0785468);
            Alfouad.setLongitude(31.2765421);
            Rehan = new Location("");
            Rehan.setLatitude(29.992168);
            Rehan.setLongitude(31.166988);
            Qasr = new Location("");
            Qasr.setLatitude(30.0262814);
            Qasr.setLongitude(31.234475);
            Demrdash = new Location("");
            Demrdash.setLatitude(30.0781932);
            Demrdash.setLongitude(31.2756256);
            Cleopatra = new Location("");
            Cleopatra.setLatitude(30.0929476);
            Cleopatra.setLongitude(31.3299626);


            DarElFouad = new Location("");
            DarElFouad.setLatitude(29.9973825);
            DarElFouad.setLongitude(30.9652677);
            MahmoudSpecial = new Location("");
            MahmoudSpecial.setLatitude(30.0417599);
            MahmoudSpecial.setLongitude(31.0937094);
            Dreamland = new Location("");
            Dreamland.setLatitude(29.9699343);
            Dreamland.setLongitude(31.0344061);
            medicalcenteroctober = new Location("");
            medicalcenteroctober.setLatitude(29.9343231);
            medicalcenteroctober.setLongitude(30.9769704);
            CityClinic = new Location("");
            CityClinic.setLatitude(29.9486387);

            CityClinic.setLongitude(31.0918808);
            Alwaha = new Location("");
            Alwaha.setLatitude(29.9323505);
            Alwaha.setLongitude(31.0132771);
            OctoberMilitary = new Location("");
            OctoberMilitary.setLatitude(29.9323505);
            OctoberMilitary.setLongitude(31.0132771);
            AsiaClinics = new Location("");
            AsiaClinics.setLatitude(29.9323505);
            AsiaClinics.setLongitude(31.0132771);
            ElShoroukHospital = new Location("");
            ElShoroukHospital.setLatitude(29.9323135);
            ElShoroukHospital.setLongitude(31.0132769);
            SphinxSpecialized = new Location("");
            SphinxSpecialized.setLatitude(29.9322764);
            SphinxSpecialized.setLongitude(31.0132767);
            Alharam = new Location("");
            Alharam.setLatitude(29.9322394);
            Alharam.setLongitude(31.0132765);
            Tabarak = new Location("");
            Tabarak.setLatitude(29.9941414);
            Tabarak.setLongitude(31.1460766);
            OctoberClinic = new Location("");
            OctoberClinic.setLatitude(29.9705175);
            OctoberClinic.setLongitude(30.9389081);
            AlMehwar = new Location("");
            AlMehwar.setLatitude(30.0094253);
            AlMehwar.setLongitude(30.9950951);
            Wafa = new Location("");
            Wafa.setLatitude(29.9732546);
            Wafa.setLongitude(31.1812977);
            Salam = new Location("");
            Salam.setLatitude(30.0266682);
            Salam.setLongitude(31.1817866);
            MaadiMilitary = new Location("");
            MaadiMilitary.setLatitude(30.0502055);
            MaadiMilitary.setLongitude(31.2070936);
            QueenClinic = new Location("");
            QueenClinic.setLatitude(29.9587115);
            QueenClinic.setLongitude(31.2495729);

            Sudan = new Location("");
            Sudan.setLatitude(30.0737429);
            Sudan.setLongitude(31.1996323);
            ShifaSpecialist = new Location("");
            ShifaSpecialist.setLatitude(30.1421833);
            ShifaSpecialist.setLongitude(31.3100228);
            Helal = new Location("");
            Helal.setLatitude(30.1422016);
            Helal.setLongitude(31.3201765);
            Mohamady = new Location("");
            Mohamady.setLatitude(30.1454433);
            Mohamady.setLongitude(31.3185523);

            Pola = new Location("");
            Pola.setLatitude(30.079702);
            Pola.setLongitude(31.2817742);
            zohoor = new Location("");
            zohoor.setLatitude(30.1454433);
            zohoor.setLongitude(31.3185523);
            Horus = new Location("");
            Horus.setLatitude(30.0807093);
            Horus.setLongitude(31.2823762);
            Ainshams = new Location("");
            Ainshams.setLatitude(30.0734448);
            Ainshams.setLongitude(31.2780994);


            Helaldis = Math.round((me.distanceTo(Helal) / 1000) * 100.0) / 100.0;
            Alfouaddis = Math.round((me.distanceTo(Alfouad) / 1000) * 100.0) / 100.0;
            Rehandis = Math.round((me.distanceTo(Rehan) / 1000) * 100.0) / 100.0;
            Qasrdis = Math.round((me.distanceTo(Qasr) / 1000) * 100.0) / 100.0;
            Demdis = Math.round((me.distanceTo(Demrdash) / 1000) * 100.0) / 100.0;
            Salamdis = Math.round((me.distanceTo(Salam) / 1000) * 100.0) / 100.0;
            MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary) / 1000) * 100.0) / 100.0;
            Dreamlanddis = Math.round((me.distanceTo(Dreamland) / 1000) * 100.0) / 100.0;
            Cleopatradis = Math.round((me.distanceTo(Cleopatra) / 1000) * 100.0) / 100.0;
            medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober) / 1000) * 100.0) / 100.0;
            CityClinicdis = Math.round((me.distanceTo(CityClinic) / 1000) * 100.0) / 100.0;
            Alwahadis = Math.round((me.distanceTo(Alwaha) / 1000) * 100.0) / 100.0;
            OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary) / 1000) * 100.0) / 100.0;
            AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics) / 1000) * 100.0) / 100.0;
            ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital) / 1000) * 100.0) / 100.0;
            SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized) / 1000) * 100.0) / 100.0;
            Alharamdis = Math.round((me.distanceTo(Alharam) / 1000) * 100.0) / 100.0;
            Tabarakdis = Math.round((me.distanceTo(Tabarak) / 1000) * 100.0) / 100.0;
            OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic) / 1000) * 100.0) / 100.0;
            AlMehwardis = Math.round((me.distanceTo(AlMehwar) / 1000) * 100.0) / 100.0;
            Wafadis = Math.round((me.distanceTo(Wafa) / 1000) * 100.0) / 100.0;
            Sudandis = Math.round((me.distanceTo(Sudan) / 1000) * 100.0) / 100.0;
            ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist) / 1000) * 100.0) / 100.0;
            Mohamadydis = Math.round((me.distanceTo(Mohamady) / 1000) * 100.0) / 100.0;
            zohoordis = Math.round((me.distanceTo(zohoor) / 1000) * 100.0) / 100.0;
            Horusdis = Math.round((me.distanceTo(Horus) / 1000) * 100.0) / 100.0;
            Poladis = Math.round((me.distanceTo(Pola) / 1000) * 100.0) / 100.0;
            Ainshamsdis = Math.round((me.distanceTo(Ainshams) / 1000) * 100.0) / 100.0;
            DarElFouaddis = Math.round((me.distanceTo(DarElFouad) / 1000) * 100.0) / 100.0;
            MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial) / 1000) * 100.0) / 100.0;
            QueenClinicdis = Math.round((me.distanceTo(QueenClinic) / 1000) * 100.0) / 100.0;
        }
        final Location Locations[] = {
                Alfouad, Rehan, Qasr, Demrdash, Cleopatra, DarElFouad,
                MahmoudSpecial, Dreamland, medicalcenteroctober, CityClinic, Alwaha, OctoberMilitary, AsiaClinics, ElShoroukHospital,
                SphinxSpecialized, Alharam, Tabarak, OctoberClinic, AlMehwar,
                Wafa, Salam, MaadiMilitary, QueenClinic, Sudan, ShifaSpecialist, Helal,
                Mohamady, Pola, zohoor, Horus, Ainshams};

        final double Distances[] = {Alfouaddis, Rehandis, Qasrdis, Demdis, Cleopatradis, DarElFouaddis,
                MahmoudSpecialdis, Dreamlanddis, medicalcenteroctoberdis, CityClinicdis, Alwahadis, OctoberMilitarydis
                , AsiaClinicsdis, ElShoroukHospitaldis,
                SphinxSpecializeddis, Alharamdis, Tabarakdis, OctoberClinicdis,
                AlMehwardis, Wafadis, Salamdis, MaadiMilitarydis, QueenClinicdis, Sudandis, ShifaSpecialistdis, Helaldis, Mohamadydis
                , Poladis, zohoordis, Horusdis, Ainshamsdis};


        final ArrayList<yourappomodel> appoItems = new ArrayList<yourappomodel>();
        final ArrayList<yourmedicinelistmodel> medItems = new ArrayList<yourmedicinelistmodel>();
        final MyCustomAdapter medadpter = new MyCustomAdapter(medItems);


        int mHour, mMin;


        final String userPhone = storage.getString("User number", "0");


        final MyCustomAdapter1 appoadpter = new MyCustomAdapter1(appoItems);
        ls.setAdapter(medadpter);
        lss.setAdapter(appoadpter);
        appoItems.removeAll(appoItems);
        appoadpter.notifyDataSetChanged();


        // getting all appointments data, on booking, doctor's name, appointment time, and speciality are stored in shared prefrences
        for (int b = 0; b < Names.length; b++) {
            {
                for (int bb = 0; bb < Specialities.length; bb++) {
                    String reservationpath = "Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb] + "/doctor1patients/" + userPhone;
                    String docname = storage.getString(reservationpath + "/docname", "no");
                    String doctime = storage.getString(reservationpath + "/doctime", "no");
                    String hospitalnameandspecial = storage.getString(reservationpath + "/hospnameandspecial", "no");
                    {
                        if (!docname.equals("no") && !doctime.equals("no") && !hospitalnameandspecial.equals("no")) {
                            appoItems.add(new yourappomodel(Names[+b] + " " + ">" + " " + hospitalnameandspecial, docname, doctime, Names[+b], Specialities[+bb]));
                        }
                    }
                }
            }
        }
        appoadpter.notifyDataSetChanged();

//metrics used to adjust dialogs size
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;

// on shared prefrence change, appointemnts are refreshed to be interactive with user
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                        appoItems.removeAll(appoItems);
                        appoadpter.notifyDataSetChanged();
                        //Toast.makeText(getContext(), "shared", Toast.LENGTH_SHORT).show();
                        for (int b = 0; b < Names.length; b++) {
                            {
                                for (int bb = 0; bb < Specialities.length; bb++) {
                                    String reservationpath = "Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb] + "/doctor1patients/" + userPhone;
                                    if (storage.contains(reservationpath + "/docname")
                                    ) {
                                        String docname = storage.getString(reservationpath + "/docname", "no");
                                        String doctime = storage.getString(reservationpath + "/doctime", "no");
                                        String hospitalnameandspecial = storage.getString(reservationpath + "/hospnameandspecial", "no");

                                        appoItems.add(new yourappomodel(Names[+b] + " " + ">" + " " + hospitalnameandspecial, docname, doctime, Names[+b], Specialities[+bb]));
                                    }
                                }
                            }
                        }
                        appoadpter.notifyDataSetChanged();
                    }
                };
        storage.registerOnSharedPreferenceChangeListener(listener);


        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*When user reserves, he sets children on the Firebase, and on detecting such changes, appointments list refreshes again
                Shared prefrence listener is so weak, in case of enabling power saving mode sometimes the listener is removed.
                 so, to assure user comfort, and app interacivity, On data change listener is added */
                appoItems.removeAll(appoItems);
                appoadpter.notifyDataSetChanged();

                /* Because every hospital has 11 speciality, Nested loop was made to go through every speciality from every hospital to check if there is an appointment
                 * for the user */
                for (int b = 0; b < Names.length; b++) {
                    {
                        for (int bb = 0; bb < Specialities.length; bb++) {
                            String reservationpath = "Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb] + "/doctor1patients/" + userPhone;
                            if (dataSnapshot.child(reservationpath).exists()
                            ) {
                                if (dataSnapshot.child(reservationpath).getValue().toString().equals("booked") &&
                                        dataSnapshot.child("Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb] + "/Availability").getValue().toString().equals("True")) {
                                    String docname = storage.getString(reservationpath + "/docname", "no");
                                    String doctime = storage.getString(reservationpath + "/doctime", "no");
                                    String hospitalnameandspecial = storage.getString(reservationpath + "/hospnameandspecial", "no");

                                    appoItems.add(new yourappomodel(Names[+b] + " " + ">" + " " + hospitalnameandspecial, docname, doctime, Names[+b], Specialities[+bb]));
                                } else {
                            /* if the firebase has no child for the user, it means that either he or the canceled
                            the appointment. and in both cases, the appointment is removed from the Shared preferences*/
                                    editor.remove(reservationpath + "/docname");
                                    editor.remove(reservationpath + "/doctime");
                                    editor.commit();
                                }
                            } else {
                                /*if the booking child dosen't exist at all, its value is removed from firebase*/
                                editor.remove(reservationpath + "/docname");
                                editor.remove(reservationpath + "/doctime");
                                editor.commit();
                            }
                        }
                    }
                }
                appoadpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        lss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String hospname = String.valueOf(parent.getItemAtPosition(position));
                final String specialityname = String.valueOf(appoItems.get(position).getspecailname());
                final int item = (int) parent.getItemIdAtPosition(position);

                decisionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                decisionDialog.show();
                //decisionDialog.getWindow().setLayout((6 * width)/7, (11 * height)/24);


                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(getContext())
                                .setTitle("Confirmation")
                                .setMessage("Do you want to cancel your appointment?")
                                .setIcon(R.drawable.ic_sure)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (isNetworkAvailable()) {
                                            appoItems.remove(item);

                                            // on canceling the  appointment, the childs from fire base and reservation paths are removed.
                                            String reservationpath = "Hospitals/" + hospname + "/Specialities/" + specialityname + "/doctor1patients/" + userPhone;

                                            dref.child(reservationpath).removeValue();

                                            editor.remove(reservationpath + "/docname");
                                            editor.remove(reservationpath + "/doctime");
                                            editor.remove(reservationpath + "/hospnameandspecial");
                                            editor.commit();


                                            decisionDialog.dismiss();

                                            appoadpter.notifyDataSetChanged();
                                            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "To cancel your appointment, Please connect to Internet", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        decisionDialog.dismiss();
                                    }
                                }).show();


                        ;

                    }
                });
                navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < Names.length; i++) {
                            if (hospname.equals(Names[+i])) {

                                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?" + "&daddr=" + loctostr(Locations[+i]));
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);

                            }
                        }
                    }
                });
                profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < Names.length; i++) {
                            if (hospname.equals(Names[+i])) {

                                Intent intent = new Intent(getActivity(), Hospitaldetail.class);
                                intent.putExtra("Name", Names[+i]);
                                intent.putExtra("Type", Types[+i]);
                                intent.putExtra("Location", Locations[+i]);
                                intent.putExtra("Distance", distostr(Distances[+i]));
                                intent.putExtra("Logo", Icons[+i]);
                                startActivity(intent);
                            }
                        }
                    }
                });

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Every Medicine can have 3 reminders, each need 1 request to alarm and notification
                 *
                 * so, the following method was used.  an int value (starts with zero) will be used. furthermore,
                 *
                 * this int value will be multiplied by 3, then converted to sting to be used as key paths for every
                 *
                 * medicine. for example, the first medicine will have shared preferences keys of "NAME 0","CODE 0","ICON 0"
                 *
                 * the second medicine will have Keys of "NAME 3 --1*3--","CODE 3","ICON 3". and so on
                 *
                 *  the numbers in between, in addition to the int value (0,1,2) for the first medicine for example will be
                 *
                 * used in the requests codes
                 *
                 */
                int x = storage.getInt("number", -1) + 1;
                editor.putInt("number", x);
                editor.commit();

                final String exaist = String.valueOf(x * 3);

                Intent intent = new Intent(getContext(), midicineadd.class);
                intent.putExtra("exist", exaist);
                startActivity(intent);


            }
        });

        aadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = storage.getInt("number", -1) + 1;
                editor.putInt("number", x);
                editor.commit();

                final String exaist = String.valueOf(x * 3);

                Intent intent = new Intent(getContext(), midicineadd.class);
                intent.putExtra("exist", exaist);
                startActivity(intent);


            }
        });

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);


        int i;

        //to get all medicine data from shared preferences. for loop was used,

        for (i = 0; i < 100; i++) {
            //if a midicine Which has the code CODE+i exist, for every medicine has CODE value
            if (storage.contains("CODE" + i)) {

                //adding Items to the medicine listview, and adding reminders.
                String gottenname = storage.getString("NAME" + i, "");
                String gottendose = storage.getString("DOSE" + i, "");
                int icon = storage.getInt("ICON" + i, 0);
                int color1 = storage.getInt("Color1" + i, 0);
                int color2 = storage.getInt("Color2" + i, 0);


                if (icon == 0) {

                    LayerDrawable not_rounded_pill = (LayerDrawable) getResources().getDrawable(R.drawable.not_rounded_pill);
                    not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[color1])), PorterDuff.Mode.SRC_IN);
                    not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[color1])), PorterDuff.Mode.MULTIPLY);
                    not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[color2])), PorterDuff.Mode.SRC_IN);
                    not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[color2])), PorterDuff.Mode.MULTIPLY);
                    medItems.add(new yourmedicinelistmodel(gottendose,
                            gottenname, not_rounded_pill));
                } else {
                    Drawable img = getResources().getDrawable(Vectors[+icon]);
                    img.setColorFilter(getResources().getColor(mColors[+color1]), PorterDuff.Mode.SRC_IN);
                    img.setColorFilter(getResources().getColor(mColors[+color1]), PorterDuff.Mode.MULTIPLY);

                    medItems.add(new yourmedicinelistmodel(gottendose,
                            gottenname, img));
                }
            }
            if (storage.contains("Timer1Hour" + i)) {
                int exisst = i;
                // int exisst=  storage.getInt("Exist"+i,465465);

                //getting time to set in Alarm manager
                mMin = storage.getInt("Timer1Min" + i, 5);
                mHour = storage.getInt("Timer1Hour" + i, 5);

                //getting the current date and time, and the alarm ones.
                Date date = new Date();
                Calendar cal_now = Calendar.getInstance();
                Calendar cal_alarm = Calendar.getInstance();
                cal_alarm.setTime(date);
                cal_now.setTime(date);
                cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);
                cal_alarm.set(Calendar.MINUTE, mMin);
                cal_alarm.set(Calendar.SECOND, 0);
                //      Toast.makeText(getContext(), "first"+String.valueOf(mHour)+":"+String.valueOf(mMin)+":"+String.valueOf(i), Toast.LENGTH_SHORT).show();
                if (cal_alarm.before(cal_now)) {
                    // to prevent the alarm from ringing if its time is before now, and make it start from tomorrow
                    cal_alarm.add(Calendar.DATE, 1);
                }
                Intent intent = new Intent(getContext(), MyBrodcastReciever.class);
                // giving the request code to the Brodcast reciever
                intent.putExtra("requestcode", (exisst));
                //Setting pending intent to fire when a specific time comes, in this case, the first medicine time
                PendingIntent peendingIntent = PendingIntent.getBroadcast(getContext(), exisst, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), peendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), 1000 * 60 * 60 * 24, peendingIntent);
            }

            if (storage.contains("Timer2Hour" + i)) {
                int exisst = i;
                // int exisst=  storage.getInt("Exist"+i,465465);
                mMin = storage.getInt("Timer2Min" + i, 5);
                mHour = storage.getInt("Timer2Hour" + i, 5);
                Date date = new Date();
                Calendar cal_now = Calendar.getInstance();
                Calendar cal_alarm = Calendar.getInstance();
                cal_alarm.setTime(date);
                cal_now.setTime(date);
                cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);
                cal_alarm.set(Calendar.MINUTE, mMin);
                cal_alarm.set(Calendar.SECOND, 0);
                if (cal_alarm.before(cal_now)) {
                    cal_alarm.add(Calendar.DATE, 1);
                }
                Intent intent = new Intent(getContext(), MyBrodcastReciever.class);
                intent.putExtra("requestcode", (exisst));
                PendingIntent peendingIntent = PendingIntent.getBroadcast(getContext(), exisst + 1, intent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), 1000 * 60 * 60 * 24, peendingIntent);
            }

            if (storage.contains("Timer3Hour" + i)) {
                int exisst = i;
                //int exisst=  storage.getInt("Exist"+i,465465);
                mMin = storage.getInt("Timer3Min" + i, 5);
                mHour = storage.getInt("Timer3Hour" + i, 5);
                Date date = new Date();
                Calendar cal_now = Calendar.getInstance();
                Calendar cal_alarm = Calendar.getInstance();
                cal_alarm.setTime(date);
                cal_now.setTime(date);
                cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);
                cal_alarm.set(Calendar.MINUTE, mMin);
                cal_alarm.set(Calendar.SECOND, 0);
                Intent intent = new Intent(getContext(), MyBrodcastReciever.class);
                intent.putExtra("requestcode", (exisst));
                PendingIntent peendingIntent = PendingIntent.getBroadcast(getContext(),
                        exisst + 1, intent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),
                        1000 * 60 * 60 * 24, peendingIntent);
            }
        }


        if (!storage.contains("number")) {
            editor.putInt("number", -1);
            editor.commit();

        }


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                                          final int y = (int) parent.getItemIdAtPosition(position);
                                          int x = storage.getInt("CODE", 0);
                                          new AlertDialog.Builder(getContext())
                                                  .setTitle("Confirmation")
                                                  .setMessage("Do you want to delete this medication?")
                                                  .setIcon(R.drawable.ic_sure)
                                                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                                      public void onClick(DialogInterface dialog, int whichButton) {

                                                          String namee = (String) parent.getItemAtPosition(position);
                                                          int i = 0;
                                                          for (i = 0; i < 100; i++) {
                                                              if (storage.getString("NAME" + i, "bs").equals(namee)) {
                                                                  editor.remove("NAME" + i);
                                                                  editor.remove("DOSE" + i);
                                                                  editor.remove("CODE" + i);
                                                                  editor.remove("ICON" + i);

                                                                  editor.remove("Color1" + i);
                                                                  editor.remove("Color2" + i);
                                                                  editor.putString("REMOVED" + i, "now");
                                                                  editor.commit();
                                                                  editor.remove("Timer1Min" + i);
                                                                  editor.remove("Timer1Hour" + i);
                                                                  editor.remove("Timer2Min" + i);
                                                                  editor.remove("Timer2Hour" + i);
                                                                  editor.remove("Timer3Min" + i);
                                                                  editor.remove("Timer3Hour" + i);
                                                                  Intent intent = new Intent(getContext(), MyBrodcastReciever.class);
                                                                  PendingIntent.getBroadcast(getContext(), (i), intent, 0).cancel();
                                                                  PendingIntent.getBroadcast(getContext(), (i) + 1, intent, 0).cancel();
                                                                  PendingIntent.getBroadcast(getContext(), (i) + 2, intent, 0).cancel();


                                                              }

                                                          }
                                                          medItems.remove(y);
                                                          medadpter.notifyDataSetChanged();

                                                          //Refresing the medicine section if the adapter count became 0, this lessen the taken space for the app, and protect it from bugs in wrost cases
                                                          if (medadpter.getCount() == 0) {

                                                              for (i = 0; i < 100; i++) {
                                                                  editor.remove("NAME" + i);
                                                                  editor.remove("DOSE" + i);
                                                                  editor.remove("CODE" + i);
                                                                  editor.remove("Color1" + i);
                                                                  editor.remove("Color2" + i);
                                                                  editor.remove("ICON" + i);
                                                                  editor.putString("REMOVED" + i, "now");
                                                                  editor.commit();
                                                                  editor.remove("Timer1Min" + i);
                                                                  editor.remove("Timer1Hour" + i);
                                                                  editor.remove("Timer2Min" + i);
                                                                  editor.remove("Timer2Hour" + i);
                                                                  editor.remove("Timer3Min" + i);
                                                                  editor.remove("Timer3Hour" + i);
                                                                  Intent intent = new Intent(getContext(), MyBrodcastReciever.class);
                                                                  PendingIntent.getBroadcast(getContext(), (i), intent, 0).cancel();
                                                                  PendingIntent.getBroadcast(getContext(), (i) + 1, intent, 0).cancel();
                                                                  PendingIntent.getBroadcast(getContext(), (i) + 2, intent, 0).cancel();


                                                              }


                                                              ls.setVisibility(View.GONE);
                                                              editor.putInt("number", -1);
                                                              editor.commit();
                                                          } else {
                                                              ls.setVisibility(View.VISIBLE);
                                                          }

                                                          editor.commit();

                                                      }
                                                  })
                                                  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                      @Override
                                                      public void onClick(DialogInterface dialog, int which) {
                                                          dialog.dismiss();
                                                      }
                                                  }).show();


                                      }
                                  }

        );


        if (medadpter.getCount() == 0) {
            ls.setVisibility(View.GONE);
        } else {
            ls.setVisibility(View.VISIBLE);
        }

        return V;

    }


    private class MyCustomAdapter extends BaseAdapter {


        ArrayList<yourmedicinelistmodel> medItems = new ArrayList<yourmedicinelistmodel>();

        MyCustomAdapter(ArrayList<yourmedicinelistmodel> Items) {
            this.medItems = Items;
        }

        @Override
        public int getCount() {
            return medItems.size();
        }

        @Override
        public Object getItem(int position) {
            return medItems.get(position).getMedname();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view1 = inflater.inflate(R.layout.yourmedicinelistitem, null);

            TextView textView = (TextView) view1.findViewById(R.id.mediciene_name);
            TextView textView2 = (TextView) view1.findViewById(R.id.mediciende_time);
            ImageView photo = (ImageView) view1.findViewById(R.id.medicieneicon);
            textView.setText(medItems.get(i).getMedname());
            textView2.setText(medItems.get(i).getMeddate());
            photo.setImageDrawable(medItems.get(i).getImg());
            return view1;
        }
    }

    private class MyCustomAdapter1 extends BaseAdapter {


        ArrayList<yourappomodel> appoItems = new ArrayList<yourappomodel>();

        MyCustomAdapter1(ArrayList<yourappomodel> Items) {
            this.appoItems = Items;
        }

        @Override
        public int getCount() {
            return appoItems.size();

        }

        @Override
        public Object getItem(int position) {
            return appoItems.get(position).getHospname();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view2 = inflater.inflate(R.layout.yourappointmentlistitem, null);

            TextView textView = (TextView) view2.findViewById(R.id.appo_name);
            TextView textView2 = (TextView) view2.findViewById(R.id.Doc_name);
            TextView textView3 = (TextView) view2.findViewById(R.id.appo_time);
            textView2.setText(appoItems.get(i).getApponame());
            textView.setText(appoItems.get(i).getAppocheck());
            textView3.setText(appoItems.get(i).getAppotime());
            return view2;
        }
    }

    private String distostr(double dis) {
        String str = String.valueOf(dis);
        return str;
    }

    private String loctostr(Location location) {
        String loci = location.getLatitude() + "," + location.getLongitude();
        return loci;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}



