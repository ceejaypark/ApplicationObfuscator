package obfuscate;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class PictureEncryptionObfuscator implements Obfuscater {

	private static final String PICTURE = ".\\resources\\puppies.png";
	private static final String DECRYPTER = ".\\resources\\Decrypter.java";
	
	@Override
	public HashMap<String, File> execute(HashMap<String, File> files,
			HashMap<String, File> blacklist, File manifest) throws IOException {
		
		try{
			writeDecryptClass();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		File resFolder = getResDirectory(MainObfuscater.OUTPUT);
		Crypter crypter = new Crypter(PICTURE);
		Picture picture = new Picture(PICTURE);
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
															
					crypter.encrypt(value, resFolder.getCanonicalPath() + "\\drawable-mdpi\\pe" + count+".png");
					picture.save(resFolder.getCanonicalPath() + "\\drawable-mdpi\\o" + count+".png");
					System.out.println(value.equals(Decrypter.decrypt(resFolder.getCanonicalPath() + "\\drawable-mdpi\\o" + count+".png",
							resFolder.getCanonicalPath() + "\\drawable-mdpi\\pe" + count+".png")));
					
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

	private File getResDirectory(String path){
		for(File directory : getAllDirectory(new File(path))){
			if(directory.getName().equals("res")){
				return directory;
			}
		}
		return null;
	}
	
	private List<File> getAllDirectory(File file) {
	    List<File> subdirs = Arrays.asList(file.listFiles(new FileFilter() {
	        public boolean accept(File f) {
	            return f.isDirectory();
	        }
	    }));
	    subdirs = new ArrayList<File>(subdirs);

	    List<File> deepSubdirs = new ArrayList<File>();
	    for(File subdir : subdirs) {
	        deepSubdirs.addAll(getAllDirectory(subdir)); 
	    }
	    subdirs.addAll(deepSubdirs);
	    return subdirs;
	}

	private void writeDecryptClass() throws IOException {
		Files.copy(DECRYPTER, dst, StandardCopyOption.REPLACE_EXISTING);
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
