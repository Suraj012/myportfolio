package com.example.user.myapps1st;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.myapps1st.Adapter.ExperienceAdapter;
import com.example.user.myapps1st.Adapter.SkillAdapter;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Experience.DialogExperienceActivity;
import com.example.user.myapps1st.Experience.ExperienceDetail;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.Skill.DialogSkillActivity;
import com.example.user.myapps1st.util.RecyclerItemClickListener;
import com.rey.material.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class About_fragment extends Fragment {
    RecyclerView recyclerView, recyclerViewE;
    ArrayList<SkillInfo> list = new ArrayList<>();
    DatabaseHelper mydb;
    Button addSkill, addExperience;
    TextView errorE, errorS;
    SwipeRefreshLayout refreshLayout;


    public About_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        addSkill = (Button) view.findViewById(R.id.addSkill);
        addExperience = (Button) view.findViewById(R.id.addExperience);
        errorE = (TextView) view.findViewById(R.id.errorE);
        errorS = (TextView) view.findViewById(R.id.errorS);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogSkillActivity.class);
                startActivity(intent);
//                DialogSkill dialog = new DialogSkill();
//                Bundle args = new Bundle();
//                args.putString("enable", enable);
//                dialog.setArguments(args);
//                Toast.makeText(getActivity(), "heloo", Toast.LENGTH_SHORT).show();
//                dialog.show(getFragmentManager(), "Dialog_show");
            }
        });

        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogExperienceActivity.class);
                startActivity(intent);
            }
        });

        mydb = new DatabaseHelper(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
//        recyclerView.setHasFixedSize(true);
//        SkillAdapter adapter = new SkillAdapter(mydb.selectSkillInfo());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);

        recyclerViewE = (RecyclerView) view.findViewById(R.id.recyclerE);
//        recyclerViewE.setHasFixedSize(true);
//        ExperienceAdapter adapter1 = new ExperienceAdapter(mydb.selectExperienceInfo());
//        recyclerViewE.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewE.setAdapter(adapter1);

        return view;
    }

    public void Refresh() {
        if(refreshLayout != null) {

            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
            int countF = mydb.selectSkillInfo().size();
            if (countF > 0) {
                recyclerView.setHasFixedSize(true);
                final SkillAdapter adapter = new SkillAdapter(getActivity(), About_fragment.this, mydb.selectSkillInfo());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
                errorS.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                errorS.setVisibility(View.VISIBLE);
            }

            int countE = mydb.selectExperienceInfo().size();
            if (countE > 0) {
                recyclerViewE.setHasFixedSize(true);
                ExperienceAdapter adapter1 = new ExperienceAdapter(getActivity(), mydb.selectExperienceInfo());
                recyclerViewE.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewE.setAdapter(adapter1);
                errorE.setVisibility(View.GONE);
                recyclerViewE.setVisibility(View.VISIBLE);

                recyclerViewE.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View childView, int position) {
                                ArrayList<ExperienceInfo> list = mydb.selectExperienceInfo();
                                final ExperienceInfo info = list.get(position);
                                int id = Integer.parseInt(info.id);
                                Intent intent = new Intent(getActivity(), ExperienceDetail.class);
                                intent.putExtra("position", id);
                                startActivity(intent);
                                Log.e("EditIDD", String.valueOf(id));
                            }

                            @Override
                            public void onItemLongPress(View childView, int position) {
//                                ArrayList<ExperienceInfo> list = mydb.selectExperienceInfo();
//                                final ExperienceInfo info = list.get(position);
//                                int id = Integer.parseInt(info.id);
//                                DialogOptionListExperience dialog = new DialogOptionListExperience();
//                                YoYo.with(Techniques.Pulse).duration(500);
//                                Bundle args = new Bundle();
//                                args.putInt("position", id);
//                                args.putString("intent","fragment");
//                                dialog.setArguments(args);
//                                dialog.show((getActivity()).getFragmentManager(), "Dialog_Option_List");
                            }
                        }));

            } else {
                errorE.setVisibility(View.VISIBLE);
                recyclerViewE.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Onpause", "Pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Onresume", "Resume");
        Refresh();


//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        ArrayList<SkillInfo> list = mydb.selectSkillInfo();
//                        final SkillInfo info = list.get(position);
//                        int id = Integer.parseInt(info.id);
//                        Toast.makeText(getActivity(),"Item Clicked", Toast.LENGTH_SHORT).show();
//                        Log.e("EditIDD", String.valueOf(id));
//
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        ArrayList<SkillInfo> list = mydb.selectSkillInfo();
//                        final SkillInfo info = list.get(position);
//                        int id = Integer.parseInt(info.id);
//                        DialogOptionListSkill dialog = new DialogOptionListSkill();
//                        YoYo.with(Techniques.Pulse).duration(500);
//                        Bundle args = new Bundle();
//                        args.putInt("position", id);
//                        dialog.setArguments(args);
//                        dialog.show((getActivity()).getFragmentManager(), "Dialog_Option_List");
//                    }
//                })
//        );
    }

}
