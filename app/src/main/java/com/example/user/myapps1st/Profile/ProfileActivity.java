package com.example.user.myapps1st.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.GoogleAnalytics.AnalyticsApplication;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rey.material.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suraj on 7/6/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    ImageView imageView;

    Button add, cancel, imageButton;
    EditText name, designation, facebook, google, twitter, instagram, description, websites;
    DatabaseHelper mydb;
    private RequestQueue requestQueue;
    private StringRequest request;
    TextInputLayout nameLayout, designationLayout, descriptionLayout, facebookLayout, googleLayout, twitterLayout, instagramLayout, websitesLayout;
    int id;
    MyHelper myHelper = new MyHelper(this);
    private String namevalue, designationvalue, facebookvalue, descriptionvalue, twittervalue, instagramvalue, googlevalue, websitesvalue, imageValue;
    Bitmap thumbnail = null;
    ByteArrayOutputStream bytes;

    AnalyticsApplication application1;

    private Tracker mTracker;
    File destination = null;

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

        imageView = (ImageView) findViewById(R.id.imageview);
        imageButton = (Button) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        application1 = (AnalyticsApplication) getApplicationContext();
        mTracker = application1.getDefaultTracker();

        Log.e("Mainactivity", "main");
        Log.e("Mainactivity name", String.valueOf(name));
        mTracker.setScreenName("Image~" + "Home");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        id = getIntent().getIntExtra("id", 0);
        Log.e("IDD", String.valueOf(id));

        if (id != 0) {
            namevalue = getIntent().getStringExtra("name");
            designationvalue = getIntent().getStringExtra("designation");
            descriptionvalue = getIntent().getStringExtra("description");
            facebookvalue = getIntent().getStringExtra("facebook");
            instagramvalue = getIntent().getStringExtra("instagram");
            twittervalue = getIntent().getStringExtra("twitter");
            googlevalue = getIntent().getStringExtra("google");
            websitesvalue = getIntent().getStringExtra("websites");
            imageValue = getIntent().getStringExtra("image");
            Bitmap bitmapImage = BitmapFactory.decodeFile(imageValue);
            imageView.setImageBitmap(bitmapImage);
            if(imageView.getTag() == null){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
            }

            //ProfileInfo info = mydb.getProfileInfo(id + "");

            add.setText("Update");
            name.setText(namevalue);
            designation.setText(designationvalue);
            description.setText(descriptionvalue);
            facebook.setText(facebookvalue);
            google.setText(googlevalue);
            twitter.setText(twittervalue);
            instagram.setText(instagramvalue);
            websites.setText(websitesvalue);
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
                                       mTracker.send(new HitBuilders.EventBuilder()

                                               .setCategory("Home ")

                                               .setAction("Add Pressed")

                                               .build());
                                       // TODO Auto-generated method stub
                                       namevalue = name.getText().toString();
                                       designationvalue = designation.getText().toString();
                                       facebookvalue = facebook.getText().toString();
                                       googlevalue = google.getText().toString();
                                       twittervalue = twitter.getText().toString();
                                       instagramvalue = instagram.getText().toString();
                                       descriptionvalue = description.getText().toString();
                                       websitesvalue = websites.getText().toString();
                                       if (namevalue.isEmpty() || designationvalue.isEmpty() || facebookvalue.isEmpty() || googlevalue.isEmpty() || twittervalue.isEmpty() || instagramvalue.isEmpty() || descriptionvalue.isEmpty()) {
                                           nameLayout.setError("Enter name");
                                           designationLayout.setError("Enter designation");
                                           descriptionLayout.setError("Enter description");
                                           facebookLayout.setError("Enter facebook url");
                                           googleLayout.setError("Enter google url");
                                           twitterLayout.setError("Enter twitter url");
                                           instagramLayout.setError("Enter instagram url");
                                       } else {
                                           addProfile();
                                       }
                                   }
                               }

        );
    }

    private void addProfile() {
        //for encoding blob image
//        final byte[] profileImage = bytes.toByteArray();
//        final String encodeImage =  Base64.encodeToString(profileImage,Base64.DEFAULT);

        requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        request = new StringRequest(Request.Method.POST, Constants.addProfile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                if (response.trim().equalsIgnoreCase("success")) {
                    Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ProfileActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myHelper.isNetworkAvailable(ProfileActivity.this)) {
                    Toast.makeText(ProfileActivity.this, "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(ProfileActivity.this);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("name", namevalue);
                hashMap.put("designation", designationvalue);
                hashMap.put("description", descriptionvalue);
                hashMap.put("facebook", facebookvalue);
                hashMap.put("google", googlevalue);
                hashMap.put("twitter", twittervalue);
                hashMap.put("instagram", instagramvalue);
                hashMap.put("websites", websitesvalue);
                hashMap.put("image", String.valueOf(destination));

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String directory = "MyPortfolio";
        destination = new File(Environment.getExternalStorageDirectory() + "/" + directory,
                "profile" + System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {

        thumbnail = null;
        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String directory = "MyPortfolio";
                destination = new File(Environment.getExternalStorageDirectory() + "/" + directory,
                        "profile" + System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(thumbnail);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
