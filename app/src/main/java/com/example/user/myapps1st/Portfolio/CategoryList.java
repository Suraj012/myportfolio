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
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.user.myapps1st.Adapter.CategoryAdapter;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.example.user.myapps1st.util.RecyclerItemClickListener;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryList extends AppCompatActivity implements Serializable,SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    DatabaseHelper mydb;
    TextView errorE;
    View fab;
    ProgressView progressView;
    int count;
    SwipeRefreshLayout refreshLayout;
    MyHelper myHelper = new MyHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1976d2"));
        }

        getSupportActionBar().setTitle("Portfolio Lists");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        errorE = (TextView) findViewById(R.id.errorE);
        mydb = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        progressView = (ProgressView) findViewById(R.id.progressview);
        //progressView.setVisibility(View.VISIBLE);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

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
        fetchCategory();
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

    public void fetchCategory() {
        progressView.setVisibility(View.VISIBLE);
        errorE.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryList.this);
        if (refreshLayout.isRefreshing()) {
            progressView.setVisibility(View.GONE);
        }
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressView.setVisibility(View.GONE);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                    AlertDialogClass.displaySnackBar(CategoryList.this, "Data Updated Successfully", R.color.bluePrimary);
                }
                Log.e("Response", response);
                final List<CategoryInfo> categoryinfo = new ArrayList<>();
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
                            CategoryInfo info = new CategoryInfo();
                            JSONObject finalObject = parentArray.getJSONObject(i);
                            info.setId(finalObject.getString("id"));
                            info.setCategory(finalObject.getString("category"));
                            categoryinfo.add(info);
                        }
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        errorE.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
                CategoryAdapter adapter = new CategoryAdapter(CategoryList.this, (ArrayList<CategoryInfo>) categoryinfo);

                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoryList.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(CategoryList.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.e("Single", "Click");
                        CategoryInfo info = categoryinfo.get(position);
                        int id = Integer.parseInt(info.getId());
                        Intent intent = new Intent(CategoryList.this, PortfolioList.class);
                        intent.putExtra("position", id);
                        startActivity(intent);
                        Log.e("CategoryInfo", String.valueOf(categoryinfo));
                        Log.e("CategoryId", String.valueOf(id));
                        Log.e("CategoryIdV", info.category);
                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {
                        Log.e("Double", "Click");
                        CategoryInfo info = categoryinfo.get(position);
                        int id = Integer.parseInt(info.getId());
                        String category = info.getCategory();
                        DialogOptionListCategory dialog = new DialogOptionListCategory();
                        YoYo.with(Techniques.Pulse).duration(500);
                        Bundle args = new Bundle();
                        args.putInt("position", id);
                        args.putString("category", category);
                        args.putString("intent", "activity");
                        dialog.setArguments(args);
                        dialog.show(getFragmentManager(), "Dialog_Option_List");
                    }
                }));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errorE) {
                if (myHelper.isNetworkAvailable(CategoryList.this)) {
                    Toast.makeText(CategoryList.this, "Server Error.. !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryList.this, "Something went wrong. Please check your internet connection and try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                String deviceId = myHelper.getImei(CategoryList.this);
                String uid = myHelper.getUID(CategoryList.this);
                Log.e("deviceId", deviceId);
                Log.e("uID", uid);
//                hashMap.put("deviceId", deviceId);
                hashMap.put("uid", uid);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    public void Refresh() {
        Log.e("Refresch list", "list");
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            AlertDialogClass.displaySnackBar(this, "Data Updated Successfully", R.color.bluePrimary);
        }
        count = mydb.selectCategoryInfo().size();
        Log.e("Counted", String.valueOf(count));
        if (count > 0) {
            recyclerView.setHasFixedSize(true);
            CategoryAdapter adapter = new CategoryAdapter(this, mydb.selectCategoryInfo());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            errorE.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
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
        Log.e("Counted", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Counted", "Resume");
        fetchCategory();
    }

    @Override
    public void onRefresh() {
        fetchCategory();
    }
}
