package mainGUI;

import javax.swing.JProgressBar;

/**
 * Extended JProgressBar to allow for new implementation the the
 * setValue method.
 * 
 * @author cwu323
 * @extends JProgressBar
 */
@SuppressWarnings("serial")
public class MyProgBar extends JProgressBar {

	public MyProgBar() {
		restart();
	}

	/**
	 * Method to reset the progress bar
	 * 
	 */
	public void restart() {
		this.setMaximum(1000);
		this.setStringPainted(true);
		this.setValue(0);
		this.setString("Begin obfuscation by clicking the \"Obfuscate\" button.");
	}

	//Create custom overload to change value
	public void setValue(String info, int value){
		this.setValue(value);
		this.setString(info);
	}
}
