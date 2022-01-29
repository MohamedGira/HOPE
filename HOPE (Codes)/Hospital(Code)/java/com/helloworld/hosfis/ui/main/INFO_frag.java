package com.helloworld.hosfis.ui.main;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.helloworld.hosfis.MainActivity;
import com.helloworld.hosfis.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class INFO_frag extends Fragment {


    CardView Emergency_card, set, dialogset;
    TextView emergency_available;
    EditText BloodAp, BloodBp, BloodAn, BloodBn, BloodABp, BloodABn, BloodOp, BloodOn, NursuiryI, NursuiryII, NursuiryIII, NursuiryIIIA, NursuiryIIIB, NursuiryIIIC, num_edit;
    ListView Specallist;
    TimePicker doctime;
    EditText docname;
    final String bloodtypes[] = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    final String nursierys[] = {"I", "II", "III", "IIIA", "IIIB", "IIIC"};
    final String Specialities[] = {"Skin", "Teeth", "Child", "Brain & Nerves", "Bones", "Infertility",
            "Ear, Nose and Throat", "Heart", "Immunity", "Heart & Chest"};
    public static final String PREFS_NAME = "30";


    DatabaseReference dref;
    boolean avalable = false;

    public INFO_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;
        final View v = inflater.inflate(R.layout.fragment_info_frag, container, false);
        Emergency_card = v.findViewById(R.id.emergency_card);
        emergency_available = v.findViewById(R.id.Emergncy_avalability);
        dref = FirebaseDatabase.getInstance().getReference();
        BloodAp = v.findViewById(R.id.Aplusedit);
        BloodAn = v.findViewById(R.id.Aminsuedit);
        BloodBp = v.findViewById(R.id.Bplusedit);
        BloodBn = v.findViewById(R.id.Bminsuedit);
        BloodABp = v.findViewById(R.id.ABplusedit);
        BloodABn = v.findViewById(R.id.ABminsuedit);
        BloodOp = v.findViewById(R.id.Oplusedit);
        BloodOn = v.findViewById(R.id.Ominsuedit);
        NursuiryI = v.findViewById(R.id.lvl1edit);
        NursuiryII = v.findViewById(R.id.lvl2edit);
        NursuiryIII = v.findViewById(R.id.lvl3edit);
        NursuiryIIIA = v.findViewById(R.id.lvl4edit);
        NursuiryIIIB = v.findViewById(R.id.lvl5edit);
        NursuiryIIIC = v.findViewById(R.id.lvl6edit);
        num_edit = v.findViewById(R.id.num_edit);
        set = v.findViewById(R.id.setcard);

        Specallist = v.findViewById(R.id.speicallits);
        final ArrayList<specialitiesmodel> speciality = new ArrayList<specialitiesmodel>();
        final MyCustomAdapter medadpter = new MyCustomAdapter(speciality);
        Specallist.setAdapter(medadpter);


        final Dialog dialogdoc = new Dialog(getContext());
        dialogdoc.setContentView(R.layout.dialog_doctor);

        final Spinner spinner = (Spinner) dialogdoc.findViewById(R.id.today);
        dialogset = dialogdoc.findViewById(R.id.dialogsetcard);
        doctime = dialogdoc.findViewById(R.id.timepicker);
        docname = dialogdoc.findViewById(R.id.docname);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.days, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        final String Hospitalname = storage.getString("HospitalName", "Zembaboy");


        final String pathmainSpeciality = "Hospitals/" + Hospitalname + "/Specialities/";

        Specallist.setOnTouchListener(new ListView.OnTouchListener() {


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


        for (int i = 0; i < Specialities.length; i++) {

            speciality.add(new specialitiesmodel(Specialities[+i], R.drawable.ic_check));
        }


        Specallist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String speciality = parent.getItemAtPosition(position).toString();

                boolean specialAvailible = storage.getBoolean(speciality + "Available", false);
                final ImageView check = (ImageView) view.findViewById(R.id.vector);

                if (specialAvailible) {

                    dref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(pathmainSpeciality + speciality + "/doctor1patients").exists()) {
                                if (dataSnapshot.child(pathmainSpeciality + speciality + "/doctor1patients").getChildrenCount() == 0) {
                                   // Toast.makeText(getContext(), String.valueOf(dataSnapshot.child(pathmainSpeciality + speciality + "/doctor1patients").getChildrenCount()), Toast.LENGTH_SHORT).show();
                                    editor.putBoolean(speciality + "Available", false);
                                    check.getDrawable().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.SRC_IN);
                                    editor.remove(speciality + "/Docname");
                                    editor.remove(speciality + "/DocTime");
                                    editor.commit();
                                } else {
                                    String patnum = String.valueOf(dataSnapshot.child(pathmainSpeciality + speciality + "/doctor1patients").getChildrenCount());


                                    if (dataSnapshot.child(pathmainSpeciality + speciality + "/doctor1patients").getChildrenCount() == 1) {

                                        new AlertDialog.Builder(getContext())
                                                .setTitle("Remove appointments")
                                                .setMessage("There are" + " " + patnum + " " + "patient waiting for this doctor. To notifiy him, Click on notify cancellation")
                                                .setIcon(R.drawable.ic_sure)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(getContext(), Notify.class);
                                                        intent.putExtra("SpecialName", speciality);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show();

                                    } else {
                                        new AlertDialog.Builder(getContext())
                                                .setTitle("Remove appointments")
                                                .setMessage("There are" + " " + patnum + " " + "patients waiting for this doctor. To notifiy them, Click on notify cancellation")
                                                .setIcon(R.drawable.ic_sure)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(getContext(), Notify.class);
                                                        intent.putExtra("SpecialName", speciality);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show();
                                    }


                                }

                            } else {
                                editor.putBoolean(speciality + "Available", false);
                                check.getDrawable().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.SRC_IN);
                                editor.remove(speciality + "/Docname");
                                editor.remove(speciality + "/DocTime");
                                editor.commit();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });

                } else {
                    dialogdoc.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //dialogdoc.getWindow().setLayout((6 * width)/7, (6 * height)/12);

                    dialogdoc.show();
                }

                dialogset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String DocName = docname.getText().toString().trim();
                        if (doctime.getCurrentMinute() < 10 && doctime.getCurrentHour() < 10) {
                            String DocTime = doctime.getCurrentHour() + ":" + "0" + doctime.getCurrentMinute();
                            String Docday = spinner.getSelectedItem().toString();
                            editor.putString(speciality + "/Docname", DocName);
                            editor.putString(speciality + "/DocTime", Docday + " " + "at" + " " + DocTime);
                            editor.putString(speciality + "/DocTimefornoti", DocTime);

                            editor.putBoolean(speciality + "Available", true);
                            check.getDrawable().setColorFilter(getResources().getColor(R.color.lightblue), PorterDuff.Mode.SRC_IN);
                            editor.commit();
                            dialogdoc.dismiss();
                        } else if (doctime.getCurrentMinute() < 10 && doctime.getCurrentHour() >= 10) {
                            String DocTime = doctime.getCurrentHour() + ":" + "0" + doctime.getCurrentMinute();
                            String Docday = spinner.getSelectedItem().toString();
                            editor.putString(speciality + "/Docname", DocName);
                            editor.putString(speciality + "/DocTime", Docday + " " + "at" + " " + DocTime);
                            editor.putBoolean(speciality + "Available", true);
                            editor.putString(speciality + "/DocTimefornoti", DocTime);

                            check.getDrawable().setColorFilter(getResources().getColor(R.color.lightblue), PorterDuff.Mode.SRC_IN);
                            editor.commit();
                            dialogdoc.dismiss();
                        } else if (doctime.getCurrentMinute() >= 10 && doctime.getCurrentHour() < 10) {
                            String DocTime = doctime.getCurrentHour() + ":" + doctime.getCurrentMinute();
                            String Docday = spinner.getSelectedItem().toString();
                            editor.putString(speciality + "/Docname", DocName);
                            editor.putString(speciality + "/DocTime", Docday + " " + "at" + " " + DocTime);
                            editor.putBoolean(speciality + "Available", true);
                            check.getDrawable().setColorFilter(getResources().getColor(R.color.lightblue), PorterDuff.Mode.SRC_IN);
                            editor.commit();
                            dialogdoc.dismiss();
                        } else {
                            String DocTime = doctime.getCurrentHour() + ":" + doctime.getCurrentMinute();
                            String Docday = spinner.getSelectedItem().toString();
                            editor.putString(speciality + "/Docname", DocName);
                            editor.putString(speciality + "/DocTime", Docday + " " + "at" + " " + DocTime);
                            editor.putBoolean(speciality + "Available", true);
                            editor.putString(speciality + "/DocTimefornoti", DocTime);

                            check.getDrawable().setColorFilter(getResources().getColor(R.color.lightblue), PorterDuff.Mode.SRC_IN);
                            editor.commit();
                            dialogdoc.dismiss();
                        }


                    }
                });
            }

        });
        medadpter.notifyDataSetChanged();
        final EditText[] texts = {BloodAp, BloodBp, BloodAn, BloodBn, BloodABp, BloodABn, BloodOp, BloodOn
                , NursuiryI, NursuiryII, NursuiryIII, NursuiryIIIA, NursuiryIIIB, NursuiryIIIC};
        final EditText[] Nuronly = {NursuiryI, NursuiryII, NursuiryIII, NursuiryIIIA, NursuiryIIIB, NursuiryIIIC};
        final EditText[] bloodonly = {BloodAp, BloodBp, BloodAn, BloodBn, BloodABp, BloodABn, BloodOp, BloodOn};

        final String Specialities[] = {"Skin", "Teeth", "Child", "Brain & Nerves", "Bones", "Infertility",
                "Ear, Nose and Throat", "Heart", "Immunity", "Heart & Chest"};
        final String pathmainNur = "Hospitals/" + Hospitalname + "/Nurseries/";
        final String pathmainBlood = "Hospitals/" + Hospitalname + "/BloodTypes/";

        for (int n = 0; n < nursierys.length; n++) {
            if (storage.contains(pathmainNur + nursierys[+n])) {
                Nuronly[+n].setText(storage.getString(pathmainNur + nursierys[+n], "0"));
            }
        }
        num_edit.setText(storage.getString("Hospitals/" + Hospitalname +"/Beds" ,"0"));
        for (int n = 0; n < bloodtypes.length; n++) {
            if (storage.contains(pathmainBlood + bloodtypes[+n])) {
                bloodonly[+n].setText(storage.getString(pathmainBlood + bloodtypes[+n], "0"));
            }
        }

        Emergency_card.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (avalable) {
                    emergency_available.setText("Not available");
                    emergency_available.setTextColor(getResources().getColor(R.color.RED));
                    emergency_available.setTextSize(25);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Hospitals/" + Hospitalname + "/Emergency care");
                    myRef.setValue("False");
                    avalable = false;
                } else {
                    emergency_available.setText("Available");
                    emergency_available.setTextColor(getResources().getColor(R.color.Green));
                    emergency_available.setTextSize(25);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Hospitals/" + Hospitalname + "/Emergency care");
                    myRef.setValue("True");
                    avalable = true;

                }
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                for (int i = 0; i < texts.length; i++) {
                    if (texts[+i].getText().toString().isEmpty()) {
                        texts[+i].setError("Can't be empty");
                        editor.putBoolean("full", false);
                        editor.commit();
                    } else {
                        editor.putBoolean("full", true);
                        editor.commit();
                    }
                }
                if( num_edit.getText().toString().isEmpty()){
                    num_edit.setError("Can't be empty");
                    editor.putBoolean("full", false);
                }

                if (storage.getBoolean("full", false) == true) {
                    DatabaseReference myRefI = database.getReference(pathmainNur + "I");
                    DatabaseReference myRefII = database.getReference(pathmainNur + "II");
                    DatabaseReference myRefIII = database.getReference(pathmainNur + "III");
                    DatabaseReference myRefIIIA = database.getReference(pathmainNur + "IIIA");
                    DatabaseReference myRefIIIB = database.getReference(pathmainNur + "IIIB");
                    DatabaseReference myRefIIIC = database.getReference(pathmainNur + "IIIC");
                    DatabaseReference myRefA = database.getReference(pathmainBlood + "A+");
                    DatabaseReference myRefa = database.getReference(pathmainBlood + "A-");
                    DatabaseReference myRefB = database.getReference(pathmainBlood + "AB+");
                    DatabaseReference myRefb = database.getReference(pathmainBlood + "AB-");
                    DatabaseReference myRefAB = database.getReference(pathmainBlood + "B+");
                    DatabaseReference myRefab = database.getReference(pathmainBlood + "B-");
                    DatabaseReference myRefO = database.getReference(pathmainBlood + "O+");
                    DatabaseReference myRefo = database.getReference(pathmainBlood + "O-");
                    DatabaseReference myRefbed = database.getReference("Hospitals/" + Hospitalname +"/Beds" );


                    myRefI.setValue(NursuiryI.getText().toString().trim());
                    myRefII.setValue(NursuiryII.getText().toString().trim());
                    myRefIII.setValue(NursuiryIII.getText().toString().trim());
                    myRefIIIA.setValue(NursuiryIIIA.getText().toString().trim());
                    myRefIIIB.setValue(NursuiryIIIB.getText().toString().trim());
                    myRefIIIC.setValue(NursuiryIIIC.getText().toString().trim());
                    myRefA.setValue(BloodAp.getText().toString().trim());
                    myRefa.setValue(BloodAn.getText().toString().trim());
                    myRefB.setValue(BloodBp.getText().toString().trim());
                    myRefb.setValue(BloodBn.getText().toString().trim());
                    myRefAB.setValue(BloodABp.getText().toString().trim());
                    myRefab.setValue(BloodABn.getText().toString().trim());
                    myRefO.setValue(BloodOp.getText().toString().trim());
                    myRefo.setValue(BloodOn.getText().toString().trim());
                    myRefbed.setValue(num_edit.getText().toString().trim());


                    editor.putString(pathmainNur + "I", NursuiryI.getText().toString().trim());
                    editor.putString(pathmainNur + "II", NursuiryII.getText().toString().trim());
                    editor.putString(pathmainNur + "III", NursuiryIII.getText().toString().trim());
                    editor.putString(pathmainNur + "IIIA", NursuiryIIIA.getText().toString().trim());
                    editor.putString(pathmainNur + "IIIB", NursuiryIIIB.getText().toString().trim());
                    editor.putString(pathmainNur + "IIIC", NursuiryIIIC.getText().toString().trim());
                    editor.putString(pathmainBlood + "A+", BloodAp.getText().toString().trim());
                    editor.putString(pathmainBlood + "A-", BloodABn.getText().toString().trim());
                    editor.putString(pathmainBlood + "B+", BloodBp.getText().toString().trim());
                    editor.putString(pathmainBlood + "B-", BloodBn.getText().toString().trim());
                    editor.putString(pathmainBlood + "AB+", BloodABp.getText().toString().trim());
                    editor.putString(pathmainBlood + "AB-", BloodABn.getText().toString().trim());
                    editor.putString(pathmainBlood + "O+", BloodOp.getText().toString().trim());
                    editor.putString(pathmainBlood + "O-", BloodOn.getText().toString().trim());
                    editor.putString("Hospitals/" + Hospitalname +"/Beds" , num_edit.getText().toString().trim());
                    editor.putBoolean("full", false);
                    editor.commit();

                    for (int o = 0; o < Specialities.length; o++) {
                        boolean avail = storage.getBoolean(Specialities[+o] + "Available", false);
                        if (avail) {
                            String Name = storage.getString(Specialities[+o] + "/Docname", "don't");
                            String Time = storage.getString(Specialities[+o] + "/DocTime", "don't");
                            DatabaseReference myRefavail = database.getReference(pathmainSpeciality + Specialities[+o] + "/Availability");
                            DatabaseReference myRefname = database.getReference(pathmainSpeciality + Specialities[+o] + "/doctor1");
                            DatabaseReference myReftime = database.getReference(pathmainSpeciality + Specialities[+o] + "/doctor1time");
                            myRefavail.setValue("True");
                            myRefname.setValue(Name);
                            myReftime.setValue(Time);
                        } else {
                            DatabaseReference myRefavail = database.getReference(pathmainSpeciality + Specialities[+o] + "/Availability");
                            myRefavail.setValue("False");
                        }

                    }
                }


            }

        });
        return v;
    }

    private class MyCustomAdapter extends BaseAdapter {
        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        ArrayList<specialitiesmodel> speciality = new ArrayList<specialitiesmodel>();

        MyCustomAdapter(ArrayList<specialitiesmodel> Items) {
            this.speciality = Items;
        }

        @Override
        public int getCount() {
            return speciality.size();
        }

        @Override
        public Object getItem(int position) {
            return speciality.get(position).getspecial();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view1 = inflater.inflate(R.layout.doctors, null);

            TextView textView = (TextView) view1.findViewById(R.id.speciality);
            textView.setText(speciality.get(i).getspecial());
            ImageView check = (ImageView) view1.findViewById(R.id.vector);
            for (int f = 0; f < Specialities.length; f++) {
                boolean DoctorAvailibile = storage.getBoolean(Specialities[+i] + "Available", false);
                if (DoctorAvailibile) {
                    check.getDrawable().setColorFilter(getResources().getColor(R.color.lightblue), PorterDuff.Mode.SRC_IN);
                } else {
                    check.getDrawable().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.SRC_IN);
                }
            }

            return view1;
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}