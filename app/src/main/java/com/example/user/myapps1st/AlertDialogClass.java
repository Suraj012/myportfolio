package com.example.user.myapps1st;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

/**
 * Created by User on 7/1/2016.
 */
public class AlertDialogClass {
    //ask about library
    public static void displaySnackBar(Context context, String msg, int snackColor) {
        int color = snackColor == 0 ? R.color.green_complete : snackColor;
        SnackbarManager.show(
                com.nispok.snackbar.Snackbar.with(context)
                        .text(msg)
                        .actionLabel("OK")
                        .actionColorResource(R.color.white)
                        .color(ContextCompat.getColor(context, color))
                        .textTypeface(Typeface.create("sans-serif-medium", 0))
                        .type(SnackbarType.MULTI_LINE)
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(com.nispok.snackbar.Snackbar snackbar) {

                            }
                        })
        );
    }

    public static void displayDialog(final Context context, String title, String msg, String positiveText, String negativeText, final String subject) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(msg);
        if (positiveText != null) {
            builder.setCancelable(false);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            if (subject != null && subject.equalsIgnoreCase("logout")) {
                                SharedPreferences sharedPreferences = (context).getSharedPreferences("LoginPreferences", context.MODE_PRIVATE);
                                sharedPreferences.edit().clear().commit();
                                Intent intent = new Intent(context, LoginActivity1.class);
                                context.startActivity(intent);
                                ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                                LoginActivity1.password.setText("");
                                LoginActivity1.email.setText("");
                                //  } else if ("") {
                            }
                        }
                    });
        } else {
            builder.setCancelable(true);
        }
        if (negativeText != null) {

            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (subject != null) {
                        builder.setCancelable(true);
                    }
                }
            });
        }
        builder.create().show();
    }

}