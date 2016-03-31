package com.woop.tryreverseengineerthis.storage;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by Jay on 3/19/2016.
 */
public class LocationStorage {

    private static final String TAG = "LocationStorage";

    private static List<Location> history = new ArrayList<>();
    private static Location last = new Location("");
    private static boolean locationEnabled = true;

    private static Object lock = new Object();

    public static Location getLocation(){
        return last;
    }

    public static List<Location> getAllLocation(){
        synchronized (lock){
            Log.d(TAG, "Getting all locations to send");
            List<Location> temp = history;
            history.clear();
            return temp;
        }
    }

    public static void setLocation(Location newLocation){
        synchronized (lock) {
            Log.d(TAG, "Updated");
            Log.d(TAG, history.size() + "");
            history.add(newLocation);
            last = newLocation;
        }
    }

    public static void disableLocation(){
        locationEnabled = false;
    }

    public static void enableLocation(){
        locationEnabled = true;
    }
}
