package obfuscate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

public class MainObfuscater {
	private static final String CONFIG = ".\\resources\\config.properties";
	private static String TARGET = "";

	// lists and sets of files and obfuscater
	public static ArrayList<Obfuscater> obfuscaters = new ArrayList<Obfuscater>();
	public static HashMap<String, File> filesForObfuscation = new HashMap<String, File>();
	public static List<String> blackList = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		// variables used for opening and loading config.properties
		Properties configProperties = new Properties();
		InputStream configFileInputStream = null;

		// load config.properties into property variable
		configFileInputStream = new FileInputStream(CONFIG);
		configProperties.load(configFileInputStream);

		// retrieve the target directory for output files
		TARGET = new File(configProperties.getProperty("target")).getCanonicalPath();

		// ------------------------------------FILE HASHMAP
		// ADDITION---------------------------------//
		// get the black list of files to ignore for obfuscation
		String singleStringBlackList = configProperties.getProperty("blacklist");
		String[] blackListAsArray = singleStringBlackList.split(",");
		blackList = addBlackListToList(blackListAsArray);
		// add non black listed files to hash map
		File targetFolder = new File(TARGET);
		addFilesToHashMap(targetFolder);

		// ------------------------------------OBFUSCATER
		// ADDITION------------------------------------//
		// add appropriate classes to the list of obfuscater
		if (Boolean.parseBoolean(configProperties.getProperty("commentremoval"))) {
			// add to 'obfuscaters', comment removing obfuscater class
		} else if (Boolean.parseBoolean(configProperties.getProperty("renamelocalvariables"))) {
			// add to 'obfuscaters', rename local variable obfuscater class
		} else if (Boolean.parseBoolean(configProperties.getProperty("renamefields"))) {
			// add to 'obfuscaters', rename field obfuscater class
		} else if (Boolean.parseBoolean(configProperties.getProperty("renameclass"))) {
			// add to 'obfuscaters', rename class obfuscater class
		} else if (Boolean.parseBoolean(configProperties.getProperty("minification"))) {
			// add to 'obfuscaters', minification obfuscater class
		}

		// --------------RANDOM TEST----------------//
		for (Entry<String, File> entry : filesForObfuscation.entrySet()) {
			System.out.println("KEY: " + entry.getKey() + " *** " + "FILE: " + entry.getValue().getName());
		}
	}

	/**
	 * Converts an string array of black list file names into a string list of
	 * canonical file paths
	 * 
	 * @param stringArray
	 *            array of black list file names written as in the config file
	 * @return string list of the canonical file path that should be ignored
	 */
	private static List<String> addBlackListToList(String[] stringArray) {
		List<String> outputList = new ArrayList<String>();

		// add each of the black list in canonical path format
		for (int i = 0; i < stringArray.length; i++) {
			outputList.add(TARGET + "\\" + stringArray[i]);
		}

		return outputList;
	}

	/**
	 * Adds files not black listed into the hash map for files for obfuscation
	 * Recursively calls directories contained within the first folder
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
			} else {
				if (listOfFiles[i].isDirectory()) {
					//recursive call to any directory
					addFilesToHashMap(listOfFiles[i]);
				} else if (listOfFiles[i].isFile()) {
					//add files to hash map since not black listed
					filesForObfuscation.put(listOfFiles[i].getCanonicalPath(), listOfFiles[i]);
				}
			}
		}
	}
}
