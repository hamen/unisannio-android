package org.dronix.android.unisannio.fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.UniApp;
import org.dronix.android.unisannio.models.UniPoint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

public class MapFragment extends Fragment {

    private static View view;

    private static GoogleMap mMap;

    private static Double latitude, longitude;


    /**
     * ** Sets up the map if it is possible to do so ****
     */
    public static void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) UniApp.getActivity().getSupportFragmentManager().findFragmentById(R.id.location_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. <p> This should only be called once and when we are sure that
     * {@link #mMap} is not null.
     */
    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
    }

    public static Fragment newInstance(List<UniPoint> markers) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MARKERS", (java.util.ArrayList<? extends android.os.Parcelable>) markers);

        MapFragment fragment = new MapFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = (RelativeLayout) inflater.inflate(R.layout.fragment_maps, container, false);
        // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
        latitude = 26.78;
        longitude = 72.56;

        setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mMap != null) {
            setUpMap();
        }

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) UniApp.getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.location_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * * The mapfragment's id must be removed from the FragmentManager *** or else if the same it is passed on the next time then *** app will crash
     * ***
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            UniApp.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(UniApp.getActivity().getSupportFragmentManager().findFragmentById(R.id.location_map))
                    .commit();
            mMap = null;
        }
    }
}