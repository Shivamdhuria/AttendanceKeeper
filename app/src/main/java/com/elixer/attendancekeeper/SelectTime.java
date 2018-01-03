package com.elixer.attendancekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectTime extends AppCompatActivity {
    EditText mondayTime,tuesdayTime,wednesdayTime,thursdayTime,fridayTime,saturdayTime,sundayTime;
    Button button_submit;
    protected static ArrayList<Integer> arrayListTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
     //   SelectDay.arrayList;
        Log.e("Days",SelectDay.arrayList.toString());
        mondayTime=(EditText)findViewById(R.id.edittext_monday_time);
        tuesdayTime=(EditText)findViewById(R.id.edittext_tuesday_time);

        wednesdayTime=(EditText)findViewById(R.id.edittext_wednesday_time);

        thursdayTime=(EditText)findViewById(R.id.edittext_thursday_time);

        fridayTime=(EditText)findViewById(R.id.edittext_friday_time);

        saturdayTime=(EditText)findViewById(R.id.edittext_saturday_time);

       sundayTime=(EditText)findViewById(R.id.edittext_sunday_time);
        button_submit =(Button)findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListTime = new ArrayList<>(
                        Arrays.asList(0,0,0,0,0,0,0)
                );
            }
        });



    }
}
