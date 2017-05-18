package com.example.user.myapps1st.Education;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.rey.material.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 5/11/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogOptionListEducation extends DialogFragment {
    LayoutInflater inflater;
    View view;
    Button edit, delete;
    DatabaseHelper mydb;
    String position;
    int editId, deleteId, detailId, id;
    Activity activity;
    MyHelper myHelper = new MyHelper(getActivity());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_popupoptionlist_e, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        YoYo.with(Techniques.ZoomInDown).duration(600).playOn(view);
        edit = (Button) view.findViewById(R.id.edit);
        delete = (Button) view.findViewById(R.id.delete);
        final String intent = getArguments().getString("intent");
        mydb = new DatabaseHelper(getActivity());

        final int id = getArguments().getInt("position");
        final String college = getArguments().getString("college");
        final String university = getArguments().getString("university");
        final String degree = getArguments().getString("degree");
        final String marks = getArguments().getString("marks");
        final String startDate = getArguments().getString("startDate");
        final String endDate = getArguments().getString("endDate");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogEducationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("college", college);
                intent.putExtra("university", university);
                intent.putExtra("degree", degree);
                intent.putExtra("marks", marks);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                Log.e("Editid", String.valueOf(id));
                startActivity(intent);
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Log.e("ID", String.valueOf(id));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure, You want to delete.");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEducation(id);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                builder.show();
                dismiss();
            }
        });
        return builder.create();

    }

    public void deleteEducation(final int id) {
        final EducationList educationList = (EducationList) activity;
        final RequestQueue requestQueue = Volley.newRequestQueue(educationList);
        StringRequest request = new StringRequest(Request.Method.POST, Constants.deleteEducation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject parentObject = new JSONObject(response);
                    int status = Integer.parseInt(parentObject.getString("status"));
                    if (status == 0) {
                        educationList.fetchEducation();
                        //AlertDialogClass.displaySnackBar(educationList, "Data Deleted Successfully", R.color.purplePrimary);
                        dismiss();
                    } else {
                        AlertDialogClass.displaySnackBar(educationList, "Something went wrong. Please try again later.", R.color.purplePrimary);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //info = gson.fromJson(response, UserInfo.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errorE) {
                if (myHelper.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "Server Error.. !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong. Please check your internet connection and try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                Log.e("id", String.valueOf(id));
                hashMap.put("id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
