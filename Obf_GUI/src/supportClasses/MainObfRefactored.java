package supportClasses;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.SwingWorker;

import mainGUI.ExecuteObf;
import mainGUI.MyProgBar;
import obfuscate.*;

public class MainObfRefactored extends SwingWorker<Void, String> implements PropertyChangeListener{
	private ArrayList<Obfuscater> obfuscaters = new ArrayList<Obfuscater>();
	private HashMap<String, File> filesForObf = new HashMap<String, File>();
	private HashMap<String, JRadioButton> selectedTechniques;
	private ArrayList<String> blacklist = new ArrayList<String>();
	private MyProgBar mpb;
	private int totalTasks;
	private int currentTask = 0;
	private ArrayList<String> infos = new ArrayList<String>();

	public MainObfRefactored(File inputFolder, File outputFolder,
			ArrayList<String> blacklist,
			HashMap<String, JRadioButton> selectedTechniques, MyProgBar mpb)
			throws IOException {
		this.blacklist = blacklist;
		this.selectedTechniques = selectedTechniques;
		this.mpb = mpb;
		this.addPropertyChangeListener(this);
		addObfuscaters();
		
		totalTasks = obfuscaters.size()+2;
		infos.add("Preparing files to obfuscate.");
		setProgress(currentTask);
		
		FolderCopy fc = new FolderCopy();
		fc.beginCopy(inputFolder, outputFolder, blacklist);

		this.blacklist = fc.copiedBlacklist();
		
		addFilesToHashMap(outputFolder);

		currentTask++;
		infos.add("Beginning obfuscation process.");
		setProgress(currentTask);
	}

	private int progBarVal(){
		double percentComplete = ((double)currentTask) /((double)totalTasks);
		
		double val = ((double)mpb.getMaximum()) * percentComplete;
		return (int)val;
		
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

	@Override
	protected Void doInBackground() throws Exception {
		Thread.sleep(200);
		for (int i = 0; i < obfuscaters.size(); i++) {
			currentTask++;
			infos.add("Obfuscation technique " + (i+1) + " out of "+ obfuscaters.size()+" is executing.");
			setProgress(currentTask);
			obfuscaters.get(i).execute(filesForObf);
			Thread.sleep(2000);
			
		}
		
		infos.add("Obfuscation completed.");
		currentTask++;
		setProgress(currentTask);
		Thread.sleep(1000);

		return null;
	}

	@Override
	protected void process(List<String> string) {
		for(String x: string){
			mpb.changeVal(x, progBarVal());
			mpb.revalidate();
			mpb.repaint();
		}
	}

	@Override
	protected void done() {
		mpb.restart();
		ExecuteObf.getInstance().setRunning(false);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if("progress" == e.getPropertyName()){
			mpb.changeVal(infos.get(currentTask), progBarVal());
			if(currentTask == totalTasks){
				infos.clear();
			}
		}
		
	}
}
