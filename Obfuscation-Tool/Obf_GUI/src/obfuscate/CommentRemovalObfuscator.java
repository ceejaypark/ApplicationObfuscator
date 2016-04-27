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
 * Obfuscation technique to remove all comments from the source code of the
 * project to be obfuscated
 * 
 * @author cwu323, mcho588
 */
public class CommentRemovalObfuscator implements Obfuscator{
	
	@Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{
		
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			File file = fileEntry.getValue();
			
			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);
			
			StringBuilder fileString = new StringBuilder();
			String lineInFile;
			
			while ((lineInFile = fileInput.readLine()) != null) {
				String noBlank = lineInFile.replaceAll("\\s", "");
				if(!noBlank.startsWith("//")){
					fileString.append("\n" + lineInFile);
				}else{
					fileString.append("\n" + " ");
				}
				
				
			}
			
			String noComment = fileString.toString().replaceAll("/\\*(?:.|[\\n\\r])*?\\*/", " ");
			
			
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter fileOutput = new BufferedWriter(fileWriter);
			
			fileWriter.write(noComment);
			
			fileOutput.flush();
			fileOutput.close();
			fileInput.close();
			fileWriter.close();
			
		}
		
		return files;
	}
	@Override
	public String getName() {
		return "Comment Removal Obfuscation";
	}
}
