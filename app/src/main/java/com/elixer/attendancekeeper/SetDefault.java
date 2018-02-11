package com.elixer.attendancekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class SetDefault extends AppCompatActivity {
    Switch switchPresent;
    EditText editTextPercentage;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_default);

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
