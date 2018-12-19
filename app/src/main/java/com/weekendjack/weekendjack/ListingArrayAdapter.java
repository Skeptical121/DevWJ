package com.weekendjack.weekendjack;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * This is a test (copy of ServiceListingArrayAdpater)
 */

class ListingArrayAdapter extends ArrayAdapter<ServiceListing> {

    private Context context;
    private List<ServiceListing> serviceListingList;

    //constructor, call on creation
    public ListingArrayAdapter(Context context, int resource, List<ServiceListing> objects) {
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