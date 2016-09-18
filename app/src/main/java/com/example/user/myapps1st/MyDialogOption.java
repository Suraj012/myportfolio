package com.example.user.myapps1st;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Contact.ContactActivity;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Experience.ExperienceList;
import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.Model.ContactInfo;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.Model.ProfileInfo;
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.Model.WorkInfo;
import com.example.user.myapps1st.Portfolio.CategoryList;
import com.example.user.myapps1st.Portfolio.WorkList;
import com.example.user.myapps1st.Profile.ProfileActivity;
import com.example.user.myapps1st.Skill.SkillList;
import com.rey.material.widget.Button;

import java.util.ArrayList;

/**
 * Created by User on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyDialogOption extends DialogFragment {
    LayoutInflater inflater;
    View view;
    Button profile,experience, skill, portfolio, contact, category;
    DatabaseHelper mydb;
    String position;
    int profileId, skillId, experienceId, contactId, portfolioId, categoryId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_popupoption, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        YoYo.with(Techniques.ZoomInDown).duration(600).playOn(view);
        profile = (Button) view.findViewById(R.id.profile);
        experience = (Button) view.findViewById(R.id.experience);
        skill = (Button) view.findViewById(R.id.skill);
        portfolio = (Button) view.findViewById(R.id.portfolio);
        category = (Button) view.findViewById(R.id.category);
        contact = (Button) view.findViewById(R.id.contact);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<ProfileInfo> list = mydb.selectProfileInfo();
                for (int i = 0; i < list.size(); i++) {
                    final ProfileInfo info = list.get(i);
                    position = info.id;
                    profileId = Integer.parseInt(position);
                    Log.e("ID", String.valueOf(profileId));
                }

                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("id", profileId);
                startActivity(intent);
                dismiss();
            }
        });

        skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<SkillInfo> list = mydb.selectSkillInfo();
                for (int i = 0; i < list.size(); i++) {
                    final SkillInfo info = list.get(i);
                    position = info.id;
                    skillId = Integer.parseInt(position);
                    Log.e("ID", String.valueOf(skillId));
                }

                Intent intent = new Intent(getActivity(), SkillList.class);
                intent.putExtra("id",skillId);
                startActivity(intent);
                dismiss();
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<ExperienceInfo> list = mydb.selectExperienceInfo();
                for (int i = 0; i < list.size(); i++) {
                    final ExperienceInfo info = list.get(i);
                    position = info.id;
                    experienceId = Integer.parseInt(position);
                    Log.e("ID", String.valueOf(experienceId));
                }

                Intent intent = new Intent(getActivity(), ExperienceList.class);
                intent.putExtra("id",experienceId);
                startActivity(intent);
                dismiss();
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<CategoryInfo> list = mydb.selectCategoryInfo();
                for (int i = 0; i < list.size(); i++) {
                    final CategoryInfo info = list.get(i);
                    position = info.id;
                    categoryId = Integer.parseInt(position);
                    Log.e("ID", String.valueOf(categoryId));
                }

                Intent intent = new Intent(getActivity(), CategoryList.class);
                intent.putExtra("id",categoryId);
                startActivity(intent);
                dismiss();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<ContactInfo> list = mydb.selectContactInfo();
                for (int i = 0; i < list.size(); i++) {
                    final ContactInfo info = list.get(i);
                    position = info.id;
                    contactId = Integer.parseInt(position);
                    Log.e("ID", String.valueOf(contactId));
                }

                Intent intent = new Intent(getActivity(), ContactActivity.class);
                intent.putExtra("id", contactId);
                startActivity(intent);
                dismiss();
            }
        });
        portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<WorkInfo> list = mydb.selectWorkInfo();
                for (int i = 0; i < list.size(); i++) {
                    final WorkInfo info = list.get(i);
                    position = info.id;
                    portfolioId = Integer.parseInt(position);
                    Log.e("ID", String.valueOf(contactId));
                }

                Intent intent = new Intent(getActivity(), WorkList.class);
                intent.putExtra("id", portfolioId);
                startActivity(intent);
                dismiss();
            }
        });



        return builder.create();

    }
}
