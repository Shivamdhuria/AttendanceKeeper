package com.elixer.attendancekeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SelectPriorities extends AppCompatActivity {
    CheckBox checkReminder,checkAlarm;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_priorities);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        checkReminder = (CheckBox)findViewById(R.id.check_box_reminder);
        checkAlarm=(CheckBox)findViewById(R.id.check_box_alarm);
        buttonSubmit=(Button)findViewById(R.id.button_submit);


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

            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewClass.alarm=checkAlarm.isChecked();
                NewClass.reminder=checkReminder.isChecked();
                //Set up a Dialog Box here
                Intent intentCurrentAttendance = new Intent(getApplication(),CurrentAttendance.class);
                startActivity(intentCurrentAttendance);
                finish();
            }
        });

    }
}
