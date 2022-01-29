package com.helloworld.hosfis.ui.main;

public class specializationmodel {
    public String Specialization;
    public String Name;
    public String Number;



    public specializationmodel(String Specialization , String Name, String Number) {
        this.Name=Name;
        this.Specialization=Specialization;
        this.Number=Number;

    }

    public String getspecailname() { return Specialization; }
    public String getdocname() { return Name; }
    public String getpatnum() { return Number; }







}
