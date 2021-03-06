package obfuscate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import supportClasses.MainObfRefactored;

/**
 * This obfuscation method removes packages inside the project to be obfuscated
 * 
 * @author jkim506
 *
 */
public class DirectoryFlattenerObfuscator implements Obfuscator{
	
	private HashMap<String, Boolean> importsToDelete = new HashMap<String, Boolean>();
	private File manifest;
	@Override
	public HashMap<String,File> execute(HashMap<String,File> files, HashMap<String,File> blacklist,  File manifest ) throws IOException{
		
		this.manifest = manifest;
		
		HashMap<String, File> newFiles = new HashMap<String, File>();
		
		String output = MainObfRefactored.sourceFolder.getCanonicalPath();
		int folderCount = output.split("\\\\").length;
		
		for (Map.Entry<String, File> fileEntry : files.entrySet()) {
			String path = fileEntry.getKey();
			File file = fileEntry.getValue();
			
			int folderCountForThisFile = path.split("\\\\").length;
			
			//if there exists a subdirectory under the target folder
			if(folderCountForThisFile - folderCount > 1){
				String fileNameOnly = file.getName();				
				path = output + "\\\\" + fileNameOnly;				
				//make a new file
				File newFile = new File(path);
				//copy the original file to the new file
				Files.copy(file.toPath(), newFile.toPath());
				//delete the original file
				file.delete();
				//set file to the new file
				file = newFile;
			}
			
			newFiles.put(path, file);
		}
		//Get the output directory
		File outputFileDirectory = new File(MainObfRefactored.sourceFolder.getCanonicalPath());
		
		//Delete all subdirectories and add it to packages hashmap
		for(Path p : Files.walk(outputFileDirectory.toPath()).
		        sorted((a, b) -> b.compareTo(a)).
		        toArray(Path[]::new))
		{
			if(p.toFile().isDirectory() && !(p.equals(outputFileDirectory.toPath()))){
				String[] tempDirectories = p.toString().split("\\\\");
				String key = tempDirectories[tempDirectories.length-1];
				importsToDelete.put(key, new Boolean(true));
				Files.delete(p); 
			}
		}
		
		files = newFiles;
		
		//Walk through all files to get rid of directory references.
		for(Map.Entry<String, File> fileEntry : files.entrySet()){
			
			List<String> linesOfCode = new ArrayList<String>();
			File file = fileEntry.getValue();
			
			FileReader fileReader = new FileReader(file);
			BufferedReader fileInput = new BufferedReader(fileReader);
			
			String lineInFile;
						
			while ((lineInFile = fileInput.readLine()) != null) {
				String original = lineInFile;
				if(original.contains("import")){
					String packageName = original.split("import")[1].trim();					
					for(String subPackages : packageName.split("\\.")){						
						if(importsToDelete.containsKey("java"))
							continue;
						
						if(importsToDelete.containsKey(subPackages)){
							original = original.replaceAll("\\." + subPackages, "");
							break;
						}
					}
				}
				
				if(original.contains("package")){
					String packageName = original.split("package")[1].trim();		
					for(String subPackages : packageName.split("\\.")){		
						if(subPackages.contains(";")){
							subPackages = subPackages.split(";")[0];
						}
						if(importsToDelete.containsKey(subPackages)){
							original = original.replaceAll("\\." + subPackages, "");
							break;
						}
					}
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
			fileReader.close();
		}
		
		changeManifest();
		
		return files;
	}
	
	private void changeManifest() throws IOException{
		Set<String> keys = importsToDelete.keySet();
		FileReader fr = new FileReader(this.manifest);
		BufferedReader br = new BufferedReader(fr);
		
		List<String> outputString = new ArrayList<String>(); 
		String line;
		
		while ((line = br.readLine()) != null){
			for (String x:keys){
				if (line.contains(x) && line.contains("android:name")){
					line = line.replaceAll("." + x, "");
					break;
				}
			}
			outputString.add(line);
		}
		
		FileWriter fw = new FileWriter(this.manifest);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(String s : outputString){
			s = s + "\n";
			bw.write(s);
		}
		
		br.close();
		fr.close();
		bw.close();
		fw.close();
	}
	@Override
	public String getName() {
		return "Directory Flattening Obfuscation";
	}
}
