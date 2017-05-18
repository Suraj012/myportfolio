package com.example.user.myapps1st;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.rey.material.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity1 extends AppCompatActivity {
    Button register;
    FancyButton login;
    static EditText email;
    static EditText password;
    DatabaseHelper mydb;
    ImageButton showPass, hidePass;
    String emailValue, passwordValue;
    private RequestQueue requestQueue;
    private StringRequest request;
    String deviceId;
    MyHelper myHelper = new MyHelper(this);

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};


    private static final int TIME_INTERVAL = 3000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login1);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        mydb = new DatabaseHelper(LoginActivity1.this);

        login = (FancyButton) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        showPass = (ImageButton) findViewById(R.id.showPass);
        hidePass = (ImageButton) findViewById(R.id.hidePass);

        //phone_state = Permission.Utility.checkPermission(this, Manifest.permission.READ_PHONE_STATE, 101, "Phone state permission is necessary");
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordValue = password.getText().toString();
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                password.setText(passwordValue);
                showPass.setVisibility(View.GONE);
                hidePass.setVisibility(View.VISIBLE);
                password.setSelection(passwordValue.length());
            }
        });

        hidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordValue = password.getText().toString();
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password.setText(passwordValue);
                showPass.setVisibility(View.VISIBLE);
                hidePass.setVisibility(View.GONE);
                password.setSelection(passwordValue.length());
            }
        });

        SharedPreferences sharedPreferences = (LoginActivity1.this).getSharedPreferences("Login", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Default").toString();
        String response = sharedPreferences.getString("response", "Default").toString();
        Log.e("Username", username +"response" +response);

        if (((!username.equalsIgnoreCase("Default")) && (!response.equalsIgnoreCase("Default")))) {
            Intent intent = new Intent(LoginActivity1.this, MainActivity1.class);
            startActivity(intent);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailValue = email.getText().toString();
                passwordValue = password.getText().toString();
                if (emailValue.isEmpty() || passwordValue.isEmpty()) {
                    Toast.makeText(
                            LoginActivity1.this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
                    login.setClickable(true);
                } else {
                    loginAuthenticate();
                }
//                if (emailValue.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
//                    if (mydb.isValidLoggedIn(emailValue, passwordValue)) {
//
//                        SharedPreferences sharedPreferences = (LoginActivity1.this).getSharedPreferences("LoginPreferences", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("email", emailValue);
//                        editor.putString("password", passwordValue);
//                        editor.commit();
//
//                        Toast.makeText(LoginActivity1.this, "Login Successfull", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(LoginActivity1.this, MainActivity.class));
//                    } else {
//                        Toast.makeText(LoginActivity1.this, "Login Unsuccessfull", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity1.this, "Invalid Email", Toast.LENGTH_LONG).show();
//                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity1.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            moveTaskToBack(true);
        } else {
            Toast.makeText(getBaseContext(), "Tap back button once more to exit", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Internet Connection. Do you want to enable ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    private void loginAuthenticate() {
        login.setClickable(false);
        deviceId = myHelper.getImei(LoginActivity1.this);
        requestQueue = Volley.newRequestQueue(LoginActivity1.this);
        request = new StringRequest(Request.Method.POST, Constants.loginAuthenticate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String token = object.getString("token");
                    String uid = object.getString("uid");
                    Log.e("Response", String.valueOf(token.length()));
                    if (token.equalsIgnoreCase(String.valueOf(0))) {
                        Toast.makeText(LoginActivity1.this, "Incorrect Username/Password.. !", Toast.LENGTH_SHORT).show();
                        password.setText(passwordValue);
                        email.setText(emailValue);
                        login.setClickable(true);

                    } else if (token!=null) {
                        SharedPreferences sharedPreferences = (LoginActivity1.this).getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", emailValue);
                        editor.putString("response", token);
                        editor.putString("uid", uid);
                        editor.putInt("lastLogin", (int) new Date().getTime());
                        Log.e("id", uid);
                        editor.commit();
                        Toast.makeText(LoginActivity1.this, "Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity1.this, MainActivity1.class);
                        startActivity(intent);
                        // overridePendingTransition(R.anim.abc_slide_out_top, R.anim.abc_slide_in_bottom);

                    } else {
                        Toast.makeText(LoginActivity1.this, "Incorrect Password.. !", Toast.LENGTH_SHORT).show();
                        email.setText(emailValue);
                        password.setText(passwordValue);
                        login.setClickable(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
                login.setClickable(true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("username", emailValue);
                hashMap.put("password", passwordValue);
                hashMap.put("deviceId", deviceId);
                Log.e("device", deviceId);

                //   Log.e("password", passwordValue);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            deviceId = myHelper.getImei(LoginActivity1.this);
        }
    }
}
