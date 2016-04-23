package com.woop.tryreverseengineerthis;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

public class Decrypter {

    public static Context context;
    private final static String TAG = "Decrypter";

    public synchronized String decrypt(int number) {
        int oId = context.getResources().getIdentifier("o" + number, "drawable", context.getPackageName());
        int eId = context.getResources().getIdentifier("pe" + number, "drawable", context.getPackageName());

        Log.d(TAG, "" + number);
        Log.d(TAG, oId + "");
        Log.d(TAG, eId + "");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        String message = decrypt(
                new Picture(BitmapFactory.decodeResource(context.getResources(), eId, options)),
                new Picture(BitmapFactory.decodeResource(context.getResources(), oId, options)));

        Log.d(TAG, message);
        return message;
    }

	private static String decrypt(Picture copy, Picture key){
        int allowed = deCalcAllowed(copy, key, 1, 1);
        int count=0;
        String text = "";

        for(int i = 0; i<key.width(); i++)
            for(int j = 0; j<key.height(); j++){
                if(!(i == 1 && j == 1)){
                    count++;
                    if(count==allowed){
                        text += deCryptChar(copy, key, i, j);
                        count=0;
                    }                
                }
            }
        return text;
    }
	
	 private static char deCryptChar(Picture copy, Picture key, int x, int y){
	        ColorOverHead d = difference(copy, key, x, y);
	        int a = d.getRed() + d.getGreen() + d.getBlue();
	        return (char) a;
	 }
	 
	 private static int deCalcAllowed(Picture copy, Picture key, int x, int y){
	        ColorOverHead d = difference(copy, key, x, y);
	        return (d.getRed() * 127 + d.getGreen() * 127) + d.getBlue();
	 }
	    
	 private static ColorOverHead difference(Picture copy, Picture key, int x, int y){
	        int pix = key.get(x, y), blue = Color.blue(pix), red = Color.red(pix), green = Color.green(pix);
            int c = copy.get(x, y), Cblue = Color.blue(c), Cred = Color.red(c), Cgreen = Color.green(c);
	        return new ColorOverHead(Math.abs(red-Cred), Math.abs(green-Cgreen), Math.abs(blue-Cblue));
	 }
}

class ColorOverHead
{
    private int blue;
    private int red;
    private int green;

    ColorOverHead(int r, int g, int b)
    {
        blue = b;
        red = r;
        green = g;
    }

    public int getGreen(){
        return green;
    }

    public int getBlue(){
        return blue;
    }

    public int getRed(){
        return red;
    }
}

class Picture
{
    private Bitmap image;

    public Picture(Bitmap image)
    {
        this.image = image;
    }
    public int height()
    {
        return image.getHeight();
    }
    public int width()
    {
        return image.getWidth();
    }
    public int get(int i, int j)
    {
        return image.getPixel(i, j);
    }
}
