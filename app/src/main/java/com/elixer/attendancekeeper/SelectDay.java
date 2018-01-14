package com.elixer.attendancekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;

import static com.elixer.attendancekeeper.NewClass.days;

public class SelectDay extends AppCompatActivity {
    protected static ArrayList<Integer> arrayList ;
    CheckBox monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);

        arrayList = new ArrayList<>(
                Arrays.asList(0,0,0,0,0,0,0)
        );

        monday = (CheckBox)findViewById(R.id.checkbox_monday);
       tuesday = (CheckBox)findViewById(R.id.checkbox_tuesday);
        wednesday = (CheckBox)findViewById(R.id.checkbox_wednesday);
        thursday = (CheckBox)findViewById(R.id.checkbox_thursday);
        friday = (CheckBox)findViewById(R.id.checkbox_friday);
        saturday = (CheckBox)findViewById(R.id.checkbox_saturday);
        sunday = (CheckBox)findViewById(R.id.checkbox_sunday);
        button_submit = (Button)findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrayList = new ArrayList<>(
                        Arrays.asList(0,0,0,0,0,0,0)
                );

                if(monday.isChecked()){
                    arrayList.set(0, 1);
                }
                if(tuesday.isChecked()){
                    arrayList.set(1, 1);
                }
                if(wednesday.isChecked()){
                    arrayList.set(2, 1);
                }
                if(thursday.isChecked()){
                    arrayList.set(3, 1);
                }
                if(friday.isChecked()){
                    arrayList.set(4, 1);
                }
                if(saturday.isChecked()){
                    arrayList.set(5, 1);
                }
                if(sunday.isChecked()){
                    arrayList.set(6, 1);
                }

                Intent intentSelectTime = new Intent(getApplicationContext(),SelectTime.class);
                startActivity(intentSelectTime);
                days = arrayList;
            }
        });



    }
}
