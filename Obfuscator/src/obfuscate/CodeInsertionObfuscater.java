package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CodeInsertionObfuscater implements Obfuscater {
	
	List<MethodDeclarationLines> methodsDeclarations = new ArrayList<MethodDeclarationLines>();

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files) throws IOException {
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			
			FileInputStream in = new FileInputStream(fileEntry.getValue().getAbsolutePath());

			CompilationUnit cu;
			try {
				// parse the file
				cu = JavaParser.parse(in);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				in.close();
			}
			
			List<String> linesOfCode = new ArrayList<String>();
			List<Integer> braces = new ArrayList<Integer>();
			
			File file = fileEntry.getValue();
			
			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);
			
			String lineInFile;
			
			boolean inMethod = false;
			
			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				lineInFile = lineInFile.trim();
				
				
				if (inMethod) {
					//recognition of starting braces and ending braces to track end of class
					//end of class indicates stopping of code insertion
					if (braces.isEmpty()) {
						if (lineInFile.contains("{")) {
							braces.add(0);
						}
						if (lineInFile.contains("}")) {
							//class ends here
							inMethod = false;
							linesOfCode.add(original);
							continue;
						}
					} else {
						if (lineInFile.contains("{")) {
							braces.add(0);
						}
						if (lineInFile.contains("}")) {
							braces.remove(0);
						}
					}
					
					//select a random number between 1~100, if below 3, insert code
					int randomNum = getRandomNumber(100, 1);
					if (randomNum < 10) {
						linesOfCode.add(generateRandomCode(getStartWhitespace(original)));
					}
					linesOfCode.add(original);
					
				} else {
					//copy lines of code that aren't in class to its exact format
					linesOfCode.add(original);
					
					//start of class allows code insertion
					if (lineInFile.matches("((public|private|protected|static|final|native|synchronized|abstract|transient)+\\s)+[\\$_\\w\\<\\>\\[\\]]*\\s+[\\$_\\w]+\\([^\\)]*\\)?\\s*\\{?[^\\}]*\\}?")) {
						inMethod = true;
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
		
		return files;
	}
	
	private String getStartWhitespace(String s) {
		String whitespace = "";
		
		//iterates until no whitespace at the front
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				//single white space
				whitespace = whitespace + ' ';
			} else if (s.charAt(i) == '\t') {
				//tab is four white space
				whitespace = whitespace + "    ";
			} else { 
				break;
			}
		}
		
		//return whitespaces as string
		return whitespace;
	}
	
	private String generateRandomCode(String whitespace) {
		String randomCode = "";
		
		int numOfVariables = getRandomNumber(3, 1);
		ArrayList<String> variables = new ArrayList<String>();
		
		for (int i = 0; i < numOfVariables; i++) {
			variables.add("v" + getRandomNumber(999,100));
		}
		
		for (int i = 0; i < numOfVariables; i ++) {
			randomCode = randomCode + whitespace + "int " + variables.get(i) + " = " + getRandomNumber(100,1) + ";\n";
		}
		
		//add for loop or if statement start
		int forOrIf = getRandomNumber(2,1);
		if (forOrIf == 1) {
			//add for loop
			randomCode = randomCode + whitespace + "for (int i = 0; i < " + variables.get(getRandomNumber(variables.size(),1)-1) + "; i++) {\n";
		} else {
			randomCode = randomCode + whitespace + "if (" + variables.get(getRandomNumber(variables.size(),1)-1) + " < 2 || 30 == "
					+ variables.get(getRandomNumber(variables.size(),1)-1) + ") {\n";
		}
		
		for (int i = 0; i < getRandomNumber(5,1); i ++) {
			randomCode = randomCode + whitespace + "    " + variables.get(getRandomNumber(variables.size(),1)-1) + " = "
					+ variables.get(getRandomNumber(variables.size(),1)-1) + " + " +variables.get(getRandomNumber(variables.size(),1)-1) + ";\n";
		}
		
		randomCode = randomCode + whitespace + "}";

		return randomCode;
	}
	
	private int getRandomNumber(int maximum, int minimum) {
		Random rand = new Random();
		int number = rand.nextInt((maximum - minimum) + 1) + minimum;
		
		return number;
	}
	
	private class MethodVisitor extends VoidVisitorAdapter {
		
		@Override
        public void visit(MethodDeclaration md, Object arg) {
            MethodDeclarationLines mdl = new MethodDeclarationLines(md.getBeginLine(),md.getEndLine());
            methodsDeclarations.add(mdl);
        }
	}
	
	private class MethodDeclarationLines {
		private int startLine, endLine;
		
		public MethodDeclarationLines(int start, int end) {
			this.startLine = start;
			this.endLine = end;
		}
		
		public int getStartLine() {
			return startLine;
		}
		
		public int getEndLine() {
			return endLine;
		}
	}
}
