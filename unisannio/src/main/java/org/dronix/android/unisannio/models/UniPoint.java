package org.dronix.android.unisannio.models;

import com.google.android.gms.maps.model.LatLng;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class UniPoint {

    private String mFaculty;

    private String mName;

    private String mAddress;

    private double mLat;

    private double mLng;

    private LatLng mGeopoint;

    public UniPoint(String namefaculty, String name, String address, double lat, double lng) {
        mFaculty = namefaculty;
        mName = name;
        mAddress = address;
        mLat = lat;
        mLng = lng;
        mGeopoint = new LatLng((int) (lat * 1E6), (int) (lng * 1E6));
    }
}
