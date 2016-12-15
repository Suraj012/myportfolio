package com.example.user.myapps1st.Skill;

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
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_form);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }
        getSupportActionBar().setTitle("Edit Skill");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        skill = (EditText) findViewById(R.id.skill);
        rate = (EditText) findViewById(R.id.rate);
        skillLayout = (TextInputLayout) findViewById(R.id.skillLayout);
        rateLayout = (TextInputLayout) findViewById(R.id.rateLayout);
        mydb = new DatabaseHelper(this);
        id = getIntent().getIntExtra("id", 0);
        if(id != 0){
            SkillInfo info = mydb.getSkillInfo(id + "");
            add.setText("Update");
            skill.setText(info.skill);
            rate.setText(info.rate);
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

    public void addSkill(){
        String rateValue = rate.getText().toString();
        String skillValue = skill.getText().toString();
        if(rateValue.isEmpty() || skillValue.isEmpty()){
            rateLayout.setError("Enter rating");
            skillLayout.setError("Enter skill");
        }else{
            if(id == 0) {
                boolean insert = mydb.insertSkillInfo(rateValue, skillValue);
                if (insert == true) {
                    finish();
                } else
                    Toast.makeText(this, "Something went wrong...! Plzz try again", Toast.LENGTH_LONG).show();
            }else{
                boolean insert = mydb.editSkillInfo(String.valueOf(id), rateValue, skillValue);
                if (insert == true) {

                    finish();
                } else
                    Toast.makeText(DialogSkillActivity.this, "Something went wrong...!", Toast.LENGTH_LONG).show();
            }
        }
    }
}

