package com.elixer.attendancekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
     //   SelectDay.arrayList;
        relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        gridLayout = (GridLayout)findViewById(R.id.daysLayout);
        i=0;
        //initialize checkbox
        checkbox = (CheckBox)findViewById(R.id.check_box_default);

        daysTime = new ArrayList<>(
                Arrays.asList("null","null","null","null","null","null","null"));
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

            if (arrayList.get(i).equals("null")) {
                //For MONDAY
                if (i == 0) {
                    textViewMonday.setVisibility(View.GONE);
                    mondayTime.setVisibility(View.GONE);

                }

                //For TUESDAY
                if (i == 1) {
                    textViewTuesday.setVisibility(View.GONE);
                    tuesdayTime.setVisibility(View.GONE);


                }


                //For WEDNESDAY
                if (i == 2) {
                    textViewWednesday.setVisibility(View.GONE);
                    wednesdayTime.setVisibility(View.GONE);


                }

                //For ThursDAY
                if (i == 3) {
                    textViewThursday.setVisibility(View.GONE);
                    thursdayTime.setVisibility(View.GONE);


                }

                //For FrIDAY
                if (i == 4) {
                    textViewFriday.setVisibility(View.GONE);
                    fridayTime.setVisibility(View.GONE);


                }

                //For SATURDAY
                if (i == 5) {
                    textViewSaturday.setVisibility(View.GONE);
                    saturdayTime.setVisibility(View.GONE);


                }

                //For SUNDAY
                if (i == 6) {
                    textViewSunday.setVisibility(View.GONE);
                    sundayTime.setVisibility(View.GONE);


                }
            }
        }


        checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"Douh!!",Toast.LENGTH_LONG).show();
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

        defaultTime.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    button_submit.performClick();
                }
                return false;
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            //Value of no error
            Boolean noError = false;

            @Override
            public void onClick(View view) {
                int count = 0;

                for (int i = 0; i < 7; i++) {
                    //if default time is set
                    if (checkbox.isChecked()) {
                        if (!arrayList.get(i).equals("null")) {

                            daysTime.set(i, defaultTime.getText().toString());

                        }

                    } else {
                        //if default time isn't set

                        if (!arrayList.get(i).equals("null")) {
                            //For MONDAY
                            if (i == 0) {


                                daysTime.set(i, mondayTime.getText().toString());
                                if(searchIfEmpty(mondayTime.getText().toString())){
                                    count=count+1;
                                }

                            }
                            //For TUESDAY
                            if (i == 1) {
                                daysTime.set(i, tuesdayTime.getText().toString());
                                if(searchIfEmpty(tuesdayTime.getText().toString())){
                                    count=count+1;
                                }

                            }


                            //For WEDNESDAY
                            if (i == 2) {
                                daysTime.set(i, wednesdayTime.getText().toString());
                                if(searchIfEmpty(wednesdayTime.getText().toString())){
                                    count=count+1;
                                }

                            }

                            //For ThursDAY
                            if (i == 3) {
                                daysTime.set(i, thursdayTime.getText().toString());
                                if(searchIfEmpty(thursdayTime.getText().toString())){
                                    count=count+1;
                                }

                            }

                            //For FrIDAY
                            if (i == 4) {
                                daysTime.set(i, fridayTime.getText().toString());
                                if(searchIfEmpty(fridayTime.getText().toString())){
                                    count=count+1;
                                }

                            }

                            //For SATURDAY
                            if (i == 5) {
                                daysTime.set(i, saturdayTime.getText().toString());
                                if(searchIfEmpty(saturdayTime.getText().toString())){
                                    count=count+1;
                                }

                            }

                            //For SUNDAY
                            if (i == 6) {
                                daysTime.set(i, sundayTime.getText().toString());
                                if(searchIfEmpty(sundayTime.getText().toString())){
                                    count=count+1;
                                }

                            }


                        }
                    }
                }

                if (checkbox.isChecked() && defaultTime.getText().toString().equals("")) {

                    count=count+1;


                }


                if(count>0) {
                    Snackbar.make(view, "Time field empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }else{


                    Intent intentSelectTime = new Intent(getApplication(), CurrentAttendance.class);
                    startActivity(intentSelectTime);


                }
                }

            private Boolean searchIfEmpty(String s) {

                return s.equals("");

            }


        });



        }
}
