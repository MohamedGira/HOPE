package com.helloworld.uifirs222.Models;

public class otherhospitalmodel {
    public String Name;
    public String Kind;
    public Double Distance;
    public String Location;

    public int Logo;


    public otherhospitalmodel(String Name, String Kind, int Logo, Double Distance, String Location) {
        this.Name=Name;
        this.Kind=Kind;
        this.Logo=Logo;
        this.Distance=Distance;
        this.Location= Location;

    }


    public Double getDistance() {

        return Distance;
    }


}
