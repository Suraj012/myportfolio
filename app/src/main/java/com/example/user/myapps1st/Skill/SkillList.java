package com.example.user.myapps1st.Skill;

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
import com.example.user.myapps1st.About_fragment;
import com.example.user.myapps1st.Adapter.SkillAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.SkillInfo;
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

public class SkillList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorS;
    View fab;
    int countS;
    SwipeRefreshLayout refreshLayout;
    About_fragment aboutFragment;
    ProgressView progressView;
    SkillAdapter adapter;
    MyHelper myHelper = new MyHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#512da8"));
        }
        getSupportActionBar().setTitle("Skill Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        errorS = (TextView) findViewById(R.id.errorS);
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
                Intent intent = new Intent(getApplicationContext(), DialogSkillActivity.class);
                startActivity(intent);
            }
        });
        fetchSkill();

//        countS = mydb.selectSkillInfo().size();
//        if(countS > 0) {
//            recyclerView = (RecyclerView) findViewById(R.id.recycler);
//            recyclerView.setHasFixedSize(true);
//            SkillAdapter adapter = new SkillAdapter(SkillList.this , null, mydb.selectSkillInfo());
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(adapter);
//            recyclerView.setVisibility(View.VISIBLE);
//            errorS.setVisibility(View.GONE);
//        }else{
//            errorS.setVisibility(View.VISIBLE);
//        }
    }
//    public void Refresh() {
//        if(refreshLayout.isRefreshing()){
//            refreshLayout.setRefreshing(false);
//            AlertDialogClass.displaySnackBar(this, "Data Updated Successfully", R.color.purplePrimary);
//        }
//        Log.e("CountS", String.valueOf(countS));
//        int count = mydb.selectSkillInfo().size();
//        if(count > 0) {
//            recyclerView = (RecyclerView) findViewById(R.id.recycler);
//            recyclerView.setHasFixedSize(true);
//            SkillAdapter adapter = new SkillAdapter(SkillList.this, aboutFragment, mydb.selectSkillInfo());
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(adapter);
//            recyclerView.setVisibility(View.VISIBLE);
//            errorS.setVisibility(View.GONE);
//        }else{
//            errorS.setVisibility(View.VISIBLE);
//            Log.e("error","error");
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setVisibility(View.GONE);
        fetchSkill();
    }

    @Override
    public void onRefresh() {
        fetchSkill();
    }

    public void fetchSkill() {
        progressView.setVisibility(View.VISIBLE);
        errorS.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(SkillList.this);
        if (refreshLayout.isRefreshing()) {
            progressView.setVisibility(View.GONE);
        }
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getSkill, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressView.setVisibility(View.GONE);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                    AlertDialogClass.displaySnackBar(SkillList.this, "Data Updated Successfully", R.color.purplePrimary);
                }
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
                adapter = new SkillAdapter(SkillList.this, null, (ArrayList<SkillInfo>) skillInfo);

                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SkillList.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SkillList.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.e("Single", "Click");
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
                        Log.e("Double", "Click");
                        SkillInfo info = skillInfo.get(position);
                        int id = Integer.parseInt(info.getId());
                        String skill = info.getSkill();
                        String rate = info.getRate();
                        DialogOptionListSkill dialog = new DialogOptionListSkill();
                        YoYo.with(Techniques.Pulse).duration(500);
                        Bundle args = new Bundle();
                        args.putInt("position", id);
                        args.putString("skill", skill);
                        args.putString("rate", rate);
                        args.putString("intent", "activity");
                        dialog.setArguments(args);
                        dialog.show(getFragmentManager(), "Dialog_Option_List");
                    }
                }));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errorE) {
                if (myHelper.isNetworkAvailable(SkillList.this)) {
                    Toast.makeText(SkillList.this, "Server Error.. !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SkillList.this, "Something went wrong. Please check your internet connection and try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                String deviceId = myHelper.getImei(SkillList.this);
                String uid = myHelper.getUID(SkillList.this);
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
