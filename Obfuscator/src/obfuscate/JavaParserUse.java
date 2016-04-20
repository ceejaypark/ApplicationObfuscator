package obfuscate;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaParserUse {
	public static List<String> hello = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream("Test.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = JavaParser.parse(in);
		} finally {
			in.close();
		}

		// visit and print the methods names
		//new MethodVisitor().visit(cu, null);
		 new VariableVisitor().visit(cu, null);

		for (int i = 0; i < hello.size(); i++) {
			System.out.println(hello.get(i));
		}
	}

	/**
	 * Simple visitor implementation for visiting MethodDeclaration nodes.
	 */
	private static class MethodVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(MethodDeclaration n, Object arg) {
			// here you can access the attributes of the method.
			// this method will be called for all methods in this
			// CompilationUnit, including inner class methods
			System.out.println(n.getName());
			List<Parameter> p = n.getParameters();
			for (int i = 0; i < p.size(); i++) {
				System.out.println("With Param: " + p.get(i).getId().getName());
			}
		}
	}

	private static class VariableVisitor extends VoidVisitorAdapter {

		/*@Override
		public void visit(VariableDeclarationExpr n, Object arg) {
			List<VariableDeclarator> myVars = n.getVars();
			for (VariableDeclarator vars : myVars) {
				System.out.println("Variable Name: " + vars.getId().getName());
			}
		}*/
		
		@Override
		public void visit(ExpressionStmt n, Object arg) {
			System.out.println(n.getBeginLine() + " "+ n.getExpression().toString());
		}
	}
}
