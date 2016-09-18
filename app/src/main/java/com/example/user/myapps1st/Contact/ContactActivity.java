package com.example.user.myapps1st.Contact;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ContactInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

/**
 * Created by Suraj on 7/6/2016.
 */
public class ContactActivity extends AppCompatActivity{
    Button add, cancel;
    EditText address, city, country, phone, primaryEmail, secondaryEmail;
    DatabaseHelper mydb;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Edit Contact");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5722")));

        mydb = new DatabaseHelper(ContactActivity.this);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        country = (EditText) findViewById(R.id.country);
        phone = (EditText) findViewById(R.id.phone);
        primaryEmail = (EditText) findViewById(R.id.primaryEmail);
        secondaryEmail = (EditText) findViewById(R.id.secondaryEmail);
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);

        id = getIntent().getIntExtra("id", 0);

        if (id != 0) {
            ContactInfo info = mydb.getContactInfo(id + "");
            add.setText("Update");
            address.setText(info.address);
            city.setText(info.city);
            country.setText(info.country);
            phone.setText(info.phone);
            primaryEmail.setText(info.primary_email);
            secondaryEmail.setText(info.secondary_email);
        }

        AddData();
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }

    public void AddData() {

        add.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {
                                       // TODO Auto-generated method stub
                                       String addressvalue = address.getText().toString();
                                       String cityvalue = city.getText().toString();
                                       String countryvalue = country.getText().toString();
                                       String phonevalue = phone.getText().toString();
                                       String primaryEmailvalue = primaryEmail.getText().toString();
                                       String secondaryEmailvalue = secondaryEmail.getText().toString();
                                       if (addressvalue.isEmpty() || cityvalue.isEmpty() || countryvalue.isEmpty() || phonevalue.isEmpty() || primaryEmailvalue.isEmpty()) {
                                           Toast.makeText(ContactActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                                       } else {
                                           if (id == 0) {
                                               boolean insert = mydb.insertContactInfo(addressvalue, cityvalue, countryvalue, phonevalue, primaryEmailvalue, secondaryEmailvalue);
                                               if (insert == true) {
                                                  // Toast.makeText(ProfileActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                                                   finish();
//                                                   Home_fragment1 fragment = (Home_fragment1) getFragmentManager().findFragmentById(R.id.home);
//                                                   fragment.populateUserList();
                                               } else
                                                   Toast.makeText(ContactActivity.this, "Something went wrong...!", Toast.LENGTH_LONG).show();
                                           } else {
                                               boolean insert = mydb.editContactInfo(String.valueOf(id), addressvalue, cityvalue, countryvalue, phonevalue, primaryEmailvalue, secondaryEmailvalue);
                                               if (insert == true) {
                                                   //LinearLayout layout = (LinearLayout) findViewById(R.id.home);
                                                   //Toast.makeText(ProfileActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
//                                                   Snackbar snackbar = Snackbar.make(layout, "Succefull", Snackbar.LENGTH_LONG);
//                                                   View snackBarView = snackbar.getView();
//                                                   snackBarView.setBackgroundColor(getResources().getColor(R.color.green_complete));
//                                                   TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//                                                   textView.setTextColor(getResources().getColor(R.color.white));
//                                                   textView.setTextSize(15);
//                                                   snackbar.show();
                                                   finish();
                                               } else
                                                   Toast.makeText(ContactActivity.this, "Something went wrong...!", Toast.LENGTH_LONG).show();
                                           }

                                       }
                                   }
                               }

        );
    }

}
