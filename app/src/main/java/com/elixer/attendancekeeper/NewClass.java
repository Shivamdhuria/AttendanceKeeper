package com.elixer.attendancekeeper;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewClass extends AppCompatActivity {
    EditText editText;
    Button button_submit;
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
                    }else {

                        //See if its unique


                        //Pres submit with name in intent
                        Intent intentSelectDay = new Intent(getApplicationContext(), SelectDay.class);
                        intentSelectDay.putExtra("name", editText.getText().toString());
                        startActivity(intentSelectDay);
                    }
                }
            });

    }
}
