package mainGUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ObfCheckList extends JPanel{
	ArrayList<String> obfNames;
	
	public ObfCheckList(){
		setLayout(new GridLayout(4, 2, 0, 0));
		obfNames = new ArrayList<String>();
		populate();
	}
	
	private void populate(){
		populateObfNames();
		for(String x : obfNames){
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel.add(new JRadioButton(x));
			this.add(panel);
		}
	}
	
	private void populateObfNames(){
		obfNames.add("Class Name Obfuscation");
		obfNames.add("Method Name Obfuscation");
		obfNames.add("Variable Name Obfuscation");
		obfNames.add("Minification");
		obfNames.add("Watermark");
		obfNames.add("Comment Removal");
		obfNames.add("Bloating");
		obfNames.add("Random Code Insertion");
		
		
	}
}
