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

public class CodeInsertionObfuscater implements Obfuscater {
	public static ArrayList<String> codeToBeInserted = new ArrayList<String>();
	
	public CodeInsertionObfuscater() {
		codeToBeInserted.add("int 123 = 5;");
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("test.java");
		
		FileReader fileReader = new FileReader(file);
		BufferedReader fileInput = new BufferedReader(fileReader);
		
		List<String> linesOfCode = new ArrayList<String>();
		List<Integer> braces = new ArrayList<Integer>();
		
		String lineInFile;
	
		boolean inClass = false;
		
		while ((lineInFile = fileInput.readLine()) != null) {
			String original = lineInFile;
			lineInFile = lineInFile.trim();
			
			if (inClass) {
				
				//end of class indicates stopping of code insertion
				if (braces.isEmpty()) {
					if (lineInFile.contains("{")) {
						braces.add(0);
					}
					if (lineInFile.contains("}")) {
						//class ends here
						inClass = false;
					}
				} else {
					if (lineInFile.contains("{")) {
						braces.add(0);
					}
					if (lineInFile.contains("}")) {
						braces.remove(0);
					}
				}
				
				linesOfCode.add(codeToBeInserted.get(0));
				linesOfCode.add(original);
				//random number generator to decide there should code inserted after
			} else {
				//copy lines of code that aren't in class to its exact format
				linesOfCode.add(original);
				String[] lineSplit = lineInFile.split(" ");
				
				//start of class allows code insertion
				if (isClassDeclaration(lineSplit)) {
					inClass = true;
				}
				continue;
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

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static boolean isClassDeclaration(String[] lineOfCode) {
		boolean isClassDeclaration = false;
		
		//if the first word is a access modifier
		if (lineOfCode[0].equals("public") || lineOfCode[0].equals("private") || lineOfCode[0].equals("protected")) {
			//followed by second word 'class' is a class declaration
			if (lineOfCode[1].equals("class")) {
				isClassDeclaration =  true;
			} else if (lineOfCode[1].equals("static")) {
				//case for static classes
				if (lineOfCode[2].equals("class")) {
					isClassDeclaration = true;
				}
			}
		}
		
		//class with no modifiers
		if (lineOfCode[0].equals("class")) {
			isClassDeclaration = true;
		}
		
		return isClassDeclaration;
	}
}
