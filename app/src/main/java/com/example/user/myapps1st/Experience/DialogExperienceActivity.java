package com.example.user.myapps1st.Experience;

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
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suraj on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogExperienceActivity extends AppCompatActivity {
    Button add, cancel;
    EditText title, company, dateFrom, dateTo, description;
    TextInputLayout titleLayout, companyLayout, datefromLayout, datetoLayout, descriptionLayout;
    DatabaseHelper mydb;
    int id;
    MyHelper myHelper = new MyHelper(this);
    private String titleValue, companyValue, datefromValue, datetoValue, descriptionValue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_form);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }

        getSupportActionBar().setTitle("Add Experience");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));

        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        title = (EditText) findViewById(R.id.title);
        company = (EditText) findViewById(R.id.company);
        dateFrom = (EditText) findViewById(R.id.dateFrom);
        dateTo = (EditText) findViewById(R.id.dateTo);
        description = (EditText) findViewById(R.id.description);

        titleLayout = (TextInputLayout) findViewById(R.id.titleLayout);
        companyLayout = (TextInputLayout) findViewById(R.id.companyLayout);
        descriptionLayout = (TextInputLayout) findViewById(R.id.descriptionLayout);
        datefromLayout = (TextInputLayout) findViewById(R.id.datefromLayout);
        datetoLayout = (TextInputLayout) findViewById(R.id.datetoLayout);
        mydb = new DatabaseHelper(this);


        id = getIntent().getIntExtra("id", 0);

        if (id != 0) {
            ExperienceInfo info = mydb.getExperienceInfo(id + "");
            getSupportActionBar().setTitle("Edit Experience");
            //text.setText("Edit Profile");
            add.setText("Update");
            title.setText(info.title);
            company.setText(info.company);
            description.setText(info.description);
            dateFrom.setText(info.dateFrom);
            dateTo.setText(info.dateTo);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkill();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addSkill() {
        titleValue = title.getText().toString();
        companyValue = company.getText().toString();
        datefromValue = dateFrom.getText().toString();
        datetoValue = dateTo.getText().toString();
        descriptionValue = description.getText().toString();


        if (titleValue.isEmpty() || companyValue.isEmpty() || datefromValue.isEmpty() || datetoValue.isEmpty() || descriptionValue.isEmpty()) {
            titleLayout.setError("Enter title");
            companyLayout.setError("Enter company name");
            datefromLayout.setError("Enter join date from");
            datetoLayout.setError("Enter join date to");
            descriptionLayout.setError("Enter description");
        } else {
            if (id == 0) {
                addExperience();
            } else {
                    Toast.makeText(DialogExperienceActivity.this, "Edit...!", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void addExperience() {
        RequestQueue requestQueue = Volley.newRequestQueue(DialogExperienceActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.addExperience, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(DialogExperienceActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DialogExperienceActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(DialogExperienceActivity.this)) {
                    Toast.makeText(DialogExperienceActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DialogExperienceActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(DialogExperienceActivity.this);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("title", titleValue);
                hashMap.put("company", companyValue);
                hashMap.put("datefrom", datefromValue);
                hashMap.put("dateto", datetoValue);
                hashMap.put("description", descriptionValue);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}

