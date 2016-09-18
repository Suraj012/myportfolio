package com.example.user.myapps1st.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myapps1st.R;

/**
 * Created by Suraj on 6/17/2016.
 */
public class ExperienceHomeAdapter extends RecyclerView.Adapter<ExperienceHomeAdapter.ViewHolder> {
    @Override
    public ExperienceHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_about1, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id = position;
        Log.e("Position", String.valueOf(holder.id));
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        int id;
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
