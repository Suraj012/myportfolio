package com.example.user.myapps1st;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Suraj on 12/10/2016.
 */
public class MyHelper {
    Context context;

    public MyHelper(Context ctx) {
        this.context = ctx;
    }

    public String getImei(Activity context) {
        TelephonyManager telephonyManager = null;
        boolean result = Permission.Utility.checkPermission(context, Manifest.permission.READ_PHONE_STATE, 500, "PHONE STATE permission is necessary");
        if (result) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            SharedPreferences sharedPreferences = context.getSharedPreferences("Imei", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("imei", telephonyManager.getDeviceId());
            editor.commit();
        }
        return telephonyManager.getDeviceId();
    }
    public String getUsername(Activity context){
        SharedPreferences preferences = context.getSharedPreferences("Login", context.MODE_PRIVATE);
        String username = preferences.getString("username", "Default");
        return username;
    }

    public String getUID(Activity context){
        Log.e("contecxt", String.valueOf(context));
        SharedPreferences preferences = context.getSharedPreferences("Login", context.MODE_PRIVATE);
        String uid = preferences.getString("uid", "Default");
        return uid;
    }

    public String getToken(Activity context){
        SharedPreferences preferences = context.getSharedPreferences("Login", context.MODE_PRIVATE);
        String token = preferences.getString("token", "Default");
        return token;
    }

    //    public String getUsername(){
//        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("username", "Default").toString();
//        Log.e("Usernameaa", username);
//        return username;
//    }
    public boolean isNetworkAvailable(Activity context) {
        Log.e("COntaxa", String.valueOf(context));
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
