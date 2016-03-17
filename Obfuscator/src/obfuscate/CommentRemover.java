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

public class CommentRemover implements Obfuscater{

	public static void main(String[] args) throws IOException{
		List<String> lines = new ArrayList<String>();
		
		File f = new File("test.java");
		
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		
		boolean isComment = false;
		
		while ((line = br.readLine()) != null) {
			String original = line;
			line = line.trim();
			
			//skip but leave blank lines
			if (line.length() == 0) {
				lines.add(original);
				continue;
			}
			
			//if the line begins with '//'
			if (line.charAt(0) == '/' && line.charAt(1) == '/' ) {
				continue;
			}
			
			//if the line begins with '/*', anything after is a comment
			if (line.charAt(0) == '/' && line.charAt(1) == '*') {
				isComment = true;
				//if the line ends with '*/', comment ended
				if (line.charAt(line.length() - 1) == '/' && line.charAt(line.length()-2) == '*') {
					isComment = false;
				}
				continue;
			}
			
			//if the line ends with '*/'
			if (line.charAt(line.length() - 1) == '/' && line.charAt(line.length()-2) == '*') {
				//if currently processing comment, comment ended
				if (isComment) {
					isComment = false;
				}
			}
			
			//if the line is still a comment ignore
			if (isComment) {
				continue;
			}
			
			original = removeEndComments(original);
			
			//add lines that are not comments
			lines.add(original);
		}
		
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (String s : lines) {
			s = s + "\n";
			bw.write(s);
		}
		bw.flush();
		bw.close();
	}
	
	@Override
	public HashMap<String, File> execute(HashMap<String, File> Files) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Given a line of code, removes any end bit comments it may contain
	 * For example: i++; //increments i by one
	 * Will return: i++;
	 * @param line is the line of code given
	 * @return Substring of the line with only code
	 */
	private static String removeEndComments(String line) {
		boolean endOfCode = false;
		boolean inDoubleQuotes = false;
		boolean inSingleQuotes = false;
		
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
			
			// code is not in string or char mode and ';' is shown, end of code
			if (!inDoubleQuotes && !inSingleQuotes) {
				if (line.charAt(i) == ';') {
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
