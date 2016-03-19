package com.woop.tryreverseengineerthis.listener;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jay on 3/19/2016.
 */
public class ClassLocationListener implements LocationListener {

    private static final String TAG = "ClassLocationListener";

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");
        Log.d(TAG, "Longitude: " + location.getLongitude() + "Latitude: " + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
