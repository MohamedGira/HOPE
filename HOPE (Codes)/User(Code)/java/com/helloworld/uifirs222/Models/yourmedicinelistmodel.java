package com.helloworld.uifirs222.Models;

import android.graphics.drawable.Drawable;

public class yourmedicinelistmodel {
String meddate ;
String medname;
Drawable img;
    public yourmedicinelistmodel(String date, String name, Drawable img) {
        this.meddate = date;
        this.medname = name;
        this.img = img;
    }

    public Drawable getImg() { return img; }

    public String getMeddate() {
        return meddate;
    }

    public String getMedname() {
        return medname;
    }
}

