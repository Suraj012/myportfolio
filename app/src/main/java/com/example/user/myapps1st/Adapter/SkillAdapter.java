package com.example.user.myapps1st.Adapter;

/**
 * Created by User on 5/30/2016.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.user.myapps1st.About_fragment;
import com.example.user.myapps1st.AlertDialogClass;
import com.example.user.myapps1st.Constants;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.MyHelper;
import com.example.user.myapps1st.R;
import com.example.user.myapps1st.Skill.SkillList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkillAdapter extends RecyclerSwipeAdapter<SkillAdapter.SimpleViewHolder> {
    ArrayList<SkillInfo> data = new ArrayList<>();
    private int id;
    DatabaseHelper mydb;
    MyHelper myHelper;

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView title, rate;
        SwipeLayout swipeLayout;
        RoundCornerProgressBar progressBar;

        @TargetApi(Build.VERSION_CODES.M)
        public SimpleViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            rate = (TextView) itemView.findViewById(R.id.rate);
            progressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.progress);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mydb = new DatabaseHelper(mContext);
        }
    }

    private Context mContext;
    private About_fragment fragment;
    AlertDialog.Builder builder;
    View view;


    public SkillAdapter(Activity context, About_fragment fragment, ArrayList<SkillInfo> info) {
        this.mContext = context;
        this.fragment = fragment;
        this.data = info;
        this.myHelper = new MyHelper(mContext);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_about_skilllist, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        final SkillInfo info = data.get(position);
        viewHolder.title.setText(info.getSkill());
        id = Integer.parseInt(info.getId());
        float rating = (Float.parseFloat(info.getRate())) * 10;
        viewHolder.rate.setText(String.valueOf(rating) + "%");
        viewHolder.progressBar.setProgress(rating);

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
            }

            @Override
            public void onOpen(final SwipeLayout layout) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure, You want to delete.");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                        StringRequest request = new StringRequest(Request.Method.POST, Constants.deleteSkill, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String responses = response.trim();
                                if (responses.equalsIgnoreCase("success")) {
                                    Log.e("Responseskill", response);
                                    AlertDialogClass.displaySnackBar(mContext, "Data Deleted Successfully", R.color.purplePrimary);
                                    viewHolder.swipeLayout.close();
                                    if (fragment != null) {
                                        About_fragment frag = fragment;
                                        frag.onResume();
                                    } else {
                                        SkillList list = (SkillList) mContext;
                                        list.fetchSkill();
                                    }
                                } else {
                                    Log.e("Responseskillelse", response);
                                    AlertDialogClass.displaySnackBar(mContext, "Something went wrong.. !", R.color.purplePrimary);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError errorE) {
                                if (myHelper.isNetworkAvailable((Activity) mContext)) {
                                    Toast.makeText(mContext, "Server Error.. !", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "Something went wrong. Please check your internet connection and try again later", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        ) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("id", String.valueOf(id));
                                return hashMap;
                            }
                        };
                        requestQueue.add(request);
                        dialog.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewHolder.swipeLayout.close();
                        dialog.dismiss();
                    }
                });
                builder.show();
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                // Toast.makeText(layout.getContext(), "Start_closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClose(SwipeLayout layout) {
                // Toast.makeText(layout.getContext(), "closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("Slkadfjkl;ze", String.valueOf(data.size()));
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
