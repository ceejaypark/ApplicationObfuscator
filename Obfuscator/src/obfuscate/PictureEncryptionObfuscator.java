package obfuscate;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureEncryptionObfuscator implements Obfuscater {

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
				String cutDown = original.trim();
				if(encryptNextLine){
					String variableName = cutDown.split("String ")[0];
					String lineToAdd = "String " + variableName + " = Decrypter.decrypt(" + count +")"; 
					
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
		
		
		return null;
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
				"	\n" +
				"   public static String decrypt(int number){\n" +
				"  		LOGIC" +
				"   }\n" +
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
