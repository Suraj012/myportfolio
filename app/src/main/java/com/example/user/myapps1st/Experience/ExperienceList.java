package com.example.user.myapps1st.Experience;

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
import com.example.user.myapps1st.Adapter.ExperienceAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ExperienceInfo;
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

public class ExperienceList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorE;
    View fab;
    int countE;
    SwipeRefreshLayout refreshLayout;
    ExperienceAdapter adapter;
    ProgressView progressView;
    MyHelper myHelper = new MyHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }

        getSupportActionBar().setTitle("Experience Lists");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        //getSupportActionBar().setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor("#c2185b")));
        //getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff8a80")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        errorE = (TextView) findViewById(R.id.errorE);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        progressView = (ProgressView) findViewById(R.id.progressview);
        progressView.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DialogExperienceActivity.class);
                startActivity(intent);
            }
        });
        fetchExperience();
//        if(countE > 0) {
//            recyclerView = (RecyclerView) findViewById(R.id.recycler);
//            recyclerView.setHasFixedSize(true);
//            ExperienceAdapter adapter = new ExperienceAdapter(this, mydb.selectExperienceInfo());
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(adapter);
//            recyclerView.setVisibility(View.VISIBLE);
//            errorE.setVisibility(View.GONE);
//        }else{
//            errorE.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("abc", "abc");
        recyclerView.setVisibility(View.GONE);
        fetchExperience();
    }

    @Override
    public void onRefresh() {
        fetchExperience();
    }

    public void fetchExperience() {
        progressView.setVisibility(View.VISIBLE);
        errorE.setVisibility(View.GONE);
        final RequestQueue requestQueue = Volley.newRequestQueue(ExperienceList.this);
        if (refreshLayout.isRefreshing()) {
            progressView.setVisibility(View.GONE);
        }
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getExperience, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressView.setVisibility(View.GONE);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                    AlertDialogClass.displaySnackBar(ExperienceList.this, "Data Updated Successfully", R.color.purplePrimary);
                }
                Log.e("Response", response);
                final List<ExperienceInfo> experienceInfo = new ArrayList<>();
                try {
                    JSONObject parentObject = new JSONObject(response);
                    int status = Integer.parseInt(parentObject.getString("status"));
                    if (status == 0) {
                        errorE.setVisibility(View.GONE);
//                        error.setVisibility(View.GONE);
//                        searchLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
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
                        recyclerView.setVisibility(View.GONE);
                        errorE.setVisibility(View.VISIBLE);
//                        searchLayout.setVisibility(View.GONE);
//                        groupLayout.setVisibility(View.GONE);
//                        contact.setVisibility(View.GONE);
//                        error.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
                adapter = new ExperienceAdapter(ExperienceList.this, (ArrayList<ExperienceInfo>) experienceInfo);

                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ExperienceList.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ExperienceList.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        ArrayList<ExperienceInfo> list = mydb.selectExperienceInfo();
//                        final ExperienceInfo info = list.get(position);
//                        int id = Integer.parseInt(info.id);
//                        Intent intent = new Intent(getApplicationContext(), ExperienceDetail.class);
//                        intent.putExtra("position", id);
//                        startActivity(intent);
//                        Log.e("EditIDD", String.valueOf(id));

                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {
                        ExperienceInfo info = experienceInfo.get(position);
                        int id = Integer.parseInt(info.getId());
                        String title = info.getTitle();
                        String company = info.getCompany();
                        String description = info.getDescription();
                        String startDate = info.getDateFrom();
                        String endDate = info.getDateTo();
                        DialogOptionListExperience dialog = new DialogOptionListExperience();
                        YoYo.with(Techniques.Pulse).duration(500);
                        Bundle args = new Bundle();
                        args.putInt("position", id);
                        args.putString("intent", "activity");
                        args.putString("title", title);
                        args.putString("company", company);
                        args.putString("description", description);
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
                if (myHelper.isNetworkAvailable(ExperienceList.this)) {
                    Toast.makeText(ExperienceList.this, "Server Error.. !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExperienceList.this, "Something went wrong. Please check your internet connection and try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
//                            0);
//                } else {
//                    Toast.makeText(getContext(),"cba",Toast.LENGTH_SHORT).show();
//                    TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//                    imeiId = telephonyManager.getDeviceId();
//                }
                HashMap<String, String> hashMap = new HashMap<String, String>();
                String deviceId = myHelper.getImei(ExperienceList.this);
                String uid = myHelper.getUID(ExperienceList.this);
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
