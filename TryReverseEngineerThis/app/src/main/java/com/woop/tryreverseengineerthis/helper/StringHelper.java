package com.woop.tryreverseengineerthis.helper;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Base64;

import com.woop.tryreverseengineerthis.LandingActivity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.os.Build.FINGERPRINT;
import static android.os.Build.HARDWARE;
import static android.os.Build.MODEL;
import static android.os.Build.PRODUCT;
import static android.os.Build.MANUFACTURER;
import static javax.crypto.Cipher.*;

/**
 * Created by Jay on 4/16/2016.
 */
public class StringHelper {

    private static HashMap<String, String> dynamicHashMap;
    private static HashMap<String, String> staticHashMap;
    private static Cipher cipher;

    private static Object lock;

    private final static String TAG = "StringHelper";

    public static void initialise(Context applicationContext) throws NoSuchPaddingException, NoSuchAlgorithmException {

        lock = new Object();
        dynamicHashMap = new HashMap<>();
        staticHashMap = new HashMap<>();
        cipher = getInstance("AES");

        String f = FINGERPRINT;
        String m = MODEL;
        String b = MANUFACTURER;
        String p = PRODUCT;
        String h = HARDWARE;
        String t = applicationContext.TELEPHONY_SERVICE;
        String c = applicationContext.CONNECTIVITY_SERVICE;
        String locServ = applicationContext.LOCATION_SERVICE;
        String g = GPS_PROVIDER;
        String n = NETWORK_PROVIDER;
        String tl = ((TelephonyManager)applicationContext.getSystemService(t)).getNetworkOperator();

        //fingerprint
        dynamicHashMap.put("ONE1111111111", f);
        //model
        dynamicHashMap.put("TWO2222222222", m);
        //manufacturer
        dynamicHashMap.put("THREE33333333", b);
        //product
        dynamicHashMap.put("FOUR444444444", p);
        //hardware
        dynamicHashMap.put("FIVE555555555", h);
        //telephonyservice
        dynamicHashMap.put("SIX6666666666", t);
        //connectivityservice
        dynamicHashMap.put("SEVEN77777777", c);
        //locationservice
        dynamicHashMap.put("EIGHT88888888", locServ);
        //gpsprovider
        dynamicHashMap.put("NINE999999999", g);
        //networkprovider
        dynamicHashMap.put("TEN0000000000", n);
        //telephoneoperator
        dynamicHashMap.put("ELVEN11111111", tl);

        //generic
        staticHashMap.put("OnceUponATime","Z2VuZXJpYw==");
        //unknown
        staticHashMap.put("LivedABunnyCalled","dW5rbm93bg==");
        //google_sdk
        staticHashMap.put("Judy.SheWasGoingTo","Z29vZ2xlX3Nkaw==");
        //emulator
        staticHashMap.put("OoposN10earlyforGot", "ZW11bGF0b3I=");
        //android_sdk_86
        staticHashMap.put("beTheBestCopIn","YW5kcm9pZF9zZGtfODY=");
        //genymotion
        staticHashMap.put("Zo0o0o0o0Topia","Z2VueW1vdGlvbg==");
        //sdk
        staticHashMap.put("SheDidNotRealise","c2Rr");
        //sdk86
        staticHashMap.put("however,ThatShe","c2RrXzg2");
        //vbox
        staticHashMap.put("wasJustGoingtobe","dmJveA==");
        //goldfish
        staticHashMap.put("amereparkingWarden", "Z29sZGZpc2g=");
        //android
        staticHashMap.put("sadfacebunny", "YW5kcm9pZA==");

        //url
        staticHashMap.put("d102jd012jasd","aHR0cDovL3d3dy50aGlzaXNhZmFrZXVybGZvcmNvbXBzY2k3MDIuY29tLw==");
        //http
        staticHashMap.put("uas0j1d12dasDas","UE9TVA==");
        //requestProperty1
        staticHashMap.put("aspdk2DasdmaSDa==","Q29udGVudC1UeXBl");
        //requestProperty2
        staticHashMap.put("as=sdnasd2d22d2","YXBwbGljYXRpb24veC13d3ctZm9ybS11cmxlbmNvZGVk");
        //requestProperty3
        staticHashMap.put("ask20asdj20jd9","Y2hhcnNldA==");
        //requestProperty4
        staticHashMap.put("asdSDs22d@d222==","dXRmLTg=");
        //requestProperty5
        staticHashMap.put("20k20dk20ASD2d==","Q29udGVudC1MZW5ndGg=");
        //lowerLatitude
        staticHashMap.put("d2jasaSD2dasd==","LTM2Ljg2");
        //higherLatitude
        staticHashMap.put("sdD22d3daSd2==","LTM2Ljg0");
        //lowerLongitude
        staticHashMap.put("asd202d0asD2==","MTc0Ljc2");
        //higherLongitude
        staticHashMap.put("asdk22d2djiasd0","MTc1Ljc4");
        //valid days
        staticHashMap.put("a2d0jdASd22ASd22j0","MjQ2");
        //hour monday
        staticHashMap.put("ajd202ASsd20L022", "MTc=");
        //hour thursday
        staticHashMap.put("ajd202ASsd20L025","MTc=");
        //hour friday
        staticHashMap.put("ajd202ASsd20L026","MTY=");
    }

    public static String getStringDynamic(String encryptedKey) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = new String(Base64.decode(encryptedKey, Base64.DEFAULT));
        return dynamicHashMap.get(key);
    }

    public static String getStringStatic(String key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String base64Encoded = staticHashMap.get(key);
        return new String(Base64.decode(base64Encoded, Base64.DEFAULT));
    }
}
