package PACKAGENAMETOBEREPLACED;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

public class Decrypter {
    public static Context context;

    public static synchronized String decrypt(int number) {
        int oId = context.getResources().getIdentifier("o" + number, "drawable", context.getPackageName());
        int eId = context.getResources().getIdentifier("pe" + number, "drawable", context.getPackageName());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Picture overlayEncrypted = new Picture(BitmapFactory.decodeResource(context.getResources(), eId, options));
        Picture overlayOriginal = new Picture(BitmapFactory.decodeResource(context.getResources(), oId, options));
        String message = decrypt(overlayEncrypted, overlayOriginal);
        return message;
    }

	private static String decrypt(Picture copy, Picture key){
        int allowed = deCalcAllowed(copy, key, 1, 1);
        int count=0;
        StringBuilder text = new StringBuilder();
        for(int i = 0; i<key.width(); i++){
            for(int j = 0; j<key.height(); j++){
                if(!(i == 1 && j == 1)){
                    count++;
                    if(count==allowed){
                        text.append(deCryptChar(copy, key, i, j));
                        count=0;
                    }                
                }
            }
        }
        return text.toString();
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
	        int pix = key.getPixelByCoordinates(x, y);
            int blueRGBValue = Color.blue(pix);
            int redRGBValue = Color.red(pix);
            int greenRGBValue = Color.green(pix);
            int c = copy.getPixelByCoordinates(x, y);
            int CblueRGBValue = Color.blue(c);
            int CredRGBValue = Color.red(c);
            int CgreenRGBValue = Color.green(c);
	        return new ColorOverHead(Math.abs(redRGBValue-CredRGBValue), Math.abs(greenRGBValue-CgreenRGBValue), Math.abs(blueRGBValue-CblueRGBValue));
	 }
}

class Picture
{
    private Bitmap image;

    Picture(Bitmap image)
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
    public int getPixelByCoordinates(int i, int j)
    {
        return image.getPixel(i, j);
    }
}


class ColorOverHead
{
    private int r;
    private int g;
    private int b;

    ColorOverHead(int r, int g, int b)
    {
        this.b = b;
        this.r = r;
        this.g = g;
    }

    public int getGreen(){
        return g;
    }

    public int getBlue(){
        return b;
    }

    public int getRed(){
        return r;
    }
}