package com.example.user.myapps1st;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Model.ProfileInfo;
import com.example.user.myapps1st.Profile.ProfileActivity;
import com.rey.material.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment1 extends Fragment {
ImageButton facebook, google, twitter, instagram;
    Button add;
    DatabaseHelper mydb;
    TextView name, designation, description, error;
    LinearLayout download,share;
    ImageView profile;
    String facebookUrl, googleUrl, twitterUrl, instagramUrl;
    TextInputLayout nameLayout, designationLayout, descriptionLayout, facebookLayout, googleLayout, twitterLayout, instagramLayout;

    public Home_fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home1, container, false);
        profile = (ImageView) view.findViewById(R.id.profile);
        download = (LinearLayout) view.findViewById(R.id.download);
        share = (LinearLayout) view.findViewById(R.id.share);
        facebook = (ImageButton) view.findViewById(R.id.facebook);
        google = (ImageButton) view.findViewById(R.id.google);
        twitter = (ImageButton) view.findViewById(R.id.twitter);
        instagram = (ImageButton) view.findViewById(R.id.instagram);
        add = (Button) view.findViewById(R.id.add);
        name = (TextView) view.findViewById(R.id.name);
        designation = (TextView) view.findViewById(R.id.designation);
        description = (TextView) view .findViewById(R.id.description);
        error = (TextView) view.findViewById(R.id.error);
        mydb = new DatabaseHelper(getActivity());
        int count = mydb.selectProfileInfo().size();
        Log.e("count", String.valueOf(count));
        if(count > 0) {
            populateUserList();
            profile.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            designation.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            download.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("title", "Facebook");
                    intent.putExtra("url", "https://www.facebook.com/"+facebookUrl);
                    startActivity(intent);
                }
            });
            google.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("title", "Google");
                    intent.putExtra("url", googleUrl);
                    startActivity(intent);
                }
            });
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("title", "Twitter");
                    intent.putExtra("url", "https://www.twitter.com/"+twitterUrl);
                    startActivity(intent);
                }
            });
            instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
//                    startActivity(browserIntent);
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("title", "Instagram");
                    intent.putExtra("url", "https://www.instagram.com/"+instagramUrl);
                    startActivity(intent);
                }
            });
        }else{
            profile.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            designation.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
            download.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
    public void populateUserList(){
    	ArrayList<ProfileInfo> list = mydb.selectProfileInfo();
    	for (int i = 0; i < list.size(); i++) {

    		final ProfileInfo info = list.get(i);

            profile.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            designation.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            download.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
            add.setVisibility(View.GONE);

            facebookUrl = info.facebook;
            googleUrl = info.google;
            twitterUrl = info.twitter;
            instagramUrl = info.instagram;
            name.setText(info.name);
    		designation.setText(info.designation);
            description.setText(info.description);
		}
    }
    @Override
    public void onResume() {
        super.onResume();
        populateUserList();
    }
}
