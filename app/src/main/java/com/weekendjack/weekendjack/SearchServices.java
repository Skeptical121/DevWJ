package com.weekendjack.weekendjack;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
import java.util.HashMap;

import helpers.LoadJSON;

public class SearchServices extends AppCompatActivity implements JsonDisplayActivity {

    Spinner spinner;
    Button search;

    HashMap<String,String> serviceTypeIdList = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_services);

        spinner = (Spinner) findViewById(R.id.spinner);
        LoadJSON.downloadJSON(this, "load_service_types.php", new String[0], new String[0]); // <-- This gets the relevant categories from the database

        search = (Button) findViewById(R.id.button_search_services);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SearchServices.this, DisplayServices.class);
                intent.putExtra("service_id", serviceTypeIdList.get(spinner.getSelectedItem().toString()));
                startActivity(intent);
            }
        });
    }

    public void handleJson(JSONArray array) throws JSONException {
        String[] service_names = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            service_names[i] = obj.getString("service_name");
            serviceTypeIdList.put(service_names[i], obj.getString("id"));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, service_names);
        spinner.setAdapter(arrayAdapter);
    }
}
