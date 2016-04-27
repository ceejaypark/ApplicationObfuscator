package supportClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import mainGUI.MyProgBar;
import mainGUI.ObfCheckList;


/**
 * Singleton class to ensure that only one obfuscation process gets run.
 * Stores state of the process and toggles GUI appropriately
 * 
 * @author cwu323
 *
 */
public class ExecuteObf {
	
	private static ExecuteObf instance = null;
	
	private boolean running = false;
	private JButton exeButton;
	private ArrayList<String> blacklist;
	private MyProgBar mpb;
	protected ExecuteObf(){}
	
	public static ExecuteObf getInstance(){
		if (instance == null){
			instance = new ExecuteObf();
		}
		return instance;
	}
	
	/**
	 * Method to begin the obfuscation process, which runs on a background thread
	 * 
	 * @param inputFolder
	 * @param outputFolder
	 * @param checklist
	 * @param exeButton
	 * @param blacklist
	 * @param mpb
	 * @throws IOException
	 */
	public void exe(String inputFolder, String outputFolder, ObfCheckList checklist, JButton exeButton, ArrayList<String> blacklist, MyProgBar mpb) throws IOException{
		this.exeButton = exeButton;
		this.blacklist = blacklist;
		this.mpb = mpb;
		setRunning(true);
		File inputLoc = new File(inputFolder);
		File outputLoc = new File(outputFolder);
		
		if(!inputLoc.isDirectory() || !outputLoc.isDirectory()){
			JOptionPane.showMessageDialog(null, "Please select a valid input or output folder", "Warning", JOptionPane.WARNING_MESSAGE);
			exeButton.setEnabled(true);
			setRunning(false);
			return;
		};
		
		MainObfRefactored mor = new MainObfRefactored(new File(inputFolder), new File(outputFolder), this.blacklist, checklist.getActiveMethods(), this.mpb);
		mor.execute();
		
	}
	
	/**
	 * Method to check if an obfuscation process is running
	 * 
	 * @return bool running
	 */
	public boolean isRunning(){
		return running;
	}
	
	/**
	 * Method to set the status of the background progress
	 * Enables/Disables correspinding GUI elements
	 * 
	 * @param running
	 */
	public void setRunning (boolean running){
		this.running = running;
		exeButton.setEnabled(!running);
		this.mpb.restart();
	}
}
