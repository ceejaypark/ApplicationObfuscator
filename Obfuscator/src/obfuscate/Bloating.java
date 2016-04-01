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

public class Bloating implements Obfuscater {

	@Override
	public HashMap<String, File> execute(HashMap<String, File> files) throws IOException {

		List<String> linesOfCode = new ArrayList<String>();
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);

			String lineInFile;

			while ((lineInFile = fileInput.readLine()) != null) {
				if (randomTrueOrFalse()) {
					replaceSpace(lineInFile);
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

	private String randomCommentGenerator()
	{
		return null;
	}

	private String replaceSpace(String lineInFile) {
		String[] lineArray = lineInFile.split("\\s+");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<lineArray.length;i++) {
			sb.append(lineArray[i]);
			sb.append(randomCommentGenerator());
		}
		String alteredLine = sb.toString();
		return alteredLine; 
	}

	private boolean randomTrueOrFalse() {
		double i = Math.random();
		if (i < 0.3) {
			return true;
		}
		return false;
	}

}
