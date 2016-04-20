package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LayoutObfuscater implements Obfuscater {
	// Counts number of different class names that have been obfuscated
	static int name_counter = 65;
	static String method_name = "Mn";

	private HashMap<String, String> classNames = new HashMap<String, String>();
	private HashMap<String, String> methodNames = new HashMap<String, String>();

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files, HashMap<String, File> blacklist, File manifest)
			throws IOException {

		for (Map.Entry<String, File> fileEntry : files.entrySet()) {

			FileInputStream in = new FileInputStream(fileEntry.getValue());

			CompilationUnit cu = null;
			// parse the file
			try {
				cu = JavaParser.parse(in);
				
				new ClassVisitor().visit(cu, null);
				new ClassTypeVisitor().visit(cu, null);
				new MethodVisitor().visit(cu, null);
				new MethodCallVisitor().visit(cu, null);
				
				FileWriter fileWriter = new FileWriter(fileEntry.getValue());
				BufferedWriter fileOutput = new BufferedWriter(fileWriter);
				fileOutput.write(cu.toString());
				
				fileOutput.flush();
				fileOutput.close();
				fileWriter.close();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				in.close();
			}

		}

		return files;
	}

	private class MethodVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(MethodDeclaration md, Object arg) {
			if (md.getName().equals("main")) {
				return;
			}
			String methodName = md.getName();
			if (!methodNames.containsKey(methodName)) {
				String newName = generateMethodName();
				methodNames.put(methodName, newName);
			}
			md.setName(methodNames.get(methodName));
		}
	}

	private class MethodCallVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(MethodCallExpr mce, Object arg) {
			if (methodNames.containsKey(mce.getName())) {
				mce.setName(methodNames.get(mce.getName()));
			}
		}
	}

	private class ClassVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(ClassOrInterfaceDeclaration cd, Object arg) {
			String className = cd.getName();
			String newName = generateClassName();
			classNames.put(className, newName);
			cd.setName(classNames.get(className));
		}
	}

	private class ClassTypeVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(ClassOrInterfaceType coit, Object arg) {
			if (classNames.containsKey(coit.getName())) {
				coit.setName(classNames.get(coit.getName()));
			}
		}
	}

	private class VariableVisitor extends VoidVisitorAdapter {

		@Override
		public void visit(VariableDeclarationExpr n, Object arg) {
			List<VariableDeclarator> myVars = n.getVars();
			for (VariableDeclarator vars : myVars) {
				// System.out.println(vars.getId().getBeginLine() + " : " +
				// vars.toString());
			}
		}
	}

	/*
	 * private void readMethods(CompilationUnit cu) { List<TypeDeclaration>
	 * types = cu.getTypes(); for (TypeDeclaration type : types) {
	 * List<BodyDeclaration> members = type.getMembers(); for (BodyDeclaration
	 * member : members) { if (member instanceof MethodDeclaration) {
	 * MethodDeclaration method = (MethodDeclaration) member; if
	 * (method.getName().equals("main")) { continue; } String methodName =
	 * method.getName(); if (!methodNames.containsKey(methodName)) { String
	 * newName = generateMethodName(); methodNames.put(methodName, newName); }
	 * method.setName(methodNames.get(methodName)); } } } }
	 * 
	 * private void readClasses(CompilationUnit cu) { List<TypeDeclaration>
	 * types = cu.getTypes(); for (TypeDeclaration type : types) { if (type
	 * instanceof ClassOrInterfaceDeclaration) { ClassOrInterfaceDeclaration
	 * classOrInterface = (ClassOrInterfaceDeclaration) type; String className =
	 * classOrInterface.getName(); String newName = generateClassName();
	 * classNames.put(className, newName); classOrInterface.setName(newName); }
	 * List<BodyDeclaration> members = type.getMembers(); for (BodyDeclaration
	 * member : members) { if (member instanceof ClassOrInterfaceDeclaration) {
	 * ClassOrInterfaceDeclaration classOrInterface =
	 * (ClassOrInterfaceDeclaration) member; String className =
	 * classOrInterface.getName(); String newName = generateClassName();
	 * classNames.put(className, newName); classOrInterface.setName(newName); }
	 * } } }
	 * 
	 * private void readVariables(CompilationUnit cu) { List<TypeDeclaration>
	 * types = cu.getTypes(); for (TypeDeclaration type : types) {
	 * List<BodyDeclaration> members = type.getMembers(); for (BodyDeclaration
	 * member : members) { if (member instanceof FieldDeclaration) {
	 * FieldDeclaration field = (FieldDeclaration) member;
	 * 
	 * System.out.println(field.getType()); // Print the field's name
	 * System.out.println(field.getVariables().get(0).getId().getName()); //
	 * Print the field's init value, if not null Object initValue =
	 * field.getVariables().get(0).getInit(); if (initValue != null) {
	 * System.out.println(field.getVariables().get(0).getInit().toString()); } }
	 * } } }
	 */

	private String generateClassName() {
		String letter = Character.toString((char) name_counter);
		name_counter++;
		if (name_counter > 90 && name_counter < 97) {
			name_counter = 97;
		}
		return letter;
	}

	private String generateMethodName() {
		String returnName = method_name;
		method_name = method_name + "Mn";
		return returnName;
	}
}
