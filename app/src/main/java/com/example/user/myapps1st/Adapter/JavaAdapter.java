package com.example.user.myapps1st.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.myapps1st.Model.WorkInfo;
import com.example.user.myapps1st.R;

import java.util.ArrayList;

/**
 * Created by Suraj on 6/17/2016.
 */
public class JavaAdapter extends RecyclerView.Adapter<JavaAdapter.ViewHolder> {
    ArrayList<WorkInfo> data = new ArrayList<>();
    private Context mcontext;
    public JavaAdapter(Context context, ArrayList<WorkInfo> workinfo) {
        this.data = workinfo;
        this.mcontext = context;
    }

    @Override
    public JavaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_java_item, parent, false));
    }

    @Override
    public void onBindViewHolder(JavaAdapter.ViewHolder holder, int position) {
       // Log.e("lkajdf","alksdjf");
        WorkInfo info = data.get(position);
        Log.e("Data", String.valueOf(data));
        holder.title.setText(info.cid);
        holder.description.setText(info.description);
        holder.technology.setText(info.technology);
    }

    @Override
    public int getItemCount() {
        //Log.e("SIze", String.valueOf(data.size()));
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description,technology;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            technology = (TextView) itemView.findViewById(R.id.technology);
        }
    }
}
