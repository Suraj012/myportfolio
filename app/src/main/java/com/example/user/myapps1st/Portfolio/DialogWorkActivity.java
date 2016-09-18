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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.Model.WorkInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import java.util.List;

/**
 * Created by Suraj on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogWorkActivity extends AppCompatActivity {
    View view;
    Button add, cancel;
    EditText title, description, technology;
    TextInputLayout titleLayout, descLayout, technologyLayout;
    Spinner category;
    DatabaseHelper mydb;
    ArrayAdapter<String> dataAdapter;
    int id;
    String cid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_form);
        getSupportActionBar().setTitle("Edit Work");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        category = (Spinner) findViewById(R.id.category);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.detail);
        technology = (EditText) findViewById(R.id.technology);
        titleLayout = (TextInputLayout) findViewById(R.id.titleLayout);
        descLayout = (TextInputLayout) findViewById(R.id.detailLayout);
        technologyLayout = (TextInputLayout) findViewById(R.id.technologyLayout);
        mydb = new DatabaseHelper(this);

        List<String> data = mydb.getCategoryList();
        Log.e("Data", String.valueOf(data));
        data.add(0, "Choose Category");
        dataAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, data);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);
        category.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
               CategoryInfo info = mydb.selectCategoryInfo().get(position-1);
                cid = info.id;
                Log.e("CategoryId",cid);
            }
        });

        id = getIntent().getIntExtra("id", 0);
        if (id != 0) {
            WorkInfo info = mydb.getWorkInfo(id + "");
            add.setText("Update");
            title.setText(info.title);
            //category.setSelection(Integer.parseInt(info.category));
            description.setText(info.description);
            technology.setText(info.technology);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWork();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addWork() {
        String titleValue = title.getText().toString();
        String descriptionValue = description.getText().toString();
        String categoryValue = category.getSelectedItem().toString();
        String technologyValue = technology.getText().toString();
        if (titleValue.isEmpty() || descriptionValue.isEmpty() || categoryValue.isEmpty() || technologyValue.isEmpty()) {
            titleLayout.setError("Enter title");
            descLayout.setError("Enter description");
            technologyLayout.setError("Enter technology used");
        } else {
            if (id == 0) {
                boolean insert = mydb.insertWorkInfo(cid, titleValue, descriptionValue,technologyValue,categoryValue);
                if (insert == true) {
                    finish();
                } else
                    Toast.makeText(this, "Something went wrong...! Plzz try again", Toast.LENGTH_LONG).show();
            } else {
                boolean insert = mydb.editWorkInfo(String.valueOf(id),cid, titleValue, descriptionValue, technologyValue, categoryValue);
                if (insert == true) {
                    finish();
                } else
                    Toast.makeText(DialogWorkActivity.this, "Something went wrong...!", Toast.LENGTH_LONG).show();
            }
        }
    }
}

