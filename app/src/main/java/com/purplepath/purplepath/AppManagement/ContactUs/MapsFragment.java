package com.purplepath.purplepath.AppManagement.ContactUs;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

/**
 * @ author: Pratheep.S
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    public String TAG = "spcheck";
    RelativeLayout relative_left_arrow, relative_center_home;
    private OnActivityBackPressedListener mCallBackListener;
    private Double lattitude,longitude;
    String  markerTitle;


    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        mCallBackListener = (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }

    public static MapsFragment newInstance(Double lattitude, Double longitude, String markerTitle) {

        Bundle args = new Bundle();
        args.putDouble("lattitude", lattitude);
        args.putDouble("longitude", longitude);
        args.putString("markerTitle", markerTitle);
        MapsFragment fragment = new MapsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        relative_center_home = view.findViewById(R.id.relative_center_home);
        relative_left_arrow = view.findViewById(R.id.relative_left_arrow);
        mCallBackListener.setActionBarTitle("Contact us");
        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);
        Bundle args=getArguments();
        if(args!=null){
            if(args.containsKey("lattitude")&&args.containsKey("longitude")){
                lattitude=args.getDouble("lattitude");
                longitude=args.getDouble("longitude");
                markerTitle=args.getString("markerTitle");

            }
        }

        setupMap();

        return view;
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.map, mapFragment).commit();
        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady: called");
        LatLng latLng = new LatLng(lattitude,longitude);
        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title(markerTitle));
        float zoomLevel = 15;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;

            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
        }
    }
}
