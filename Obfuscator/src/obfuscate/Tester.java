package obfuscate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Tester {
	public static void main(String[] args) throws IOException {
		
		FileInputStream in = new FileInputStream("Test.java");
		
		CompilationUnit cu;
		try {
			// parse the file
			cu = JavaParser.parse(in);
			new MethodVisitor().visit(cu, null);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			in.close();
		}
	}
	
	private static class MethodVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(MethodDeclaration md, Object arg) {
			
			md.getBody();
			System.out.println(md.getBeginLine() + " : " + md.getEndLine());
		}
	}
}
