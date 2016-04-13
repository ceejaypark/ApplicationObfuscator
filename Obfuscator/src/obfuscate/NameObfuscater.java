package obfuscate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class NameObfuscater implements Obfuscater {

	static int count = 30;
	static HashMap<String,String> methodMap = new HashMap<String,String>();

	@Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{

		//iterate through each file
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			//get the entire files contents in a string
			Scanner sc = new Scanner(file);
			String content =sc.useDelimiter("\\Z").next();
			sc.close();

			//set up the character set for writing back to the file
			Charset charset = StandardCharsets.UTF_8;

			//try catch is used to access all field and method names in the class
			try {
				// Convert file to a URL
				URL url = file.toURI().toURL();;        
				URL[] urls = new URL[]{url};
				//load in file as a class so we can use reflection
				URLClassLoader ucl = new URLClassLoader(urls);
				
				Class<?> c = ucl.loadClass(file.getName().replaceFirst("[.][^.]+$", "") ); 
				//iterate through each declared field and rename it
				for(Field f: c.getDeclaredFields()) {
					content = Pattern.compile("\\b"+ f.getName() + "\\b").matcher(content).replaceAll(getNewName());
					//content = content.replace(, getNewName());

				}
				//iterate through declared methods and rename
				for(Method m: c.getDeclaredMethods()) {
					//check if in hashmap already, if yes, then rename to new name
					//otherwise assign a name, add it to hashmap, then rename in file

					if(!methodMap.containsKey(m.getName())){
						methodMap.put(m.getName(), getNewName());
					}
					content = Pattern.compile("\\b"+ m.getName() + "\\b").matcher(content).replaceAll(methodMap.get(m.getName()));

				}

				//Write the result back to the file
				Files.write((Paths.get(file.toURI())), content.getBytes(charset));

				ucl.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}			

		}	

		return files;
	}
	/*
	 * Method that retrieves the new name for the field 
	 * Returns some variation of the letter a 108 (l) and 49 (1)
	 * */
	private String getNewName(){
		int asciiCode = 76;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++){
			// if i is even, then the next letter should be L/l, otherwise 1/one
			if ( (i % 2) ==0){
				asciiCode = 108;
			} else{
				asciiCode = 49;
			}
			sb.append(Character.toString((char)asciiCode)) ;
		}
		count++;


		return sb.toString();
	}
}



