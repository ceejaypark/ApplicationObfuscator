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

public class CommentRemover implements Obfuscater{
	
	@Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{
		
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();
			
			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);
			
			String lineInFile;
			
			boolean isComment = false;
			
			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				lineInFile = lineInFile.trim();
				
				//skip but leave blank line
				if (lineInFile.length() == 0) {
					linesOfCode.add(original);
					continue;
				}
				
				//if the line begins with '//'
				if (lineInFile.charAt(0) == '/' && lineInFile.charAt(1) == '/' ) {
					continue;
				}
				
				//if the line begins with '/*', anything after is a comment
				if (lineInFile.charAt(0) == '/' && lineInFile.charAt(1) == '*') {
					isComment = true;
					//if the lineInFile ends with '*/', comment ended
					if (lineInFile.charAt(lineInFile.length() - 1) == '/' && lineInFile.charAt(lineInFile.length()-2) == '*') {
						isComment = false;
					}
					continue;
				}
				
				//if the line ends with '*/'
				if (lineInFile.charAt(lineInFile.length() - 1) == '/' && lineInFile.charAt(lineInFile.length()-2) == '*') {
					//if currently processing comment, comment ended
					if (isComment) {
						isComment = false;
						continue;
					}
				}
				
				//if the line is still a comment ignore
				if (isComment) {
					continue;
				}
				
				//remove comments at the end of code
				original = removeEndComments(original);
				
				//add lines that are not comments
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
	
	/**
	 * Given a line of code, removes any end bit comments it may contain
	 * For example: i++; //increments i by one
	 * Will return: i++;
	 * @param line is the line of code given
	 * @return Substring of the line with only code
	 */
	private String removeEndComments(String line) {
		boolean endOfCode = false;
		boolean inDoubleQuotes = false;
		boolean inSingleQuotes = false;
		List<Integer> inBrackets = new ArrayList<Integer>();
		//boolean inBrackets = false;
		
		//iterate through every char in the string of code
		for (int i = 0; i < line.length(); i++) {
			
			// code is in string mode, ignore everything except for end of string
			if (inDoubleQuotes) {
				if (line.charAt(i) == '"') {
					inDoubleQuotes = false;
				}
				continue;
			// code is not in string mode, and not in char mode
			// a double quotation mark indicates beginning start of string mode
			} else {
				if (line.charAt(i) == '"') {
					if (!inSingleQuotes) {
						inDoubleQuotes = true;
					}
				}
			}
			
			// code is in char mode, ignore everything except for end of char
			if (inSingleQuotes) {
				if (line.charAt(i) == '\'') {
					inSingleQuotes = false;
				}
				continue;
			// code is not in char mode, a single quotation mark indicates beginning of char mode
			} else {
				if (line.charAt(i) == '\'') {
					inSingleQuotes = true;
				}
			}
			
			//checks for bracket openings and closings
			if (!inDoubleQuotes && !inSingleQuotes) {
				if (line.charAt(i) == ')') {
					inBrackets.remove(0);
				}
				if (line.charAt(i) == '(') {
					inBrackets.add(0);
				}
			}
			
			// code is not in string or char mode or in between brackets and ';' or '{' or '}' is shown, end of code
			if (!inDoubleQuotes && !inSingleQuotes && inBrackets.isEmpty()) {
				if (line.charAt(i) == ';') {
					endOfCode = true;
				}
				if (line.charAt(i) == '{' || line.charAt(i) == '}') {
					endOfCode = true;
				}
			}
			
			//replace 'line' to be the purified code only (any comment after is excluded)
			if (endOfCode) {
				line = line.substring(0, i+1);
				break;
			}
		}
		
		//returned substring of code only
		return line;
	}
}
