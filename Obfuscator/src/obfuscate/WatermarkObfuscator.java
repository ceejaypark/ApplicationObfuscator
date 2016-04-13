package obfuscate;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WatermarkObfuscator implements Obfuscater{

	@Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{

		//Produce watermark
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {

			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();

			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;

			boolean startedJavadoc = false;
			boolean addJavadoc = false;
			StringBuilder currentJavadoc = new StringBuilder();
			String currentJavadocString = "";
			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile.trim();
				
				//if blank just add it
				if (lineInFile.length() == 0) {
					linesOfCode.add(original);
					continue;
				}
				
				//if length is longer than 2, check if its a javadoc
				if(original.length() > 2){
					if(original.charAt(0) == '/' && original.charAt(1) == '*'&& original.charAt(2) == '*'){
						startedJavadoc = true;						
						continue;
					}
				}
				
				//if length is longer than 1, check if javadoc has finished
				if(original.length() > 1){					
					if(original.charAt(original.length() - 1) == '/' && original.charAt(original.length()-2) == '*' && startedJavadoc){
						startedJavadoc = false;
						addJavadoc = true;
						currentJavadocString = currentJavadoc.toString();
						currentJavadoc.setLength(0);
						continue;
					}
				}
				
				//if javadoc has started, add it to current javadoc string builder
				if(startedJavadoc){
					currentJavadoc.append(original);
					continue;
				}
				
				//add line
				linesOfCode.add(lineInFile);
				
				//if it is time to add javadoc
				if(addJavadoc){
					//check if method or class declaration has finished
					if(lineInFile.contains("{")){
						linesOfCode.add("");
						linesOfCode.add("String a=\"" + currentJavadocString.hashCode() + "\";");
						System.out.println("Hash added:" + currentJavadocString.hashCode());		
						System.out.println("Hashed from:" + currentJavadocString);
						addJavadoc = false;
					}					
				}
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

}
