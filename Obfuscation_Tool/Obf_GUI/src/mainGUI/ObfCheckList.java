package mainGUI;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

import obfuscate.*;

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
	private ArrayList<String> obfGrouping;
	private HashMap<String, JRadioButton> map = new HashMap<String, JRadioButton>();
	private ButtonGroup bg = new ButtonGroup();
	
	public ObfCheckList(){
		setLayout(new GridLayout(5, 2, 0, 0));
		obfGrouping= new ArrayList<String>();
		populate();
	}
	
	/**
	 * This method is responsible for adding the elements into the checklist
	 * which user will use to select the level of obfuscation required
	 * 
	 * @param
	 * @return
	 */
	private void populate(){
		populateObfNames();
		for(String x : obfGrouping){
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.LEFT));
			JRadioButton jrb = new JRadioButton(x);
			if(x.equals("Standard Obfuscation")){
				jrb.setSelected(true);
			}else{
				jrb.setSelected(false);
			}
			bg.add(jrb);
			panel.add(jrb);
			this.map.put(x, jrb);
			this.add(panel);
		}
	}
	
	/**
	 * This method is a helper method to separate the addition of strings
	 * required for the hashmap
	 * 
	 * @param
	 * @return
	 */
	private void populateObfNames(){
		obfGrouping.add("Minimal Obfuscation (not recommended)");
		obfGrouping.add("Standard Obfuscation");
		obfGrouping.add("Extensive Obfuscation (may impede performance)");
	}
	
	
	/**
	 * This method is used to retrieve the hashmap of the selected buttons
	 * 
	 * @param
	 * @return HashMap<String, JRadioButton>
	 */
	public HashMap<String, JRadioButton> getCheckListMap(){
		return this.map;
	}
	
	/**
	 * 
	 */
	public ArrayList<Obfuscator> getActiveMethods(){
		ArrayList<Obfuscator> returnVal = new ArrayList<Obfuscator>();
		if(map.get("Minimal Obfuscation (not recommended)").isSelected()){
			returnVal.add(new WatermarkObfuscator());
			returnVal.add(new CommentRemovalObfuscator());
			returnVal.add(new LogDeleteObfuscator());
		}
		else if(map.get("Standard Obfuscation").isSelected()){
			returnVal.add(new WatermarkObfuscator());
			returnVal.add(new CommentRemovalObfuscator());
			returnVal.add(new ClassNameObfuscator());
			returnVal.add(new NameObfuscator());
			returnVal.add(new LogDeleteObfuscator());
		}
		else if(map.get("Extensive Obfuscation (may impede performance)").isSelected()){
			returnVal.add(new PictureEncryptionObfuscator());
			returnVal.add(new WatermarkObfuscator());
			returnVal.add(new CommentRemovalObfuscator());
			returnVal.add(new CodeInsertionObfuscator());
			returnVal.add(new ClassNameObfuscator());
			returnVal.add(new NameObfuscator());
			returnVal.add(new DirectoryFlattenerObfuscator());
			returnVal.add(new LogDeleteObfuscator());
			returnVal.add(new BloatObfuscator());
		}
		return returnVal;
	}
	
}
