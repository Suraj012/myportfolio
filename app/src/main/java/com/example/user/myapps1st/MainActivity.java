package com.example.user.myapps1st;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.Portfolio.DialogWorkActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int TIME_INTERVAL = 3000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    ImageButton home, contact, work, profile;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog mProgressDialog;
    ImageView image;
    TextView name;
    String username, photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frame, new Home_fragment1()).commit();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_sign

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        home = (ImageButton) findViewById(R.id.home);
        home.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        profile = (ImageButton) findViewById(R.id.profile);
        work = (ImageButton) findViewById(R.id.work);
        contact = (ImageButton) findViewById(R.id.contact);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frame, new Home_fragment1()).commit();

                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Home_fragment()).addToBackStack(null).commit();
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setTitle("Home");
                home.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                profile.setBackgroundColor(getResources().getColor(R.color.purplePrimaryD));
                work.setBackgroundColor(getResources().getColor(R.color.bluePrimaryD));
                contact.setBackgroundColor(getResources().getColor(R.color.orangePrimaryD));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frame, new About_fragment(), "about").commit();

                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, new About_fragment()).addToBackStack(null).commit();
                toolbar.setBackgroundColor(getResources().getColor(R.color.purplePrimary));
                toolbar.setTitle("Profile");
                profile.setBackgroundColor(getResources().getColor(R.color.purplePrimaryDark));
                home.setBackgroundColor(getResources().getColor(R.color.colorPrimaryD));
                work.setBackgroundColor(getResources().getColor(R.color.bluePrimaryD));
                contact.setBackgroundColor(getResources().getColor(R.color.orangePrimaryD));

            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frame, new Work_fragment()).commit();

                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Work_fragment()).addToBackStack(null).commit();
                toolbar.setBackgroundColor(getResources().getColor(R.color.bluePrimary));
                toolbar.setTitle("Portfolio");
                home.setBackgroundColor(getResources().getColor(R.color.colorPrimaryD));
                profile.setBackgroundColor(getResources().getColor(R.color.purplePrimaryD));
                work.setBackgroundColor(getResources().getColor(R.color.bluePrimaryDark));
                contact.setBackgroundColor(getResources().getColor(R.color.orangePrimaryD));

            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frame, new Contact_fragment()).commit();

                //getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Contact_fragment()).addToBackStack(null).commit();
                toolbar.setBackgroundColor(getResources().getColor(R.color.orangePrimary));
                toolbar.setTitle("Contact");
                home.setBackgroundColor(getResources().getColor(R.color.colorPrimaryD));
                profile.setBackgroundColor(getResources().getColor(R.color.purplePrimaryD));
                work.setBackgroundColor(getResources().getColor(R.color.bluePrimaryD));
                contact.setBackgroundColor(getResources().getColor(R.color.orangePrimaryDark));
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        image = (ImageView) header.findViewById(R.id.image);
        name = (TextView) header.findViewById(R.id.name);

        username = getIntent().getStringExtra("name");
        photo = getIntent().getStringExtra("photo");
        name.setText(username);
        Picasso.with(this).load(photo).fit().into(image);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            moveTaskToBack(true);
        } else {
            Toast.makeText(getBaseContext(), "Tap back button once more to exit", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.editProfile) {

            MyDialogOption dialog = new MyDialogOption();
            YoYo.with(Techniques.Pulse).duration(500);
            dialog.show((MainActivity.this).getFragmentManager(), "Dialog_Option");
        }
        if (id == R.id.message) {

            Intent intent = new Intent(MainActivity.this, DialogWorkActivity.class);
            startActivity(intent);
        }


        if (id == R.id.signOut) {
            signOut();
            SharedPreferences sharedPreferences = (MainActivity.this).getSharedPreferences("LoginPreferences", MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
            finish();
            LoginActivity.email.setText("");
            LoginActivity.password.setText("");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Connected", String.valueOf(mGoogleApiClient.isConnected()));

    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("Login", "onConnectionFailed:" + connectionResult);
    }

}
