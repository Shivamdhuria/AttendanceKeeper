package com.elixer.attendancekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class SetDefault extends AppCompatActivity {
    Switch switchPresent;
    EditText editTextPercentage;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_default);
        switchPresent=(Switch)findViewById(R.id.switchPresent);
        editTextPercentage=(EditText)findViewById(R.id.editTextPercentage);
        buttonSubmit= (Button)findViewById( R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextPercentage.getText().toString().equals("")){

                    Snackbar.make(view, "Enter Percentage", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else{

                    try {
                        DB snappydb = DBFactory.open(getApplicationContext(),"Attendance"); //create or open an existing database using the default name

                        snappydb.putInt("dafaultpercentage",Integer.parseInt(editTextPercentage.getText().toString()));
                        if(switchPresent.isChecked()) {
                            snappydb.put("dafaultchoice","ON");
                        }
                        else{

                            snappydb.put("dafaultchoice","OFF");
                        }
                        Log.e("Saved","Database saved");
                        Class newclass = new Class(NewClass.name,NewClass.days,NewClass.daysTime,NewClass.reminder,NewClass.alarm);

                        snappydb.close();

                    } catch (SnappydbException e) {
                    }

                }

                //Create Database of class


                //Go back to main activity
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intentMainActivity);
                finish();


            }
        });


    }
}
