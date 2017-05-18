package com.example.user.myapps1st;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.myapps1st.Contact.ContactActivity;
import com.example.user.myapps1st.Database.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_fragment extends Fragment {
    TextView location, contact, email, address, city, country, phone, primaryEmail, secondaryEmail, error;
    Button add;
    DatabaseHelper mydb;
    LinearLayout map, info;
    MapView mapView;
    GoogleMap googleMap;
    private RequestQueue requestQueue;
    private StringRequest request;
    ProgressView progress;
    MyHelper myHelper = new MyHelper(getActivity());


    public Contact_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        location = (TextView) view.findViewById(R.id.location);
        contact = (TextView) view.findViewById(R.id.contact);
        email = (TextView) view.findViewById(R.id.email);
        address = (TextView) view.findViewById(R.id.address);
        city = (TextView) view.findViewById(R.id.city);
        country = (TextView) view.findViewById(R.id.country);
        phone = (TextView) view.findViewById(R.id.phone);
        primaryEmail = (TextView) view.findViewById(R.id.primaryEmail);
        secondaryEmail = (TextView) view.findViewById(R.id.secondaryEmail);
        error = (TextView) view.findViewById(R.id.error);
        add = (Button) view.findViewById(R.id.add);
        map = (LinearLayout) view.findViewById(R.id.map);
        info = (LinearLayout) view.findViewById(R.id.info);
        mapView = (MapView) view.findViewById(R.id.mapView);
        progress = (ProgressView) view.findViewById(R.id.progressview);
        progress.setVisibility(View.VISIBLE);
        mapView.onCreate(savedInstanceState);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactActivity.class);
                startActivity(intent);
            }
        });

       getContact();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        getContact();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            Toast.makeText(getActivity(),"Granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"Not Granted", Toast.LENGTH_SHORT).show();

        }

    }
    private void getContact() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, Constants.getContact, new Response.Listener<String>() {

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
                            String addressValue = finalObject.getString("address");
                            String cityValue = finalObject.getString("city");
                            String countryValue = finalObject.getString("country");
                            Double latitudeValue = Double.valueOf(finalObject.getString("latitude"));
                            Double longitudeValue = Double.valueOf(finalObject.getString("longitude"));
                            String phoneValue = finalObject.getString("phone");
                            String primaryEmailValue = finalObject.getString("primaryEmail");
                            String secondaryEmailValue = finalObject.getString("secondaryEmail");

                            address.setText(addressValue + ", ");
                            city.setText(cityValue + ", ");
                            country.setText(countryValue);
                            phone.setText(phoneValue);
                            primaryEmail.setText(primaryEmailValue);
                            secondaryEmail.setText(secondaryEmailValue);

                            map.setVisibility(View.VISIBLE);
                            info.setVisibility(View.VISIBLE);
                            error.setVisibility(View.GONE);
                            add.setVisibility(View.GONE);

                            boolean result = Permission.Utility.checkPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION, 0, "Access Fine Location permission is necessary");
                            if (result) {

                                //for instant run. important!!
                                mapView.onResume();
                                // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
                                MapsInitializer.initialize(getActivity().getApplicationContext());
                                final LatLng address = new LatLng(latitudeValue, longitudeValue);
                                googleMap = mapView.getMap();
                                googleMap.clear();
                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                                googleMap.getUiSettings().setZoomGesturesEnabled(true);
                                // create marker
                                MarkerOptions marker = new MarkerOptions().position(address).title(addressValue);
                                // Changing marker icon
                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                                // adding marker
                                googleMap.addMarker(marker);
                                googleMap.setMyLocationEnabled(true);
                                // Updates the location and zoom of the MapView
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(latitudeValue, longitudeValue)).zoom(12).build();
                                googleMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));
                            }
                        }
                    }else{
                        error.setVisibility(View.VISIBLE);
                        add.setVisibility(View.VISIBLE);
                        map.setVisibility(View.GONE);
                        info.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errore) {
                progress.setVisibility(View.GONE);
                map.setVisibility(View.GONE);
                info.setVisibility(View.GONE);
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
}
