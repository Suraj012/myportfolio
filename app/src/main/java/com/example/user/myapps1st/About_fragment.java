package com.example.user.myapps1st;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.myapps1st.Adapter.EducationAdapter;
import com.example.user.myapps1st.Adapter.ExperienceAdapter;
import com.example.user.myapps1st.Adapter.SkillAdapter;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Education.DialogEducationActivity;
import com.example.user.myapps1st.Experience.DialogExperienceActivity;
import com.example.user.myapps1st.Model.EducationInfo;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.Skill.DialogSkillActivity;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class About_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView, recyclerViewE, recyclerViewEdu;
    ArrayList<SkillInfo> list = new ArrayList<>();
    DatabaseHelper mydb;
    Button addSkill, addExperience, addEducation;
    TextView errorE, errorS, errorEdu;
    SwipeRefreshLayout refreshLayout;
    ProgressView progressViewS, progressViewE, progressViewEdu;
    MyHelper myHelper = new MyHelper(getActivity());

    public About_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        addSkill = (Button) view.findViewById(R.id.addSkill);
        addExperience = (Button) view.findViewById(R.id.addExperience);
        addEducation = (Button) view.findViewById(R.id.addEducation);
        errorE = (TextView) view.findViewById(R.id.errorE);
        errorS = (TextView) view.findViewById(R.id.errorS);
        errorEdu = (TextView) view.findViewById(R.id.errorEdu);
        progressViewE = (ProgressView) view.findViewById(R.id.progressviewE);
        progressViewS = (ProgressView) view.findViewById(R.id.progressviewS);
        progressViewEdu = (ProgressView) view.findViewById(R.id.progressviewEdu);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);


        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogSkillActivity.class);
                startActivity(intent);
