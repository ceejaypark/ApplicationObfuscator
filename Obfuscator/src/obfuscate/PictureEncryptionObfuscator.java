package obfuscate;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class PictureEncryptionObfuscator implements Obfuscater {

	private static final String PICTURE = ".\\resources\\puppies.png";
	
	@Override
	public HashMap<String, File> execute(HashMap<String, File> files,
			HashMap<String, File> blacklist, File manifest) throws IOException {
		
		try{
			writeDecryptClass();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			int count = 0;
			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();

			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;
			boolean encryptNextLine = false;
			
			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				if(encryptNextLine){
					String cutDown = original.trim();
					int whiteSpaceCount = original.indexOf(original.trim());
					String variableName = cutDown.split("String ")[1].split("=")[0].trim();
					String value = cutDown.split("\"")[1].split("\"")[0];
										
					System.out.println(value);
					System.out.println(variableName);
					
					new Crypter(PICTURE).encrypt(value, "H:\\702\\output\\TryReverseEngineerThis-obfuscated\\app\\src\\main\\res\\drawable-mdpi\\pe" + count+".png");
					new Picture(PICTURE).save("H:\\702\\output\\TryReverseEngineerThis-obfuscated\\app\\src\\main\\res\\drawable-mdpi\\o" + count+".png");
					System.out.println(value.equals(Decrypter.decrypt("H:\\702\\output\\TryReverseEngineerThis-obfuscated\\app\\src\\main\\res\\drawable-mdpi\\o" + count+".png",
							"H:\\702\\output\\TryReverseEngineerThis-obfuscated\\app\\src\\main\\res\\drawable-mdpi\\pe" + count+".png")));
					
					String lineToAdd = "String " + variableName + " = Decrypter.decrypt(" + count +")"; 
					while(whiteSpaceCount > 0)
					{
						lineToAdd = ' ' + lineToAdd;
						whiteSpaceCount--;
					}
					
					count++;
					encryptNextLine = false;
					linesOfCode.add(lineToAdd);
					continue;
				}
				
				if (original.contains("@PictureObfuscate")){
					encryptNextLine = true;
					continue;
				}

				linesOfCode.add(original);
			}

			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter fileOutput = new BufferedWriter(fileWriter);

			for (String s : linesOfCode) {
				s = s + "\n";
				fileOutput.write(s);
			}

			fileOutput.flush();
			fileOutput.close();
			fileInput.close();
		}
		return files;
	}

	private void writeDecryptClass() throws IOException {

		String javaClass = 
				"import java.awt.Color; \n" +
				"import java.awt.image.BufferedImage;\n" +
				"import java.io.File;\n" +
				"import java.io.IOException;\n" +
				"import java.net.URL;\n" +
				"import javax.imageio.ImageIO;\n" +
				"public class Decrypter {    \n" +
				"	\n" +
				"	private static Picture copy;\n" +
				"	private static Picture key;\n" +
				"   private static Context context\n" +
				"\n" + 
				"	public static void setContext(Context context){\n" + 
				"		this.context = context;\n" + 
				"	}\n" +
				"   public static String decrypt(int number){\n" +
				"		int oId = context.getResources().getIdentifier(\"o\" + number, \"drawable\", getPackageName());\n"+
				"       int eId = context.getResources().getIdentifier(\"pe\" + number, \"drawable\", getPackageName());\n"+
				"		try{\n"+
				"			File fileOriginal = new File(Environment.getDataDirectory() + \"\\\\pictures\\\\\" + oId);\n"+
				"			InputStream inputStream = context.getResources().openRawResource(oId);\n"+
				"			OutputStream outStream = new FileOutputStream(fileOriginal);\n"+
				"			byte buffer[] = new byte[1024];\n"+
				"			int len;\n"+
				"			\n"+
				"			while((len=inputStream.read(buf))>0){\n"+
				"				out.write(buf,0,len);\n"+
				"				out.close();\n"+
				"				inputStream.close();\n"+
				"			}\n"+
				"		catch(IOException e){}\n"+
				"		}\n"+
				"		try{\n"+
				"			File fileEncrypted = new File(Environment.getDataDirectory() + \"\\\\pictures\\\\\" + eId);\n"+
				"			InputStream inputStream = context.getResources().openRawResource(eId);\n"+
				"			OutputStream outStream = new FileOutputStream(fileEncrypted);\n"+
				"			byte buffer[] = new byte[1024];\n"+
				"			int len;\n"+
				"			\n"+
				"			while((len=inputStream.read(buf))>0)\n"+
				"				out.write(buf,0,len);\n"+
				"			out.close();\n"+
				"			inputStream.close();\n"+
				"		}\n"+
				"		catch(IOException e){}\n"+
				"		}\n"+
				"		decrypt(fileOriginal, fileEncrypted);\n"+
				"	}\n"+
				"\n" +
				"	private static String decrypt(String original, String encrypted){\n" +
				"		copy = new Picture(original);\n" +
				"	    key = new Picture(encrypted);\n" +
				"		\n" +
				"        int allowed = deCalcAllowed(1, 1);\n" +
				"        int count=0;\n" +
				"        String text = \"\";\n" +
				"        \n" +
				"        for(int i = 0; i<key.width(); i++)\n" +
				"            for(int j = 0; j<key.height(); j++){\n" +
				"                if(!(i == 1 && j == 1)){\n" +
				"                    count++;\n" +
				"                    if(count==allowed){\n" +
				"                        text += deCryptChar(i, j);\n" +
				"                        count=0;\n" +
				"                    }                \n" +
				"                }\n" +
				"            }\n" +
				"        \n" +
				"        return text;\n" +
				"    }\n" +
				"	\n" +
				"	 private static char deCryptChar(int x, int y){\n" +
				"	        Color d = difference(x, y);\n" +
				"	        int a = d.getRed() + d.getGreen() + d.getBlue();\n" +
				"	        return (char) a;\n" +
				"	 }\n" +
				"	 \n" +
				"	 private static int deCalcAllowed(int x, int y){\n" +
				"	        Color d = difference(x, y);\n" +
				"	        return (d.getRed() * 127 + d.getGreen() * 127) + d.getBlue();\n" +
				"	 }\n" +
				"	    \n" +
				"	 private static Color difference(int x, int y){\n" +
				"	        Color pix = key.get(x,y);\n" +
				"	        int blue = pix.getBlue();\n" +
				"	        int red = pix.getRed();\n" +
				"	        int green = pix.getGreen();\n" +
				"	        Color c = copy.get(x,y);\n" +
				"	        int Cblue = c.getBlue();\n" +
				"	        int Cred = c.getRed();\n" +
				"	        int Cgreen = c.getGreen();\n" +
				"	        return new Color(Math.abs(red-Cred), Math.abs(green-Cgreen), Math.abs(blue-Cblue)); \n" +
				"	 }\n" +
				"}\n" +
				"class Picture\n" +
				"{\n" +
				"    private BufferedImage image;    // the rasterized image\n" +
				"    private String filename;        // name of file\n" +
				"    private Picture(int w, int h) \n" +
				"    {\n" +
				"        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);\n" +
				"        // set to TYPE_INT_ARGB to support transparency\n" +
				"        filename = w + \"-by-\" + h;\n" +
				"    }\n" +
				"    private String toString(){\n" +
				"        return filename;\n" +
				"    }\n" +
				"    public Picture(String filename) \n" +
				"    {\n" +
				"        this.filename = filename;\n" +
				"        try {\n" +
				"            // try to read from file in working directory\n" +
				"            File file = new File(filename);\n" +
				"            if (file.isFile()) {\n" +
				"                image = ImageIO.read(file);\n" +
				"            }\n" +
				"            // now try to read from file in same directory as this .class file\n" +
				"            else {\n" +
				"                URL url = getClass().getResource(filename);\n" +
				"                if (url == null) { url = new URL(filename); }\n" +
				"                image = ImageIO.read(url);\n" +
				"            }\n" +
				"        }\n" +
				"        catch (IOException e) {\n" +
				"            // e.printStackTrace();\n" +
				"            throw new RuntimeException(\"Could not open file: \" + filename);\n" +
				"        }\n" +
				"        // check that image was read in\n" +
				"        if (image == null) {\n" +
				"            throw new RuntimeException(\"Invalid image file: \" + filename);\n" +
				"        }\n" +
				"    }\n" +
				"    public Picture(File file) \n" +
				"    {\n" +
				"        try { image = ImageIO.read(file); }\n" +
				"        catch (IOException e) {\n" +
				"            e.printStackTrace();\n" +
				"            throw new RuntimeException(\"Could not open file: \" + file);\n" +
				"        }\n" +
				"        if (image == null) {\n" +
				"            throw new RuntimeException(\"Invalid image file: \" + file);\n" +
				"        }\n" +
				"    }\n" +
				"    \n" +
				"    private int height() \n" +
				"    {\n" +
				"        return image.getHeight(null);\n" +
				"    }\n" +
				"    private int width() \n" +
				"    {\n" +
				"        return image.getWidth(null);\n" +
				"    }\n" +
				"    private Color get(int i, int j) \n" +
				"    {\n" +
				"        return new Color(image.getRGB(i, j));\n" +
				"    }\n" +
				"    private void set(int i, int j, Color c) {\n" +
				"        if (c == null) { throw new RuntimeException(\"can't set Color to null\"); }\n" +
				"        image.setRGB(i, j, c.getRGB());\n" +
				"    }\n" +
				"    private void save(String name) \n" +
				"    {\n" +
				"        save(new File(name));\n" +
				"    }\n" +
				"    private void save(File file) \n" +
				"    {\n" +
				"        this.filename = file.getName();\n" +
				"        String suffix = filename.substring(filename.lastIndexOf('.') + 1);\n" +
				"        suffix = suffix.toLowerCase();\n" +
				"        if (suffix.equals(\"jpg\") || suffix.equals(\"png\")) \n" +
				"        {\n" +
				"            try { ImageIO.write(image, suffix, file); }\n" +
				"            catch (IOException e) { e.printStackTrace(); }\n" +
				"        }\n" +
				"        else \n" +
				"        {\n" +
				"            System.out.println(\"Error: filename must end in .jpg or .png\");\n" +
				"        }\n" +
				"    }\n" +
				"}\n";
		
		System.out.println(MainObfuscater.sourceFolder);
		
		FileWriter fileWriter = new FileWriter(MainObfuscater.sourceFolder + "\\Decrypter.java");
		BufferedWriter fileOutput = new BufferedWriter(fileWriter);

		fileOutput.write(javaClass);

		fileOutput.flush();
		fileOutput.close();
		fileWriter.close();
		
	}
}

