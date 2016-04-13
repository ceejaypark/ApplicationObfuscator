package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Removes lines with Log (for android) and System.out.println (for Java).
 * WILL REMOVE ANY CODE IN SAME LINE LOG AND SYSTEM.OUT.PRINTLN IS IN!!!
 * 
 * @author jkim506
 *
 */

public class LogDeleteObfuscator implements Obfuscater {

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files)
			throws IOException {

		//Delete logs
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {

			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();

			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;

			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				
				if (original.contains("Log.") || original.contains("System.out.println"))
					continue;
				
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

}
