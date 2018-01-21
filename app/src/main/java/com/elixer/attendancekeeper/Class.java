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

    public int current;
    public int total;



    public Class(String name, ArrayList<Integer> days,ArrayList<String> daysTime, boolean reminder,boolean alarm ,int current,int total) {

        this.name = name;
        this.days = days;
        this.daysTime=daysTime;
        this.reminder = reminder;
        this.alarm=alarm;
        this.current = current;
        this.total = total;



    }

    public String getName() {
        return name;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        this.days = days;
    }

    public static ArrayList<String> getDaysTime() {
        return daysTime;
    }

    public static void setDaysTime(ArrayList<String> daysTime) {
        Class.daysTime = daysTime;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}