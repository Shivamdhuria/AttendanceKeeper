package com.elixer.attendancekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class CurrentAttendance extends AppCompatActivity {
    FloatingActionButton buttonAddTotal,buttonMinusTotal,buttonAddCurrent,buttonMinusCurrent;
    EditText editTextTotal,editTextCurrent;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_attendance);


        editTextCurrent=(EditText)findViewById(R.id.editTextCurrent);
        editTextTotal=(EditText)findViewById(R.id.editTextTotal);
        buttonSubmit=(Button)findViewById(R.id.buttonSubmit);
        buttonAddTotal = (FloatingActionButton) findViewById(R.id.buttonAddTotal);
        buttonMinusTotal = (FloatingActionButton) findViewById(R.id.buttonMinusTotal);
        buttonAddCurrent = (FloatingActionButton) findViewById(R.id.buttonAddCurrent);
        buttonMinusCurrent = (FloatingActionButton) findViewById(R.id.buttonMinusCurrent);

        buttonAddTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTotal.setText(String.valueOf(Integer.parseInt(editTextTotal.getText().toString()) + 1));


            }
        });
        buttonMinusTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTotal.setText(String.valueOf(Integer.parseInt(editTextTotal.getText().toString()) - 1));


            }
        });

        buttonAddCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextCurrent.setText(String.valueOf(Integer.parseInt(editTextCurrent.getText().toString()) + 1));


            }
        });
        buttonMinusCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextCurrent.setText(String.valueOf(Integer.parseInt(editTextCurrent.getText().toString()) - 1));


            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(editTextCurrent.getText().toString())>Integer.parseInt(editTextTotal.getText().toString())){


                    Snackbar.make(view, "Present Attendance can't be greater than total attendance", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else{


                        int current = Integer.parseInt(editTextCurrent.getText().toString());
                        int total = Integer.parseInt(editTextTotal.getText().toString());
                       // DB snappydb = DBFactory.open(getApplicationContext(),"Attendance"); //create or open an existing database using the default name


                        //Adding new class to database
                        Class newclass = new Class(NewClass.name,NewClass.days,NewClass.daysTime,NewClass.reminder,NewClass.alarm,current,total,true,0);

                        //Creating Gson
                        Gson gson = new Gson();
                        String newClass = gson.toJson(newclass);
                         Log.e("DaysTime......... ",NewClass.daysTime.toString());
                        //Saving objects in String gson
                        SharedPreferences sharedPref =getApplicationContext().getSharedPreferences("attend",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(NewClass.name,newClass);
                        editor.commit();

                    //ONLY IF NOT SET
                    Intent intentSetDefault = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intentSetDefault);
                    finish();








                }

            }
        });

    }
}
