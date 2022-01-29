package com.helloworld.hosfis.ui.main;

import android.graphics.Bitmap;

public class accidentmodelone {

    public String Type;
    public String Location;
    public String Injuries;
    public String Distance;
    public Bitmap mp1;
    public accidentmodelone(String Type,String Location,String Injuries,String Distance,Bitmap mp1) {

        this.Type=Type;
        this.Location=Location;
        this.Injuries=Injuries;
        this.Distance=Distance;
        this.mp1=mp1;

    }


    public String getDistance() { return Distance;}
    public String getType() { return Type;}
    public String getLocation(){return Location;}
    public String getInjuries() { return Injuries;}
    public Bitmap getMp1(){return  mp1;}


}
