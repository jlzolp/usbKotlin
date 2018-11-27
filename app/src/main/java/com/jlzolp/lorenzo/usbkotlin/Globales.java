package com.jlzolp.lorenzo.usbkotlin;

import android.app.Application;

public class Globales extends Application {
    private static Globales instance;

    // Global variable
    private int bau=0;
    private int dts;
    private int sps;
    private int pard;
    private int cntl;
    private boolean bandera;


    // Restrict the constructor from being instantiated
    private Globales(){}

    public void setBandera(boolean B){
        this.bandera=B;
    }
    public boolean getBandera(){
        return this.bandera;
    }

    public void setbau (int b){
        this.bau=b;
    }
    public int getbau() {
        return this.bau;
    }

    public void setdts (int d){
        this.dts=d;
    }
    public int getdts() {
        return this.dts;
    }

    public void setsps (int s){
        this.sps=s;
    }
    public int getsps() {
        return this.sps;
    }

    public void setpard (int p){
        this.pard=p;
    }
    public int getpard() {
        return this.pard;
    }

    public void setcntl (int c){
        this.cntl=c;
    }
    public int getcntl() {
        return this.cntl;
    }

    public static synchronized Globales getInstance(){
        if(instance==null){
            instance=new Globales();
        }
        return instance;
    }
}
