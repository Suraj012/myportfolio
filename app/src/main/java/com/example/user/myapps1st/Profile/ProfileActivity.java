package com.example.user.myapps1st.Profile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ProfileInfo;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

/**
 * Created by Suraj on 7/6/2016.
 */
public class ProfileActivity extends AppCompatActivity{
    Button add, cancel;
    EditText name, designation, facebook, google, twitter, instagram, description,websites;
    DatabaseHelper mydb;
    TextInputLayout nameLayout, designationLayout, descriptionLayout, facebookLayout, googleLayout, twitterLayout, instagramLayout,websitesLayout;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#303f9f"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit profile");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));


        mydb = new DatabaseHelper(ProfileActivity.this);
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.name);
        designation = (EditText) findViewById(R.id.designation);
        facebook = (EditText) findViewById(R.id.facebook);
        google = (EditText) findViewById(R.id.google);
        twitter = (EditText) findViewById(R.id.twitter);
        instagram = (EditText) findViewById(R.id.instagram);
        description = (EditText) findViewById(R.id.description);
        websites = (EditText) findViewById(R.id.websites);

        nameLayout = (TextInputLayout) findViewById(R.id.nameLayout);
        designationLayout = (TextInputLayout) findViewById(R.id.designationLayout);
        descriptionLayout = (TextInputLayout) findViewById(R.id.descriptionLayout);
        facebookLayout = (TextInputLayout) findViewById(R.id.facebookLayout);
        googleLayout = (TextInputLayout) findViewById(R.id.googleLayout);
        twitterLayout = (TextInputLayout) findViewById(R.id.twitterLayout);
        instagramLayout = (TextInputLayout) findViewById(R.id.instagramLayout);
        websitesLayout = (TextInputLayout) findViewById(R.id.websitesLayout);

        id = getIntent().getIntExtra("id", 0);

        if (id != 0) {
            ProfileInfo info = mydb.getProfileInfo(id + "");
            add.setText("Update");
            name.setText(info.name);
            designation.setText(info.designation);
            description.setText(info.description);
            facebook.setText(info.facebook);
            google.setText(info.google);
            twitter.setText(info.twitter);
            instagram.setText(info.instagram);
            websites.setText(info.websites);
        }

        AddData();
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }

    public void AddData() {

        add.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View v) {
                                       // TODO Auto-generated method stub
                                       String namevalue = name.getText().toString();
                                       String designationvalue = designation.getText().toString();
                                       String facebookvalue = facebook.getText().toString();
                                       String googlevalue = google.getText().toString();
                                       String twittervalue = twitter.getText().toString();
                                       String instagramvalue = instagram.getText().toString();
                                       String descriptionvalue = description.getText().toString();
                                       String websitesvalue = websites.getText().toString();
                                       if (namevalue.isEmpty() || designationvalue.isEmpty() || facebookvalue.isEmpty() || googlevalue.isEmpty() || twittervalue.isEmpty() || instagramvalue.isEmpty() || descriptionvalue.isEmpty()) {
                                           nameLayout.setError("Enter name");
                                           designationLayout.setError("Enter designation");
                                           descriptionLayout.setError("Enter description");
                                           facebookLayout.setError("Enter facebook url");
                                           googleLayout.setError("Enter google url");
                                           twitterLayout.setError("Enter twitter url");
                                           instagramLayout.setError("Enter instagram url");
                                       } else {
                                           if (id == 0) {
                                               boolean insert = mydb.insertProfileInfo(namevalue, designationvalue, descriptionvalue, facebookvalue, googlevalue, twittervalue, instagramvalue,websitesvalue);
                                               if (insert == true) {
                                                  // Toast.makeText(ProfileActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                                                   finish();
//                                                   Home_fragment1 fragment = (Home_fragment1) getFragmentManager().findFragmentById(R.id.home);
//                                                   fragment.populateUserList();
                                               } else
                                                   Toast.makeText(ProfileActivity.this, "Something went wrong...!", Toast.LENGTH_LONG).show();
                                           } else {
                                               boolean insert = mydb.editProfileInfo(String.valueOf(id), namevalue, designationvalue, descriptionvalue, facebookvalue, googlevalue, twittervalue, instagramvalue,websitesvalue);
                                               if (insert == true) {
                                                   //LinearLayout layout = (LinearLayout) findViewById(R.id.home);
                                                   //Toast.makeText(ProfileActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
//                                                   Snackbar snackbar = Snackbar.make(layout, "Succefull", Snackbar.LENGTH_LONG);
//                                                   View snackBarView = snackbar.getView();
//                                                   snackBarView.setBackgroundColor(getResources().getColor(R.color.green_complete));
//                                                   TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//                                                   textView.setTextColor(getResources().getColor(R.color.white));
//                                                   textView.setTextSize(15);
//                                                   snackbar.show();
                                                   finish();
                                               } else
                                                   Toast.makeText(ProfileActivity.this, "Something went wrong...!", Toast.LENGTH_LONG).show();
                                           }

                                       }
                                   }
                               }

        );
    }

}
