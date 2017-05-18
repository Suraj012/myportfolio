package com.example.user.myapps1st.Education;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import com.example.user.myapps1st.Model.EducationInfo;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suraj on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogEducationActivity extends AppCompatActivity {
    Button add, cancel;
    EditText degree, college, university, marks, startDate, endDate;
    TextInputLayout degreeLayout, collegeLayout, universityLayout, marksLayout, startLayout, endLayout;
    DatabaseHelper mydb;
    int id;
    MyHelper myHelper = new MyHelper(DialogEducationActivity.this);
    private String degreeValue, collegeValue, universityValue, marksValue, startValue, endValue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_form);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }

        getSupportActionBar().setTitle("Add Education");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));

        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        degree = (EditText) findViewById(R.id.degree);
        college = (EditText) findViewById(R.id.college);
        university = (EditText) findViewById(R.id.university);
        marks = (EditText) findViewById(R.id.marks);
        startDate = (EditText) findViewById(R.id.start);
        endDate = (EditText) findViewById(R.id.end);

        degreeLayout = (TextInputLayout) findViewById(R.id.degreeLayout);
        collegeLayout = (TextInputLayout) findViewById(R.id.collegeLayout);
        universityLayout = (TextInputLayout) findViewById(R.id.universityLayout);
        marksLayout = (TextInputLayout) findViewById(R.id.marksLayout);
        startLayout = (TextInputLayout) findViewById(R.id.startLayout);
        endLayout = (TextInputLayout) findViewById(R.id.endLayout);

        mydb = new DatabaseHelper(this);


        id = getIntent().getIntExtra("id", 0);
        final String collegeV = getIntent().getStringExtra("college");
        final String universityV = getIntent().getStringExtra("university");
        final String degreeV = getIntent().getStringExtra("degree");
        final String marksV = getIntent().getStringExtra("marks");
        final String startDateV = getIntent().getStringExtra("startDate");
        final String endDateV = getIntent().getStringExtra("endDate");

        if (id != 0) {
            getSupportActionBar().setTitle("Edit Education");
            EducationInfo info = mydb.getEducationInfo(id + "");
            //text.setText("Edit Profile");
            add.setText("Update");
            degree.setText(degreeV);
            college.setText(collegeV);
            university.setText(universityV);
            marks.setText(marksV);
            startDate.setText(startDateV);
            endDate.setText(endDateV);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addData() {
        degreeValue = degree.getText().toString();
        collegeValue = college.getText().toString();
        universityValue = university.getText().toString();
        marksValue = marks.getText().toString();
        startValue = startDate.getText().toString();
        endValue = endDate.getText().toString();


        if (degreeValue.isEmpty() || collegeValue.isEmpty() || universityValue.isEmpty() || marksValue.isEmpty() || startValue.isEmpty() || endValue.isEmpty()) {
            degreeLayout.setError("Enter degree");
            collegeLayout.setError("Enter college");
            universityLayout.setError("Enter university");
            marksLayout.setError("Enter marks");
            startLayout.setError("Enter start date");
            endLayout.setError("Enter end date");
        } else {
            if (id == 0) {
                addEducation();
            } else {
                editEducation();
            }
        }
    }
    private void addEducation() {
        RequestQueue requestQueue = Volley.newRequestQueue(DialogEducationActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.addEducation, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(DialogEducationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DialogEducationActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(DialogEducationActivity.this)) {
                    Toast.makeText(DialogEducationActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DialogEducationActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(DialogEducationActivity.this);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("degree", degreeValue);
                hashMap.put("college", collegeValue);
                hashMap.put("university", universityValue);
                hashMap.put("marks", marksValue);
                hashMap.put("start_date", startValue);
                hashMap.put("end_date", endValue);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
    private void editEducation() {
        RequestQueue requestQueue = Volley.newRequestQueue(DialogEducationActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.editEducation, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(DialogEducationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DialogEducationActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(DialogEducationActivity.this)) {
                    Toast.makeText(DialogEducationActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DialogEducationActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(DialogEducationActivity.this);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("degree", degreeValue);
                hashMap.put("college", collegeValue);
                hashMap.put("university", universityValue);
                hashMap.put("marks", marksValue);
                hashMap.put("start_date", startValue);
                hashMap.put("end_date", endValue);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}

