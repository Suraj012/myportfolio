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
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.R;

import java.util.ArrayList;

public class ExperienceAdapter extends RecyclerSwipeAdapter<ExperienceAdapter.SimpleViewHolder> {
    ArrayList<ExperienceInfo> data = new ArrayList<>();
    private int id;

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView title, company, dateFrom, dateTo, description;
        SwipeLayout swipeLayout;

        @TargetApi(Build.VERSION_CODES.M)
        public SimpleViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            company = (TextView) itemView.findViewById(R.id.company);
            dateFrom = (TextView) itemView.findViewById(R.id.dateFrom);
            dateTo = (TextView) itemView.findViewById(R.id.dateTo);
            description = (TextView) itemView.findViewById(R.id.description);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);

        }
    }

    private Context mContext;
    AlertDialog.Builder builder;
    View view;


    public ExperienceAdapter(Context context, ArrayList<ExperienceInfo> info) {
        this.mContext = context;
        this.data = info;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_about_experiencelist, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        ExperienceInfo info = data.get(position);
        id = Integer.parseInt(info.id);
        viewHolder.title.setText(info.title);
        viewHolder.company.setText(info.company);
        String[] dateFrom = info.dateFrom.split("-");
        String yearF = dateFrom[0];
        String monthF = dateFrom[1];
        String dayF = dateFrom[2];
        viewHolder.dateFrom.setText(yearF);

        if(info.dateTo.equalsIgnoreCase("current")) {
            viewHolder.dateTo.setText(info.dateTo);
        }else{
            String[] dateTo = info.dateTo.split("-");
            String yearT = dateTo[0];
            String monthT = dateTo[1];
            String dayT = dateTo[2];
            viewHolder.dateTo.setText(yearT);
        }
        viewHolder.description.setText(info.description);


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
