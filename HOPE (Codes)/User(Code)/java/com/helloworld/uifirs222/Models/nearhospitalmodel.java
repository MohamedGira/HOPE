package com.helloworld.uifirs222.Models;

public class nearhospitalmodel {
    public String Name;
    public String Kind;
    public Double Distance;
    public String Location;
    public String Number;
    public int Logo;


    public nearhospitalmodel(String Name, String Kind, int Logo, Double Distance, String Location,String Number) {
        this.Name=Name;
        this.Kind=Kind;
        this.Logo=Logo;
        this.Location= Location;
        this.Distance=Distance;
        this.Number=Number;

    }


    public Double getDistance() {

        return Distance;
    }



}
