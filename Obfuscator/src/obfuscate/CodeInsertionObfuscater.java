package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class CodeInsertionObfuscater implements Obfuscater {
	int deadCodeStatus = 0;
	boolean deadMethodGenerated = false;
	boolean misleadCreated = false;
	HashMap<String, File> files = new HashMap<String, File>();

	@Override
	public HashMap<String, File> execute(HashMap<String, File> filesInput,
			HashMap<String, File> blacklist, File manifest) throws IOException {

		this.files = filesInput;

		createMislead();

		for (Map.Entry<String, File> fileEntry : files.entrySet()) {

			setDeadCodeStatus();
			deadMethodGenerated = false;

			List<String> linesOfCode = new ArrayList<String>();
			List<Integer> methodBraces = new ArrayList<Integer>();
			List<Integer> classBraces = new ArrayList<Integer>();

			File file = fileEntry.getValue();

			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;

			boolean inMethod = false;
			boolean inClass = false;

			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				lineInFile = lineInFile.trim();

				if (inClass) {
					// recognition of starting braces and ending braces to track
					// end of class
					// end of class indicates stopping of code insertion
					if (classBraces.isEmpty()) {
						if (lineInFile.contains("{")) {
							classBraces.add(0);
						}
						if (lineInFile.contains("}")) {
							// class ends here
							inClass = false;
							linesOfCode.add(original);
							continue;
						}
					} else {
						if (lineInFile.contains("{")) {
							classBraces.add(0);
						}
						if (lineInFile.contains("}")) {
							classBraces.remove(0);
						}
					}
				} else {
					// regex indicates a class declaration, method can be
					// inserted
					if (lineInFile
							.matches(".*class\\s+(\\w+)(\\s+extends\\s+(\\w+))?(\\s+implements\\s+([\\w,\\s]+))?\\s*\\{.*$")) {
						inClass = true;
					}
				}

				if (inMethod) {
					// recognition of starting braces and ending braces to track
					// end of method
					// end of method indicates stopping of code insertion
					if (methodBraces.isEmpty()) {
						if (lineInFile.contains("{")) {
							methodBraces.add(0);
						}
						if (lineInFile.contains("}")) {
							// class ends here
							inMethod = false;
							linesOfCode.add(original);
							continue;
						}
					} else {
						if (lineInFile.contains("{")) {
							methodBraces.add(0);
						}
						if (lineInFile.contains("}")) {
							methodBraces.remove(0);
						}
					}

					// select a random number between 1~100, if below 10, insert
					// code
					int randomNum = getRandomNumber(100, 1);
					if (randomNum < 10) {
						linesOfCode
								.add(generateRandomCode(getStartWhitespace(original)));
					}
					if (randomNum <= 100) {
						// WRITE RANDOM IF STATEMENT DEAD CODE LOGIC HERE
						if (deadMethodGenerated) {

							String noWhiteSpace = original.replaceAll("\\s+",
									"");
							if (noWhiteSpace.startsWith("if")
									|| noWhiteSpace.startsWith("else if")) {
								linesOfCode.add(generateDeadIf(noWhiteSpace));
							}
						}
					}
					linesOfCode.add(original);

				} else {
					// copy lines of code that aren't in class to its exact
					// format

					// Testing for annotations
					String x = lineInFile.replaceAll("\\s", "");

					if (x.startsWith("@") && !inMethod && inClass) {
						if (!deadMethodGenerated) {
							if (getRandomNumber(4, 0) <= 2) {
								linesOfCode.add(generateDeadMethod());
								deadMethodGenerated = true;
							}
						}
					}

					// regex indicates method declaration, so code insertion is
					// enabled
					if (lineInFile
							.matches("((public|private|protected|static|final|native|synchronized|abstract|transient)+\\s)+[\\$_\\w\\<\\>\\[\\]]*\\s+[\\$_\\w]+\\([^\\)]*\\)?\\s*\\{?[^\\}]*\\}?")) {
						if (inClass
								&& !linesOfCode.get(linesOfCode.size() - 1)
										.contains("@Override")) {
							if (!deadMethodGenerated) {
								if (getRandomNumber(4, 0) <= 2) {
									linesOfCode.add(generateDeadMethod());
									deadMethodGenerated = true;
								}
							}
						}

						inMethod = true;
					}

					linesOfCode.add(original);
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
			fileWriter.close();
		}

		return files;
	}

	private String getStartWhitespace(String s) {
		String whitespace = "";

		// iterates until no whitespace at the front
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				// single white space
				whitespace = whitespace + ' ';
			} else if (s.charAt(i) == '\t') {
				// tab is four white space
				whitespace = whitespace + "    ";
			} else {
				break;
			}
		}

		// return whitespaces as string
		return whitespace;
	}

	private String generateRandomCode(String whitespace) {
		String randomCode = "";

		int numOfVariables = getRandomNumber(3, 1);
		ArrayList<String> variables = new ArrayList<String>();

		for (int i = 0; i < numOfVariables; i++) {
			variables.add("v" + getRandomNumber(999, 100));
		}

		for (int i = 0; i < numOfVariables; i++) {
			randomCode = randomCode + whitespace + "int " + variables.get(i)
					+ " = " + getRandomNumber(100, 1) + ";\n";
		}

		// add for loop or if statement start
		int forOrIf = getRandomNumber(2, 1);
		if (forOrIf == 1) {
			// add for loop
			randomCode = randomCode
					+ whitespace
					+ "for (int fjhdafcjvklzxjcklzx = 0; fjhdafcjvklzxjcklzx < "
					+ variables.get(getRandomNumber(variables.size(), 1) - 1)
					+ "; fjhdafcjvklzxjcklzx++) {\n";
		} else {
			randomCode = randomCode + whitespace + "if ("
					+ variables.get(getRandomNumber(variables.size(), 1) - 1)
					+ " < 2 || 30 == "
					+ variables.get(getRandomNumber(variables.size(), 1) - 1)
					+ ") {\n";
		}

		for (int i = 0; i < getRandomNumber(5, 1); i++) {
			randomCode = randomCode + whitespace + "    "
					+ variables.get(getRandomNumber(variables.size(), 1) - 1)
					+ " = "
					+ variables.get(getRandomNumber(variables.size(), 1) - 1)
					+ " + "
					+ variables.get(getRandomNumber(variables.size(), 1) - 1)
					+ ";\n";
		}

		randomCode = randomCode + whitespace + "}";

		return randomCode;
	}

	private int getRandomNumber(int maximum, int minimum) {
		Random rand = new Random();
		int number = rand.nextInt((maximum - minimum) + 1) + minimum;

		return number;
	}

	// ====================================================================================
	// Dead code generation methods
	private String generateDeadMethod() {
		String stringMethod = "private boolean getClassStatus(String input){\n"
				+ "\tif (input.length() == 16){\n" + "\t\treturn true;\n"
				+ "\t}\n" + "\treturn false;\n" + "}";

		String intMethod = "private boolean getClassStatus(int input){\n"
				+ "\tif ((((double)input)/2) % 2 != 1){\n"
				+ "\t\treturn true;\n" + "\t}\n" + "\treturn false;\n" + "}";

		if (deadCodeStatus == 0) {
			return intMethod;
		} else if (deadCodeStatus == 1) {
			return stringMethod;
		} else {
			return stringMethod + "\n" + intMethod;
		}
	}

	private void setDeadCodeStatus() {
		this.deadCodeStatus = getRandomNumber(2, 0);
	}

	private int deadInt() {
		int result = 0;
		while ((((double) result) / 2) % 2 != 1) {
			result = getRandomNumber(1414, 0);
		}
		return result;
	}

	private String deadString() {
		int length = getRandomNumber(20, 1);
		while (length == 16) {
			length = getRandomNumber(20, 1);
		}

		char[] output = new char[length];
		for (int i = 0; i < output.length; i++) {
			int rand = getRandomNumber(122, 65);
			while (rand >= 91 && rand <= 96) {
				rand = getRandomNumber(122, 65);
			}

			output[i] = (char) rand;
		}
		return output.toString();
	}

	private String getDeadInput() {
		if (this.deadCodeStatus == 0) {
			return deadInt() + "";
		} else if (this.deadCodeStatus == 1) {
			return "\"" + deadString() + "\"";
		} else {
			if (getRandomNumber(1, 0) == 0)
				return deadInt() + "";
			else
				return "\"" + deadString() + "\"";
		}
	}

	private String generateDeadIf(String original) {
		String output;
		if (original.startsWith("if")) {
			output = "if(getClassStatus(" + getDeadInput() + ")){\n"
					+ "Mislead.getInstance().addStatus()"
					+ "}\n"
					+ "else ";
		} else {
			output = "else if(getClassStatus(" + getDeadInput() + ")){\n"
					+ "Mislead.getInstance().addStatus()"
					+ "}\n";
		}
		return output;
	}

	private void createMislead() throws IOException {
		File misleadTarget = new File(
				MainObfuscater.sourceFolder.getCanonicalPath()
						+ "\\Mislead.java");
		File localVersion = new File(".\\resources\\Mislead.java");

		Files.copy(localVersion.getCanonicalFile().toPath(),
				misleadTarget.toPath(), StandardCopyOption.REPLACE_EXISTING);

		Scanner sc = new Scanner(misleadTarget);
		String content = sc.useDelimiter("\\Z").next();
		sc.close();

		content = content.replaceAll("TOBEREPLACED", MainObfuscater.srcPackage);

		Files.write(misleadTarget.getCanonicalFile().toPath(),
				content.getBytes(StandardCharsets.UTF_8));
		files.put(misleadTarget.getCanonicalPath(), misleadTarget);
	}
}
