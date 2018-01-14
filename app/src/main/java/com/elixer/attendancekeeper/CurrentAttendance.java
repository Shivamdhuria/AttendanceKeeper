package com.elixer.attendancekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class CurrentAttendance extends AppCompatActivity {
    Button buttonSubmitdone;
    EditText editTextTotal,editTextCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_attendance);

        buttonSubmitdone=(Button)findViewById(R.id.buttonSubmitdone);
        editTextCurrent=(EditText)findViewById(R.id.editTextCurrent);
        editTextTotal=(EditText)findViewById(R.id.editTextTotal);


        buttonSubmitdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(editTextCurrent.getText().toString())>Integer.parseInt(editTextTotal.getText().toString())){


                    Snackbar.make(view, "Present Attendance can't be greater than total attendance", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else{

                    try {
                        DB snappydb = DBFactory.open(getApplicationContext(),"Attendance"); //create or open an existing database using the default name


                        //Adding new class to database
                        Class newclass = new Class(NewClass.name,NewClass.days,NewClass.daysTime,NewClass.reminder,NewClass.alarm);
                        Log.e("new class",newclass.days.toString());
                        snappydb.put(NewClass.name,newclass);
                        snappydb.close();

                    } catch (SnappydbException e) {
                    }

                    //ONLY IF NOT SET
                    Intent intentSetDefault = new Intent(getApplicationContext(),SetDefault.class);
                    startActivity(intentSetDefault);

                }

            }
        });

    }
}