package com.example.user.myapps1st.Experience;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_form);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }

        getSupportActionBar().setTitle("Edit Experience");
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
        String titleValue = title.getText().toString();
        String companyValue = company.getText().toString();
        String datefromValue = dateFrom.getText().toString();
        String datetoValue = dateTo.getText().toString();
        String descriptionValue = description.getText().toString();


        if (titleValue.isEmpty() || companyValue.isEmpty() || datefromValue.isEmpty() || datetoValue.isEmpty() || descriptionValue.isEmpty()) {
            titleLayout.setError("Enter title");
            companyLayout.setError("Enter company name");
            datefromLayout.setError("Enter join date from");
            datetoLayout.setError("Enter join date to");
            descriptionLayout.setError("Enter description");
        } else {
            if (id == 0) {
                boolean insert = mydb.insertExperienceInfo(titleValue, companyValue, datefromValue, datetoValue, descriptionValue);
                if (insert == true) {
                    finish();
                } else
                    Toast.makeText(this, "Something went wrong...!", Toast.LENGTH_LONG).show();
            } else {
                boolean insert = mydb.editExperienceInfo(String.valueOf(id), titleValue, companyValue, datefromValue, datetoValue, descriptionValue);
                if (insert == true) {
                    finish();
                } else
                    Toast.makeText(DialogExperienceActivity.this, "Something went wrong...!", Toast.LENGTH_LONG).show();

            }
        }
    }
}

