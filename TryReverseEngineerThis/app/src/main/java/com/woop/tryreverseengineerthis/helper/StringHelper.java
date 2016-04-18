package com.woop.tryreverseengineerthis.helper;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;

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
    private static HashMap<String, String> staticHashMap;
    private static Cipher cipher;

    private static Object lock;

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
        String l = applicationContext.LOCATION_SERVICE;
        String g = GPS_PROVIDER;
        String n = NETWORK_PROVIDER;
        String tl = ((TelephonyManager)applicationContext.getSystemService(t)).getNetworkOperator();

        dynamicHashMap.put("ONE1111111111", f);
        dynamicHashMap.put("TWO2222222222", m);
        dynamicHashMap.put("THREE33333333", b);
        dynamicHashMap.put("FOUR444444444", p);
        dynamicHashMap.put("FIVE555555555", h);
        dynamicHashMap.put("SIX6666666666", c);
        dynamicHashMap.put("SEVEN77777777", l);
        dynamicHashMap.put("EIGHT88888888", g);
        dynamicHashMap.put("NINE999999999", t);
        dynamicHashMap.put("TEN0000000000", n);
        dynamicHashMap.put("ELVEN11111111", tl);

        staticHashMap.put("OnceUponATime","ï›½t<2¾½£Au4€/;");
        staticHashMap.put("LivedABunnyCalled","UÓÂM«L’¡Œ£û©”#2");
        staticHashMap.put("Judy.SheWasGoingTo","YòNÒþ‰%ˆ7=f¦ÉÓ");
        staticHashMap.put("beTheBestCopIn","ÁH^Ö²Q9_óËÎÈYø");
        staticHashMap.put("Zo0o0o0o0Topia","™¬‘çörç?š`sàð½ËCKŒy©±ì¬G<D");
        staticHashMap.put("SheDidNotRealise",":|dó2?œÀ.õe?®ÀL");
        staticHashMap.put("however,ThatShe","ºÆ¢Ÿy˜bÜšâ?VÞ?");
        staticHashMap.put("wasJustGoingtobe","þÏ)é0K®S›¡n„ši+");
        staticHashMap.put("amereparkingWarden","³Ê²+G/F•“QRSïÄ");
        staticHashMap.put("sadfacebunny", "1yÇÞ³’IÀ$“V„¬Ê");
        staticHashMap.put("theend", "„új–_ý‡[»¬k@ƒr");
    }

    public static String getStringDynamic(String encyrptedKey, String key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        String hashMapKey = "";
        synchronized (lock) {
            cipher.init(DECRYPT_MODE, aesKey);
            hashMapKey = new String(cipher.doFinal(encyrptedKey.getBytes()));
        }
        return dynamicHashMap.get(hashMapKey);
    }

    public static String getStringStatic(String id, String key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] encrypted = staticHashMap.get(id).getBytes();
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        String decryptedText = "";
        synchronized (lock) {
            cipher.init(DECRYPT_MODE, aesKey);
            decryptedText = new String(cipher.doFinal(encrypted));
        }
        return decryptedText;
    }


}
