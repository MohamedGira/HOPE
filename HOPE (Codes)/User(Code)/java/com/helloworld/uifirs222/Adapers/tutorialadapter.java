package com.helloworld.uifirs222.Adapers;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.helloworld.uifirs222.R;

public class tutorialadapter extends PagerAdapter {
Context contextt;
LayoutInflater layoutInflaterr;

    public tutorialadapter(Context contextt, LayoutInflater layoutInflaterr) {
        this.contextt = contextt;
        this.layoutInflaterr = layoutInflaterr;
    }

    String [] disitemarr = {"We are Happy To have You Here. This app is your medical companion." +
            " It is especially designed to ease your medical activity in Egypt.","This App is composed of " +
            "3 Main Services (A Medical Reminder, A Hospital Directory, and an Accident Reporting System) each" +
            " of which are designed to do certain tasks to help you have better experience with the egyptian health sector",
            "The purpose of this section of the app is to remind the people with chronic disease to take their medications regularly." +
                    " also it have a reminder for your medical appointments with Doctors or medical consultants ",
            "The purpose of this section is to provide You with a detailed profile of the hospitals which are contain crucial information about the hospital like (number of blood bags, available beds...etc)"
    ,"This is is the essintial section of our app as it is responsible for the lives of those who are injured. This section has an system that can report exact information about any accident, which decrease the ambulance arrival time significantly."};
    String [] headitemarr = {"Welcome To The APP!", "WE Hope You Enjoy The APP!", "A Medical Reminder","A Hospital Directory","An Accident Reporting System"};
    int [] imgitemarr = {R.drawable.ic_welcome_vector,R.drawable.ic_reminder_vector,R.drawable.ic_reminder_vector,R.drawable.ic_hospital_vector,R.drawable.ic_accdent_vector};
    @Override
    public int getCount() {
        return headitemarr.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o; }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflaterr = (LayoutInflater) contextt.getSystemService(contextt.LAYOUT_INFLATER_SERVICE);
       View v= layoutInflaterr.inflate(R.layout.tutorialitem,container,false);
ImageView imgTextView =v.findViewById(R.id.tutimgitemxml);
        TextView txtdicription= v.findViewById(R.id.tutdisitemxml);
        TextView txthead= v.findViewById(R.id.tutheaditemxml);
        txthead.setText(headitemarr[position]);
        txtdicription.setText(disitemarr[position]);
        imgTextView.setImageResource(imgitemarr[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
container.removeView((View) object);
    }
}
