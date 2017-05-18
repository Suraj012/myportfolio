package com.example.user.myapps1st;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Contact.ContactActivity;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Education.EducationList;
import com.example.user.myapps1st.Experience.ExperienceList;
import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.Model.EducationInfo;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.Portfolio.CategoryList;
import com.example.user.myapps1st.Profile.ProfileActivity;
import com.example.user.myapps1st.Skill.SkillList;
import com.rey.material.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyDialogOption extends DialogFragment {
    LayoutInflater inflater;
    View view;
    Button profile, education, experience, skill, portfolio, contact, category;
    DatabaseHelper mydb;
    String position;
    private RequestQueue requestQueue;
    private StringRequest request;
    int profileId, educationId, skillId, experienceId, contactId, portfolioId, categoryId;
    MyHelper myHelper = new MyHelper(getActivity());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_popupoption, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        YoYo.with(Techniques.ZoomInDown).duration(600).playOn(view);
        profile = (Button) view.findViewById(R.id.profile);
        education = (Button) view.findViewById(R.id.education);
        experience = (Button) view.findViewById(R.id.experience);
        skill = (Button) view.findViewById(R.id.skill);
        category = (Button) view.findViewById(R.id.category);
        contact = (Button) view.findViewById(R.id.contact);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfile();
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
                intent.putExtra("id", skillId);
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
                intent.putExtra("id", experienceId);
                startActivity(intent);
                dismiss();
            }
        });

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb = new DatabaseHelper(getActivity());
                ArrayList<EducationInfo> list = mydb.selectEducationInfo();
                for (int i = 0; i < list.size(); i++) {
                    final EducationInfo info = list.get(i);
                    position = info.id;
                    educationId = Integer.parseInt(position);
                    Log.e("ID", String.valueOf(educationId));
                }

                Intent intent = new Intent(getActivity(), EducationList.class);
                intent.putExtra("id", educationId);
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
                intent.putExtra("id", categoryId);
                startActivity(intent);
                dismiss();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContact();
            }
        });

        return builder.create();

    }

    private void getProfile() {
        requestQueue = Volley.newRequestQueue(getActivity());
        request = new StringRequest(Request.Method.POST, Constants.getProfile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equalsIgnoreCase(String.valueOf(0))) {
                        JSONArray finalArray = object.getJSONArray("data");
                        for (int i = 0; i < finalArray.length(); i++) {
                            JSONObject finalObject = finalArray.getJSONObject(i);
                            String name = finalObject.getString("name");
                            String designation = finalObject.getString("designation");
                            String description = finalObject.getString("description");
                            String websites = finalObject.getString("websites");
                            String facebook = finalObject.getString("facebook");
                            String google = finalObject.getString("google");
                            String twitter = finalObject.getString("twitter");
                            String instagram = finalObject.getString("instagram");
                            String image = finalObject.getString("image");
                            int uid = Integer.parseInt(myHelper.getUID(getActivity()));
                            if (!(uid == 0)) {
                                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                Log.e("IDD", String.valueOf(uid));
                                intent.putExtra("id", uid);
                                intent.putExtra("name", name);
                                intent.putExtra("websites", websites);
                                intent.putExtra("designation", designation);
                                intent.putExtra("description", description);
                                intent.putExtra("facebook", facebook);
                                intent.putExtra("twitter", twitter);
                                intent.putExtra("google", google);
                                intent.putExtra("instagram", instagram);
                                intent.putExtra("image", image);
                                startActivity(intent);
                                dismiss();
                            }
                        }
                    } else {
                        Intent intent = new Intent(getActivity(), ProfileActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(getActivity());
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("deviceId", "869087022141664");
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void getContact() {
        requestQueue = Volley.newRequestQueue(getActivity());
        request = new StringRequest(Request.Method.POST, Constants.getContact, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equalsIgnoreCase(String.valueOf(0))) {
                        JSONArray finalArray = object.getJSONArray("data");
                        for (int i = 0; i < finalArray.length(); i++) {
                            JSONObject finalObject = finalArray.getJSONObject(i);
                            String address = finalObject.getString("address");
                            String city = finalObject.getString("city");
                            String country = finalObject.getString("country");
                            String latitude = finalObject.getString("latitude");
                            String longitude = finalObject.getString("longitude");
                            String phone = finalObject.getString("phone");
                            String primaryEmail = finalObject.getString("primaryEmail");
                            String secondaryEmail = finalObject.getString("secondaryEmail");
                            int uid = Integer.parseInt(myHelper.getUID(getActivity()));
                            if (!(uid == 0)) {
                                Intent intent = new Intent(getActivity(), ContactActivity.class);
                                Log.e("IDD", String.valueOf(uid));
                                intent.putExtra("id", uid);
                                intent.putExtra("address", address);
                                intent.putExtra("city", city);
                                intent.putExtra("country", country);
                                intent.putExtra("latitude", latitude);
                                intent.putExtra("longitude", longitude);
                                intent.putExtra("phone", phone);
                                intent.putExtra("primaryEmail", primaryEmail);
                                intent.putExtra("secondaryEmail", secondaryEmail);
                                startActivity(intent);
                                dismiss();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("Login", getActivity().MODE_PRIVATE);
                String uid = prefs.getString("uid", "Default");
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("deviceId", "869087022141664");
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
