package mainGUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import supportClasses.MainObfRefactored;

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
		
		
		MainObfRefactored mor = new MainObfRefactored(new File(inputFolder), new File(outputFolder), this.blacklist, checklist.getCheckListMap(), this.mpb);
		
		
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void setRunning (boolean running){
		this.running = running;
		exeButton.setEnabled(!running);
		
	}
}
