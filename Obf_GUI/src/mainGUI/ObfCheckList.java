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
		setLayout(new GridLayout(5, 2, 0, 0));
		obfNames = new ArrayList<String>();
		populate();
		 disableDFAndCR();
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
		obfNames.add("Method and Variable Name Obfuscation");
		obfNames.add("Minification");
		obfNames.add("Watermark");
		obfNames.add("Comment Removal");
		obfNames.add("Bloating");
		obfNames.add("Random Code Insertion");
		obfNames.add("Directory Flattener");
		obfNames.add("Console Output Remover (Android)");
	}
	
	public HashMap<String, JRadioButton> getCheckListMap(){
		return this.map;
	}
	
	public void disableDFAndCR(){
		map.get("Random Code Insertion").setSelected(false);
		map.get("Random Code Insertion").setEnabled(false);
		map.get("Directory Flattener").setSelected(false);
		map.get("Directory Flattener").setEnabled(false);
	}
	
	public void enableDFAndCR(){
		map.get("Random Code Insertion").setEnabled(true);
		map.get("Directory Flattener").setEnabled(true);
	}
}
