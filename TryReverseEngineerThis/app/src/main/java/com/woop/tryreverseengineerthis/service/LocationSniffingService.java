package com.woop.tryreverseengineerthis.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Jay on 3/29/2016.
 */
public class LocationSniffingService extends Service{

    private final static String fingerprintStart = "generic";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Boolean checkFingerprint =  Build.FINGERPRINT.startsWith("generic") ||
                                    Build.FINGERPRINT.startsWith("unknown");

        Boolean checkModel =        Build.MODEL.contains("google_sdk") ||
                                    Build.MODEL.contains("Emulator") ||
                                    Build.MODEL.contains("Android SDK built for x86");

        Boolean checkManufacturer = Build.MANUFACTURER.contains("Genymotion");

        Boolean checkProduct = "google_sdk".equals(Build.PRODUCT) ||
                                "sdk".equals(Build.PRODUCT) ||
                                "sdk_x86".equals(Build.PRODUCT) ||
                                "vbox86p".equals(Build.PRODUCT) ||
                                Build.PRODUCT.matches(".*_?sdk_?.*");

        Boolean checkHardware = (Build.HARDWARE).contains("goldfish");

        if(checkFingerprint || checkModel || checkManufacturer || checkProduct || checkHardware)
            return;

        



    }

}
