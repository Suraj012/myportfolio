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

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_form);

        getSupportActionBar().setTitle("Add Category");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));

        //getActivity().getActionBar().setTitle("Add Portfolio Category");
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        category = (EditText) findViewById(R.id.category);
        categoryLayout = (TextInputLayout) findViewById(R.id.categorylayout);
        mydb = new DatabaseHelper(this);
        id = getIntent().getIntExtra("position", 0);
        Log.e("Category", String.valueOf(id));

        if (id != 0) {
            CategoryInfo info = mydb.getCategoryInfo(id + "");
            getSupportActionBar().setTitle("Edit Category");
            add.setText("Update");
            category.setText(info.category);
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
        String categoryValue = category.getText().toString();
        if (categoryValue.isEmpty()) {
            categoryLayout.setError("Enter category");
        } else {
            if (id == 0) {
                boolean insert = mydb.insertCategoryInfo(categoryValue);
                if (insert == true) {
                    finish();
                } else
                    Toast.makeText(this, "Something went wrong...!Plzz try again later", Toast.LENGTH_LONG).show();
            } else {
                boolean insert = mydb.editCategoryInfo(String.valueOf(id), categoryValue);
                if (insert == true) {
                    finish();
                } else
                    Toast.makeText(this, "Something went wrong...!Plzz try again later", Toast.LENGTH_LONG).show();
            }
        }
    }
}

