package com.helloworld.hosfis.ui.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
public class AppointmentsFragment extends Fragment {


    public AppointmentsFragment() {
        // Required empty public constructor
    }
    DatabaseReference dref;
    ListView appolist;
    final String Specialities []={"Skin","Teeth","Child","Brain & Nerves","Bones","Infertility",
            "Ear, Nose and Throat","Heart","Immunity","Heart & Chest"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_appointments, container, false);

        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        String HospName=storage.getString("HospitalName","Zembaboy");
        final ArrayList<specializationmodel> speciality = new ArrayList<specializationmodel>();

        appolist = v.findViewById(R.id.appointments);

        final AppointmentsFragment.MyCustomAdapter medadpter = new AppointmentsFragment.MyCustomAdapter(speciality);
        appolist.setAdapter(medadpter);
        final String pathmainNur="Hospitals/" + HospName + "/Nurseries/";
        final String pathmainBlood="Hospitals/" + HospName+ "/BloodTypes/";
        final String pathmainSpeciality="Hospitals/" + HospName+ "/Specialities/";

        for (int i=0;i<Specialities.length;i++){
            boolean avail= storage.getBoolean(Specialities[+i]+"Available",false);
            if(avail){
                String docname=storage.getString(Specialities[+i]+"/Docname","fsa");
                String numofpat=storage.getString(Specialities[+i]+"/Patnum","0");
                speciality.add(new specializationmodel(Specialities[+i],docname,"Patients:"+" "+numofpat));
            }
        }

        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

speciality.removeAll(speciality);
                for (int i=0;i<Specialities.length;i++){
                    if(dataSnapshot.child(pathmainSpeciality+Specialities[+i]+"/Availability").exists()
                    &&dataSnapshot.child(pathmainSpeciality+Specialities[+i]+"/doctor1").exists()
                    ){
                        if(dataSnapshot.child(pathmainSpeciality+Specialities[+i] + "/Availability").getValue()
                                .toString().equals("True")){

                            editor.putBoolean(Specialities[+i]+"Available",true);
                            String ddocname=dataSnapshot.child(pathmainSpeciality+Specialities[+i]+"/doctor1").getValue().toString();
                            String nnumofpat=String.valueOf(dataSnapshot.child(pathmainSpeciality+Specialities[+i]+"/doctor1patients").getChildrenCount());
                            editor.putString(Specialities[+i]+"/Docname",ddocname);
                            editor.putString(Specialities[+i]+"/Patnum",nnumofpat);
speciality.add(new specializationmodel(Specialities[+i],ddocname,nnumofpat));
                        }
                        else
                        {editor.putBoolean(Specialities[+i]+"Available",false);}
                    }
                    for(DataSnapshot uniqueKeySnapshot : dataSnapshot.child(pathmainSpeciality+Specialities[+i]+"/doctor1patients").getChildren()){
                            String usernumber = uniqueKeySnapshot.getKey();


                        ArrayList<String> s = new ArrayList<String>();
                        s.add(usernumber);


                    }

                }
                editor.commit();
                medadpter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        appolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choosenSpecial= parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(getActivity(),Notify.class);
                intent.putExtra("SpecialName",choosenSpecial);
                startActivity(intent);
            }
        });

return v;
    }

    private class MyCustomAdapter extends BaseAdapter {
        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        ArrayList<specializationmodel> speciality= new ArrayList<specializationmodel>();
        MyCustomAdapter(ArrayList<specializationmodel> Items){
            this.speciality= Items;
        }

        @Override
        public int getCount() {return speciality.size();}

        @Override
        public Object getItem(int position) {
            return speciality.get(position).getspecailname();
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
            textView.setText(speciality.get(i).getspecailname());

            TextView textView1=(TextView) view1.findViewById(R.id.Docname);
            textView1.setText(speciality.get(i).Name);

            TextView textView2=(TextView) view1.findViewById(R.id.patnum);
            textView2.setText(speciality.get(i).getpatnum());


            return view1;
        }
    }

}
