package obfuscate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Name Obfuscater works to rename both methods and variables within
 * the supplied files
 * @author Elizabeth
 *
 */
public class NameObfuscater implements Obfuscater {

	static int count = 1;
	static boolean overflow = false;
	static HashMap<String,String> methodMap = new HashMap<String,String>();
	static HashMap<String,String> publicFieldsMap = new HashMap<String,String>();
	
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
			content = replaceFields(file,content);
			content = replaceDeclaredMethods(content);

			//Write the result back to the file
			Files.write((Paths.get(file.toURI())), content.getBytes(charset));

		}
		//iterate through files again to rename method calls as well
		//TODO add blacklist files as well
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			//get the entire files contents in a string
			Scanner sc = new Scanner(file);
			String content =sc.useDelimiter("\\Z").next();
			sc.close();

			//set up the character set for writing back to the file
			Charset charset = StandardCharsets.UTF_8;

			content = checkMethodCalls(content);
			content = methodVariableRename(content);
			content = checkFieldCalls(file,content);
			Files.write((Paths.get(file.toURI())), content.getBytes(charset));

		}
		
		//need to iterate through the blacklisted files to rename public variables and method calls in them too
		for(Map.Entry<String, File> blacklistFile : blacklist.entrySet()){
			
		}

		return files;
	}

	/**
	 * Method used to rename fields within a file, and add declared public variables to the hashmap
	 * @param file
	 * @param content
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String replaceFields(File file,String content) throws FileNotFoundException, IOException{
		StringBuffer contentsb = new StringBuffer(content);
		//Extract the file line by line
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line
				//variable declaration check (only checks if it has a = sign
				Pattern p = Pattern.compile("\\b(\\w+)\\s*=\\s*(?:\"([^\"]*)\"|([^ ]*)\\b)");
				Matcher m = p.matcher(line);
				while(m.find()){
					//matcher group index 1 is the name of the variable
					//get variables new name
					String newName = getNewName();
					//check if variable is public, and if so add to the global hashmap
					Matcher m1 = Pattern.compile("\\bpublic\\b").matcher(line);
					if(m1.find()){
						publicFieldsMap.put(m.group(1), newName);
					}
					if(publicFieldsMap.containsKey(m.group(1))){
						contentsb = replaceSB(contentsb,m.group(1),publicFieldsMap.get(m.group(1)));

					}else{
						contentsb = replaceSB(contentsb,m.group(1),newName);

					}
				}

				//second pattern to check for variables declared without an equals sign, only need to do public
				Pattern p2 = Pattern.compile("\\bpublic\\b\\s+\\w+\\b\\s+\\w+\\b(\\s+[;]|[;])");
				Matcher m2 = p2.matcher(line);
				while(m2.find()){
					//matcher group index 1 is the name of the variable
					//get variables new name
					String newName = getNewName();
					String[] strArr = m2.group().split("\\s+");
					strArr[2] = strArr[2].replaceAll("[^a-zA-Z ]", "");
					publicFieldsMap.put(strArr[2], newName);
					contentsb = replaceSB(contentsb,strArr[2],newName);
				}
			}
		}
		return contentsb.toString();
	}
	
	
	/**
	 * Method used to rename method signatures andn their refereneces within a file
	 * @param content
	 * @return
	 */
	private String replaceDeclaredMethods(String content){
		//use regex pattern matching to find method declarations
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

			//check if declaration is in in hashmap already, if yes, then rename to new name
			//otherwise parse the name, assign a new one, add it to hashmap, then rename all instances in the file
			if(!methodMap.containsKey(methodName)){

				//String renamed = methodDec.replace(methodName, getNewName() + "(");
				//add to hashmap
				methodMap.put(methodName, getNewName());
			}
			//rename in file
			content = content.replace(methodName, methodMap.get(methodName));
		}
		return content;
	}
	
	/**
	 * Iterates through files again to rename method calls
	 */
	private String checkMethodCalls(String content){
		for (Entry<String, String> entry : methodMap.entrySet()){
			content = content.replaceAll("\\b"+entry.getKey()+"(\\()", methodMap.get(entry.getKey()) + "(");
		}

		return content;
	}

	/**
	 * Method used to rename the variables declared inside mehtod signatures
	 * @param content
	 * @return
	 */
	private String methodVariableRename(String content){
		Pattern p = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+(|\\s+(\\w+) *)\\([^\\)]*\\) *(\\{?|[^;])");
		Matcher m = p.matcher(content);
		while(m.find()){			
			Matcher matchBrackets = Pattern.compile("\\(([^)]+)\\)").matcher(m.group());
			while(matchBrackets.find()) {
				String[] indVariables = matchBrackets.group().split("\\,");
				for(int i =0; i< indVariables.length;i++){
					//remove any brackets etc
					indVariables[i] = indVariables[i].replaceAll("[^a-zA-Z ]", "");
					//split into sub array with element two being the variable name
					indVariables[i] = indVariables[i].trim();
					String[] separateWords = indVariables[i].split("\\s+");
					content = content.replaceAll(separateWords[1],getNewName());
				}
			}
		}

		return content;
	}

	/**
	 * Method to check that all fields have been renamed, and to search for public variable use
	 * @param file
	 * @param content
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String checkFieldCalls(File file,String content) throws FileNotFoundException, IOException{
	
		//Extract the file line by line
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				//variable use check
				Pattern p = Pattern.compile("\\b[^\\W\\d]\\w*(?:\\s*\\.\\s*[^\\W\\d]\\w*\\b)+(?!\\s*\\()");
				Matcher m = p.matcher(line);
				while(m.find()){
					String i = m.group();
					String[] strArr = i.split("\\.");
					//if it contains the field name, then it is decleared elsewhere, so rename to that
					if(publicFieldsMap.containsKey(strArr[1])){
						//rename in file
						content = content.replaceAll(m.group(),strArr[0] + "." + publicFieldsMap.get(strArr[1]));
					}
				}

			}
		}
		return content;
	}
	
/**
 * Method to rename variables using stringbuffer
 * @param buff
 * @param toReplace
 * @param replaceTo
 * @return
 */
	private StringBuffer replaceSB(StringBuffer buff,String toReplace,String replaceTo){
		Pattern replacePattern = Pattern.compile("\\b"+toReplace+"\\b");
		Matcher matcher = replacePattern.matcher(buff);
		while(matcher.find()){
			buff = new StringBuffer(matcher.replaceAll(replaceTo));//.appendReplacement(buff, replaceTo);
		}

		return buff;
	}

	/**
	 * Method that retrieves the new name for the field 
	 * Returns some variation of the letter a 108 (l) and 49 (1)
	 * */
	private String getNewName(){
		int asciiCode = 76;
		StringBuffer sb = new StringBuffer();
		if(count >= 70){
			count = 1;
			overflow = true;
		}
		for (int i = 0; i < count; i++){
			// if i is even, then the next letter should be L/l, otherwise 1/one
			if ( (i % 2) == 0){
				if(overflow){
					asciiCode = 76;
				}else{
					asciiCode = 108;
				}
			} else{
				asciiCode = 49;
			}
			sb.append(Character.toString((char)asciiCode)) ;
		}
		count++;


		return sb.toString();
	}
}



