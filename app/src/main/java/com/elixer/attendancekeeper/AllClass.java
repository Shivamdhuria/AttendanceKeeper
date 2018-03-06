package com.elixer.attendancekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllClass extends AppCompatActivity {

    //a List of type hero for holding list items
    List<String> allClassList;

    //the listview
    ListView allclasslistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initializing listview
        //initializing objects
        allClassList = new ArrayList<>();
        allclasslistView = (ListView) findViewById(R.id.allListview);

        //Fetching Data from Listview
        fetchAllClasses();


        // ListView Item Click Listener
        allclasslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemName    = (String) allclasslistView.getItemAtPosition(position);
                Intent intentClassDescriptionAndSettings = new Intent(getApplicationContext(),ClassDescriptionAndSettings.class);
                intentClassDescriptionAndSettings.putExtra("classname",itemName);
                startActivity(intentClassDescriptionAndSettings);



            }

        });
    }







    public void fetchAllClasses(){
        SharedPreferences prefA =getApplicationContext().getSharedPreferences("attend", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefA.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            //Converting from Json to Object
            /** Use the string printed to create a new object */
            //Creating Gson
            Gson gson = new Gson();
            Class classes = gson.fromJson(entry.getValue().toString(), Class.class);

            /** Print one variable to the console */

            String className = classes.getName();

            allClassList.add(className);



        }


        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, allClassList);

        //attaching adapter to the listview
        allclasslistView.setAdapter(arrayAdapter);
    }
}