class Crypter {    
    private Picture copy;
    private Picture key;
    private int bytes;
    private int countc; //count chars at text, when crypting
    /**
     * Construct Crypter object. 
     * @param key insert Key-picture for encryption or decryption. Must be .png! 
     */
    public Crypter(String key){
        this.key = new Picture(key);
        copy = new Picture(key);
        bytes = this.key.width() * this.key.height()-2;
        
    }
    /**
     * encrypt String into the picture using key-picture defined in constructor. 
     * @param text insert the String for encryption
     * @param file file name for result. Must be .png
     * @return Returns true, if everything went fine. False, if something went wrong. 
     */
    public boolean encrypt(String text, String file){
        copy = new Picture(key.toString());        
        countc = 0;        
        int count=0;
        
        if(text.length() == 0) return false; // Nothing to encrypt
        if(text.length() > bytes) return false; // The string is too long
        
        int allowed = (int) Math.floor(bytes/text.length()); // bytes between characters. includes char.                
        allowed(allowed, 1, 1);        
        
        //int badData = (int)Math.floor(bytes/allowed)-text.length(); 
        
        try{
            for(int i = 0; i<key.width(); i++)
                for(int j = 0; j<key.height(); j++){
                    if(!(i == 1 && j == 1)){
                        count++;
                        if(count==allowed){
                            cryptChar(i, j, next(text));
                            count=0;
                        }
                        else{
                            cryptChar(i, j, 0);
                        }                
                    }
                }
            
            copy.save(file);
        }
        catch(Exception e){
            System.out.println("I'm sorry, something went wrong, probably invalid character, try using characters present in 8-bit ASCII");
            return false;
        }
        
        return true;        
    }
    
