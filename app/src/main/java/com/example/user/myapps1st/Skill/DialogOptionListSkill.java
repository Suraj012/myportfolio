package com.example.user.myapps1st.Skill;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Experience.ExperienceList;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

import java.util.ArrayList;

/**
 * Created by User on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogOptionListSkill extends DialogFragment {
    LayoutInflater inflater;
    View view;
    Button edit,delete, detail;
    DatabaseHelper mydb;
    String position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_popupoptionlist_e, null);
        final AlertDialog.Builder[] builder = {new AlertDialog.Builder(getActivity())};
        builder[0].setView(view);
        YoYo.with(Techniques.ZoomInDown).duration(600).playOn(view);
        edit = (Button) view.findViewById(R.id.edit);
        delete = (Button) view.findViewById(R.id.delete);
        detail = (Button) view.findViewById(R.id.detail);
        final int id = getArguments().getInt("position");
        mydb = new DatabaseHelper(getActivity());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogSkillActivity.class);
                intent.putExtra("id", id);
                Log.e("Editid", String.valueOf(id));
                startActivity(intent);
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure, You want to delete.");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SkillInfo info = mydb.getSkillInfo(String.valueOf(id));
                        Log.e("delete", String.valueOf(info.id));
                        mydb.deleteSkillInfo(info.id);
                        dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("delete", String.valueOf(id));
                        dismiss();
                    }
                });
                builder.show();
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<ExperienceInfo> list = mydb.selectExperienceInfo();
                for (int i = 0; i < list.size(); i++) {
                    final ExperienceInfo info = list.get(i);
                    position = info.id;

                }

                Intent intent = new Intent(getActivity(), ExperienceList.class);

                startActivity(intent);
                dismiss();
            }
        });



        return builder[0].create();

    }
}
