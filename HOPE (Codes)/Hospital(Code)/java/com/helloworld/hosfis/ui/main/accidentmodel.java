package com.helloworld.hosfis.ui.main;

import android.graphics.Bitmap;

public class accidentmodel {

    public String Type;
    public String Location;
    public String Injuries;
    public String Distance;
    public Bitmap mp1,mp2;
    public String Time;
    public accidentmodel(String Type,String Location,String Injuries,String Distance,String Time,Bitmap mp1,Bitmap mp2) {

        this.Type=Type;
        this.Location=Location;
        this.Injuries=Injuries;
        this.Distance=Distance;
        this.Time=Time;
        this.mp1=mp1;
        this.mp2=mp2;
    }


    public String getDistance() { return Distance;}
    public String getType() { return Type;}
    public String getLocation(){return Location;}
    public String getInjuries() { return Injuries;}
    public String getTime() { return Time;}

    public Bitmap getMp1(){return  mp1;}
    public Bitmap getMp2(){return  mp2;}


}
