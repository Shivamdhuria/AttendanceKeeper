package com.elixer.attendancekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TimeTable extends AppCompatActivity {

    //a List of type hero for holding list items
    List<Class> classList;

    //the listview
    ListView listViewTable;
    ListViewAdapterTimeTable adapterTime;
    String day;
    TextView textViewEmpty;
   // TextView textViewDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        Intent intent=getIntent();
        String date =intent.getStringExtra("date");
        day = intent.getStringExtra("day");
        //Log.e(">.......",day);
         TextView textViewDateDisplay =(TextView)findViewById(R.id.textViewDateDisplay);
        listViewTable = (ListView)findViewById(R.id.listViewTable);
        textViewEmpty=(TextView)findViewById(R.id.textViewEmpty);




        textViewDateDisplay.setText(date);
        classList = new ArrayList<>();
        try{
           fetchAllPreference();


        }catch (Exception ex){

            ex.printStackTrace();
        }


    }
    public void fetchAllPreference() {
        SharedPreferences prefA = getApplicationContext().getSharedPreferences("attend", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefA.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            //Converting from Json to Object
            /** Use the string printed to create a new object */
            //Creating Gson
            Gson gson = new Gson();
            Class classes = gson.fromJson(entry.getValue().toString(), Class.class);

            /** Print one variable to the console */
            // System.out.println("Username : " + classes.getName());
            //Load the list into a hashSet
            List<String> listDays = new ArrayList<String>(classes.getDays());
            for (String string : listDays) {
                if (string.matches(day)) {
                    // Log.e("BOOOLEAN","FOUND");


                    classList.add(classes);


                }


                //Toast.makeText(getApplicationContext(),(entry.getKey() + ": " + entry.getValue().toString()),Toast.LENGTH_LONG).show();
            }

            //creating the adapter
            adapterTime = new ListViewAdapterTimeTable(this, R.layout.list_row_timetable, classList);

            //attaching adapter to the listview
            listViewTable.setAdapter(adapterTime);
            //Show TextView if empth
            if(adapterTime.getCount()==0){
                textViewEmpty.setVisibility(View.VISIBLE);

            }else {
                textViewEmpty.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out_back);
        finish();
        super.onBackPressed();  // optional depending on your needs

    }




    private String getDate() {
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }


}