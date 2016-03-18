package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NameObfuscater implements Obfuscater {

	static int count = 65;

	
	@Override
	public  HashMap<String, File> execute(HashMap<String, File> files) throws IOException {
//get list of files
		
List<String> linesOfCode = new ArrayList<String>();
		//iterate through each file
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			//String content = new Scanner(file).useDelimiter("\\Z").next();
		
			try {
			    // Convert File to a URL
			    URL url = file.toURI().toURL();;         // file:/c:/myclasses/
			    URL[] urls = new URL[]{url};
			   
			    URLClassLoader ucl = new URLClassLoader(urls);
		    	Class c = ucl.loadClass("Input4"); 
		    	for(Field f: c.getDeclaredFields()) {
		    		System.out.println("Field name" + f.getName());
		    	}
			} catch (MalformedURLException e) {
			} catch (ClassNotFoundException e) {
			}			
			
			//Field[] fields = YourClassName.class.getFields();

		}
		
		return files;
		}
	
	private String getNewName(){
		String newName = Character.toString((char)count);
		count++;
		if(count > 90 ){
			//avoiding the punctuation from 91 - 96 in ASCII
			count = 97;
		}
		return newName;
	}
	}


	
