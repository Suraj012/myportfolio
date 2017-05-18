package com.example.user.myapps1st.Education;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.example.user.myapps1st.Adapter.EducationAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.EducationInfo;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.example.user.myapps1st.util.RecyclerItemClickListener;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EducationList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorEdu;
    View fab;
    int countEdu;
    SwipeRefreshLayout refreshLayout;
    ProgressView progressView;
    MyHelper myHelper = new MyHelper(this);
    EducationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }

        getSupportActionBar().setTitle("Education Lists");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        //getSupportActionBar().setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor("#c2185b")));
        //getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff8a80")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        errorEdu = (TextView) findViewById(R.id.errorEdu);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        progressView = (ProgressView) findViewById(R.id.progressview);
        progressView.setVisibility(View.VISIBLE);
        refreshLayout.setOnRefreshListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DialogEducationActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setVisibility(View.GONE);
        fetchEducation();
    }

    @Override
    public void onRefresh() {
        fetchEducation();
    }

    public void fetchEducation() {
        progressView.setVisibility(View.VISIBLE);
        errorEdu.setVisibility(View.GONE);
        final RequestQueue requestQueue = Volley.newRequestQueue(EducationList.this);
        if (refreshLayout.isRefreshing()) {
            progressView.setVisibility(View.GONE);
        }
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getEducation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressView.setVisibility(View.GONE);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                    AlertDialogClass.displaySnackBar(EducationList.this, "Data Updated Successfully", R.color.purplePrimary);
                }
                Log.e("Response", response);
                final List<EducationInfo> educationInfo = new ArrayList<>();
                try {
                    JSONObject parentObject = new JSONObject(response);
                    int status = Integer.parseInt(parentObject.getString("status"));
                    if (status == 0) {
                        errorEdu.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONArray parentArray = parentObject.getJSONArray("data");
                        for (int i = 0; i < parentArray.length(); i++) {
                            EducationInfo info = new EducationInfo();
                            JSONObject finalObject = parentArray.getJSONObject(i);
                            info.setId(finalObject.getString("id"));
                            info.setUid(finalObject.getString("uid"));
                            info.setCollege(finalObject.getString("college"));
                            info.setUniversity(finalObject.getString("university"));
                            info.setDegree(finalObject.getString("degree"));
                            info.setMarks(finalObject.getString("marks"));
                            info.setStartDate(finalObject.getString("start_date"));
                            info.setEndDate(finalObject.getString("end_date"));
                            educationInfo.add(info);
                        }
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        errorEdu.setVisibility(View.VISIBLE);
//                        searchLayout.setVisibility(View.GONE);
//                        groupLayout.setVisibility(View.GONE);
//                        contact.setVisibility(View.GONE);
//                        error.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
                adapter = new EducationAdapter(EducationList.this, (ArrayList<EducationInfo>) educationInfo);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EducationList.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(EducationList.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                            ArrayList<EducationInfo> list = mydb.selectEducationInfo();
//                            final EducationInfo info = list.get(position);
//                            int id = Integer.parseInt(info.id);
//                            Intent intent = new Intent(getApplicationContext(), EducationDetail.class);
//                            intent.putExtra("position", id);
//                            startActivity(intent);
//                            Log.e("EditIDD", String.valueOf(id));

                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {
                        Log.e("position", String.valueOf(position));
                        EducationInfo info = educationInfo.get(position);
                        int id = Integer.parseInt(info.getId());
                        String degree = info.getDegree();
                        String college = info.getCollege();
                        String university = info.getUniversity();
                        String marks = info.getMarks();
                        String startDate = info.getStartDate();
                        String endDate = info.getEndDate();
                        DialogOptionListEducation dialog = new DialogOptionListEducation();
                        YoYo.with(Techniques.Pulse).duration(500);
                        Bundle args = new Bundle();
                        args.putInt("position", id);
                        args.putString("intent", "activity");
                        args.putString("college", college);
                        args.putString("university", university);
                        args.putString("degree", degree);
                        args.putString("marks", marks);
                        args.putString("startDate", startDate);
                        args.putString("endDate", endDate);
                        dialog.setArguments(args);
                        dialog.show(getFragmentManager(), "Dialog_Option_List");
                    }

                }));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errorE) {
                if (myHelper.isNetworkAvailable(EducationList.this)) {
                    Toast.makeText(EducationList.this, "Server Error.. !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EducationList.this, "Something went wrong. Please check your internet connection and try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                String deviceId = myHelper.getImei(EducationList.this);
                String uid = myHelper.getUID(EducationList.this);
                Log.e("deviceId", deviceId);
                Log.e("uID", uid);
                hashMap.put("deviceId", deviceId);
                hashMap.put("uid", uid);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
