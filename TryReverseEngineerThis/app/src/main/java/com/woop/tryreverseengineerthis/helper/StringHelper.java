package com.woop.tryreverseengineerthis.helper;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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
    private static HashMap<String, byte[]> staticHashMap;
    private static Cipher cipher;

    public static void initialise(Context applicationContext) throws NoSuchPaddingException, NoSuchAlgorithmException {

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
        String l = applicationContext.LOCATION_SERVICE;
        String g = GPS_PROVIDER;
        String n = NETWORK_PROVIDER;
        String tl = ((TelephonyManager)applicationContext.getSystemService(t)).getNetworkOperator();

        dynamicHashMap.put("aasdpososjdoa", f);
        dynamicHashMap.put("d1oas0odja0jd", m);
        dynamicHashMap.put("d12j012jd0d1j", b);
        dynamicHashMap.put("dpqsa;ojasodj", p);
        dynamicHashMap.put("asdln12palslm", h);
        dynamicHashMap.put("asldmlmsldm21", c);
        dynamicHashMap.put("asod122omd2o2", l);
        dynamicHashMap.put("asdoj122d0jd2", g);
        dynamicHashMap.put("asdlm1dmdmdm2", t);
        dynamicHashMap.put("d20kd20kd0222", n);
        dynamicHashMap.put("a0jd2d20j220j", tl);
    }

    public static String getStringDynamic(byte[] encyrptedKey, String key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(DECRYPT_MODE, aesKey);
        String hashMapKey = new String(cipher.doFinal(encyrptedKey));
        return dynamicHashMap.get(hashMapKey);
    }

    public static String getStringStatic(String id, String key){
        return "";
    }


}
