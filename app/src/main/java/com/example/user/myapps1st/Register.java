package com.example.user.myapps1st;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText name, email, username, password, contact;
    Button register, cancel;
    RadioGroup gender;
    Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    Toolbar toolbar;
    private RequestQueue requestQueue;
    private StringRequest request;
    String namevalue, emailvalue, usernamevalue, passwordvalue, contactvalue, gendervalue;
    Boolean phone_state;
    String deviceId;
    MyHelper myHelper = new MyHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        mydb = new DatabaseHelper(Register.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Register Account");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone_state = Permission.Utility.checkPermission(this, Manifest.permission.READ_PHONE_STATE, 101, "Phone state permission is necessary");
        if(phone_state){
            deviceId = myHelper.getImei();
            Log.e("devies", deviceId);
        }

        register = (Button) findViewById(R.id.register);
        cancel = (Button) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        contact = (EditText) findViewById(R.id.number);
        gender = (RadioGroup) findViewById(R.id.gender);
        spinner = (com.rey.material.widget.Spinner) findViewById(R.id.spinner);

        List<String> lists = new ArrayList<String>();
        lists.add("Male");
        lists.add("Female");
        dataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lists);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        AddData();
    }


    public void AddData() {

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                addUser();
                // TODO Auto-generated method stub
                namevalue = name.getText().toString();
                emailvalue = email.getText().toString();
                usernamevalue = username.getText().toString();
                passwordvalue = password.getText().toString();
                contactvalue = contact.getText().toString();
                gendervalue = spinner.getSelectedItem().toString();
                Log.e("Gender", gendervalue);
                if (!namevalue.isEmpty() && !emailvalue.isEmpty() && !usernamevalue.isEmpty() && !passwordvalue.isEmpty() && !contactvalue.isEmpty() && !gendervalue.isEmpty()) {
                    if (isValidMail(emailvalue) && isValidNumber(contactvalue)) {

                        //Inserting on web via api
                        addUser();

                        //Inserting on Sqlite database..
//                        boolean insert = mydb.insertUserInfo(namevalue, emailvalue, gendervalue, usernamevalue, passwordvalue, contactvalue);
//                        if (insert == true) {
//                            Toast.makeText(Register.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
//                            finish();
//                        } else
//                            Toast.makeText(Register.this, "Unsuccessfull", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(Register.this, "Enter valid information", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Please fill all the forms.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidNumber(String contact) {
        return Patterns.PHONE.matcher(contact).matches();
    }

    private void addUser() {
        requestQueue = Volley.newRequestQueue(Register.this);
        request = new StringRequest(Request.Method.POST, Constants.addUser, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    String message = object.getString("message");
                    if (message.equalsIgnoreCase("success")) {
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isNetworkAvailable()) {
                    Toast.makeText(Register.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("name", namevalue);
                hashMap.put("username", usernamevalue);
                hashMap.put("email", emailvalue);
                hashMap.put("password", passwordvalue);
                hashMap.put("gender", gendervalue);
                hashMap.put("contact", contactvalue);
                hashMap.put("deviceId", deviceId);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101){
            deviceId = myHelper.getImei();
        }
    }
}
