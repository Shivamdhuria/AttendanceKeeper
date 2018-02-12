package com.elixer.attendancekeeper;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

public class ClassDescriptionAndSettings extends AppCompatActivity {
    Intent intentReceived;
    TextView textViewClassName;
    String className;
    EditText edittextCurrent, edittextTotal;
    Button buttonAddTotal, buttonMinusTotal, buttonAddCurrent, buttonMinusCurrent;
    Class classes;
    ScrollView scrollView;
    CheckBox checkboxAlarm, checkboxReminder;
    float percentage;
    DecoView arcView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_description_and_settings);

        intentReceived = getIntent();
        className = intentReceived.getStringExtra("classname");
        //Toast.makeText(getApplicationContext(),className,Toast.LENGTH_SHORT).show();
        textViewClassName = (TextView) findViewById(R.id.className);
        edittextCurrent = (EditText) findViewById(R.id.editTextCurrent);
        edittextTotal = (EditText) findViewById(R.id.editTextTotal);
        buttonAddTotal = (Button) findViewById(R.id.buttonAddTotal);
        buttonMinusTotal = (Button) findViewById(R.id.buttonMinusTotal);
        buttonAddCurrent = (Button) findViewById(R.id.buttonAddCurrent);
        buttonMinusCurrent = (Button) findViewById(R.id.buttonMinusCurrent);
        checkboxAlarm = (CheckBox) findViewById(R.id.check_box_alarm);
        checkboxReminder = (CheckBox) findViewById(R.id.check_box_reminder);
        //scrollView = (ScrollView)findViewById(R.id.scrollview);
        arcView = (DecoView)findViewById(R.id.dynamicArcView);

        textViewClassName.setText(className);
        getClassByName(className);


        buttonAddTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextTotal.setText(String.valueOf(classes.getTotal() + 1));

                classes.setTotal(Integer.parseInt(edittextTotal.getText().toString()));
                saveinsharedpref(classes);
                //Intent intentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intentMainActivity);
                //Update the Percentage
                //Refresh for decoview
                decoRefresh(view);




            }
        });
        buttonMinusTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextTotal.setText(String.valueOf(classes.getTotal() - 1));

                classes.setTotal(Integer.parseInt(edittextTotal.getText().toString()));
                saveinsharedpref(classes);
                //Intent intentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intentMainActivity);
                //Update the Percentage
                //Refresh for decoview
                decoRefresh(view);


            }
        });

        buttonAddCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextCurrent.setText(String.valueOf(classes.getCurrent() + 1));
                classes.setCurrent(Integer.parseInt(edittextCurrent.getText().toString()));

                saveinsharedpref(classes);
                //Intent intentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intentMainActivity);
                //Update the Percentage
                //Refresh for decoview
                decoRefresh(view);


            }
        });
        buttonMinusCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextCurrent.setText(String.valueOf(classes.getCurrent() - 1));
                classes.setCurrent(Integer.parseInt(edittextCurrent.getText().toString()));

                saveinsharedpref(classes);
                //Intent intentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intentMainActivity);
                //Update the Percentage
                //Refresh for decoview
                decoRefresh(view);


            }
        });
        checkboxAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkboxAlarm.isChecked() && checkboxReminder.isChecked()) {
                    checkboxReminder.setChecked(false);
                }
                classes.setAlarm(checkboxAlarm.isChecked());
                classes.setReminder(checkboxReminder.isChecked());
                saveinsharedpref(classes);
            }
        });
        checkboxReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkboxAlarm.isChecked() && checkboxReminder.isChecked()) {
                    checkboxAlarm.setChecked(false);
                }
                classes.setAlarm(checkboxAlarm.isChecked());
                classes.setReminder(checkboxReminder.isChecked());
                saveinsharedpref(classes);
            }
        });

        //Decoview
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
        final TextView textPercentage = (TextView)findViewById(R.id.textViewPercentage);
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


    }

    private void getClassByName(String className) {

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("attend", Context.MODE_PRIVATE);

        String classGson = sharedPref.getString(className, "");

        //Converting from Json to Object
        /** Use the string printed to create a new object */
        //Creating Gson
        Gson gson = new Gson();
        classes = gson.fromJson(classGson.toString(), Class.class);
        edittextTotal.setText(Integer.toString(classes.getTotal()));
        edittextCurrent.setText(Integer.toString(classes.getCurrent()));
        checkboxReminder.setChecked(classes.isReminder());
        checkboxAlarm.setChecked(classes.isAlarm());
    }

    private void saveinsharedpref(Class newclass) {


        Gson gson = new Gson();
        String newClass = gson.toJson(classes);
        SharedPreferences sharedPref = getSharedPreferences("attend", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(newclass.name, newClass);
        editor.commit();
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        BuildDialogBox();
        Toast.makeText(getApplicationContext(), "Blue", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void BuildDialogBox() {

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //Delete from Shared Preferences
                //Saving objects in String gson

                try {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("attend", Context.MODE_PRIVATE);
                    sharedPref.edit().remove(className).commit();
                    cancelReminder(className);
                    Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentMainActivity);
                } catch (Exception er) {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
            }
        });
// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Are you sure you want to delete " + className + "?")
                .setTitle("Delete Class");

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void cancelReminder(String name) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int hashcode = name.hashCode();
        Log.e("Cancell, Alarm", "cancelling....." + name + String.valueOf(hashcode));
        //Intent myIntent = new Intent(getContext(),NotificationPublisher.class);
        Intent notificationMessage = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, hashcode, notificationMessage, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();

        alarmManager.cancel(pendingIntent);
    }



    public void decoRefresh (View v){
        //Decoview
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
        final TextView textPercentage = (TextView)findViewById(R.id.textViewPercentage);
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
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

}