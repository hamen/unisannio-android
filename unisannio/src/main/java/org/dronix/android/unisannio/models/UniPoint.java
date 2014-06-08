package org.dronix.android.unisannio.models;

import com.google.android.gms.maps.model.LatLng;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class UniPoint implements Parcelable {

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
        mGeopoint = new LatLng(lat, lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFaculty);
        dest.writeString(this.mName);
        dest.writeString(this.mAddress);
        dest.writeDouble(this.mLat);
        dest.writeDouble(this.mLng);
        dest.writeParcelable(this.mGeopoint, flags);
    }

    private UniPoint(Parcel in) {
        this.mFaculty = in.readString();
        this.mName = in.readString();
        this.mAddress = in.readString();
        this.mLat = in.readDouble();
        this.mLng = in.readDouble();
        this.mGeopoint = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static Parcelable.Creator<UniPoint> CREATOR = new Parcelable.Creator<UniPoint>() {
        public UniPoint createFromParcel(Parcel source) {
            return new UniPoint(source);
        }

        public UniPoint[] newArray(int size) {
            return new UniPoint[size];
        }
    };
}
