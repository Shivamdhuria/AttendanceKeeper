package com.elixer.attendancekeeper;

import java.util.ArrayList;

/**
 * Created by Admin on 1/2/2018.
 */

public class Class {
    public String name;
    public ArrayList<Integer> days;
    static ArrayList<String> daysTime;



    public boolean reminder;
    public boolean alarm;



    public Class(String name, ArrayList<Integer> days,ArrayList<String> daysTime, boolean reminder,boolean alarm ) {

        this.name = name;
        this.days = days;
        this.daysTime=daysTime;
        this.reminder = reminder;
        this.alarm=alarm;



    }

}