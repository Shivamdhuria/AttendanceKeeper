package com.elixer.attendancekeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;

public class CurrentAttendance extends AppCompatActivity {
    FloatingActionButton buttonAddTotal,buttonMinusTotal,buttonAddCurrent,buttonMinusCurrent;
    EditText editTextTotal,editTextCurrent;
    Button buttonSubmit;
    CheckBox checkReminder,checkAlarm;

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
        checkReminder = (CheckBox)findViewById(R.id.check_box_reminder);
        checkAlarm=(CheckBox)findViewById(R.id.check_box_alarm);


        checkAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAlarm.isChecked() && checkReminder.isChecked()) {
                    checkReminder.setChecked(false);
                }

            }
        });
        checkReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAlarm.isChecked() && checkReminder.isChecked()) {
                    checkAlarm.setChecked(false);
                }
                if(checkReminder.isChecked()){
                    buildDialogBox();
                }

            }
        });

        buttonAddTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTotal.setText(String.valueOf(Integer.parseInt(editTextTotal.getText().toString()) + 1));


            }
        });
        buttonMinusTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(editTextTotal.getText().toString()) - 1<0){
                    //Nothing
                }else {
                    editTextTotal.setText(String.valueOf(Integer.parseInt(editTextTotal.getText().toString()) - 1));
                }

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
                if(Integer.parseInt(editTextCurrent.getText().toString()) - 1<0){
                    //Nothing
                }else {
                    editTextCurrent.setText(String.valueOf(Integer.parseInt(editTextCurrent.getText().toString()) - 1));
                }

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

                        NewClass.alarm=checkAlarm.isChecked();
                        NewClass.reminder=checkReminder.isChecked();
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
                    Intent intentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                    //Clearing Stack
                    intentMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentMainActivity);






                }

            }
        });



    }

    private void buildDialogBox() {

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setNeutralButton("Proceed", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
                buttonSubmit.performClick();

            }
        });

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Make sure Your Battery Manager isn't blocking the app from sending you notifications.Go to FAQ's for more information")
                .setTitle("Notice");

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
