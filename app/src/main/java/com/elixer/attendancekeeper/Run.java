package com.elixer.attendancekeeper;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Run extends IntentService {
    List<Class> classList;
    Class classes;

    // Must create a default constructor
    public Run() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
        classList = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // This describes what will happen when service is triggered
        Log.e("Service Running","To set alarms/Reminder for individual");


        fetchAllPreference();
        setUpAlarmForNextDay();

    }

    private void setUpAlarmForNextDay() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,Calendar.DATE+1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);

        Intent intentService = new Intent(this,Run.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0000, intentService, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
        //Toast.makeText(this,"Daily set alarm",Toast.LENGTH_LONG).show();
        Log.e("Alarm sice to run again","Alarm Manager for Service at 0.05  " + calendar.get(Calendar.DAY_OF_WEEK));
    }

    private Boolean searchDay(Class classes,String today) {

        Boolean val=false;
        //Load the list into a hashSet
        Set<String> set = new HashSet<String>(classes.getDays());
        if (set.contains(today))
        {
            //Log.e("BOOOLEAN","FOUND");
            val = true;
        }
        return val;
    }

    private String getDate() {


            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);
            Log.e("dayyyy",dayOfTheWeek);
            return dayOfTheWeek;
        }



    //Fetching from Database
    public void fetchAllPreference(){
        SharedPreferences prefA =getApplicationContext().getSharedPreferences("attend", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefA.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            //Converting from Json to Object
            /** Use the string printed to create a new object */
            //Creating Gson
            Gson gson = new Gson();
            Class classes = gson.fromJson(entry.getValue().toString(), Class.class);

            /** Print one variable to the console */
            System.out.println("Username : " + classes.getName());
            String today=getDate();
            Boolean found =searchDay(classes,today);
            classList.add(classes);
         //   Log.e("BOOOLEAN",found.toString());
            if(found && classes.getStatus() && classes.isReminder()){
                //Same day as Today,Set alarm here
                Log.e("Setting Alarm for class",classes.getName());
                setAlarm(classes.getName());

            }



        }


    }
    private void setAlarm(String className) {


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 26);
        calendar.set(Calendar.SECOND, 0);

        Intent notificationMessage = new Intent(this,NotificationPublisher.class);
         //Log.e("Setting Alarm.",".............");

//This is alarm manager
        //  PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0 , notificationMessage, PendingIntent.FLAG_UPDATE_CURRENT);
        //calendar.setTimeInMillis(time);
        //  AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        int hashcode = className.hashCode();
        Log.e("unique id from string", String.valueOf(hashcode));//1843668795
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,hashcode, notificationMessage, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (30 * 1000), pendingIntent);

    }
}
