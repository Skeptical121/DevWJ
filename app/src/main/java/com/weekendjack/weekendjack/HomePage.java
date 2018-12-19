package com.weekendjack.weekendjack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import helpers.LoadJSON;

public class HomePage extends Activity implements View.OnClickListener, JsonDisplayActivity {

    int numDayOptions = 8;

    Button search;

    //day buttons//
    Button mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton, sundayButton, allWeekButton;
    Button[] dayButtons = new Button[numDayOptions];
    int[] dayToggles;

    //testing!!!//
    String serviceId;
    ListView services;
    //end//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Log.d("TEST","Hello");

        search = (Button) findViewById(R.id.button_search_services);

        //--|| Buttons representing each day of the week. Handled in onClick() ||--//
        mondayButton = (Button) findViewById(R.id.mondayButton);
        dayButtons[0] = mondayButton;
        mondayButton.setOnClickListener(this);

        tuesdayButton = (Button) findViewById(R.id.tuesdayButton);
        dayButtons[1] = tuesdayButton;
        tuesdayButton.setOnClickListener(this);

        wednesdayButton = (Button) findViewById(R.id.wednesdayButton);
        dayButtons[2] = wednesdayButton;
        wednesdayButton.setOnClickListener(this);

        thursdayButton = (Button) findViewById(R.id.thursdayButton);
        dayButtons[3] = thursdayButton;
        thursdayButton.setOnClickListener(this);

        fridayButton = (Button) findViewById(R.id.fridayButton);
        dayButtons[4] = fridayButton;
        fridayButton.setOnClickListener(this);

        saturdayButton = (Button) findViewById(R.id.saturdayButton);
        dayButtons[5] = saturdayButton;
        saturdayButton.setOnClickListener(this);

        sundayButton = (Button) findViewById(R.id.sundayButton);
        dayButtons[6] = sundayButton;
        sundayButton.setOnClickListener(this);

        allWeekButton = (Button) findViewById(R.id.allWeekButton);
        dayButtons[7] = allWeekButton;
        allWeekButton.setOnClickListener(this);
        //------------------------------------------------------------------------//
        dayToggles = new int[]{1,1,1,1,1,1,1,1}; // 0 == off, 1 == on


        //TODO: fix... this?
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, SearchServices.class);
                startActivity(intent);
            }
        });


        //testing!!!!!!
        services = (ListView) findViewById(R.id.list_services);
        serviceId = getIntent().getStringExtra("service_id");

        LoadJSON.downloadJSON(this, "search_database.php", new String[]{"type_id", "date_pref"}, new String[]{serviceId, "06"});

        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ServiceListing entry = (ServiceListing) parent.getAdapter().getItem(position);
                //TODO: change this intent so it doesn't go back to the homepage......
                Intent intent = new Intent(HomePage.this, HomePage.class);
                int serviceId = entry.getServiceId();
                intent.putExtra("service_id", serviceId);
                startActivity(intent);
            }
        });
        //end//


    }


    @Override
    public void onClick(View v){

        int button_id = v.getId();

        if (button_id == R.id.mondayButton){
            dayButtonToggler(0);
        } else if (button_id == R.id.tuesdayButton){
            dayButtonToggler(1);
        } else if (button_id == R.id.wednesdayButton){
            dayButtonToggler(2);
        } else if (button_id == R.id.thursdayButton){
            dayButtonToggler(3);
        } else if (button_id == R.id.fridayButton){
            dayButtonToggler(4);
        } else if (button_id == R.id.saturdayButton){
            dayButtonToggler(5);
        } else if (button_id == R.id.sundayButton){
            dayButtonToggler(6);
        } else if (button_id == R.id.allWeekButton){
            switchAllDayButtons(dayToggles[7]);
        }



    }

    public void switchAllDayButtons(int onOrOff){
        for (int j = 0; j < numDayOptions; j++){
            dayToggles[j] = onOrOff;
            dayButtonToggler(j);
        }
    }

    public void dayButtonToggler(int num){
        if (dayToggles[num] == 0) {
            // button was off, turn it on
            dayButtons[num].setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else {
            // button was on, turn it off
            dayButtons[num].setBackgroundColor(Color.TRANSPARENT);
        }
        //toggle the toggle
        dayToggles[num] ^= 1;
    }



    //testing!!!!!//
    @Override
    public void handleJson(JSONArray array) throws JSONException {
        List<ServiceListing> serviceList = new ArrayList<ServiceListing>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            serviceList.add(new ServiceListing(obj.getInt("id"), "", obj.getString("title"), obj.getString("description"), ""));
        }
        ArrayAdapter<ServiceListing> arrayAdapter = new ServiceListingArrayAdapter(this, 0, serviceList);
        services.setAdapter(arrayAdapter);
    }

}
