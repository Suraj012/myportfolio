package com.example.user.myapps1st.Experience;

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
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

/**
 * Created by User on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogOptionListExperience extends DialogFragment {
    LayoutInflater inflater;
    View view;
    Button edit, delete;
    DatabaseHelper mydb;
    String position;
    int editId, deleteId, detailId, id;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_popupoptionlist_e, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        YoYo.with(Techniques.ZoomInDown).duration(600).playOn(view);
        edit = (Button) view.findViewById(R.id.edit);
        delete = (Button) view.findViewById(R.id.delete);
        final int id = getArguments().getInt("position");
        final String intent = getArguments().getString("intent");
        mydb = new DatabaseHelper(getActivity());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogExperienceActivity.class);
                intent.putExtra("id", id);
                Log.e("Editid", String.valueOf(id));
                startActivity(intent);
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure, You want to delete.");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExperienceInfo info = mydb.getExperienceInfo(String.valueOf(id));
                        Log.e("delete", String.valueOf(info.id));
                       // mydb.deleteExperienceInfo(info.id);
                        Log.e("Intent", intent);
                       //  if(intent.equalsIgnoreCase("activity")) {
                            ExperienceList experienceList = (ExperienceList) getActivity().getApplicationContext();
                            experienceList.Refresh();
                            dismiss();
//                        }else{
//                           // dismiss();
//                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                builder.show();
                // dismiss();
            }
        });
        return builder.create();

    }
}
