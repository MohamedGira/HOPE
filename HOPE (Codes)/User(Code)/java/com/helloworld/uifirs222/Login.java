package com.helloworld.uifirs222;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    ImageView hope,back,loginbg,logo;
    TextInputLayout phoneNumber;
    Animation animbg,animlogo, animopen;
    ScrollView scrolll;
    CardView signup;
    TextView weltxt,nicetxt;

    public static final String PREFS_NAME ="30" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        final boolean boolog = storage.getBoolean("signed up",false);
        final boolean bootut = storage.getBoolean("Tutorial",false);
        scrolll=findViewById(R.id.scrooool);
        logo= findViewById(R.id.logo);
        phoneNumber=findViewById(R.id.number);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                //if not logged in
                if (!boolog){
                    loginbg.startAnimation(animbg);
                    logo.startAnimation(animlogo);
                    phoneNumber.startAnimation(animopen);
                    weltxt.startAnimation(animopen);
                    nicetxt.startAnimation(animopen);
                    signup.startAnimation(animopen);}
                // if not Shown the tutorial
                else if (!bootut){
                    Intent intttt = new Intent(Login.this,Tutorial.class);
                    startActivity(intttt);
                }else {
                    Intent intttt = new Intent(Login.this,MainActivity.class);
                    startActivity(intttt);
                }

            }
        }, 2000);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = phoneNumber.getEditText().getText().toString().trim();

                String numwithcode= "+2"+number;
                Errormsg();

                Intent intent = new Intent(Login.this, Verifier.class);
                intent.putExtra("fullnumber",numwithcode);
                startActivity(intent);
            }
        });




















    }
    private boolean Errormsg() {
        String number = phoneNumber.getEditText().getText().toString().trim();

        if (number.isEmpty()) {
            phoneNumber.setError("Field can't be empty");
            return false;
        } else {
            phoneNumber.setError(null);
            return true;
        }
    }

}
