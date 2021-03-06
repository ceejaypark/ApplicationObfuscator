package user_interface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")

/**
 * Implementation of JPanel to contain 3 GUI elements
 * A label, textfield and button. Responsible for selecting directories in the GUI
 * @author cwu323
 *
 */
public class FileChoose extends JPanel{
	
	private JLabel jta;
	private JTextField jtf;
	private JButton jb;
	private String defaultText;
	private String inputPrompt;
	private String restriction = "";

	/**
	 * Instantiates the GUI element.
	 * Sets input text and default text of the element
	 * 
	 * @param inputPrompt
	 * @param defaultText
	 */
	public FileChoose(String inputPrompt, String defaultText){
		this.inputPrompt = inputPrompt;
		this.defaultText = defaultText;
		addComponents();
		addListeners();
	}
	
	/**
	 * Method to add the sub components
	 */
	private void addComponents(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 87, 279, 44, 20, 0};
		gridBagLayout.rowHeights = new int[]{23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		jta = new JLabel(this.inputPrompt);
		GridBagConstraints gbc_jta = new GridBagConstraints();
		gbc_jta.insets = new Insets(0, 0, 0, 5);
		gbc_jta.gridx = 1;
		gbc_jta.gridy = 0;
		this.add(jta, gbc_jta);
		
		jtf = new JTextField(this.defaultText);
		jtf.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_jtf = new GridBagConstraints();
		gbc_jtf.fill = GridBagConstraints.HORIZONTAL;
		gbc_jtf.insets = new Insets(0, 0, 0, 5);
		gbc_jtf.gridx = 2;
		gbc_jtf.gridy = 0;
		this.add(jtf, gbc_jtf);
		
		jb = new JButton("...");
		GridBagConstraints gbc_jb = new GridBagConstraints();
		gbc_jb.insets = new Insets(0, 0, 0, 5);
		gbc_jb.gridx = 3;
		gbc_jb.gridy = 0;
		this.add(jb, gbc_jb);
	}
	
	
	/**
	 * adding focus listeners to the input textfield
	 */
	private void addListeners(){
		if (jtf != null){
			jtf.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					if (jtf.getText().equals(defaultText) && jtf.isEditable()){
						jtf.setText("");
						jtf.setForeground(Color.BLACK);
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						setDefaultText();
					}
				}
				
			});
		}
		
		if(jb != null){
			jb.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					MyDirChooser mfc = new MyDirChooser();
					if(!restriction.equals("")){
						mfc.setRestriction(restriction);
					}
					String temp = mfc.activate();
					if (!temp.equals("")){
						jtf.setText(temp);
						jtf.setForeground(Color.BLACK);
					}
				}
			});
		}
	}
	
	//Public getters and setters
	public String getFolderLoc(){
		return jtf.getText();
	}
	
	public JTextField getTextField(){
		return this.jtf;
	}
	
	public JButton getButton(){
		return this.jb;
	}
	
	public void restrict(String loc){
		this.restriction = loc;
	}
	
	public void setDefaultText(){
		jtf.setText(this.defaultText);
		jtf.setForeground(Color.LIGHT_GRAY);
	}
}
