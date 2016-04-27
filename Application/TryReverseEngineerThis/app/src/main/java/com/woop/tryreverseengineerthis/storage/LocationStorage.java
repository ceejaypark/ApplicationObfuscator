package com.woop.tryreverseengineerthis.storage;

import android.location.Location;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing the GPS Updated Data
 *
 * Created by Jay on 3/19/2016.
 */
public class LocationStorage {
    private static List<Location> history = new ArrayList<>();
    private static Location last = new Location("");
    private static Object lock = new Object();
    public static Location getLocation(){
        return last;
    }

    public static List<Location> getAllLocation(){
        synchronized (lock){
            Log.d("LocationStorage", "Getting all locations to send");
            List<Location> temp = history;
            history.clear();
            return temp;
        }
    }

    public static void setLocation(Location newLocation){
        synchronized (lock) {
            Log.d("LocationStorage", "Updated");
            Log.d("LocationStorage", history.size() + "");
            history.add(newLocation);
            last = newLocation;
        }
    }

    public static void disableLocation(){

    }

    public static void enableLocation(){

    }
}
