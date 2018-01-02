package com.elixer.attendancekeeper;

/**
 * Created by Admin on 1/2/2018.
 */

public class Class {
    public String name;
    public String[] days;
    public boolean remind;
    public long remindTime;


    public Class(String name, String[] days, boolean remind, long remindTime) {

        this.name = name;
        this.days = days;
        this.remind = remind;
        this.remindTime = remindTime;


    }

}