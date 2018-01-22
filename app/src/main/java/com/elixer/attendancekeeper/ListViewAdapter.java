package com.elixer.attendancekeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 1/22/2018.
 */

public class ListViewAdapter extends ArrayAdapter<Class> {
    //the list values in the List of type hero
    List<Class> classList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    TextView textActivity2;


    //constructor initializing the values
    public ListViewAdapter(Context context, int resource, List<Class> classList) {
        super(context, resource, classList);
        this.context = context;
        this.resource = resource;
        this.classList = classList;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater



        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
       // ImageView imageView = view.findViewById(R.id.imageView);
        TextView textViewName = view.findViewById(R.id.className);
        TextView textViewCurrent = view.findViewById(R.id.attendance);
        TextView textViewTotal  = view.findViewById(R.id.attendancetotal);
      //  Button buttonDelete = view.findViewById(R.id.buttonDelete);




      //  final TextView textPercentage = (TextView) view.findViewById(R.id.textViewPercentage);
      //  final TextView textPercentage = (TextView) findViewById(R.id.textPercentage);



        //getting the hero of the specified position
       Class classes = classList.get(position);

        //adding values to the list item
      //  imageView.setImageDrawable(context.getResources().getDrawable(hero.getImage()));
        textViewName.setText(classes.getName());
        textViewCurrent.setText(Integer.toString(classes.getCurrent()));
        textViewTotal.setText("/"+Integer.toString(classes.getTotal()));




        return view;
    }




}