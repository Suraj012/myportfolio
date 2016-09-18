package com.example.user.myapps1st.Portfolio;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.WorkInfo;
import com.example.user.myapps1st.R;

public class WorkDetail extends AppCompatActivity {
    TextView time, title, description, category, technology;
    int position;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
        position = getIntent().getIntExtra("position",0);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        category = (TextView) findViewById(R.id.category);
        technology = (TextView) findViewById(R.id.technology);
        mydb = new DatabaseHelper(this);

        WorkInfo info = mydb.getWorkInfo(position + "");
        if(position!=0){
            title.setText(info.title);
            technology.setText(info.technology);
            category.setText(info.category);
            description.setText(info.description);
        }


        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Collapsing Toolbar..
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(info.title);
       // collapsingToolbarLayout.setCollapsedTitleTextColor(new ColorDrawable(Color.parseColor("#ffffff")));
         //collapsingToolbarLayout.setExpandedTitleColor(0xff00ff00);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.cardview_light_background));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bluePrimary));
                //
    }
}
