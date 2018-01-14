package com.elixer.attendancekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static com.elixer.attendancekeeper.NewClass.daysTime;
import static com.elixer.attendancekeeper.SelectDay.arrayList;

public class SelectTime extends AppCompatActivity {
    EditText mondayTime,tuesdayTime,wednesdayTime,thursdayTime,fridayTime,saturdayTime,sundayTime,defaultTime;
    TextView textViewMonday,textViewTuesday,textViewWednesday,textViewThursday,textViewFriday,textViewSaturday,textViewSunday;
    Button button_submit;
    GridLayout gridLayout;
    RelativeLayout relativeLayout;
    CheckBox checkbox;
    int i;
    protected static ArrayList<String> arrayListTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
     //   SelectDay.arrayList;
        relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        gridLayout = (GridLayout)findViewById(R.id.daysLayout);
        i=0;
        //initialize checkbox
        checkbox = (CheckBox)findViewById(R.id.check_box_default);


        Log.e("Days", arrayList.toString());
        //textview
        textViewMonday=(TextView) findViewById(R.id.textview_monday);
        textViewTuesday=(TextView) findViewById(R.id.textview_tuesday);
        textViewWednesday=(TextView) findViewById(R.id.textview_wednesday);
        textViewThursday=(TextView) findViewById(R.id.textview_thursday);
        textViewFriday=(TextView) findViewById(R.id.textview_friday);
        textViewSaturday=(TextView) findViewById(R.id.textview_saturday);
        textViewSunday=(TextView) findViewById(R.id.textview_sunday);


        //edittext time
        defaultTime= (EditText)findViewById(R.id.edittext_default) ;
        mondayTime=(EditText)findViewById(R.id.edittext_monday_time);
        tuesdayTime=(EditText)findViewById(R.id.edittext_tuesday_time);

        wednesdayTime=(EditText)findViewById(R.id.edittext_wednesday_time);

        thursdayTime=(EditText)findViewById(R.id.edittext_thursday_time);

        fridayTime=(EditText)findViewById(R.id.edittext_friday_time);

        saturdayTime=(EditText)findViewById(R.id.edittext_saturday_time);

       sundayTime=(EditText)findViewById(R.id.edittext_sunday_time);
        button_submit =(Button)findViewById(R.id.button_submit);
        //Making invisible
        for (int i = 0; i < 7; i++) {

            //if default time isn't set

            if (arrayList.get(i) == 0) {
                //For MONDAY
                if (i == 0) {
                    textViewMonday.setVisibility(View.INVISIBLE);
                    mondayTime.setVisibility(View.INVISIBLE);

                }

                //For TUESDAY
                if (i == 1) {
                    textViewTuesday.setVisibility(View.INVISIBLE);
                    tuesdayTime.setVisibility(View.INVISIBLE);


                }


                //For WEDNESDAY
                if (i == 2) {
                    textViewWednesday.setVisibility(View.INVISIBLE);
                    wednesdayTime.setVisibility(View.INVISIBLE);


                }

                //For ThursDAY
                if (i == 3) {
                    textViewThursday.setVisibility(View.INVISIBLE);
                    thursdayTime.setVisibility(View.INVISIBLE);


                }

                //For FrIDAY
                if (i == 4) {
                    textViewFriday.setVisibility(View.INVISIBLE);
                    fridayTime.setVisibility(View.INVISIBLE);


                }

                //For SATURDAY
                if (i == 5) {
                    textViewSaturday.setVisibility(View.INVISIBLE);
                    saturdayTime.setVisibility(View.INVISIBLE);


                }

                //For SUNDAY
                if (i == 6) {
                    textViewSunday.setVisibility(View.INVISIBLE);
                    sundayTime.setVisibility(View.INVISIBLE);


                }
            }
        }


        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Douh!!",Toast.LENGTH_LONG).show();
                //Dissapearing Edit Texts
                if(gridLayout.getVisibility()==View.VISIBLE) {
                    gridLayout.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
                else{
                    relativeLayout.setVisibility(View.INVISIBLE);
                    gridLayout.setVisibility(View.VISIBLE);

                }

            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListTime = new ArrayList<>(
                        Arrays.asList("0", "0", "0", "0", "0", "0", "0")
                );

                for (int i = 0; i < 7; i++) {
                    //if default time is set
                    if (checkbox.isChecked()) {
                        if (arrayList.get(i) == 1) {

                            arrayListTime.set(i, defaultTime.getText().toString());

                        }

                    } else {
                        //if default time isn't set

                            if (arrayList.get(i) == 1) {
                                //For MONDAY
                                if (i == 0) {
                                    Log.d("MONday",mondayTime.getText().toString());
                                    String mondaay=mondayTime.getText().toString();
                                    arrayListTime.set(i, mondayTime.getText().toString());

                                }
                                //For TUESDAY
                                if (i == 1) {
                                    arrayListTime.set(i, tuesdayTime.getText().toString());

                                }


                                //For WEDNESDAY
                                if (i == 2) {
                                    arrayListTime.set(i, wednesdayTime.getText().toString());

                                }

                                //For ThursDAY
                                if (i == 3) {
                                    arrayListTime.set(i, thursdayTime.getText().toString());

                                }

                                //For FrIDAY
                                if (i == 4) {
                                    arrayListTime.set(i, fridayTime.getText().toString());

                                }

                                //For SATURDAY
                                if (i == 5) {
                                    arrayListTime.set(i, saturdayTime.getText().toString());

                                }

                                //For SUNDAY
                                if (i == 6) {
                                    arrayListTime.set(i, sundayTime.getText().toString());

                                }


                            }
                    }
                }

                Log.e("TIME",arrayListTime.toString());
               daysTime=arrayListTime;
                Intent intentSelectTime = new Intent(getApplication(),SelectPriorities.class);
                startActivity(intentSelectTime);
            }


        });



        }
}
