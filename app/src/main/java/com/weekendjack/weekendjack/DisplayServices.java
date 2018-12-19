package com.weekendjack.weekendjack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import helpers.LoadJSON;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class DisplayServices extends AppCompatActivity implements JsonDisplayActivity {

    String serviceId;
    ListView services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_services);

        services = (ListView) findViewById(R.id.list_services);
        serviceId = getIntent().getStringExtra("service_id");

        LoadJSON.downloadJSON(this, "search_database.php", new String[]{"type_id", "date_pref"}, new String[]{serviceId, "06"});

        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ServiceListing entry = (ServiceListing) parent.getAdapter().getItem(position);
                Intent intent = new Intent(DisplayServices.this, HomePage.class);
                int serviceId = entry.getServiceId();
                intent.putExtra("service_id", serviceId);
                startActivity(intent);
            }
        });
    }

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


class ServiceListingArrayAdapter extends ArrayAdapter<ServiceListing>{

    private Context context;
    private List<ServiceListing> serviceListingList;

    //constructor, call on creation
    public ServiceListingArrayAdapter(Context context, int resource, List<ServiceListing> objects) {
        super(context, resource, objects);

        this.context = context;
        this.serviceListingList = objects;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        ServiceListing serviceListing = serviceListingList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.service_listing_layout, null);

        TextView title = (TextView) view.findViewById(R.id.service_title);
        TextView description = (TextView) view.findViewById(R.id.service_description);

        title.setText(serviceListing.getServiceTitle());
        // Trim description if too long:
        int descriptionLength = serviceListing.getServiceDescription().length();
        if (descriptionLength >= 100) {
            String descriptionTrim = serviceListing.getServiceDescription().substring(0, 100) + "...";
            description.setText(descriptionTrim);
        } else {
            description.setText(serviceListing.getServiceDescription());
        }

        return view;
    }
}