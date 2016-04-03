package mainGUI;

import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class MyProgBar extends JProgressBar {

	public MyProgBar() {
		restart();
	}

	public void changeVal(String info, int increase) {
		this.setValue(increase);

		double percent = ((double)increase /(double)this.getMaximum()) * 100;
		
		String newText = (int)percent + "% - " + info;
		this.setString(newText);
	}

	public void restart() {
		this.setMaximum(1000);
		this.setStringPainted(true);
		this.setValue(0);
		this.setString("Begin obfuscation by clicking the \"Obfuscate\" button.");
	}

}
