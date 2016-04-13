package supportClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JRadioButton;

import mainGUI.MyProgBar;
import obfuscate.*;

public class MainObfRefactored {
	private ArrayList<Obfuscater> obfuscaters = new ArrayList<Obfuscater>();
	private HashMap<String, File> filesForObf = new HashMap<String, File>();
	private HashMap<String, JRadioButton> selectedTechniques;
	private ArrayList<String> blacklist = new ArrayList<String>();
	private HashMap<String, File> mappedBlacklist = new HashMap<String, File>();
	private MyProgBar mpb;

	public MainObfRefactored(File inputFolder, File outputFolder,
			ArrayList<String> blacklist,
			HashMap<String, JRadioButton> selectedTechniques, MyProgBar mpb)
			throws IOException {
		this.blacklist = blacklist;
		this.selectedTechniques = selectedTechniques;
		this.mpb = mpb;

		addObfuscaters();
		
		FolderCopy fc = new FolderCopy();
		fc.beginCopy(inputFolder, outputFolder, blacklist);

		this.blacklist = fc.copiedBlacklist();
		this.createBlackListMap();
		
		addFilesToHashMap(outputFolder);

	}
	
	private void addObfuscaters() {

		if (selectedTechniques.get("Class Name Obfuscation").isSelected()) {
		}
		if (selectedTechniques.get("Method and Variable Name Obfuscation").isSelected()) {
		}
		if (selectedTechniques.get("Minification").isSelected()) {
		}
		if (selectedTechniques.get("Watermark").isSelected()) {
		}
		if (selectedTechniques.get("Comment Removal").isSelected()) {
		}
		if (selectedTechniques.get("Bloating").isSelected()) {
		}
		if (selectedTechniques.get("Random Code Insertion").isSelected()) {
		}
		if (selectedTechniques.get("Directory Flattener").isSelected()) {
		}
		if (selectedTechniques.get("Console Output Remover (Android)").isSelected()) {
		}
	}

	private void addFilesToHashMap(File folder) throws IOException {
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			boolean skip = false;
			for (String x : blacklist) {

				if (x.equals(listOfFiles[i].getCanonicalPath())) {
					skip = true;
					break;
				}
			}
			if (skip) {
				continue;
			} else {
				if (listOfFiles[i].isDirectory()) {
					addFilesToHashMap(listOfFiles[i]);
				} else if (listOfFiles[i].isFile()) {
					if (getFileExtension(listOfFiles[i]).equals("java")) {
						filesForObf.put(listOfFiles[i].getCanonicalPath(),
								listOfFiles[i]);
					}
				}
			}
		}
	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		String[] nameSplit = fileName.split("\\.");

		if (nameSplit.length < 2) {
			return "";
		}

		return nameSplit[nameSplit.length - 1];
	}
	
	private void createBlackListMap(){
		for (String x :blacklist){
			mappedBlacklist.put(x, new File(x));
		}
	}
}
