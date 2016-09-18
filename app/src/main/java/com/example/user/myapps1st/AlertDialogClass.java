package com.example.user.myapps1st;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;

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
}