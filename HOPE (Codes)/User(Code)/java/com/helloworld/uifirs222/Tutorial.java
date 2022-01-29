package com.helloworld.uifirs222;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import androidx.viewpager.widget.ViewPager;

import com.helloworld.uifirs222.Adapers.SectionsPagerAdapter;
import com.helloworld.uifirs222.Adapers.tutorialadapter;

public class Tutorial extends AppCompatActivity {
    public static final String PREFS_NAME ="28" ;
    LinearLayout bottombar, allay;
    ViewPager viewpagerxml;
    TextView  nextbutt;
    tutorialadapter tutada;
    TextView[] dots;
    TextView txxxxxt;
    int pagenumber;
    CardView sgin_in_card;
    ValueAnimator viewpageranimation;
    Animation cardopen;
    SectionsPagerAdapter mSectionsPagerAdapter;
    int colors [] = {0xFF42C3F7,0xFF00AB2E,0xFF003A6B,0xFFFFBB00,0xFFF74242};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        bottombar= findViewById(R.id.bottombar);
        viewpagerxml= findViewById(R.id.viewpagerxml);
        txxxxxt = findViewById(R.id.thistxt);
        allay=findViewById(R.id.alllay);
        sgin_in_card = findViewById(R.id.sign_in_card);
        mSectionsPagerAdapter = new SectionsPagerAdapter(Tutorial.this,getSupportFragmentManager());
        cardopen= AnimationUtils.loadAnimation(Tutorial.this, R.anim.fab_open);
        tutada= new tutorialadapter(this, getLayoutInflater());
        viewpagerxml.setAdapter(tutada);
        final SharedPreferences storage = this.getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        final boolean pertut = storage.getBoolean("Tutorial",false);
        adddotsindicator(0);
        viewpagerxml.addOnPageChangeListener(Viewlisten);
        sgin_in_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intlog = new Intent(getApplicationContext(),MainActivity.class);
                intlog.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intlog);
                editor.putBoolean("Tutorial",true);
                editor.commit();
            }
        });
    }

    public void adddotsindicator(int position){

        dots = new TextView[5];
        bottombar.removeAllViews();
        for (int i=0; i<dots.length;i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setGravity(View.TEXT_ALIGNMENT_CENTER);
            dots[i].setTextSize(45);
            dots[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dots[i].setTextColor(getResources().getColor(R.color.Greywhite));

            bottombar.addView(dots[i]); }


        if (dots.length>0){

            dots[position].setTextColor(getResources().getColor(R.color.white));

        }
    }



    ViewPager.OnPageChangeListener Viewlisten = new ViewPager.OnPageChangeListener() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if(position < (mSectionsPagerAdapter.getCount()+1 ) && position < (colors.length -1)) {

                viewpagerxml.setBackgroundColor((Integer) ArgbEvaluator.getInstance().evaluate(positionOffset, colors[position], colors[position +1]));
                allay.setBackgroundColor((Integer) ArgbEvaluator.getInstance().evaluate(positionOffset, colors[position], colors[position +1]));
            } else {


                viewpagerxml.setBackgroundColor(colors[colors.length -1]);
                allay.setBackgroundColor(colors[colors.length -1]);
            }
        }

        @Override
        public void onPageSelected(int position) {

            adddotsindicator(position);
            pagenumber = position;
            if (pagenumber==4 )
            {
                sgin_in_card.startAnimation(cardopen);
                sgin_in_card.setClickable(true);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) { }



    };
}
