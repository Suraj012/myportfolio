package com.example.user.myapps1st.Skill;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.user.myapps1st.About_fragment;
import com.example.user.myapps1st.Adapter.SkillAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.R;

public class SkillList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorS;
    View fab;
    int countS;
    SwipeRefreshLayout refreshLayout;
    About_fragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_list);
        getSupportActionBar().setTitle("Skill Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        errorS = (TextView) findViewById(R.id.errorS);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DialogSkillActivity.class);
                startActivity(intent);
            }
        });

        countS = mydb.selectSkillInfo().size();
        if(countS > 0) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            SkillAdapter adapter = new SkillAdapter(SkillList.this , null, mydb.selectSkillInfo());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            errorS.setVisibility(View.GONE);
        }else{
            errorS.setVisibility(View.VISIBLE);
        }
    }
    public void Refresh() {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
            AlertDialogClass.displaySnackBar(this, "Data Updated Successfully", R.color.purplePrimary);
        }
        Log.e("CountS", String.valueOf(countS));
        int count = mydb.selectSkillInfo().size();
        if(count > 0) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            SkillAdapter adapter = new SkillAdapter(SkillList.this, aboutFragment, mydb.selectSkillInfo());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            errorS.setVisibility(View.GONE);
        }else{
            errorS.setVisibility(View.VISIBLE);
            Log.e("error","error");
        }
    }

    @Override
    protected void onPause() {
        Log.e("OnPause", "OnPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e("OnResume", "OnResume");
        super.onResume();
        Refresh();
    }

    @Override
    public void onRefresh() {
        Refresh();
    }
}
