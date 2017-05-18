package com.example.user.myapps1st;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.example.user.myapps1st.Profile.ProfileActivity;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_fragment1 extends Fragment {
    ImageButton facebook, google, twitter, instagram;
    Button add;
    DatabaseHelper mydb;
    TextView name, designation, description, websites, error;
    LinearLayout download, share;
    ImageView profile;
    String facebookUrl, googleUrl, twitterUrl, instagramUrl;
    TextInputLayout nameLayout, designationLayout, descriptionLayout, facebookLayout, googleLayout, twitterLayout, instagramLayout;
    ProgressView progress;
    MyHelper myHelper = new MyHelper(getActivity());

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
        websites = (TextView) view.findViewById(R.id.websites);
        add = (Button) view.findViewById(R.id.add);
        name = (TextView) view.findViewById(R.id.name);
        designation = (TextView) view.findViewById(R.id.designation);
        description = (TextView) view.findViewById(R.id.description);
        error = (TextView) view.findViewById(R.id.error);
        progress = (ProgressView) view.findViewById(R.id.progressview);
        progress.setVisibility(View.VISIBLE);
        mydb = new DatabaseHelper(getActivity());
        int count = mydb.selectProfileInfo().size();
        Log.e("count", String.valueOf(count));
        getProfile();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("title", "Facebook");
                    intent.putExtra("url", "https://www.facebook.com/" + facebookUrl);
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
                    intent.putExtra("url", "https://www.twitter.com/" + twitterUrl);
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
                    intent.putExtra("url", "https://www.instagram.com/" + instagramUrl);
                    startActivity(intent);
                }
            });
        return view;
    }
    private void getProfile() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getProfile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progress.setVisibility(View.GONE);
                Log.e("Response", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equalsIgnoreCase(String.valueOf(0))) {
                        JSONArray finalArray = object.getJSONArray("data");
                        for (int i = 0; i < finalArray.length(); i++) {
                            JSONObject finalObject = finalArray.getJSONObject(i);
                            String nameValue = finalObject.getString("name");
                            String designationValue = finalObject.getString("designation");
                            String descriptionValue = finalObject.getString("description");
                            final String websitesValue = finalObject.getString("websites");
                            String facebookValue = finalObject.getString("facebook");
                            String googleValue = finalObject.getString("google");
                            String twitterValue = finalObject.getString("twitter");
                            String instagramValue = finalObject.getString("instagram");
                            String image = finalObject.getString("image");
                            Bitmap bitmapImage = BitmapFactory.decodeFile(image);
                            //for decoding blob image
//                            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
//                            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profile.setImageBitmap(bitmapImage);
                            if(profile.getTag() == null){
                                profile.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
                            }
                            facebookUrl = facebookValue;
                            googleUrl = googleValue;
                            twitterUrl = twitterValue;
                            instagramUrl = instagramValue;
                            name.setText(nameValue);
                            description.setText(descriptionValue);
                            designation.setText(designationValue);
                            websites.setText(websitesValue);
                            websites.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), WebActivity.class);
                                    intent.putExtra("title", "Websites");
                                    intent.putExtra("url", "http://" + websitesValue);
                                    startActivity(intent);
                                }
                            });
                            profile.setVisibility(View.VISIBLE);
                            name.setVisibility(View.VISIBLE);
                            designation.setVisibility(View.VISIBLE);
                            description.setVisibility(View.VISIBLE);
                            websites.setVisibility(View.VISIBLE);
                            share.setVisibility(View.VISIBLE);
                            download.setVisibility(View.VISIBLE);
                            error.setVisibility(View.GONE);
                            add.setVisibility(View.GONE);
                        }
                    }else{
                        error.setVisibility(View.VISIBLE);
                        add.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errore) {
                progress.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                designation.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                websites.setVisibility(View.GONE);
                share.setVisibility(View.GONE);
                download.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
                if (myHelper.isNetworkAvailable(getActivity())) {
                    error.setText("Server Error. Try again later.. !");
                    Toast.makeText(getActivity(), "Server Error..!", Toast.LENGTH_SHORT).show();
                } else {
                    error.setText("No Internet Connection.. !");
                    Toast.makeText(getActivity(), "Something went wrong. Please check your internet and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String uid = myHelper.getUID(getActivity());
                String deviceId = myHelper.getImei(getActivity());
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("uid", uid);
                hashMap.put("deviceId", deviceId);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }

    public void createandDisplayPdf(String text) {

        Document doc = new Document();

        try {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", getActivity().MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "Default").toString();
            Log.e("Usernama", username);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyPortfolio";
            Log.e("Path", path);

            File dir = new File(path);
            if (!dir.exists()) {
                Log.e("abc", "abcHome");
                dir.mkdirs();
            }

            File file = new File(dir, "myportfolio.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            //Font paraFont= new Font(Font.NORMAL);
            p1.setAlignment(Paragraph.ALIGN_CENTER);

            //p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        viewPdf("myportfolio.pdf", "MyPortfolio");
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
