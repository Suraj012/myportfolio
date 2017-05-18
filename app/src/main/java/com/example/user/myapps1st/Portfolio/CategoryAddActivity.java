package com.example.user.myapps1st.Portfolio;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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
public class CategoryAddActivity extends AppCompatActivity {
    LayoutInflater inflater;
    View view;
    Button add, cancel;
    EditText category;
    TextInputLayout categoryLayout;
    DatabaseHelper mydb;
    int id;
    String categoryValue;
    MyHelper myHelper = new MyHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_form);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1976d2"));
        }

        getSupportActionBar().setTitle("Add Category");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));

        //getActivity().getActionBar().setTitle("Add Portfolio Category");
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        category = (EditText) findViewById(R.id.category);
        categoryLayout = (TextInputLayout) findViewById(R.id.categorylayout);
        mydb = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id", 0);
        String categoryV = getIntent().getStringExtra("category");
        Log.e("Category", String.valueOf(id));

        if (id != 0) {
            getSupportActionBar().setTitle("Edit Category");
            add.setText("Update");
            category.setText(categoryV);
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
        categoryValue = category.getText().toString();
        if (categoryValue.isEmpty()) {
            categoryLayout.setError("Enter category");
        } else {
            if (id == 0) {
                addCategory();
            } else {
                editCategory();
            }
        }
    }

//    public void addSkill() {
//        String categoryValue = category.getText().toString();
//        if (categoryValue.isEmpty()) {
//            categoryLayout.setError("Enter category");
//        } else {
//            if (id == 0) {
//                boolean insert = mydb.insertCategoryInfo(categoryValue);
//                if (insert == true) {
//                    finish();
//                } else
//                    Toast.makeText(this, "Something went wrong...!Plzz try again later", Toast.LENGTH_LONG).show();
//            } else {
//                boolean insert = mydb.editCategoryInfo(String.valueOf(id), categoryValue);
//                if (insert == true) {
//                    finish();
//                } else
//                    Toast.makeText(this, "Something went wrong...!Plzz try again later", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void addCategory() {
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryAddActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.addCategory, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(CategoryAddActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CategoryAddActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(CategoryAddActivity.this)) {
                    Toast.makeText(CategoryAddActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryAddActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(CategoryAddActivity.this);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("category", categoryValue);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void editCategory() {
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryAddActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.editCategroy, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(CategoryAddActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CategoryAddActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(CategoryAddActivity.this)) {
                    Toast.makeText(CategoryAddActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryAddActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("category", categoryValue);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}

