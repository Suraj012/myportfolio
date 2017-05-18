package com.example.user.myapps1st.Contact;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suraj on 7/6/2016.
 */
public class ContactActivity extends AppCompatActivity {
    Button add, cancel;
    EditText address, city, country, phone, primaryEmail, secondaryEmail;
    DatabaseHelper mydb;
    double latitude, longitude;
    String addressvalue, cityvalue, countryvalue, phonevalue, primaryEmailvalue, secondaryEmailvalue, addres;
    private RequestQueue requestQueue;
    private StringRequest request;
    int id;
    MyHelper myHelper = new MyHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#e64a19"));
        }

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
        Log.e("IDD", String.valueOf(id));

        if (id != 0) {
            addressvalue = getIntent().getStringExtra("address");
            cityvalue = getIntent().getStringExtra("city");
            countryvalue = getIntent().getStringExtra("country");
            latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
            longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
            phonevalue = getIntent().getStringExtra("phone");
            primaryEmailvalue = getIntent().getStringExtra("primaryEmail");
            secondaryEmailvalue = getIntent().getStringExtra("secondaryEmail");
            add.setText("Update");
            address.setText(addressvalue);
            city.setText(cityvalue);
            country.setText(countryvalue);
            phone.setText(phonevalue);
            primaryEmail.setText(primaryEmailvalue);
            secondaryEmail.setText(secondaryEmailvalue);
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
                                       addressvalue = address.getText().toString();
                                       cityvalue = city.getText().toString();
                                       countryvalue = country.getText().toString();
                                       phonevalue = phone.getText().toString();
                                       primaryEmailvalue = primaryEmail.getText().toString();
                                       secondaryEmailvalue = secondaryEmail.getText().toString();
                                       addres = addressvalue + "," + cityvalue + "," + countryvalue;
                                       if (addres != null && !addres.isEmpty()) {
                                           try {
                                               List<Address> addressList = new Geocoder(ContactActivity.this).getFromLocationName(addres, 1);
                                               if (addressList != null && addressList.size() > 0) {
                                                   latitude = addressList.get(0).getLatitude();
                                                   longitude = addressList.get(0).getLongitude();
                                               }
                                           } catch (Exception e) {
                                               e.printStackTrace();
                                           }
                                       }
                                       Log.e("Latitude", String.valueOf(latitude));
                                       Log.e("Longitude", String.valueOf(longitude));

                                       if (addressvalue.isEmpty() || cityvalue.isEmpty() || countryvalue.isEmpty() || phonevalue.isEmpty() || primaryEmailvalue.isEmpty()) {
                                           Toast.makeText(ContactActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                                       } else {
                                           addContact();
                                       }
                                   }
                               }
        );
    }

    private void addContact() {
        requestQueue = Volley.newRequestQueue(ContactActivity.this);
        request = new StringRequest(Request.Method.POST, Constants.addContact, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(ContactActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ContactActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(ContactActivity.this)) {
                    Toast.makeText(ContactActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContactActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(ContactActivity.this);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("address", addressvalue);
                hashMap.put("city", cityvalue);
                hashMap.put("country", countryvalue);
                hashMap.put("latitude", String.valueOf(latitude));
                hashMap.put("longitude", String.valueOf(longitude));
                hashMap.put("phone", phonevalue);
                hashMap.put("primaryEmail", primaryEmailvalue);
                hashMap.put("secondaryEmail", secondaryEmailvalue);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}