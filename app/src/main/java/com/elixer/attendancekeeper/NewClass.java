package com.elixer.attendancekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class NewClass extends AppCompatActivity {
    static String name;
    static ArrayList<String> days;
    static ArrayList<String> daysTime;


    Button button_submit;

    //For activity Select Priorities
    static Boolean reminder;
    static Boolean alarm;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        editText = (EditText) findViewById(R.id.editText_name);
        button_submit = (Button) findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //See if empty
                if (editText.getText().toString().equals("")) {
                    Snackbar.make(view, "Class Name empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (findIfUnique(editText.getText().toString())) {
                    //It's Unique
                    //Pres submit with name in intent
                    Intent intentSelectDay = new Intent(getApplicationContext(), SelectDay.class);
                    name = editText.getText().toString();
                    startActivity(intentSelectDay);
                } else {

                    //Not Unique
                    Snackbar.make(view, "Class Name already exists", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }

            }
        });
    }

                public Boolean findIfUnique(String name) {
                    SharedPreferences prefA = getApplicationContext().getSharedPreferences("attend", Context.MODE_PRIVATE);
                    Boolean unique = true;
                    Map<String, ?> allEntries = prefA.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                        //Converting from Json to Object
                        /** Use the string printed to create a new object */
                        //Creating Gson
                        Gson gson = new Gson();
                        Class classes = gson.fromJson(entry.getValue().toString(), Class.class);

                        /** Print one variable to the console */
                        // System.out.println("Username : " + classes.getName());
                        if (name.equals(classes.getName())) {
                            unique = false;
                            break;
                        }


                        //Toast.makeText(getApplicationContext(),(entry.getKey() + ": " + entry.getValue().toString()),Toast.LENGTH_LONG).show();
                    }
                    return unique;
                }
            }

