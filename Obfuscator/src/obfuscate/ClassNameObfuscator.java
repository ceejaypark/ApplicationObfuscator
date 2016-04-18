package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassNameObfuscator implements Obfuscater{
	// Counts number of different class names that have been obfuscated
	static int name_counter = 65;

	@Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{
		//List<String> linesOfCode = new ArrayList<String>();
		// List containing obfuscated names - So we don't obfuscated class names that have
		// already been obfuscated
		List<String> obfuscatedNames = new ArrayList<String>();
		// Hashmap containing the old name as key and new name as value
		HashMap<String, String> classNames = new HashMap<String,String>();
		HashMap<String, File> classNameHM = new HashMap<String, File>();
		int iterationCounter = 0;


		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();
			String canonicalPath = fileEntry.getKey();
			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);
			int classCounter = 0;
			String mainClassName = file.getName();
			// New path for the new file. 
			String newPath = new String();
			String lineInFile;
			while ((lineInFile = fileInput.readLine()) != null) {
				String className = classNameReturner(lineInFile);

				if (className != null) {
					String obfName = asciiToString(name_counter);
					obfuscatedNames.add(obfName);
					classNames.put(className, obfName);
				}

				/*


				boolean existsInHash = false;
				// get the class name in the line. If there is no class name, the 
				// string should be null
				String className = classNameReturner(lineInFile);
				// if it isn't null and if the name hasn't been obfuscated before
				// iterate through hashmap to check if name exists
				if ((className != null) && (obfuscatedChecker(obfuscatedNames, className) == false)) {
					for (String key : classNames.keySet()) {
						// if the name already exists, check the value of the key 
						// which is the new name of the class
						if (className.equals(key)) {
							// obf is the new name
							String obfName = classNames.get(className);
							lineInFile = renameClass(obfName, className, lineInFile);
							existsInHash = true;
						}
					}
					// If it doesn't exist in the hash, create a new name for it
					// and add the class name to the list of encountered classes
					// and put it in the hashmap and also rename it in the line of code
					if (existsInHash == false) {
						String obfName = asciiToString(name_counter);
						obfuscatedNames.add(obfName);
						classNames.put(className, obfName);
						lineInFile = renameClass(obfName, className, lineInFile);
						if (classCounter == 0) {
							newPath = canonicalPath.replaceAll(mainClassName, obfName + ".java");
							classCounter++;
						}
					}
				}
				// Iterate through all class names encountered and replace in line of code if
				// necessary
				for (String key: classNames.keySet()) {
					lineInFile = lineInFile.replaceAll(key, classNames.get(key));
				}
				// Add the line of code to the list of lines
				linesOfCode.add(lineInFile);
			}
			File nameChange = new File(newPath);
			FileWriter fileWriter = new FileWriter(nameChange);
			BufferedWriter fileOutput = new BufferedWriter(fileWriter);
			for (String s: linesOfCode) {
				s = s + "\n";
				fileOutput.write(s);
			}
			fileOutput.flush();
			fileOutput.close();
			fileInput.close();
			if (classNameHM.containsKey(nameChange.getAbsolutePath()) == false) {
				classNameHM.put(nameChange.getAbsolutePath(), nameChange);
			}

				 */
			}
		}
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			if (blacklist.containsKey(fileEntry.getValue().getCanonicalPath()) != true){
				List<String> linesOfCode = new ArrayList<String>();
				File file = fileEntry.getValue();
				String canonicalPath = fileEntry.getKey();
				FileReader fileReader = new FileReader(file);
				BufferedReader fileInput = new BufferedReader(fileReader);
				int classCounter = 0;
				String mainClassName = file.getName();
				// New path for the new file. 
				String newPath = new String();
				String lineInFile;
				boolean fileChanged = false;
				while ((lineInFile = fileInput.readLine()) != null) {
					String className = classNameReturner(lineInFile);

					if ((className != null) && (obfuscatedChecker(obfuscatedNames, className) == false)) {
						for (String key : classNames.keySet()) {
							// if the name already exists, check the value of the key 
							// which is the new name of the class
							if (className.equals(key)) {
								// obf is the new name
								String obfName = classNames.get(className);
								lineInFile = renameClass(obfName, className, lineInFile);
								if (fileChanged == false) {
									newPath = canonicalPath.replaceAll(mainClassName, obfName + ".java");
									fileChanged = true;
								}
							}
						}
					}
					for (String key: classNames.keySet()) {
						lineInFile = lineInFile.replaceAll(key, classNames.get(key));
					}
					linesOfCode.add(lineInFile);

				}
				System.out.println(newPath);
				File nameChange = new File(newPath);
				FileWriter fileWriter = new FileWriter(nameChange);
				BufferedWriter fileOutput = new BufferedWriter(fileWriter);
				for (String s: linesOfCode) {
					s = s + "\n";
					fileOutput.write(s);
				}
				fileOutput.flush();
				fileOutput.close();
				fileInput.close();
				if (classNameHM.containsKey(nameChange.getCanonicalPath()) == false) {
					classNameHM.put(nameChange.getCanonicalPath(), nameChange);
				}
			}
		}



		// Updates blacklist file
		for (Map.Entry<String, File> fileEntry : blacklist.entrySet()) {
			File file = fileEntry.getValue();
			System.out.println(fileEntry.getValue());
			replaceClass(file,classNames);
		}

		replaceClass(manifest,classNames);


		return classNameHM;


	}


	/*
	 * Takes a file and replaces all the class names with hashmap values
	 */
	private void replaceClass(File file, HashMap<String,String> classNames) {
		List<String> linesOfCode = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;

			while ((lineInFile = fileInput.readLine()) != null) {
				for(String s: classNames.keySet()) {
					lineInFile.replaceAll(s, classNames.get(s));
				}
				linesOfCode.add(lineInFile);
			}

			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter fileOutput = new BufferedWriter(fileWriter);

			for (String s: linesOfCode) {
				s = s + "\n";
				fileOutput.write(s);
			}

			fileOutput.flush();
			fileOutput.close();
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method that rewrites a line of code with a class name in it
	 */
	private String renameClass (String newName, String oldName, String codeLine) {		
		String result = codeLine.replaceAll(oldName, newName);
		return result;
	}

	/*
	 * Method that checks whether a class has been obfuscated already or not.
	 */
	private boolean obfuscatedChecker(List<String> list, String className) {
		for (String s: list) {
			if (s.equals(className)){
				return true;
			}
		}
		return false;
	}

	/*
	 * Method that should check if there is a class declaration or interface declaration
	 * and returns the name of it. Returns null if the line of code does not have either of these
	 */
	private String classNameReturner (String codeLine) {
		String[] line_array = codeLine.split("\\s+");
		int length = line_array.length;
		String class_Name = null;

		for (int i = 0; i < length ; i++ ) {
			if (line_array[i].equals("class") || line_array[i].equals("interface") || line_array[i].equals("enum")) {
				class_Name = line_array[i+1];
			}
		}
		return class_Name;

	}


	/*
	 * converts int into a string letter. Used for the meaningless names of classes.
	 */
	private String asciiToString (int ascii)  {
		String letter = Character.toString((char)ascii);
		name_counter++;
		if (name_counter > 90 && name_counter < 97) {
			name_counter = 97;
		}
		return letter;
	}


}
