package com.example.user.myapps1st;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

/**
 * Created by Suraj on 12/10/2016.
 */
public class MyHelper {
    Context context;
    SharedPreferences prefs;

    public MyHelper(Context ctx){
        this.context= ctx;
    }

    public String getImei(){
        TelephonyManager telephonyManager = null;
        boolean result = Permission.Utility.checkPermission(context, Manifest.permission.READ_PHONE_STATE, 500, "PHONE STATE permission is necessary");
        if (result) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            SharedPreferences sharedPreferences = context.getSharedPreferences("Imei",context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("imei", telephonyManager.getDeviceId());
            editor.commit();
        }
        return telephonyManager.getDeviceId();
    }
}
