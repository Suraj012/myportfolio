package com.example.user.myapps1st;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.myapps1st.Adapter.JavaAdapter;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Portfolio.CategoryAddActivity;
import com.example.user.myapps1st.Portfolio.DialogWorkActivity;
import com.rey.material.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Android_fragment extends Fragment {
    DatabaseHelper mydb;
    View fab, fabc;
    RecyclerView recyclerView;
    TextView error;
    int count;

    public Android_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("Work", "Work");
        mydb = new DatabaseHelper(getContext());
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        error = (TextView) view.findViewById(R.id.error);
        String id = getArguments().getString("cid");

        fab = view.findViewById(R.id.fab);
        fabc = view.findViewById(R.id.fabc);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogWorkActivity.class);
                startActivity(intent);
            }
        });
        fabc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryAddActivity.class);
                startActivity(intent);
            }
        });

        count = mydb.selectWorkInfo2(id).size();
        if(count > 0) {
            error.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);
            JavaAdapter adapter = new JavaAdapter(getActivity(), mydb.selectWorkInfo2(id));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            Log.e("Clicked1", "android");
            recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Clicked", "Clicked");
                    Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            error.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
