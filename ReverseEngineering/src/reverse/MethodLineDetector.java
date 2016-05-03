package reverse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodLineDetector {
	private static String outputFileName = "perclass.txt";
	private static String outputFileName2 = "longlist.txt";
	private static int minMethodLines = 7;
	
	private List<File> listOfJavaFiles = new ArrayList<File>();
	private String outputString = "";
	private String outputString2 = "";
	private List<ObfMethod> listOfMethods = new ArrayList<ObfMethod>();
	private String currentClass = "";
	
	public MethodLineDetector(List<File> files) {
		for (File f: files) {
			if (getFileExtension(f).equals("java")) {
				listOfJavaFiles.add(f);
			}
		}
	}
	
	public void detectSuspiciousMethods() throws IOException, ParseException {
		File outputFile = new File(outputFileName);
		File outputFile2 = new File(outputFileName2);
		
		CompilationUnit cu;
		
		for (File f: listOfJavaFiles) {
			FileInputStream in = new FileInputStream(f.getCanonicalPath());
			
			currentClass = f.getName();
			outputString = outputString + f.getName() + "\n\n";
			try {
				cu = JavaParser.parse(in);
			} finally {
				in.close();
			}
			new MethodVisitor().visit(cu, null);
			
			outputString = outputString + "--------------------------------------------------------"
					+ "----------------\n";
			
		}
		
		Collections.sort(listOfMethods);
		
		for (ObfMethod om : listOfMethods) {
			String methodName = om.getMethodName();
			int numOfLines = om.getLineNumber();
			String className = om.getClassName();
			outputString2 = outputString2 + methodName + "\t\t" + numOfLines + "\t\t" + className + "\n";
		}
		
		FileWriter fileWriter = new FileWriter(outputFile);
		FileWriter fileWriter2 = new FileWriter(outputFile2);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
		
		fileWriter.write(outputString);
		fileWriter2.write(outputString2);
		
		bufferedWriter.flush();
		bufferedWriter.close();
		fileWriter.close();
		bufferedWriter2.flush();
		bufferedWriter2.close();
		fileWriter2.close();
	}
	
	/**
	 * Get the extension of a file
	 * @param file
	 * @return
	 */
	private static String getFileExtension(File file) {
		String fileName = file.getName();
		String[] nameSplit = fileName.split("\\.");
		
		//if file has no extension return nothing
		if (nameSplit.length < 2) {
			return "";
		}
		
		return nameSplit[nameSplit.length-1];
	}
	
	private class MethodVisitor extends VoidVisitorAdapter {
		
		@Override
		public void visit(MethodDeclaration md, Object arg) {
			int start = md.getBeginLine();
			int end = md.getEndLine();
			int length = end - start;
			
			if (length < minMethodLines) {
				String methodAndLine = start + "~" + end;
				methodAndLine = methodAndLine + ":    " + md.getDeclarationAsString();
				outputString = outputString + methodAndLine + "\n";
			}
			
			ObfMethod om = new ObfMethod(md.getName(),start,end,currentClass);
			listOfMethods.add(om);
		}
	}
	
	private class ObfMethod implements Comparable<ObfMethod> {
		private String methodName;
		private int lineNumber;
		private int startLine;
		private int endLine;
		private String inClassName;
		
		public ObfMethod(String name, int start, int end, String className) {
			this.methodName = name;
			this.startLine = start;
			this.endLine = end;
			this.lineNumber = end - start;
			this.inClassName = className;
		}
		
		public String getMethodName() {
			return this.methodName;
		}
		
		public int getLineNumber() {
			return this.lineNumber;
		}
		
		public int getStartLine() {
			return this.startLine;
		}
		
		public int getEndLine() {
			return this.endLine;
		}
		
		public String getClassName() {
			return this.inClassName;
		}
		
		@Override
		public int compareTo(ObfMethod compareMethod) {
			int thisMethodLineNumber = this.endLine - this.startLine;
			int compareMethodLineNumber = compareMethod.endLine - compareMethod.startLine;
			
			return compareMethodLineNumber - thisMethodLineNumber;
		}
	}
}
