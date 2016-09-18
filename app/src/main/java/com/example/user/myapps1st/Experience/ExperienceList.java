package com.example.user.myapps1st.Experience;

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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Adapter.ExperienceAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.R;
import com.example.user.myapps1st.util.RecyclerItemClickListener;

import java.util.ArrayList;

public class ExperienceList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorE;
    View fab;
    int countE;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_list);
        getSupportActionBar().setTitle("Experience Lists");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        errorE = (TextView) findViewById(R.id.errorE);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DialogExperienceActivity.class);
                startActivity(intent);
            }
        });
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

    public void Refresh() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            AlertDialogClass.displaySnackBar(this, "Data Updated Successfully", R.color.purplePrimary);
        }
//        Fragment fragment = getFragmentManager().findFragmentByTag("about");
//        FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
//        ft.detach(fragment).attach(fragment);
        countE = mydb.selectExperienceInfo().size();
        Log.e("Count", String.valueOf(countE));
        if (countE > 0) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            ExperienceAdapter adapter = new ExperienceAdapter(this, mydb.selectExperienceInfo());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            errorE.setVisibility(View.GONE);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ArrayList<ExperienceInfo> list = mydb.selectExperienceInfo();
                            final ExperienceInfo info = list.get(position);
                            int id = Integer.parseInt(info.id);
                            Intent intent = new Intent(getApplicationContext(), ExperienceDetail.class);
                            intent.putExtra("position", id);
                            startActivity(intent);
                            Log.e("EditIDD", String.valueOf(id));

                        }

                        @Override
                        public void onItemLongPress(View childView, int position) {
                            ArrayList<ExperienceInfo> list = mydb.selectExperienceInfo();
                            final ExperienceInfo info = list.get(position);
                            int id = Integer.parseInt(info.id);
                            DialogOptionListExperience dialog = new DialogOptionListExperience();
                            YoYo.with(Techniques.Pulse).duration(500);
                            Bundle args = new Bundle();
                            args.putInt("position", id);
                            args.putString("intent", "activity");
                            dialog.setArguments(args);
                            dialog.show(getFragmentManager(), "Dialog_Option_List");
                        }

                    })
            );


        } else {
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
