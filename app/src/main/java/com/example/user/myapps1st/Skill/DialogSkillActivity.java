package com.example.user.myapps1st.Skill;

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
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suraj on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogSkillActivity extends AppCompatActivity {
    View view;
    Button add, cancel;
    EditText skill, rate;
    TextInputLayout skillLayout, rateLayout;
    DatabaseHelper mydb;
    int id;
    MyHelper myHelper = new MyHelper(this);
    private String rateValue, skillValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_form);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }
        getSupportActionBar().setTitle("Add Skill");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        skill = (EditText) findViewById(R.id.skill);
        rate = (EditText) findViewById(R.id.rate);
        skillLayout = (TextInputLayout) findViewById(R.id.skillLayout);
        rateLayout = (TextInputLayout) findViewById(R.id.rateLayout);
        mydb = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id", 0);
        String skillV = getIntent().getStringExtra("skill");
        String rateV = getIntent().getStringExtra("rate");
        if (id != 0) {
            getSupportActionBar().setTitle("Edit Skill");
            add.setText("Update");
            skill.setText(skillV);
            rate.setText(rateV);
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
        rateValue = rate.getText().toString();
        skillValue = skill.getText().toString();
        if (rateValue.isEmpty() || skillValue.isEmpty()) {
            rateLayout.setError("Enter rating");
            skillLayout.setError("Enter skill");
        } else {
            if (id == 0) {
                addSkill();
            } else {
                editSkill();
            }
        }
    }

    private void addSkill() {
        RequestQueue requestQueue = Volley.newRequestQueue(DialogSkillActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.addSkill, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(DialogSkillActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DialogSkillActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(DialogSkillActivity.this)) {
                    Toast.makeText(DialogSkillActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DialogSkillActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(DialogSkillActivity.this);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("rate", rateValue);
                hashMap.put("skill", skillValue);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void editSkill() {
        RequestQueue requestQueue = Volley.newRequestQueue(DialogSkillActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.editSkill, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(DialogSkillActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DialogSkillActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(DialogSkillActivity.this)) {
                    Toast.makeText(DialogSkillActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DialogSkillActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("rate", rateValue);
                hashMap.put("skill", skillValue);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}

