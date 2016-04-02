package mainGUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import supportClasses.MainObfRefactored;

public class ExecuteObf {
	
	private static ExecuteObf instance = null;
	
	private boolean running = false;
	private JButton exeButton;
	private ArrayList<String> blacklist;
	protected ExecuteObf(){}
	
	public static ExecuteObf getInstance(){
		if (instance == null){
			instance = new ExecuteObf();
		}
		return instance;
	}
	
	public void exe(String inputFolder, String outputFolder, ObfCheckList checklist, JButton exeButton, ArrayList<String> blacklist) throws IOException{
		this.exeButton = exeButton;
		this.blacklist = blacklist;
		setRunning(true);
		File inputLoc = new File(inputFolder);
		File outputLoc = new File(outputFolder);
		
		if(!inputLoc.isDirectory() || !outputLoc.isDirectory()){
			JOptionPane.showMessageDialog(null, "Please select a valid input or output folder", "Warning", JOptionPane.WARNING_MESSAGE);
			exeButton.setEnabled(true);
			setRunning(false);
			return;
		};
		
		MainObfRefactored mor = new MainObfRefactored(new File(inputFolder), new File(outputFolder), blacklist, checklist.getCheckListMap());
		mor.run();
		
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void setRunning (boolean running){
		this.running = running;
		exeButton.setEnabled(!running);
		
	}
}
