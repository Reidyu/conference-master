package com.example.franklin.conference.ui;

/**
 * Created by Franklin on 2018/3/31.
 */

public class meeting {
    private String name;
    private int imagID;


    public meeting(String name,int imagID) {
        this.name = name;
        this.imagID = imagID;
    }
    public String getName(){
        return name;
    }
    public int getImagID(){
        return imagID;
    }
}
