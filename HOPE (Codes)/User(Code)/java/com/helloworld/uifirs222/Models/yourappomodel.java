package com.helloworld.uifirs222.Models;

public class yourappomodel {
    String apponame;
    String appocheck;
    String appotime;
    String hospname;
    String specialname;
    public yourappomodel(String appocheck, String apponame,String appotime,String hospname,String specialname) {
        this.specialname=specialname;
        this.hospname=hospname;
        this.appocheck = appocheck;
        this.apponame = apponame;
        this.appotime = appotime;
    }
    public String getspecailname() { return specialname; }
    public String getHospname() { return hospname; }
    public String getApponame() { return apponame; }

    public String getAppocheck() { return appocheck; }

    public String getAppotime() { return appotime; }
}

