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

public class PictureEncryptionObfuscator implements Obfuscater {

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files,
			HashMap<String, File> blacklist, File manifest) throws IOException {

		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			int count = 0;
			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();

			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;
			boolean encryptNextLine = false;
			
			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				String cutDown = original.trim();
				if(encryptNextLine){
					String variableName = cutDown.split("String ")[0];
					String lineToAdd = "String " + variableName + " = DecryptImage.decrypt(" + count +")"; 
					
					count++;
					encryptNextLine = false;
					linesOfCode.add(lineToAdd);
					continue;
				}
				
				if (original.contains("@PictureObfuscate")){
					encryptNextLine = true;
					continue;
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
		
		
		return null;
	}

}
