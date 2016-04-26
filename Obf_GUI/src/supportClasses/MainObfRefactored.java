package supportClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;

import mainGUI.MyProgBar;
import obfuscate.*;

public class MainObfRefactored extends SwingWorker<Void,Integer>{
	private ArrayList<Obfuscator> obfuscators = new ArrayList<Obfuscator>();
	private HashMap<String, File> filesForObf = new HashMap<String, File>();
	private ArrayList<String> blacklist = new ArrayList<String>();
	private HashMap<String, File> mappedBlacklist = new HashMap<String, File>();
	private MyProgBar mpb;
	private File manifest;
	private File inputFolder;
	public static File outputFolder;
	public static File sourceFolder;
	public static String srcPackage;

	
	public MainObfRefactored(File inputFolder, File outputFolder,
			ArrayList<String> blacklist,
			ArrayList<Obfuscator> obfs, MyProgBar mpb)
			throws IOException {
		this.blacklist = blacklist;
		this.mpb = mpb;
		this.obfuscators = obfs;
		this.inputFolder = inputFolder;
		this.outputFolder = outputFolder;
		
		mpb.setMaximum(obfuscators.size() + 1);
		
		mpb.setValue("Copying files... %0", 0);
		
		FolderCopy fc = new FolderCopy();
		fc.beginCopy(inputFolder, outputFolder, blacklist);
		
		sourceFolder = getSourceFolder();
		setSourcePackage();
		this.blacklist = fc.copiedBlacklist();
		this.createBlackListMap();
		
		addFilesToHashMap(outputFolder);

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
				}
				else if(listOfFiles[i].getCanonicalPath().contains("AndroidManifest.xml")){
					manifest = listOfFiles[i];
				} 
				else if (listOfFiles[i].isFile()) {
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

	//=====================================================================================================================================
	/**
	 * Find and set the compulsory package required for putting it in source folder
	 * @throws IOException 
	 */
	private void setSourcePackage() throws IOException{
		int posTracker = -1;
		String[] projPathSplit = inputFolder.getCanonicalPath().split("\\\\");
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
	
	private File getSourceFolder() {
		return getSourceFolder(outputFolder);
	}
	
	public File getSourceFolder(File root)
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
	
	//=====================================================================================================================================
	@Override
	protected Void doInBackground() throws Exception {
		int count = 0;
		for(Obfuscator o : obfuscators){
			publish(count + 2);
			filesForObf = o.execute(filesForObf, mappedBlacklist, manifest);
			count++;
		}
		return null;
	}
	
	@Override
	protected void process(List<Integer> chunks){
		double percentDone =  Math.floor(100.0 * ((double)chunks.get(chunks.size() -1 ))/((double)mpb.getMaximum()));
		String info = "Obfuscating using " + obfuscators.get( chunks.get(chunks.size() -1 )).getName() + " - " + percentDone + "%";
		mpb.setValue( info, chunks.get(chunks.size() -1 ) + 2);
	}
	
	@Override
	protected void done(){
		mpb.setValue("Obfuscation Complete - 100%",mpb.getMaximum());
		JOptionPane.showMessageDialog(null, "Obfuscation complete.");
		ExecuteObf.getInstance().setRunning(false);
	}
}