    private void cryptChar(int x, int y, int a){
        Color pix = key.get(x,y);
        int blue = pix.getBlue();
        int red = pix.getRed();
        int green = pix.getGreen();
        
        if(a == 0){
            int intensity=30; // close to 40 is most secure
            int r = (int)(Math.random()*intensity);
            if(blue >= 128) blue -= r;
            else  blue += r;
            r = (int)(Math.random()*intensity);
            if(green >= 128) green -= r;
            else  green += r;
            r = (int)(Math.random()*intensity);
            if(red >= 128) red -= r;
            else  red += r;
        }
        else{
            Coord c = split3(a);
            int r = c.x;
            if(blue >= 128) blue -= r;
            else  blue += r;
            r = c.y;
            if(green >= 128) green -= r;
            else  green += r;
            r = c.z;
            if(red >= 128) red -= r;
            else  red += r;
        }        
        pix = new Color(red, green, blue);
        copy.set(x, y, pix);
    
    }
    
    private int next(String text){
        char a = 0;
        if(text.length() > countc){
        a=text.charAt(countc);        
        countc++;
        }        
        return a;        
    }
    
    private void allowed(int allowed, int x, int y){
        Color pix = key.get(x,y);
        int blue = pix.getBlue();
        int red = pix.getRed();
        int green = pix.getGreen();
        
        int count = 0;
        while(allowed > 127){
            count++;
            allowed-=127;
        }
        if(count > 0){
            Coord e = split2(count);
            if(red < 128){
                red += e.x;
            }
            else{
                red -= e.x;
            }
            if(green < 128){
                green += e.y;
            }
            else{
                green -= e.y;
            }
        }
        if(allowed <= 127){            
            if(blue < 128){                
                blue += allowed;
            }
            else if(blue >= 128){                
                blue -= allowed;
            }            
            
        }
        
        pix = new Color(red, green, blue);
        copy.set(x, y, pix);
        
        
    }
    
