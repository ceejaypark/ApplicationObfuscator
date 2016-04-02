package supportClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.SwingWorker;

import mainGUI.ExecuteObf;
import obfuscate.*;

public class MainObfRefactored extends SwingWorker<Void,String>{
	private ArrayList<Obfuscater> obfuscaters = new ArrayList<Obfuscater>();
	private HashMap<String, File> filesForObf = new HashMap<String, File>();
	private HashMap<String, JRadioButton> selectedTechniques;
	private ArrayList<String> blacklist = new ArrayList<String>();

	public MainObfRefactored(File inputFolder, File outputFolder,
			ArrayList<String> blacklist,
			HashMap<String, JRadioButton> selectedTechniques)
			throws IOException {
		this.blacklist = blacklist;
		this.selectedTechniques = selectedTechniques;

		FolderCopy fc = new FolderCopy();
		fc.beginCopy(inputFolder, outputFolder);

		addFilesToHashMap(outputFolder);
		addObfuscaters();
	}

	private void addObfuscaters() {
		if (selectedTechniques.get("Class Name Obfuscation").isSelected()) {
			obfuscaters.add(new ClassNameObfuscator());
		}
		if (selectedTechniques.get("Method Name Obfuscation").isSelected()) {
			
		}
		if (selectedTechniques.get("Variable Name Obfuscation").isSelected()) {
			obfuscaters.add(new NameObfuscater());
		}
		if (selectedTechniques.get("Minification").isSelected()) {
			
		}
		if (selectedTechniques.get("Watermark").isSelected()) {
			
		}
		if (selectedTechniques.get("Comment Removal").isSelected()) {
			obfuscaters.add(new CommentRemover());
		}
		if (selectedTechniques.get("Bloating").isSelected()) {

		}
		if (selectedTechniques.get("Random Code Insertion").isSelected()) {

		}
	}

	private void addFilesToHashMap(File folder) throws IOException {
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (blacklist.contains(listOfFiles[i].getCanonicalPath())) {
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

	@Override
	protected Void doInBackground() throws Exception {
		for (int i = 0; i < obfuscaters.size(); i++){
			obfuscaters.get(i).execute(filesForObf);
			double percent = (double)i / (double)obfuscaters.size();
			publish ("" +  percent);
		}

		return null;
	}
	
	@Override
	protected void process(List<String> string){
		for(String x : string){
			System.out.println("finished " + x + "%");
		}
		
	}
	
	@Override
	protected void done(){
		ExecuteObf.getInstance().setRunning(false);
	}
}
