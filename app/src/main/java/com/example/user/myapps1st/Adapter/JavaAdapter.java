package com.example.user.myapps1st.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myapps1st.R;

/**
 * Created by Suraj on 6/17/2016.
 */
public class JavaAdapter extends RecyclerView.Adapter<JavaAdapter.ViewHolder> {
    @Override
    public JavaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_java_item, parent, false));
    }

    @Override
    public void onBindViewHolder(JavaAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
