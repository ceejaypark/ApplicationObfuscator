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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class NameObfuscater implements Obfuscater {

	static int count = 30;
	static HashMap<String,String> methodMap = new HashMap<String,String>();

	@Override
	public  HashMap<String, File> execute(HashMap<String, File> files) throws IOException {

		//iterate through each file
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			//get the entire files contents in a string
			Scanner sc = new Scanner(file);
			String content =sc.useDelimiter("\\Z").next();
			sc.close();

			//set up the character set for writing back to the file
			Charset charset = StandardCharsets.UTF_8;

			//use regex pattern matching to find mathod declarations
			Pattern pattern = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				String methodDec = matcher.group();
				//parse the name from the declaration
				Pattern nameMatch = Pattern.compile("\\w+(\\s+|\\b)(\\()");

				Matcher matcher2 = nameMatch.matcher(methodDec);
				String methodName = "";
				if (matcher2.find()) {
					methodName = matcher2.group();
				}
				methodName= methodName.replace("(", "");
				//check if is main method, and ignore if so
				if(methodName.equals("main")){
					continue;
				}

				//check if declarationg is in in hashmap already, if yes, then rename to new name
				//otherwise parse the name, assign a new one, add it to hashmap, then rename all instances in the file
				if(!methodMap.containsKey(methodName)){

					//String renamed = methodDec.replace(methodName, getNewName() + "(");
					//add to hashmap
					methodMap.put(methodName, getNewName());
				}
				//rename in file
				content = content.replace(methodName, methodMap.get(methodName));
			}



			//				//iterate through each declared field and rename it
			//				for(Field f: c.getDeclaredFields()) {
			//					content = Pattern.compile("\\b"+ f.getName() + "\\b").matcher(content).replaceAll(getNewName());
			//					//content = content.replace(, getNewName());
			//
			//				}


			//iterate through declared methods and rename
			//				for(Method m: c.getDeclaredMethods()) {
			//					//check if in hashmap already, if yes, then rename to new name
			//					//otherwise assign a name, add it to hashmap, then rename in file
			//
			//					if(!methodMap.containsKey(m.getName())){
			//						methodMap.put(m.getName(), getNewName());
			//					}
			//					content = Pattern.compile("\\b"+ m.getName() + "\\b").matcher(content).replaceAll(methodMap.get(m.getName()));
			//
			//				}

			//Write the result back to the file
			Files.write((Paths.get(file.toURI())), content.getBytes(charset));

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



