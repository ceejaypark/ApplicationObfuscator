package mainGUI;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ExecuteObf {
	
	private static ExecuteObf instance = null;
	
	private boolean running = false;
	
	protected ExecuteObf(){}
	
	public static ExecuteObf getInstance(){
		if (instance == null){
			instance = new ExecuteObf();
		}
		return instance;
	}
	
	public void exe(String inputFolder, String outputFolder, ObfCheckList checklist, JButton exeButton){
		running = true;
		exeButton.setEnabled(false);
		File inputLoc = new File(inputFolder);
		File outputLoc = new File(outputFolder);
		
		if(!inputLoc.isDirectory() || !outputLoc.isDirectory()){
			JOptionPane.showMessageDialog(null, "Please select a valid input or output folder", "Warning", JOptionPane.WARNING_MESSAGE);
		};
	}
	
	public boolean isRunning(){
		return running;
	}
}
