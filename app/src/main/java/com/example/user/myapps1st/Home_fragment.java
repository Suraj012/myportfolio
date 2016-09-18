package com.example.user.myapps1st;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment extends Fragment {
ImageButton facebook, google, twitter, instagram;

    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        facebook = (ImageButton) view.findViewById(R.id.facebook);
        google = (ImageButton) view.findViewById(R.id.google);
        twitter = (ImageButton) view.findViewById(R.id.twitter);
        instagram = (ImageButton) view.findViewById(R.id.instagram);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/luckyforwhat"));
                startActivity(browserIntent);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/luckyforwhat"));
                startActivity(browserIntent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/surajbhandari17"));
                startActivity(browserIntent);
            }
        });
       instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/_surajb"));
                startActivity(browserIntent);
            }
        });
        return view;
    }

}