    private Coord split2(int a){
        int r = (int)(Math.random() * a);
        a-=r;
        Coord c = new Coord(a, r);
        return c;
    }
    
    private Coord split3(int a){
        int z = 0;
        int r = (int)(Math.random() * a);
        a-=r;
        if(a > r){
            z = (int)(Math.random() * a);
            a-=z;            
        }
        else{
            z = (int)(Math.random() * r);
            r-=z;            
        }
        
        Coord c = new Coord(a, r, z);
        return c;
    }
    
    
}

class Picture
{
    private BufferedImage image;    // the rasterized image
    private String filename;        // name of file

   /**
     * Create an empty w-by-h picture.
     */
    public Picture(int w, int h) 
    {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
        filename = w + "-by-" + h;
    }

    public String toString(){
        return filename;
    }
   /**
     * Create a picture by reading in a .png, .gif, or .jpg from
     * the given filename or URL name.
     */
    public Picture(String filename) 
    {
        this.filename = filename;
        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }

            // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) { url = new URL(filename); }
                image = ImageIO.read(url);
            }
        }
        catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }

        // check that image was read in
        if (image == null) {
            throw new RuntimeException("Invalid image file: " + filename);
        }
    }

   /**
     * Create a picture by reading in a .png, .gif, or .jpg from a File.
     */
    public Picture(File file) 
    {
        try { image = ImageIO.read(file); }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not open file: " + file);
        }
        if (image == null) {
            throw new RuntimeException("Invalid image file: " + file);
        }
    }
    
   /**
     * Return the height of the picture (in pixels).
     */
    public int height() 
    {
        return image.getHeight(null);
    }

   /**
     * Return the width of the picture (in pixels).
     */
    public int width() 
    {
        return image.getWidth(null);
    }

   /**
     * Return the Color of pixel (i, j).
     */
    public Color get(int i, int j) 
    {
        return new Color(image.getRGB(i, j));
    }

   /**
     * Set the Color of pixel (i, j) to c.
     */
    public void set(int i, int j, Color c) {
        if (c == null) { throw new RuntimeException("can't set Color to null"); }
        image.setRGB(i, j, c.getRGB());
    }

   /**
     * Save the picture to a file in a standard image format.
     * The filetype must be .png or .jpg.
     */
    public void save(String name) 
    {
        save(new File(name));
    }

   /**
     * Save the picture to a file in a standard image format.
     */
    public void save(File file) 
    {
        this.filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png")) 
        {
            try { ImageIO.write(image, suffix, file); }
            catch (IOException e) { e.printStackTrace(); }
        }
        else 
        {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }
}

class Coord {
    public int x;
    public int y;
    public int z;
    
    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Coord(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

}

class Decrypter {    
	
	private static Picture copy;
	private static Picture key;
	
	public static String decrypt(String original, String encrypted){
		copy = new Picture(original);
	    key = new Picture(encrypted);
		
        int allowed = deCalcAllowed(1, 1);
        int count=0;
        String text = "";

        for(int i = 0; i<key.width(); i++)
            for(int j = 0; j<key.height(); j++){
                if(!(i == 1 && j == 1)){
                    count++;
                    if(count==allowed){
                        text += deCryptChar(i, j);
                        count=0;
                    }                
                }
            }
        
        return text;
    }
	
	 private static char deCryptChar(int x, int y){
	        Color d = difference(x, y);
	        int a = d.getRed() + d.getGreen() + d.getBlue();
	        return (char) a;
	 }
	 
	 private static int deCalcAllowed(int x, int y){
	        Color d = difference(x, y);
	        return (d.getRed() * 127 + d.getGreen() * 127) + d.getBlue();
	 }
	    
	 private static Color difference(int x, int y){		 	
	        Color pix = key.get(x,y);
	        int blue = pix.getBlue();
	        int red = pix.getRed();
	        int green = pix.getGreen();
	        Color c = copy.get(x,y);
	        int Cblue = c.getBlue();
	        int Cred = c.getRed();
	        int Cgreen = c.getGreen();
	        return new Color(Math.abs(red-Cred), Math.abs(green-Cgreen), Math.abs(blue-Cblue)); 
	 }
}
