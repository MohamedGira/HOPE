package com.helloworld.uifirs222.ui.main;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.helloworld.uifirs222.Models.otherhospitalmodel;
import com.helloworld.uifirs222.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hospitalfrag extends Fragment {
    public final static int REQUEST_CODE = 10101;


    public static final String PREFS_NAME ="30" ;



    FloatingActionButton fabmain, fab1, fab2, fab3, fab4;
    Animation fabopen, fabclose, rotateclockwise, rotateanticlockwise, fab_ovelay_open, fab_ovelay_close,fab_label_open,fab_label_close;
    boolean isopen = false;
    TextView fab1_label,fab2_label,fab3_label,fab4_label,sortby;
    FrameLayout fab_overlay;
    GridView gridViewblood,gridViewnursery;
    GridAdapter gridAdapterblood;
    GridAdapterNur gridAdapternursery;
    double Distances[];

    DatabaseReference dref;



    final String bloodtypes []={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    final String nursierys []={"I","II","III","IIIA","IIIB","IIIC"};
    final String Specialities []={"Skin","Teeth","Child","Brain & Nerves","Bones","Infertility",
    "Ear, Nose and Throat","Heart","Immunity","Heart & Chest"};

    //city clinic "01003779314",
//dar 0238274799
///dreamland 0238580437
   // shorouk 01001716505
    //october medical center 01206661313

    String Numbers[] = {"01277513004", "02 37235512", "01030596413",
            "01145142917", "01279100097", "0238274799", "0225256289", "0238580437", "01206661313", "01003779314",
            "01279100097", "01101112001", "0238362462", "01001716505", "01145142917", "02 33860236", "02 24650199",
            "02 33860236", "024358318", "01279100097", "01279100097", "0225256289", "034358318",
            "01101112001", "01067770901", "0222547737", "0222547737", "0224650199", "0238362462", "0223304700", "0238362462",};



    public Hospitalfrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();



        View v= inflater.inflate(R.layout.fragment_hospitalfrag, container, false);
        // Inflate the layout for this fragment
        final ListView nl= (ListView) v.findViewById(R.id.nearhospitallistviewxml);
        final ArrayList<nearhospitalmodel> nearitem= new ArrayList<nearhospitalmodel>();
        final Hospitalfrag.MyCustomAdapterr nearadpter= new MyCustomAdapterr(nearitem);
        ImageView image =v.findViewById(R.id.refresh);

      //  final ListView ol= (ListView) v.findViewById(R.id.otherhospitallistviewxml);


//for dialog
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;


        dref = FirebaseDatabase.getInstance().getReference();
        sortby= v.findViewById(R.id.sortedby);
        fabmain = v.findViewById(R.id.fabmain);
        fab1 = v.findViewById(R.id.fab1);
        fab2 = v.findViewById(R.id.fab2);
        fab3 = v.findViewById(R.id.fab3);
        fab4 = v.findViewById(R.id.fab4);
        fab1_label= v.findViewById(R.id.fab1_label);
        fab2_label= v.findViewById(R.id.fab2_label);
        fab3_label= v.findViewById(R.id.fab3_label);
        fab4_label= v.findViewById(R.id.fab4_label);
        fab_overlay=v.findViewById(R.id.fab_overlay);
        fab_label_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_lable_open);
        fab_label_close =AnimationUtils.loadAnimation(getContext(), R.anim.fab_lable_close);
        fabopen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fab_ovelay_open =AnimationUtils.loadAnimation(getContext(), R.anim.fab_overlay_open);
        fab_ovelay_close =AnimationUtils.loadAnimation(getContext(), R.anim.fab_overlay_close);
        rotateanticlockwise = AnimationUtils.loadAnimation(getContext(), R.anim.rotation_anticlockwise);
        rotateclockwise = AnimationUtils.loadAnimation(getContext(), R.anim.rotation_clockwise);



        //Creating filteres dialogs

        final Dialog dialognur = new Dialog(getContext());
        dialognur.setContentView(R.layout.dialog_nurse);

        final Dialog dialogblood = new Dialog(getContext());
        dialogblood.setContentView(R.layout.dialog_blood);

        final Dialog dialogspecialization= new Dialog(getContext());
        dialogspecialization.setContentView(R.layout.dialog_specialization);


        final Dialog dialogfaci = new Dialog(getContext());
        dialogfaci.setContentView(R.layout.facility_dailog);

        //Initializing Blood and Nursires filters' grid view
        gridAdapterblood=new GridAdapter(getContext(),bloodtypes);
        gridViewblood= dialogblood.findViewById(R.id.gridblood);
        gridViewblood.setAdapter(gridAdapterblood);
        gridAdapternursery= new GridAdapterNur(getContext(),nursierys);
        gridViewnursery= dialognur.findViewById(R.id.gridnurse);
        gridViewnursery.setAdapter(gridAdapternursery);






        //Stting location& distance data, this is done using shared prefrence to be kept in phone storage,
        // no need to open the location every time we need to find nearest facility
        double mylat= Double.valueOf(storage.getString("LastLat","10"));
        double mylong= Double.valueOf( storage.getString("LastLong","10"));
        final Location me
                =new Location("");
        me.setLatitude(mylat);
        me.setLongitude(mylong);


        final Location Alfouad
                = new Location("");
        Alfouad.setLatitude(30.0785468);
        Alfouad.setLongitude(31.2765421);
        final Location Rehan = new Location("");
        Rehan.setLatitude(29.992168);
        Rehan.setLongitude(31.166988);
        final Location Qasr = new Location("");
        Qasr.setLatitude(30.0262814);
        Qasr.setLongitude(31.234475);
        final Location Demrdash = new Location("");
        Demrdash.setLatitude(30.0781932);
        Demrdash.setLongitude(31.2756256);
        final Location Cleopatra = new Location("");
        Cleopatra.setLatitude(30.0929476);
        Cleopatra.setLongitude(31.3299626);


        final Location DarElFouad = new Location("");
        DarElFouad.setLatitude(29.9973825);
        DarElFouad.setLongitude(30.9652677);
        final Location MahmoudSpecial = new Location("");
        MahmoudSpecial.setLatitude(30.0417599);
        MahmoudSpecial.setLongitude(31.0937094);
        final Location Dreamland = new Location("");
        Dreamland.setLatitude(29.9699343);
        Dreamland.setLongitude(31.0344061);
        final Location medicalcenteroctober = new Location("");
        medicalcenteroctober.setLatitude(29.9343231);
        medicalcenteroctober.setLongitude(30.9769704);
        final Location CityClinic = new Location("");
        CityClinic .setLatitude(29.9486387);

        CityClinic .setLongitude(31.0918808);
        final Location Alwaha = new Location("");
        Alwaha.setLatitude(29.9323505);
        Alwaha.setLongitude(31.0132771);
        final Location OctoberMilitary = new Location("");
        OctoberMilitary.setLatitude(29.9323505);
        OctoberMilitary.setLongitude(31.0132771);
        final Location AsiaClinics = new Location("");
        AsiaClinics .setLatitude(29.9323505);
        AsiaClinics .setLongitude(31.0132771);
        final Location ElShoroukHospital = new Location("");
        ElShoroukHospital .setLatitude(29.9323135);
        ElShoroukHospital .setLongitude(31.0132769);
        final Location SphinxSpecialized = new Location("");
        SphinxSpecialized.setLatitude(29.9322764);
        SphinxSpecialized.setLongitude(31.0132767);
        final Location Alharam = new Location("");
        Alharam.setLatitude(29.9322394);
        Alharam.setLongitude(31.0132765);
        final Location Tabarak = new Location("");
        Tabarak.setLatitude(29.9941414);
        Tabarak.setLongitude(31.1460766);
        final Location OctoberClinic = new Location("");
        OctoberClinic .setLatitude(29.9705175);
        OctoberClinic .setLongitude(30.9389081);
        final Location AlMehwar = new Location("");
        AlMehwar.setLatitude(30.0094253 );
        AlMehwar.setLongitude(30.9950951);
        final Location Wafa = new Location("");
        Wafa.setLatitude(29.9732546);
        Wafa.setLongitude(31.1812977);
        final Location Salam = new Location("");
        Salam.setLatitude(30.0266682);
        Salam.setLongitude(31.1817866);
        final Location MaadiMilitary = new Location("");
        MaadiMilitary .setLatitude(30.0502055);
        MaadiMilitary .setLongitude(31.2070936);
        final Location QueenClinic = new Location("");
        QueenClinic .setLatitude(29.9587115);
        QueenClinic .setLongitude(31.2495729 );
        final Location Tabarak0 = new Location("");
        Tabarak0.setLatitude(30.0855727);
        Tabarak0.setLongitude(31.1904919);
        final Location Sudan = new Location("");
        Sudan.setLatitude(30.0737429);
        Sudan.setLongitude(31.1996323);
        final Location ShifaSpecialist = new Location("");
        ShifaSpecialist .setLatitude(30.1421833);
        ShifaSpecialist .setLongitude(31.3100228);
        final Location Helal = new Location("");
        Helal .setLatitude(30.1422016);
        Helal .setLongitude(31.3201765);
        final Location Mohamady = new Location("");
        Mohamady.setLatitude(30.1454433);
        Mohamady.setLongitude(31.3185523);

        final Location Pola = new Location("");
        Pola.setLatitude(30.079702);
        Pola.setLongitude(31.2817742);
        final Location zohoor = new Location("");
        zohoor.setLatitude(30.1454433);
        zohoor.setLongitude(31.3185523);
        final Location Horus = new Location("");
        Horus.setLatitude(30.0807093);
        Horus.setLongitude(31.2823762);
        final Location Ainshams = new Location("");
        Ainshams.setLatitude(30.0734448);
        Ainshams.setLongitude(31.2780994);


        final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
        final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
        final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
        final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
        final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
        final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
        final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
        final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
        final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
        final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
        final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
        final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
        final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
        final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
        final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
        final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
        final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
        final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
        final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
        final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
        final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
        final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
        final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
        final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
        final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
        final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
        final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
        final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
        final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
        final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;

        final double Helaldis = Math.round((me.distanceTo(Helal  )/1000)*100.0)/100.0;
       final String Types []={
                "Private","Clinic","Public","Public",
                "Private","Private","Private","Private","Center",
                "Clinic","Public","Military","Clinic","Public",
                "Public","Public","Private","Clinic","Public",
                "Clinic","Private","Military","Clinic","Public",
                "Public","Public","Public","Private",
                "Public","Private","Maternity"};
        final String Names []={
                "Al-Fouad","Rehan","Elqasr Eleiny",
                "Demerdash",
                "Cleopatra","Dar ElFouad","Mahmoud","Dreamland","October Medical center","City Clinic","Al-waha","October ",
                "Asia Clinic","Al-Shorouk","Sphinx","Al-haram","Tabarak","October ","Al-Mehwar","Al-Wafa","Al-Salam","Maadi",
                "Queen Clinic", "Al-Sudan","Al-Shifaa","Al-Helal","Al-Mohamady","Saint Pola","Al-zohoor","Horus","Ainshams"};

           final int Icons[]={
                R.mipmap.ic_alfouad,R.mipmap.ic_rehan,
                R.mipmap.ic_qasr,R.mipmap.ic_demerdash,R.mipmap.ic_cleop,
                R.mipmap.ic_darelfouad,R.mipmap.ic_mahmoud,R.mipmap.ic_dream,
                R.mipmap.ic_ocrobermedicalcenter,R.mipmap.ic_city,R.mipmap.ic_waha,
                R.mipmap.ic_octobero,R.mipmap.ic_asia,R.mipmap.ic_shorok,
                R.mipmap.ic_shpinx,R.mipmap.ic_haram,R.mipmap.ic_tabarak,
                R.mipmap.ic_octpbern,R.mipmap.ic_mehwar,R.mipmap.ic_wafa,
                R.mipmap.ic_salam,R.mipmap.ic_maadi,R.mipmap.ic_queen,
                R.mipmap.ic_sudan,R.mipmap.ic_shifa,R.mipmap.ic_helal_round,
                R.mipmap.ic_mohammady,R.mipmap.ic_san_round,
                R.mipmap.ic_zohoor,R.drawable.ic_add,R.mipmap.ic_ain,
                };

        final Location Locations[]={
                Alfouad,Rehan,Qasr,Demrdash,Cleopatra,DarElFouad,
                MahmoudSpecial,Dreamland,medicalcenteroctober,CityClinic,Alwaha,OctoberMilitary,AsiaClinics,ElShoroukHospital,
                SphinxSpecialized,Alharam,Tabarak,OctoberClinic,AlMehwar,
                Wafa,Salam,MaadiMilitary,QueenClinic,Sudan,ShifaSpecialist,Helal,
                Mohamady,Pola,zohoor,Horus,Ainshams};
        Distances= new double[]{Alfouaddis, Rehandis, Qasrdis, Demdis, Cleopatradis, DarElFouaddis,
                MahmoudSpecialdis, Dreamlanddis, medicalcenteroctoberdis, CityClinicdis, Alwahadis, OctoberMilitarydis
                , AsiaClinicsdis, ElShoroukHospitaldis,
                SphinxSpecializeddis, Alharamdis, Tabarakdis, OctoberClinicdis,
                AlMehwardis, Wafadis, Salamdis, MaadiMilitarydis, QueenClinicdis, Sudandis, ShifaSpecialistdis, Helaldis, Mohamadydis
                , Poladis, zohoordis, Horusdis, Ainshamsdis};

        nl.setAdapter(nearadpter);
        nearadpter.nearitem.removeAll(nearitem);
        for(int i=0;i<Names.length;i++){
            nearitem.add(new nearhospitalmodel(Names[+i],Types[+i],Icons[+i], Distances[+i], loctostr(Locations[+i]),Numbers[+i]));
        }
        Collections.sort(nearitem,new CustomComparator());
        sortby.setText("Sorted by distance" );
        nearadpter.notifyDataSetChanged();


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


         // On location changed, and because the shared preference listener is weak, user can refresh his data by clicking on refresh
         //button (image)

                /*Location is refresh on 3 cases, on opening the app in case of activating location service, on refresh
                * on shared preferences change*/

                Toast.makeText(getContext(), "changed", Toast.LENGTH_SHORT).show();
                double mylat= Double.valueOf(storage.getString("LastLat","30"));
                double mylong= Double.valueOf(storage.getString("LastLong","30"));
                final Location me
                        =new Location("");
                me.setLatitude(mylat);
                me.setLongitude(mylong);

                final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
                final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
                final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
                final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
                final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
                final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
                final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
                final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
                final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
                final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
                final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
                final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
                final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
                final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
                final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
                final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
                final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
                final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
                final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
                final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
                final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
                final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
                final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
                final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
                final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
                final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
                final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
                final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
                final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
                final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;
                final double Helaldis = Math.round((me.distanceTo(Helal  )/1000)*100.0)/100.0;

                Distances= new double[]{Alfouaddis, Rehandis, Qasrdis, Demdis, Cleopatradis, DarElFouaddis,
                        MahmoudSpecialdis, Dreamlanddis, medicalcenteroctoberdis, CityClinicdis, Alwahadis, OctoberMilitarydis
                        , AsiaClinicsdis, ElShoroukHospitaldis,
                        SphinxSpecializeddis, Alharamdis, Tabarakdis, OctoberClinicdis,
                        AlMehwardis, Wafadis, Salamdis, MaadiMilitarydis, QueenClinicdis, Sudandis, ShifaSpecialistdis, Helaldis, Mohamadydis
                        , Poladis, zohoordis, Horusdis, Ainshamsdis};
                nearadpter.nearitem.removeAll(nearitem);


                for(int i=0;i<Names.length;i++){
                    nearitem.add(new nearhospitalmodel(Names[+i],Types[+i],Icons[+i], Distances[+i], loctostr(Locations[+i]),Numbers[+i]));
                }
//this method is used to sort places Ascendingly according to distance
                Collections.sort(nearitem,new CustomComparator());
                nearadpter.notifyDataSetChanged();
                sortby.setText("Sorted by distance" );

            }
        });
        nl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                long lil=parent.getItemIdAtPosition(position);
                String lol =String.valueOf(lil);
                Toast.makeText(getContext(), selectedItem+"...."+lol, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), Hospitaldetail.class);
                intent.putExtra("Name", selectedItem);
                intent.putExtra("Type", nearitem.get(position).Kind);
                intent.putExtra("Number", nearitem.get(position).Number);

                intent.putExtra("Location", nearitem.get(position).Location);
                if(nearitem.get(position).Distance!=null){
                intent.putExtra("Distance",distostr(nearitem.get(position).Distance/*"*1.5"*/));                }
                intent.putExtra("Logo", nearitem.get(position).Logo);
                startActivity(intent);

            }
        });

        SharedPreferences.OnSharedPreferenceChangeListener listener=
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences preferences, String key)
                    {

                       // Toast.makeText(getContext(), "changed", Toast.LENGTH_SHORT).show();
                        double mylat= Double.valueOf(preferences.getString("LastLat","30"));
                        double mylong= Double.valueOf(preferences.getString("LastLong","30"));
                        final Location me
                                =new Location("");
                        me.setLatitude(mylat);
                        me.setLongitude(mylong);
                        final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
                        final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
                        final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
                        final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
                        final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
                        final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
                        final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
                        final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
                        final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
                        final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
                        final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
                        final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
                        final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
                        final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
                        final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
                        final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
                        final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
                        final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
                        final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
                        final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
                        final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
                        final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
                        final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
                        final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
                        final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
                        final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
                        final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
                        final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
                        final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
                        final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;
                         Distances= new double[]{Alfouaddis, Rehandis, Qasrdis, Demdis, Cleopatradis, DarElFouaddis,
                                 MahmoudSpecialdis, Dreamlanddis, medicalcenteroctoberdis, CityClinicdis, Alwahadis, OctoberMilitarydis
                                 , AsiaClinicsdis, ElShoroukHospitaldis,
                                 SphinxSpecializeddis, Alharamdis, Tabarakdis, OctoberClinicdis,
                                 AlMehwardis, Wafadis, Salamdis, MaadiMilitarydis, QueenClinicdis, Sudandis, ShifaSpecialistdis, Helaldis, Mohamadydis
                                 , Poladis, zohoordis, Horusdis, Ainshamsdis};
                        nearadpter.nearitem.removeAll(nearitem);

                        for(int i=0;i<Names.length;i++){
                            nearitem.add(new nearhospitalmodel(Names[+i],Types[+i],Icons[+i], Distances[+i], loctostr(Locations[+i]),Numbers[+i]));
                        }
                        Collections.sort(nearitem,new CustomComparator());
                        nearadpter.notifyDataSetChanged();


                    }
                };
        storage.registerOnSharedPreferenceChangeListener(listener);




        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialognur.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialognur.show();
          //      dialognur.getWindow().setLayout((6 * width)/7, (6 * height)/12);

            }
        });

        gridViewnursery.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                double mylat= Double.valueOf(storage.getString("LastLat","30"));
                double mylong= Double.valueOf(storage.getString("LastLong","30"));
                final Location me
                        =new Location("");
                me.setLatitude(mylat);
                me.setLongitude(mylong);

                final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
                final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
                final double Zembaboydis = Math.round((me.distanceTo(Zembaboy)/1000)*100.0)/100.0;
                final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
                final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
                final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
                final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
                final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
                final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
                final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
                final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
                final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
                final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
                final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
                final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
                final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
                final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
                final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
                final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
                final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
                final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
                final double Tabarak0dis  = Math.round((me.distanceTo(Tabarak0 )/1000)*100.0)/100.0;
                final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
                final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
                final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
                final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
                final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
                final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
                final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
                final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
                final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
                final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;
                final double Helaldis = Math.round((me.distanceTo(Helal  )/1000)*100.0)/100.0;

                final double Distances[]={Alfouaddis,Rehandis,Zembaboydis,Qasrdis,Demdis,Cleopatradis,DarElFouaddis,
                        MahmoudSpecialdis,Dreamlanddis,medicalcenteroctoberdis,CityClinicdis,Alwahadis,OctoberMilitarydis
                        ,AsiaClinicsdis,ElShoroukHospitaldis,
                        SphinxSpecializeddis,Alharamdis,Tabarakdis,OctoberClinicdis,
                        AlMehwardis,Wafadis,Salamdis,MaadiMilitarydis,QueenClinicdis,Sudandis,ShifaSpecialistdis,Helaldis,Mohamadydis
                        ,Poladis,zohoordis,Horusdis,Ainshamsdis};*/
                nearadpter.nearitem.removeAll(nearitem);
                String choosennur= gridAdapternursery.getItem(position).toString();
                nearitem.removeAll(nearitem);
                nearadpter.notifyDataSetChanged();
                for (int b=0; b<Names.length;b++)
                {
                    String fromsortnur=  storage.getString("Hospitals/" + Names[+b] + "/Nurseries/" + choosennur,"1");
                    if (!fromsortnur.equals(0)&&!fromsortnur.equals("0")) {
                        nearitem.add(new nearhospitalmodel(Names[+b],Types[+b],Icons[+b], Distances[+b], loctostr(Locations[+b]),Numbers[+b]));
                    }}

                sortby.setText("Sorted by nursery type:"+choosennur);
                Collections.sort(nearitem,new CustomComparator());
                nearadpter.notifyDataSetChanged();
                dialognur.dismiss();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fabmain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Darkblue)));
                        fabmain.startAnimation(rotateanticlockwise);
                        fab1.startAnimation(fabclose);
                        fab2.startAnimation(fabclose);
                        fab3.startAnimation(fabclose);
                        fab4.startAnimation(fabclose);
                        fab_overlay.startAnimation(fab_ovelay_close);
                        fab_overlay.setClickable(false);
                        fab1_label.startAnimation(fab_label_close);
                        fab2_label.startAnimation(fab_label_close);
                        fab3_label.startAnimation(fab_label_close);
                        fab4_label.startAnimation(fab_label_close);
                        fab1.setClickable(false);
                        fab2.setClickable(false);
                        fab3.setClickable(false);
                        fab4.setClickable(false);
                        isopen=false;
                    }
                }, 10);




            }
        });

        {
        fabmain.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(isopen)
                { fabmain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Darkblue)));
                    fabmain.startAnimation(rotateanticlockwise);
                    fab1.startAnimation(fabclose);
                    fab2.startAnimation(fabclose);
                    fab3.startAnimation(fabclose);
                    fab4.startAnimation(fabclose);
                    fab_overlay.startAnimation(fab_ovelay_close);
                    fab_overlay.setClickable(false);
                    fab1_label.startAnimation(fab_label_close);
                    fab2_label.startAnimation(fab_label_close);
                    fab3_label.startAnimation(fab_label_close);
                    fab4_label.startAnimation(fab_label_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    fab4.setClickable(false);
                    isopen=false;


                }

                else {
                    fabmain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lightblue)));
                    fabmain.startAnimation(rotateclockwise);
                    fab1.startAnimation(fabopen);
                    fab2.startAnimation(fabopen);
                    fab3.startAnimation(fabopen);
                    fab4.startAnimation(fabopen);
                    fab_overlay.startAnimation(fab_ovelay_open);
                    fab_overlay.setClickable(true);
                    fab1_label.startAnimation(fab_label_open);
                    fab2_label.startAnimation(fab_label_open);
                    fab3_label.startAnimation(fab_label_open);
                    fab4_label.startAnimation(fab_label_open);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    fab3.setClickable(true);
                    fab4.setClickable(true);
                    isopen=true;
                }

            }


        });

        fab_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabmain.startAnimation(rotateanticlockwise);
                fab1.startAnimation(fabclose);
                fab2.startAnimation(fabclose);
                fab3.startAnimation(fabclose);
                fab4.startAnimation(fabclose);
                fab_overlay.startAnimation(fab_ovelay_close);
                fab_overlay.setClickable(false);
                fab1_label.startAnimation(fab_label_close);
                fab2_label.startAnimation(fab_label_close);
                fab3_label.startAnimation(fab_label_close);
                fab4_label.startAnimation(fab_label_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                fab4.setClickable(false);
                isopen=false;

            }
        });

        }

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogblood.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //        dialogblood.getWindow().setLayout((6 * width)/7, (8 * height)/12);
dialogblood.show();
            }
        });

        gridViewblood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                double mylat= Double.valueOf(storage.getString("LastLat","30"));
                double mylong= Double.valueOf(storage.getString("LastLong","30"));
                final Location me
                        =new Location("");
                me.setLatitude(mylat);
                me.setLongitude(mylong);

                final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
                final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
                final double Zembaboydis = Math.round((me.distanceTo(Zembaboy)/1000)*100.0)/100.0;
                final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
                final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
                final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
                final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
                final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
                final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
                final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
                final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
                final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
                final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
                final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
                final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
                final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
                final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
                final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
                final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
                final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
                final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
                final double Tabarak0dis  = Math.round((me.distanceTo(Tabarak0 )/1000)*100.0)/100.0;
                final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
                final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
                final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
                final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
                final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
                final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
                final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
                final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
                final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
                final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;
                final double Distances[]={Alfouaddis,Rehandis,Zembaboydis,Qasrdis,Demdis,Cleopatradis,DarElFouaddis,
                        MahmoudSpecialdis,Dreamlanddis,medicalcenteroctoberdis,CityClinicdis,Alwahadis,OctoberMilitarydis
                        ,AsiaClinicsdis,ElShoroukHospitaldis,
                        SphinxSpecializeddis,Alharamdis,Tabarakdis,OctoberClinicdis,
                        AlMehwardis,Wafadis,Salamdis,MaadiMilitarydis,QueenClinicdis,Sudandis,ShifaSpecialistdis,Helaldis,Mohamadydis
                        ,Poladis,zohoordis,Horusdis,Ainshamsdis};*/
                nearadpter.nearitem.removeAll(nearitem);

                String choosenblood= gridAdapterblood.getItem(position).toString();
                nearitem.removeAll(nearitem);
                nearadpter.notifyDataSetChanged();
                for (int b=0; b<Names.length;b++)
                {
                    String frombloodsort=  storage.getString("Hospitals/" + Names[+b] + "/BloodTypes/" + choosenblood,"1");
                    if (!frombloodsort.equals(0)&&!frombloodsort.equals("0")) {
                        nearitem.add(new nearhospitalmodel(Names[+b],Types[+b],Icons[+b], Distances[+b], loctostr(Locations[+b]),Numbers[+b]));
                    }}
                sortby.setText("Sorted by blood type:"+choosenblood);
                Collections.sort(nearitem,new CustomComparator());
                nearadpter.notifyDataSetChanged();
                dialogblood.dismiss();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fabmain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Darkblue)));
                        fabmain.startAnimation(rotateanticlockwise);
                        fab1.startAnimation(fabclose);
                        fab2.startAnimation(fabclose);
                        fab3.startAnimation(fabclose);
                        fab4.startAnimation(fabclose);
                        fab_overlay.startAnimation(fab_ovelay_close);
                        fab_overlay.setClickable(false);
                        fab1_label.startAnimation(fab_label_close);
                        fab2_label.startAnimation(fab_label_close);
                        fab3_label.startAnimation(fab_label_close);
                        fab4_label.startAnimation(fab_label_close);
                        fab1.setClickable(false);
                        fab2.setClickable(false);
                        fab3.setClickable(false);
                        fab4.setClickable(false);
                        isopen=false;
                    }
                }, 10);




            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Spinner spinner = (Spinner) dialogfaci.findViewById(R.id.facilityspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.HopsTypes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                CardView find =dialogfaci.findViewById(R.id.findfacilitycard);

                find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                  /*      double mylat= Double.valueOf(storage.getString("LastLat","30"));
                        double mylong= Double.valueOf(storage.getString("LastLong","30"));
                        final Location me
                                =new Location("");
                        me.setLatitude(mylat);
                        me.setLongitude(mylong);

                        final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
                        final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
                        final double Zembaboydis = Math.round((me.distanceTo(Zembaboy)/1000)*100.0)/100.0;
                        final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
                        final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
                        final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
                        final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
                        final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
                        final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
                        final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
                        final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
                        final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
                        final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
                        final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
                        final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
                        final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
                        final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
                        final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
                        final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
                        final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
                        final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
                        final double Tabarak0dis  = Math.round((me.distanceTo(Tabarak0 )/1000)*100.0)/100.0;
                        final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
                        final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
                        final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
                        final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
                        final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
                        final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
                        final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
                        final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
                        final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
                        final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;
                        final double Distances[]={Alfouaddis,Rehandis,Zembaboydis,Qasrdis,Demdis,Cleopatradis,DarElFouaddis,
                                MahmoudSpecialdis,Dreamlanddis,medicalcenteroctoberdis,CityClinicdis,Alwahadis,OctoberMilitarydis
                                ,AsiaClinicsdis,ElShoroukHospitaldis,
                                SphinxSpecializeddis,Alharamdis,Tabarakdis,OctoberClinicdis,
                                AlMehwardis,Wafadis,Salamdis,MaadiMilitarydis,QueenClinicdis,Sudandis,ShifaSpecialistdis,Helaldis,Mohamadydis
                                ,Poladis,zohoordis,Horusdis,Ainshamsdis};*/
                        nearadpter.nearitem.removeAll(nearitem);
                        String choosentype= spinner.getSelectedItem().toString();

                        nearitem.removeAll(nearitem);
                        nearadpter.notifyDataSetChanged();
                        for (int b=0; b<Types.length;b++)
                        {
                            if (choosentype.equals(Types[+b].trim())) {
                                nearitem.add(new nearhospitalmodel(Names[+b],Types[+b],Icons[+b], Distances[+b], loctostr(Locations[+b]),Numbers[+b]));
                            }}
                        sortby.setText("Filter:"+" "+choosentype);
                        Collections.sort(nearitem,new CustomComparator());
                        nearadpter.notifyDataSetChanged();
                        dialogfaci.dismiss();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fabmain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Darkblue)));
                                fabmain.startAnimation(rotateanticlockwise);
                                fab1.startAnimation(fabclose);
                                fab2.startAnimation(fabclose);
                                fab3.startAnimation(fabclose);
                                fab4.startAnimation(fabclose);
                                fab_overlay.startAnimation(fab_ovelay_close);
                                fab_overlay.setClickable(false);
                                fab1_label.startAnimation(fab_label_close);
                                fab2_label.startAnimation(fab_label_close);
                                fab3_label.startAnimation(fab_label_close);
                                fab4_label.startAnimation(fab_label_close);
                                fab1.setClickable(false);
                                fab2.setClickable(false);
                                fab3.setClickable(false);
                                fab4.setClickable(false);
                                isopen=false;
                            }
                        }, 10);




                    }
                });

                dialogfaci.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //  dialogspecialization.getWindow().setLayout((6 * width)/7, (13 * height)/48);
                dialogfaci.show();
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Spinner spinner = (Spinner) dialogspecialization.findViewById(R.id.specializationspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.Specialities, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                CardView find =dialogspecialization.findViewById(R.id.findcard);

                find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                  /*      double mylat= Double.valueOf(storage.getString("LastLat","30"));
                        double mylong= Double.valueOf(storage.getString("LastLong","30"));
                        final Location me
                                =new Location("");
                        me.setLatitude(mylat);
                        me.setLongitude(mylong);

                        final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
                        final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
                        final double Zembaboydis = Math.round((me.distanceTo(Zembaboy)/1000)*100.0)/100.0;
                        final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
                        final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
                        final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
                        final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
                        final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
                        final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
                        final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
                        final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
                        final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
                        final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
                        final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
                        final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
                        final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
                        final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
                        final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
                        final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
                        final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
                        final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
                        final double Tabarak0dis  = Math.round((me.distanceTo(Tabarak0 )/1000)*100.0)/100.0;
                        final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
                        final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
                        final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
                        final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
                        final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
                        final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
                        final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
                        final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
                        final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
                        final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;
                        final double Distances[]={Alfouaddis,Rehandis,Zembaboydis,Qasrdis,Demdis,Cleopatradis,DarElFouaddis,
                                MahmoudSpecialdis,Dreamlanddis,medicalcenteroctoberdis,CityClinicdis,Alwahadis,OctoberMilitarydis
                                ,AsiaClinicsdis,ElShoroukHospitaldis,
                                SphinxSpecializeddis,Alharamdis,Tabarakdis,OctoberClinicdis,
                                AlMehwardis,Wafadis,Salamdis,MaadiMilitarydis,QueenClinicdis,Sudandis,ShifaSpecialistdis,Helaldis,Mohamadydis
                                ,Poladis,zohoordis,Horusdis,Ainshamsdis};*/
                        nearadpter.nearitem.removeAll(nearitem);
                        String choosenspecial= spinner.getSelectedItem().toString();

                        nearitem.removeAll(nearitem);
                        nearadpter.notifyDataSetChanged();
                        for (int b=0; b<Names.length;b++)
                        {
                            String Availability="Hospitals/" + Names[+b] + "/Specialities/" + choosenspecial+"/Availability";
                            String fromspecialsort=  storage.getString(Availability,"1");
                            if (fromspecialsort.equals("True")) {
                                nearitem.add(new nearhospitalmodel(Names[+b],Types[+b],Icons[+b], Distances[+b], loctostr(Locations[+b]),Numbers[+b]));
                            }}
                        sortby.setText("Sorted by :"+choosenspecial+" "+"Doctors");
                        Collections.sort(nearitem,new CustomComparator());
                        nearadpter.notifyDataSetChanged();
                        dialogspecialization.dismiss();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fabmain.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Darkblue)));
                                fabmain.startAnimation(rotateanticlockwise);
                                fab1.startAnimation(fabclose);
                                fab2.startAnimation(fabclose);
                                fab3.startAnimation(fabclose);
                                fab4.startAnimation(fabclose);
                                fab_overlay.startAnimation(fab_ovelay_close);
                                fab_overlay.setClickable(false);
                                fab1_label.startAnimation(fab_label_close);
                                fab2_label.startAnimation(fab_label_close);
                                fab3_label.startAnimation(fab_label_close);
                                fab4_label.startAnimation(fab_label_close);
                                fab1.setClickable(false);
                                fab2.setClickable(false);
                                fab3.setClickable(false);
                                fab4.setClickable(false);
                                isopen=false;
                            }
                        }, 10);




                    }
                });

                dialogspecialization.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
              //  dialogspecialization.getWindow().setLayout((6 * width)/7, (13 * height)/48);
                dialogspecialization.show();
            }
        });

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (int b=0; b<Names.length;b++)
                {if (dataSnapshot.child("Hospitals/"+Names[+b]+"/BloodTypes").exists()) {
                        for (int bb=0; bb<bloodtypes.length;bb++){
                        {
                            editor.putString("Hospitals/" + Names[+b] + "/BloodTypes/" + bloodtypes[+bb],
                                    dataSnapshot.child("Hospitals/" + Names[+b] + "/BloodTypes/" + bloodtypes[+bb]).getValue().toString().trim());
                            editor.commit();
                        }
                        }}}
                for (int b=0; b<Names.length;b++)
                {if (dataSnapshot.child("Hospitals/"+Names[+b]+"/Nurseries").exists()) {
                    for (int bb=0; bb<nursierys.length;bb++){
                        {
                            editor.putString("Hospitals/" + Names[+b] + "/Nurseries/" + nursierys[+bb],
                                    dataSnapshot.child("Hospitals/" + Names[+b] + "/Nurseries/" + nursierys[+bb]).getValue().toString().trim());
                            editor.commit();
                        }
                    }}}
                for (int b=0; b<Names.length;b++)
                {if (dataSnapshot.child("Hospitals/"+Names[+b]+"/Specialities").exists()) {
                    for (int bb=0; bb<Specialities.length;bb++){
                        String Availability="Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb]+"/Availability";
                        String doctor1="Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb]+"/doctor1";
                        String doctor1time="Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb]+"/doctor1time";
                        String doctor2="Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb]+"/doctor2";
                        String doctor2time="Hospitals/" + Names[+b] + "/Specialities/" + Specialities[+bb]+"/doctor2time";
                        {if(dataSnapshot.child(Availability).exists()){
                            editor.putString(Availability,dataSnapshot.child(Availability).getValue().toString().trim());
                            if(dataSnapshot.child(Availability).getValue().toString().trim().equals("True")){
                                editor.putString(doctor1,dataSnapshot.child(doctor1).getValue().toString().trim());
                                editor.putString(doctor1time,dataSnapshot.child(doctor1time).getValue().toString().trim());
                                if(dataSnapshot.child(doctor2).exists()){

                                    editor.putString(doctor2,dataSnapshot.child(doctor2).getValue().toString().trim());
                                    editor.putString(doctor2time,dataSnapshot.child(doctor2time).getValue().toString().trim());
                                }
                                }

                            }
                            editor.commit();
                        }
                    }}}






            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });




        return v;

    }




    private class MyCustomAdapterr extends  BaseAdapter {


        ArrayList<nearhospitalmodel> nearitem = new ArrayList<nearhospitalmodel>();
        MyCustomAdapterr(ArrayList<nearhospitalmodel> Items){
            this.nearitem= Items;
        }

        @Override
        public int getCount() {return nearitem.size();}

        @Override
        public Object getItem(int position) { return nearitem.get(position).Name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1= inflater.inflate(R.layout.nearhospitallistitem,null);

            TextView textView=(TextView) view1.findViewById(R.id.nearhospitalnamexml);
            TextView textView2=(TextView) view1.findViewById(R.id.nearhospitaldistancexml);
            ImageView photo= (ImageView)  view1.findViewById(R.id.nearhospitalitemimgxml);
            textView.setText(nearitem.get(i).Name);
            textView2.setText(nearitem.get(i).Kind);
            photo.setImageResource(nearitem.get(i).Logo);
            return view1;
        }
    }

    private class CustomComparator implements Comparator<nearhospitalmodel> {
        @Override
        public int compare(nearhospitalmodel o1, nearhospitalmodel o2) {
            return Double.compare(o1.getDistance(), o2.getDistance());
        }}


    private String loctostr(Location location) {
        String loci= location.getLatitude()+","+location.getLongitude();
        return loci;
    }
    private String distostr(double dis) {
        String str= String.valueOf(dis);
        return str;
    }
    public class GridAdapter extends BaseAdapter{

        Context context;

        View view;
        LayoutInflater layoutInflater;

        public GridAdapter(Context context, String[] bloodtypes) {
            this.context = context;
            bloodtypes = bloodtypes;

        }
     @Override
        public int getCount() {
            return bloodtypes.length;
        }

        @Override
        public Object getItem(int position) {
            return bloodtypes[+position];
        }

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView==null){
                view=new View(context);
                view= layoutInflater.inflate(R.layout.single_blood,null);
                TextView textView =view.findViewById(R.id.what);
                textView.setText(bloodtypes[position]);
            }
            else{
                view=new View(context);
                view= layoutInflater.inflate(R.layout.single_blood,null);
                TextView textView =view.findViewById(R.id.what);
                textView.setText(bloodtypes[position]);
            }
            return view;
        }
    }

    public class GridAdapterNur extends BaseAdapter {


        Context context;

        View view;
        LayoutInflater layoutInflater;

        public GridAdapterNur(Context context, String[] nursierys) {
            this.context = context;
            nursierys = nursierys;

        }



        @Override
        public int getCount() {
            return nursierys.length;
        }

        @Override
        public Object getItem(int position) {
            return nursierys[+position];
        }

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView==null){
                view=new View(context);
                view= layoutInflater.inflate(R.layout.single_blood,null);
                TextView textView =view.findViewById(R.id.what);
                textView.setText(nursierys[position]);
            }
            else{
                view=new View(context);
                view= layoutInflater.inflate(R.layout.single_blood,null);
                TextView textView =view.findViewById(R.id.what);
                textView.setText(nursierys[position]);
            }
            return view;
        }

    }

}
