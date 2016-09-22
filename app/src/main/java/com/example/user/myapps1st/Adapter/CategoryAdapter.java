package com.example.user.myapps1st.Adapter;

/**
 * Created by User on 5/30/2016.
 */

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.Portfolio.CategoryList;
import com.example.user.myapps1st.Portfolio.CategoryAddActivity;
import com.example.user.myapps1st.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerSwipeAdapter<CategoryAdapter.SimpleViewHolder> {
    ArrayList<CategoryInfo> data = new ArrayList<>();
    private int id;
    DatabaseHelper mydb;

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView title, count;
        SwipeLayout swipeLayout;

        @TargetApi(Build.VERSION_CODES.M)
        public SimpleViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            count = (TextView) itemView.findViewById(R.id.count);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mydb = new DatabaseHelper(mContext);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (swipeLayout.getOpenStatus() == SwipeLayout.Status.Close) {
                        int position = getLayoutPosition();
                        ArrayList<CategoryInfo> list = mydb.selectCategoryInfo();
                        final CategoryInfo info = list.get(position);
                        int idd = Integer.parseInt(info.id);

                        Intent intent = new Intent(mContext, CategoryAddActivity.class);
                        intent.putExtra("position", idd);
                        mContext.startActivity(intent);
                        Log.e("CategoryId", String.valueOf(idd));
                    } else {
                        //Toast.makeText(view.getContext(),"Something went wrong.Plzz try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private Context mContext;
    AlertDialog.Builder builder;
    View view;


    public CategoryAdapter(Context context, ArrayList<CategoryInfo> info) {
        this.mContext = context;
        this.data = info;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_portfolio_categorylist, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        final CategoryInfo info = data.get(position);
        String c = String.valueOf(position + 1);
        viewHolder.count.setText(c +") ");
        viewHolder.title.setText(info.category);


        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
            }

            @Override
            public void onOpen(final SwipeLayout layout) {
                Log.e("Enable:","enable");

                builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setMessage("Are you sure, You want to delete? Your all the project related to this will be deleted?");
                builder.setView(view).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int idd = Integer.parseInt(info.id);
                        Log.e("delete", String.valueOf(idd));
                        mydb.deleteCategoryInfo(String.valueOf(idd));
                        mydb.deleteWorkInfo(String.valueOf(idd));
                        data.remove(position);
                        notifyItemRemoved(position);
                        CategoryList activity = (CategoryList) mContext;
                        activity.Refresh();
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
        //Log.e("Slkadfjkl;ze", String.valueOf(data.size()));
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
