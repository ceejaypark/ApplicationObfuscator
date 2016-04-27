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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Name Obfuscater works to rename both methods and variables within the
 * supplied files
 * 
 * @author Elizabeth, cwu323, jkim 506
 *
 */
public class NameObfuscator implements Obfuscator {

	static int count = 1;
	static int overflow = 0;
	static HashMap<String, String> methodMap = new HashMap<String, String>();
	static HashMap<String, String> publicFieldsMap = new HashMap<String, String>();
	ArrayList<String> uniqueNameCheck = new ArrayList<String>();

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files,
			HashMap<String, File> blacklist, File manifest) throws IOException {

		// iterate through each file
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			// get the entire files contents in a string
			Scanner sc = new Scanner(file);
			String content = sc.useDelimiter("\\Z").next();
			sc.close();

			// set up the character set for writing back to the file
			Charset charset = StandardCharsets.UTF_8;
			content = replaceFields(file, content);
			content = replaceDeclaredMethods(content);

			// Write the result back to the file
			Files.write((Paths.get(file.toURI())), content.getBytes(charset));

		}
		// iterate through files again to rename method calls as well
		// TODO add blacklist files as well
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			// get the entire files contents in a string
			Scanner sc = new Scanner(file);
			String content = sc.useDelimiter("\\Z").next();
			sc.close();

			// set up the character set for writing back to the file
			Charset charset = StandardCharsets.UTF_8;

			content = checkMethodCalls(content);
			content = methodVariableRename(content);
			content = checkFieldCalls(file, content);
			Files.write((Paths.get(file.toURI())), content.getBytes(charset));

		}

		// need to iterate through the blacklisted files to rename public
		// variables and method calls in them too
		for (Map.Entry<String, File> blacklistFile : blacklist.entrySet()) {

		}

		return files;
	}

	/**
	 * Method used to rename fields within a file, and add declared public
	 * variables to the hashmap
	 * 
	 * @param file
	 * @param content
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String replaceFields(File file, String content)
			throws FileNotFoundException, IOException {
		StringBuffer contentsb = new StringBuffer(content);
		// Extract the file line by line
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			String line;
		while((line = br.readLine())!=null) {
			if (line.trim().startsWith("import")) {
				continue;
			}
			// process the line
			// variable declaration check (only checks if it has a = sign
				Pattern p = Pattern
						.compile("([^((\\.)|\"|=)])+\\b(\\w+)\\s*=[^=]\\s*(.*)\\s*;");
				Matcher m = p.matcher(line);
				while (m.find()) {
					// matcher group index 1 is the name of the variable
					// get variables new name
					
					// check if variable is public, and if so add to the global
					// hashmap
					Matcher m1 = Pattern.compile("\\bpublic\\b").matcher(line);
					if (m1.find()) {
						if (!publicFieldsMap.containsKey(m.group(2))) {

							publicFieldsMap.put(m.group(2), getNewName());

						}
					}
					if (publicFieldsMap.containsKey(m.group(2))) {
						contentsb = replaceSB(contentsb, m.group(2),
								publicFieldsMap.get(m.group(2)));
					} else {
						String newName = getNewName();
						contentsb = replaceSB(contentsb, m.group(2), newName);
					}
				}

				// second pattern to check for variables declared without an
				// equals sign

				Pattern p2 = Pattern
						.compile("(public|protected|private)\\s+((final\\s+)|(static\\s+))?(\\w+((<(.*)>)?))\\s+\\w+\\b(\\s+[;]|[;])");
				Matcher m2 = p2.matcher(line);
				while (m2.find()) {
					if (!(m2.group().contains("public"))) {
						String[] varDec = m2.group().split("\\s+");
						varDec[varDec.length - 1] = varDec[varDec.length - 1]
								.replaceAll("[^a-zA-Z ]", "");
						contentsb = replaceSB(contentsb,
								varDec[varDec.length - 1], getNewName());
					} else {
						// matcher group index 1 is the name of the variable
						// get variables new name
						
						
						String[] strArr = m2.group().split("\\s+");
						strArr[strArr.length - 1] = strArr[strArr.length - 1]
								.replaceAll("[^a-zA-Z ]", "");
						if(publicFieldsMap.containsKey(strArr[strArr.length -1])){
							contentsb = replaceSB(contentsb,strArr[strArr.length - 1], publicFieldsMap.get(strArr[strArr.length -1]));
						}
						else{
							String newName = getNewName();
							publicFieldsMap.put(strArr[strArr.length - 1], newName);
							contentsb = replaceSB(contentsb,
									strArr[strArr.length - 1], newName);
						}
					}
				}
			}
		}

		return contentsb.toString();
	}

	/**
	 * Method used to rename method signatures andn their refereneces within a
	 * file
	 * 
	 * @param content
	 * @return
	 */
	private String replaceDeclaredMethods(String content) {
		// use regex pattern matching to find method declarations
		Pattern pattern = Pattern
				.compile("[^@Override]\n((public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;]))");

		Matcher matcher = pattern.matcher(content);
		int count = 0;
		while (matcher.find()) {
			String methodDec = matcher.group();

			Pattern pConstructor = Pattern
					.compile("(public|private|protected)+\\s+\\w+\\(");
			Matcher matchConstructor = pConstructor.matcher(methodDec);

			if (matchConstructor.find()) {
				continue;
			}

			// parse the name from the declaration
			Pattern nameMatch = Pattern.compile("\\w+(\\s+|\\b)(\\()");
			Matcher matcher2 = nameMatch.matcher(methodDec);
			String methodName = "";
			if (matcher2.find()) {
				methodName = matcher2.group();
			}
			methodName = methodName.replace("(", "");
			// check if is main method, and ignore if so
			if (methodName.equals("main")) {
				continue;
			}

			String patternMethodDec = methodDec.replace("{", "\\{");
			patternMethodDec = patternMethodDec.replace("(", "\\(");
			patternMethodDec = patternMethodDec.replace(")", "\\)");

			//Find and replace the calls to public methods in the map. Consider special cases of @Override methods
			Matcher match = Pattern.compile(
					"[\r?\n](.*)[\r?\n]\\s+(\\w+|)\\s+" + patternMethodDec)
					.matcher(content);
			boolean isOverriden = false;
			while (match.find()) {
				String a = new String(match.group());
				if (a.replaceAll("\\s","").startsWith("@Override")) {
					count++;
					System.out.println(match.group());
					isOverriden = true;
				}
			}
			if (isOverriden) {
				continue;
			}
			
			// check if declaration is in in hashmap already, if yes, then
			// rename to new name
			// otherwise parse the name, assign a new one, add it to hashmap,
			// then rename all instances in the file
			if (!methodMap.containsKey(methodName)) {

				// String renamed = methodDec.replace(methodName, getNewName() +
				// "(");
				// add to hashmap
				methodMap.put(methodName, getNewName());
			}

			// rename in file
			content = content.replace(methodName, methodMap.get(methodName));
		}
		return content;
	}

	/**
	 * Iterates through files again to rename method calls
	 */
	private String checkMethodCalls(String content) {

		for (Entry<String, String> entry : methodMap.entrySet()) {

			content = replaceSB(new StringBuffer(content),
					"\\b" + entry.getKey() + "(\\()",
					methodMap.get(entry.getKey()) + "(").toString();

		}

		return content;
	}

	/**
	 * Method used to rename the variables declared inside mehtod signatures
	 * 
	 * @param content
	 * @return
	 */
	private String methodVariableRename(String content) {
		Pattern p = Pattern
				.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");
		Matcher m = p.matcher(content);
		while (m.find()) {
			Matcher matchBrackets = Pattern.compile("\\(([^)]+)\\)").matcher(
					m.group());
			while (matchBrackets.find()) {
				String[] indVariables = matchBrackets.group().split("\\,");
				for (int i = 0; i < indVariables.length; i++) {
					// remove any brackets etc
					indVariables[i] = indVariables[i].replaceAll("[^a-zA-Z ]",
							"");
					// split into sub array with element two being the variable
					// name
					indVariables[i] = indVariables[i].trim();
					String[] separateWords = indVariables[i].split("\\s+");

					if (separateWords.length > 1) {
						content = replaceSB(new StringBuffer(content),
								separateWords[separateWords.length - 1],
								getNewName()).toString();
					}
				}
			}
		}
		return content;
	}

	/**
	 * Method to check that all fields have been renamed, and to search for
	 * public variable use
	 * 
	 * @param file
	 * @param content
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private String checkFieldCalls(File file, String content)
	{
		String[] lineByLine = content.split("\n");
			for(String line : lineByLine) {
				line = line + "\n";
				if(line.trim().startsWith("import")){
					continue;
				}
				// variable use check
				Pattern p = Pattern
						.compile("\\b\\w+((\\(.*\\))?)\\.\\w+((\\(.*\\))?)\\b");
				Matcher m = p.matcher(line);
				while (m.find()) {
					String i = m.group();
					String[] strArr = i.split("\\.");
					if(strArr[0].replaceAll("[^a-zA-Z ]","").equals("R")){
						continue;
					}
					// if it contains the field name, then it is decleared
					// elsewhere, so rename to that
					strArr[strArr.length -1] = strArr[strArr.length -1].replaceAll("[^a-zA-Z ]","");
					
					if (publicFieldsMap.containsKey(strArr[strArr.length -1])) {
						// rename in file
						String find =i;
						find = find.replace(strArr[strArr.length -1], publicFieldsMap.get(strArr[strArr.length -1]));
						
						
						String newCont = content.replaceAll(Pattern.quote(i), find);
						
						content = newCont;
					}
				}
			}
		return content;
	}

	/**
	 * Method to rename variables using stringbuffer
	 * 
	 * @param buff
	 * @param toReplace
	 * @param replaceTo
	 * @return
	 */
	private StringBuffer replaceSB(StringBuffer buff, String toReplace,
			String replaceTo) {

		StringBuffer newBuff = new StringBuffer();

		Matcher m = Pattern.compile("(?m)^.*$").matcher(buff);
		boolean isSkip = false;
		while (m.find()) {
			String found = m.group();
			//isSkip to represent if line is an overridden method. Use special check to consider
			//if overridden method is from a self written interface
			if (isSkip) {
				isSkip = false;

				String[] temp = found.split("\\(");
				
				String[] check = temp[0].split("\\s+");
				if(check.length > 3 && methodMap.containsKey(check[3])){
					StringBuilder sb = new StringBuilder();
					for(int k = 0; k <check.length; k ++){
						if(k != check.length -1){
							sb.append(check[k] + " ");
						}else{
							sb.append(methodMap.get(check[k]));
						}
					}
					
					newBuff.append(sb.toString()+"(");
				}
				else{
					newBuff.append(temp[0] + "(");
				}
				
				StringBuilder sb = new StringBuilder();
				
				for (int i = 1; i < temp.length; i++) {
					sb.append(temp[i]);
				}

				found = sb.toString();

			}

			//if line contains import or package, append then ignore line
			if (found.contains("import") | found.contains("package")) {
				newBuff.append(found + "\n");
				continue;
			}
			//If line contains @Override, following line must be an overridden method.
			//Set isSkip boolean to true to consider special case
			if (found.contains("@Override")) {
				isSkip = true;
			}

			Pattern replacePattern = Pattern.compile("\\b" + toReplace + "\\b");
			if(toReplace.startsWith("\\b")){
				replacePattern = Pattern.compile(toReplace);
			}
			Matcher matcher = replacePattern.matcher(found);
			if (matcher.find()) {
				
				newBuff.append(new StringBuffer(matcher.replaceAll(replaceTo))
						+ "\n");
			} else {
				newBuff.append(found + "\n");
			}
		}
		return newBuff;
	}

	/**
	 * Method that retrieves the new name for the field Returns some variation
	 * of the letter a 108 (l) and 49 (1)
	 * */
	private String getNewName() {
		int asciiCode = 76;
		StringBuffer sb = new StringBuffer();
		//limiting lengths of variables to 100.
		if (count >= 100) {
			count = 1;
			overflow++;
		}
		for (int i = 0; i < count; i++) {
			// generating a variable name using only 2 letters
			if ((i % 2) == 0) {
				if (overflow == 0) {
					asciiCode = 76;
				} else if(overflow == 1){
					asciiCode = 108;
				} else if(overflow == 2){
					asciiCode = 79;
				} else if(overflow == 3){
					asciiCode = 111;
				} else if(overflow == 4){
					asciiCode = 105;
				} else{
					asciiCode = 73;
				}
			} else {
				asciiCode = 49;
			}
			sb.append(Character.toString((char) asciiCode));
		}
		count++;
		String returnVal = sb.toString();
		
		//Ensuring generated name is unique. if not generate a new one
		if(uniqueNameCheck.contains(returnVal)){
			returnVal = getNewName();
		}else{
			uniqueNameCheck.add(returnVal);
		}

		return returnVal;
	}
	@Override
	public String getName() {
		return "Variable Name Obfuscation";
	}
}
