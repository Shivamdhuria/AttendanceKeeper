package com.elixer.attendancekeeper;

import java.util.ArrayList;

/**
 * Created by Admin on 1/2/2018.
 */

public class Class {
    public String name;
    public ArrayList<String> days;
    public boolean reminder;
    public boolean alarm;
    public long remindTime;


    public Class(String name, ArrayList<String> days, boolean reminder,boolean alarm, long remindTime) {

        this.name = name;
        this.days = days;
        this.reminder = reminder;
        this.alarm=alarm;
        this.remindTime = remindTime;


    }

}