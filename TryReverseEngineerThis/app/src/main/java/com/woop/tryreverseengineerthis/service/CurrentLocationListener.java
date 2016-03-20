package com.woop.tryreverseengineerthis.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.woop.tryreverseengineerthis.storage.LocationStorage;

/**
 * Created by Jay on 3/19/2016.
 */
public class CurrentLocationListener implements LocationListener {

    private static final String TAG = "CurrentLocationListener";

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Longitude: " + location.getLongitude() + "Latitude: " + location.getLatitude());
        LocationStorage.setLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        LocationStorage.enableLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        LocationStorage.disableLocation();
    }
}
