package com.elixer.attendancekeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Admin on 1/22/2018.
 */

public class ListViewAdapter extends ArrayAdapter<Class> {
    //the list values in the List of type hero
    List<Class> classList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    TextView textActivity2;
    Boolean check;
    Class classes;
    TextView textViewCurrent,textViewTotal;


    //constructor initializing the values
    public ListViewAdapter(Context context, int resource, List<Class> classList) {
        super(context, resource, classList);
        this.context = context;
        this.resource = resource;
        this.classList = classList;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater



        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
       // ImageView imageView = view.findViewById(R.id.imageView);
        Switch status = view.findViewById(R.id.status);
        TextView textViewName = view.findViewById(R.id.className);
        textViewCurrent = view.findViewById(R.id.attendance);
        textViewTotal  = view.findViewById(R.id.attendancetotal);
        Button buttonAbsent = view.findViewById(R.id.button_absent);
        Button buttonPresent = view.findViewById(R.id.button_present);
        Button buttonOff = view.findViewById(R.id.button_off);
        String today = getDate();






      //  final TextView textPercentage = (TextView) view.findViewById(R.id.textViewPercentage);
      //  final TextView textPercentage = (TextView) findViewById(R.id.textPercentage);



        //getting the hero of the specified position
       classes = classList.get(position);

        //adding values to the list item
      //  imageView.setImageDrawable(context.getResources().getDrawable(hero.getImage()));
        textViewName.setText(classes.getName());
        textViewCurrent.setText(Integer.toString(classes.getCurrent()));
        textViewTotal.setText("/"+Integer.toString(classes.getTotal()));

        try {
            check = classes.getStatus();
            //  Log.d("check from data",check.toString());
            status.setChecked(check);
        }catch (Exception er){
            er.printStackTrace();
        }

        //Making buttons appear if today string not found and setting up notifacations and alars
        Boolean found =searchDay(today);
     //   Log.e("BOOOLEAN",found.toString());
        if(found){
         //   Log.e("INVI","INVIIIII");
            buttonAbsent.setVisibility(View.VISIBLE);
            buttonPresent.setVisibility(View.VISIBLE);
            buttonOff.setVisibility(View.VISIBLE);

            //setting  notifications
          //  setAlarm();

        }


        //Buttons initialised here
        buttonPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes=classList.get(position);
                int current = classes.getCurrent();
                int total = classes.getTotal();
                current = current+1;
                total=total+1;
                classes.setCurrent(current);
                classes.setTotal(total);
                saveinsharedpref(classes);
            }
        });

        buttonAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes=classList.get(position);
                int current = classes.getCurrent();
                int total = classes.getTotal();
               // current = current+1;
                total=total+1;
               // classes.setCurrent(current);
                classes.setTotal(total);
                saveinsharedpref(classes);
            }
        });

        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes=classList.get(position);
                int current = classes.getCurrent();
                int total = classes.getTotal();

                saveinsharedpref(classes);
            }
        });

        //If switch clicked
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes = classList.get(position);
                check = classes.getStatus();
                Log.d(classes.getName().toString() + " check from data ", check.toString());


                if (check) {
                    check = false;
                    cancelReminder(classes.getName());
                } else {
                    check = true;
                }
                classes.setStatus(check);

                saveinsharedpref(classes);

             //   Log.d(classes.getName().toString() + " checkto loop", check.toString());
            //    Intent intentService = new Intent(context,Run.class);
              //  startRun(context);


            }

            private void saveinsharedpref(Class newclass) {



                Gson gson = new Gson();
                String newClass = gson.toJson(classes);
                SharedPreferences sharedPref =getContext().getSharedPreferences("attend",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(newclass.name,newClass);
                editor.commit();
            }
        });







        return view;
    }

    private void cancelReminder(String name) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        int hashcode = name.hashCode();
        Log.e("Cancell, Alarm","cancelling....."+ name+ String.valueOf(hashcode));
        //Intent myIntent = new Intent(getContext(),NotificationPublisher.class);
        Intent notificationMessage = new Intent(getContext(),NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),hashcode, notificationMessage, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();

        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm() {


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 26);
        calendar.set(Calendar.SECOND, 0);

        Intent notificationMessage = new Intent(getContext(),NotificationPublisher.class);
       // Log.e("Setting Alarm",calendar.toString());

//This is alarm manager
      //  PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0 , notificationMessage, PendingIntent.FLAG_UPDATE_CURRENT);
        //calendar.setTimeInMillis(time);
      //  AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getContext(), 0, notificationMessage, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (20 * 1000), pendingIntent);
        Toast.makeText(getContext(),"Alarm sent",Toast.LENGTH_SHORT).show();
    }

    private Boolean searchDay(String today) {
        Boolean val=false;
        //Load the list into a hashSet
        Set<String> set = new HashSet<String>(classes.getDays());
        if (set.contains(today))
        {
            Log.e("BOOOLEAN","FOUND");
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

    private void saveinsharedpref(Class newclass) {



        Gson gson = new Gson();
        String newClass = gson.toJson(classes);
        SharedPreferences sharedPref =getContext().getSharedPreferences("attend",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(newclass.name,newClass);
        //update in listview
        editor.commit();
       // textViewCurrent.setText(Integer.toString(classes.getCurrent()));
       // textViewTotal.setText("/"+Integer.toString(classes.getTotal()));
        notifyDataSetChanged();
    }

    private void startRun(Context context){
        Intent intentService = new Intent(context,Run.class);
        context.startService(intentService);
    }


}