//                DialogSkill dialog = new DialogSkill();
//                Bundle args = new Bundle();
//                args.putString("enable", enable);
//                dialog.setArguments(args);
//                Toast.makeText(getActivity(), "heloo", Toast.LENGTH_SHORT).show();
//                dialog.show(getFragmentManager(), "Dialog_show");
            }
        });

        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogExperienceActivity.class);
                startActivity(intent);
            }
        });

        addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogEducationActivity.class);
                startActivity(intent);
            }
        });

        mydb = new DatabaseHelper(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
//        recyclerView.setHasFixedSize(true);
//        SkillAdapter adapter = new SkillAdapter(mydb.selectSkillInfo());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);

        recyclerViewE = (RecyclerView) view.findViewById(R.id.recyclerE);
//        recyclerViewE.setHasFixedSize(true);
//        ExperienceAdapter adapter1 = new ExperienceAdapter(mydb.selectExperienceInfo());
//        recyclerViewE.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewE.setAdapter(adapter1);

        recyclerViewEdu = (RecyclerView) view.findViewById(R.id.recyclerEdu);
        getSkill();
        getExperience();
        getEducation();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Onpause", "Pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        getSkill();
        getExperience();
        getEducation();
    }

    private void getSkill() {
        progressViewS.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getSkill, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressViewS.setVisibility(View.GONE);
                Log.e("Response", response);
                final List<SkillInfo> skillInfo = new ArrayList<>();
                try {
                    JSONObject parentObject = new JSONObject(response);
                    int status = Integer.parseInt(parentObject.getString("status"));
                    if (status == 0) {
                        errorS.setVisibility(View.GONE);
//                        error.setVisibility(View.GONE);
//                        searchLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONArray parentArray = parentObject.getJSONArray("data");
                        for (int i = 0; i < parentArray.length(); i++) {
                            SkillInfo info = new SkillInfo();
                            JSONObject finalObject = parentArray.getJSONObject(i);
                            info.setId(finalObject.getString("id"));
                            info.setUid(finalObject.getString("uid"));
                            info.setRate(finalObject.getString("rate"));
                            info.setSkill(finalObject.getString("skill"));
                            skillInfo.add(info);
                        }
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        errorS.setVisibility(View.VISIBLE);
                        errorS.setText("Sorry ! Not found any Information.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
                SkillAdapter adapter = new SkillAdapter(getActivity(), About_fragment.this, (ArrayList<SkillInfo>) skillInfo);

                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errore) {
                progressViewS.setVisibility(View.GONE);
                if (myHelper.isNetworkAvailable(getActivity())) {
                    errorS.setText("Server Error.");
                    errorS.setVisibility(View.VISIBLE);
                } else {
                    errorS.setText("No internet connection..!");
                    errorS.setVisibility(View.VISIBLE);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(getActivity());
                String deviceId = myHelper.getImei(getActivity());
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("deviceId", deviceId);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void getExperience() {
        progressViewE.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getExperience, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressViewE.setVisibility(View.GONE);
                Log.e("ResponseE", response);
                final List<ExperienceInfo> experienceInfo = new ArrayList<>();
                try {
                    JSONObject parentObject = new JSONObject(response);
                    int status = Integer.parseInt(parentObject.getString("status"));
                    if (status == 0) {
                        errorE.setVisibility(View.GONE);
                        recyclerViewE.setVisibility(View.VISIBLE);
                        JSONArray parentArray = parentObject.getJSONArray("data");
                        for (int i = 0; i < parentArray.length(); i++) {
                            ExperienceInfo info = new ExperienceInfo();
                            JSONObject finalObject = parentArray.getJSONObject(i);
                            info.setId(finalObject.getString("id"));
                            info.setUid(finalObject.getString("uid"));
                            info.setTitle(finalObject.getString("title"));
                            info.setCompany(finalObject.getString("company"));
                            info.setDescription(finalObject.getString("description"));
                            info.setDateFrom(finalObject.getString("datefrom"));
                            info.setDateTo(finalObject.getString("dateto"));
                            experienceInfo.add(info);
                        }
                    } else {
                        recyclerViewE.setVisibility(View.GONE);
                        errorE.setText("Sorry ! Not found any Information.");
                        errorE.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
                ExperienceAdapter adapter = new ExperienceAdapter(getActivity(), (ArrayList<ExperienceInfo>) experienceInfo);

                recyclerViewE.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewE.setLayoutManager(layoutManager);
                recyclerViewE.setAdapter(adapter);
                recyclerViewE.setVisibility(View.VISIBLE);
                //errorE.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errore) {
                progressViewE.setVisibility(View.GONE);
                if (myHelper.isNetworkAvailable(getActivity())) {
                    errorE.setText("Server Error");
                    errorE.setVisibility(View.VISIBLE);
                } else {
                    errorE.setText("No internet connection..!");
                    errorE.setVisibility(View.VISIBLE);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(getActivity());
                String deviceId = myHelper.getImei(getActivity());
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("deviceId", deviceId);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void getEducation() {
        progressViewEdu.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getEducation, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressViewEdu.setVisibility(View.GONE);
                Log.e("ResponseEdu", response);
                final List<EducationInfo> educationInfo = new ArrayList<>();
                try {
                    JSONObject parentObject = new JSONObject(response);
                    int status = Integer.parseInt(parentObject.getString("status"));
                    if (status == 0) {
                        errorEdu.setVisibility(View.GONE);
//                        error.setVisibility(View.GONE);
//                        searchLayout.setVisibility(View.VISIBLE);
                        recyclerViewEdu.setVisibility(View.VISIBLE);
                        JSONArray parentArray = parentObject.getJSONArray("data");
                        for (int i = 0; i < parentArray.length(); i++) {
                            EducationInfo info = new EducationInfo();
                            JSONObject finalObject = parentArray.getJSONObject(i);
                            info.setId(finalObject.getString("id"));
                            info.setUid(finalObject.getString("uid"));
                            info.setCollege(finalObject.getString("college"));
                            info.setUniversity(finalObject.getString("university"));
                            info.setDegree(finalObject.getString("degree"));
                            info.setStartDate(finalObject.getString("start_date"));
                            info.setEndDate(finalObject.getString("end_date"));
                            info.setMarks(finalObject.getString("marks"));
                            educationInfo.add(info);
                        }
                    } else {
                        recyclerViewEdu.setVisibility(View.GONE);
                        errorEdu.setText("Sorry ! Not found any Information.");
                        errorEdu.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
                EducationAdapter adapter = new EducationAdapter(getActivity(), (ArrayList<EducationInfo>) educationInfo);

                recyclerViewEdu.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewEdu.setLayoutManager(layoutManager);
                recyclerViewEdu.setAdapter(adapter);
                recyclerViewEdu.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errore) {
                progressViewEdu.setVisibility(View.GONE);
                if (myHelper.isNetworkAvailable(getActivity())) {
                    errorEdu.setText("Server Error.");
                    errorEdu.setVisibility(View.VISIBLE);
                } else {
                    errorEdu.setText("No internet connection..!");
                    errorEdu.setVisibility(View.VISIBLE);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(getActivity());
                String deviceId = myHelper.getImei(getActivity());
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("deviceId", deviceId);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }


    @Override
    public void onRefresh() {
        getSkill();
        getExperience();
        getEducation();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
