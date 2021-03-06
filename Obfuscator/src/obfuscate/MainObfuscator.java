package obfuscate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

public class MainObfuscator {
	private static final String CONFIG = ".\\resources\\config.properties";

	// lists and sets of files and obfuscater
	public static ArrayList<Obfuscater> obfuscaters = new ArrayList<Obfuscater>();
	public static HashMap<String, File> filesForObfuscation = new HashMap<String, File>();
	public static List<String> blackList = new ArrayList<String>();
	public static HashMap<String, File> mappedBlacklist = new HashMap<String, File>();
	public static File manifest;
	public static String OUTPUT = "";
	public static String INPUT = "";
	public static File sourceFolder;
	public static String srcPackage = "";
	
	public static void main(String[] args) throws IOException {

		// variables used for opening and loading config.properties
		Properties configProperties = new Properties();
		InputStream configFileInputStream = null;

		// load config.properties into property variable
		configFileInputStream = new FileInputStream(CONFIG);
		configProperties.load(configFileInputStream);

		// retrieve the input directory from the configuration file
		File inputDir = new File(configProperties.getProperty("input"));
		String inputDirectoryName = new File(configProperties.getProperty("input")).getName();
		INPUT = inputDir.getCanonicalPath();
		
		// retrieve the output directory from the configuration file
		// further add in the directory, "-obfuscated" added to the input directory name
		String outputDirectoryName = "\\" + inputDirectoryName + "-obfuscated";
		File outputDir = new File(configProperties.getProperty("output") + outputDirectoryName);
		OUTPUT = outputDir.getCanonicalPath();
		
		if(!outputDir.exists()){
			outputDir.mkdir();
		}
		
		//copy all files from input directory to the output directory
		copyFolder(inputDir,outputDir);
		sourceFolder = getSourceFolder();
		setSourcePackage();
		
		// ------------------------------------FILE HASHMAP ADDITION---------------------------------//
		// get the black list of files to ignore for obfuscation
		String singleStringBlackList = configProperties.getProperty("blacklist");
		String[] blackListAsArray = singleStringBlackList.split(",");
		blackList = addBlackListToList(blackListAsArray);
		for (String x : blackList){
			mappedBlacklist.put(x,  new File(x));
		}
		
		// add non black listed files to hash map
		addFilesToHashMap(outputDir);

		// ------------------------------------OBFUSCATER ADDITION------------------------------------//
		// add appropriate classes to the list of obfuscater
		if (Boolean.parseBoolean(configProperties.getProperty("pictureencrypt"))){
			obfuscaters.add(new PictureEncryptionObfuscator());
		}
		if (Boolean.parseBoolean(configProperties.getProperty("watermark"))){
			//obfuscaters.add(new WatermarkObfuscator());
			obfuscaters.add(new WatermarkObfuscator());
		}
		if (Boolean.parseBoolean(configProperties.getProperty("commentremoval"))) {
			// add comment removing obfuscater
			obfuscaters.add(new CommentRemover());
		} 
		if (Boolean.parseBoolean(configProperties.getProperty("insertcode"))) {
			// add code insertion obfuscater
			obfuscaters.add(new CodeInsertionObfuscater());
		}		
		if (Boolean.parseBoolean(configProperties.getProperty("renameclass"))) {
			// add to 'obfuscaters', rename class obfuscater class
			obfuscaters.add(new ClassNameObfuscator());
		} 
		if (Boolean.parseBoolean(configProperties.getProperty("minification"))) {
			// add to 'obfuscaters', minification obfuscater class
			
		}
		if (Boolean.parseBoolean(configProperties.getProperty("renamelocalvariables"))) {
			// add to 'obfuscaters', rename local variable obfuscater class
			obfuscaters.add(new NameObfuscater());
		}
		if (Boolean.parseBoolean(configProperties.getProperty("directoryflatenor"))){
			// add to 'obfuscaters', get rid of directories
			obfuscaters.add(new DirectoryFlatenorObfuscator());
		}
		if (Boolean.parseBoolean(configProperties.getProperty("logdelete"))){
			// add to 'obfuscaters', get rid of logs
			obfuscaters.add(new LogDeleteObfuscator());
		}
		if (Boolean.parseBoolean(configProperties.getProperty("bloating"))) {
			obfuscaters.add(new Bloating());
		}
		
		
		// execute every obfuscation process in order
		for (Obfuscater obfuscaterProcess : obfuscaters) {
			long startTime = System.nanoTime(); 
			System.out.println("Processing: " + obfuscaterProcess.getClass().toString());
			filesForObfuscation = obfuscaterProcess.execute(filesForObfuscation, mappedBlacklist, manifest);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime); 
			System.out.println("Took: " + duration);
		}

	}

	/**
	 * Converts an string array of black list file names into a string list of
	 * canonical file paths.
	 * 
	 * @param stringArray
	 *            array of black list file names written as in the config file
	 * @return string list of the canonical file path that should be ignored
	 */
	private static List<String> addBlackListToList(String[] stringArray) {
		List<String> outputList = new ArrayList<String>();

		// add each of the black list in canonical path format
		for (int i = 0; i < stringArray.length; i++) {
			outputList.add(OUTPUT + "\\" + stringArray[i]);
		}

		return outputList;
	}

	/**
	 * Adds files not black listed into the hash map for files for obfuscation.
	 * Recursively calls directories contained within the first folder.
	 * 
	 * @param folder
	 *            folder to add files from
	 * @throws IOException
	 */
	private static void addFilesToHashMap(File folder) throws IOException {
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			// if the blacklist contains the file name
			if (blackList.contains(listOfFiles[i].getCanonicalPath())) {
				// do not add file to hash map and continue
				continue;
			}
			
			else if(listOfFiles[i].getCanonicalPath().contains("AndroidManifest.xml")){
				manifest = listOfFiles[i];
			}
			
			else {
				if (listOfFiles[i].isDirectory()) {
					//recursive call to any directory
					addFilesToHashMap(listOfFiles[i]);
				} else if (listOfFiles[i].isFile()) {
					if (getFileExtension(listOfFiles[i]).equals("java")) {
						// add files to hash map since not black listed and is java file
						filesForObfuscation.put(listOfFiles[i].getCanonicalPath(), listOfFiles[i]);
					}
				}
			}
		}
	}
	
	/**
	 * Copies all files and folders recursively from src to dest.
	 * Code from "http://www.mkyong.com/java/how-to-copy-directory-in-java/" and modified
	 * @param src is the source folder where the files and folders are copied from
	 * @param dest is the destination folder where the files and folders are copied to
	 * @throws IOException
	 */
	private static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdirs();
				//System.out.println("Directory copied from " + src + "  to " + dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			//System.out.println("File copied from " + src + " to " + dest);
		}
	}
	
	/**
	 * Get the extension of a file
	 * @param file
	 * @return
	 */
	private static String getFileExtension(File file) {
		String fileName = file.getName();
		String[] nameSplit = fileName.split("\\.");
		
		//if file has no extension return nothing
		if (nameSplit.length < 2) {
			return "";
		}
		
		return nameSplit[nameSplit.length-1];
	}
	
	/**
	 * Find and set the compulsory package required for putting it in source folder
	 * @throws IOException 
	 */
	private static void setSourcePackage() throws IOException{
		int posTracker = -1;
		String[] projPathSplit = INPUT.split("\\\\");
		String[] srcPathSplit = sourceFolder.getCanonicalPath().split("\\\\");
		if(projPathSplit.length > srcPathSplit.length){
			return;
		}
		
		while(posTracker + 1 < projPathSplit.length && posTracker < srcPathSplit.length && projPathSplit[posTracker + 1].equals(srcPathSplit[posTracker + 1])){
			posTracker++;
		}
		
		if(posTracker < 0){
			return;
		}
		
		StringBuilder packageTemp = new StringBuilder();
		
		for (int i = (posTracker + 1); i < srcPathSplit.length; i++){
			if(srcPathSplit[i].equals("app") && srcPathSplit[i+1].equals("src") && srcPathSplit[i+2].equals("main") && srcPathSplit[i+3].equals("java")){
				i = i+3;
				packageTemp = new StringBuilder();
				continue;
			}
			
			packageTemp.append(srcPathSplit[i]);
			
			
			if (i != srcPathSplit.length -1){
				packageTemp.append(".");
			}
		}
		
		srcPackage = packageTemp.toString();
	}
	
	private static File getSourceFolder() {
		return getSourceFolder(new File(OUTPUT));
	}
	
	public static File getSourceFolder(File root)
	{		
	    File[] files = root.listFiles(); 	    
	    for (File file : files) {
	    	if (file.isFile()) {
	            try{
	            	String[] split = file.getName().split("\\.");
	                String ext = split[split.length - 1];
	            	if(ext.contains("java"))
	            		return root;
	            }catch(Exception e){
	            	continue;
	            }
	    	}
	    }
	    
	    for (File file : files) {
	        try {
				if(file.getCanonicalPath().contains("Test"))
					continue;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	if (file.isDirectory()) {
	            File folder = getSourceFolder(file);
	            if(folder != null) return folder;
	        }
	    }
	    return null;
	}
}
