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
import com.example.user.myapps1st.Adapter.PortfolioAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.WorkInfo;
import com.example.user.myapps1st.R;
import com.example.user.myapps1st.util.RecyclerItemClickListener;

import java.util.ArrayList;

public class PortfolioList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorE;
    View fab;
    int countE;
    SwipeRefreshLayout refreshLayout;
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_portfolio_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1976d2"));
        }

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        errorE = (TextView) findViewById(R.id.errorE);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        Log.e("kjla", "ljakdsd");
        cid = getIntent().getIntExtra("position",0);
        Log.e("Position", String.valueOf(cid));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DialogWorkActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Refresh() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            AlertDialogClass.displaySnackBar(this, "Data Updated Successfully", R.color.bluePrimary);
        }
        countE = mydb.selectWorkInfo2(String.valueOf(cid)).size();
        Log.e("CountE", String.valueOf(countE));
        if(countE > 0) {
            recyclerView.setHasFixedSize(true);
            PortfolioAdapter adapter = new PortfolioAdapter(this, mydb.selectWorkInfo2(String.valueOf(cid)));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            errorE.setVisibility(View.GONE);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View childView, int position) {
                    ArrayList<WorkInfo> list = mydb.selectWorkInfo2(String.valueOf(cid));
                    final WorkInfo info = list.get(position);
                    int id = Integer.parseInt(info.id);
                    Intent intent = new Intent(getApplicationContext(), WorkDetail.class);
                    intent.putExtra("position", id);
                    startActivity(intent);
                    Log.e("EditIDD", String.valueOf(id));
                    Log.e("Title", info.title);
                }

                @Override
                public void onItemLongPress(View childView, int position) {
                    ArrayList<WorkInfo> list = mydb.selectWorkInfo2(String.valueOf(cid));
                    final WorkInfo info = list.get(position);
                    int id = Integer.parseInt(info.id);
                    Log.e("ID", String.valueOf(id));
                    DialogOptionListWork dialog = new DialogOptionListWork();
                    YoYo.with(Techniques.Pulse).duration(500);
                    Bundle args = new Bundle();
                    args.putInt("position", id);
                    dialog.setArguments(args);
                    dialog.show(getFragmentManager(), "Dialog_Option_List");
                }
            }) {
            });
        }else{
            errorE.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    @Override
    public void onRefresh() {
        Refresh();
    }
}
