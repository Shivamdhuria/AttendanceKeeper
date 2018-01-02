package com.elixer.attendancekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SelectDay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);
        String name = getIntent().getStringExtra("name");
        Log.e("Class name",name);

    }
}
