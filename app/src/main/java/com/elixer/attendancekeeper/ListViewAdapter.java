package com.elixer.attendancekeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    DecoView arcView;
    View views;
    View viewTitle;
    int indexTime;
    ArrayList<String> daysTime;
    int activeClassesCount;
    TextView textViewClassNumber;
    Button buttonPresent,buttonOff,buttonAbsent;


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
        //textViewClassNumber=view.findViewById(R.id.textviewClassNumber);
        TextView textViewTime = view.findViewById(R.id.textViewTime);
        DecoView arcView = (DecoView) view.findViewById(R.id.dynamicArcView);
         buttonAbsent = view.findViewById(R.id.button_absent);
        buttonPresent = view.findViewById(R.id.button_present);
        buttonOff = view.findViewById(R.id.button_off);
        views = view.findViewById(R.id.view);
        viewTitle = view.findViewById(R.id.viewTitle);
        String today = getDate();
        //listtime
        daysTime = new ArrayList<>();
        activeClassesCount = 0;
       // textViewClassNumber = view.findViewById(R.id.textViewClassCount);


        //  final TextView textPercentage = (TextView) view.findViewById(R.id.textViewPercentage);
        //  final TextView textPercentage = (TextView) findViewById(R.id.textPercentage);


        //getting the hero of the specified position
        classes = classList.get(position);

        //adding values to the list item
        //  imageView.setImageDrawable(context.getResources().getDrawable(hero.getImage()));
        textViewName.setText(classes.getName());

        textViewCurrent.setText(" " + Integer.toString(classes.getCurrent()) + "/" + Integer.toString(classes.getTotal()));
        //  textViewTotal.setText("/"+Integer.toString(classes.getTotal()));
        // Create background track
        //
        //
        Log.e("curent ", String.valueOf(classes.getCurrent()) + "      " + String.valueOf(classes.getTotal()));
//        Log.e("per", String.valueOf(classes.getCurrent() / classes.getTotal()));
        float percentage;
        try {
           percentage = (classes.getCurrent() * 100) / classes.getTotal();
        }catch (ArithmeticException ex){
            percentage=0;
        }


        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(20f)
                .build());


        //   int backIndex = srcView.addSeries(seriesItem);
        final SeriesItem seriesItem1;


        if (percentage > 75.00) {
            //Create data series track
            seriesItem1 = new SeriesItem.Builder(Color.argb(255, 79, 196, 0))

                    .setRange(0, 100, 0)
                    .setLineWidth(14f)
                    .setSpinDuration(800)
                    .build();


        } else {


            seriesItem1 = new SeriesItem.Builder(Color.RED)

                    .setRange(0, 100, 0)
                    .setLineWidth(14f)
                    .setSpinDuration(800)
                    .build();
        }


        int series1Index = arcView.addSeries(seriesItem1);

        // int seriesIndex2 = arcView.addSeries(seriesItem2);
        //Calculating %age


        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(200)
                .build());

        arcView.addEvent(new DecoEvent.Builder(percentage).setIndex(series1Index).setDelay(3).build());


        //Textview
        final TextView textPercentage = (TextView) view.findViewById(R.id.textViewPercentage);
        seriesItem1.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                float percentFilled = ((currentPosition - seriesItem1.getMinValue()) / (seriesItem1.getMaxValue() - seriesItem1.getMinValue()));
                textPercentage.setText(String.format("%.0f%%", percentFilled * 100f));
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
        //arcView.addEvent(new DecoEvent.Builder(percentage).setIndex(seriesIndex2).setDelay(10).build());

        try {
            check = classes.getStatus();
            //  Log.d("check from data",check.toString());
            status.setChecked(check);
        } catch (Exception er) {
            er.printStackTrace();
        }

        //Making buttons appear if today string not found and setting up notifacations and alars
        Boolean found = searchDay(today);
        //   Log.e("BOOOLEAN",found.toString());
        if (found) {
            //   Log.e("INVI","INVIIIII");
            buttonAbsent.setVisibility(View.VISIBLE);
            buttonPresent.setVisibility(View.VISIBLE);
            buttonOff.setVisibility(View.VISIBLE);
            views.setVisibility(View.VISIBLE);
            viewTitle.setBackgroundColor(Color.parseColor("#FB5056"));
            activeClassesCount = activeClassesCount + 1;

            try {
                Log.e("classe time", classes.getDaysTime().toString());
                List<String> listDaysTime = new ArrayList<String>(classes.getDaysTime());
                textViewTime.setText("Class at " + listDaysTime.get(indexTime) + " today!");
            } catch (Exception er) {
                er.printStackTrace();
            }

            //setting  notifications
            //  setAlarm();

        }

        if(classes.getUpdate()==1){
            buttonInvisible();

        }


//         textViewClassNumber.setText(String.valueOf(activeClassesCount)+ " classes today.");
        //Buttons initialised here
        buttonPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes = classList.get(position);
                int current = classes.getCurrent();
                int total = classes.getTotal();
                current = current + 1;
                total = total + 1;
                classes.setCurrent(current);
                classes.setTotal(total);
                cancelReminder(classes.getName());
                saveinsharedpref(classes);
                buttonInvisible();
                classes.setUpdate(1);
                saveinsharedpref(classes);
            }
        });

        buttonAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes = classList.get(position);
                int current = classes.getCurrent();
                int total = classes.getTotal();
                // current = current+1;
                total = total + 1;
                // classes.setCurrent(current);
                classes.setTotal(total);
                cancelReminder(classes.getName());
                saveinsharedpref(classes);
                buttonInvisible();
                classes.setUpdate(1);
                saveinsharedpref(classes);
            }
        });

        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classes = classList.get(position);
                int current = classes.getCurrent();
                int total = classes.getTotal();
                cancelReminder(classes.getName());

                saveinsharedpref(classes);
                buttonInvisible();
                classes.setUpdate(1);
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
                    //Runing service again
                    Intent intentService = new Intent(context, Run.class);
                    context.startService(intentService);

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
                SharedPreferences sharedPref = getContext().getSharedPreferences("attend", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(newclass.name, newClass);
                editor.commit();
            }
        });








        return view;
    }

    private void buttonInvisible() {
        {
            buttonAbsent.setVisibility(View.GONE);
            buttonOff.setVisibility(View.GONE);
            buttonPresent.setVisibility(View.GONE);
            viewTitle.setBackgroundColor(Color.parseColor("#dedede"));
            Log.e("INVI..","invvv");
        }


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
        List <String> listDays = new ArrayList<String>(classes.getDays());
        for (String string : listDays)
        {
            if(string.matches(today)){
                // Log.e("BOOOLEAN","FOUND");
                val = true;
                indexTime = listDays.indexOf(string);
                Log.e("index Value",String.valueOf(indexTime));



            }


            //Set<String> time = new HashSet<String>(classes.getDaysTime());
        }

        return val;
    }

    private String getDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
      //  Log.e("dayyyy",dayOfTheWeek);
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