package mainGUI;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class is responsible for the panel that displays what obfuscation techniques
 * are available for selection. 
 * 
 * CHANGE THIS FILE IF ANY OBFUSCATION TECHNIQUES ARE ADDED SO USER CAN SELECT IT.
 * @author cwu323
 *
 */
@SuppressWarnings("serial")
public class ObfCheckList extends JPanel{
	private ArrayList<String> obfNames;
	private HashMap<String, JRadioButton> map = new HashMap<String, JRadioButton>();
	
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
			JRadioButton jrb = new JRadioButton(x);
			jrb.setSelected(true);
			panel.add(jrb);
			this.map.put(x, jrb);
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
	
	public HashMap<String, JRadioButton> getCheckListMap(){
		return this.map;
	}
}
