package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Removes lines with Log (for android) and System.out.println (for Java).
 * Will replace the existing Log. and System outs with a useless output
 * 

 * @author jkim506
 *
 */

public class LogDeleteObfuscator implements Obfuscator {

	private SecureRandom random = new SecureRandom();
	
	@Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{

		//Delete logs
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {

			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();

			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;

			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				
				if (original.contains("Log.")){
					original = "Log.d(\"NothingToSeeHere\", \"Downloading ram...\");";
				}
				else if (original.contains("System.out.println")){
					original.replace("\\((.*?)\\)", "pikabu");
				}
				else if (original.contains(".printStackTrace")){
					original = "int " + generateRandomString() + " = 1;";
				}				

				
				linesOfCode.add(original);
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

	public String generateRandomString() {
		return "a" + new BigInteger(130, random).toString(32);
	}
	@Override
	public String getName() {
		return "Log Delete Obfuscation";
	}
	
}
