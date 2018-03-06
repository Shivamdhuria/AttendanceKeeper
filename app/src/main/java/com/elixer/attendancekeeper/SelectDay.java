package com.elixer.attendancekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.elixer.attendancekeeper.NewClass.days;

public class SelectDay extends AppCompatActivity {
    protected static ArrayList<String> arrayList ;
    CheckBox monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);

        arrayList = new ArrayList<>(
                Arrays.asList("null","null","null","null","null","null","null")
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
                        Arrays.asList("null","null","null","null","null","null","null")
                );

                if(monday.isChecked()){
                    arrayList.set(0, "Monday");
                }
                if(tuesday.isChecked()){
                    arrayList.set(1, "Tuesday");
                }
                if(wednesday.isChecked()){
                    arrayList.set(2, "Wednesday");
                }
                if(thursday.isChecked()){
                    arrayList.set(3, "Thursday");
                }
                if(friday.isChecked()){
                    arrayList.set(4, "Friday");
                }
                if(saturday.isChecked()){
                    arrayList.set(5,"Saturday");
                }
                if(sunday.isChecked()){
                    arrayList.set(6, "Sunday");
                }

                //Compare to see if null
                if(compareList(arrayList)){
                    Snackbar.make(view, "Select atleast one working day", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else {
                    Intent intentSelectTime = new Intent(getApplicationContext(), SelectTime.class);
                    startActivity(intentSelectTime);
                    days = arrayList;
                }
            }
        });



    }
    public static boolean compareList(List ls1){
         List ls2= new ArrayList<>(
                 Arrays.asList("null","null","null","null","null","null","null"));
        return ls1.toString().contentEquals(ls2.toString())?true:false;
    }
}
