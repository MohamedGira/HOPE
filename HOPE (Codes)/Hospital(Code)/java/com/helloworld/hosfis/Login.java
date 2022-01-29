package com.helloworld.hosfis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {
    ImageView hope,back,loginbg,logo;

    TextInputLayout HospCode;
    Animation animbg,animlogo, animopen;
    ScrollView scrolll;
    CardView signup;
    TextView weltxt,nicetxt;
    public static final String PREFS_NAME ="30" ;

    final String Names []={
            "Al-Fouad","Rehan","Elqasr Eleiny","Demerdash",
            "Cleopatra","Dar ElFouad ","Mahmoud ","Dreamland ","October Medical center "
            ,"City Clinic","Al-waha ","October ","Asia Clinic","Al-Shorouk ",
            "Sphinx","Al-haram ","Tabarak ","October ","Al-Mehwar",
            "Al-Wafa","Al-Salam ","Maadi","Queen Clinic","Al-Sudan",
            "Al-Shifaa","Al-Helal","Al-Mohamady","Miswsud ","Saint Pola",
            "Al-zohoor","Horus ","Ainshams "};

    final String Codes []={
            "300785468","29992168","300262814","300781932",
            "300929476","299973825","300417599","299699343","299343231"
            ,"299486387","299323505","299323505","299323505","299323135",
            "299322764","299322394","299941414","299705175","300094253",
            "299732546","300266682","300502055","299587115","300737429",
            "301421833","301422016","301454433","300786569","30079702",
            "301454433","300807093","300734448"};
    Location Alfouad, Rehan, Qasr, Demrdash, Cleopatra, DarElFouad,
            MahmoudSpecial, Dreamland, medicalcenteroctober, CityClinic, Alwaha, OctoberMilitary, AsiaClinics, ElShoroukHospital,
            SphinxSpecialized, Alharam, Tabarak, OctoberClinic, AlMehwar,
            Wafa, Salam, MaadiMilitary, QueenClinic, Sudan, ShifaSpecialist, Helal,
            Mohamady, Pola, zohoor, Horus, Ainshams;

    List<String> list = Arrays.asList(Codes);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        final boolean perlog = storage.getBoolean("signed up",false);
        final boolean pertut = storage.getBoolean("Tutorial",false);
        scrolll=findViewById(R.id.scrooool);
        logo= findViewById(R.id.logo);
        HospCode=findViewById(R.id.number);
        loginbg=findViewById(R.id.login_img);
        signup=findViewById(R.id.signup);
        weltxt= findViewById(R.id.welcome_txt);
        nicetxt=findViewById(R.id.nice_txt);
        animbg = AnimationUtils.loadAnimation(this, R.anim.spalsh);
        animlogo = AnimationUtils.loadAnimation(this, R.anim.logo_translate);
        animopen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        scrolll.setOnTouchListener( new View.OnTouchListener() {
            //this method prevents the user from scrolling the background of the login activity
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

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
            CityClinic.setLatitude(29.9506387);

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
        }
        final Location Locations[] = {
                Alfouad, Rehan, Qasr, Demrdash, Cleopatra, DarElFouad,
                MahmoudSpecial, Dreamland, medicalcenteroctober, CityClinic, Alwaha, OctoberMilitary, AsiaClinics, ElShoroukHospital,
                SphinxSpecialized, Alharam, Tabarak, OctoberClinic, AlMehwar,
                Wafa, Salam, MaadiMilitary, QueenClinic, Sudan, ShifaSpecialist, Helal,
                Mohamady, Pola, zohoor, Horus, Ainshams};

            new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!perlog){

                    loginbg.startAnimation(animbg);
                    logo.startAnimation(animlogo);
                    HospCode.startAnimation(animopen);
                    weltxt.startAnimation(animopen);
                    nicetxt.startAnimation(animopen);
                    signup.startAnimation(animopen);}
                else if (!pertut){
                    Intent intttt = new Intent(Login.this,MainActivity.class);
                    startActivity(intttt);
                    finish();
                }else {
                    Intent intttt = new Intent(Login.this,MainActivity.class);
                    startActivity(intttt);
                    finish();
                }

            }
        }, 2000);




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = HospCode.getEditText().getText().toString().trim();
if (list.contains(code)){
                for(int i=0;i<Codes.length;i++){
                    if(code.equals(Codes[+i])){

                        editor.putString("HospitalName",Names[+i]);
                        editor.putString("HospitalLocation",String.valueOf(Locations[+i].getLatitude())
                                +","+String.valueOf(Locations[+i].getLongitude()));
                        editor.commit();
                       // Toast.makeText(Login.this, Names[+i], Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                        editor.putBoolean("signed up",true);
                        editor.commit();
                        break;
                    }
                }
                }
else{HospCode.setError("invalid password");
if (!HospCode.getEditText().getText().toString().trim().equals(code.trim())){

}}

            }
        });


    }
    private boolean Errormsg() {
        String number = HospCode.getEditText().getText().toString().trim();

        if (number.isEmpty()) {
            HospCode.setError("Field can't be empty");
            return false;
        } else {
            HospCode.setError(null);
            return true;
        }
    }

}
