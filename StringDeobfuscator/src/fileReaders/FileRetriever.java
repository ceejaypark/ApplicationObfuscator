package fileReaders;

import java.io.File;
import java.util.ArrayList;
/**
 * Class containing methods to retrieve the java files of a specific folder
 * 
 * @author chuan
 *
 */
public class FileRetriever {
	private File inputFolder;
	private ArrayList<File> javaFiles = new ArrayList<File>();
	private ArrayList<File> propFiles = new ArrayList<File>();
	
	public FileRetriever(File inputFolder){
		this.inputFolder = inputFolder;
		locateJavaFiles(inputFolder);
	}
	
	public ArrayList<File> getJavaFiles(){
		return this.javaFiles;
	}
	
	public File getInputFolder(){
		return this.inputFolder;
	}
	
	public ArrayList<File> getPropertyFiles(){
		return this.propFiles;
	}
	
	/**
	 * Method to populate the array list with the java files present
	 * in input folder
	 * 
	 * @param input
	 */
	private void locateJavaFiles(File input){
		File[] innerFiles = input.listFiles();
		for(File f : innerFiles){
			if(f.isDirectory()){
				locateJavaFiles(f);
			}else if(getFileExtension(f).equals("java")){
				this.javaFiles.add(f);
			}
			else if(getFileExtension(f).equals("properties")){
				this.propFiles.add(f);
			}
		}
	}
	
	/**
	 * Helper class to find the extension of the file
	 * @param f
	 * @return
	 */
	private String getFileExtension(File f){
		String name = f.getName();
		String[] nameSplit = name.split("\\.");
		
		if(name.length() < 2){
			return "";
		}
		
		return nameSplit[nameSplit.length - 1];
	}
}
