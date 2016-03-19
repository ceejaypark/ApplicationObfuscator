package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassNameObfuscator implements Obfuscater{
	// Counts number of different class names that have been obfuscated
	static int name_counter = 65;

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files) throws IOException {
		List<String> linesOfCode = new ArrayList<String>();

		// Hashmap containing the old name as key and new name as value
		HashMap<String, String> classNames = new HashMap<String,String>();

		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();

			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;

			while ((lineInFile = fileInput.readLine()) != null) {
				boolean existsInHash = false;
				// get the class name in the line. If there is no class name, the 
				// string should be null
				String className = classNameReturner(lineInFile);
				// if it isn't null, iterate through hashmap to check if name exists
				if (className != null) {
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
					// and put it in the hashmap and also rename it in code
					if (existsInHash == false) {
						String obfName = asciiToString(name_counter);
						classNames.put(className, obfName);
						lineInFile = renameClass(obfName, className, lineInFile);
					}
					lineInFile.renameClass(className, classNames.get(className));
					
				}
				else if (className == null) {
					
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
		}
		return files;
	}
	
	/*
	 * Method that rewrites a line of code with a class name in it
	 */
	private String renameClass (String newName, String oldName, String codeLine) {		
		String result = codeLine.replaceAll(oldName, newName);
		return result;
	}

	
	private String classNameReturner (String codeLine) {
		String[] line_array = codeLine.split("\\s+");
		int length = line_array.length;
		String class_Name = null;

		for (int i = 0; i < length ; i++ ) {
			if (line_array[i].equals("class") || line_array[i].equals("interface")) {
				class_Name = line_array[i+1];
			}
		}
		return class_Name;

	}


	private String asciiToString (int ascii)  {
		String letter = Character.toString((char)ascii);
		name_counter++;
		if (name_counter > 90 && name_counter < 97) {
			name_counter = 97;
		}
		return letter;
	}


}
