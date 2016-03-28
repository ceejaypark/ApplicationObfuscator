package mainGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FileChoose extends JPanel{
	
	JTextField jtf;
	JButton jb;
	String fileLoc;
	String defaultText;

	public FileChoose(String defaultText){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.defaultText = defaultText;
		addComponents();
		addListeners();
	}
	
	private void addComponents(){
		jtf = new JTextField(defaultText);
		jb = new JButton("...");
		
		this.add(jtf);
		this.add(jb);
	}
	
	private void addListeners(){
		if (jtf != null){
			jtf.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					if (jtf.getText() == defaultText){
						jtf.setText("");
						jtf.setForeground(Color.BLACK);
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (jtf.getText() == ""){
						jtf.setText(defaultText);
						jtf.setForeground(Color.LIGHT_GRAY);
					}
					
				}
				
			});
		}
		
		if(jb != null){
			jb.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					jtf.setText("fhdkhfkjdsfhdafldaf");	
				}
			});
		}
		
		
	}
}
