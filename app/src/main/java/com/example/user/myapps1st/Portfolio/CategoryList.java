package com.example.user.myapps1st.Portfolio;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Adapter.CategoryAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.R;
import com.example.user.myapps1st.util.RecyclerItemClickListener;

import java.util.ArrayList;

public class CategoryList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorE;
    View fab;
    int count;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        getSupportActionBar().setTitle("Portfolio Lists");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        errorE = (TextView) findViewById(R.id.errorE);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryAddActivity.class);
                startActivity(intent);
            }
        });

//        if(count > 0) {
//            recyclerView = (RecyclerView) findViewById(R.id.recycler);
//            recyclerView.setHasFixedSize(true);
//            CategoryAdapter adapter = new CategoryAdapter(this, mydb.selectCategoryInfo());
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(adapter);
//            recyclerView.setVisibility(View.VISIBLE);
//            errorE.setVisibility(View.GONE);
//        }else{
//            errorE.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Refresh() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
            AlertDialogClass.displaySnackBar(this, "Data Updated Successfully", R.color.bluePrimary);
        }
        count = mydb.selectCategoryInfo().size();
        Log.e("Counted", String.valueOf(count));
        if(count > 0) {
            recyclerView.setHasFixedSize(true);
            CategoryAdapter adapter = new CategoryAdapter(this, mydb.selectCategoryInfo());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            errorE.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else{
            errorE.setVisibility(View.VISIBLE);
        }
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                ArrayList<CategoryInfo> list = mydb.selectCategoryInfo();
                final CategoryInfo info = list.get(position);
                int idd = Integer.parseInt(info.id);
                Intent intent = new Intent(CategoryList.this, PortfolioList.class);
                intent.putExtra("position", idd);
                intent.putExtra("title", info.category);
                startActivity(intent);
                Log.e("CategoryId", String.valueOf(idd));
            }

            @Override
            public void onItemLongPress(View childView, int position) {
                ArrayList<CategoryInfo> list = mydb.selectCategoryInfo();
                final CategoryInfo info = list.get(position);
                int id = Integer.parseInt(info.id);
                Log.e("ID", String.valueOf(id));
                DialogOptionListCategory dialog = new DialogOptionListCategory();
                YoYo.with(Techniques.Pulse).duration(500);
                Bundle args = new Bundle();
                args.putInt("position", id);
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "Dialog_Option_List");
            }
        }) {
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Counted","pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Counted","Resume");
        Refresh();
    }

    @Override
    public void onRefresh() {
        Refresh();
    }
}
