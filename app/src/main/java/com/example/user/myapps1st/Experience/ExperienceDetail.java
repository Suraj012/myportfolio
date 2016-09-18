package com.example.user.myapps1st.Experience;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExperienceDetail extends AppCompatActivity {
    TextView time, title, company, dateFrom, dateTo, detail;
    int position;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);
        position = getIntent().getIntExtra("position",0);
        title = (TextView) findViewById(R.id.title);
        company = (TextView) findViewById(R.id.company);
        dateFrom = (TextView) findViewById(R.id.dateFrom);
        dateTo = (TextView) findViewById(R.id.dateTo);
        detail = (TextView) findViewById(R.id.detail);
        mydb = new DatabaseHelper(this);

        ExperienceInfo info = mydb.getExperienceInfo(position + "");
        if(position!=0){
            title.setText(info.title);
            company.setText(info.company);
            dateFrom.setText(info.dateFrom);
            dateTo.setText(info.dateTo);
            detail.setText(info.description);
        }


        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);

        //Collapsing Toolbar..
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(info.title);
       // collapsingToolbarLayout.setCollapsedTitleTextColor(new ColorDrawable(Color.parseColor("#ffffff")));
         //collapsingToolbarLayout.setExpandedTitleColor(0xff00ff00);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673ab7")));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.cardview_light_background));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));


        //Date
        Thread myThread = null;
        Runnable myRunnableThread = new CountDownRunner();
        myThread = new Thread(myRunnableThread);
        myThread.start();
        //
    }

        //Date start
    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    time = (TextView) findViewById(R.id.time);
                    Date dt = new Date();
                    int year = dt.getYear();
                    int month = dt.getMonth();
                    int day = dt.getDay();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    int seconds = dt.getSeconds();
                    String curTime = hours + ":" + minutes + ":" + seconds;
                    try {
                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        final Date dateObj = sdf.parse(curTime);
                        String timeFinal = new SimpleDateFormat("hh:mm:ss a").format(dateObj);
                        time.setText(timeFinal);
                    } catch (final ParseException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000); // Pause of 1 Second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }

    }
    //Date end
}
