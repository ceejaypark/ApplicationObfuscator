package com.woop.tryreverseengineerthis.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

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

        Handler handler = new Handler();
        int delay = 300000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                

                if(!isValid())
                    return;
                else
                    return;
            }
        }, delay);
    }

    private boolean isValid(){
        //Check build's fingerprint
        Boolean checkFingerprint =  Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown");

        //Check build's model
        Boolean checkModel =        Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86");

        //Check Manurfacturer for Genymotion (an emulation software)
        Boolean checkManufacturer = Build.MANUFACTURER.contains("Genymotion") ||
                Build.MANUFACTURER.contains("unknown");

        //Check build's product
        Boolean checkProduct = "google_sdk".equals(Build.PRODUCT) ||
                "sdk".equals(Build.PRODUCT) ||
                "sdk_x86".equals(Build.PRODUCT) ||
                "vbox86p".equals(Build.PRODUCT) ||
                Build.PRODUCT.matches(".*_?sdk_?.*");

        //Check for goldfish hardware
        Boolean checkHardware = (Build.HARDWARE).contains("goldfish") ||
                (Build.HARDWARE).contains("vbox86");

        //Return if any is detected
        if(checkFingerprint || checkModel || checkManufacturer || checkProduct || checkHardware)
            return false;

        //Check for telephone operator
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telephonyManager.getNetworkOperator();

        //Return if it is android
        if("Android".equals(operator))
            return false;

        //Return if debugger is connected
        if(Debug.isDebuggerConnected())
            return false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Return if internet is not connected
        if(networkInfo == null || !(networkInfo.isConnected()))
            return false;



        return true;
    }

}
