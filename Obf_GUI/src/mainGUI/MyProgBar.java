package mainGUI;

import javax.swing.JProgressBar;

public class MyProgBar extends JProgressBar{

	public MyProgBar(){
		this.setStringPainted(true);
		this.setString("Begin obfuscation by clicking the \"Obfuscate\" button.");
	}
	
	public void changeVal(String info, int increase){
		double currentVal = this.getPercentComplete();
		double newVal = currentVal + increase;
		
		String newText = newVal + " - " + info;
		this.setString(newText);
	}
	
	
	
}
