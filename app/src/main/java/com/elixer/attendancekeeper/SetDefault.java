package com.elixer.attendancekeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

public class SetDefault extends AppCompatActivity {
    Intent intentReceived;
    TextView textViewClassName;
    String className;
    EditText edittextCurrent, edittextTotal;
    FloatingActionButton buttonAddTotal, buttonMinusTotal, buttonAddCurrent, buttonMinusCurrent;


    float percentage;
    DecoView arcView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_default);


        //Toast.makeText(getApplicationContext(),className,Toast.LENGTH_SHORT).show();

        edittextCurrent = (EditText) findViewById(R.id.editTextCurrent);
        edittextTotal = (EditText) findViewById(R.id.editTextTotal);
        buttonAddTotal = (FloatingActionButton) findViewById(R.id.buttonAddTotal);
        buttonMinusTotal = (FloatingActionButton) findViewById(R.id.buttonMinusTotal);
        buttonAddCurrent = (FloatingActionButton) findViewById(R.id.buttonAddCurrent);
        buttonMinusCurrent = (FloatingActionButton) findViewById(R.id.buttonMinusCurrent);

        //scrollView = (ScrollView)findViewById(R.id.scrollview);
        arcView = (DecoView)findViewById(R.id.dynamicArcView);




        //Adding percentage
        buttonAddTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextTotal.setText(String.valueOf(Integer.parseInt(edittextTotal.getText().toString()) + 1));


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
                edittextTotal.setText(String.valueOf(Integer.parseInt(edittextTotal.getText().toString()) - 1));


                //Update the Percentage
                //Refresh for decoview
                decoRefresh(view);


            }
        });

        buttonAddCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextCurrent.setText(String.valueOf(Integer.parseInt(edittextCurrent.getText().toString()) + 1));



            }
        });
        buttonMinusCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittextCurrent.setText(String.valueOf(Integer.parseInt(edittextCurrent.getText().toString()) - 1));


            }
        });


        //Decoview
        try {
           percentage = Integer.parseInt(edittextTotal.getText().toString());
        }catch (ArithmeticException ex){
          //  percentage=0;
        }


        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(20f)
                .build());


        //   int backIndex = srcView.addSeries(seriesItem);
        final SeriesItem seriesItem1;



            //Create data series track
            seriesItem1 = new SeriesItem.Builder(Color.argb(255, 79, 196, 0))

                    .setRange(0, 100, 0)
                    .setLineWidth(14f)
                    .setSpinDuration(800)
                    .build();





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



    private void saveinsharedpref(Class newclass) {



        SharedPreferences sharedPref = getSharedPreferences("attend", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.commit();
    }

    // create an action bar button





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
           percentage = Integer.parseInt(edittextTotal.getText().toString());
        }catch (ArithmeticException ex){
           // percentage=0;
        }


        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(20f)
                .build());


        //   int backIndex = srcView.addSeries(seriesItem);
        final SeriesItem seriesItem1;



            //Create data series track
            seriesItem1 = new SeriesItem.Builder(Color.argb(255, 79, 196, 0))

                    .setRange(0, 100, 0)
                    .setLineWidth(14f)
                    .setSpinDuration(800)
                    .build();





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

    public void getSavedValuees(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {

            editor.get
            editor.commit();
        }catch (Exception ex){


        }


    }



}
