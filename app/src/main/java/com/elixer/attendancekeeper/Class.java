package com.elixer.attendancekeeper;

import java.util.ArrayList;

/**
 * Created by Admin on 1/2/2018.
 */

public class Class {
    public String name;
    public ArrayList<String> days;
    public ArrayList<String> daysTime;



    public boolean reminder;
    public boolean alarm;

    public int current;
    public int total;
    public Boolean status;
    int update ;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Class(String name, ArrayList<String> days, ArrayList<String> daysTime, boolean reminder, boolean alarm , int current, int total, Boolean status,int update) {

        this.name = name;
        this.days = days;
        this.daysTime=daysTime;
        this.reminder = reminder;
        this.alarm=alarm;
        this.current = current;
        this.total = total;

        this.status = status;
        this.update = update;



    }

    public String getName() {
        return name;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
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

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }


    public ArrayList<String> getDaysTime() {
        return daysTime;
    }

    public void setDaysTime(ArrayList<String> daysTime) {
        this.daysTime = daysTime;
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