package com.helloworld.uifirs222.ui.main;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

public class imagemodel {

    public Drawable mp1;
    public String name;
    public String dose;
    public imagemodel (Drawable mp1,String name,String dose) {

        this.mp1=mp1;
        this.name=name;
        this.dose=dose;

    }



    public Drawable getMp1(){return  mp1;}
    public String getName(){return name;};
    public String getDose(){return dose;};



}
