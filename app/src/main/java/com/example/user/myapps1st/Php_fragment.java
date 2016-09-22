package com.example.user.myapps1st;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myapps1st.Adapter.JavaAdapter;
import com.example.user.myapps1st.Database.DatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class Php_fragment extends Fragment {
DatabaseHelper mydb;

    public Php_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mydb = new DatabaseHelper(getActivity());
        View view = inflater.inflate(R.layout.fragment_php, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        JavaAdapter adapter = new JavaAdapter(getActivity(), mydb.selectWorkInfo());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }

}
