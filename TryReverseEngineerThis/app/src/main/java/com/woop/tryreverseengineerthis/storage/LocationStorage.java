package com.woop.tryreverseengineerthis.storage;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 3/19/2016.
 */
public class LocationStorage {

    private static List<Location> history = new ArrayList<>();
    private static Location last = new Location("");
    private static boolean locationEnabled = false;

    public static Location getLocation(){
        return !locationEnabled ? null : last;
    }

    public static void setLocation(Location newLocation){
        history.add(newLocation);
        last = newLocation;
    }

    public static void disableLocation(){
        locationEnabled = false;
    }

    public static void enableLocation(){
        locationEnabled = true;
    }
}
