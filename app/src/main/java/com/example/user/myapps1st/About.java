package com.example.user.myapps1st;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class About extends AppCompatActivity {
    TextView title, version, desc;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About");
        title = (TextView) findViewById(R.id.title);
        version = (TextView) findViewById(R.id.version);
        desc = (TextView) findViewById(R.id.desc);
        image = (ImageView) findViewById(R.id.image);

        YoYo.with(Techniques.RollIn).duration(3000).playOn(image);
        YoYo.with(Techniques.RollIn).duration(4000).playOn(title);
        YoYo.with(Techniques.RollIn).duration(5000).playOn(version);
    }
}
