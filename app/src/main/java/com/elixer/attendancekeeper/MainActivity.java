package com.elixer.attendancekeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //a List of type hero for holding list items
    List<Class> classList;

    //the listview
    ListView listView;
    //For Current Date in Text View
    TextView textViewCurrentDate,textViewEmpty;
    TextView textViewRemainingClasses;
    static int defaultTime,defaultPercentage;

    ListViewAdapter adapter;
    String today;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // TextView textView = (TextView)findViewById(R.id.textviewClassNumber);
        //Generating text View
        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        //initializing listview
        //initializing objects
        classList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);
        textViewCurrentDate = (TextView) findViewById(R.id.textviewDate);
        textViewRemainingClasses=(TextView)findViewById(R.id.textViewRemainingClasses);
        textViewEmpty=(TextView)findViewById(R.id.textViewEmpty);
        today = getDay();
        count =0;
       // textViewRemainingClasses.setText("lol");
        findAndSaveDefaults();

        displayRemainingClasses();




        //On click Date button
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(".............", "Day was clicked: " + dateClicked + " with events " + events);
                String day =convertToDay(dateClicked);
                Intent timeTableIntent = new Intent(getApplicationContext(),TimeTable.class);
                timeTableIntent.putExtra("day",day);
                timeTableIntent.putExtra("date",convertToDate(dateClicked));
                startActivity(timeTableIntent);
            }



            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d("..........", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Opening New Class Activity
                Intent intentNewClass = new Intent(getApplication(),NewClass.class);
                startActivity(intentNewClass);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void displayRemainingClasses() {
        SharedPreferences sharedPref =getApplicationContext().getSharedPreferences("attend", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("class", "");
        Log.e("json",json);
        //Try to fetch all values from shared preferene
        try{
            fetchAllPreference();
            if(count==1){
                textViewRemainingClasses.setText(String.valueOf(count) + " Class Today");
            }else {
                textViewRemainingClasses.setText(String.valueOf(count) + " Classes Today");
            }


        }catch (Exception ex){


        }
        //Setting Date



        //Setting Current Date
        textViewCurrentDate.setText(getDate());
    }

    private String convertToDay(Date dateClicked) {

        String dayOfTheWeek = (String) DateFormat.format("EEEE", dateClicked); // Thursd
     //   Log.e("Date......",dayOfTheWeek);
        return dayOfTheWeek;
    }

    private String convertToDate(Date dateClicked) {

        String date = (String) DateFormat.format("EEEE", dateClicked)+" , "+ (String) DateFormat.format("dd", dateClicked)
        +" "+(String) DateFormat.format("MMMM",  dateClicked);// Thursd
      //  Log.e("Date......",dayOfTheWeek);
        return date;
    }

    private void findAndSaveDefaults() {




            try {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                defaultPercentage= preferences.getInt("percentage",0);
                defaultTime=preferences.getInt("time",0);

            }catch (Exception ex){
                defaultPercentage = 75;
                defaultTime=5;




            }

    }

    private String getDate() {
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    private void BeginService() {
        Intent serviceRun=new Intent(this,Run.class);
        startService(serviceRun);
    }

    private void setAlarmForDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,Calendar.DATE+1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);

        Intent intentService = new Intent(this,Run.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0000, intentService, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
        //Toast.makeText(this,"Daily set alarm",Toast.LENGTH_LONG).show();
    //    Log.e("Alarm for service","Alarm Manager for Service at 0.05  " + calendar.get(Calendar.DAY_OF_WEEK));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //ONLY IF NOT SET
            Intent intentSetDefault = new Intent(getApplicationContext(),SetDefault.class);
            startActivity(intentSetDefault);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all_class) {
            // Handle the camera action

            Intent allClassIntent = new Intent(this,AllClass.class);
            startActivity(allClassIntent);
        } else if (id == R.id.faqs) {

            Intent faqsIntent = new Intent(this,Faqs.class);
            startActivity(faqsIntent);

        } else if (id == R.id.nav_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Attendance Keeper App");
                String sAux = "\nKeep tracl of your attendance\\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.elixer.attendancekeeper \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        }
            else if(id == R.id.feedback){


            String recepientEmail = "elixerapplications@gmail.com"; // either set to destination email or leave empty
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + recepientEmail));
            intent. putExtra(Intent.EXTRA_SUBJECT, "Feedback regarding Attendance Keeper");
            intent.putExtra(Intent.EXTRA_TEXT,"Hey There,");
            startActivity(intent);

            }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void fetchAllPreference(){
        SharedPreferences prefA =getApplicationContext().getSharedPreferences("attend",Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefA.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            //Converting from Json to Object
            /** Use the string printed to create a new object */
            //Creating Gson
            Gson gson = new Gson();
            Class classes = gson.fromJson(entry.getValue().toString(), Class.class);

            /** Print one variable to the console */
           // System.out.println("Username : " + classes.getName());
            //Searching if today is class for setting tetview
            if(searchClassTodayAndChecked(today,classes)){
               count=count+1;

            }



            classList.add(classes);


           //Toast.makeText(getApplicationContext(),(entry.getKey() + ": " + entry.getValue().toString()),Toast.LENGTH_LONG).show();
        }

        //creating the adapter
        adapter = new ListViewAdapter(this, R.layout.list_row, classList);
        //Set alarm for date change on create for tomorrow
        setAlarmForDate();
        //This class runs service
        BeginService();
        //attaching adapter to the listview
        listView.setAdapter(adapter);


        //Display Textview
        if(adapter.getCount()==0){
            textViewEmpty.setVisibility(View.VISIBLE);
        }else{
            textViewEmpty.setVisibility(View.GONE);
        }



    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String val = intent.getStringExtra("size");
            textViewRemainingClasses.setText(val);
            Log.e("Val............",val);

        }
    };
    private Boolean searchClassTodayAndChecked(String today,Class classes) {
        Boolean val = false;

        //Load the list into a hashSet
        List<String> listDays = new ArrayList<String>(classes.getDays());
        for (String string : listDays) {
            if (string.matches(today)&& classes.getStatus()) {
                // Log.e("BOOOLEAN","FOUND");
                val = true;



            }


            //Set<String> time = new HashSet<String>(classes.getDaysTime());
        }

        return val;
    }

    private String getDay() {

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        //  Log.e("dayyyy",dayOfTheWeek);
        return dayOfTheWeek;
    }








}
