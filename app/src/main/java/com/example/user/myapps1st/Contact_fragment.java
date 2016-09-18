package com.example.user.myapps1st;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.user.myapps1st.Contact.ContactActivity;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ContactInfo;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_fragment extends Fragment {
    TextView location, contact, email, address, city, country, phone, primaryEmail, secondaryEmail, error;
    Button add;
    DatabaseHelper mydb;
    LinearLayout map,info;


    public Contact_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        location = (TextView) view.findViewById(R.id.location);
        contact = (TextView) view.findViewById(R.id.contact);
        email = (TextView) view.findViewById(R.id.email);
        address = (TextView) view.findViewById(R.id.address);
        city = (TextView) view.findViewById(R.id.city);
        country = (TextView) view.findViewById(R.id.country);
        phone = (TextView) view.findViewById(R.id.phone);
        primaryEmail = (TextView) view.findViewById(R.id.primaryEmail);
        secondaryEmail = (TextView) view.findViewById(R.id.secondaryEmail);
        error = (TextView) view.findViewById(R.id.error);
        add = (Button) view.findViewById(R.id.add);
        map = (LinearLayout) view.findViewById(R.id.map);
        info = (LinearLayout) view.findViewById(R.id.info);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactActivity.class);
                startActivity(intent);
            }
        });

        mydb = new DatabaseHelper(getActivity());
        int count = mydb.selectContactInfo().size();
        Log.e("count", String.valueOf(count));
        if(count > 0) {
            populateContactInfo();
            map.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
        }else{
            map.setVisibility(View.GONE);
            info.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
        }

        return view;

    }
    public void populateContactInfo(){
        ArrayList<ContactInfo> list = mydb.selectContactInfo();
        for (int i = 0; i < list.size(); i++) {

            final ContactInfo info = list.get(i);
            address.setText(info.address + ", ");
            city.setText(info.city + ", ");
            country.setText(info.country);
            phone.setText(info.phone);
            primaryEmail.setText(info.primary_email);
            secondaryEmail.setText(info.secondary_email);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        populateContactInfo();
    }
}
