package com.example.user.myapps1st.Portfolio;

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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Adapter.WorkAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.WorkInfo;
import com.example.user.myapps1st.R;
import com.example.user.myapps1st.util.RecyclerItemClickListener;

import java.util.ArrayList;

public class WorkList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorS;
    View fab;
    int countS;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1976d2"));
        }

        getSupportActionBar().setTitle("Work Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));
        errorS = (TextView) findViewById(R.id.errorS);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DialogWorkActivity.class);
                startActivity(intent);
            }
        });

        countS = mydb.selectWorkInfo().size();
        if(countS > 0) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            WorkAdapter adapter = new WorkAdapter(WorkList.this, mydb.selectWorkInfo());
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
        int count = mydb.selectWorkInfo().size();
        if(count > 0) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            WorkAdapter adapter = new WorkAdapter(WorkList.this, mydb.selectWorkInfo());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            errorS.setVisibility(View.GONE);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ArrayList<WorkInfo> list = mydb.selectWorkInfo();
                            final WorkInfo info = list.get(position);
                            int id = Integer.parseInt(info.id);
                            Intent intent = new Intent(getApplicationContext(), WorkDetail.class);
                            intent.putExtra("position", id);
                            startActivity(intent);
                            Log.e("EditIDD", String.valueOf(id));

                        }

                        @Override
                        public void onItemLongPress(View childView, int position) {
                            ArrayList<WorkInfo> list = mydb.selectWorkInfo();
                            final WorkInfo info = list.get(position);
                            int id = Integer.parseInt(info.id);
                            DialogOptionListWork dialog = new DialogOptionListWork();
                            YoYo.with(Techniques.Pulse).duration(500);
                            Bundle args = new Bundle();
                            args.putInt("position", id);
                            dialog.setArguments(args);
                            dialog.show(getFragmentManager(), "Dialog_Option_List");
                        }

                    })
            );

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
