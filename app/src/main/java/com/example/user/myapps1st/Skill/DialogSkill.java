package com.example.user.myapps1st.Skill;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

/**
 * Created by Suraj on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogSkill extends DialogFragment {
    LayoutInflater inflater;
    View view;
    Button add, cancel;
    EditText skill, rate;
    TextInputLayout skillLayout, rateLayout;
    DatabaseHelper mydb;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_skill_form, null);
        getActivity().getActionBar().setTitle("Add Skill");
        YoYo.with(Techniques.FadeIn).duration(800).playOn(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setView(view);
        this.setCancelable(false);
        add = (Button) view.findViewById(R.id.add);
        cancel = (Button) view.findViewById(R.id.cancel);
        skill = (EditText) view.findViewById(R.id.skill);
        rate = (EditText) view.findViewById(R.id.rate);
        skillLayout = (TextInputLayout) view.findViewById(R.id.skillLayout);
        rateLayout = (TextInputLayout) view.findViewById(R.id.rateLayout);
        mydb = new DatabaseHelper(getActivity());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkill();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
    public void addSkill(){
        String rateValue = rate.getText().toString();
        String skillValue = skill.getText().toString();
        if(rateValue.isEmpty() || skillValue.isEmpty()){
            rateLayout.setError("Enter rating");
            skillLayout.setError("Enter skill");
        }else{
            boolean insert = mydb.insertSkillInfo(rateValue, skillValue);
            if (insert == true) {
                dismiss();
            } else
                Toast.makeText(getActivity(), "Something went wrong...!", Toast.LENGTH_LONG).show();
        }
    }
}

