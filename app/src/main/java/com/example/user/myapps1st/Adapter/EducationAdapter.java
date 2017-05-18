package com.example.user.myapps1st.Adapter;

/**
 * Created by User on 5/30/2016.
 */

import android.annotation.TargetApi;
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

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.user.myapps1st.Model.EducationInfo;
import com.example.user.myapps1st.R;

import java.util.ArrayList;

public class EducationAdapter extends RecyclerSwipeAdapter<EducationAdapter.SimpleViewHolder> {
    ArrayList<EducationInfo> data = new ArrayList<>();
    private int id;

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView degree, college, university, marks, startDate, endDate;
        SwipeLayout swipeLayout;

        @TargetApi(Build.VERSION_CODES.M)
        public SimpleViewHolder(final View itemView) {
            super(itemView);
            degree = (TextView) itemView.findViewById(R.id.degree);
            college = (TextView) itemView.findViewById(R.id.college);
            university = (TextView) itemView.findViewById(R.id.university);
            marks = (TextView) itemView.findViewById(R.id.marks);
            startDate = (TextView) itemView.findViewById(R.id.startDate);
            endDate = (TextView) itemView.findViewById(R.id.endDate);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

        }
    }

    private Context mContext;
    AlertDialog.Builder builder;
    View view;


    public EducationAdapter(Context context, ArrayList<EducationInfo> info) {
        this.mContext = context;
        this.data = info;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_about_educationlist, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        EducationInfo info = data.get(position);
        id = Integer.parseInt(info.id);
        viewHolder.degree.setText(info.degree);
        viewHolder.college.setText(info.college);
        viewHolder.startDate.setText("Start Date: "+info.startDate);
        viewHolder.endDate.setText("End Date: "+info.endDate);
        viewHolder.university.setText(info.university);
        viewHolder.marks.setText(info.marks);


        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
            }

            @Override
            public void onOpen(final SwipeLayout layout) {
                Log.e("Enable:","enable");

                builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setMessage("Are you sure, You want to delete?");
                builder.setView(view).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewHolder.swipeLayout.close();
                        Toast.makeText(layout.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(layout.getContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
                        viewHolder.swipeLayout.close();

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
    public int getItemNumber(){
        return id;
    }

    @Override
    public int getItemCount() {
        Log.e("List", String.valueOf(data.size()));
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
