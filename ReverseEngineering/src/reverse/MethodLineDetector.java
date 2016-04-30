package reverse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodLineDetector {
	private static String outputFileName = "output.txt";
	private static int minMethodLines = 7;
	
	private List<File> listOfJavaFiles = new ArrayList<File>();
	private String outputString = "";
	
	public MethodLineDetector(List<File> files) {
		for (File f: files) {
			if (getFileExtension(f).equals("java")) {
				listOfJavaFiles.add(f);
			}
		}
	}
	
	public void detectSuspiciousMethods() throws IOException, ParseException {
		File outputFile = new File(outputFileName);
		
		CompilationUnit cu;
		
		for (File f: listOfJavaFiles) {
			FileInputStream in = new FileInputStream(f.getCanonicalPath());
			
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
		
		FileWriter fileWriter = new FileWriter(outputFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		fileWriter.write(outputString);
		
		bufferedWriter.flush();
		bufferedWriter.close();
		fileWriter.close();
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
		}
	}
}
